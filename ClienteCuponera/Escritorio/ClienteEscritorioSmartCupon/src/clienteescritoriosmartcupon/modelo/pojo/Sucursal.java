package clienteescritoriosmartcupon.modelo.pojo;

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
    private String direccion;
    private Integer idPromocion;
    private String nombreComercial;
    private Integer idSucursalNuevo;

    public Sucursal() {
    }

    public Sucursal(Integer idSucursal, String nombreSucursal, Integer codigoPostal, String colonia, String ciudad, String telefono, Float latitud, Float longitud, String nombreEncargado, Integer idEmpresa, String direccion, Integer idPromocion, String nombreComercial, Integer idSucursalNuevo) {
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
        this.direccion = direccion;
        this.idPromocion = idPromocion;
        this.nombreComercial = nombreComercial;
        this.idSucursalNuevo = idSucursalNuevo;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public Integer getIdSucursalNuevo() {
        return idSucursalNuevo;
    }

    public void setIdSucursalNuevo(Integer idSucursalNuevo) {
        this.idSucursalNuevo = idSucursalNuevo;
    }

    
    
    


}
