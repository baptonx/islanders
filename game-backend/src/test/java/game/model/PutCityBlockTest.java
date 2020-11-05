package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PutCityBlockTest {
    PutCityBlock m1;
    PutCityBlock m2;
    PutCityBlock m3;
    PutCityBlock m4;

    @BeforeEach
    void setUp() {
        m1 = new PutCityBlock(1, 0);
        m2 = new PutCityBlock(1, 2);
        m3 = new PutCityBlock(1, 0);
        m4 = new PutCityBlock(0, 2);
    }

    @Test
    void testEquals() {
        assertEquals(m1, m3);
        assertTrue(m1.equals(m3) && m3.equals(m1));
        assertEquals(m1, String.class);
        assertFalse(m1.equals(null));
        assertTrue(m1.equals(m1));
        assertFalse(m1.equals(m2));
        assertFalse(m2.equals(m4));
    }

    @Test
    void testHashCode() {
        assertTrue(m1.hashCode() == m3.hashCode());
    }
}