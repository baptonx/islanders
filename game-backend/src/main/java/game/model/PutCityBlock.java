package game.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class PutCityBlock extends Command {
    private int position;
    private int typeCityBlock;

    public PutCityBlock(){
        this.position = -1;
        this.typeCityBlock = -1;
    }

    public PutCityBlock(int position, int typeCityBlock) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PutCityBlock that = (PutCityBlock) o;
        return position == that.position &&
                typeCityBlock == that.typeCityBlock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, typeCityBlock);
    }
}
