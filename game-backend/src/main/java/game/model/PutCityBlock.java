package game.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class PutCityBlock extends Command {
    private int position;
    private int typeCityBlock;

    public PutCityBlock() {
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
