package game.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class UndoRedoCollector{
    Deque<Command> undos;
    Deque<Command> redos;

    public UndoRedoCollector(){
        undos = new ArrayDeque<>();
        redos = new ArrayDeque<>();
    }

    public void add(final Command command) {
        if(command!=(null)) {
            undos.add(command);
            redos = new ArrayDeque<>();
        }
    }

    public void undo() {
        if(!undos.isEmpty()) {
            redos.add(undos.getLast());
            Command com = undos.removeLast();
            com.undo();
        }
    }

    public void redo() {
        if(!redos.isEmpty()) {
            undos.add(redos.getLast());
            Command com = redos.removeLast();
            com.redo();
        }
    }

    public int getNbUndoables() {
        if(!undos.isEmpty()) {
            return undos.size();
        }
        return 0;
    }

    public int getNbRedoables() {
        if(!redos.isEmpty()) {
            return redos.size();
        }
        return 0;
    }
}

