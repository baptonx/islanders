
package game.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.hanleyt.JerseyExtension;


import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import game.model.*;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class GameResourceTest {
    GameResource g;
    MapFactory mf;

    static {
        System.setProperty("jersey.config.test.container.port", "0");
    }

//	static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());

    @SuppressWarnings("unused")
    @RegisterExtension
    JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

    Application configureJersey() {
        Storage storage = null;
        try {
            storage = new Storage("src/main/java/game/data/mapsTest.json");
            g = new GameResource(storage);
            g.resetMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // data = Mockito.mock(Storage.class);
        return new ResourceConfig(GameResource.class)
                .register(MyExceptionMapper.class)
                .register(JacksonFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(g.getStorage()).to(Storage.class);
                    }
                });
    }

    //	<T> T LogJSONAndUnmarshallValue(final Response res, final Class<T> classToRead) {
//		res.bufferEntity();
//		final String json = res.readEntity(String.class);
//		log.log(Level.INFO, "JSON received: " + json);
//		final T obj = res.readEntity(classToRead);
//		res.close();
//		return obj;
//	}
    @BeforeEach
    void setUp() {
        mf = new MapFactory();
    }

    /*Test add map to back-end*/
    @Test
    void postMap(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        String name = maptest.getName();
        final Response res = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        //Exception map double
        final Response resMapDouble = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), resMapDouble.getStatus());
    }

    /* Getting a list of available maps*/
    @Test
    void getMapsNames(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        // add map
        final Response resPost = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        // get id of maps
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), resGet.getStatus());
        final List<String> names = resGet.readEntity(new GenericType<>() {
        });
        assertEquals(maptest.getName(), names.get(0));
    }

    /*Test get map from name*/
    @Test
    void getMapFromName(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        final Response resPost = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        // get map
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/maps/" + maptest.getName())
                .request()
                .get();
        MapResource resMap = resGet.readEntity(MapResource.class);
        assertEquals(maptest, resMap);
        // get false map
        final Response resGetFalseMap = client
                .target(baseUri)
                .path("game/api/v1/maps/nameOfFalseMap")
                .request()
                .get();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), resGetFalseMap.getStatus());
    }

   /* @Test
    void getMapFromNameException(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        assertThrows(StreamCorruptedException.class, () -> {
            client.target(baseUri)
                    .path("game/api/v1/maps/" + maptest.getName())
                    .request()
                    .get();
        });

    }*/

    /*Test get Random Map*/
    @Test
    void getRandomMap(final Client client, final URI baseUri) {
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/maps/random")
                .request()
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), resGet.getStatus());

        MapResource resMap = resGet.readEntity(MapResource.class);
        assertEquals(MapResource.class, resMap.getClass());
        assertTrue(resMap.getTabTiles().length > 0);
        assertTrue(resMap.getCommandsCollectors().size() == 0);
        assertTrue(resMap.getScores().size() == 0);
    }

    /*Test get Top scores of a map. Warning just the 5 best scores are returned !!!*/
    @Test
    void getTopScores(final Client client, final URI baseUri) {
        //add map with a list of score
        List<Score> scores = Stream.generate(Score::new).limit(10).collect(toList());
        MapResource maptest = mf.newRandomMap();
        maptest.setScores(scores);
        final Response resPost = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        //get the score of the mapTest
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/topScores/" + maptest.getName())
                .request()
                .get();
        final List<Score> resScore = resGet.readEntity(new GenericType<>() {
        });

        //Get just the 5 best scores
        scores.sort(new ScoreComparator());
        List<Score> topScores = scores.stream().limit(5).collect(Collectors.toList());
        assertEquals(topScores, resScore);
    }


    @Test
    void postGame(final Client client, final URI baseUri) {

        MapResource maptest = mf.newRandomMap();
        final Response resMap = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        assertEquals(Response.Status.OK.getStatusCode(), resMap.getStatus());


        List<Command> commands = new ArrayList<>();
        commands.add(new MoveCityBlock(0, 1));
        commands.add(new PutCityBlock(1, 2));
        commands.add(new MoveCityBlock(2, 3));

        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        String json = "[";
        try {
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(0));
            json += ",";
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(1));
            json += ",";
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(2));
            json += "]";
            //json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);

        final Response res = client
                .target(baseUri)
                .path("game/api/v1/replays/" + maptest.getName() + "/Paul/1000")
                .request()
                .post(Entity.json(json));
        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        //Exception name not unique between score and command
        final Response resUnicity = client
                .target(baseUri)
                .path("game/api/v1/rep  lays/" + maptest.getName() + "/Paul/1000")
                .request()
                .post(Entity.json(json));
    }


    @Test
    void getPlayerCommandsFromMap(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        final Response resMap = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        assertEquals(Response.Status.OK.getStatusCode(), resMap.getStatus());


        List<Command> commands = new ArrayList<>();
        commands.add(new MoveCityBlock(0, 1));
        commands.add(new PutCityBlock(1, 2));
        commands.add(new MoveCityBlock(2, 3));

        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        String json = "[";
        try {
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(0));
            json += ",";
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(1));
            json += ",";
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(2));
            json += "]";
            //json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        final Response res = client
                .target(baseUri)
                .path("game/api/v1/replays/" + maptest.getName() + "/Paul/1000")
                .request()
                .post(Entity.json(json));
        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/replays/" + maptest.getName() + "/Paul")
                .request()
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), resGet.getStatus());
        final List<Command> resCommand = resGet.readEntity(new GenericType<>() {
        });
        assertEquals(commands, resCommand);


        //Exception for a unkown player
        final Response resGetUnkownPlayer = client
                .target(baseUri)
                .path("game/api/v1/replays/" + maptest.getName() + "/unkownPlayer")
                .request()
                .get();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), resGetUnkownPlayer.getStatus());
    }


    //Test get Top scores of a map. Warning just the 5 best scores are returned !!!
    @Test
    void getPlayerFromMap(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        final Response resMap = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        assertEquals(Response.Status.OK.getStatusCode(), resMap.getStatus());

        // PLAYER1
        List<Command> commands = new ArrayList<>();
        commands.add(new MoveCityBlock(0, 1));
        commands.add(new PutCityBlock(1, 2));
        commands.add(new MoveCityBlock(2, 3));


        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        String json = "[";
        try {
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(0));
            json += ",";
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(1));
            json += ",";
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(2));
            json += "]";
            //json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        final Response resPlayer1 = client
                .target(baseUri)
                .path("game/api/v1/replays/" + maptest.getName() + "/Paul/1000")
                .request()
                .post(Entity.json(json));
        assertEquals(Response.Status.OK.getStatusCode(), resPlayer1.getStatus());


        // PLAYER2
        commands = new ArrayList<>();
        commands.add(new PutCityBlock(0, 1));
        commands.add(new PutCityBlock(1, 2));
        commands.add(new PutCityBlock(2, 3));

        json = "[";
        try {
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(0));
            json += ",";
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(1));
            json += ",";
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands.get(2));
            json += "]";
            //json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commands);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        final Response resPlayer2 = client
                .target(baseUri)
                .path("game/api/v1/replays/" + maptest.getName() + "/Baptiste/3000")
                .request()
                .post(Entity.json(json));
        assertEquals(Response.Status.OK.getStatusCode(), resPlayer2.getStatus());


        // Test getPlayerFromMap
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/replays/" + maptest.getName())
                .request()
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), resGet.getStatus());
        final List<String> res = resGet.readEntity(new GenericType<>() {
        });
        assertTrue(res.contains("Paul"));
        assertTrue(res.contains("Baptiste"));
        assertTrue(res.size() == 2);
    }

    @Test
    void fausseRoute(final Client client, final URI baseUri) {
        final Response res = client
                .target(baseUri)
                .path("game/api/v1/repl/")
                .request()
                .get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), res.getStatus());
    }

}
