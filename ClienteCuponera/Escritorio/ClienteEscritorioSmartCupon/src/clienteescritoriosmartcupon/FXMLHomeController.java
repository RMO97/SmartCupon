/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
            FXMLLoader vistaLoad = new FXMLLoader(getClass().getResource("FXMLAdminEmpresas.fxml"));
            Parent vista = vistaLoad.load();
            
            FXMLAdminEmpresasController controlador = vistaLoad.getController();
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
    }

    @FXML
    private void btnGestionPromociones(ActionEvent event) {
    }

    @FXML
    private void btnGestionUsuarios(ActionEvent event) {
    }
    
}
