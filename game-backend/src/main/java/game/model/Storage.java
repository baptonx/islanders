package game.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Storage {

    private List<MapResource> listMap;
    private ObjectMapper mapper = new ObjectMapper();
    private File file;

    public Storage(final String path) throws IOException {
        super();
        try {
            file = new File(path);
            this.listMap = mapper.readValue(file, new TypeReference<List<MapResource>>() {
            });
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
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
    public MapResource getMap(final String name) throws IllegalArgumentException {
        Optional<MapResource> m = listMap.stream().filter(map -> map.getName().equals(name)).findFirst();
        if (m.isEmpty()) {
            throw new IllegalArgumentException("Il n'existe aucune map de ce nom");
        }
        return m.get();
    }

    public void addMap(final MapResource m) throws IllegalArgumentException, IOException {
        if (listMap.stream().noneMatch(maps -> maps.getName().equals(m.getName()))) {
            listMap.add(m);
            this.refreshMap();
        } else {
            throw new IllegalArgumentException("Une map avec le même nom existe déjà");
        }
    }

    public void addRandomMap() throws IllegalArgumentException, IOException {
        final MapResource m = new MapFactory().newRandomMap();
        this.addMap(m);
    }

    public void addScore(final String mapName, final Score s) throws IllegalArgumentException, IOException {
        final MapResource m = this.getMap(mapName);
        listMap = listMap.stream().filter(map -> !map.getName().equals(mapName)).collect(Collectors.toList());
        m.addScore(s);
        this.addMap(m);
    }

    public void addCommand(final String mapName, final CommandCollector c) throws IllegalArgumentException, IOException {
        final MapResource m = this.getMap(mapName);
        listMap = listMap.stream().filter(map -> !map.getName().equals(mapName)).collect(Collectors.toList());
        m.addCommand(c);
        this.addMap(m);
    }

    public void addGame(final String mapName, final CommandCollector c, final Score s) throws IllegalArgumentException, IOException {
        final MapResource m = this.getMap(mapName);
        final MapResource temp = m;
        listMap = listMap.stream().filter(map -> !map.getName().equals(mapName)).collect(Collectors.toList());
        try {
            m.addGame(s, c);
            this.addMap(m);
        } catch (IllegalArgumentException e) {
            this.addMap(temp);
            throw e;
        }
    }

    public List<String> getCommandCollectorFromMap(final String name) throws IllegalArgumentException {
        return this.getMap(name).getCommandsCollectors().stream().map(command -> command.getPlayerName()).collect(Collectors.toList());
    }

    public List<Command> getCommands(final String map_name, final String player_name) throws IllegalArgumentException {
        return this.getMap(map_name).getCommandCollector(player_name).getCommands();
    }

    public void refreshMap() throws IOException {
        try {
            mapper.writeValue(file, listMap);
        } catch (IOException e) {
            throw e;
        }
    }

    public void resetMap() throws IOException {
        listMap = new ArrayList<>();
        this.refreshMap();
    }

}
