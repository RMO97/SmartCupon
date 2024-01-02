/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriosmartcupon.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Richard
 */
public class Utilidades {
  public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }   
  public static Optional<ButtonType> mostrarAlertaConfirmacion(String titulo, String confirmacion){
        Alert dialogoAlertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoAlertaConfirmacion.setTitle(titulo);
        dialogoAlertaConfirmacion.setHeaderText(null);
        dialogoAlertaConfirmacion.setContentText(confirmacion);
        return dialogoAlertaConfirmacion.showAndWait();
    }
}
