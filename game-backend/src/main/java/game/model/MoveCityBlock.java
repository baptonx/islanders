package game.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class MoveCityBlock extends Command {
    private int x;
    private int y;
    private int posBefore;
    private int posAfter;
    private boolean mementoHasMovedBlock;
    private int mementoTypeMoveBlock;
    private int mementoPosMoveBlock;
    // private List<Tile> map;

    private List<Integer> mementoAvailableCityBlock;
    private int mementoCityBlockSelected;

    private String mementoNomJoueur;
    private int mementoScore;
    private int mementoNextScore;

    private boolean mementoGameOver;


    public MoveCityBlock() {
        //this.map = new ArrayList<>();
        this.posBefore = -1;
        this.posAfter = -1;
    }

    public MoveCityBlock(final int posBefore, final int posAfter) {
        this.posBefore = posBefore;
        this.posAfter = posAfter;
    }

    public boolean isMementoGameOver() {
        return mementoGameOver;
    }

    public int getPosBefore() {
        return posBefore;
    }

    public int getPosAfter() {
        return posAfter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMementoHasMovedBlock() {
        return mementoHasMovedBlock;
    }

    public int getMementoTypeMoveBlock() {
        return mementoTypeMoveBlock;
    }

    public int getMementoPosMoveBlock() {
        return mementoPosMoveBlock;
    }
    /*
    public List<Tile> getMap() {
        return map;
    }

     */

    public List<Integer> getMementoAvailableCityBlock() {
        return mementoAvailableCityBlock;
    }

    public int getMementoCityBlockSelected() {
        return mementoCityBlockSelected;
    }

    public String getMementoNomJoueur() {
        return mementoNomJoueur;
    }

    public int getMementoScore() {
        return mementoScore;
    }

    public int getMementoNextScore() {
        return mementoNextScore;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MoveCityBlock that = (MoveCityBlock) o;
        return posBefore == that.posBefore &&
                posAfter == that.posAfter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posBefore, posAfter);
    }

}
