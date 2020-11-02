package game.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MapResource {
    private String name;
    private List<Score> scores;
    private Tile[] tabTiles;

    public MapResource() {
        NameGenerator ng = new NameGenerator();
        this.name = ng.generateName(6);
        this.tabTiles = new Tile[100];
        this.scores = new ArrayList<>();
    }

    public MapResource(String name) {
        this.name = name;
        this.scores = new ArrayList<>();
        this.tabTiles = new Tile[100];
    }

    public void generateRandomMap() {
        List<Tile> tileArray = new ArrayList<>(100);
        int[] indexArray = new Random().ints(100, 0, 2).toArray();
        Arrays.stream(indexArray).forEach(index -> {
            switch (index) {
                case 0:
                    tileArray.add(new Grass());
                case 1:
                    tileArray.add(new Tree());
                case 2:
                    tileArray.add(new Water());
            }
        });
        tileArray.toArray(this.tabTiles);
    }

    public Tile getTile(int position) throws ArrayIndexOutOfBoundsException {
        if (position < 100 && position > 0)
            return tabTiles[position];
        else
            throw new ArrayIndexOutOfBoundsException("Tile position must be between 0 and 100");
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MapResource{" +
                "name='" + name + '\'' +
                ", scores=" + scores +
                ", tabTiles=" + Arrays.toString(tabTiles) +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> newScore) {
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
        return  Objects.equals(name, that.name) &&
                Objects.equals(scores, that.scores) &&
                Arrays.equals(tabTiles, that.tabTiles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, scores);
        result = 31 * result + Arrays.hashCode(tabTiles);
        return result;
    }
}