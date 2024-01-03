package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.PromocionDAO;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Promocion;
import clienteescritoriosmartcupon.modelo.pojo.Usuario;
import clienteescritoriosmartcupon.utils.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.stage.Stage;

public class FXMLCuponesController implements Initializable {

    private Integer idEmpresa = null;
    private Integer rol = null;
    private ObservableList<Promocion> cuponesEmpresa;

    @FXML
    private TableView<Promocion> tvObtenerCupon;
    @FXML
    private TextField txfCodigo;
    @FXML
    private TableColumn colNombrePromocion;
    @FXML
    private TableColumn colNumeroCuponesMaximo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cuponesEmpresa = FXCollections.observableArrayList();
        configurarColumnasTabla();
        consultarCupones();
    }

    public void inicializarInformacion(int idEmpresa, int rol) {
        this.idEmpresa = idEmpresa;
        this.rol = rol;
        consultarCupones();
    }

    @FXML
    private void btnCanjear(ActionEvent event) {
        configurarColumnasTabla();
        String codigoCupon = txfCodigo.getText();
        Promocion promocion = new Promocion();
        promocion.setIdEmpresa(idEmpresa);
        promocion.setCodigoPromocion(codigoCupon);
        
        boolean cuponPertenece = verificarCuponPerteneceEmpresa(promocion);
        
        if (cuponPertenece) {
            Mensaje mensaje = PromocionDAO.canjearCupon(promocion);
            if (!mensaje.getError()) {
                Utilidades.mostrarAlertaSimple("Éxito", mensaje.getMensaje(), Alert.AlertType.INFORMATION);
                cuponesEmpresa.clear();
                consultarCupones();
            } else {
                Utilidades.mostrarAlertaSimple("Error", mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error", "El código del cupón no existe en la empresa seleccionada.", Alert.AlertType.ERROR);
        }
    }

    private boolean verificarCuponPerteneceEmpresa(Promocion promocion) {
        return promocion.getIdEmpresa() == idEmpresa;
    }

    private void configurarColumnasTabla() {
        colNombrePromocion.setCellValueFactory(new PropertyValueFactory<>("nombrePromocion"));
        colNumeroCuponesMaximo.setCellValueFactory(new PropertyValueFactory<>("numeroCuponesMaximo"));
    }

    private void consultarCupones() {
        Usuario usuario = new Usuario();
        usuario.setRol(rol);
        Integer rolUsuario = usuario.getRol();        
        if (rolUsuario != null) {
            HashMap<String, Object> respuesta = rolUsuario == 1
                    ? PromocionDAO.obtenerCupones()
                    : PromocionDAO.obtenerCuponesPorEmpresa(idEmpresa);

            if (!(boolean) respuesta.get("error")) {
                List<Promocion> listaCupones = (List<Promocion>) respuesta.get("promocion");
                tvObtenerCupon.setItems(FXCollections.observableArrayList(listaCupones));
            } else {
                Utilidades.mostrarAlertaSimple("Error", (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnCerrar(ActionEvent event) {
        Stage stage = (Stage) txfCodigo.getScene().getWindow();
        stage.close();
    }
}




