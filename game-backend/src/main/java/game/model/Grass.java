package game.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Grass extends Tile {

    public Grass() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Grass;
    }
}
