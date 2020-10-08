package game.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Singleton
@Path("game")
@Api(value = "game")
public class GameResource {

    private Storage storage;

    public GameResource() {
        super();
        this.listMaps = new ArrayList<MapResource>();
    }

    @GET
    @Path("api/v1/maps")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMaps(){
        final List<String> names;
        Arrays
                .stream(new ArrayList[]{listMaps})
                .
    }

    @POST
    @Path("api/v1/maps")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setMaps(){
        
    }

}