/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Richard
 */
public class EmpresaDAO {
    
    public static List<Empresa> obtenerEmpresas(){
        List<Empresa> empresas = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try{
                empresas = conexionBD.selectList("empresa.obtenerEmpresas");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        return empresas;
    }
    
    
    public static List<Empresa> obtenerEmpresa(){
        List<Empresa> empresas = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if(conexionBD!=null){
            try {
                empresas = conexionBD.selectList("empresa.obtenerEmpresa");
            } catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        return empresas;
    }
    
    public static List<Empresa> obtenerEmpresaPorNombre(String nombreEmpresa){
        List<Empresa> empresas = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD!=null){
            try {
                empresas = conexionBD.selectList("empresa.obtenerEmpresaPorNombre", nombreEmpresa);
            } catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        
        return empresas;
    }
    
    public static Empresa obtenerEmpresaPorRepresentante(String representante){
        Empresa empresa = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD!=null){
            try {
                empresa = conexionBD.selectOne("empresa.obtenerEmpresaPorRepresentante", representante);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        return empresa;
    }
    
    public static Empresa obtenerEmpresaPorRFC(String rfc){
        Empresa empresa = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD!=null){
            try {
                empresa = conexionBD.selectOne("empresa.obtenerEmpresaPorRFC", rfc);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        
        return empresa;
    }
    
    
    public static Mensaje registrarEmpresa(Empresa empresa){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try {
                int filasAfectadas = conexionBD.insert("empresa.registrar", empresa);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    mensaje.setError(false);
                    mensaje.setMensaje("Empresa agregada con exito");
                }else{
                    mensaje.setMensaje("No se puede registrar la informacion de la empresa");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error "+ e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Por el momento no hay conexion a la base de datos, intentelo mas tarde");
        }
        
        
        return mensaje;
    }
    
    public static Mensaje editarEmpresa(Empresa empresa){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try {
                int filasAfectadas = conexionBD.update("empresa.editar", empresa);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    mensaje.setError(false);
                    mensaje.setMensaje("Empresa editada con exito");
                }else{
                    mensaje.setMensaje("No se puede editar la informacion de la empresa");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error "+ e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Por el momento no hay conexion a la base de datos, intentelo mas tarde");
        }
        
        return mensaje;
    }
      
    public static Mensaje eliminarEmpresa(Integer idEmpresa){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try {
                Empresa empresaExistente = conexionBD.selectOne("empresa.obtenerEmpresaPorId", idEmpresa);
                if(empresaExistente!=null){
                    int numeroFilasAfectadas = conexionBD.delete("empresa.eliminar", idEmpresa);
                    conexionBD.commit();
                    if(numeroFilasAfectadas > 0){
                        mensaje.setError(false);
                        mensaje.setMensaje("Empresa eliminada exitosamente de la base de datos");
                    }else{
                        mensaje.setMensaje("Lo sentimos, no se pudo eliminar la empresa de la base");
                    }
                }else{
                    mensaje.setMensaje("No hay ninguna empresa registrada en la base de datos con el ID que proporcionaste");
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
    
    public static Mensaje subirLogoEmpresa(Integer idEmpresa, byte[] logo){
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD!=null){
            try {
                Empresa empresaLogo = new Empresa();
                empresaLogo.setIdEmpresa(idEmpresa);
                empresaLogo.setLogoEmpresa(logo);
                int filasAfectadas = conexionBD.update("empresa.subirLogo", empresaLogo);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    mensaje.setError(false);
                    mensaje.setMensaje("Logo de la empresa subida correctamente");
                }else{
                    mensaje.setMensaje("Hubo un error al momento de guardar el logo de la empresa, revise su imagen");
                }
            } catch (Exception e) {
                mensaje.setMensaje("Error: "+ e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            mensaje.setMensaje("Lo sentimos por el momento no hay conexion con la base de datos, por favor intentelo mas tarde");
        }
        
        return mensaje;
    }
    
    
     public static Empresa obtenerLogo(int idEmpresa){
        Empresa empresa = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try {
                empresa = conexionBD.selectOne("empresa.obtenerLogo", idEmpresa);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
   
        }
        
        return empresa;
    }
     
     
     public static List<Empresa> obtenerNomEmpresaPorId(Integer idEmpresa){
        List<Empresa> empresas = null;
        SqlSession conexionBD = MyBatisUtil.getSession();

        if(conexionBD!=null){
            try {
                empresas = conexionBD.selectList("empresa.obtenerNomEmpresaPorId", idEmpresa);
            } catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }
        return empresas;
    }
}