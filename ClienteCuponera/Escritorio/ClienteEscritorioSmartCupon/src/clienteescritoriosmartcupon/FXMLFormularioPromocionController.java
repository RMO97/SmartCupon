package clienteescritoriosmartcupon;

import clienteescritoriosmartcupon.modelo.DAO.EmpresaDAO;
import clienteescritoriosmartcupon.modelo.DAO.PromocionDAO;
import clienteescritoriosmartcupon.modelo.DAO.SucursalDAO;
import clienteescritoriosmartcupon.modelo.pojo.Empresa;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Promocion;
import clienteescritoriosmartcupon.modelo.pojo.Sucursal;
import clienteescritoriosmartcupon.utils.Utilidades;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;

public class FXMLFormularioPromocionController implements Initializable {
    
    private Promocion promocion;
    private boolean editable = true;
    private Integer estatus;
    private Boolean est;
    private Integer tipo;
    private boolean tip;
    private File fotografia;
    private ObservableList<Empresa> sucursales;
    private Integer idPromocion;
    private Integer idSucursal;
    private Integer idEmpresa;

    @FXML
    private ImageView ivFoto;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDescripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaExpiracion;
    @FXML
    private RadioButton rbDescuento;
    @FXML
    private RadioButton rbCostoRebajado;
    @FXML
    private TextField tfValor;
    @FXML
    private Label lbPorcentaje;
    @FXML
    private TextField tfCategoria;
    @FXML
    private TextField tfNumeroMaximos;
    @FXML
    private TextField tfCodigoPromocion;
    @FXML
    private RadioButton rbActivo;
    @FXML
    private RadioButton rbInactivo;
    @FXML
    private TextField tfRestricciones;
    @FXML
    private ToggleGroup tgTipo;
    @FXML
    private ToggleGroup tgEstatus;
    @FXML
    private Label lbSucursal;
    @FXML
    private ComboBox<Empresa> cbEmpresa;
    @FXML
    private Label lbEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cargarInformacionSucursales();
        configurarSeleccion();
        
