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

import game.model.MapFactory;
import game.model.MapResource;
import game.model.Storage;
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
    private final MapFactory mf;

    @Inject
    public GameResource(final Storage storage) {
        super();
        this.storage = storage;
        this.mf = new MapFactory();
    }

    // Route pour obtenir les noms (attribut primitif) des cartes disponibles
    @GET
    @Path("api/v1/maps")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getMapsId() {
        return this.storage.getListMap()
                .stream().map(m -> m.getName())
                .collect(Collectors.toList());
    }

    // Route pour obtenir une map depuis le nom
    @GET
    @Path("api/v1/maps/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<MapResource> getMapFromName(@PathParam("name") final String name) throws StreamCorruptedException {
        Optional<MapResource> map = this.storage
                .getListMap()
                .stream()
                .filter(m -> m.getName().equals(name))
                .findFirst();
        if (map.isEmpty()) {
            throw new StreamCorruptedException("There ain't no map with this name");
        }
        return map;
    }

    // Route pour ajouter une nouvelle map
    @POST
    @Path("api/v1/maps")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postMap(final MapResource m) {
        final MapResource map = m;
        storage.addMap(map);
        return Response.status(Response.Status.OK).entity(map).build();
    }

    // Route pour obtenir les topScores d'une map depuis l'id (il prend les cinq premiers scores de l'attribut scores)
    // GET api/v1/maps/topScores/{map_id} => {"topScores": [0,0,0,0,0]}
    /*@GET
    @Path("api/v1/maps/{map_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getTopScores() {

    }

    // Route pour ajouter le score d'un joueur sur une map
    // POST api/v1/maps/{map_id}/{player_name}/{score} => 200 OK
    @POST
    @Path("api/v1/maps/{map_name}/{player_name}/{score}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postScore() {

    }*/

    // Route pour obtenir une map générée aléatoirement par le back-end
    // GET api/v1/maps/random => {"map": {"id" : 45123, "name":"random", "scores" : [5,4,3,3,3,.....], "tabTiles":[...]}}
    @GET
    @Path("api/v1/maps/random")
    @Produces(MediaType.APPLICATION_JSON)
    public MapResource getRandomMap() {
        MapResource m = mf.newRandomMap();
        this.storage.addMap(m);
        return m;
    }

    // Route pour ajouter la liste des commandes faites par un joueur durant une partie
    // Body : {"player_name" : "Paul", "map_id" : 741, "undos" : [{"putCityBlock" : { "position" : 74, "typeCityBlock" : 1}},...]}
   /* @POST
    @Path("api/v1/replays")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postReplay() {

    }

    // Route pour récupérer l'ensemble des id et le score des replays d'un joueur sur une map donnée par son nom
    @GET
    @Path("api/v1/replays/{map_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getReplaysFromMap() {

    }

    // Route pour récupérer l'ensemble des commandes faites par un joueur depuis l'id d'un replay
    @GET
    @Path("api/v1/replays/{replay_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getCommandsFromReplay() {

    }
*/

}