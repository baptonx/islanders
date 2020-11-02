package game.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type")
@XmlSeeAlso({Grass.class, Tree.class, Water.class})
public abstract class Tile {
    public boolean equals(Object o) {
        System.out.println(this.getClass());
        if (this.getClass() == o.getClass()) {
            return true;
        }
        return false;
    }
}
