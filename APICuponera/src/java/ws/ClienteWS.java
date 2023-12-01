package ws;

import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.ClienteDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;

/**
 *
 * @author Richard
 */

@Path("cliente")
public class ClienteWS {
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registroCliente(String json){
        Gson gson = new Gson();
        Cliente cliente = gson.fromJson(json, Cliente.class);
        if(cliente !=null && cliente.getCorreoElectronico()!=null && !cliente.getCorreoElectronico().isEmpty()){
            return ClienteDAO.registrarCliente(cliente);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
            
        } 
    }
    
    
    @Path("editar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje editarCliente(String json){
          Gson gson = new Gson();
        Cliente cliente = gson.fromJson(json, Cliente.class);
        if(cliente !=null && cliente.getIdCliente()!=null && cliente.getIdCliente()>0){
            return ClienteDAO.editarCliente(cliente);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
            
        } 
    }
    
    @Path("eliminar")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarCliente(@FormParam("idCliente") Integer idCliente){
        Mensaje mensaje = null;
        if(idCliente!=null){
            mensaje = ClienteDAO.eliminarCuenta(idCliente);
        }else{
             throw new WebApplicationException(Response.Status.BAD_REQUEST);           
        }
        
        return mensaje;
    }
}
