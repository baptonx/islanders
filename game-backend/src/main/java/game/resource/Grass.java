package game.resource;

public class Grass extends Tile{
    CityBlock block;

    public CityBlock getCityBlock() {
        return block;
    }

    public void setCityBlock(CityBlock c) {
        block = c;
    }

    public void setCityBlock(int typeCityBlock) {
        block = CityBlock.getCityBlockByType(typeCityBlock);
    }

}
