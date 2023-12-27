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
public class Sucursal {
    private Integer idSucursal;
    private String nombreSucursal;
    private Integer codigoPostal;
    private String colonia;
    private String ciudad;
    private String telefono;
    private Float latitud;
    private Float longitud;
    private String nombreEncargado;
    private Integer idEmpresa;
    private Integer idDireccion;
    private Integer idPromocion;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal, String nombreSucursal, Integer codigoPostal, String colonia, String ciudad, String telefono, Float latitud, Float longitud, String nombreEncargado, Integer idEmpresa, Integer idDireccion, Integer idPromocion) {
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.codigoPostal = codigoPostal;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombreEncargado = nombreEncargado;
        this.idEmpresa = idEmpresa;
        this.idDireccion = idDireccion;
        this.idPromocion = idPromocion;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }
}
