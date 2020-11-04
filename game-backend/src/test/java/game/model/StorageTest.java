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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    Storage storage;
    private ObjectMapper mapper = new ObjectMapper();
    private File file;
    private CommandCollector collector;

    @BeforeEach
    void setUp(){
        storage = new Storage("src/main/java/game/data/mapsTest.txt");
        file = new File("src/main/java/game/data/mapsTest.txt");
        List<Command> commands = new ArrayList<>();
        commands.add(new MoveCityBlock(0,1));
        commands.add(new PutCityBlock(1,2));
        commands.add(new MoveCityBlock(2,3));
        collector = new CommandCollector("Paul", commands);
    }
    
    @Test
    void getListMap() {
        List<MapResource> listMap = storage.getMaps();
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
            assertEquals(m,storage.getMap(m.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();

    }

    @Test
    void getMapFromName(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        MapResource mm = storage.getMap(m.getName());
        assertEquals(m,mm);
        try {
            assertEquals(mm,mapper.readValue(file, new TypeReference<List<MapResource>>(){}).stream().filter(map -> map.getName().equals(m.getName())).findFirst().get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();
    }

    @Test
    void addMap(){
        MapResource m = new MapFactory().newRandomMap();
        int lastMapsFileLen = -2;
        try {
            lastMapsFileLen = mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.addMap(m);
        try {
            assertEquals(lastMapsFileLen+1, storage.listMapSize());
            assertEquals(lastMapsFileLen+1, mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();
    }

    @Test
    void addCommand(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        int lastMapsFileLen = -2;
        try {
            lastMapsFileLen = mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.addCommand(m.getName(),collector);
        try {
            assertEquals(lastMapsFileLen, mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
            assertEquals(lastMapsFileLen,storage.listMapSize());
            assertEquals(m,mapper.readValue(file, new TypeReference<List<MapResource>>() {}).stream().filter(map -> map.getName().equals(m.getName())).findFirst().get());
            assertEquals(m,storage.getMap(m.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();
    }

    @Test
    void addGame(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        Score s = new Score("Hugo",5000);
        int lastMapsFileLen = -2;
        try {
            lastMapsFileLen = mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.addGame(m.getName(), collector, s);
        try {
            assertEquals(lastMapsFileLen, mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
            assertEquals(lastMapsFileLen,storage.listMapSize());
            assertEquals(m,mapper.readValue(file, new TypeReference<List<MapResource>>() {}).stream().filter(map -> map.getName().equals(m.getName())).findFirst().get());
            assertEquals(m,storage.getMap(m.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();
    }

    @Test
    void addRandomMap(){
        int lastMapsFileLen = -2;
        try {
            lastMapsFileLen = mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.addRandomMap();
        try {
            assertEquals(lastMapsFileLen+1, storage.listMapSize());
            assertEquals(lastMapsFileLen+1, mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();
    }

    @Test
    void addMapAlreadyNamed(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        assertThrows(IllegalArgumentException.class,() -> storage.addMap(m));
        storage.resetMap();
    }

    @Test
    void getCommandCollector(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        storage.addGame(m.getName(),collector,new Score("Paul", 5000));
        storage.addGame(m.getName(), collector, new Score("Paul", 10000));
        List<Command> commands = new ArrayList<>();
        commands.add(new MoveCityBlock(0,1));
        commands.add(new PutCityBlock(1,2));
        commands.add(new MoveCityBlock(2,3));
        CommandCollector collector2 = new CommandCollector("Hugo", commands);
        storage.addGame(m.getName(), collector2, new Score("Hugo", 5000));
        assertEquals(storage.getCommandCollectorFromMap(m.getName()), m.getCommandCollectors().stream().map(command -> command.getPlayerName()).collect(Collectors.toList()));
        storage.resetMap();
    }


    @Test
    void resetMap(){
        storage.resetMap();
        try {
            assertTrue(storage.getMaps().isEmpty());
            assertTrue(mapper.readValue(file, new TypeReference<List<MapResource>>() {}).isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}