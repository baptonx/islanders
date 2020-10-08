package game.resource;

import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import java.util.Map;

@Singleton
@Path("map")
@Api(value = "map")
public class MapResource {
    private String name;
    private int id;
    private Map<Integer, Integer> topScore;
}