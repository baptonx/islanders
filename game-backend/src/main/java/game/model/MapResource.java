package game.model;

import com.github.javafaker.Faker;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@XmlRootElement
public class MapResource {
    private String name;
    private List<Score> scores;
    private Tile[] tabTiles;
    private List<CommandCollector> commandsCollectors;

    public MapResource() {
        final Faker faker = new Faker();
        this.name = faker.dragonBall().character();
        this.tabTiles = new Tile[100];
        this.scores = new ArrayList<>();
        this.commandsCollectors = new ArrayList<>();
    }

    public MapResource(final String name) {
        this.name = name;
        this.scores = new ArrayList<>();
        this.tabTiles = new Tile[100];
        this.commandsCollectors = new ArrayList<>();
    }

    public void generateRandomMap() {
        final int[] indexArray = new Random().ints(100, 0, 3).toArray();
        IntStream.range(0, indexArray.length).forEach(i -> {
            if (indexArray[i] == 0) {
                tabTiles[i] = new Grass();
            } else if (indexArray[i] == 1) {
                tabTiles[i] = new Tree();
            } else if (indexArray[i] == 2) {
                tabTiles[i] = new Water();
            }
        });
    }

    public Tile getTile(final int position) throws ArrayIndexOutOfBoundsException {
        if (position < 100 && position > 0) {
            return tabTiles[position];
        } else {
            throw new ArrayIndexOutOfBoundsException("Tile position must be between 0 and 100");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MapResource{" +
                "name='" + name + '\'' +
                ", scores=" + scores +
                ", tabTiles=" + Arrays.toString(tabTiles) +
                '}';
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Score> getScores() {
        return scores;
    }

    public List<Score> getTopScores() {
        this.scores.sort(new ScoreComparator());
        return this.scores.stream().limit(5).collect(Collectors.toList());
    }

    public void setScores(final List<Score> newScore) {
        this.scores = newScore;
    }

    // Renvoie vrai si le score est ajouté/mis à jour
    public boolean addScore(final Score s) {
        //Si le joueur n'a aucun score dans la map
        if (scores.stream().noneMatch(sc -> sc.getPlayer().equals(s.getPlayer()))) {
            scores.add(s);
            return true;
        } else {
            final Optional<Score> existingScore = scores.stream()
                    .filter(sc -> sc.getPlayer().equals(s.getPlayer()))
                    .findFirst();
            if (s.getScore() >= existingScore.get().getScore()) {
                existingScore.get().setScore(s.getScore());
                return true;
            } else {
                return false;
            }
        }
    }

    public void setTabTiles(final Tile[] tabTiles) {
        this.tabTiles = tabTiles.clone();
    }

    public Tile[] getTabTiles() {
        return tabTiles.clone();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MapResource that = (MapResource) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(scores, that.scores) &&
                Arrays.equals(tabTiles, that.tabTiles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, scores);
        result = 31 * result + Arrays.hashCode(tabTiles);
        return result;
    }

    public void addCommand(final CommandCollector c) {
        if (commandsCollectors.stream().noneMatch(com -> com.getPlayerName().equals(c.getPlayerName()))) {
            commandsCollectors.add(c);
        } else {
            commandsCollectors.stream()
                    .filter(com -> com.getPlayerName().equals(c.getPlayerName()))
                    .findFirst().get().setCommands(c.getCommands());
            //existingCommandCollector.get().setPlayerName(c.getPlayerName());

        }
    }

    public List<CommandCollector> getCommandsCollectors() {
        return commandsCollectors;
    }

    public CommandCollector getCommandCollector(final String player) throws IllegalArgumentException {
        final Optional<CommandCollector> cc = this.commandsCollectors.stream().filter(command -> command.getPlayerName().equals(player)).findFirst();
        if (cc.isEmpty()) {
            throw new IllegalArgumentException("Il n'existe pas de commandes enregistrées pour ce joueur");
        }
        return cc.get();
    }

    public void setCommandsCollector(final List<CommandCollector> commandsCollector) {
        this.commandsCollectors = commandsCollector;
    }

    public void addGame(final Score score, final CommandCollector commands) throws IllegalArgumentException {
        if (!score.getPlayer().equals(commands.getPlayerName())) {
            throw new IllegalArgumentException("Commandes et scores ne correspondent pas aux même joueur");
        }
        if (addScore(score)) {
            addCommand(commands);
        }
    }
}
