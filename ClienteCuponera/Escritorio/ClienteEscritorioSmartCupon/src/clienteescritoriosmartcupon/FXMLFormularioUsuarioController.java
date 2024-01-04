package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.EmpresaDAO;
import clienteescritoriosmartcupon.modelo.DAO.UsuarioDAO;
import clienteescritoriosmartcupon.modelo.pojo.Empresa;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Usuario;
import clienteescritoriosmartcupon.utils.Utilidades;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLFormularioUsuarioController implements Initializable {
    private Usuario usuario;
    private int idEmpresaSeleccionada;

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoP;
    @FXML
    private TextField tfApellidoM;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfContraseña;
    @FXML
    private TextField tfCorreoElectronico;
    @FXML
    private ComboBox<Empresa> cbEmpresa;
    @FXML
    private ComboBox<String> cbRol;

    private ObservableList<Empresa> empresa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (usuario != null) {
            cbRol.setEditable(false);
            cbRol.setVisible(false);
        }
        cargarInformacionEmpresa();
        configurarSeleccionEmpresa();
        List<String> nombresRoles = Arrays.asList("Administrador General", "Administrador Comercial");
        cbRol.setItems(FXCollections.observableArrayList(nombresRoles));
    }

    public void inicializarInformacion(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            cbRol.setEditable(false);
            cbRol.setVisible(false);
            cargarDatos();
        } else {

        }
    }

    @FXML
    private void btnAceptar(ActionEvent event) {
        if (usuario == null) {
            registrarUsuario();
        } else {
            editarUsuario();
        }
    }

    private void registrarUsuario() {
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoP.getText();
        String apellidoMaterno = tfApellidoM.getText();
        String curp = tfCurp.getText();
        String username = tfUsername.getText();
        String password = tfContraseña.getText();
        String correoElectronico = tfCorreoElectronico.getText();

        boolean isValido = true;
        if(nombre.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre del Usuario");
            alert.setContentText("El Nombre no puede ir vacio");
        }
        if(apellidoPaterno.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre del Usuario");
            alert.setContentText("El Apellido Paterno no puede ir vacio");
        }
        if(apellidoMaterno.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre del Usuario");
            alert.setContentText("El Apellido Materno no puede ir vacio");
        }
        if(curp.isEmpty() || !curp.matches("\\d{18}$")){
            isValido = false;
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la curp ");
            if(curp.isEmpty()){
                alert.setContentText("El curp no puede ir vacio.");
            }else{
                alert.setContentText("El curp debe tener exactamente 18 caracteres alfanumericos.");
            }
            alert.showAndWait();
        }
        if(username.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre del Usuario");
            alert.setContentText("El Username no puede ir vacio");
        }
        if(password.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre del Usuario");
            alert.setContentText("La contraseña no puede ir vacio");
        }
        if(correoElectronico.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre del Usuario");
            alert.setContentText("El Correo electronico no puede ir vacio");
        }
        

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellidoPaterno(apellidoPaterno);
        usuario.setApellidoMaterno(apellidoMaterno);
        usuario.setCurp(curp);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setCorreoElectronico(correoElectronico);
        String nombreRol = cbRol.getValue();
        if (nombreRol != null) {
            int valorRol = obtenerValorRol(nombreRol);
            usuario.setRol(valorRol);
        } else {
            Utilidades.mostrarAlertaSimple("Error", "Por favor, selecciona un rol", Alert.AlertType.ERROR);
            return;
        }
        if (!nombreRol.equals("Administrador General")) {
            Empresa empresaSeleccionada = cbEmpresa.getValue();
            if (empresaSeleccionada != null) {
                usuario.setIdEmpresa(empresaSeleccionada.getIdEmpresa());
            } else {
                Utilidades.mostrarAlertaSimple("Error", "Por favor, selecciona una empresa", Alert.AlertType.ERROR);
                return;
            }
        }

    Mensaje mensaje = UsuarioDAO.registrarUsuario(usuario);

    if (!mensaje.getError()) {
        Utilidades.mostrarAlertaSimple("Éxito", "Usuario registrado exitosamente", Alert.AlertType.INFORMATION);
        cerrarPantalla();
    } else {
        Utilidades.mostrarAlertaSimple("Error", "Error al registrar usuario: " + mensaje.getMensaje(), Alert.AlertType.ERROR);
    }
}
    private void editarUsuario() {
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoP.getText();
        String apellidoMaterno = tfApellidoM.getText();
        String curp = tfCurp.getText();
        String username = tfUsername.getText();
        String password = tfContraseña.getText();
        String correoElectronico = tfCorreoElectronico.getText();

        usuario.setNombre(nombre);
        usuario.setApellidoPaterno(apellidoPaterno);
        usuario.setApellidoMaterno(apellidoMaterno);
        usuario.setCurp(curp);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setCorreoElectronico(correoElectronico);

        Empresa empresaSeleccionada = cbEmpresa.getValue();

        if (empresaSeleccionada != null) {
            int idEmpresaSeleccionada = empresaSeleccionada.getIdEmpresa();
            usuario.setIdEmpresa(idEmpresaSeleccionada);
        }

        Mensaje mensaje = UsuarioDAO.editarUsuario(usuario);

        if (!mensaje.getError()) {
            System.out.println("Usuario actualizado exitosamente");
        } else {
            System.out.println("Error al actualizar usuario: " + mensaje.getMensaje());
        }

        cerrarPantalla();
    }

    private int obtenerValorRol(String nombreRol) {
        switch (nombreRol) {
            case "Administrador General":
                return 1;
            case "Administrador Comercial":
                return 2;
            default:
                return -1;
        }
    }

    private void cargarInformacionEmpresa() {
        empresa = FXCollections.observableArrayList();
        List<Empresa> info = EmpresaDAO.obtenerEmpresa();
        empresa.addAll(info);

        cbEmpresa.setConverter(new StringConverter<Empresa>() {
            @Override
            public String toString(Empresa empresa) {
                return empresa.getNombreEmpresa();
            }

            @Override
            public Empresa fromString(String string) {
                return null;
            }
        });

        cbEmpresa.setItems(empresa);
    }

    private void configurarSeleccionEmpresa() {
        cbEmpresa.valueProperty().addListener(new ChangeListener<Empresa>() {
            @Override
            public void changed(ObservableValue<? extends Empresa> observable,
                                Empresa oldValue, Empresa newValue) {
                if (newValue != null) {
                    idEmpresaSeleccionada = newValue.getIdEmpresa();
                }
            }
        });
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarPantalla();
    }

    private void cerrarPantalla() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }

    private void cargarDatos() {
        tfNombre.setText(usuario.getNombre());
        tfApellidoP.setText(usuario.getApellidoPaterno());
        tfApellidoM.setText(usuario.getApellidoMaterno());
        tfCurp.setText(usuario.getCurp());
        tfUsername.setText(usuario.getUsername());
        tfContraseña.setText(usuario.getPassword());
        tfCorreoElectronico.setText(usuario.getCorreoElectronico());

        Empresa empresaSeleccionada = empresa.stream()
                .filter(emp -> emp.getIdEmpresa() == usuario.getIdEmpresa())
                .findFirst()
                .orElse(null);
        cbEmpresa.setValue(empresaSeleccionada);

        if (cbRol.isVisible()) {
            String nombreRol = (usuario.getRol() == 1) ? "Administrador General" : "Administrador Comercial";
            cbRol.setValue(nombreRol);
        }
    }
}
