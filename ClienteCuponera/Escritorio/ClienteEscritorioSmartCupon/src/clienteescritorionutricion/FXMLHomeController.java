/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion;

import clienteescritoriosmartcupon.modelo.pojo.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Richard
 */
public class FXMLHomeController implements Initializable {

    private Usuario usuarioSesion;
    
    @FXML
    private Label lbRol;
    @FXML
    private Label lbNombreUsuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarHomeSesion(Usuario usuarioLogeado){
        this.usuarioSesion = usuarioLogeado;
        
    }

    @FXML
    private void btnGestionEmpresas(ActionEvent event) {
    }

    @FXML
    private void btnGestionSucursales(ActionEvent event) {
    }

    @FXML
    private void btnGestionCupones(ActionEvent event) {
    }

    @FXML
    private void btnGestionPromociones(ActionEvent event) {
    }

    @FXML
    private void btnGestionUsuarios(ActionEvent event) {
    }
    
}
