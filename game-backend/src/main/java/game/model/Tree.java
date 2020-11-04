package game.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Tree extends Tile {

    public Tree() {
        super();
    }

    @Override
    public int hashCode() {
        return Objects.hash("Tree");
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof Tree;
    }
}


