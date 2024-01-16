//Create player & enemy tank objects
//move(), shoot()

import java.util.ArrayList;

public class Tank {

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    double x, y, TEMP;
    int bounces;
    int max;

    Tank(int x, int y, int bounces, int max) {
        this.x = x;
        this.y = y;
        this.bounces = bounces;
        this.max = max;
    }

    /**
     * Create new Bullet and add it to array of Bullets
     */
    public void shoot() {
        if (bullets.size() < max) {
            bullets.add(new Bullet(x, y, TEMP, TEMP, bounces, this));
        }
    }
}