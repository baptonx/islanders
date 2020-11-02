
package game.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hanleyt.JerseyExtension;

import java.io.File;
import java.io.FileWriter;
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

import game.model.MapFactory;
import game.model.MapResource;
import game.model.Score;
import game.model.ScoreComparator;
import game.model.Storage;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Assertions;
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
        Storage storage = new Storage("src/main/java/game/data/storageTest.txt");
        storage.resetMap();
        g = new GameResource(storage);
        // data = Mockito.mock(Storage.class);
        return new ResourceConfig(GameResource.class)
                .register(MyExceptionMapper.class)
                .register(JacksonFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(storage).to(Storage.class);
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
    void setUp(){
        mf = new MapFactory();
    }

    //Test add map to back-end
    @Test
    void postMap(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        final Response res = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
        MapResource mapResponse = res.readEntity(MapResource.class);
        assertEquals(maptest, mapResponse);
    }

    // Getting a list of available maps
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
        final List<String> names = resGet.readEntity(new GenericType<>() {});
        assertEquals(maptest.getName(), names.get(0));
    }

    //Test get map from name
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
                .path("game/api/v1/maps/"+maptest.getName())
                .request()
                .get();
        MapResource resMap = resGet.readEntity(MapResource.class);
        assertEquals(maptest, resMap);
    }
    @Test
    void getMapFromNameException(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        Assertions.assertThrows(StreamCorruptedException.class, () -> {
         final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/maps/" + maptest.getName())
                .request()
                .get();
        });

    }

    //Test get Random Map
    @Test
    void getRandomMap(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        final Response res = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/maps/"+maptest.getName())
                .request()
                .get();
        MapResource resMap = resGet.readEntity(MapResource.class);
        assertEquals(maptest, resMap);
        assertArrayEquals(maptest.getTabTiles(), resMap.getTabTiles());
    }

    //Test get Top scores of a map. Warning just the 5 best scores are returned !!!
    @Test
    void getTopScores(final Client client, final URI baseUri){
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
                .path("game/api/v1/topScores/"+maptest.getName())
                .request()
                .get();
        final List<Score> resScore = resGet.readEntity(new GenericType<>() {});

        //Get just the 5 best scores
        scores.sort(new ScoreComparator());
        List<Score> topScores = scores.stream().limit(5).collect(Collectors.toList());

        assertEquals(topScores, resScore);
    }
}
