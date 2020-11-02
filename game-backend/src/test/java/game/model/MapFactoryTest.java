package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapFactoryTest {
    MapFactory mf;

    @BeforeEach
    void setUp(){
        mf = new MapFactory();
    }

    @Test
    void testNewMapWithoutConstructor(){
        MapResource map = mf.newMap();
        assertNotNull(map.getName());
        assertArrayEquals(map.getTabTiles(), );
    }

    @Test
    void testRandomMap() {

    }
}