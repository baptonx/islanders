package game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandCollectorTest {
    Command c1;
    Command c2;
    Command c3;
    CommandCollector collector;

    @BeforeEach
    void setUp(){
        List<Command> commands = new ArrayList<>();
        commands.add(c1);
        commands.add(c2);
        commands.add(c3);
    }

    @Test
    void getPlayerName() {
    }

    @Test
    void setPlayerName() {
    }

    @Test
    void getCommands() {
    }

    @Test
    void setCommands() {

    }

    @Test
    void testGetPlayerName() {
    }

    @Test
    void testSetPlayerName() {
    }

    @Test
    void testGetCommands() {
    }

    @Test
    void testSetCommands() {
    }
}