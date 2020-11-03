package game.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MapResource {
    private String name;
    private List<Score> scores;
    private Tile[] tabTiles;

    public MapResource() {
        final NameGenerator ng = new NameGenerator();
        this.name = ng.generateName(6);
        this.tabTiles = new Tile[100];
        this.scores = new ArrayList<>();
    }

    public MapResource(final String name) {
        this.name = name;
        this.scores = new ArrayList<>();
        this.tabTiles = new Tile[100];
    }

    public void generateRandomMap() {
        final int[] indexArray = new Random().ints(100, 0, 3).toArray();
        IntStream.range(0, indexArray.length).forEach(i -> {
            if (indexArray[i] == 0) {
                tabTiles[i] = new Grass();
            } else if (indexArray[i] == 1) {
                tabTiles[i] = new Tree();
            } else if (indexArray[i] == 2) {
                tabTiles[i] = new Water();
            }
        });
    }

    public Tile getTile(final int position) throws ArrayIndexOutOfBoundsException {
        if (position < 100 && position > 0) {
            return tabTiles[position];
        } else {
            throw new ArrayIndexOutOfBoundsException("Tile position must be between 0 and 100");
        }
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

    public void setName(final String name) {
        this.name = name;
    }

    public List<Score> getScores() {
        return scores;
    }

    public List<Score> getTopScores() {
        this.scores.sort(new ScoreComparator());
        return this.scores.stream().limit(5).collect(Collectors.toList());
    }

    public void setScores(final List<Score> newScore) {
        this.scores = newScore;
    }

    public void addScore(final Score s) {
        this.scores.add(s);
    }

    public void setTabTiles(final Tile[] tabTiles) {
        this.tabTiles = tabTiles;
    }

    public Tile[] getTabTiles() {
        return tabTiles;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MapResource that = (MapResource) o;
        return Objects.equals(name, that.name) &&
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
