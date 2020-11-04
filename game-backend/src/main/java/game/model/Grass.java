package game.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Grass extends Tile {

    public Grass() {
        super();
    }

    @Override
    public int hashCode() {
        return Objects.hash("Grass");
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof Grass;
    }
}
