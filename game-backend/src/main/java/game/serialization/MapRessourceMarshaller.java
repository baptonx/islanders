package game.serialization;

import game.model.MapResource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MapRessourceMarshaller implements MessageBodyWriter<MapResource> {

    @Override
    public long getSize(final MapResource map, final Class<?> clazz, final Type type, final Annotation[] annotations, final MediaType mediaType) {
        return -1;
    }

    @Override
    public boolean isWriteable(final Class<?> clazz, final Type type, final Annotation[] annotations, final MediaType mediaType) {
        return clazz == MapResource.class;
    }

    @Override
    public void writeTo(final MapResource map, final Class<?> clazz, final Type type, final Annotation[] annotations, final MediaType mediaType,
                        final MultivaluedMap<String, Object> valueMap, final OutputStream stream) throws IOException, WebApplicationException {

        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("title", map.getName()).build();
        //.add("scores", map.getScores()
        //.add("tiles", map.getTabTiles())).build();

        final DataOutputStream outputStream = new DataOutputStream(stream);
        outputStream.writeBytes(jsonObject.toString());
    }
}

