package game.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tree extends Tile {

    public Tree(){
        super();
    }

    public boolean equals(Object o) {
        return o instanceof Tree;
    }
}


