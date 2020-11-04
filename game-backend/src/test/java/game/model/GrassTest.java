package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassTest {
    Grass grassTile1;
    Grass grassTile2;

    @BeforeEach
    void setUp(){
        grassTile1 = new Grass();
        grassTile2 = new Grass();
    }
    @Test
    void testHashCode() {
        System.out.println(grassTile1.hashCode());
        assertTrue(grassTile1.hashCode() == grassTile2.hashCode());
    }

    @Test
    void testEquals() {
        assertEquals(grassTile1, grassTile2);
        assertTrue(grassTile1.equals(grassTile2) && grassTile2.equals(grassTile1));
    }
}