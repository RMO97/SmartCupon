package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.PromocionDAO;
import static clienteescritoriosmartcupon.modelo.DAO.SucursalDAO.eliminarSucursal;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Promocion;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLModuloPromocionController implements Initializable {
    private ObservableList<Promocion> promocionesEmpresa;
    private int idEmpresa;

    @FXML
    private TableView<Promocion> tvPromocion;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colFechaExpiracion;
    @FXML
    private TableColumn colRestricciones;
    @FXML
    private TableColumn colCategoria;
    @FXML
    private TableColumn colCuponesMaximos;
    @FXML
    private TableColumn colCodigoPromocion;
    @FXML
    private TableColumn colEstatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        promocionesEmpresa = FXCollections.observableArrayList();
        configurarColumnasTabla();
    }

    public void inicializarInformacion(int idEmpresa){
        this.idEmpresa = idEmpresa;
        System.out.println("el id empresa es: "+this.idEmpresa);
        System.out.println("el id empresa es: "+idEmpresa);

        consultarInformacionPromocion();
    }    

    @FXML
    private void btnRegistrarPromocion(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void btnEliminarPromocion(ActionEvent event) {
        int posicionSeleccionada = tvPromocion.getSelectionModel().getSelectedIndex();
        if(posicionSeleccionada!= -1){
            Promocion promocion = promocionesEmpresa.get(posicionSeleccionada);
            lanzarAlertaEliminar(promocion.getIdPromocion());
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de promocion", "Para poder eliminar favor de seleccionar una promocion de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnEditarPromocion(ActionEvent event) {
        int posicionSeleccionada = tvPromocion.getSelectionModel().getSelectedIndex();
        if(posicionSeleccionada != -1){
            Promocion promocion = promocionesEmpresa.get(posicionSeleccionada);
            irFormulario(promocion);
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de promocion", "Para poder modificar favor de seleccionar una promocion de la tabla", Alert.AlertType.WARNING);
        }
    }
    
    public void consultarInformacionPromocion(){
        if (idEmpresa!=0) {
            HashMap<String, Object> respuesta = PromocionDAO.obtenerPromocionesEmpresa(idEmpresa);
            if(!(boolean)respuesta.get("error")){
                List<Promocion> promociones = (List<Promocion>) respuesta.get("promociones");
                promocionesEmpresa.addAll(promociones);
                tvPromocion.setItems(promocionesEmpresa);

            }else{
                Utilidades.mostrarAlertaSimple("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
            }
        } else {
            HashMap<String, Object> respuesta = PromocionDAO.obtenerPromociones();
            if(!(boolean)respuesta.get("error")){
                List<Promocion> promociones = (List<Promocion>) respuesta.get("promociones");
                promocionesEmpresa.addAll(promociones);
                tvPromocion.setItems(promocionesEmpresa);

            }else{
                Utilidades.mostrarAlertaSimple("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
            }
        }
        
    }
    
    public void configurarColumnasTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombrePromocion"));
        colCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        colCodigoPromocion.setCellValueFactory(new PropertyValueFactory("codigoPromocion"));
        colCuponesMaximos.setCellValueFactory(new PropertyValueFactory("numeroCuponesMaximo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colFechaExpiracion.setCellValueFactory(new PropertyValueFactory("fechaDeExpiracionPromocion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaDeInicioPromocion"));
        colRestricciones.setCellValueFactory(new PropertyValueFactory("restriccion"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colEstatus.setCellFactory(column -> new TableCell<Promocion, Boolean>() {
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Activo" : "Inactivo");
                }
            }
        });
    }
    
    public void irFormulario(Promocion promocion){
        try {
            Stage stage = new Stage();
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("FXMLFormularioPromocion.fxml"));
            Parent vista = loadVista.load();
            FXMLFormularioPromocionController controladorEditar = loadVista.getController();
            System.out.println("el id empresa es: "+idEmpresa);

            controladorEditar.inicializarFormulario(promocion,idEmpresa);
            
            Scene scene = new Scene(vista);
            stage.setScene(scene);
            stage.setTitle("Formulario promocion");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void lanzarAlertaEliminar(int idPromocion) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Borrar promoción");
        alert.setHeaderText("¿Desea eliminar esta promoción o modificar el estado?");

        ButtonType eliminarButton = new ButtonType("Eliminar", ButtonBar.ButtonData.OK_DONE);
        ButtonType modificarButton = new ButtonType("Modificar Estado", ButtonBar.ButtonData.NEXT_FORWARD);
        ButtonType cancelarButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(eliminarButton, modificarButton, cancelarButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == eliminarButton) {
                eliminarSucursal(idPromocion);
            } else if (result.get() == modificarButton) {
                int posicionSeleccionada = tvPromocion.getSelectionModel().getSelectedIndex();
                if (posicionSeleccionada != -1) {
                    Promocion promocion = promocionesEmpresa.get(idPromocion);
                    irFormulario(promocion);
                }
            }
        }
    }
    
    public void eliminarSucursaL(int idPromocion){
        Mensaje mensaje = PromocionDAO.eliminarPromocion(idPromocion);
        if(!mensaje.getError()){
            Utilidades.mostrarAlertaSimple("Borrar promocion", "Se ha eliminado la promocion con exito", Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error al eliminar", mensaje.getMensaje(), Alert.AlertType.ERROR);

        }
    }
}
