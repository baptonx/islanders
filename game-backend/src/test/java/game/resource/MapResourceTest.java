package game.resource;

import game.model.Grass;
import game.model.MapResource;
import game.model.Score;
import game.model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
        assertArrayEquals(m.getTabTiles(), new Grass[100]);
    }

    @Test
    void constructorMapResourceWithParam() {
        assertEquals("CarteBG", map_test.getName());
        assertArrayEquals(map_test.getTabTiles(), new Grass[100]);
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
    void generateRandomMap() {
        map_test.generateRandomMap();
        Arrays.stream(map_test.getTabTiles()).forEach(object ->
            assertTrue(object != null)
        );
    }
}