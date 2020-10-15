package game.resource;

import com.github.hanleyt.JerseyExtension;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TestGameResource {
    Storage data;
    static {
        System.setProperty("jersey.config.test.container.port", "0");
    }

//	static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());

    @SuppressWarnings("unused")
    @RegisterExtension
    JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

    Application configureJersey() {
        data = new Storage();
        // data = Mockito.mock(Storage.class);

        return new ResourceConfig(GameResource.class)
                .register(MyExceptionMapper.class)
                .register(MoxyJsonFeature.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(data).to(Storage.class);
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

    @Test
    void testConstructorMapResource(){
        MapResource m = new MapResource();
        assertNull(m.getName());
        assertEquals(0, m.getId());
        assertTrue(m.getScore().isEmpty());
        assertEquals(new Grass[100], m.getTabTiles());
        m = new MapResource("Test", 85472);
        assertEquals("Test", m.getName());
        assertEquals(85472, m.getId());
        assertEquals(new Grass[100], m.getTabTiles());
        assertTrue(m.getScore().isEmpty());
    }

    @Test
    void testSetScoreMapResource{
        MapResource m = new MapResource();
    }
    @Test
    void testPostMap(final Client client, final URI baseUri) {
        //MapResource map = new MapResource("Map 1", );
        MapResource m = new MapResource("CarteBG", 1);
        final Response res = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(m));
        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
        MapResource mapResponse = res.readEntity(MapResource.class);
        assertEquals("CarteBG", mapResponse.getName());
        assertEquals(1, mapResponse.getId());
    }

    // Example of a route test. The one for getting a list of available maps
    // To edit
    @Test
    void testGetMapsId(final Client client, final URI baseUri) {
        // ajout d'une carte
        MapResource m = new MapResource("CarteBG", 1);
        final Response resPost = client
                .target(baseUri)
                .path("game/api/v1/maps")
                .request()
                .post(Entity.json(m));
        System.out.println(resPost);
        // récupération de la carte
		final Response resGet = client
			.target(baseUri)
			.path("game/api/v1/maps")
			.request()
			.get();
		assertEquals(Response.Status.OK.getStatusCode(), resGet.getStatus());
		System.out.println(resGet);
		final List<Integer> ids = resGet.readEntity(new GenericType<>(){});
        assertEquals(ids.get(0), "1");
        // add other assertions to check 'names'
    }



    
}
