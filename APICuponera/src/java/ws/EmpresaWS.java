/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.EmpresaDAO;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;

/**
 *
 * @author Richard
 */

@Path("empresa")
public class EmpresaWS {
    
    @Path("obtenerEmpresas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> obtenerEmpresas(){
        List<Empresa> respuesta = null;
        respuesta = EmpresaDAO.obtenerEmpresas();
        return respuesta;
    }
    
    @Path("obtenerEmpresa")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> obtenerEmpresa(){
        List<Empresa> respuesta = null;
        return respuesta = EmpresaDAO.obtenerEmpresa();
    }
    
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje registroEmpresa(String json){
        Gson gson = new Gson();
        Empresa empresa = gson.fromJson(json, Empresa.class);
        if(empresa != null && empresa.getDireccion()!=null){
            return EmpresaDAO.registrarEmpresa(empresa);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
    }
    
    
    @Path("obtenerEmpresaPorNombre/{nombreEmpresa}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> obtenerEmpresaPorNombre(@PathParam("nombreEmpresa") String nombreEmpresa){
        List<Empresa> respuesta = null;
        if(nombreEmpresa != null && !nombreEmpresa.isEmpty()){
            respuesta = EmpresaDAO.obtenerEmpresaPorNombre(nombreEmpresa);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
        return respuesta;
    
    }
    
    @Path("obtenerEmpresaPorRepresentante/{representante}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa obtenerEmpresaPorRepresentante(@PathParam("representante") String representante){
        Empresa respuesta = null;
        if(representante !=null && !representante.isEmpty()){
            respuesta = EmpresaDAO.obtenerEmpresaPorRepresentante(representante);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
        return respuesta;
    }
    
    
    @Path("obtenerEmpresaPorRFC/{rfc}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa obtenerEmpresaPorRFC(@PathParam("rfc") String rfc){
        Empresa respuesta = null;
        if(rfc !=null && !rfc.isEmpty()){
            respuesta = EmpresaDAO.obtenerEmpresaPorRFC(rfc);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);            
        }
        
        return respuesta;
    }
    
    
    @Path("editar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje editarEmpresa(String json){
        Gson gson = new Gson();
        Empresa empresa = gson.fromJson(json, Empresa.class);
        if(empresa !=null && empresa.getIdEmpresa()!=null && empresa.getIdEmpresa()>0){
            return EmpresaDAO.editarEmpresa(empresa);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);            
        }
        
    }
    @Path("eliminar")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarEmpresa(@FormParam("idEmpresa")Integer idEmpresa){
        Mensaje respuesta = null;
        if(idEmpresa!=null){
            respuesta = EmpresaDAO.eliminarEmpresa(idEmpresa);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);                        
        }
        
        return respuesta;
    }
    
    @Path("subirLogo/{idEmpresa}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje subirLogoEmpresa(@PathParam("idEmpresa") Integer idEmpresa, byte[]logo){
        Mensaje mensaje = null;
        if(idEmpresa!= null && idEmpresa>0 && logo !=null){
            mensaje = EmpresaDAO.subirLogoEmpresa(idEmpresa, logo);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);                                    
        }
        
        return mensaje;
    }
    
    
    @Path("obtenerLogo/{idEmpresa}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa obtenerLogo(@PathParam("idEmpresa") Integer idEmpresa){
        Empresa empresa = null;
        if(idEmpresa != null && idEmpresa > 0){
            empresa = EmpresaDAO.obtenerLogo(idEmpresa);
        }else{
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return empresa;
    }
    
}
