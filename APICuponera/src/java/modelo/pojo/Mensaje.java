/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojo;

/**
 *
 * @author Richard
 */
public class Mensaje {
    private String mensaje;
    private Boolean error;

    public Mensaje() {
    }

    public Mensaje(String mensaje, Boolean error) {
        this.mensaje = mensaje;
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
    
}
