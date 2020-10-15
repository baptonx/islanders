package game.resource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private List<MapResource> listMap;

    public Storage(){
        super();
        this.listMap = new ArrayList<>();
    }

    public List<MapResource> getListMap(){

        return this.listMap;

    }

    public void addMap(MapResource m) {

        listMap.add(m);

    }

}
