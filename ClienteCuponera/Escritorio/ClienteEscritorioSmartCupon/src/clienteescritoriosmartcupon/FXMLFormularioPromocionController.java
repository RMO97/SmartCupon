package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.PromocionDAO;
import clienteescritoriosmartcupon.modelo.DAO.SucursalDAO;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Promocion;
import clienteescritoriosmartcupon.modelo.pojo.Sucursal;
import clienteescritoriosmartcupon.utils.Utilidades;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;

public class FXMLFormularioPromocionController implements Initializable {
    
    private Promocion promocion;
    private Sucursal ObtenerSucursal;
    private boolean Edit = true;
    private Integer estatus;
    private Boolean est;
    private Integer tipo;
    private boolean tip;
    private File fotografia;
    private ObservableList<Sucursal> sucursales;
    private Integer idPromocion;
    private Integer idSucursal;

    @FXML
    private ImageView ivFoto;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDescripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaExpiracion;
    @FXML
    private RadioButton rbDescuento;
    @FXML
    private RadioButton rbCostoRebajado;
    @FXML
    private TextField tfValor;
    @FXML
    private Label lbPorcentaje;
    @FXML
    private TextField tfCategoria;
    @FXML
    private TextField tfNumeroMaximos;
    @FXML
    private TextField tfCodigoPromocion;
    @FXML
    private RadioButton rbActivo;
    @FXML
    private RadioButton rbInactivo;
    @FXML
    private TextField tfRestricciones;
    @FXML
    private ToggleGroup tgTipo;
    @FXML
    private ToggleGroup tgEstatus;
    @FXML
    private ComboBox<Sucursal> cbSucursal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionSucursales();
        configurarSeleccion();
        rbInactivo.setVisible(false);
        tgEstatus.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(rbActivo.isSelected()){
                    estatus = 1;
                    est=true;
                }else{
                    estatus = 0;
                    est=false;
                }
            }    
        });
        tgTipo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(rbDescuento.isSelected()){
                    tipo = 1;
                    tip = true;
                    lbPorcentaje.setVisible(true);
                }else{
                    tipo = 0;
                    tip = false;
                    lbPorcentaje.setVisible(false);
                }
            }    
        });
    }    

    @FXML
    private void btnAceptar(ActionEvent event) {
        
        boolean isValido = true;
        if ("".equals(estatus)) {
            isValido = false;
        }
        if (Edit) {
            Promocion promocionEditada = new Promocion();
            Sucursal sucursalEditada = new Sucursal();
            promocionEditada.setIdPromocion(this.promocion.getIdPromocion());
            promocionEditada.setCategoria(tfCategoria.getText());
            promocionEditada.setCodigoPromocion(tfCodigoPromocion.getText());
            promocionEditada.setDescripcion(tfDescripcion.getText());
            promocionEditada.setEstatus(est);
            promocionEditada.setFechaDeExpiracionPromocion(dpFechaExpiracion.getValue().toString());
            promocionEditada.setFechaDeInicioPromocion(dpFechaInicio.getValue().toString());
            promocionEditada.setTipoPromocion(tip);
            promocionEditada.setNombrePromocion(tfNombre.getText());
            promocionEditada.setNumeroCuponesMaximo(Integer.parseInt(tfNumeroMaximos.getText()));
            promocionEditada.setRestriccion(tfRestricciones.getText());
            promocionEditada.setValor(Float.parseFloat(tfValor.getText()));
            Sucursal Seleccion = cbSucursal.getValue();
            promocionEditada.setIdEmpresa(Seleccion.getIdEmpresa());
            sucursalEditada.setIdPromocion(idPromocion);
            sucursalEditada.setIdSucursal(idSucursal);
            editar(promocionEditada);
            editarSucursal(sucursalEditada);
        }else{
            Promocion promocionNueva = new Promocion();
            Sucursal sucursalEditada = new Sucursal();
            promocionNueva.setCategoria(tfCategoria.getText());
            promocionNueva.setCodigoPromocion(tfCodigoPromocion.getText());
            promocionNueva.setDescripcion(tfDescripcion.getText());
            promocionNueva.setEstatus(est);
            promocionNueva.setFechaDeExpiracionPromocion(dpFechaExpiracion.getValue().toString());
            promocionNueva.setFechaDeInicioPromocion(dpFechaInicio.getValue().toString());
            promocionNueva.setTipoPromocion(tip);
            promocionNueva.setNombrePromocion(tfNombre.getText());
            promocionNueva.setNumeroCuponesMaximo(Integer.parseInt(tfNumeroMaximos.getText()));
            promocionNueva.setRestriccion(tfRestricciones.getText());
            promocionNueva.setValor(Float.parseFloat(tfValor.getText()));
            Sucursal Seleccion = cbSucursal.getValue();
            //
            sucursalEditada.setIdPromocion(idPromocion);
            sucursalEditada.setIdSucursal(sucursalEditada.getIdSucursal());
            promocionNueva.setIdEmpresa(Seleccion.getIdEmpresa());
            //
            registrar(promocionNueva);
            editarSucursal(sucursalEditada);
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarPantalla();
    }

    @FXML
    private void btnSeleccionarFoto(ActionEvent event) {
        fotografia = mostrarDialogoSeleccion();
        if(fotografia !=null){
            mostrarImagenSeleccionada(fotografia);
        }
    }

    @FXML
    private void btnSubirFoto(ActionEvent event) {
        if(fotografia != null){
            Mensaje msj = PromocionDAO.subirFotografiaPromocion(fotografia, promocion.getIdPromocion());
            if(!msj.getError()==true){
                Utilidades.mostrarAlertaSimple("fotografia guardada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al guardar la fotografia", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    }
    
    private File mostrarDialogoSeleccion(){
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");
        //configuracion para restringir tipos de archivos
        FileChooser.ExtensionFilter filtroArchivos = new FileChooser.ExtensionFilter("Archivos PNG (*.png, *.jpg)", "*.png", "*.jpg");
        dialogoSeleccionImg.getExtensionFilters().add(filtroArchivos);
        //obtener imagen
        Stage escenario = (Stage) tfNombre.getScene().getWindow();
        return dialogoSeleccionImg.showOpenDialog(escenario);
        
    }
    
    private void mostrarImagenSeleccionada(File foto){
        try {
            BufferedImage buffer = ImageIO.read(foto);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivFoto.setImage(image);
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error al cargar", "Error al intentar visualizar la imagen seleccionada", Alert.AlertType.ERROR);
        }
    }
    
    private void obtenerFoto(int idPromocion){
        Promocion promocionFoto = PromocionDAO.obtenerFotografia(idPromocion);
        if(promocionFoto != null && promocionFoto.getFotografiaBase64()!=null&&promocionFoto.getFotografiaBase64().length()>0){
            byte[] decodeImg = Base64.getDecoder().decode(promocionFoto.getFotografiaBase64().replaceAll("\\n", ""));
            Image image = new Image(new ByteArrayInputStream(decodeImg));
            ivFoto.setImage(image);
        }
    }
    
    private void registrar(Promocion promocionNueva){
        Mensaje msj = PromocionDAO.registrarPromocion(promocionNueva);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Promocion guardado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void editar(Promocion promocionEditada){
        
        Mensaje msj = PromocionDAO.editarPromocion(promocionEditada);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Promocion guardado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        }else{
            Utilidades.mostrarAlertaSimple("Error al editar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    private void editarSucursal(Sucursal sucursalEditada){

        Mensaje msj = SucursalDAO.editarPromocionSucursal(sucursalEditada);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Promocion guardado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        }else{
            Utilidades.mostrarAlertaSimple("Error al editar sucursal", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    public void inicializarFormulario(Promocion promocion){
        this.promocion = promocion;
        this.idPromocion = promocion.getIdPromocion();
        if(promocion == null){
            this.Edit = false;
        }else{
            rbInactivo.setVisible(true);
            tfCategoria.setText(promocion.getCategoria());
            tfCodigoPromocion.setText(promocion.getCodigoPromocion());
            tfDescripcion.setText(promocion.getDescripcion());
            tfNombre.setText(promocion.getNombrePromocion());
            String numMax = Integer.toString(promocion.getNumeroCuponesMaximo());
            tfNumeroMaximos.setText(numMax);
            tfRestricciones.setText(promocion.getRestriccion());
            String val = Float.toString(promocion.getValor());
            tfValor.setText(val);
            if(promocion.getEstatus() == true){
                rbActivo.setSelected(true);
            }else if(promocion.getEstatus() == false){
                rbInactivo.setSelected(true);
            }
            
            if(promocion.getTipoPromocion() == true){
                rbDescuento.setSelected(true);
            }else if(promocion.getTipoPromocion()== false){
                rbCostoRebajado.setSelected(true);
            }

            Sucursal empresaSeleccionada = sucursales.stream()
                    .filter(emp -> emp.getIdPromocion()== promocion.getIdPromocion())
                    .findFirst()
                    .orElse(null);
            cbSucursal.setValue(empresaSeleccionada);
            
            String patter = "yyyy-MM-dd";
            DateTimeFormatter date = DateTimeFormatter.ofPattern(patter);
            dpFechaExpiracion.setValue(LocalDate.parse(promocion.getFechaDeExpiracionPromocion(),date));
            dpFechaInicio.setValue(LocalDate.parse(promocion.getFechaDeInicioPromocion(),date));
            obtenerFoto(promocion.getIdPromocion());
        }
    }
    
    private void cerrarPantalla(){
        Stage stage = (Stage)tfCategoria.getScene().getWindow();
        stage.close();
    }
   
    private void cargarInformacionSucursales(){
        
        sucursales = FXCollections.observableArrayList();
        List<Sucursal> info = PromocionDAO.buscarSucursalPromocion();
        /*
        if (idEmpresa!=null) {
            info = PromocionDAO.buscarSucursalPromocion();
            
        } else {
            info = PromocionDAO.buscarPorNombre(idEmpresa);
        }*/
        
        sucursales.addAll(info);
        cbSucursal.setItems(sucursales);
            //System.out.println(municipios);
        
        
        cbSucursal.setConverter(new StringConverter<Sucursal>() {
            @Override
            public String toString(Sucursal sucursales) {
                return sucursales.getNombreSucursal();
            }

            @Override
            public Sucursal fromString(String string) {
                return null;
            }
        });
        
        
    }
    
    
    private void configurarSeleccion(){
        //System.out.println("si se llama al metodo");
        cbSucursal.valueProperty().addListener(new ChangeListener<Sucursal>(){
            @Override
            public void changed(ObservableValue<? extends Sucursal> observable, Sucursal oldValue, Sucursal newValue){
                if (newValue != null) {
                    idSucursal = newValue.getIdSucursal();
                }
            }
        });
    }
    
}

