package game.model;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
    @Override
    public int compare(Score s1, Score s2) {
        return s1.getScore() - s2.getScore();
    }

}
