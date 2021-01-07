package game.model;

import java.io.Serializable;
import java.util.Comparator;

/** Objet utilisé pour classer les scores, permet de retourner les 5 meilleurs scores d'une map **/
public class ScoreComparator implements Comparator<Score>, Serializable {
    @Override
    public int compare(final Score s1, final Score s2) {
        return s2.getScore() - s1.getScore();
    }
}
