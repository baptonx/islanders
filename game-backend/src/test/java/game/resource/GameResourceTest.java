
package game.resource;

import com.github.hanleyt.JerseyExtension;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class GameResourceTest {
    Storage storage;
    MapFactory mf;
    MapResource map;

    static {
        System.setProperty("jersey.config.test.container.port", "0");
    }

//	static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());

    @SuppressWarnings("unused")
    @RegisterExtension
    JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

    Application configureJersey() {
        storage = new Storage("src/main/java/game/data/storageTest.txt");
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
        map = mf.newMap("CarteBG");
        this.storage.addMap(map);
    }

    @Test
    void postMap(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        maptest.toString();
        final Response res = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(maptest));
        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
        MapResource mapResponse = res.readEntity(MapResource.class);
        assertEquals(maptest.getName(), mapResponse.getName());
    }

    // Example of a route test. The one for getting a list of available maps
    // To edit
    @Test
    void getMapsNames(final Client client, final URI baseUri) {
        // ajout d'une carte
        final Response resPost = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(map));
        System.out.println(resPost);
        // récupération des id des Maps
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), resGet.getStatus());
        System.out.println(resGet);
        final List<String> names = resGet.readEntity(new GenericType<>() {
        });
        System.out.println(names.get(0));
        assertEquals(names.get(0), map.getName());
        // add other assertions to check 'names'
    }

    @Test
    void getMapFromName(final Client client, final URI baseUri) {
        final Response resPost = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(this.map));
        System.out.println(resPost);
        System.out.println(this.map);
        // récupération de la carte
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/maps/CarteBG")
                .request()
                .get();
        System.out.println(resGet);
        System.out.println(this.map);
        MapResource resMap = resGet.readEntity(MapResource.class);
        assertEquals(this.map, resMap);
    }

    @Test
    void getRandomMap(final Client client, final URI baseUri) {
        MapResource maptest = mf.newRandomMap();
        this.storage.addMap(maptest);
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/maps/random")
                .request()
                .get();
        System.out.println(resGet);
        MapResource resMap = resGet.readEntity(MapResource.class);
        System.out.println(resMap);
        assertArrayEquals(maptest.getTabTiles(), resMap.getTabTiles());
        assertEquals(maptest, this.storage.getMapFromName(maptest.getName()));
    }
    @Test
    void getTopScores(final Client client, final URI baseUri){
        List<Score> scores = Stream.generate(Score::new).limit(10).collect(toList());
        this.map.setScores(scores);
        final Response resPost = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(this.map));
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/topScores/CarteBG")
                .request()
                .get();
        final List<Score> resScore = resGet.readEntity(new GenericType<>() {});
        scores.sort(new ScoreComparator());
        List<Score> topScores = scores.stream().limit(5).collect(Collectors.toList());
        System.out.println(topScores);
        assertEquals(resScore, topScores);
    }
}
