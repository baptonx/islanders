package game.model;

import java.util.Objects;

/** Objet contenant le score fait par un joueur **/
public class Score {

    private String player;
    private int score;

    public Score() {
        player = "";
        score = 0;
    }

    public Score(final String player, final int score) {
        this.player = player;
        this.score = score;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Score score1 = (Score) o;
        return score == score1.score &&
                Objects.equals(player, score1.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, score);
    }


    public String getPlayer() {
        return player;
    }

    public void setPlayer(final String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }
}
