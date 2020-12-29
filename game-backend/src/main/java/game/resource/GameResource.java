package game.resource;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.model.Command;
import game.model.CommandCollector;
import game.model.MapFactory;
import game.model.MapResource;
import game.model.Score;
import game.model.Storage;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public Storage getStorage() {
        return storage;
    }

    public void resetMap() throws IOException {
        storage.resetMap();
    }

    /** Route pour obtenir les noms (attribut primitif) des cartes disponibles **/
    @GET
    @Path("api/v1/maps")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getMapsNames() {
        return this.storage.getMapsName();
    }

    /** Route pour obtenir une map depuis le nom **/
    @GET
    @Path("api/v1/maps/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public MapResource getMapFromName(@PathParam("name") final String name) {
        return this.storage.getMap(name);
    }

    /** Route pour ajouter une nouvelle map **/
    @POST
    @Path("api/v1/maps")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postMap(final MapResource m) throws IllegalArgumentException, IOException {
        final MapResource map = m;
        storage.addMap(map);
        return Response.status(Response.Status.OK).build();
    }

    /** Route pour obtenir les topScores d'une map depuis l'id (il prend les cinq premiers scores de l'attribut scores) **/
    @GET
    @Path("api/v1/topScores/{map_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Score> getTopScores(@PathParam("map_name") final String map_name) {
        final MapResource map = storage.getMap(map_name);
        return map.getTopScores();
    }

    /**
     * Les deux route suivantes sont commentées car nous nous sommes rendus comptes qu'un postScore s'accompagne forcement d'un postReplay
     **/

    // Route pour ajouter le score d'un joueur sur une map
    // POST api/v1/maps/{map_id}/{player_name}/{score} => 200 OK
    /*@POST
    @Path("api/v1/maps/{map_name}/{player_name}/{score}")
    public void postScore(@PathParam("map_name") final String map_name, @PathParam("player_name") final String player_name, @PathParam("score") final int score) throws StreamCorruptedException {
        this.storage.addScore(map_name, new Score(player_name, score));
    }*/

    // Route pour ajouter la liste des commandes faites par un joueur durant une partie
    // Body : {"player_name" : "Paul", "map_id" : 741, "undos" : [{"putCityBlock" : { "position" : 74, "typeCityBlock" : 1}},...]}
    /*@POST
    @Path("api/v1/replays/{map_name}/{player_name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void postReplay(@PathParam("map_name") final String map_name, @PathParam("player_name") final String player_name, final List<Command> commands) {
        CommandCollector cc = new CommandCollector(player_name, commands);
        storage.addCommand(map_name, cc);
    }*/

    /** Route pour obtenir une map générée aléatoirement par le back-end **/
    // GET api/v1/maps/random => {"map": {"id" : 45123, "name":"random", "scores" : [5,4,3,3,3,.....], "tabTiles":[...]}}
    @GET
    @Path("api/v1/maps/random")
    @Produces(MediaType.APPLICATION_JSON)
    public MapResource getRandomMap() throws IllegalArgumentException, IOException {
        final MapResource m = mf.newRandomMap();
        this.storage.addMap(m);
        return m;
    }

    /** Route permettant de poster les informations en fin de partie **/
    @POST
    @Path("api/v1/replays/{map_name}/{player_name}/{score}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postGame(@PathParam("map_name") final String map_name, @PathParam("player_name") final String player_name, @PathParam("score") final int score, final String commands) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        List<Command> c = new ArrayList<>();
        try {
            c = mapper.readValue(commands, new TypeReference<List<Command>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        /*
        try {
            storage.addGame(map_name, new CommandCollector(player_name, c), new Score(player_name, score));
        } catch (IOException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.valueOf(e.getMessage())).build();
        }
        */
        storage.addGame(map_name, new CommandCollector(player_name, c), new Score(player_name, score));
        return Response.status(Response.Status.OK).build();
    }

    /** Route pour récupérer l'ensemble des replays des joueurs sur une map donnée (retourne le nom des joueurs) **/
    @GET
    @Path("api/v1/replays/{map_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getPlayerFromMap(@PathParam("map_name") final String map_name) {
        return storage.getCommandCollectorFromMap(map_name);
    }

    /** Route pour récupérer l'ensemble des commandes faites par un joueur depuis son nom sur le nom de la map correspondante **/
    @GET
    @Path("api/v1/replays/{map_name}/{player_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Command> getPlayerCommandsFromMap(@PathParam("map_name") final String map_name, @PathParam("player_name") final String player_name) {
        return storage.getCommands(map_name, player_name);
    }


}
