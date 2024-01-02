/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriosmartcupon.modelo.DAO;

import clienteescritoriosmartcupon.modelo.ConexionHTTP;
import clienteescritoriosmartcupon.modelo.pojo.CodigoHTTP;
import clienteescritoriosmartcupon.modelo.pojo.Empresa;
import clienteescritoriosmartcupon.modelo.pojo.Mensaje;
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

/**
 *
 * @author Richard
 */
public class EmpresaDAO {
    
    public static List<Empresa> obtenerEmpresa(){
        List<Empresa> empresa = new ArrayList<>();
        String url = Constantes.URL_WS + "empresa/obtenerEmpresa";
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Type tipoListaEmpresa = new TypeToken<List<Empresa>>(){}.getType();
            Gson gson = new Gson();
            empresa = gson.fromJson(respuesta.getContenido(), tipoListaEmpresa);
        }
        return empresa;
    }    
    public static HashMap<String, Object> obtenerEmpresas(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URL_WS+"empresa/obtenerEmpresas";
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionGET(url);
        if(codigoRespuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            respuesta.put("error", false);
            Type tipoListaEmpresa = new TypeToken<List<Empresa>>(){}.getType();
            Gson gson = new Gson();
            List<Empresa> empresas = gson.fromJson(codigoRespuesta.getContenido(), tipoListaEmpresa);
            respuesta.put("empresas", empresas);
        }else{
            respuesta.put("error", true);
            respuesta.put("mensaje", "Hubo un error al obtener la informacion de las empresas, por favor intentelo mas tarde");
    }
        return respuesta;
   }
    public static Mensaje registrarEmpresa(Empresa empresa){
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS+"empresa/registrar";
        Gson gson = new Gson();
        String parametros = gson.toJson(empresa);
        CodigoHTTP respuesta = ConexionHTTP.peticionPOSTJSON(url, parametros);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            mensaje.setError(true);
            mensaje.setMensaje("Error en la peticion para registrar una nueva empresa");
        }
        
        return mensaje;
    }
    
    public static Mensaje editarEmpresa(Empresa empresa){
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS+"empresa/editar";
        Gson gson = new Gson();
        String parametros = gson.toJson(empresa);
        CodigoHTTP respuesta = ConexionHTTP.peticionPUTJSON(url, parametros);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            mensaje.setError(true);
            mensaje.setMensaje("Error en la peticion para editar una  empresa");
        }
        
        return mensaje;
    }
    
    public static Mensaje eliminarEmpresa(Integer idEmpresa){
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS+"empresa/eliminar";
        String parametros = String.format("idEmpresa=%s", idEmpresa);
        System.out.println(parametros);
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
    
    public static Mensaje subirLogoEmpresa(File logo, int idEmpresa){
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URL_WS+"empresa/subirLogo/"+idEmpresa;
         try {
            byte[] imagen = Files.readAllBytes(logo.toPath());
            CodigoHTTP codigoRespuesta = ConexionHTTP.peticionPUTImagen(url, imagen);
            if(codigoRespuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
                Gson gson = new Gson();
                mensaje = gson.fromJson(codigoRespuesta.getContenido(), Mensaje.class);
            }else{
                mensaje.setError(true);
                mensaje.setMensaje("Hubo un error al intentar subir el logo, por favor intentelo mas tarde.");
            }
        } catch (IOException ex) {
            mensaje.setError(true);
            mensaje.setMensaje("El archivo seleccionado no puede ser enviado para su almacenamiento");
        }
        
        return mensaje;
    }
    
    public static Empresa obtenerLogoEmpresa(int idEmpresa){
        Empresa empresa = null;
        String url = Constantes.URL_WS+"empresa/obtenerLogo/"+idEmpresa;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            empresa = gson.fromJson(respuesta.getContenido(), Empresa.class);
        }
        return empresa;
    }
}
