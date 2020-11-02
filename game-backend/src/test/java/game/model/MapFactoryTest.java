package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MapFactoryTest {
    MapFactory mf;

    @BeforeEach
    void setUp(){
        mf = new MapFactory();
    }

    @Test
    void newMapWithoutParameters(){
        MapResource map = mf.newMap();
        assertNotNull(map.getName());
        assertArrayEquals(map.getTabTiles(), new Tile[100]);
    }

    @Test
    void newMapWithName(){
        MapResource map = mf.newMap("Super map");
        assertEquals(map.getName(), "Super map");
        assertArrayEquals(map.getTabTiles(), new Tile[100]);
    }

    @Test
    void randomMap() {
        MapResource map = mf.newRandomMap();
        assertNotNull(map.getName());
        Arrays.stream(map.getTabTiles()).forEach(object ->
                assertThat(object, instanceOf(Tile.class))
        );
    }
}