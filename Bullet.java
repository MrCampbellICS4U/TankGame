
//Create bullet objects via Tank.shoot()
import java.awt.Rectangle;
import java.util.Random;

public class Bullet {
	final int WIDTH = 10;
	final int HEIGHT = 10;
	Random ra = new Random();
	double x, y;
	double speedx = 5;
	double speedy = 5;
	int maxBounces;
	int bounces = 0;
	Rectangle hitbox;
	Tank tank;

	Bullet(double bx, double by, double xspeed, double yspeed, int bounce, Tank tankk) {
		x = bx;
		y = by;
		speedx = xspeed;
		speedy = yspeed;
		maxBounces = bounce;
		hitbox = new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
		tank = tankk;
	}

	public void move(int panW, int panH) {
		if (y >= panH - HEIGHT || y <= 0) {
			bounces++;
			if (bounces <= maxBounces)
				speedy *= -1;
			else
				tank.bullets.remove(this);
		}

		if (x >= panW - WIDTH || x <= 0) {
			bounces++;
			if (bounces <= maxBounces)
				speedx *= -1;
			else
				tank.bullets.remove(this);
		}

		y += speedy;
		x += speedx;
	}
}