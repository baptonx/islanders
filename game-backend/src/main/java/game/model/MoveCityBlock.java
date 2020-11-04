package game.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlRootElement;

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
}
