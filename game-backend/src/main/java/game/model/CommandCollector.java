package game.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
/** Objet contenant la liste des commandes faites par un joueur **/
public class CommandCollector {
    private String playerName;
    private List<Command> commands;

    public CommandCollector() {
        this.playerName = "";
        this.commands = new ArrayList<>();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommandCollector that = (CommandCollector) o;
        return Objects.equals(playerName, that.playerName) &&
                Objects.equals(commands, that.commands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, commands);

    }

    public CommandCollector(final String p, final List<Command> c) {
        this.playerName = p;
        this.commands = c;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(final List<Command> commands) {
        this.commands = commands;
    }
}
