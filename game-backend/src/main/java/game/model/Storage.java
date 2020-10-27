package game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Storage {

    private List<MapResource> listMap;

    public Storage() {
        super();
        this.listMap = new ArrayList<>();
    }

    public List<MapResource> getListMap() {
        return this.listMap;
    }

    public MapResource getMapFromName(String name) {
        return this.listMap.stream().filter(map -> map.getName() == name).findFirst().get();
    }

    public void addMap(MapResource m) {
        listMap.add(m);
    }

}
