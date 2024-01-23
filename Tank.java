//Create player & enemy tank objects
//move(), shoot()

import java.awt.Rectangle;
import java.util.ArrayList;

public class Tank extends Rectangle {

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    double x, y;
    int type;
    int bounces;
    int max;
    int maxBombs;
    int rotation, topRotation;

    Tank(int type, int x, int y, int width, int height, int bounces, int max, int maxBombs) {
        super(x + width, y, width, height);
        this.type = type;
        this.x = x;
        this.y = y;
        this.bounces = bounces;
        this.max = max;
        this.maxBombs = maxBombs;
    }

    /**
     * Create new Bullet and add it to array of Bullets
     */
    public void shoot(double dx, double dy) {
        if (bullets.size() < max) {
            Bullet b;
            bullets.add(
                    b = new Bullet((2 * x + width) / 2, (2 * y + height) / 2, dx, dy, width / 5, height / 5, bounces,
                            this));
            for (int i = 0; i < 7; i++)
                b.move();
        }
    }

    public void bomb() {
        if (bombs.size() < maxBombs) {
            bombs.add(new Bomb((2 * x + width) / 2, (2 * y + height) / 2, this));
        }
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
        super.x = (int) x;
        super.y = (int) y;
    }
}