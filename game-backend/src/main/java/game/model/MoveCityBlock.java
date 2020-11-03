package game.model;

public class MoveCityBlock {
    private int posBefore;
    private int posAfter;

    public MoveCityBlock(int posBefore, int posAfter) {
        this.posBefore = posBefore;
        this.posAfter = posAfter;
    }

    public int getPosBefore() {
        return posBefore;
    }

    public int getPosAfter() {
        return posAfter;
    }
}
