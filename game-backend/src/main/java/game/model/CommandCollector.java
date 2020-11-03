package game.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CommandCollector {
    private String playerName;
    private List<Command> commands;

    public CommandCollector(){
        this.playerName = "";
        this.commands = new ArrayList<>();
    }

    public CommandCollector(String p, List<Command> c) {
        this.playerName = p;
        this.commands = c;
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
