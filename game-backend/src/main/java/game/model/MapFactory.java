package game.model;

public class MapFactory {

    public MapResource newMap(){
        return new MapResource();
    }

    public MapResource newMap(String name){
        return new MapResource(name);
    }

    public MapResource newRandomMap(){
        NameGenerator name = new NameGenerator();
        MapResource m = new MapResource(name.generateName(6));
        m.generateRandomMap();
        return m;
    }
}
