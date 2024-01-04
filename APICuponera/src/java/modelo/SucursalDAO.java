package modelo;

import java.util.List;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import modelo.pojo.Sucursal;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class SucursalDAO {
    
    public static List <Sucursal> obtenerPorPromocion (int idPromocion){
        List<Sucursal> sucursal = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                sucursal = conexionBD.selectList("sucursal.buscarPorPromocion", idPromocion);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("errorDAO");
        }
      
        return sucursal;
    }
    public static List<Sucursal> buscarPorNombre(String nombreSucursal){
        List<Sucursal> sucursal = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                sucursal = conexionBD.selectList("sucursal.buscarPorNombre", nombreSucursal);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("errorDAO");
        }
      
        return sucursal;
    }
    
    public static List<Sucursal> buscarPorDireccion(String direccion){
        List<Sucursal> sucursal = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                sucursal = conexionBD.selectList("sucursal.buscarPorDireccion", direccion);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("errorDAO");
        }
      
        return sucursal;
    }
    
    public static List<Sucursal> obtenerPorEmpresa(Integer idEmpresa){
        List<Sucursal> sucursal = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                sucursal = conexionBD.selectList("sucursal.obtenerPorEmpresa", idEmpresa);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("errorDAO");
        }
      
        return sucursal;
    }
    
    public static List<Sucursal> obtenerPorID(Integer idSucursal){
        List<Sucursal> sucursal = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                sucursal = conexionBD.selectList("sucursal.buscarPorId", idSucursal);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("errorDAO");
        }
      
        return sucursal;
    }
    
    public static List<Sucursal> obtenerSucursales(){
        List<Sucursal> sucursal = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                sucursal = conexionBD.selectList("sucursal.buscarSucursal");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("errorDAO");
        }
      
        return sucursal;
    }
    
    public static List<Empresa> obtenerEmpresas(){
        List<Empresa> empresa = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                empresa = conexionBD.selectList("sucursal.obtenerEmpresas");
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("errorDAO");
        }
      
        return empresa;
    }
    
    public static List<Usuario> buscarEmpresaUsuario(Integer idEmpresa){
        List<Usuario> usuario = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                usuario = conexionBD.selectList("sucursal.buscarEmpresaUsuario", idEmpresa);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("errorDAO");
        }
      
        return usuario;
    }
    
    public static Mensaje registrarSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.insert("sucursal.registrarSucursal", sucursal);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("Sucursal agregado con exito");
                }else{
                    msj.setMensaje("No se puede registrar la informacion de la sucursal enviado");
                }
            }catch (Exception e){
                e.printStackTrace();
                msj.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Por el momento no hay conexion con la base de datos.");
        }
        
        
        return msj;
    }
    
    public static Mensaje actualizarSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                Sucursal sucursalExiste = conexionBD.selectOne("sucursal.buscarPorId", sucursal.getIdSucursal());
                if(sucursalExiste != null){
                    int FilasAfectadas = conexionBD.update("sucursal.editarSucursal", sucursal);
                    conexionBD.commit();
                    if(FilasAfectadas > 0){
                        msj.setError(false);
                        msj.setMensaje("Inoformacion de sucursal actualizado con exito");
                    }else{
                        msj.setMensaje("No se puede actualizar la informacion de la sucursal enviado");
                    }
                }else{
                    msj.setMensaje("El id de la sucursal no existe");
                }
            }catch (Exception e){
                e.printStackTrace();
                msj.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }   
        }else{
            msj.setMensaje("Por el momento no hay conexion con la base de datos.");
        }
        return msj;
    }
    public static Mensaje editarPromocionSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                Sucursal sucursalExiste = conexionBD.selectOne("sucursal.buscarPorId", sucursal.getIdSucursal());
                if(sucursalExiste != null){
                    int FilasAfectadas = conexionBD.update("sucursal.editarPromocionSucursal", sucursal);
                    conexionBD.commit();
                    if(FilasAfectadas > 0){
                        msj.setError(false);
                        msj.setMensaje("Informacion de sucursal actualizada con exito");
                    }else{
                        msj.setMensaje("No se puede actualizar la informacion de la sucursal enviado");
                    }
                }else{
                    msj.setMensaje("El id de la sucursal no existe");
                }
            }catch (Exception e){
                e.printStackTrace();
                msj.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }   
        }else{
            msj.setMensaje("Por el momento no hay conexion con la base de datos.");
        }
        return msj;
    }
    
    public static Mensaje editarEmpresaSucursal(Sucursal sucursal){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                Sucursal sucursalExiste = conexionBD.selectOne("sucursal.buscarPorId", sucursal.getIdSucursal());
                if(sucursalExiste != null){
                    int FilasAfectadas = conexionBD.update("sucursal.editarEmpresaSucursal", sucursal);
                    conexionBD.commit();
                    if(FilasAfectadas > 0){
                        msj.setError(false);
                        msj.setMensaje("Informacion de sucursal actualizada con exito");
                    }else{
                        msj.setMensaje("No se puede actualizar la informacion de la sucursal enviado");
                    }
                }else{
                    msj.setMensaje("El id de la sucursal no existe");
                }
            }catch (Exception e){
                e.printStackTrace();
                msj.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }   
        }else{
            msj.setMensaje("Por el momento no hay conexion con la base de datos.");
        }
        return msj;
    }
    
    public static Mensaje eliminarSucursal(Integer idSucursal){
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                Sucursal sucursalExistente = conexionBD.selectOne("sucursal.buscarPorId", idSucursal);
                if(sucursalExistente !=null){
                    int numeroFilasAfectadas = conexionBD.delete("sucursal.eliminarSucursal", idSucursal);
                    conexionBD.commit();
                    if(numeroFilasAfectadas > 0){
                        msj.setError(false);
                        msj.setMensaje("Informacion de sucursal borrada con exito");
                    }else{
                        msj.setError(true);
                        msj.setMensaje("Lo sentimos, no se pudo borrar la informacion de la sucursal");
                    }
                }else{
                    msj.setError(true);
                    msj.setMensaje("El identificador a eliminar no existe");
                }
                
            }catch (Exception e){
                msj.setError(true);
                msj.setMensaje("Error"+e.getMessage());
            }finally{
                conexionBD.close();
            }   
        }else{
            msj.setError(true);
            msj.setMensaje("Por el momento no hay conexion con la base de datos.");
        
        }
        return msj;
    }
}
