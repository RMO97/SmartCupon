/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritoriosmartcupon.modelo.DAO;

import clienteescritoriosmartcupon.modelo.ConexionHTTP;
import clienteescritoriosmartcupon.modelo.pojo.CodigoHTTP;
import clienteescritoriosmartcupon.modelo.pojo.RespuestaLogin;
import clienteescritoriosmartcupon.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/**
 *
 * @author Richard
 */
public class InicioSesionDAO {
    public static RespuestaLogin validarSesion(String username, String password){
        RespuestaLogin respuesta = new RespuestaLogin();
        String url = Constantes.URL_WS+"autenticacion/loginEscritorio";
        String parametros = String.format("username=%s&password=%s", username, password);

        CodigoHTTP respuestaConexion = ConexionHTTP.peticionPOST(url, parametros);
        if(respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaConexion.getContenido(), RespuestaLogin.class);
        }else{
            respuesta.setError(true);
            respuesta.setContenido("Hubo una error en la peticion, por favor intentelo mas tarde");
        }
        return respuesta;
    }
}
