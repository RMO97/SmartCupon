package modelo;

import java.util.HashMap;
import modelo.pojo.Cliente;
import modelo.pojo.Mensaje;
import modelo.pojo.RespuestaLoginMovil;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Richard
 */
public class ClienteDAO {
       

     public static Cliente obtenerCliente(Integer idCliente){
         Cliente cliente = null;
         SqlSession conexionBD = MyBatisUtil.getSession();
         if(conexionBD!=null){
             try{ 
                 cliente = conexionBD.selectOne("cliente.obtenerClientePorId",idCliente);
             }catch(Exception e){
                e.getMessage();
            }finally{
                conexionBD.close();
            }
         }
         return cliente;
            
     }
     
    
     public static Mensaje registroCliente(String direccion, String nombre, String apellidoPaterno, String apellidoMaterno,
                                           String correoElectronico, String telefono, String fechaNacimiento, String password){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellidoMaterno(apellidoMaterno);
        cliente.setApellidoPaterno(apellidoPaterno);
        cliente.setCorreoElectronico(correoElectronico);
        cliente.setDireccion(direccion);
        cliente.setNumeroTelefono(telefono);
        cliente.setPassword(password);
        cliente.setFechaNacimiento(fechaNacimiento);
        SqlSession  conexionBD = MyBatisUtil.getSession();
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
     
    public static RespuestaLoginMovil edicionCliente(Integer idCliente,String direccion, String nombre, String apellidoPaterno, String apellidoMaterno,
                                           String correoElectronico, String telefono, String fechaNacimiento, String password){
        RespuestaLoginMovil mensaje = new RespuestaLoginMovil();
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("idCliente", idCliente);
        parametros.put("nombre", nombre);
        parametros.put("direccion",direccion);
        parametros.put("apellidoPaterno", apellidoPaterno);
        parametros.put("apellidoMaterno", apellidoMaterno);
        parametros.put("correoElectronico", correoElectronico);
        parametros.put("numeroTelefono", telefono);
        parametros.put("fechaNacimiento", fechaNacimiento);
        parametros.put("password", password);
        SqlSession conexionBD = MyBatisUtil.getSession();
        mensaje.setError(true);
        if(conexionBD!=null){
            try {
                Cliente clienteExistente = conexionBD.selectOne("cliente.obtenerClientePorId",idCliente);
                if(clienteExistente!=null){
                    int numeroFilasAfectadas = conexionBD.update("cliente.editar", parametros);
                    conexionBD.commit();
                    if(numeroFilasAfectadas > 0){
                        mensaje.setError(false);
                        mensaje.setMensaje("Cliente actualizado con exito");
                        mensaje.setCliente(clienteExistente);
                    }else{
                        mensaje.setMensaje("Lo sentimos no se pudo actualizar la informacion del cliente");
                    }
                }else{
                    mensaje.setMensaje("Error el id del cliente proporcionado no existe en la base de datos");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Lo sentimos de momento no hay conexion con la base de datos, intentelo mas tarde.");
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
