package game.resource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private ArrayList<MapResource> listMap;

    public Storage(){
        super();
        this.listMap = new ArrayList<MapResource>();
    }

    public ArrayList<MapResource> getListMap(){
        return this.listMap;
    }

    public void addMap(MapResource m) {
        listMap.add(m);
    }

}
