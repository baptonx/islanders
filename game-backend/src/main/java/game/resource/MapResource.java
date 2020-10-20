package game.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;


@XmlAccessorType(XmlAccessType.FIELD)
public class MapResource {
    private String name;
    private int id;
    private List<Score> scores;
    private Tile[] tabTiles;

    public MapResource() {
        //tabTiles = generateRandomMap();
        this.tabTiles = new Grass[100];
        this.scores = new ArrayList<>();
    }

    public MapResource(String name, int id) {
        this.name = name;
        this.id = id;
        this.scores = new ArrayList<Score>();
        this.tabTiles = new Grass[100];
    }
    public MapResource generateRandomMap() {
        List<Tile> tileArray = new ArrayList<>(100);
        int[] indexArray = new Random().ints(100, 0,2).toArray();
        Arrays.stream(indexArray).forEach( index -> {
            switch (index){
                case 0: tileArray.add(new Grass());
                case 1: tileArray.add(new Tree());
                case 2: tileArray.add(new Water());
            }
        });
        System.out.println(tileArray.toString());
        NameGenerator name = new NameGenerator();
        MapResource newMap= new MapResource(name.generateName(6), (int) (Math.random()*1000));
        tileArray.toArray(tabTiles);
        return newMap;
    }

    public Tile getTile(int position) throws ArrayIndexOutOfBoundsException {
        if(position<100 && position >0)
            return tabTiles[position];
        else
            throw new ArrayIndexOutOfBoundsException("Tile position must be between 0 and 100");
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
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(scores, that.scores) &&
                Arrays.equals(tabTiles, that.tabTiles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, id, scores);
        result = 31 * result + Arrays.hashCode(tabTiles);
        return result;
    }
}