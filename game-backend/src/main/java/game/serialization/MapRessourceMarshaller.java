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
        public long getSize(MapResource map, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
            return -1;
        }

        @Override
        public boolean isWriteable(Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
            return clazz == MapResource.class;
        }

        @Override
        public void writeTo(MapResource map, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType,
                            MultivaluedMap<String, Object> valueMap, OutputStream stream) throws IOException, WebApplicationException {

            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("title", map.getName()).build();
                    //.add("scores", map.getScores()
                    //.add("tiles", map.getTabTiles())).build();

            DataOutputStream outputStream = new DataOutputStream(stream);
            outputStream.writeBytes(jsonObject.toString());
        }
    }

