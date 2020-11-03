package game.model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.exception.StorageException;


public class Storage {

    private List<MapResource> listMap;
    private ObjectMapper mapper = new ObjectMapper();
    private File file;

    public Storage(String path) {
        super();
        file = new File(path);
        try {
            this.listMap = mapper.readValue(file, new TypeReference<List<MapResource>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MapResource> getListMap() {
        return this.listMap;
    }

    public int listMapSize(){
        return listMap.size();
    }

    //Lancer une exception si pas le bon nom
    public MapResource getMap(String name) {
        return listMap.stream().filter(map -> map.getName().equals(name)).findFirst().get();
    }

    public void addMap(MapResource m) throws StorageException {
        if(listMap.stream().noneMatch(maps -> maps.getName().equals(m.getName()))){
            listMap.add(m);
            this.refreshMap();
        }
        else{
            throw new StorageException("Map avec nom similaire déjà existant");
        }
    }

    public void addRandomMap() throws StorageException {
        MapResource m = new MapFactory().newRandomMap();
        this.addMap(m);
    }

    public void addScore(String mapName, Score s) throws StorageException {
        MapResource m = listMap.stream().filter(map -> map.getName().equals(mapName)).findFirst().get();
        listMap = listMap.stream().filter(map -> !map.getName().equals(mapName)).collect(Collectors.toList());
        m.addScore(s);
        this.addMap(m);
    }

    public void refreshMap(){
        try {
            mapper.writeValue(file, listMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetMap(){
        listMap = new ArrayList<>();
        this.refreshMap();
    }

}
