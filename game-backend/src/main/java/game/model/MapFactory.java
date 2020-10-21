package game.model;

import game.resource.MapResource;

public class MapFactory {

    public MapResource map(){
        return new MapResource();
    }

    public MapResource map(String name, int id){
        return new MapResource(name, id);
    }

    public MapResource randomMap(){
        NameGenerator name = new NameGenerator();
        MapResource m = new MapResource(name.generateName(6), (int) (Math.random() * 1000));
        m.generateRandomMap();
        return m;
    }
}
