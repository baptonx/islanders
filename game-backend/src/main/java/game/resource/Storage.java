package game.resource;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private List<MapResource> listMap;

    public Storage(){
        super();
        this.listMap = new ArrayList<MapResource>();
    }

    public List<MapResource> getListMap(){
        return this.listMap;
    }

}
