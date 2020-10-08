package game.resource;

public abstract class CityBlock {
    private int points;
    private int radius;

    public int getType() {
        return points;
    }
    public int getPoints() {
        return points;
    }
    public int getRadius() {
        return radius;
    }

    public static CityBlock getCityBlockByType(int typeCityBlock) {
        if(typeCityBlock == 0) {
            return new House();
        }
        else if(typeCityBlock == 1) {
            return new Circus();
        }
        else if(typeCityBlock == 2) {
            return new WindTurbine();
        }
        else {
            return new Fountain();
        }
    }

}
