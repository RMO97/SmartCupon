/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.EmpresaDAO;
import clienteescritoriosmartcupon.modelo.pojo.Empresa;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.utils.Utilidades;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Richard
 */
public class FXMLFormularioEmpresaController implements Initializable {

    private boolean empresaEdit = true;
    private int idEmpresaEdit;
    private File logo;
    
    @FXML
    private TextField tfNombreEmpresa;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfNombreComercial;
    @FXML
    private TextField tfCiudad;
    @FXML
    private TextField tfNombreRepresentante;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfPaginaWeb;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfRfc;
    @FXML
    private TextField tfNumero;
    @FXML
    private ImageView ivLogo;
    @FXML
    private Button btnSeleccionar;
    @FXML
    private Button btnSubir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ivLogo.setVisible(false);
        btnSeleccionar.setVisible(false);
        btnSubir.setVisible(false);
    }    

    
    public void inicializarFormulario(Empresa empresa)throws ParseException{
        if(empresa == null){
            this.empresaEdit = false;
        }else{
            tfNombreEmpresa.setText(empresa.getNombreEmpresa());
            tfNombreComercial.setText(empresa.getNombreComercial());
            tfCodigoPostal.setText(empresa.getCodigoPostal());
            tfCiudad.setText(empresa.getCiudad());
            tfNombreRepresentante.setText(empresa.getNombreRepresentante());
            tfPaginaWeb.setText(empresa.getPaginaWeb());
            tfTelefono.setText(empresa.getTelefono());
            tfRfc.setText(empresa.getRfc());
            
            if (empresa.getDireccion() != null && !empresa.getDireccion().isEmpty()) {
            String direccion = empresa.getDireccion();
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d+");
            java.util.regex.Matcher matcher = pattern.matcher(direccion);
                if (matcher.find()) {
                    tfCalle.setText(direccion.substring(0, matcher.start()).trim());
                    tfNumero.setText(matcher.group());
                } 
            }
            this.idEmpresaEdit = empresa.getIdEmpresa();
            ivLogo.setVisible(true);
            btnSeleccionar.setVisible(true);
            btnSubir.setVisible(true);
            obtenerLogoEmpresa(empresa.getIdEmpresa());
        }
    }
    
    public void obtenerLogoEmpresa(int idEmpresa){
        Empresa empresaLogo = EmpresaDAO.obtenerLogoEmpresa(idEmpresa);
        if(empresaLogo!=null&& empresaLogo.getLogoEmpresaBase64()!=null && empresaLogo.getLogoEmpresaBase64().length()>0){
            byte[] decodeImage = Base64.getDecoder().decode(empresaLogo.getLogoEmpresaBase64().replaceAll("\\n", ""));
            Image image = new Image(new ByteArrayInputStream(decodeImage));
            ivLogo.setImage(image);
        }else{
            Utilidades.mostrarAlertaSimple("No hay contenido", "Esta empresa no tiene logo", Alert.AlertType.INFORMATION);
        }
        
    }
    
    private File dialogoMostrarSeleccion(){
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter filtroArchivos = new FileChooser.ExtensionFilter("Archivos de imagen (*.png, *.jpg, *.jpeg, *.svg)", "*.PNG","*.JPG","*.JPEG","*.SVG");
        dialogoSeleccionImg.getExtensionFilters().add(filtroArchivos);
        Stage stage = (Stage)tfNombreEmpresa.getScene().getWindow();
        return dialogoSeleccionImg.showOpenDialog(stage);
    }           
         
    
     private void mostrarImagenSeleccionada(File logo){
        try{
            BufferedImage buffer = ImageIO.read(logo);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivLogo.setImage(image);
        }catch(IOException ioe){
            Utilidades.mostrarAlertaSimple("Error al cargar", "Error al intentar vizualizar la imagen seleccionada", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void btnGuardar(ActionEvent event) {
        String nombreEmpresa = tfNombreEmpresa.getText();
        String nombreComercial = tfNombreComercial.getText();
        String nombreRepresentante = tfNombreRepresentante.getText();
        String codigoPostal = tfCodigoPostal.getText();
        String pagWeb = tfPaginaWeb.getText();
        String direccion = ""+tfCalle.getText()+" "+tfNumero.getText();
        String telefono = tfTelefono.getText();
        String rfc = tfRfc.getText();
        String ciudad = tfCiudad.getText();
        boolean isValido = true;
        if(nombreEmpresa.isEmpty()){
            isValido = false;
        }
        if(nombreComercial.isEmpty()){
            isValido = false;
        }
        if(nombreRepresentante.isEmpty()){
            isValido = false;
        }
        if(codigoPostal.isEmpty()){
            isValido = false;
        }
        if(pagWeb.isEmpty()){
            isValido = false;
        }
        if(direccion.isEmpty()){
            isValido = false;
        }
        if(telefono.isEmpty()){
            isValido = false;
        }
        if(rfc.isEmpty()){
            isValido = false;
        }
        if(ciudad.isEmpty()){
            isValido = false;
        }
        
        if(isValido){
            if(empresaEdit){
                Empresa empresaEditada = new Empresa();
                empresaEditada.setIdEmpresa(idEmpresaEdit);
                empresaEditada.setNombreEmpresa(nombreEmpresa);
                empresaEditada.setNombreComercial(nombreComercial);
                empresaEditada.setNombreRepresentante(nombreRepresentante);
                empresaEditada.setCiudad(ciudad);
                empresaEditada.setTelefono(telefono);
                empresaEditada.setDireccion(direccion);
                empresaEditada.setCodigoPostal(codigoPostal);
                empresaEditada.setRfc(rfc);
                empresaEditada.setPaginaWeb(pagWeb);
                //aqui
                empresaEditada.setEstatus(true);
                editarEmpresa(empresaEditada);
            }else{
                Empresa empresaNueva = new Empresa();
                empresaNueva.setNombreEmpresa(nombreEmpresa);
                empresaNueva.setNombreComercial(nombreComercial);
                empresaNueva.setNombreRepresentante(nombreRepresentante);
                empresaNueva.setCiudad(ciudad);
                empresaNueva.setTelefono(telefono);
                empresaNueva.setDireccion(direccion);
                empresaNueva.setCodigoPostal(codigoPostal);
                empresaNueva.setRfc(rfc);
                empresaNueva.setEstatus(true);
                empresaNueva.setPaginaWeb(pagWeb);
                registrarEmpresa(empresaNueva);
            }
        }
    }

    
    public void editarEmpresa(Empresa empresaEditada){
        Mensaje mensaje = EmpresaDAO.editarEmpresa(empresaEditada);
        if(!mensaje.getError()){
            Utilidades.mostrarAlertaSimple("Empresa editada", mensaje.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        }else{
            Utilidades.mostrarAlertaSimple("Error al editar", mensaje.getMensaje(), Alert.AlertType.ERROR);
        }   
    }
    
    public void registrarEmpresa(Empresa empresaRegistrada){
        Mensaje mensaje = EmpresaDAO.registrarEmpresa(empresaRegistrada);
        if(!mensaje.getError()){
            Utilidades.mostrarAlertaSimple("Empresa registrada", mensaje.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", mensaje.getMensaje(), Alert.AlertType.ERROR);
        }

    }
    
    public void cerrarPantalla(){
        Stage stage = (Stage)tfNombreEmpresa.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarPantalla();
    }

    @FXML
    private void btnSeleccionar(ActionEvent event) {
        logo = dialogoMostrarSeleccion();
        if(logo!=null){
            mostrarImagenSeleccionada(logo);
        }
    }

    @FXML
    private void btnSubir(ActionEvent event) {
        if(logo !=null){
            Mensaje mensaje = EmpresaDAO.subirLogoEmpresa(logo, idEmpresaEdit);
            if(!mensaje.getError()){
                Utilidades.mostrarAlertaSimple("Logo guardado", mensaje.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al guardar", mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    }
    
}
