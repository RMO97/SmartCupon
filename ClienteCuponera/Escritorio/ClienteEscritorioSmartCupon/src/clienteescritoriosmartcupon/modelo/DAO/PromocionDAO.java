/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriosmartcupon.modelo.DAO;

import clienteescritoriosmartcupon.modelo.ConexionHTTP;
import clienteescritoriosmartcupon.modelo.pojo.CodigoHTTP;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Promocion;
import clienteescritoriosmartcupon.utils.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Richard
 */
public class PromocionDAO {
    public static List<Promocion> buscarPorNombre(String nombreSucursal){
        List<Promocion> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"promocion/buscarPorNombre";
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
        String url = Constantes.URL_WS+"promocion/buscarPorFechaInicio";
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
        String url = Constantes.URL_WS+"promocion/buscarPorFechaTermino";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaNombre = new TypeToken<List<Promocion>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaNombre);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
    public static Mensaje registrarSucursal(Promocion promocion){
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
    
    public static Mensaje editarSucursal(Promocion promocion){
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
    
}
