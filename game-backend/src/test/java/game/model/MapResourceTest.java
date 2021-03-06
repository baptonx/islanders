package game.model;

import game.model.MapResource;
import game.model.Score;
import game.model.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class MapResourceTest {
    private MapResource map_test;
    private MapResource m1;
    private MapResource m2;
    private List<Score> scores;
    private CommandCollector collector;
    List<Command> commands;

    @BeforeEach
    void setUp() {
        map_test = new MapResource("CarteBG");
        m1 = new MapResource("map1");
        m2 = new MapResource("map1");

        scores = new ArrayList<>();
        Score scoreB = new Score("bapt", 10000);
        Score scoreP = new Score("paul", 5000);
        scores.add(scoreB);
        scores.add(scoreP);

        commands = new ArrayList<>();
        commands.add(new MoveCityBlock(0, 1));
        commands.add(new PutCityBlock(1, 2));
        commands.add(new MoveCityBlock(2, 3));
        collector = new CommandCollector("Paul", commands);
    }

    @Test
    void getName() {
        assertEquals("CarteBG", map_test.getName());
    }

    @Test
    void constructorMapResourceWithoutParam() {
        MapResource m = new MapResource();
        assertTrue(m.getScores().isEmpty());
        assertArrayEquals(m.getTabTiles(), new Tile[100]);
    }

    @Test
    void constructorMapResourceWithParam() {
        assertEquals("CarteBG", map_test.getName());
        assertArrayEquals(map_test.getTabTiles(), new Tile[100]);
        assertTrue(map_test.getScores().isEmpty());
    }

    @Test
    void setScoreMapResource() {
        map_test.setScores(scores);
        assertEquals(scores, map_test.getScores());
    }

    @Test
    void addScoreMapResource() {
        map_test.addScore(scores.get(1));
        assertTrue(map_test.getScores().contains(scores.get(1)));
    }

    @Test
    void getTile() {
        MapFactory mf = new MapFactory();
        Tile t = mf.newRandomMap().getTile(12);
        assertThat(t, instanceOf(Tile.class));
    }

    @Test
    void toStringTest() {
        assertEquals(map_test.toString(), "MapResource{name='CarteBG', scores=[], tabTiles=[null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]}");
    }

    @Test
    void getTileException() throws Exception {
        int position1 = 150;
        int position2 = -12;
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> map_test.getTile(position1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> map_test.getTile(position2));
    }


    @Test
    void setName() {
        String newname = "Nouvelle Map";
        map_test.setName(newname);
        assertEquals(newname, map_test.getName());
    }

    @Test
    void generateRandomMap() {
        map_test.generateRandomMap();
        Arrays.stream(map_test.getTabTiles()).forEach(object ->
                assertThat(object, instanceOf(Tile.class))
        );
        assertEquals(100, map_test.getTabTiles().length);
    }

    @Test
    void getTopScores() {
        List<Score> scores = new ArrayList<>();
        scores.add(new Score("bapt", 92));
        scores.add(new Score("bapt", 52));
        scores.add(new Score("bapt", 72));
        scores.add(new Score("bapt", 82));
        scores.add(new Score("bapt", 62));
        scores.add(new Score("bapt", 42));
        map_test.setScores(scores);
        scores.sort(new ScoreComparator());
        scores.remove(5);
        System.out.println(scores.toString());
        List<Score> sc = map_test.getTopScores();
        System.out.println(sc.toString());
        assertEquals(scores, sc);
    }

    @Test
    void addScore() {
        Score score1 = new Score("bapt", 10000);
        map_test.addScore(score1);
        assertTrue(map_test.getScores().contains(score1));
        Score score2 = new Score("hugo", 5000);
        map_test.addScore(score2);
        assertTrue(map_test.getScores().contains(score2));
        Score score3 = new Score("hugo", 8000);
        map_test.addScore(score3);
        assertEquals(2, map_test.getScores().size());
        assertEquals(8000, map_test.getScores().get(1).getScore());
    }

    @Test
    void addCommand() {
        map_test.addCommand(collector);
        assertTrue(map_test.getCommandsCollectors().contains(collector));
        List<Command> listCommands = new ArrayList<>();
        listCommands.add(new PutCityBlock(1, 2));
        listCommands.add(new MoveCityBlock(2, 3));
        listCommands.add(new PutCityBlock(4, 2));
        CommandCollector cc2 = new CommandCollector("Paul", listCommands);
        map_test.addCommand(cc2);
        assertTrue(map_test.getCommandsCollectors().contains(cc2));
        assertEquals(map_test.getCommandsCollectors().stream().map(command -> command.getPlayerName().equals("Paul")).collect(Collectors.toList()).size(), 1);
    }

    @Test
    void addGameLowerScore() {
        map_test.addGame(new Score("hugz", 27), new CommandCollector("hugz", commands));
        map_test.addGame(new Score("hugz", 20), new CommandCollector("hugz", commands));
        assertEquals(map_test.getScores().stream().filter(score -> score.getPlayer().equals("hugz")).collect(Collectors.toList()).size(), 1);
        assertEquals(map_test.getScores().stream().filter(score -> score.getPlayer().equals("hugz")).findFirst().get().getScore(), 27);
        assertThrows(IllegalArgumentException.class, () -> {
            map_test.addGame(new Score("hugz", 27), collector);
        });

    }

    @Test
    void setCommandCollectors() {
        CommandCollector c1 = new CommandCollector();
        CommandCollector c2 = new CommandCollector();
        CommandCollector c3 = new CommandCollector();
        List<CommandCollector> lcc = new ArrayList<>();
        lcc.add(c1);
        lcc.add(c2);
        lcc.add(c3);
        map_test.setCommandsCollector(lcc);
        assertEquals(lcc, map_test.getCommandsCollectors());

    }

    @Test
    void testEquals() {
        MapFactory mf = new MapFactory();
        MapResource m3 = mf.newRandomMap();
        MapResource m4 = mf.newRandomMap();
        Assertions.assertEquals(m1, m2);
        Assertions.assertTrue(m1.equals(m2) && m2.equals(m1));
        assertNotEquals(m3, map_test);
        assertNotEquals(map_test, null);
        assertNotEquals(map_test, String.class);
        assertEquals(m3, m3);
        m3.setName("boo");
        m4.setName("boo");
        assertNotEquals(m3, m4);
        m3.setName("freee");
        m3.setScores(this.scores);
        m4.setScores(this.scores);
        assertNotEquals(m3, m4);
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(m1.hashCode(), m2.hashCode());
        assertNotEquals(m1.hashCode(), map_test.hashCode());
    }

}