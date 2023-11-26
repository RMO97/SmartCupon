package modelo;

import modelo.pojo.Mensaje;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class UsuarioDAO {
    /*public static Usuario obtenerUsuario(int idUsuario){
        Usuario usuario = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD !=null){
            try {
                usuario = conexionBD.selectOne("usuario.obtenerUsuario", idUsuario);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        return usuario;
    }*/
    
    public static Mensaje registrarUsuario(Usuario usuario){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if (conexionBD!=null){
            try{
                int filasAfectadas = conexionBD.insert("usuario.registrarUsuario",usuario);
                conexionBD.commit();
                
                if (filasAfectadas>0){
                    msj.setError(false);
                    msj.setMensaje("Usuario agregado con exito");
                }else{
                    msj.setMensaje("No se pudo registrar Usuario");
                }
            }catch (Exception e){
                e.printStackTrace();
                msj.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Por el momento no hay conexion para guardar el Usuario");
        }
        return msj;
    }
    
    
}
