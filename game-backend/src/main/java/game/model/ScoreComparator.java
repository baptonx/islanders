package game.model;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
    @Override
    public int compare(final Score s1, final Score s2) {
        return s1.getScore() - s2.getScore();
    }

}
