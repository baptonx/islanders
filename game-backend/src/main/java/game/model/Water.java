package game.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Water extends Tile {
    public Water() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof Water;
    }

}
