package ws;

import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.ClienteDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import modelo.pojo.RespuestaLoginMovil;

/**
 *
 * @author Richard
 */

@Path("cliente")
public class ClienteWS {
    
    @Path("obtenerClientePorId/{idCliente}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Cliente obtenerCliente(@PathParam("idCliente")Integer idCliente){
        Cliente respuesta = null;
        if(idCliente != null){
            respuesta = ClienteDAO.obtenerCliente(idCliente);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);            
        }
        return respuesta;
    }
    
    @Path("registro")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registrarCliente(@FormParam("nombre")String nombre, @FormParam("apellidoPaterno") String apellidoPaterno, 
                                    @FormParam("apellidoMaterno")String apellidoMaterno, @FormParam("direccion") String direccion, 
                                    @FormParam("correoElectronico")String correoElectronico, @FormParam("numeroTelefono") String numeroTelefono, 
                                    @FormParam("fechaNacimiento") String fechaNacimiento, @FormParam("password") String password){
        
        Mensaje mensaje = null;
        if(!nombre.isEmpty()&& !apellidoMaterno.isEmpty()&& !apellidoPaterno.isEmpty()&& !direccion.isEmpty()&& !correoElectronico.isEmpty()&& !numeroTelefono.isEmpty()
                && !fechaNacimiento.isEmpty()&& !password.isEmpty()){
            mensaje = ClienteDAO.registroCliente(direccion, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, numeroTelefono, fechaNacimiento, password);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return mensaje;
    }
    
    @Path("editarCliente")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaLoginMovil edicionCliente(@FormParam("idCliente")Integer idCliente,@FormParam("nombre")String nombre, @FormParam("apellidoPaterno") String apellidoPaterno, 
                                    @FormParam("apellidoMaterno")String apellidoMaterno, @FormParam("direccion") String direccion, 
                                    @FormParam("correoElectronico")String correoElectronico, @FormParam("numeroTelefono") String numeroTelefono, 
                                    @FormParam("fechaNacimiento") String fechaNacimiento, @FormParam("password") String password){
        RespuestaLoginMovil mensaje = null;
        if(idCliente!=null&&!nombre.isEmpty()&& !apellidoMaterno.isEmpty()&& !apellidoPaterno.isEmpty()&& !direccion.isEmpty()&& !correoElectronico.isEmpty()&& !numeroTelefono.isEmpty()
                && !fechaNacimiento.isEmpty()&& !password.isEmpty()){
            mensaje = ClienteDAO.edicionCliente(idCliente, direccion, nombre, apellidoPaterno, apellidoMaterno, correoElectronico, numeroTelefono, fechaNacimiento, password);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return mensaje;
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
