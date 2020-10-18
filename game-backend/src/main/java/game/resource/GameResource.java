package game.resource;

//import javax.ws.rs.*;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@Path("game")
@Api(value = "game")
public class GameResource {

    private final Storage storage;

    @Inject
    public GameResource(final Storage storage) {
        super();
        this.storage = storage;
    }

    // récupérer les noms des maps existantes
    @GET
    @Path("api/v1/maps")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getMapsId(){
        return this.storage.getListMap()
                .stream().map(m -> m.getName())
                .collect(Collectors.toList());
    }

    // récupérer la map du nom {name}
    @GET
    @Path("api/v1/maps/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<MapResource> getMapFromName(@PathParam("name") final String name) throws StreamCorruptedException {
        Optional<MapResource> map = this.storage
                .getListMap()
                .stream()
                .filter(m -> m.getName().equals(name))
                .findFirst();
        if(map.isEmpty()){
            throw new StreamCorruptedException("There ain't no map with this name");
        }
        return map;
    }

    // poster une nouvelle map
    @POST
    @Path("api/v1/maps")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postMap(final MapResource m){
        final MapResource map = m;
        storage.addMap(map);
        return Response.status(Response.Status.OK).entity(map).build();
    }

}