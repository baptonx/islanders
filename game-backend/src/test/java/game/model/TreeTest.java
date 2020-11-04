package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    Tree treeTile1;
    Tree treeTile2;

    @BeforeEach
    void setUp(){
        treeTile1 = new Tree();
        treeTile2 = new Tree();
    }
    @Test
    void testHashCode() {
        System.out.println(treeTile1.hashCode());
        assertTrue(treeTile1.hashCode() == treeTile2.hashCode());
    }

    @Test
    void testEquals() {
        assertEquals(treeTile1, treeTile2);
        assertTrue(treeTile1.equals(treeTile2) && treeTile2.equals(treeTile1));
    }
}