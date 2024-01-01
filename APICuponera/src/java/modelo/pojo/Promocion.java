package modelo.pojo;

public class Promocion {
    private Integer idPromocion;
    private String nombrePromocion;
    private String descripcion;
    private byte[] imagenPromocion;
    private String fechaDeInicioPromocion;
    private String fechaDeExpiracionPromocion;
    private String restriccion;
    private String categoria;
    private Integer numeroCuponesMaximo;
    private String codigoPromocion;
    private Boolean estatus;
    private String fotografiaBase64;
    private Boolean tipoPromocion;
    private Float valor;
    private Integer idSucursal;
    private Integer idEmpresa;
    private String nombreSucursal;
    private String nombreEmpresa;

    public Promocion() {
    }

    public Promocion(Integer idPromocion, String nombrePromocion, String descripcion, byte[] imagenPromocion, String fechaDeInicioPromocion, String fechaDeExpiracionPromocion, String restriccion, String categoria, Integer numeroCuponesMaximo, String codigoPromocion, Boolean estatus, String fotografiaBase64, Boolean tipoPromocion, Float valor, Integer idSucursal, Integer idEmpresa, String nombreSucursal, String nombreEmpresa) {
        this.idPromocion = idPromocion;
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
        this.fotografiaBase64 = fotografiaBase64;
        this.tipoPromocion = tipoPromocion;
        this.valor = valor;
        this.idSucursal = idSucursal;
        this.idEmpresa = idEmpresa;
        this.nombreSucursal = nombreSucursal;
        this.nombreEmpresa = nombreEmpresa;
    }

    

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
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

    public byte[] getImagenPromocion() {
        return imagenPromocion;
    }

    public void setImagenPromocion(byte[] imagenPromocion) {
        this.imagenPromocion = imagenPromocion;
    }

    public String getFechaDeInicioPromocion() {
        return fechaDeInicioPromocion;
    }

    public void setFechaDeInicioPromocion(String fechaDeInicioPromocion) {
        this.fechaDeInicioPromocion = fechaDeInicioPromocion;
    }

    public String getFechaDeExpiracionPromocion() {
        return fechaDeExpiracionPromocion;
    }

    public void setFechaDeExpiracionPromocion(String fechaDeExpiracionPromocion) {
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

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public String getFotografiaBase64() {
        return fotografiaBase64;
    }

    public void setFotografiaBase64(String fotografiaBase64) {
        this.fotografiaBase64 = fotografiaBase64;
    }

    public Boolean getTipoPromocion() {
        return tipoPromocion;
    }

    public void setTipoPromocion(Boolean tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
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

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }
    
    
    
   
}



