package game.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class MoveCityBlock extends Command {
    private int posBefore;
    private int posAfter;

    public MoveCityBlock(){
        this.posBefore = -1;
        this.posAfter = -1;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveCityBlock that = (MoveCityBlock) o;
        return posBefore == that.posBefore &&
                posAfter == that.posAfter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posBefore, posAfter);
    }
}
