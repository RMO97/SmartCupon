/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriosmartcupon.modelo.pojo;

/**
 *
 * @author Richard
 */
public class CodigoHTTP {
    
    private Integer codigoRespuesta;
    private String contenido;

    public CodigoHTTP() {
    }

    public CodigoHTTP(Integer codigoRespuesta, String contenido) {
        this.codigoRespuesta = codigoRespuesta;
        this.contenido = contenido;
    }

    public Integer getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(Integer codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
}
