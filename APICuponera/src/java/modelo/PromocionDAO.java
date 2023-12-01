package modelo;


import java.util.ArrayList;
import java.util.List;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class PromocionDAO {

    
    public static List<Promocion> obtenerPromocionesActivas() {
        List<Promocion> promociones = new ArrayList<>();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                promociones = conexionBD.selectList("promocion.obtenerListaCupones");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return promociones;
    }
    
    public static Mensaje canjearCupon(Promocion promocion) {
        Mensaje mensaje = new Mensaje();
        mensaje.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();   
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.update("promocion.canjearCupon", promocion);
                conexionBD.commit();
                if (filasAfectadas > 0) {
                    mensaje.setError(false);
                    mensaje.setMensaje("Cupón canjeado con éxito");
                } else {
                    mensaje.setMensaje("No se pudo canjear el cupón");
                }
            } catch (Exception e) {
                e.printStackTrace();
                mensaje.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            mensaje.setMensaje("Por el momento no hay conexión para canjear el cupón");
        }
        return mensaje;
         
    public static List<Promocion> buscarPorNombre(String nombrePromocion){
        List<Promocion> promocion = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                promocion = conexionBD.selectList("promocion.buscarPorNombre", nombrePromocion);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("no hay conexion con la base de datos");
        }
      
        return promocion;
    }
    
    public static List<Promocion> buscarFechaInicio(String fechaDeInicioPromocion){
        List<Promocion> promocion = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                promocion = conexionBD.selectList("promocion.buscarFechaInicio", fechaDeInicioPromocion);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("no hay conexion con la base de datos");
        }
      
        return promocion;
    }
    
    public static List<Promocion> buscarFechaTermino(String fechaDeExpiracionPromocion){
        List<Promocion> promocion = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                promocion = conexionBD.selectList("promocion.buscarFechaTermino", fechaDeExpiracionPromocion);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            
            System.out.println("no hay conexion con la base de datos");
        }
      
        return promocion;
    }
    
    public static Mensaje registrarPromocion(Promocion promocion){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int filasAfectadas = conexionBD.insert("promocion.registrarPromocion", promocion);
                conexionBD.commit();
                if(filasAfectadas > 0){
                    msj.setError(false);
                    msj.setMensaje("Promocion agregado con exito");
                }else{
                    msj.setMensaje("No se puede registrar la informacion de la promocion enviada");
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
    
    public static Mensaje actualizarPromocion(Promocion promocion){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                Promocion promocionExiste = conexionBD.selectOne("promocion.buscarPorId", promocion.getIdPromocion());
                if(promocionExiste != null){
                    int FilasAfectadas = conexionBD.update("promocion.editarPromocion", promocion);
                    conexionBD.commit();
                    if(FilasAfectadas > 0){
                        msj.setError(false);
                        msj.setMensaje("Informacion de la promocion actualizada con exito");
                    }else{
                        msj.setMensaje("No se puede actualizar la informacion de la promocion enviada");
                    }
                }else{
                    msj.setMensaje("El id de la promocion no existe");
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
    
    public static Mensaje eliminarPromocion(Integer idPromocion){
        Mensaje msj = new Mensaje();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try{
                Promocion promocionExistente = conexionBD.selectOne("promocion.buscarPorId", idPromocion);
                if(promocionExistente !=null){
                    int numeroFilasAfectadas = conexionBD.delete("promocion.eliminarPromocion", idPromocion);
                    conexionBD.commit();
                    if(numeroFilasAfectadas > 0){
                        msj.setError(false);
                        msj.setMensaje("Informacion de la promocion borrada con exito");
                    }else{
                        msj.setError(true);
                        msj.setMensaje("Lo sentimos, no se pudo borrar la informacion de la promocion");
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
    
    public static Promocion obtenerFotografia(int idPromocion){
        Promocion promocion = null;
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                promocion = conexionBD.selectOne("promocion.obtenerFoto", idPromocion);
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                conexionBD.close();
            }
        }else{
            System.out.println("no hay conexion con la base de datos");
        }
        return promocion;
    }
    
    public static Mensaje subirFotografiaPromocion(int idPromocion, byte[] foto){
        Mensaje msj = new Mensaje();
        msj.setError(true);
        SqlSession conexionBD = MyBatisUtil.getSession();
        if(conexionBD != null){
            try {
                Promocion promocionFoto = new Promocion();
                promocionFoto.setIdPromocion(idPromocion);
                promocionFoto.setImagenPromocion(foto);
                int filasAfectadas = conexionBD.update("promocion.subirFoto", promocionFoto);
                conexionBD.commit();
                if(filasAfectadas>0){
                    msj.setError(false);
                    msj.setMensaje("Fotografia de la promocion guardada correctamente");
                }else{
                    msj.setMensaje("Hubo un error al intentar guardar la fotografia de la promocion, revise su imagen");
                }
            } catch (Exception e) {
                msj.setMensaje("Error: "+e.getMessage());
            }finally{
                conexionBD.close();
            }
        }else{
            msj.setMensaje("Lo sentimos por el momento no hay conexion para guardar la fotografia de la promocion");
        }
        return msj;
    }
}
