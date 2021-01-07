package game.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class PutCityBlock extends Command {
    private int position;
    private int x;
    private int y;
    private int typeCityBlock;
    private boolean mementoHasMovedBlock;
    private int mementoTypeMoveBlock;
    private int mementoPosMoveBlock;
    // private List<Tile> map;
    private List<Integer> mementoAvailableCityBlock;
    private int mementoCityBlockSelected;
    private String mementoNomJoueur;
    private int mementoScore;
    private int mementoNextScore;

    public PutCityBlock() {
        //this.map = new ArrayList<>();
        this.position = -1;
        this.typeCityBlock = -1;
    }

    public PutCityBlock(final int position, final int typeCityBlock) {
        this.position = position;
        this.typeCityBlock = typeCityBlock;
    }

    public int getPosition() {
        return position;
    }

    public int getTypeCityBlock() {
        return typeCityBlock;
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
        final PutCityBlock that = (PutCityBlock) o;
        return position == that.position &&
                typeCityBlock == that.typeCityBlock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, typeCityBlock);
    }
}
