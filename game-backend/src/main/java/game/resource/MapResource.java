package game.resource;

import io.swagger.annotations.Api;
import org.checkerframework.checker.signature.qual.Identifier;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import java.util.Map;

@Path("map")
@Api(value = "map")
public class MapResource {
    private String name;
    private int id;
    private Map<Integer, Integer> topScore;
    private Tile[] tabTiles;

    public MapResource() {
        tabTiles = new Tile[100];
    }

    public Tile getTile(int position) {
        return tabTiles[position];
    }

    public Tile[] getTabTiles() {
        return tabTiles;
    }


}