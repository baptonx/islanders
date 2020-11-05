package game.resource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.IOException;

public class MyExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(final Exception exception) {
        exception.printStackTrace();
        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }
        if(exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_GATEWAY).entity(exception.getMessage()).build();
        }
        return Response.status(500).build();
    }
}
