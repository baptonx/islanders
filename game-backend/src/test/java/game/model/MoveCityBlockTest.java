package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveCityBlockTest {
    MoveCityBlock m1;
    MoveCityBlock m2;
    MoveCityBlock m3;
    MoveCityBlock m4;

    @BeforeEach
    void setUp() {
        m1 = new MoveCityBlock(1, 0);
        m2 = new MoveCityBlock(1, 2);
        m3 = new MoveCityBlock(1, 0);
        m4 = new MoveCityBlock(0, 2);
    }

    @Test
    void testEquals() {
        assertEquals(m1, m3);
        assertTrue(m1.equals(m3) && m3.equals(m1));
        assertNotEquals(m1,String.class);
        assertNotEquals(m1,null);
        assertEquals(m1,m1);
        assertNotEquals(m1,m2);
        assertNotEquals(m2,m4);
    }

    @Test
    void testHashCode() {
        assertEquals(m1.hashCode(),m3.hashCode());
    }
}