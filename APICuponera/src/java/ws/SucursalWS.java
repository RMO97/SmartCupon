package ws;

import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.SucursalDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Sucursal;

@Path("sucursal")
public class SucursalWS {
    
    @GET
    @Path("obtener")
    @Produces(MediaType.APPLICATION_JSON)
    public String cadenaTexto(){
        return "aun funciona";
    }
    
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
}
