package modelo.pojo;

import java.util.Date;

public class Promocion {
    private Integer idPromocion;
    private Integer idTipoPromocion;
    private Integer idSucursal;
    private Integer idEmpresa;
    private String nombrePromocion;
    private String descripcion;
    private String imagenPromocion;
    private Date fechaDeInicioPromocion;
    private Date fechaDeExpiracionPromocion;
    private String restriccion;
    private String categoria;
    private Integer numeroCuponesMaximo;
    private String codigoPromocion;
    private boolean estatus;

    public Promocion() {
    }

    public Promocion(Integer idTipoPromocion, Integer idSucursal, Integer idEmpresa, String nombrePromocion,
            String descripcion, String imagenPromocion, Date fechaDeInicioPromocion, Date fechaDeExpiracionPromocion,
            String restriccion, String categoria, Integer numeroCuponesMaximo, String codigoPromocion,
            boolean estatus) {
        this.idTipoPromocion = idTipoPromocion;
        this.idSucursal = idSucursal;
        this.idEmpresa = idEmpresa;
        this.nombrePromocion = nombrePromocion;
        this.descripcion = descripcion;
        this.imagenPromocion = imagenPromocion;
        this.fechaDeInicioPromocion = fechaDeInicioPromocion;
        this.fechaDeExpiracionPromocion = fechaDeExpiracionPromocion;
        this.restriccion = restriccion;
        this.categoria = categoria;
        this.numeroCuponesMaximo = numeroCuponesMaximo;
        this.codigoPromocion = codigoPromocion;
        this.estatus = estatus;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Integer getIdTipoPromocion() {
        return idTipoPromocion;
    }

    public void setIdTipoPromocion(Integer idTipoPromocion) {
        this.idTipoPromocion = idTipoPromocion;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombrePromocion() {
        return nombrePromocion;
    }

    public void setNombrePromocion(String nombrePromocion) {
        this.nombrePromocion = nombrePromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenPromocion() {
        return imagenPromocion;
    }

    public void setImagenPromocion(String imagenPromocion) {
        this.imagenPromocion = imagenPromocion;
    }

    public Date getFechaDeInicioPromocion() {
        return fechaDeInicioPromocion;
    }

    public void setFechaDeInicioPromocion(Date fechaDeInicioPromocion) {
        this.fechaDeInicioPromocion = fechaDeInicioPromocion;
    }

    public Date getFechaDeExpiracionPromocion() {
        return fechaDeExpiracionPromocion;
    }

    public void setFechaDeExpiracionPromocion(Date fechaDeExpiracionPromocion) {
        this.fechaDeExpiracionPromocion = fechaDeExpiracionPromocion;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getNumeroCuponesMaximo() {
        return numeroCuponesMaximo;
    }

    public void setNumeroCuponesMaximo(Integer numeroCuponesMaximo) {
        this.numeroCuponesMaximo = numeroCuponesMaximo;
    }

    public String getCodigoPromocion() {
        return codigoPromocion;
    }

    public void setCodigoPromocion(String codigoPromocion) {
        this.codigoPromocion = codigoPromocion;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }
    
    
}