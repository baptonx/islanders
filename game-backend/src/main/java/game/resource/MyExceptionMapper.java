package game.resource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(final Exception exception) {
        exception.printStackTrace();
        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }
        /** On fait une erreur BAD_REQUEST et on fait remonter le message d'erreur définie dans les classes **/
        if(exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
        }
        return Response.status(500).build();
    }
}
