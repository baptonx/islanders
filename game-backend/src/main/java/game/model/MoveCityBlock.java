package game.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class MoveCityBlock extends Command {
    public int x;
    public int y;
    public int posBefore;
    public int posAfter;
    public boolean mementoHasMovedBlock;
    public int mementoTypeMoveBlock;
    public int mementoPosMoveBlock;
    public List<Tile> map;

    public List<Integer> mementoAvailableCityBlock;
    public int mementoCityBlockSelected;

    public String mementoNomJoueur;
    public int mementoScore;
    public int mementoNextScore;

    public MoveCityBlock() {
        this.map = new ArrayList<>();
        this.posBefore = -1;
        this.posAfter = -1;
    }

    public MoveCityBlock(final int posBefore, final int posAfter) {
        this.posBefore = posBefore;
        this.posAfter = posAfter;
    }

    public int getPosBefore() {
        return posBefore;
    }

    public int getPosAfter() {
        return posAfter;
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
