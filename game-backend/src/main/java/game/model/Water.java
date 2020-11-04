package game.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Water extends Tile {
    public Water() {
        super();
    }

    @Override
    public int hashCode() {
        return Objects.hash("Water");
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof Water;
    }

}
