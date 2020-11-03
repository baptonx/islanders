package game.model;

import game.model.MapResource;
import game.model.Score;
import game.model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class MapResourceTest {
    private MapResource map_test;
    private List<Score> scores;

    @BeforeEach
    void setUp() {
        map_test = new MapResource("CarteBG");
        scores = new ArrayList<>();
        Score scoreB = new Score("bapt", 10000);
        Score scoreP = new Score("paul", 5000);
        scores.add(scoreB);
        scores.add(scoreP);
    }

    @Test
    void getName() {
        assertEquals("CarteBG", map_test.getName());
    }

    @Test
    void constructorMapResourceWithoutParam() {
        MapResource m = new MapResource();
        assertTrue(m.getScores().isEmpty());
        assertArrayEquals(m.getTabTiles(), new Tile[100]);
    }

    @Test
    void constructorMapResourceWithParam() {
        assertEquals("CarteBG", map_test.getName());
        assertArrayEquals(map_test.getTabTiles(), new Tile[100]);
        assertTrue(map_test.getScores().isEmpty());
    }

    @Test
    void setScoreMapResource() {
        map_test.setScores(scores);
        assertEquals(scores, map_test.getScores());
    }
    @Test
    void addScoreMapResource() {
        map_test.addScore(scores.get(1));
        assertTrue(map_test.getScores().contains(scores.get(1)));
    }

    @Test
    void getTile(){
        MapFactory mf = new MapFactory();
        Tile t = mf.newRandomMap().getTile(12);
        assertThat(t, instanceOf(Tile.class));
    }

    @Test
    void toStringTest(){
        map_test.toString();
        assertEquals(map_test.toString(), "MapResource{name='CarteBG', scores=[], tabTiles=[null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]}");
    }
    @Test
    void hashCodeTest(){
        System.out.println(map_test.hashCode());
        assertEquals(map_test.hashCode(), -580015703);
    }

    @Test
    void getTileException() throws Exception {
        int position1 = 150;
        int position2 = -12;
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> map_test.getTile(position1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> map_test.getTile(position2));
    }


    @Test
    void setName() {
        String newname = "Nouvelle Map";
        map_test.setName(newname);
        assertEquals(newname, map_test.getName());
    }

    @Test
    void generateRandomMap() {
        map_test.generateRandomMap();
        Arrays.stream(map_test.getTabTiles()).forEach(object ->
                assertThat(object, instanceOf(Tile.class))
        );
        assertEquals(100,map_test.getTabTiles().length);
    }
    @Test
    void getTopScores() {
        List<Score> scores = new ArrayList<>();
        scores.add(new Score("bapt", 42));
        scores.add(new Score("bapt", 52));
        scores.add(new Score("bapt", 62));
        scores.add(new Score("bapt", 72));
        scores.add(new Score("bapt", 82));
        scores.add(new Score("bapt", 92));
        map_test.setScores(scores);
        scores.remove(0);
        assertEquals(map_test.getTopScores(), scores);
    }

}