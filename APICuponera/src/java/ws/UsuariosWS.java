package ws;

import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import modelo.UsuarioDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Usuario;

@Path ("usuarios")
public class UsuariosWS {
    
    @Context
    private UriInfo context;
    @GET
    @Path("prueba")
    @Produces(MediaType.APPLICATION_JSON)
    public String prueba (){
        return "hola mundo desde pacientes";
    }
    @Path ("/registrarUsuario")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarUsuario(String json){
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(json, Usuario.class);
        if (usuario !=null){
            return UsuarioDAO.registrarUsuario(usuario);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
          
    }
    
    
}
