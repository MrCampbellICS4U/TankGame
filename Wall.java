//Create wall objects for level design

import java.awt.Rectangle;

public class Wall extends Rectangle {

    int type;

    Wall(int x, int y, int width, int height, int type) {
        super(x, y, width, height);
        this.type = type;
    }
}