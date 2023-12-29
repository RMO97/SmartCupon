/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.InicioSesionDAO;
import clienteescritoriosmartcupon.modelo.pojo.RespuestaLogin;
import clienteescritoriosmartcupon.modelo.pojo.Usuario;
import clienteescritoriosmartcupon.utils.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Richard
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfNombreUsuario;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private Label lbErrorNomUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
        lbErrorNomUsuario.setText("");
        lbErrorPassword.setText("");
        String username = tfNombreUsuario.getText();
        String password = tfPassword.getText();
        boolean isValido = true;
          if(username.isEmpty()){
            lbErrorNomUsuario.setText("El nombre de usuario es requerido");
            isValido = false;
        }
        if(password.isEmpty()){
            lbErrorPassword.setText("La contraseña es requerida");
            isValido = false;
        }
        
        if(isValido){
            verificarSesion(username, password);
        }
    }
    
    private void verificarSesion(String username, String password){
        RespuestaLogin respuestaValidacionLogin = InicioSesionDAO.validarSesion(username, password);
         if(!respuestaValidacionLogin.getError()){
            Utilidades.mostrarAlertaSimple("Credenciales correctas", respuestaValidacionLogin.getContenido(), Alert.AlertType.INFORMATION);
            irPantallaPrincipal(respuestaValidacionLogin.getUsuario());
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuestaValidacionLogin.getContenido(), Alert.AlertType.ERROR);
        }
    }
    
    private void irPantallaPrincipal(Usuario usuario){
        
    }
    
}
