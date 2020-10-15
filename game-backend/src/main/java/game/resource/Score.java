package game.resource;

import java.util.Objects;

public class Score {

    private String player;
    private int score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return score == score1.score &&
                Objects.equals(player, score1.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, score);
    }

    public Score(){
        player = "";
        score = 0;
    }

    public Score(String player, int score){
        this.player = player;
        this.score = score;
    }


    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
