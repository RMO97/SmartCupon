/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriosmartcupon.modelo.DAO;

import clienteescritoriosmartcupon.modelo.ConexionHTTP;
import clienteescritoriosmartcupon.modelo.pojo.CodigoHTTP;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
import clienteescritoriosmartcupon.modelo.pojo.Sucursal;
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
public class SucursalDAO {
     public static List<Sucursal> buscarPorNombre(String nombreSucursal){
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"sucursal/buscarPorNombre";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaNombre = new TypeToken<List<Sucursal>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaNombre);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
    public static List<Sucursal> buscarPorDireccion(Integer idDireccion){
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"sucursal/buscarPorDireccion";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaDireccion = new TypeToken<List<Sucursal>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaDireccion);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
    public static List<Sucursal> obtenerPorEmpresa(Integer idEmpresa){
        List<Sucursal> sucursales = new ArrayList<>();
        String url = Constantes.URL_WS+"sucursal/buscarPorEmpresa";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoListaEmpresa = new TypeToken<List<Sucursal>>(){}.getType();
            sucursales = gson.fromJson(respuesta.getContenido(), tipoListaEmpresa);
            System.out.println(sucursales);
        }
        return sucursales;
    }
    
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
}
