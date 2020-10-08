package game.resource;

//import javax.ws.rs.*;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
        this.storage = new Storage();
    }

    @GET
    @Path("api/v1/maps")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMapsId(){
        final List<Integer> ids = new ArrayList<Integer>();
        this.storage.getListMap()
                .stream()
                .forEach(elt -> ids.add(elt.getId()));
        return Response.status(Response.Status.OK).entity(ids).build();
    }

    @POST
    @Path("api/v1/maps")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setMaps(MapResource m){
        final MapResource map = m;
        storage.addMap(map);
        return Response.status(Response.Status.OK).entity(map).build();
    }

}