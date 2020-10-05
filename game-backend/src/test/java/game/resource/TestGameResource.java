package game.resource;

import com.github.hanleyt.JerseyExtension;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class TestGameResource {
    static {
        System.setProperty("jersey.config.test.container.port", "0");
    }

//	static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());

    @SuppressWarnings("unused")
    @RegisterExtension
    JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

    Application configureJersey() {
        return new ResourceConfig(GameResource.class)
                .register(MyExceptionMapper.class)
                .register(MoxyJsonFeature.class);
    }

//	<T> T LogJSONAndUnmarshallValue(final Response res, final Class<T> classToRead) {
//		res.bufferEntity();
//		final String json = res.readEntity(String.class);
//		log.log(Level.INFO, "JSON received: " + json);
//		final T obj = res.readEntity(classToRead);
//		res.close();
//		return obj;
//	}

    // Example of a route test. The one for getting a list of available maps
    // To edit
    @Test
    void testGetNames(final Client client, final URI baseUri) {
//		final Response res = client
//			.target(baseUri)
//			.path("game/maps/names")
//			.request()
//			.get();

//		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
//		final List<String> names = res.readEntity(new GenericType<>() {});
        // add other assertions to check 'names'
    }
}
