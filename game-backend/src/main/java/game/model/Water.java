package game.model;

import game.model.Tile;

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
    public boolean equals(Object o) {
        return o instanceof Water;
    }

}
