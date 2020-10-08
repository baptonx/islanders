package game.resource;

import com.google.common.net.MediaType;
import com.sun.research.ws.wadl.Response;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Singleton
@Path("game")
@Api(value = "game")
public class GameResource {

    public GameResource() {
        super();
    }

    @GET
    @Path("api/v1/maps")
    @Consumes({MediaType.APPLICATION_XML_UTF_8})
    public Response getMaps()

}