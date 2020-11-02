package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreComparatorTest {
    Score s1;
    Score s2;
    Score s3;
    ScoreComparator sc;

    @BeforeEach
    void setUp() {
        s1 = new Score("hugz", 54);
        s2 = new Score("bapt", 69);
        s3 = new Score("hugz", 69);
        sc = new ScoreComparator();
    }

    @Test
    void greaterThan() {
        assertTrue(sc.compare(s1,s2) <0);
    }
    @Test
    void smallerThan() {
        assertTrue(sc.compare(s2,s1) >0);
    }
    @Test
    void equals() {
        assertTrue(sc.compare(s2,s3) == 0);
    }
}