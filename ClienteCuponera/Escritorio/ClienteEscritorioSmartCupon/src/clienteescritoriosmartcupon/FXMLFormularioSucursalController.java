package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.SucursalDAO;
import clienteescritoriosmartcupon.modelo.pojo.Empresa;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Sucursal;
import clienteescritoriosmartcupon.utils.Utilidades;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class FXMLFormularioSucursalController implements Initializable {
    private Sucursal sucursal;
    private boolean editable = true;
    private int idEmpresa;
    private int idSucursal;
    private ObservableList<Empresa> empresas;

    @FXML
    private Label lbEmpresa;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfCiudad;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfLatitud;
    @FXML
    private TextField tfLongitud;
    @FXML
    private TextField tfNombreEncargado;
    @FXML
    private ComboBox<Empresa> cbEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionEmpresas();
        configurarSeleccion();
    }    

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarPantalla();
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        //TODOvalidaciones
        if (editable) {
            Sucursal sucursalEditado = new Sucursal();
            sucursalEditado.setIdSucursal(this.sucursal.getIdSucursal());
            sucursalEditado.setCiudad(tfCiudad.getText());
            sucursalEditado.setCodigoPostal(Integer.parseInt(tfCodigoPostal.getText()));
            sucursalEditado.setColonia(tfColonia.getText());
            sucursalEditado.setDireccion(tfDireccion.getText());
            sucursalEditado.setLatitud(Float.parseFloat(tfLatitud.getText()));
            sucursalEditado.setLongitud(Float.parseFloat(tfLongitud.getText()));
            sucursalEditado.setNombreSucursal(tfNombre.getText());
            sucursalEditado.setNombreEncargado(tfNombreEncargado.getText());
            sucursalEditado.setTelefono(tfTelefono.getText());
            Empresa Seleccion = cbEmpresa.getValue();
            sucursalEditado.setIdEmpresa(Seleccion.getIdEmpresa());
            //sucursalEditado.setIdEmpresa(idEmpresa);
            editar(sucursalEditado);
            
        }else{
            Sucursal sucursalNuevo = new Sucursal();
            sucursalNuevo.setCiudad(tfCiudad.getText());
            sucursalNuevo.setCodigoPostal(Integer.parseInt(tfCodigoPostal.getText()));
            sucursalNuevo.setColonia(tfColonia.getText());
            sucursalNuevo.setDireccion(tfDireccion.getText());
            sucursalNuevo.setLatitud(Float.parseFloat(tfLatitud.getText()));
            sucursalNuevo.setLongitud(Float.parseFloat(tfLongitud.getText()));
            sucursalNuevo.setNombreSucursal(tfNombre.getText());
            sucursalNuevo.setNombreEncargado(tfNombreEncargado.getText());
            sucursalNuevo.setTelefono(tfTelefono.getText());
            Empresa Seleccion = cbEmpresa.getValue();
            sucursalNuevo.setIdEmpresa(Seleccion.getIdEmpresa());
            //sucursalNuevo.setIdEmpresa(idEmpresa);
            registrar(sucursalNuevo);
        }
        
        
    }
    
    private void registrar(Sucursal sucursalNuevo){
       Mensaje msj = SucursalDAO.registrarSucursal(sucursalNuevo);
       if(!msj.getError()){
           Utilidades.mostrarAlertaSimple("Domicilio guardado", msj.getMensaje(), Alert.AlertType.INFORMATION);
           cerrarPantalla();
       }else{
           Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
       }
    }
    private void editar(Sucursal sucursalEditado){
        
        Mensaje msj = SucursalDAO.editarSucursal(sucursalEditado);
       if(!msj.getError()){
           Utilidades.mostrarAlertaSimple("Domicilio guardado", msj.getMensaje(), Alert.AlertType.INFORMATION);
           cerrarPantalla();
       }else{
           Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
       }
    }/*
    private void editarEmpresa(Sucursal sucursalEditado){
        
        Mensaje msj = SucursalDAO.editarEmpresaSucursal(sucursalEditado);
       if(!msj.getError()){
           Utilidades.mostrarAlertaSimple("Domicilio guardado", msj.getMensaje(), Alert.AlertType.INFORMATION);
           cerrarPantalla();
       }else{
           Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
       }
    }*/
    
    private void cerrarPantalla(){
        Stage stage = (Stage)tfCiudad.getScene().getWindow();
        stage.close();
    }
    
    public void inicializarFormulario(Sucursal sucursal){
        this.sucursal = sucursal;
        //this.idEmpresa = idEmpresa;
        //this.idSucursal = sucursal.getIdSucursal();
        if(sucursal == null){
            this.editable = false;
        }else{
            tfCiudad.setText(sucursal.getCiudad());
            String cp = Integer.toString(sucursal.getCodigoPostal());
            tfCodigoPostal.setText(cp);
            tfColonia.setText(sucursal.getColonia());
            tfDireccion.setText(sucursal.getDireccion());
            String lat = Float.toString(sucursal.getLatitud());
            tfLatitud.setText(lat);
            String lon = Float.toString(sucursal.getLongitud());
            tfLongitud.setText(lon);
            tfNombre.setText(sucursal.getNombreSucursal());
            tfNombreEncargado.setText(sucursal.getNombreEncargado());
            tfTelefono.setText(sucursal.getTelefono());
            Empresa empresaSeleccionada = empresas.stream()
                    .filter(emp -> emp.getIdEmpresa()== sucursal.getIdEmpresa())
                    .findFirst()
                    .orElse(null);
            cbEmpresa.setValue(empresaSeleccionada);
        }
    }
    private void cargarInformacionEmpresas(){
        
        empresas = FXCollections.observableArrayList();
        List<Empresa> info = SucursalDAO.buscarEmpresa();
        empresas.addAll(info);
        cbEmpresa.setItems(empresas);
            //System.out.println(municipios);
        
        
        cbEmpresa.setConverter(new StringConverter<Empresa>() {
            @Override
            public String toString(Empresa empresas) {
                return empresas.getNombreComercial();
            }

            @Override
            public Empresa fromString(String string) {
                return null;
            }
        });
        
        
    }
    
    
    private void configurarSeleccion(){
        //System.out.println("si se llama al metodo");
        cbEmpresa.valueProperty().addListener(new ChangeListener<Empresa>(){
            @Override
            public void changed(ObservableValue<? extends Empresa> observable, Empresa oldValue, Empresa newValue){
                if (newValue != null) {
                    idEmpresa = newValue.getIdEmpresa();
                }
            }
        });
    }
    
   
}

