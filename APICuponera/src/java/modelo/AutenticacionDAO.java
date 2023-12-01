/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;
import modelo.pojo.Cliente;
import modelo.pojo.RespuestaLoginEscritorio;
import modelo.pojo.RespuestaLoginMovil;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Richard
 */
public class AutenticacionDAO {
    
    public static RespuestaLoginEscritorio verificarSesionEscritorio(String username, String password){
    
        RespuestaLoginEscritorio respuesta = new RespuestaLoginEscritorio();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("username", username);
                parametros.put("password", password);
                Usuario usuario = conexionBD.selectOne("autenticacion.loginEscritorio", parametros);
                if(usuario != null){
                    respuesta.setError(false);
                    respuesta.setMensaje("Bienvenido(a) "+usuario.getNombre()+" al sistema cuponera");
                    respuesta.setUsuario(usuario);
                }else{
                    respuesta.setMensaje("Error: no existe ningun usuario registrado en la base de datos con los parametros proporcionados");
                }
                
            }catch(Exception e){
                respuesta.setMensaje("Error: " + e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.setMensaje("Error: Por el momento no hay conexion con la base de datos");
        }
       
        return respuesta;
    } 
    
    public static RespuestaLoginMovil verificarSesionMovil(String correoElectronico, String password){
        RespuestaLoginMovil respuesta = new RespuestaLoginMovil();
        respuesta.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try {
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("correoElectronico", correoElectronico);
                parametros.put("password", password);
                Cliente cliente = conexionBD.selectOne("autenticacion.loginMovil", parametros);
                if(cliente != null){
                    respuesta.setError(false);
                    respuesta.setMensaje("Bienvenido(a) "+cliente.getNombre()+" a cuponera");
                    respuesta.setCliente(cliente);
                }else{
                    respuesta.setMensaje("Error: no existe ningun cliente registrado en la base de datos con los parametros proporcionados");
                }
            } catch (Exception e) {
                respuesta.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.setMensaje("Error por el momento no hay conexion con la base de datos");
        }
        
        return respuesta;
    }
}
