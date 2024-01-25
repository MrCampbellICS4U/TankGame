//Create mines

import java.awt.Rectangle;

public class Bomb extends Rectangle {
    double x, y;
    int bombTick;
    Tank tank;

    Bomb(double x, double y, Tank tank) {
        super((int) (x - Main.size * 1.5), (int) (y - Main.size * 1.5), Main.size * 3, Main.size * 3);
        bombTick = 0;
        this.x = x;
        this.y = y;
        this.tank = tank;
    }
}