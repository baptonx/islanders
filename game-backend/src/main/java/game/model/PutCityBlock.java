package game.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PutCityBlock implements Command {
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
}
