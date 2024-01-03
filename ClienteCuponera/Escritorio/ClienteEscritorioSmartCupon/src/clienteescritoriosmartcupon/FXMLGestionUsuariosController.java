package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.UsuarioDAO;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Usuario;
import clienteescritoriosmartcupon.utils.Utilidades;
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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLGestionUsuariosController implements Initializable {

    @FXML
    private TextField tfBuscar;
    @FXML
    private TableColumn<Usuario, String> colNombre, colApellidoP, colApellidoM, colCURP, colCorreo, colUsername, colPass;
    @FXML
    private TableColumn<Usuario, Integer> colRol;
    @FXML
    private TableView<Usuario> tvUsuarios;

    private ObservableList<Usuario> usuarios;
    private FilteredList<Usuario> listaUsuarioFiltro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarios = FXCollections.observableArrayList();
        configurarColumnasTabla();
        consultarInformacionUsuarios();
        configurarFiltro();
    }

    @FXML
    private void btnRegistrar(ActionEvent event) {
        irFormularioUsuario(null);
        consultarInformacionUsuarios();
    }

    @FXML
    private void btnEditar(ActionEvent event) {
        Usuario usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null){
        irFormularioUsuario(usuarioSeleccionado);
        consultarInformacionUsuarios();
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un usuario", "Debes de seleccionar un Usuario para Editar", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
        Usuario usuarioSeleccionado = tvUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null && confirmarEliminacion(usuarioSeleccionado.getNombre())) {
            eliminarUsuario(usuarioSeleccionado.getIdUsuario());
        }
    }

    private boolean confirmarEliminacion(String nombreUsuario) {
        Optional<ButtonType> respuesta = Utilidades.mostrarAlertaConfirmacion("Confirmar eliminación",
                "¿Estás seguro de eliminar al usuario " + nombreUsuario + "?");

        return respuesta.orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
    
    private void consultarInformacionUsuarios() {
        usuarios.clear();
        HashMap<String, Object> respuesta = UsuarioDAO.obtenerUsuarios();

        if (! (boolean) respuesta.get("error")) {
            List<Usuario> usuarioList = (List<Usuario>) respuesta.get("usuario");
            usuarios.addAll(usuarioList);
            tvUsuarios.setItems(usuarios);
        } else {
            Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        colApellidoM.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        colCURP.setCellValueFactory(new PropertyValueFactory<>("curp"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        colRol.setCellFactory(tc -> new TableCell<Usuario, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : (item == 1 ? "Administrador General" : "Administrador Comercial"));
            }
        });
    }

    private void irFormularioUsuario(Usuario usuario) {
        try {
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("FXMLFormularioUsuario.fxml"));
            Parent vista = loadVista.load();

            FXMLFormularioUsuarioController controlador = loadVista.getController();
            controlador.inicializarInformacion(usuario);

            Scene escenaAdmin = new Scene(vista);
            Stage escenarioAdmin = new Stage();
            escenarioAdmin.setScene(escenaAdmin);
            escenarioAdmin.setTitle("Formulario Usuarios");
            escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
            escenarioAdmin.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminarUsuario(int idUsuario) {
        Mensaje mensaje = UsuarioDAO.eliminarUsuario(idUsuario);

        if (!mensaje.getError()) {
            System.out.println("Usuario eliminado exitosamente");
            consultarInformacionUsuarios();
        } else {
            System.out.println("Error al eliminar usuario: " + mensaje.getMensaje());
        }
    }

    

    private void configurarFiltro() {
        if (listaUsuarioFiltro == null) {
            listaUsuarioFiltro = new FilteredList<>(usuarios, p -> true);
            SortedList<Usuario> sortedData = new SortedList<>(listaUsuarioFiltro);
            sortedData.comparatorProperty().bind(tvUsuarios.comparatorProperty());
            tvUsuarios.setItems(sortedData);
        }

        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            listaUsuarioFiltro.setPredicate(usuario -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return usuario.getNombre().toLowerCase().contains(lowerCaseFilter)
                        || usuario.getApellidoPaterno().toLowerCase().contains(lowerCaseFilter)
                        || usuario.getApellidoMaterno().toLowerCase().contains(lowerCaseFilter)
                        || usuario.getCorreoElectronico().toLowerCase().contains(lowerCaseFilter)
                        || usuario.getUsername().toLowerCase().contains(lowerCaseFilter)
                        || usuario.getCurp().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }
}

