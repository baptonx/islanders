package game.resource;

import io.swagger.annotations.Api;
import org.checkerframework.checker.signature.qual.Identifier;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import java.util.HashMap;
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

    public MapResource(String name, int id) {
        this.name = name;
        this.id = id;
        this.topScore = new HashMap<>();
        this.tabTiles = new Tile[100];
    }

    public Tile getTile(int position) {
        return tabTiles[position];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getTopScore() {
        return topScore;
    }

    public void setTopScore(Map<Integer, Integer> topScore) {
        this.topScore = topScore;
    }

    public void setTabTiles(Tile[] tabTiles) {
        this.tabTiles = tabTiles;
    }

    public Tile[] getTabTiles() {
        return tabTiles;
    }


}