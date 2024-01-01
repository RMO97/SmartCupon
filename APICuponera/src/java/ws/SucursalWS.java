package ws;

import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.SucursalDAO;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import modelo.pojo.Sucursal;
import modelo.pojo.Usuario;

@Path("sucursal")
public class SucursalWS {
    
    @GET
    @Path("buscarPorNombre/{nombreSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> buscarPorNombre(@PathParam("nombreSucursal") String nombreSucursal){
        List<Sucursal> sucursales = null;
        if(nombreSucursal !=null){
            sucursales = (List<Sucursal>) SucursalDAO.buscarPorNombre(nombreSucursal);
            return sucursales;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("buscarPorDireccion/{direccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> buscarPordireccion(@PathParam("direccion") String direccion){
        List<Sucursal> sucursales = null;
        if(direccion !=null){
            sucursales = (List<Sucursal>) SucursalDAO.buscarPorDireccion(direccion);
            return sucursales;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("buscarPorEmpresa/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> buscarPorEmpresa(@PathParam("idEmpresa") Integer idEmpresa){
        List<Sucursal> sucursales = null;
        if(idEmpresa !=null){
            sucursales = (List<Sucursal>) SucursalDAO.obtenerPorEmpresa(idEmpresa);
            return sucursales;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("buscarPorId/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> buscarPorIdSucursal(@PathParam("idSucursal") Integer idSucursal){
        List<Sucursal> sucursales = null;
        if(idSucursal !=null){
            sucursales = (List<Sucursal>) SucursalDAO.obtenerPorID(idSucursal);
            return sucursales;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("obtenerSucursales")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> obtenerSucursales(){
        List<Sucursal> sucursales = null;
        
        sucursales = (List<Sucursal>) SucursalDAO.obtenerSucursales();
        return sucursales;
        
    }
    @GET
    @Path("obtenerEmpresas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> obtenerEmpresas(){
        List<Empresa> empresas = null;
        
        empresas = (List<Empresa>) SucursalDAO.obtenerEmpresas();
        return empresas;
        
    }
    
    @GET
    @Path("buscarEmpresaUsuario/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> buscarEmpresaUsuario(@PathParam("idEmpresa") Integer idEmpresa){
        List<Usuario> usuario = null;
        if(idEmpresa !=null){
            usuario = (List<Usuario>) SucursalDAO.buscarEmpresaUsuario(idEmpresa);
            return usuario;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @POST
    @Path("registrarSucursal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarSucursal(String json){
        
        Gson gson = new Gson();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        if(sucursal !=null){
            return SucursalDAO.registrarSucursal(sucursal);
        }else{
           throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @PUT
    @Path("actualizarSucursal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje actualizarSucursal(String json){
        Gson gson = new Gson();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        if (sucursal !=null ) {
            return SucursalDAO.actualizarSucursal(sucursal);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    @PUT
    @Path("actualizarPromocionSucursal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje actualizarPromocionSucursal(String json){
        Gson gson = new Gson();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        if (sucursal !=null ) {
            return SucursalDAO.editarPromocionSucursal(sucursal);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @PUT
    @Path("actualizarEmpresaSucursal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje actualizarEmpresaSucursal(String json){
        Gson gson = new Gson();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        if (sucursal !=null ) {
            return SucursalDAO.editarEmpresaSucursal(sucursal);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @DELETE
    @Path("eliminarSucursal/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarSucursal(@PathParam("idSucursal") Integer idSucursal){
        
        Mensaje msj = null;
        msj = SucursalDAO.eliminarSucursal(idSucursal);
        
        return msj;
    }
}
