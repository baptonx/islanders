package game.resource;

import com.google.common.net.MediaType;
import com.sun.research.ws.wadl.Response;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Map;

@Singleton
@Path("game")
@Api(value = "game")
public class GameResource {
    private MapResource maps;

    public GameResource() {
        super();
        maps = new MapResource();
    }

    @GET
    @Path("api/v1/maps")
    @Produces(MediaType.JSON_UTF_8)
    public Response getMaps(){

    }

}