package game.model;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


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

    public MapResource getMapFromName(String name) {
        return this.listMap.stream().filter(map -> map.getName() == name).findFirst().get();
    }

    public void addMap(MapResource m) {
        listMap.add(m);
        try {
            mapper.writeValue(file, listMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void resetMap(){
        listMap = new ArrayList<>();
        try {
            mapper.writeValue(file, listMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
