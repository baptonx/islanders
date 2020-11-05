package game.model;

import game.model.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {
    private Score score1;
    private Score score2;
    private Score score3;
    private Score score4;
    private Score score5;

    @BeforeEach
    void setUp(){
        score1 = new Score("biden", 264);
        score2 = new Score("biden", 214);
        score3 = new Score("biden", 264);
        score4 = new Score("trump", 214);
        score5 = new Score();
    }

    @Test
    void constructorWithParam() {
        assertEquals("biden", score1.getPlayer());
        assertEquals(264, score1.getScore());
    }

    @Test
    void constructorWithoutParam() {
        assertEquals("", score5.getPlayer());
        assertEquals(0, score5.getScore());
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


    @Test
    void testEquals() {
        assertEquals(score1, score3);
        assertTrue(score1.equals(score3) && score3.equals(score1));
        assertFalse(score1.equals(score2));
        assertFalse(score1.equals(null));
        assertFalse(score1.equals(String.class));
        assertFalse(score2.equals(score4));
    }

    @Test
    void testHashCode() {
        System.out.println(score1.hashCode());
        assertTrue(score1.hashCode() == score3.hashCode());
        assertFalse(score1.hashCode() == score2.hashCode());
    }
}