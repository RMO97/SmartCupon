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
public class Empresa {
    private Integer idEmpresa;
    private Integer idDireccion;
    private String nombreEmpresa;
    private String nombreComercial;
    private String logoEmpresaBase64;
    private byte[] logoEmpresa;
    private String nombreRepresentante; 
    private String codigoPostal;
    private String ciudad;
    private String telefono;
    private String paginaWeb;
    private String rfc;
    private Boolean estatus;

    public Empresa() {
    }

    public Empresa(Integer idEmpresa, Integer idDireccion, String nombreEmpresa, String nombreComercial, String logoEmpresaBase64, byte[] logoEmpresa, String nombreRepresentante, String codigoPostal, String ciudad, String telefono, String paginaWeb, String rfc, Boolean estatus) {
        this.idEmpresa = idEmpresa;
        this.idDireccion = idDireccion;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreComercial = nombreComercial;
        this.logoEmpresaBase64 = logoEmpresaBase64;
        this.logoEmpresa = logoEmpresa;
        this.nombreRepresentante = nombreRepresentante;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.rfc = rfc;
        this.estatus = estatus;
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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getLogoEmpresaBase64() {
        return logoEmpresaBase64;
    }

    public void setLogoEmpresaBase64(String logoEmpresaBase64) {
        this.logoEmpresaBase64 = logoEmpresaBase64;
    }

    public byte[] getLogoEmpresa() {
        return logoEmpresa;
    }

    public void setLogoEmpresa(byte[] logoEmpresa) {
        this.logoEmpresa = logoEmpresa;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }
    
    
}
