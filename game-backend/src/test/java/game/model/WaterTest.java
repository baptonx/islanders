package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaterTest {
    Water waterTile1;
    Water waterTile2;

    @BeforeEach
    void setUp(){
        waterTile1 = new Water();
        waterTile2 = new Water();
    }
    @Test
    void testHashCode() {
        System.out.println(waterTile1.hashCode());
        assertTrue(waterTile1.hashCode() == waterTile2.hashCode());
    }

    @Test
    void testEquals() {
        assertEquals(waterTile1, waterTile2);
        assertTrue(waterTile1.equals(waterTile2) && waterTile2.equals(waterTile1));
    }
}