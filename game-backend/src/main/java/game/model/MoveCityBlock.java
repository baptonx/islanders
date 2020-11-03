package game.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MoveCityBlock implements Command {
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
