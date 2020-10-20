package game.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class MapResourceTest {
    private MapResource map_test;
    private List<Score> scores;

    @BeforeEach
    void setUp() {
        map_test = new MapResource("CarteBG", 1);
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
    void constructorMapResourceNoParam() {
        MapResource m = new MapResource();
        assertNull(m.getName());
        assertEquals(0, m.getId());
        assertTrue(m.getScores().isEmpty());
        assertEquals(m.getTabTiles(), new Grass[100]);
    }

    @Test
    void constructorMapResourceWithParam() {
        assertEquals("CarteBG", map_test.getName());
        assertEquals(1, map_test.getId());
        assertEquals(map_test.getTabTiles(), new Grass[100]);
        assertTrue(map_test.getScores().isEmpty());
    }

    @Test
    void setScoreMapResource() {
        map_test.setScores(scores);
        assertEquals(scores, map_test.getScores());
    }

    @Test
    void getTileException() throws Exception {
        int position1 = 150;
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> map_test.getTile(position1));
    }

    @Test
    void setName() {
        String newname = "Nouvelle Map";
        map_test.setName(newname);
        assertEquals(newname, map_test.getName());
    }
    @Test
    void generateRandomMap(){

    }
}