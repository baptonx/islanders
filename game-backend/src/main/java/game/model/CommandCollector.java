package game.model;

import java.util.ArrayList;
import java.util.List;

public class CommandCollector {
    private String playerName;
    private List<Command> commands;

    public CommandCollector(String p, List<Command> c) {
        this.playerName = p;

        this.commands

        this.commands = new ArrayList<>(c);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
