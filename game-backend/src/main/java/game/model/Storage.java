package game.model;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.namespace.QName;


public class Storage {

    private List<MapResource> listMap;
    private ObjectMapper mapper = new ObjectMapper();
    private File file;

    public Storage(final String path) {
        super();
        file = new File(path);
        try {
            this.listMap = mapper.readValue(file, new TypeReference<List<MapResource>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MapResource> getMaps() {
        return this.listMap;
    }

    public List<String> getMapsName() {
        return listMap.stream().map(m -> m.getName()).collect(Collectors.toList());
    }


    public int listMapSize() {
        return listMap.size();
    }

    //Lancer une exception si pas le bon nom
    public MapResource getMap(final String name) {
        return listMap.stream().filter(map -> map.getName().equals(name)).findFirst().get();
    }

    public void addMap(final MapResource m) throws IllegalArgumentException {
        if (listMap.stream().noneMatch(maps -> maps.getName().equals(m.getName()))) {
            listMap.add(m);
            this.refreshMap();
        } else {
            throw new IllegalArgumentException("Map avec nom similaire déjà existant");
        }
    }

    public void addRandomMap() throws IllegalArgumentException {
        final MapResource m = new MapFactory().newRandomMap();
        this.addMap(m);
    }

    public void addScore(final String mapName, final Score s) throws IllegalArgumentException {
        final MapResource m = this.getMap(mapName);
        listMap = listMap.stream().filter(map -> !map.getName().equals(mapName)).collect(Collectors.toList());
        m.addScore(s);
        this.addMap(m);
    }

    public void addCommand(final String mapName, final CommandCollector c) throws IllegalArgumentException {
        final MapResource m = listMap.stream().filter(map -> map.getName().equals(mapName)).findFirst().get();
        listMap = listMap.stream().filter(map -> !map.getName().equals(mapName)).collect(Collectors.toList());
        m.addCommand(c);
        this.addMap(m);
    }

    public void addGame(final String mapName, final CommandCollector c, final Score s) {
        final MapResource m = this.getMap(mapName);
        listMap = listMap.stream().filter(map -> !map.getName().equals(mapName)).collect(Collectors.toList());
        m.addGame(s,c);
        this.addMap(m);
    }

    public List<String> getCommandCollectorFromMap(final String name) {
        return this.getMap(name).getCommandCollectors().stream().map(command -> command.getPlayerName()).collect(Collectors.toList());
    }

    public List<Command> getCommands(final String map_name, final String player_name) {
        return this.getMap(map_name).getCommandCollectors().stream().filter(command -> command.getPlayerName().equals(player_name)).findFirst().get().getCommands();
    }

    public void refreshMap() {
        try {
            mapper.writeValue(file, listMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetMap() {
        listMap = new ArrayList<>();
        this.refreshMap();
    }

}
