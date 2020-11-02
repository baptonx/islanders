package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreComparatorTest {
    Score s1;
    Score s2;

    @BeforeEach
    void setUp() {
        s1 = new Score("hugz", 54);
        s2 = new Score("bapt", 69);
        ScoreComparator sc = new ScoreComparator();
    }

    @Test
    void compare() {

    }
}