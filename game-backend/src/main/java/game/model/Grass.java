package game.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Grass extends Tile {

    public Grass() {
        super();
    }

    public boolean equals(Object o) {
        return o instanceof Grass;
    }
}
