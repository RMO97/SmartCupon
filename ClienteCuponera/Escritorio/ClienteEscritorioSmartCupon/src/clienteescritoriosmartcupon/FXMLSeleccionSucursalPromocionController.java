package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.PromocionDAO;
import clienteescritoriosmartcupon.modelo.DAO.SucursalDAO;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Promocion;
import clienteescritoriosmartcupon.modelo.pojo.Sucursal;
import clienteescritoriosmartcupon.utils.Utilidades;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLSeleccionSucursalPromocionController implements Initializable {
    private Integer idEmpresa;
    private Integer idPromocion;
    private Integer idSucursal;
    private ObservableList<Sucursal> sucursalesEmpresa;

    //private TableView<Sucursal> sucursalesTable;
    @FXML
    private TableColumn tcSucursal;
    @FXML
    private Label lbSeleccionsucursal;
    @FXML
    private TableView<Sucursal> tvSucursales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sucursalesEmpresa = FXCollections.observableArrayList();
        configurarColumnasTabla();
    }
    
    public void inicializarInformacion(int idEmpresa, Integer idPromocion){
        this.idEmpresa = idEmpresa;
        consultarInformacionSucursal();
        System.out.println("el idPromocion es: "+idPromocion);
        if (idPromocion!=null) {
            this.idPromocion = idPromocion;
        } else {
            obtenerUltimaPromocion();
        }
    }
    
    public void consultarInformacionSucursal(){
        if (idEmpresa!=0) {
            HashMap<String, Object> respuesta = SucursalDAO.obtenerPorEmpresa(idEmpresa);
            if(!(boolean)respuesta.get("error")){
                List<Sucursal> sucursales = (List<Sucursal>) respuesta.get("sucursales");
                sucursalesEmpresa.addAll(sucursales);
                tvSucursales.setItems(sucursalesEmpresa);
                //System.out.println("idSucursal metodo clase: "+idSucursal);

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
        tcSucursal.setCellValueFactory(new PropertyValueFactory("nombreSucursal"));
        
    }

    private void obtenerUltimaPromocion() {
        Promocion respuesta = PromocionDAO.obtenerUltimaPromocion();
            if(respuesta!=null){
                System.out.println("entra al metodo Ultima promocion");
                this.idPromocion = respuesta.getIdPromocion();
            }else{
                Utilidades.mostrarAlertaSimple("Error","no se puedo obtener el valor" , Alert.AlertType.ERROR);
            }
    }

    @FXML
    private void btnAgregarSucursal(ActionEvent event) {
        int posicionSeleccionada = tvSucursales.getSelectionModel().getSelectedIndex();
        if(posicionSeleccionada != -1){
            Sucursal sucursal = sucursalesEmpresa.get(posicionSeleccionada);
            sucursal.setIdPromocion(idPromocion);
            
            registrarSucursaL(sucursal);
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de sucursal", "Para poder modificar debes seleccionar una sucursal de la tabla", Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void btnEditarSucursal(ActionEvent event) {
        int posicionSeleccionada = tvSucursales.getSelectionModel().getSelectedIndex();
        if(posicionSeleccionada != -1){
            Sucursal sucursal = sucursalesEmpresa.get(posicionSeleccionada);
            //sucursal.setIdPromocion(idPromocion);
            System.out.println("idSucursal: "+sucursal.getIdSucursal());
            System.out.println("idSucursal clase: "+idSucursal);
            sucursal.setIdSucursalNuevo(sucursal.getIdSucursal());
            System.out.println("idSucursalNuevo es: "+sucursal.getIdSucursalNuevo());
            
            editarSucursal(sucursal);
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de sucursal", "Para poder modificar debes seleccionar una sucursal de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEliminarSucursal(ActionEvent event) {
        int posicionSeleccionada = tvSucursales.getSelectionModel().getSelectedIndex();
        if(posicionSeleccionada!= -1){
            Sucursal sucursal = sucursalesEmpresa.get(posicionSeleccionada);
            //idSucursal = sucursal.getIdSucursal();
            
            //sucursal.setIdSucursal(idSucursal);
            sucursal.setIdPromocion(idPromocion);
            System.out.println("el id sucursal es: "+idSucursal);
            System.out.println("el id promocion es: "+idPromocion);
                        
            lanzarAletaEliminar(sucursal);
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de sucursal", "Para poder eliminar favor de seleccionar una sucursal", Alert.AlertType.WARNING);
        }
        
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        cerrarPantalla();
    }
    
    private void cerrarPantalla(){
        Stage stage = (Stage)lbSeleccionsucursal.getScene().getWindow();
        stage.close();
    }
    
    public void lanzarAletaEliminar(Sucursal sucursal){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar sucursal");
        alert.setHeaderText("¿Desea eliminar esta sucursal de la promocion?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            eliminarSucursal(sucursal);
        }
    }
    public void eliminarSucursal(Sucursal sucursal){
        
        Mensaje mensaje = PromocionDAO.eliminarSucursalPromocion(sucursal);
        if(!mensaje.getError()){
            Utilidades.mostrarAlertaSimple("Borrar sucursal", "Se ha eliminado la sucursal de la promocion con exito", Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error al eliminar", mensaje.getMensaje(), Alert.AlertType.ERROR);

        }
    }
    public void editarSucursal(Sucursal sucursal){
        
        Mensaje mensaje = PromocionDAO.editarSucursalPromocion(sucursal);
        if(!mensaje.getError()){
            Utilidades.mostrarAlertaSimple("editar sucursal", "Se ha editado la sucursal de la promocion con exito", Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error al editar", mensaje.getMensaje(), Alert.AlertType.ERROR);

        }
    }
    public void registrarSucursaL(Sucursal sucursal){
        Mensaje mensaje = PromocionDAO.registrarSucursalPromocion(sucursal);
        if(!mensaje.getError()){
            Utilidades.mostrarAlertaSimple("registrar sucursal", "Se ha agregado la sucursal de la promocion con exito", Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", mensaje.getMensaje(), Alert.AlertType.ERROR);

        }
    }

}