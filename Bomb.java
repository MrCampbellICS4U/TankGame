//Create mines

import java.awt.Rectangle;

public class Bomb extends Rectangle {

    double x, y, width, height;
    int bombTick;
    Tank tank;

    Bomb(double x, double y, Tank tank) {
        bombTick = 0;
        this.x = x;
        this.y = y;
        this.tank = tank;
    }
}