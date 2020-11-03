package game.model;

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
}