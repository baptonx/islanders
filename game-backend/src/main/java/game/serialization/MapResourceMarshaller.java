package game.serialization;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.awt.print.Book;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MapResourceMarshaller implements MessageBodyWriter<Book> {


    @Override
    public long getSize(Book book, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public boolean isWriteable(Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
        return clazz == Book.class;
    }

    @Override
    public void writeTo(Book book, Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> valueMap, OutputStream stream) throws IOException, WebApplicationException {

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("title", book.getTitle())
                .add("author", book.getAuthor()).build();

        DataOutputStream outputStream = new DataOutputStream(stream);
        outputStream.writeBytes(jsonObject.toString());
    }
}
