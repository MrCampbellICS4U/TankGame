
//Create bullet objects via Tank.shoot()
import java.awt.Rectangle;

public class Bullet extends Rectangle {
    double x, y, dx, dy;
    int bounces;
    Tank tank;

    Bullet(double x, double y, double dx, double dy, int width, int height, int bounces, Tank tank) {
        super((int) x, (int) y, width, height);
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.bounces = bounces;
        this.tank = tank;
    }

    public void move() {
        // if (collide w wall) {
        // bounces--;
        // if (bounces >= 0)
        // dy *= -1;
        // else
        // tank.bullets.remove(this);
        // }

        // if (collide w wall) {
        // bounces--;
        // if (bounces >= 0)
        // dx *= -1;
        // else
        // tank.bullets.remove(this);
        // }
        y += dy;
        x += dx;
        super.setLocation((int) x, (int) y);
    }
}
