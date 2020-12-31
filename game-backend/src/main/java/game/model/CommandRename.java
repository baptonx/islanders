package game.model;

import java.util.List;
import java.util.Objects;

public class CommandRename extends Command {
    private String mementoNomJoueur;

    public CommandRename() {
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommandRename that = (CommandRename) o;
        return mementoNomJoueur == that.mementoNomJoueur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mementoNomJoueur);
    }
}
