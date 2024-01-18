//Create player & enemy tank objects
//move(), shoot()

import java.awt.Rectangle;
import java.util.ArrayList;

public class Tank extends Rectangle {

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    double x, y;
    int bounces;
    int max;

    Tank(int x, int y, int width, int height, int bounces, int max) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.bounces = bounces;
        this.max = max;
    }

    /**
     * Create new Bullet and add it to array of Bullets
     */
    public void shoot(double dx, double dy) {
        // if (bullets.size() < max) {
        bullets.add(new Bullet(x, y, dx, dy, width / 5, height / 5, bounces, this));
        // }
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
        super.x = (int) x;
        super.y = (int) y;
    }
}