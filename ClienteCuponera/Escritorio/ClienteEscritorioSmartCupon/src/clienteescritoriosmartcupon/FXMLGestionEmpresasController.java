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
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Richard
 */
public class FXMLGestionEmpresasController implements Initializable {

    private ObservableList<Empresa> empresasList;
    
    private FilteredList<Empresa> empresasFiltro;
    
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colNombreComercial;
    @FXML
    private TableColumn<?, ?> colRepreLegal;
    @FXML
    private TableColumn<?, ?> colDireccion;
    @FXML
    private TableColumn<?, ?> colCodigoPostal;
    @FXML
    private TableColumn<?, ?> colCiudad;
    @FXML
    private TableColumn<?, ?> colTelefono;
    @FXML
    private TableColumn<?, ?> colPagWeb;
    @FXML
    private TableColumn<?, ?> colRFC;
    @FXML
    private TableColumn<Empresa, Boolean> colEstatus;
    @FXML
    private TableView<Empresa> tvEmpresas;
    @FXML
    private TextField tfBuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empresasList = FXCollections.observableArrayList();
        configurarColumnasTabla();
    }    
    
    public void inicializarInformacion(){
        consultarInformacionEmpresas();
        configurarFiltros();

    }
    
    public void consultarInformacionEmpresas(){
        empresasList.clear();
        HashMap<String, Object> respuesta = EmpresaDAO.obtenerEmpresas();
        if (!(boolean)respuesta.get("error")){
            List<Empresa> empresas = (List<Empresa>)respuesta.get("empresas");
            empresasList.addAll(empresas);
            tvEmpresas.setItems(empresasList);
        }else{
            Utilidades.mostrarAlertaSimple("Error", (String)respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    public void configurarColumnasTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreEmpresa"));
        colNombreComercial.setCellValueFactory(new PropertyValueFactory("nombreComercial"));
        colRepreLegal.setCellValueFactory(new PropertyValueFactory("nombreRepresentante"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        colCiudad.setCellValueFactory(new PropertyValueFactory("ciudad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colPagWeb.setCellValueFactory(new PropertyValueFactory("paginaWeb"));
        colRFC.setCellValueFactory(new PropertyValueFactory("rfc"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colEstatus.setCellFactory(column -> new TableCell<Empresa, Boolean>() {
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
    
    public void irFormulario(Empresa empresa)throws ParseException{
        try{
            Stage stage = new Stage();
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("FXMLFormularioEmpresa.fxml"));
            Parent vista = loadVista.load();
            FXMLFormularioEmpresaController controller = loadVista.getController();
            controller.inicializarFormulario(empresa);
            
            Scene scene = new Scene(vista);
            stage.setScene(scene);
            stage.setTitle("Formulario Empresa");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
     public void lanzarAletaEliminar(int idEmpresa){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar Empresa");
        alert.setHeaderText("¿Desea eliminar esta empresa?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            eliminarEmpresa(idEmpresa);
        }
    }
    public void eliminarEmpresa(int idEmpresa){
        Mensaje mensaje = EmpresaDAO.eliminarEmpresa(idEmpresa);
        if(!mensaje.getError()){
            Utilidades.mostrarAlertaSimple("Borrar empresa", "Se ha eliminado la empresa con exito", Alert.AlertType.INFORMATION);
        }else{
            Utilidades.mostrarAlertaSimple("Error al eliminar", mensaje.getMensaje(), Alert.AlertType.ERROR);

        }
    }
    
    

    @FXML
    private void btnIrFormularioRegistro(ActionEvent event)throws ParseException{
        irFormulario(null);
        consultarInformacionEmpresas();
    }

    @FXML
    private void btnIrFormularioEdicion(ActionEvent event) throws ParseException {
        int posicionSeleccionada = tvEmpresas.getSelectionModel().getSelectedIndex();
        if(posicionSeleccionada!= -1){
            Empresa empresa = empresasList.get(posicionSeleccionada);
            System.out.println(empresa.getEstatus());
            irFormulario(empresa);
            consultarInformacionEmpresas();
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de empresa", "Para poder editar por favor seleccione una empresa de la tabla", Alert.AlertType.WARNING);
        }
    }
    

    @FXML
    private void btnEliminarEmpresa(ActionEvent event) {
        int posicionSeleccionada = tvEmpresas.getSelectionModel().getSelectedIndex();
        if(posicionSeleccionada!= -1){
            Empresa empresa = empresasList.get(posicionSeleccionada);
            lanzarAletaEliminar(empresa.getIdEmpresa());
            consultarInformacionEmpresas();
        }else{
            Utilidades.mostrarAlertaSimple("Seleccion de empresa", "Para poder editar por favor seleccione una empresa de la tabla", Alert.AlertType.WARNING);
        }
    }
    
    public void configurarFiltros(){
        if(empresasFiltro == null){
            empresasFiltro = new FilteredList<>(empresasList,p->true);
            SortedList<Empresa> sortedData = new SortedList<>(empresasFiltro);
            sortedData.comparatorProperty().bind(tvEmpresas.comparatorProperty());
            tvEmpresas.setItems(sortedData);
        }
        
        tfBuscar.textProperty().addListener((obersavable,oldValue, newValue)->{
            empresasFiltro.setPredicate(empresa ->{
                if(newValue == null || newValue.trim().isEmpty()){
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();
                return empresa.getNombreEmpresa().toLowerCase().contains(lowerCaseFilter) || empresa.getNombreComercial().toLowerCase().contains(lowerCaseFilter)
                       || empresa.getRfc().toLowerCase().contains(lowerCaseFilter) || empresa.getNombreRepresentante().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }
}
