
package game.resource;

import com.github.hanleyt.JerseyExtension;

import java.net.URI;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import game.model.MapFactory;
import game.model.MapResource;
import game.model.Storage;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

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
        storage = new Storage();
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
    void testPostMap(final Client client, final URI baseUri) {
        MapResource map = mf.newRandomMap();
        map.toString();
        final Response res = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(map));
        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
        MapResource mapResponse = res.readEntity(MapResource.class);
        assertEquals(map.getName(), mapResponse.getName());
    }

    // Example of a route test. The one for getting a list of available maps
    // To edit
    @Test
    void testGetMapsIds(final Client client, final URI baseUri) {
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
    void testGetMapFromName(final Client client, final URI baseUri) {
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
    void testGetRandomMap(final Client client, final URI baseUri) {
        MapResource map = mf.newRandomMap();
        this.storage.addMap(map);
        final Response resGet = client
                .target(baseUri)
                .path("game/api/v1/maps/random")
                .request()
                .get();
        System.out.println(resGet);
        MapResource resMap = resGet.readEntity(MapResource.class);
        System.out.println(resMap);
        assertArrayEquals(map.getTabTiles(), resMap.getTabTiles());
        assertEquals(map, this.storage.getMapFromName(map.getName()));
    }
}
