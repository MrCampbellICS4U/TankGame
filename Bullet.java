
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
	int bounces;
	Rectangle hitbox;

	Bullet(double bx, double by, double xspeed, double yspeed, int bounce) {
		x = bx;
		y = by;
		speedx = xspeed;
		speedy = yspeed;
		bounces = bounce;
		hitbox = new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
	}

	public void move(int panW, int panH) {
		if (y >= panH - HEIGHT || y <= 0)
			speedy *= -1;
		if (x >= panW - WIDTH || x <= 0)
			speedx *= -1;

		y += speedy;
		x += speedx;
	}
}