        rbInactivo.setVisible(false);
        tgEstatus.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(rbActivo.isSelected()){
                    estatus = 1;
                    est=true;
                }else{
                    estatus = 0;
                    est=false;
                }
            }    
        });
        tgTipo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(rbDescuento.isSelected()){
                    tipo = 1;
                    tip = true;
                    lbPorcentaje.setVisible(true);
                }else{
                    tipo = 0;
                    tip = false;
                    lbPorcentaje.setVisible(false);
                }
            }    
        });
    }    

    @FXML
    private void btnAceptar(ActionEvent event) {
        boolean isValido = true;
        
        String Categoria = tfCategoria.getText();
        String CodigoPromocion = tfCodigoPromocion.getText();
        String Descripcion = tfDescripcion.getText();
        String FechaDeExpiracionPromocion = dpFechaExpiracion.getValue().toString();
        String FechaDeInicioPromocion = dpFechaInicio.getValue().toString();
        String NombrePromocion = tfNombre.getText();
        String NumeroCuponesMaximo = tfNumeroMaximos.getText();
        String Valor = tfValor.getText();
        
        if(Categoria.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la categoria de la promocion");
            alert.setContentText("La categoria de la promocion no puede ir vacia");
        }
        if(CodigoPromocion.isEmpty() || CodigoPromocion.length() != 8 || !CodigoPromocion.matches("\\d{5}$")){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el codigo de la promocion");
            alert.setContentText("El codigo de la promocion no puede ir vacio");
            if(CodigoPromocion.isEmpty()){
                alert.setContentText("El codigo de la promocion no puede ir vacio.");
            }else if(CodigoPromocion.length() != 8) {
                alert.setContentText("El codigo de la promocion solo puede contener al menos 8 caracteres.");
            }else{
                alert.setContentText("El codigo de la promocion solo puede contener numeros y letras sin caracteres especiales");
            }
        }
        if(Descripcion.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la descripcion de la promocion");
            alert.setContentText("La descripcion de la promocion no puede ir vacia");
        }
        if (FechaDeExpiracionPromocion.isEmpty()) {
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la fecha de expiración de la promoción");
            alert.setContentText("La fecha de expiración de la promoción no puede ir vacía");
            alert.showAndWait();
        } else {
            LocalDate fechaExpiracion = LocalDate.parse(FechaDeExpiracionPromocion);

            if (fechaExpiracion.isBefore(LocalDate.now())) {
                isValido = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Error en la fecha de expiración de la promoción");
                alert.setContentText("La fecha de expiración de la promoción no puede ser anterior a la fecha actual");
                alert.showAndWait();
            }
        }

        if (FechaDeInicioPromocion.isEmpty()) {
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la fecha de inicio de la promoción");
            alert.setContentText("La fecha de inicio de la promoción no puede ir vacía");
            alert.showAndWait();
        } else {
            LocalDate fechaInicio = LocalDate.parse(FechaDeInicioPromocion);

            if (fechaInicio.isBefore(LocalDate.now())) {
                isValido = false;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Error en la fecha de inicio de la promoción");
                alert.setContentText("La fecha de inicio de la promoción no puede ser anterior a la fecha actual");
                alert.showAndWait();
            }
        }
        if(NombrePromocion.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre de la promocion");
            alert.setContentText("EL nombre de la promocion no puede ir vacio");
        }
        if(NumeroCuponesMaximo.isEmpty()){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el numero de cupones maximos de la promocion");
            alert.setContentText("El numero de comones maximos de la promocion no puede ir vacio");
        }
        
        if(Valor.isEmpty() || !Valor.matches("\\d{10}$")){
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el valor de la promocion");
            
            if(Valor.isEmpty()){
                alert.setContentText("El valor de la promocion no puede ir vacio");
            }else {
                alert.setContentText("El valor solo puede contener numeros sin caracteres especiales");
            }
        }
        if ("".equals(estatus)) {
            isValido = false;
        }
        if ("".equals(tipo)) {
            isValido = false;
        }
        if (cbEmpresa == null) {
            isValido = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error en el nombre de la empresa");
            alert.setContentText("El nombre de la empresa no puede ir vacio");
        }
        if (isValido) {
            if (editable) {
                Promocion promocionEditada = new Promocion();
                promocionEditada.setIdPromocion(idPromocion);
               

                promocionEditada.setCategoria(Categoria);
                promocionEditada.setCodigoPromocion(CodigoPromocion);
                promocionEditada.setDescripcion(Descripcion);
                promocionEditada.setEstatus(est);
                promocionEditada.setFechaDeExpiracionPromocion(FechaDeExpiracionPromocion);
                promocionEditada.setFechaDeInicioPromocion(FechaDeInicioPromocion);
                promocionEditada.setTipoPromocion(tip);
                promocionEditada.setNombrePromocion(NombrePromocion);
                promocionEditada.setNumeroCuponesMaximo(Integer.parseInt(NumeroCuponesMaximo));
                promocionEditada.setRestriccion(tfRestricciones.getText());
                promocionEditada.setValor(Float.parseFloat(Valor));
                if (idEmpresa!=null) {
                    promocionEditada.setIdEmpresa(idEmpresa);
                } else {
                    Empresa Seleccion = cbEmpresa.getValue();
                    promocionEditada.setIdEmpresa(Seleccion.getIdEmpresa());
                }

                irSeleccionSucursales(idEmpresa,idPromocion);
                editar(promocionEditada);
            }else{
                
                Promocion promocionNueva = new Promocion();
                promocionNueva.setEstatus(est);
                promocionNueva.setTipoPromocion(tip);
                promocionNueva.setCategoria(Categoria);
                promocionNueva.setCodigoPromocion(CodigoPromocion);
                promocionNueva.setDescripcion(Descripcion);
                promocionNueva.setEstatus(est);
                promocionNueva.setFechaDeExpiracionPromocion(FechaDeExpiracionPromocion);
                promocionNueva.setFechaDeInicioPromocion(FechaDeInicioPromocion);
                promocionNueva.setTipoPromocion(tip);
                promocionNueva.setNombrePromocion(NombrePromocion);
                promocionNueva.setNumeroCuponesMaximo(Integer.parseInt(NumeroCuponesMaximo));
                promocionNueva.setRestriccion(tfRestricciones.getText());
                promocionNueva.setValor(Float.parseFloat(Valor));

                if (idEmpresa!=null) {
                    promocionNueva.setIdEmpresa(idEmpresa);
                } else {
                    Empresa Seleccion = cbEmpresa.getValue();
                    promocionNueva.setIdEmpresa(Seleccion.getIdEmpresa());
                }
                irSeleccionSucursales(idEmpresa,idPromocion);
                registrar(promocionNueva);

            }
        }
        
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        cerrarPantalla();
    }

    @FXML
    private void btnSeleccionarFoto(ActionEvent event) {
        fotografia = mostrarDialogoSeleccion();
        if(fotografia !=null){
            mostrarImagenSeleccionada(fotografia);
        }
    }

    @FXML
    private void btnSubirFoto(ActionEvent event) {
        if(fotografia != null){
            Mensaje msj = PromocionDAO.subirFotografiaPromocion(fotografia, promocion.getIdPromocion());
            if(!msj.getError()==true){
                Utilidades.mostrarAlertaSimple("fotografia guardada", msj.getMensaje(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Error al guardar la fotografia", msj.getMensaje(), Alert.AlertType.ERROR);
            }
        }
    }
    
    private File mostrarDialogoSeleccion(){
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter filtroArchivos = new FileChooser.ExtensionFilter("Archivos PNG (*.png, *.jpg)", "*.png", "*.jpg");
        dialogoSeleccionImg.getExtensionFilters().add(filtroArchivos);
        Stage escenario = (Stage) tfNombre.getScene().getWindow();
        return dialogoSeleccionImg.showOpenDialog(escenario);
        
    }
    
    private void mostrarImagenSeleccionada(File foto){
        try {
            BufferedImage buffer = ImageIO.read(foto);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            ivFoto.setImage(image);
        } catch (IOException e) {
            Utilidades.mostrarAlertaSimple("Error al cargar", "Error al intentar visualizar la imagen seleccionada", Alert.AlertType.ERROR);
        }
    }
    
    private void obtenerFoto(int idPromocion){
        Promocion promocionFoto = PromocionDAO.obtenerFotografia(idPromocion);
        if(promocionFoto != null && promocionFoto.getFotografiaBase64()!=null&&promocionFoto.getFotografiaBase64().length()>0){
            byte[] decodeImg = Base64.getDecoder().decode(promocionFoto.getFotografiaBase64().replaceAll("\\n", ""));
            Image image = new Image(new ByteArrayInputStream(decodeImg));
            ivFoto.setImage(image);
        }
    }
    
    private void registrar(Promocion promocionNueva){
        Mensaje msj = PromocionDAO.registrarPromocion(promocionNueva);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Promocion guardado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void editar(Promocion promocionEditada){
        
        Mensaje msj = PromocionDAO.editarPromocion(promocionEditada);
        if(!msj.getError()){
            Utilidades.mostrarAlertaSimple("Promocion guardado", msj.getMensaje(), Alert.AlertType.INFORMATION);
            cerrarPantalla();
        }else{
            Utilidades.mostrarAlertaSimple("Error al editar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    public void inicializarFormulario(Promocion promocion, Integer idEmpresa){
        this.promocion = promocion;
        this.idEmpresa = idEmpresa;
        
        if (idEmpresa!=null && idEmpresa !=0) {
            cbEmpresa.setVisible(false);
            lbEmpresa.setVisible(false);
        }else if(idEmpresa==0){
            cbEmpresa.setVisible(true);
            lbEmpresa.setVisible(true);
        }
        
        if(promocion == null){
            this.editable = false;
        }else{
            rbInactivo.setVisible(true);
            tfCategoria.setText(promocion.getCategoria());
            tfCodigoPromocion.setText(promocion.getCodigoPromocion());
            tfDescripcion.setText(promocion.getDescripcion());
            tfNombre.setText(promocion.getNombrePromocion());
            String numMax = Integer.toString(promocion.getNumeroCuponesMaximo());
            tfNumeroMaximos.setText(numMax);
            tfRestricciones.setText(promocion.getRestriccion());
            String val = Float.toString(promocion.getValor());
            tfValor.setText(val);
            if(promocion.getEstatus() == true){
                rbActivo.setSelected(true);
            }else if(promocion.getEstatus() == false){
                rbInactivo.setSelected(true);
            }
            
            if(promocion.getTipoPromocion() == true){
                rbDescuento.setSelected(true);
            }else if(promocion.getTipoPromocion()== false){
                rbCostoRebajado.setSelected(true);
            }

            Empresa empresaSeleccionada = sucursales.stream()
                    .filter(emp -> emp.getIdEmpresa()== promocion.getIdEmpresa())
                    .findFirst()
                    .orElse(null);
            cbEmpresa.setValue(empresaSeleccionada);
            this.idPromocion = promocion.getIdPromocion();
            String patter = "yyyy-MM-dd";
            DateTimeFormatter date = DateTimeFormatter.ofPattern(patter);
            dpFechaExpiracion.setValue(LocalDate.parse(promocion.getFechaDeExpiracionPromocion(),date));
            dpFechaInicio.setValue(LocalDate.parse(promocion.getFechaDeInicioPromocion(),date));
            obtenerFoto(promocion.getIdPromocion());
        }
    }
    
    private void cerrarPantalla(){
        Stage stage = (Stage)tfCategoria.getScene().getWindow();
        stage.close();
    }
   
    private void cargarInformacionSucursales() {
    sucursales = FXCollections.observableArrayList();
    HashMap<String, Object> info = EmpresaDAO.obtenerEmpresas();

    List<Empresa> listaEmpresas = (List<Empresa>) info.get("empresas");

    sucursales.addAll(listaEmpresas);

    cbEmpresa.setItems(sucursales);
    cbEmpresa.setConverter(new StringConverter<Empresa>() {
        @Override
        public String toString(Empresa empresa) {
            return empresa.getNombreComercial();
        }

        @Override
        public Empresa fromString(String string) {
            return null;
        }
    });
    }

    private void configurarSeleccion() {
        cbEmpresa.valueProperty().addListener(new ChangeListener<Empresa>() {
            @Override
            public void changed(ObservableValue<? extends Empresa> observable, Empresa oldValue, Empresa newValue) {
                if (newValue != null) {
                    idEmpresa = newValue.getIdEmpresa();
                }
            }
        });
    }
    
    public void irSeleccionSucursales(Integer idEmpresa, Integer idPromocion){
        try {
            Stage stage = new Stage();
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("FXMLSeleccionSucursalPromocion.fxml"));
            Parent vista = loadVista.load();
            FXMLSeleccionSucursalPromocionController controladorEditar = loadVista.getController();
            controladorEditar.inicializarInformacion(idEmpresa, idPromocion);
            
            Scene scene = new Scene(vista);
            stage.setScene(scene);
            stage.setTitle("Formulario promocion");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
}

