package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.SucursalDAO;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Sucursal;
import clienteescritoriosmartcupon.utils.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLModuloSucursalController implements Initializable {
    private int idEmpresa;
    private ObservableList<Sucursal> sucursalesEmpresa;

    @FXML
    private TableView<Sucursal> tvSucursales;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colLongitud;
    @FXML
    private TableColumn colLatitud;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colCodigoPostal;
    @FXML
    private TableColumn colColonia;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colEncargado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sucursalesEmpresa = FXCollections.observableArrayList();
        configurarColumnasTabla();
        
    }
    
    public void inicializarInformacion(int idEmpresa){
        this.idEmpresa = idEmpresa;
        
        consultarInformacionSucursal();
    }

    @FXML
    private void btnEliminarSucursal(ActionEvent event) {
        int posicionSeleccionada = tvSucursales.getSelectionModel().getSelectedIndex();
        if(posicionSeleccionada!= -1){
            Sucursal sucursal = sucursalesEmpresa.get(posicionSeleccionada);
            lanzarAletaEliminar(sucursal.getIdSucursal());
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de sucursal", "Para poder eliminar favor de seleccionar una sucursal", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEditarsucursal(ActionEvent event) {
        int posicionSeleccionada = tvSucursales.getSelectionModel().getSelectedIndex();
        if(posicionSeleccionada != -1){
            Sucursal sucursal = sucursalesEmpresa.get(posicionSeleccionada);
            irFormulario(sucursal);
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de sucursal", "Para poder modificar debes seleccionar una sucursal de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnRegistrarSucursal(ActionEvent event) {
        irFormulario(null);
    }
    
    public void consultarInformacionSucursal(){
        if (idEmpresa!=0) {
            HashMap<String, Object> respuesta = SucursalDAO.obtenerPorEmpresa(idEmpresa);
            if(!(boolean)respuesta.get("error")){
                List<Sucursal> sucursales = (List<Sucursal>) respuesta.get("sucursales");
                sucursalesEmpresa.addAll(sucursales);
                tvSucursales.setItems(sucursalesEmpresa);

            }else{
                Utilidades.mostrarAlertaSimple("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
            }
            
        } else {
            HashMap<String, Object> respuesta = SucursalDAO.obtenerSucursales();
            if(!(boolean)respuesta.get("error")){
                List<Sucursal> sucursales = (List<Sucursal>) respuesta.get("sucursales");
                sucursalesEmpresa.addAll(sucursales);
                tvSucursales.setItems(sucursalesEmpresa);

            }else{
                Utilidades.mostrarAlertaSimple("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
            }
        }
        
    }
    
    public void configurarColumnasTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreSucursal"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        colColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        colEncargado.setCellValueFactory(new PropertyValueFactory("nombreEncargado"));
        colLatitud.setCellValueFactory(new PropertyValueFactory("latitud"));
        colLongitud.setCellValueFactory(new PropertyValueFactory("longitud"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
    }
    
    public void irFormulario(Sucursal sucursal){
        try {
            Stage stage = new Stage();
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("FXMLFormularioSucursal.fxml"));
            Parent vista = loadVista.load();
            FXMLFormularioSucursalController controladorEditar = loadVista.getController();
            controladorEditar.inicializarFormulario(sucursal,idEmpresa);
            
            Scene scene = new Scene(vista);
            stage.setScene(scene);
            stage.setTitle("Formulario sucursal");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void lanzarAletaEliminar(int idSucursal){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar sucursal");
        alert.setHeaderText("¿Desea eliminar esta sucursal?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            eliminarSucursaL(idSucursal);
        }
    }
    public void eliminarSucursaL(int idSucursal){
        Mensaje mensaje = SucursalDAO.eliminarSucursal(idSucursal);
        if(!mensaje.getError()){
            Utilidades.mostrarAlertaSimple("Borrar sucursal", "Se ha eliminado la sucursal con exito", Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error al eliminar", mensaje.getMensaje(), Alert.AlertType.ERROR);

        }
    }
}
