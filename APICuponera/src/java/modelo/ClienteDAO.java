package modelo;

import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Richard
 */
public class ClienteDAO {
    
    public static Mensaje registrarCliente(Cliente cliente){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try {
                int filasAfectadas = conexionBD.insert("cliente.registrar", cliente);
                conexionBD.commit();
                if(filasAfectadas>0){
                    mensaje.setError(false);
                    mensaje.setMensaje("Cuenta registrada con exito");
                }else{
                    mensaje.setMensaje("No se pudo registrar la cuenta");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Por el momento no hay conexion al base de datos, intentelo mas tarde");
        }
        
        return mensaje;
    }
    
    public static Mensaje editarCliente(Cliente cliente){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try {
                int filasAfectadas = conexionBD.insert("cliente.editar", cliente);
                conexionBD.commit();
                if(filasAfectadas>0){
                    mensaje.setError(false);
                    mensaje.setMensaje("Cuenta editada con exito");
                }else{
                    mensaje.setMensaje("No se pudo editar la cuenta");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Por el momento no hay conexion al base de datos, intentelo mas tarde");
        }
        
        return mensaje;
    }
    
    public static Mensaje eliminarCuenta(Integer idCliente){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try {
                Cliente clienteExistente = conexionBD.selectOne("cliente.obtenerClientePorId", idCliente);
                if(clienteExistente!=null){
                    int numeroFilasAfectadas = conexionBD.delete("cliente.eliminar", idCliente);
                    conexionBD.commit();
                    if(numeroFilasAfectadas > 0){
                        mensaje.setError(false);
                        mensaje.setMensaje("Cuenta eliminada con exito");
                    }else{
                        mensaje.setMensaje("Lo sentimos, no se pudo eliminar tu cuenta, intentalo mas tarde");
                    }
                }else{
                    mensaje.setMensaje("No hay ninguna cuenta registrada en la base de datos con el ID que proporcionaste");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: " +e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Lo sentimos, por el momento no hay conexion a la base de datos");
        }
        return mensaje;
        
    }
}
