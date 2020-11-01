package game.model;

public interface Command {
    void execute();
    void canDo();
    void done();
    void isDone();
    void undo();
    void redo();
}
