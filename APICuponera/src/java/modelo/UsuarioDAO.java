package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.Mensaje;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class UsuarioDAO {
    
    public static List<Usuario> buscarCuentas(String filtro) {
    List<Usuario> cuentas = new ArrayList<>();
    SqlSession conexionBD = MyBatisUtil.getSession();
    if (conexionBD != null) {
        try {
            cuentas = conexionBD.selectList("usuario.buscarCuentas", filtro);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return cuentas;
}
    
    public static boolean existeUsuarioConUsername(String username) {
    boolean existe = false;
    SqlSession conexionBD = MyBatisUtil.getSession();
    if (conexionBD != null) {
        try {
            int count = conexionBD.selectOne("usuario.contarUsuariosConUsername", username);
            existe = count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return existe;
}



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
     public static Mensaje editarUsuario(Usuario usuario) {
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("usuario.editarUsuario", usuario);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("Usuario editado con éxito");
                } else {
                    msj.setMensaje("No se pudo editar el Usuario");
                }
            } catch (Exception e) {
                e.printStackTrace();
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("Por el momento no hay conexión para editar el Usuario");
        }
        return msj;
    }

    public static Mensaje eliminarUsuario(int idUsuario) {
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();

        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.delete("usuario.eliminarUsuario", idUsuario);
                conexionBD.commit();

                if (filasAfectadas > 0) {
                    msj.setError(false);
                    msj.setMensaje("Usuario eliminado con éxito");
                } else {
                    msj.setMensaje("No se pudo eliminar el Usuario");
                }
            } catch (Exception e) {
                e.printStackTrace();
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            msj.setMensaje("Por el momento no hay conexión para eliminar el Usuario");
        }
        return msj;
    }
}
