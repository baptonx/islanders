package game.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class PutCityBlock extends Command {
    public int position;
    public int x;
    public int y;
    public int typeCityBlock;
    public boolean mementoHasMovedBlock;
    public int mementoTypeMoveBlock;
    public int mementoPosMoveBlock;
    public List<Tile> map;
    public List<Integer> mementoAvailableCityBlock;
    public int mementoCityBlockSelected;
    public String mementoNomJoueur;
    public int mementoScore;
    public int mementoNextScore;

    public PutCityBlock() {
        this.map = new ArrayList<>();
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
