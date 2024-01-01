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
import modelo.PromocionDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import modelo.pojo.Sucursal;


@Path("promocion")
public class PromocionWS {
    
    @GET
    @Path("obtener")
    @Produces(MediaType.APPLICATION_JSON)
    public String cadenaTexto(){
        return "aun funciona";
    }
    
    @GET
    @Path("buscarPorNombre/{nombrePromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> buscarPorNombre(@PathParam("nombrePromocion") String nombrePromocion){
        List<Promocion> promociones = null;
        if(nombrePromocion !=null){
            promociones = (List<Promocion>) PromocionDAO.buscarPorNombre(nombrePromocion);
            return promociones;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
     /* 
    @Path("/obtenerListaCupones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> obtenerListaCupones() {
        return PromocionDAO.obtenerPromocionesActivas();
    }
    
    @Path("/canjearCupon")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje canjearCupon(String json) {
        Gson gson = new Gson();
        Promocion promocion = gson.fromJson(json, Promocion.class);
        if (promocion != null && promocion.getCodigoPromocion() != null && !promocion.getCodigoPromocion().isEmpty()) {
            return PromocionDAO.canjearCupon(promocion);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }*/

    
    @GET
    @Path("buscarPorFechaInicio/{fechaDeInicioPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> buscarFechaInicio(@PathParam("fechaDeInicioPromocion") String fechaDeInicioPromocion){
        List<Promocion> promociones = null;
        if(fechaDeInicioPromocion !=null){
            promociones = (List<Promocion>) PromocionDAO.buscarFechaInicio(fechaDeInicioPromocion);
            return promociones;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("buscarPorFechaTermino/{fechaDeExpiracionPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> buscarFechaTermino(@PathParam("fechaDeExpiracionPromocion") String fechaDeExpiracionPromocion){
        List<Promocion> promociones = null;
        if(fechaDeExpiracionPromocion !=null){
            promociones = (List<Promocion>) PromocionDAO.buscarFechaTermino(fechaDeExpiracionPromocion);
            return promociones;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("obtenerPromociones")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> obtenerPromociones(){
        List<Promocion> promociones = null;
        
        promociones = (List<Promocion>) PromocionDAO.obtenerPromociones();
        return promociones;
        
    }
    
    @GET
    @Path("obtenerPromocionesEmpresa/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> obtenerPromocionesEmpresa(@PathParam("idEmpresa") Integer idEmpresa){
        List<Promocion> promociones = null;
        if(idEmpresa !=null){
            promociones = (List<Promocion>) PromocionDAO.obtenerPromocionesEmpresa(idEmpresa);
            return promociones;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("buscarSucursalPromocion")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> buscarSucursalPromocion(){
        List<Sucursal> sucursales = null;
        sucursales = (List<Sucursal>) PromocionDAO.buscarSucursalPromocion();
        if(sucursales !=null){
            return sucursales;
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @POST
    @Path("registrarPromocion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarPromocion(String json){
        Gson gson = new Gson();
        Promocion promocion = gson.fromJson(json, Promocion.class);
        if(promocion !=null){
            return PromocionDAO.registrarPromocion(promocion);
        }else{
           throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @PUT
    @Path("actualizarPromocion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje actualizarPromocion(String json){
        Gson gson = new Gson();
        Promocion promocion = gson.fromJson(json, Promocion.class);
        if (promocion !=null ) {
            return PromocionDAO.actualizarPromocion(promocion);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @DELETE
    @Path("eliminarPromocion/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarPromocion(@PathParam("idPromocion") Integer idPromocion){
        
        Mensaje msj = null;
        msj = PromocionDAO.eliminarPromocion(idPromocion);
        
        return msj;
    }
    
    @Path("obtenerFoto/{idPromocion}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion obtenerFotografia(@PathParam("idPromocion") Integer idPromocion){
        Promocion promocion = null;
        if(idPromocion !=null && idPromocion>0){
            promocion = PromocionDAO.obtenerFotografia(idPromocion);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return promocion;
    }
    
    @Path("registrarFoto/{idPromocion}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registrarFotografia(@PathParam("idPromocion") Integer idPromocion,
                                       byte[] foto){
        Mensaje msj = null;
        if(idPromocion != null && idPromocion > 0 && foto != null){
            msj = PromocionDAO.subirFotografiaPromocion(idPromocion, foto);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return msj;
        
        
    }
    
}
