package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PutCityBlockTest {
    PutCityBlock m1;
    PutCityBlock m2;

    @BeforeEach
    void setUp(){
        m1 = new PutCityBlock();
        m2 = new PutCityBlock();
    }

    @Test
    void testEquals() {
        assertEquals(m1, m2);
        assertTrue(m1.equals(m2) && m2.equals(m1));
    }

    @Test
    void testHashCode() {
        System.out.println(m1.hashCode());
        assertTrue(m1.hashCode() == m2.hashCode());
    }
}