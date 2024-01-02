package clienteescritoriosmartcupon.modelo.DAO;

import clienteescritoriosmartcupon.modelo.ConexionHTTP;
import clienteescritoriosmartcupon.modelo.pojo.CodigoHTTP;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Usuario;
import clienteescritoriosmartcupon.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class UsuarioDAO {

    public static HashMap<String, Object> obtenerUsuarios() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URL_WS + "usuarios/obtenerUsuarios";
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);

        if (codigoRespuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta.put("error", false);
            Type tipoListaUsuarios = new TypeToken<List<Usuario>>(){}.getType();
            Gson gson = new Gson();
            List<Usuario> usuario = gson.fromJson(codigoRespuesta.getContenido(), tipoListaUsuarios);
            respuesta.put("usuario", usuario);
        } else {
            respuesta.put("error", true);
            respuesta.put("mensaje", "Hubo un error al obtener la informacion de los usuarios, por favor inténtelo más tarde");
        }
        return respuesta;
    }

    public static Mensaje registrarUsuario(Usuario usuario) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "usuarios/registrarUsuario";
        Gson gson = new Gson();
        String parametros = gson.toJson(usuario);
        CodigoHTTP respuesta = ConexionHTTP.peticionPOSTJSON(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            msj.setError(true);
            msj.setMensaje("Error en la peticion para agregar la informacion del usuario");
        }
        return msj;
    }

    public static Mensaje editarUsuario(Usuario usuario) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "usuarios/editarUsuario";
        Gson gson = new Gson();
        String parametros = gson.toJson(usuario);
        CodigoHTTP respuesta = ConexionHTTP.peticionPUTJSON(url, parametros);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            msj.setError(true);
            msj.setMensaje("Error en la petición para editar el usuario");
        }
        return msj;
    }

    public static Mensaje eliminarUsuario(int idUsuario) {
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "usuarios/eliminarUsuario/" + idUsuario;
        CodigoHTTP respuesta = ConexionHTTP.peticionDELETE(url, url);

        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            msj.setError(true);
            msj.setMensaje("Error en la petición para eliminar el usuario");
        }
        return msj;
    }
}


