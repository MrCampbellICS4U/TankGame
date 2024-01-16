
//Create bullet objects via Tank.shoot()
import java.awt.Rectangle;

public class Bullet {
    final int WIDTH = 10;
    final int HEIGHT = 10;
    double x, y, dx, dy;
    int bounces;
    Rectangle hitbox;
    Tank tank;

    Bullet(double x, double y, double dx, double dy, int bounces, Tank tank) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.bounces = bounces;
        hitbox = new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
        this.tank = tank;
    }

    public void move(int panW, int panH) {
        if (y >= panH - HEIGHT || y <= 0) {
            bounces--;
            if (bounces >= 0)
                dy *= -1;
            else
                tank.bullets.remove(this);
        }

        if (x >= panW - WIDTH || x <= 0) {
            bounces--;
            if (bounces >= 0)
                dx *= -1;
            else
                tank.bullets.remove(this);
        }

        y += dy;
        x += dx;
        hitbox.translate((int) dx, (int) dy);
    }
}
