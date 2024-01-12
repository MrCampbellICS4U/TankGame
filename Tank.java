//Create player & enemy tank objects
//move(), shoot()

import java.util.ArrayList;

public class Tank {
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    double x, y, TEMP;
    int bounces;

    /**
     * Create new Bullet and add it to array of Bullets
     */
    public void shoot(int count, int maxFire, boolean shoot) {
        if (count == maxFire)
            return;
        if (shoot) {
            bullets.add(new Bullet(x, y, TEMP, TEMP, bounces));
            shoot = false;
        }
    }
}
