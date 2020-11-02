package game.model;

import game.model.Tile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Water extends Tile {
    public Water() {
        super();
    }

    public boolean equals(Object o) {
        return o instanceof Water;
    }


}
