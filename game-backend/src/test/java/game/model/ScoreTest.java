package game.model;

import game.model.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {
    private Score score1;
    private Score score2;

    @BeforeEach
    void setUp(){
        score1 = new Score("bapt", 5000);
        score2 = new Score();
    }

    @Test
    void constructorWithParam() {
        assertEquals("bapt", score1.getPlayer());
        assertEquals(5000, score1.getScore());
    }

    @Test
    void constructorWithoutParam() {
        assertEquals("", score2.getPlayer());
        assertEquals(0, score2.getScore());
    }

    @Test
    void setPlayer() {
        score2.setPlayer("paul");
        assertEquals("paul", score2.getPlayer());
    }

    @Test
    void setScore() {
        score2.setScore(2000);
        assertEquals(2000, score2.getScore());
    }
}