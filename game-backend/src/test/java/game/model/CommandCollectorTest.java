package game.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandCollectorTest {
    CommandCollector collector;
    public List<Command> commands;


    @BeforeEach
    void setUp(){
        commands= new ArrayList<>();
        commands.add(new MoveCityBlock(0,1));
        commands.add(new PutCityBlock(1,2));
        commands.add(new MoveCityBlock(2,3));
        collector = new CommandCollector("Paul", commands);
    }

    @Test
    void getPlayerName() {
        assertEquals("Paul", collector.getPlayerName());
    }

    @Test
    void setPlayerName() {
        collector.setPlayerName("Hugo");
        assertEquals("Hugo", collector.getPlayerName());
    }

    @Test
    void getCommands() {
        assertEquals(commands, collector.getCommands());
    }

    @Test
    void setCommands() {
        List<Command> c = new ArrayList<>();
        c.add(new MoveCityBlock(0,1));
        c.add(new PutCityBlock(1,2));
        c.add(new MoveCityBlock(2,3));
        collector.setCommands(c);
        assertEquals(c, collector.getCommands());
    }

    @Test
    void testHashCode() {
        CommandCollector c1 = new CommandCollector();
        CommandCollector c2 = new CommandCollector();
        System.out.println(c1.hashCode());
        Assertions.assertTrue(c1.hashCode() == c2.hashCode());
        assertFalse(c1.hashCode() == collector.hashCode());
    }
    @Test
    void testEquals() {
        CommandCollector c1 = new CommandCollector();
        CommandCollector c2 = new CommandCollector();
        Assertions.assertTrue(c1.equals(c2) && c2.equals(c1));
        assertTrue(c1.equals(c2));
        c1.setPlayerName("toto");
        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(null));
    }
}