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
    }

    @Test
    void getMapFromName() {

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
            List<MapResource> newStorage = mapper.readValue(file, new TypeReference<List<MapResource>>() {});
            MapResource mm = newStorage.stream().filter(map -> map.getName().equals(m.getName())).findFirst().get();
            assertEquals(mm.getName(), m.getName());
            assertArrayEquals(mm.getTabTiles(), m.getTabTiles());
            assertEquals(lastStorageLen+1, newStorage.size());
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