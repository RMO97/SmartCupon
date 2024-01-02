package ws;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import modelo.UsuarioDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Usuario;

@Path("usuarios")
public class UsuariosWS {

    @GET
    @Path("obtenerUsuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> buscarPorNombre() {
        List<Usuario> usuarios = UsuarioDAO.obtenerUsuarios();
        return usuarios;
    }

    @Path("/buscarCuentas")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Usuario> buscarCuentas(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        if (jsonObject.has("filtro")) {
            String filtro = jsonObject.get("filtro").getAsString();
            return UsuarioDAO.buscarCuentas(filtro);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @Path("/registrarUsuario")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registrarUsuario(String json) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(json, Usuario.class);

        if (UsuarioDAO.existeUsuarioConUsername(usuario.getUsername())) {
            Mensaje mensajeError = new Mensaje();
            mensajeError.setError(true);
            mensajeError.setMensaje("Ya existe un usuario con ese username");
            return mensajeError;
        }

        if (usuario != null) {
            return UsuarioDAO.registrarUsuario(usuario);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @Path("/editarUsuario")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje editarUsuario(String json) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(json, Usuario.class);
        if (usuario != null && usuario.getIdUsuario() != null) {
            return UsuarioDAO.editarUsuario(usuario);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @Path("/eliminarUsuario/{idUsuario}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarUsuario(@PathParam("idUsuario") int idUsuario) {
        if (idUsuario > 0) {
            return UsuarioDAO.eliminarUsuario(idUsuario);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
