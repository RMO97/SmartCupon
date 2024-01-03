package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.pojo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Richard
 */
public class FXMLHomeController implements Initializable {

    private Usuario usuarioSesion;
    
    @FXML
    private Label lbNombreUsuario;
    @FXML
    private Label lbRol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    public void inicializarHome(Usuario usuarioSesion){
        this.usuarioSesion = usuarioSesion;
        lbNombreUsuario.setText(usuarioSesion.getNombre()+ " "+ usuarioSesion.getApellidoPaterno()+ " "+ usuarioSesion.getApellidoMaterno());
        if(usuarioSesion.getRol() == 1){
            lbRol.setText("Administrador general");
        }else if(usuarioSesion.getRol() == 2){
            lbRol.setText("Administrador comercial ");
        }
    }
    @FXML
    private void btnGestionEmpresas(ActionEvent event) {
        try{
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLGestionEmpresas.fxml"));
            Parent vista = vistaLoad.load();
            
            FXMLGestionEmpresasController controlador = vistaLoad.getController();
            controlador.inicializarInformacion();
            
            Stage stage = new Stage();
            Scene escenaAdmin = new Scene(vista);
            stage.setScene(escenaAdmin);
            stage.setTitle("Empresas");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    private void btnGestionSucursales(ActionEvent event) {
    }

    @FXML
    private void btnGestionCupones(ActionEvent event) {
    try {
        String fxmlPath;
        if (usuarioSesion.getRol() == 1) {
            fxmlPath = "FXMLCupones.fxml";
        } else if (usuarioSesion.getRol() == 2) {
            fxmlPath = "FXMLEmpresaCupon.fxml";
        } else {
            System.out.println("Rol de usuario no válido");
            return;
        }
        FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent vista = vistaLoad.load();
        if (fxmlPath.equals("FXMLEmpresaCupon.fxml")) {
            // Si es FXMLEmpresaCupon.fxml, pasamos el usuarioSesion al controlador
            FXMLEmpresaCuponController empresaCuponController = vistaLoad.getController();
            empresaCuponController.inicializar(usuarioSesion);
        }
        Stage stage = new Stage();
        Scene escenaAdmin = new Scene(vista);
        stage.setScene(escenaAdmin);
        stage.setTitle("Gestión de Cupones");
        stage.showAndWait();

    } catch (IOException ioe) {
        ioe.printStackTrace();
            }
        }

    @FXML
    private void btnGestionPromociones(ActionEvent event) {
    }

    @FXML
    private void btnGestionUsuarios(ActionEvent event) {
         try {
        FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLGestionUsuarios.fxml"));
        Parent vista = vistaLoad.load();

        FXMLGestionUsuariosController controlador = vistaLoad.getController();
        // Puedes realizar cualquier inicialización necesaria en el controlador FXMLGestionUsuarios aquí

        Stage stage = new Stage();
        Scene escenaAdmin = new Scene(vista);
        stage.setScene(escenaAdmin);
        stage.setTitle("Gestión de Usuarios");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    } catch (IOException ioe) {
        ioe.printStackTrace();
        // Agrega manejo de excepciones aquí según tus necesidades
    }
    }
    
}
