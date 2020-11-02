package game.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tree extends Tile {

    public Tree(){
        super();
    }
    public static void main(String args[]){
        Tree t = new Tree();
        System.out.println(t);
    }
}


