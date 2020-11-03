package game.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    Storage storage;
    private ObjectMapper mapper = new ObjectMapper();
    private File file;

    @BeforeEach
    void setUp(){
        storage = new Storage("src/main/java/game/data/mapsTest.txt");
        file = new File("src/main/java/game/data/mapsTest.txt");
    }
    
    @Test
    void getListMap() {
        List<MapResource> listMap = storage.getListMap();
        try {
            assertEquals(listMap, mapper.readValue(file, new TypeReference<List<MapResource>>() {}));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addScore(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        int lastMapsFileLen = -2;
        try {
            lastMapsFileLen = mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Score s = new Score("Paul", 150);
        storage.addScore(m.getName(), s);
        try {
            assertEquals(lastMapsFileLen, mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
            assertEquals(lastMapsFileLen,storage.listMapSize());
            assertEquals(m,mapper.readValue(file, new TypeReference<List<MapResource>>() {}).stream().filter(map -> map.getName().equals(m.getName())).findFirst().get());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getMapFromName() {
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        MapResource mm = storage.getMapFromName(m.getName());
        assertEquals(m,mm);
    }

    @Test
    void addMap() {
        MapResource m = new MapFactory().newRandomMap();
        int lastStorageLen = -2;
        try {
            lastStorageLen = mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.addMap(m);
        try {

            assertEquals(lastStorageLen+1, mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void resetMap(){
        storage.resetMap();
        try {
            assertTrue(mapper.readValue(file, new TypeReference<List<MapResource>>() {}).isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}