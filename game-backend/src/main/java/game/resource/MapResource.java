package game.resource;

import io.swagger.annotations.Api;
import org.checkerframework.checker.signature.qual.Identifier;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


//@Path("map")
//@Api(value = "map")
public class MapResource {
    private String name;
    private int id;
    private List<Score> scores;
    private Tile[] tabTiles;

    public MapResource() {
        //tabTiles = generateRandomMap();
        this.tabTiles = new Grass[100];
        this.scores = new ArrayList<Score>();
    }

    public MapResource(String name, int id) {
        this.name = name;
        this.id = id;
        this.scores = new ArrayList<Score>();
        this.tabTiles = new Grass[100];
    }
    private Tile[] generateRandomMap(){
        return null;    }

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

    public List<Score> getScore() {
        return scores;
    }

    public void setScore(List<Score> newScore) {
        this.scores = newScore;
    }

    public void setTabTiles(Tile[] tabTiles) {

        this.tabTiles = tabTiles;
    }

    public Tile[] getTabTiles() {

        return tabTiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapResource that = (MapResource) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(scores, that.scores) &&
                Arrays.equals(tabTiles, that.tabTiles);
    }

    @Override
    public int hashCode() {

        return 0;
    }
}