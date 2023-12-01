package ws;

import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import modelo.PromocionDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;

@Path("cupones")
public class PromocionWS {
    
    @Path("/obtenerListaCupones")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> obtenerListaCupones() {
        return PromocionDAO.obtenerPromocionesActivas();
    }
    
    @Path("/canjearCupon")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Mensaje canjearCupon(String json) {
        Gson gson = new Gson();
        Promocion promocion = gson.fromJson(json, Promocion.class);
        if (promocion != null && promocion.getCodigoPromocion() != null && !promocion.getCodigoPromocion().isEmpty()) {
            return PromocionDAO.canjearCupon(promocion);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
