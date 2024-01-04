package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.SucursalDAO;
import clienteescritoriosmartcupon.modelo.pojo.Empresa;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Promocion;
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
    private Integer idEmpresa;
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
        boolean isValido = true;
        String Ciudad = tfCiudad.getText();
        String CodigoPostal = tfCodigoPostal.getText();
        String Colonia = tfColonia.getText();
        String Direccion = tfDireccion.getText();
        String Latitud = tfLatitud.getText();
        String Longitud = tfLongitud.getText();
        String NombreSucursal = tfNombre.getText();
        String NombreEncargado = tfNombreEncargado.getText();
        String Telefono = tfTelefono.getText();
        
        if(Ciudad.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la ciudad de la Sucursal");
            alert.setContentText("La ciudad de la sucursal no puede ir vacia");
            alert.showAndWait();
        }
        if(CodigoPostal.isEmpty()|| !CodigoPostal.matches("\\d{5}$")){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el codigo postal de la sucursal");
            alert.setContentText("El codigo postal de la sucursal no puede ir vacio");
            if(CodigoPostal.isEmpty()){
                alert.setContentText("El codigo postal de la sucursal no puede ir vacio");
            }else if(CodigoPostal.length() != 13) {
                alert.setContentText("El codigo postal solo puede contener numeros y letras sin caracteres especiales");
            }
            alert.showAndWait();
        }
        if(Colonia.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la colonia de la sucursal");
            alert.setContentText("La colonia de la sucursal no puede ir vacia");
            alert.showAndWait();
        }
        if(Direccion.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la direccion de la sucursal");
            alert.setContentText("La direccion de la sucursal no puede ir vacia");
            alert.showAndWait();
        }
        if(Latitud.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la latitud de la sucursal");
            alert.setContentText("La latitud de la sucursal no puede ir vacia");
            alert.showAndWait();
        }
        if(Longitud.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la longitud de la sucursal");
            alert.setContentText("La longitud de la sucursal no puede ir vacia");
            alert.showAndWait();
        }
        if(NombreSucursal.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre de la sucursal");
            alert.setContentText("El nombre de la sucursal no puede ir vacio");
            alert.showAndWait();
        }
        if(NombreEncargado.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre del encargado de la sucursal");
            alert.setContentText("El nombre del encargado de la sucursal no puede ir vacio");
            alert.showAndWait();
        }
        if(Telefono.isEmpty() || Telefono.length() != 10 || !Telefono.matches("\\d{10}$")){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el telefono de la sucursal");
            
            if(Telefono.isEmpty()){
                alert.setContentText("El telefono de la sucursal no puede ir vacio");
            }else if(Telefono.length() != 10) {
                alert.setContentText("El telefono solo puede contener al menos 10 caracteres.");
            }else{
                alert.setContentText("El codigo postal solo puede contener numeros y letras sin caracteres especiales");

            }
            alert.showAndWait();
        }
        if (cbEmpresa == null) {
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre de la empresa");
            alert.setContentText("El nombre de la empresa no puede ir vacio");
            alert.showAndWait();
        }
        
        if (isValido) {
            if (editable) {
                
            
                
            Sucursal sucursalEditado = new Sucursal();
            sucursalEditado.setIdSucursal(this.sucursal.getIdSucursal());
            sucursalEditado.setCiudad(Ciudad);
            sucursalEditado.setCodigoPostal(Integer.parseInt(CodigoPostal));
            sucursalEditado.setColonia(Colonia);
            sucursalEditado.setDireccion(Direccion);
            sucursalEditado.setLatitud(Float.parseFloat(Latitud));
            sucursalEditado.setLongitud(Float.parseFloat(Longitud));
            sucursalEditado.setNombreSucursal(NombreSucursal);
            sucursalEditado.setNombreEncargado(NombreEncargado);
            sucursalEditado.setTelefono(Telefono);
            if (idEmpresa!=null) {
                sucursalEditado.setIdEmpresa(idEmpresa);
                
            } else {
                Empresa Seleccion = cbEmpresa.getValue();
                sucursalEditado.setIdEmpresa(Seleccion.getIdEmpresa());
            }
            editar(sucursalEditado);
            
        }else{
            Sucursal sucursalNuevo = new Sucursal();
            sucursalNuevo.setCiudad(Ciudad);
            sucursalNuevo.setCodigoPostal(Integer.parseInt(CodigoPostal));
            sucursalNuevo.setColonia(Colonia);
            sucursalNuevo.setDireccion(Direccion);
            sucursalNuevo.setLatitud(Float.parseFloat(Latitud));
            sucursalNuevo.setLongitud(Float.parseFloat(Longitud));
            sucursalNuevo.setNombreSucursal(NombreSucursal);
            sucursalNuevo.setNombreEncargado(NombreEncargado);
            sucursalNuevo.setTelefono(Telefono);
            
            if (idEmpresa!=null) {
                sucursalNuevo.setIdEmpresa(idEmpresa);
                
            } else {
                Empresa Seleccion = cbEmpresa.getValue();
                sucursalNuevo.setIdEmpresa(Seleccion.getIdEmpresa());
            }
            registrar(sucursalNuevo);
        }
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
    }
    
    private void cerrarPantalla(){
        Stage stage = (Stage)tfCiudad.getScene().getWindow();
        stage.close();
    }
    
    public void inicializarFormulario(Sucursal sucursal, Integer idEmpresa){
        this.sucursal = sucursal;
        this.idEmpresa = idEmpresa;
        
        if (idEmpresa!=null && idEmpresa !=0) {
            cbEmpresa.setVisible(false);
            lbEmpresa.setVisible(false);
        }else if(idEmpresa==0){
            cbEmpresa.setVisible(true);
            lbEmpresa.setVisible(true);
        }
        
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

