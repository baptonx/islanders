package game.model;

import com.github.javafaker.Faker;

public class MapFactory {

    public MapResource newMap() {
        return new MapResource();
    }

    public MapResource newMap(final String name) {
        return new MapResource(name);
    }

    public MapResource newRandomMap() {
        final Faker faker = new Faker();
        final MapResource m = new MapResource(faker.dragonBall().character());
        m.generateRandomMap();
        return m;
    }
}
