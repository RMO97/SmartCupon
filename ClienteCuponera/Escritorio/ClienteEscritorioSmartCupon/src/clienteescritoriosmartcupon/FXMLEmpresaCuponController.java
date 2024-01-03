package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.EmpresaDAO;
import clienteescritoriosmartcupon.modelo.pojo.Empresa;
import clienteescritoriosmartcupon.modelo.pojo.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class FXMLEmpresaCuponController implements Initializable {
    private Usuario usuarioSesion;
    @FXML
    private ComboBox<Empresa> cbEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacionEmpresa();
    }    
   
    public void inicializar(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
        System.out.println("EL ROL ES: " + usuarioSesion.getRol());
        
    } 

    @FXML
    private void btnAceptar(ActionEvent event) {
        Empresa selectedEmpresa = cbEmpresa.getValue();
        if (selectedEmpresa != null) {
            int idEmpresa = selectedEmpresa.getIdEmpresa();
            abrirCuponesController(idEmpresa, usuarioSesion.getRol());
            System.out.println("ID EMPRESA: " + idEmpresa + "ROL: " + usuarioSesion.getRol());
        } else {
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        Stage stage = (Stage) cbEmpresa.getScene().getWindow();
        stage.close();
    }

    private void cargarInformacionEmpresa() {
        List<Empresa> empresas = EmpresaDAO.obtenerEmpresa();
        cbEmpresa.getItems().addAll(empresas);
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
    }

    private void abrirCuponesController(int idEmpresa, int rol) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCupones.fxml"));
            Parent root = loader.load();
            FXMLCuponesController cuponesController = loader.getController();
            cuponesController.inicializarInformacion(idEmpresa, rol);
            Scene scene = new Scene(root);
            Stage stage = (Stage) cbEmpresa.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

