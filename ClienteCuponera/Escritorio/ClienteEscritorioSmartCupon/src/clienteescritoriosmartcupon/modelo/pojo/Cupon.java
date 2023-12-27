/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriosmartcupon.modelo.pojo;

import java.util.Date;

/**
 *
 * @author Richard
 */
public class Cupon {
    private Integer idCupon;
    private Integer idPromocion;
    private Integer idUsuario;
    private String codigoCupon;
    private Date fechaVencimiento;
    private String estatus;

    public Cupon() {
    }

    public Cupon(Integer idCupon, Integer idPromocion, Integer idUsuario, String codigoCupon, Date fechaVencimiento, String estatus) {
        this.idCupon = idCupon;
        this.idPromocion = idPromocion;
        this.idUsuario = idUsuario;
        this.codigoCupon = codigoCupon;
        this.fechaVencimiento = fechaVencimiento;
        this.estatus = estatus;
    }

    public Integer getIdCupon() {
        return idCupon;
    }

    public void setIdCupon(Integer idCupon) {
        this.idCupon = idCupon;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigoCupon() {
        return codigoCupon;
    }

    public void setCodigoCupon(String codigoCupon) {
        this.codigoCupon = codigoCupon;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
