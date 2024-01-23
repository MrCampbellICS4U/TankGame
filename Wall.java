//Create wall objects for level design

import java.awt.Rectangle;

public class Wall extends Rectangle {

    int type;
    int gridx, gridy;

    Wall(int x, int y, int width, int height, int type, int gridx, int gridy) {
        super(x, y, width, height);
        this.type = type;
        this.gridx = gridx;
        this.gridy = gridy;
    }
}