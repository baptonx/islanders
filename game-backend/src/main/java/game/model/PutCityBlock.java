package game.model;

public class PutCityBlock {
    private int position;
    private int typeCityBlock;

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
