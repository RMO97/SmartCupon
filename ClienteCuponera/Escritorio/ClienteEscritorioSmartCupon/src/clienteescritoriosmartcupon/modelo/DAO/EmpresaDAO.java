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
import java.net.HttpURLConnection;

/**
 *
 * @author Richard
 */
public class EmpresaDAO {
    
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
        CodigoHTTP codigoRespuesta = ConexionHTTP.peticionPUT(url, parametros);
        if(codigoRespuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            mensaje = gson.fromJson(codigoRespuesta.getContenido(), Mensaje.class);
        }else{
            mensaje.setError(true);
            mensaje.setMensaje("Error en la peticion para la edicion del paciente.");
        }
        
        return mensaje;
    }
}
