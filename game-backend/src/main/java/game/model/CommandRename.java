package game.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class CommandRename extends Command {
    private String nouveauNomJoueur;
    private String mementoNomJoueur;

    public CommandRename() {
        mementoNomJoueur = "player";
        nouveauNomJoueur = "";
    }

    public String getMementoNomJoueur() {
        return this.mementoNomJoueur;
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
        return mementoNomJoueur.equals(that.getMementoNomJoueur());
    }

    public String getNouveauNomJoueur() {
        return nouveauNomJoueur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mementoNomJoueur);
    }
}
