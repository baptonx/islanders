package game.resource;

import io.swagger.annotations.Api;
import org.checkerframework.checker.signature.qual.Identifier;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


//@Path("map")
//@Api(value = "map")
public class MapResource {
    private String name;
    private int id;
    private Map<Integer, Integer> topScore;
    private Tile[] tabTiles;

    public MapResource() {
        tabTiles = generateRandomMap();
    }

    public MapResource(String name, int id) {
        this.name = name;
        this.id = id;
        this.topScore = new HashMap<>();
       // this.tabTiles = generateRandomMap();
    }
    private Tile[] generateRandomMap(){
        Tile[] tab = new Tile[100];
        for( int i=0; i<100; i++){
            int randomType = (int) Math.round(Math.random()*3);
            switch (randomType){
                case 0: tab[i] = new Grass();
                case 1: tab[i] = new Tree();
                case 2: tab[i] = new Water();
            }

        }
        return tab;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapResource that = (MapResource) o;
        return id == that.id &&
                name.equals(that.name) &&
                Objects.equals(topScore, that.topScore) &&
                Arrays.equals(tabTiles, that.tabTiles);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}