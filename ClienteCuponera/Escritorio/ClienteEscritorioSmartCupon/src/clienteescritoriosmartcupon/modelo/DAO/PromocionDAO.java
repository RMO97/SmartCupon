package clienteescritoriosmartcupon.modelo.DAO;

import clienteescritoriosmartcupon.modelo.ConexionHTTP;
import clienteescritoriosmartcupon.modelo.pojo.CodigoHTTP;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Promocion;
import clienteescritoriosmartcupon.modelo.pojo.Sucursal;
import clienteescritoriosmartcupon.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class PromocionDAO {
    public static List<Promocion> buscarPorNombre(String nombreSucursal){
        List<Promocion> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"promocion/buscarPorNombre"+nombreSucursal;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaNombre = new TypeToken<List<Promocion>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaNombre);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
    public static List<Promocion> buscarFechaInicio(String fechaDeInicioPromocion){
        List<Promocion> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"promocion/buscarPorFechaInicio"+fechaDeInicioPromocion;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaNombre = new TypeToken<List<Promocion>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaNombre);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
    public static List<Promocion> buscarFechaTermino(String fechaDeExpiracionPromocion){
        List<Promocion> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"promocion/buscarPorFechaTermino"+fechaDeExpiracionPromocion;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaNombre = new TypeToken<List<Promocion>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaNombre);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
    public static HashMap <String, Object> obtenerPromociones(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"promocion/obtenerPromociones";
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
        if(codigoRespuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            respuesta.put("error", false);
            Type tipoLista = new TypeToken<List<Promocion>>(){}.getType();
            Gson gson = new Gson();
            List<Promocion> promociones = gson.fromJson(codigoRespuesta.getContenido(), tipoLista);
            respuesta.put("promociones", promociones);
        }else{
            respuesta.put("error", true);
            respuesta.put("mensaje", "Hubo un error al obtener la informacion de las promociones, por favor intentelo mas tarde.");
        }
        return respuesta;
    }
    
    public static HashMap <String, Object> obtenerPromocionesEmpresa(Integer idEmpresa){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"promocion/obtenerPromocionesEmpresa/"+idEmpresa;
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
        if(codigoRespuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            respuesta.put("error", false);
            Type tipoLista = new TypeToken<List<Promocion>>(){}.getType();
            Gson gson = new Gson();
            List<Promocion> promociones = gson.fromJson(codigoRespuesta.getContenido(), tipoLista);
            respuesta.put("promociones", promociones);
        }else{
            respuesta.put("error", true);
            respuesta.put("mensaje", "Hubo un error al obtener la informacion de las promociones, por favor intentelo mas tarde.");
        }
        return respuesta;
    }
    
    public static List<Sucursal> buscarSucursalPromocion(){
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"promocion/buscarSucursalPromocion";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaNombre = new TypeToken<List<Sucursal>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaNombre);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
    public static Mensaje registrarPromocion(Promocion promocion){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "promocion/registrarPromocion";
        Gson gson = new Gson();
        String parametros = gson.toJson(promocion);
        CodigoHTTP respuesta = ConexionHTTP.peticionPOSTJSON(url, parametros);
        if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Error en la peticion para agregar la informacion de la promocion");
        }
        return msj;
    }
    
    public static Mensaje editarPromocion(Promocion promocion){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "promocion/actualizarPromocion";
        Gson gson = new Gson();
        String parametros = gson.toJson(promocion);
        CodigoHTTP respuesta = ConexionHTTP.peticionPUTJSON(url, parametros);
        if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Error en la peticion para modificar la informacion de la promocion");
        }
        return msj;
    }
    
    public static Mensaje subirFotografiaPromocion(File fotografia, int idPromocion){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS+"promocion/registrarFoto/"+idPromocion;
        try {
            byte[] imagen = Files.readAllBytes(fotografia.toPath());
            CodigoHTTP respuesta = ConexionHTTP.peticionPUTImagen(url, imagen);
            if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
            }else{
                msj.setError(true);
                msj.setMensaje("DAO Hubo un error al intentar subir la imagen, por favor intentelo mas tarde");
            }
        } catch (IOException e) {
            msj.setError(true);
            msj.setMensaje("El archivo seleccionado no puede ser enviado para su almacenamiento");
        }
        return msj;
    }
    
    public static Promocion obtenerFotografia(int idPromocion){
        Promocion promocion = null;
        String url = Constantes.URL_WS + "promocion/obtenerFoto/"+idPromocion;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if (respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            promocion = gson.fromJson(respuesta.getContenido(), Promocion.class);
        }
        return promocion;
    }
    
    public static Mensaje eliminarPromocion(Integer idPromocion){
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS+"promocion/eliminarPromocion"+ idPromocion;
        String parametros = String.format("idPromocion=%s", idPromocion);
        //System.out.println(parametros);
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionDELETE(url, parametros);
        if(codigoRespuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            mensaje = gson.fromJson(codigoRespuesta.getContenido(), Mensaje.class);
        }else{
            mensaje.setError(true);
            mensaje.setMensaje("Error en la peticion para eliminar la empresa.");
        }

        return mensaje;
    }
    
}
