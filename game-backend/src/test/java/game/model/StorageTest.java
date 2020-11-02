package game.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    Storage storage;
    private ObjectMapper mapper = new ObjectMapper();
    private File file;

    @BeforeEach
    void setUp(){
        storage = new Storage("src/main/java/game/data/storageTest.txt");
        file = new File("src/main/java/game/data/storageTest.txt");
    }

    @Test
    void storageConstructor(){

    }

    @Test
    void getListMap() {
        System.out.println(storage.getListMap());
    }

    @Test
    void getMapFromName() {

    }

    @Test
    void addMap() {
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        try {
            assertEquals(mapper.readValue(file, new TypeReference<List<MapResource>>() {}).stream().filter(map -> map.getName() == m.getName()).findFirst().get(), m);
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