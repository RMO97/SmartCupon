package clienteescritoriosmartcupon.modelo.DAO;

import clienteescritoriosmartcupon.modelo.ConexionHTTP;
import clienteescritoriosmartcupon.modelo.pojo.CodigoHTTP;
import clienteescritoriosmartcupon.modelo.pojo.Empresa;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Sucursal;
import clienteescritoriosmartcupon.modelo.pojo.Usuario;
import clienteescritoriosmartcupon.utils.Constantes;
import clienteescritoriosmartcupon.utils.Utilidades;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SucursalDAO {
     public static List<Sucursal> buscarPorNombre(String nombreSucursal){
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"sucursal/buscarPorNombre"+ nombreSucursal;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaNombre = new TypeToken<List<Sucursal>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaNombre);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
    public static List<Sucursal> buscarPorDireccion(String direccion){
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"sucursal/buscarPorDireccion"+ direccion;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaDireccion = new TypeToken<List<Sucursal>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaDireccion);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
    public static HashMap <String, Object> obtenerPorEmpresa(Integer idEmpresa){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"sucursal/buscarPorEmpresa"+idEmpresa;
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
        if(codigoRespuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            respuesta.put("error", false);
            Type tipoListaEmpresa = new TypeToken<List<Sucursal>>(){}.getType();
            Gson gson = new Gson();
            List<Sucursal> sucursales = gson.fromJson(codigoRespuesta.getContenido(), tipoListaEmpresa);
            respuesta.put("sucursales", sucursales);
        }else{
            respuesta.put("error", true);
            respuesta.put("mensaje", "Hubo un error al obtener la informacion de las sucursales, por favor intentelo mas tarde.");
        }
        return respuesta;
    }
    
    public static HashMap <String, Object> obtenerPorIdSucursal(Integer idSucursal){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"sucursal/buscarPorId"+idSucursal;
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
        if(codigoRespuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            respuesta.put("error", false);
            Type tipoListaEmpresa = new TypeToken<List<Sucursal>>(){}.getType();
            Gson gson = new Gson();
            List<Sucursal> sucursales = gson.fromJson(codigoRespuesta.getContenido(), tipoListaEmpresa);
            respuesta.put("sucursales", sucursales);
        }else{
            respuesta.put("error", true);
            respuesta.put("mensaje", "Hubo un error al obtener la informacion de las sucursales, por favor intentelo mas tarde.");
        }
        return respuesta;
    }
    
    public static HashMap <String, Object> obtenerEmpresaUsuario(Integer idEmpresa){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"sucursal/buscarEmpresaUsuario"+idEmpresa;
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
        if(codigoRespuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            respuesta.put("error", false);
            Type tipoListaEmpresa = new TypeToken<List<Usuario>>(){}.getType();
            Gson gson = new Gson();
            List<Usuario> empresas = gson.fromJson(codigoRespuesta.getContenido(), tipoListaEmpresa);
            respuesta.put("empresas", empresas);
        }else{
            respuesta.put("error", true);
            respuesta.put("mensaje", "Hubo un error al obtener la informacion de las empresas, por favor intentelo mas tarde.");
        }
        return respuesta;
    }
    
    public static HashMap <String, Object> obtenerSucursales(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"sucursal/obtenerSucursales";
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
        if(codigoRespuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            respuesta.put("error", false);
            Type tipoListaEmpresa = new TypeToken<List<Sucursal>>(){}.getType();
            Gson gson = new Gson();
            List<Sucursal> sucursales = gson.fromJson(codigoRespuesta.getContenido(), tipoListaEmpresa);
            respuesta.put("sucursales", sucursales);
        }else{
            respuesta.put("error", true);
            respuesta.put("mensaje", "Hubo un error al obtener la informacion de las sucursales, por favor intentelo mas tarde.");
        }
        return respuesta;
    }
    
    public static List<Empresa> buscarEmpresa(){
        List<Empresa> empresas = new ArrayList<>();
        String url = Constantes.URL_WS+"sucursal/obtenerEmpresas";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Empresa>>(){}.getType();
            empresas = gson.fromJson(respuesta.getContenido(), tipoLista);
            System.out.println(empresas);
        }
        return empresas;
    }
    /*
    public static HashMap <String, Object> obtenerEmpresas(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"sucursal/obtenerEmpresas";
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
        if(codigoRespuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            respuesta.put("error", false);
            Type tipoListaEmpresa = new TypeToken<List<Empresa>>(){}.getType();
            Gson gson = new Gson();
            List<Empresa> empresas = gson.fromJson(codigoRespuesta.getContenido(), tipoListaEmpresa);
            respuesta.put("sucursales", empresas);
        }else{
            respuesta.put("error", true);
            respuesta.put("mensaje", "Hubo un error al obtener la informacion de las empresas, por favor intentelo mas tarde.");
        }
        return respuesta;
    }*/
    
    public static Mensaje registrarSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "sucursal/registrarSucursal";
        Gson gson = new Gson();
        String parametros = gson.toJson(sucursal);
        CodigoHTTP respuesta = ConexionHTTP.peticionPOSTJSON(url, parametros);
        if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Error en la peticion para agregar la informacion de la sucursal");
        }
        return msj;
    }
    
    public static Mensaje editarSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "sucursal/actualizarSucursal";
        Gson gson = new Gson();
        String parametros = gson.toJson(sucursal);
        CodigoHTTP respuesta = ConexionHTTP.peticionPUTJSON(url, parametros);
        if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Error en la peticion para modificar la informacion de la sucursal");
        }
        return msj;
    }
    
    public static Mensaje editarPromocionSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "sucursal/actualizarPromocionSucursal";
        Gson gson = new Gson();
        String parametros = gson.toJson(sucursal);
        CodigoHTTP respuesta = ConexionHTTP.peticionPUTJSON(url, parametros);
        if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Error en la peticion para modificar la informacion de la sucursal");
        }
        return msj;
    }
    
    public static Mensaje editarEmpresaSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        String url = Constantes.URL_WS + "sucursal/actualizarEmpresaSucursal";
        Gson gson = new Gson();
        String parametros = gson.toJson(sucursal);
        CodigoHTTP respuesta = ConexionHTTP.peticionPUTJSON(url, parametros);
        if(respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK){
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setMensaje("Error en la peticion para modificar la informacion de la sucursal");
        }
        return msj;
    }
    
    public static Mensaje eliminarSucursal(Integer idSucursal){
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS+"sucursal/eliminarSucursal"+ idSucursal;
        String parametros = String.format("idSucursal=%s", idSucursal);
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
