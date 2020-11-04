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
    void getMapsName(){
        MapResource m1 = new MapFactory().newMap("Map1");
        MapResource m2 = new MapFactory().newMap("Map2");
        try {
            assertEquals(storage.getMapsName(), mapper.readValue(file, new TypeReference<List<MapResource>>() {}).stream().map(map -> map.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addScore(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        Score s = new Score("Paul", 150);
        storage.addScore(m.getName(), s);
        try {
            assertEquals(storage.listMapSize(), mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
            assertEquals(storage.getMap(m.getName()),mapper.readValue(file, new TypeReference<List<MapResource>>() {}).stream().filter(map -> map.getName().equals(m.getName())).findFirst().get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();

    }

    @Test
    void getMapFromName(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        try {
            assertEquals(storage.getMap(m.getName()),mapper.readValue(file, new TypeReference<List<MapResource>>(){}).stream().filter(map -> map.getName().equals(m.getName())).findFirst().get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();
    }

    @Test
    void addMap(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        try {
            assertEquals(storage.listMapSize(), mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();
    }

    @Test
    void addCommand(){
        MapResource m = new MapFactory().newRandomMap();
        storage.addMap(m);
        storage.addCommand(m.getName(),collector);
        try {
            assertEquals(storage.listMapSize(), mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
            assertEquals(storage.getMap(m.getName()),mapper.readValue(file, new TypeReference<List<MapResource>>() {}).stream().filter(map -> map.getName().equals(m.getName())).findFirst().get());
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
        storage.addGame(m.getName(), collector, s);
        try {
            assertEquals(storage.listMapSize(), mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
            assertEquals(storage.getMap(m.getName()),mapper.readValue(file, new TypeReference<List<MapResource>>() {}).stream().filter(map -> map.getName().equals(m.getName())).findFirst().get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.resetMap();
    }

    @Test
    void addRandomMap(){
        storage.addRandomMap();
        try {
            assertEquals(this.storage.listMapSize(), mapper.readValue(file, new TypeReference<List<MapResource>>() {}).size());
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