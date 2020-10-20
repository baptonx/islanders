package game.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Executable;

import static org.junit.jupiter.api.Assertions.*;

class MapResourceTest {
    private MapResource map_test;

    @BeforeEach
    void setUp() {
        map_test = new MapResource();
    }

    @Test
    void getName() {
        assertEquals("CarteBG", map_test.getName());
    }

    @Test
    void getTileException() throws Exception {
        int position1 = 150;
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> map_test.getTile(position1));
    }


    @Test
    void setName() {
        String newname = "Nouvelle Map";
        map_test.setName(newname);
        assertEquals(newname, map_test.getName());
    }

    @Test
    void getScore() {
    }

    @Test
    void setScore() {
    }
