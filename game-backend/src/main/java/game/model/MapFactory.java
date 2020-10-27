package game.model;

public class MapFactory {

    public MapResource newMap(){
        return new MapResource();
    }

    public MapResource newMap(String name, int id){
        return new MapResource(name, id);
    }

    public MapResource newRandomMap(){
        NameGenerator name = new NameGenerator();
        MapResource m = new MapResource(name.generateName(6), (int) (Math.random() * 1000));
        m.generateRandomMap();
        return m;
    }
}
