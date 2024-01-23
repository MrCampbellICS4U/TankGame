//Create mines

import java.awt.Rectangle;

public class Bomb extends Rectangle {
    Rectangle explosion;
    double x, y, width, height;
    int bombTick;
    Tank tank;

    Bomb(double x, double y, Tank tank) {
        bombTick = 0;
        this.x = x;
        this.y = y;
        this.tank = tank;
        explosion = new Rectangle((int) (x - Main.size * 3 / 2), (int) (y - Main.size * 3.2), Main.size * 3,
                Main.size * 3);
    }
}