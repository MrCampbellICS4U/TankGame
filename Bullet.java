//Create bullet objects via Tank.shoot()
import java.awt.Rectangle;
import java.util.Random;
public class Bullet {
	Random ra = new Random();
	double x, y;
	int speedx = 5;
	int speedy = 5;
	int width = 10;
	int height = 10;
	Rectangle hitbox;
	Bullet(double bx, double by){
		 x = bx;
		 y = by;
		hitbox = new Rectangle((int)x, (int)y, width, height);
	 }
	public void shoot(int panW, int panH) {
//		if (count > maxFire) return;
			Bullet b = new Bullet (ra.nextInt(200, 401), ra.nextInt(200, 401));
			Main.bullets.add(b);
				if (y >= panH - height || y <= 0) {
					speedy *= -1; 
					y = 300;
					System.out.print(speedy);
				}
				if (x >= (panW - width) || x <= 0) {
					 speedx *= -1;
					 x = 300;
					 System.out.println(speedx);
				}
				y += speedy;
				x += speedx;
			
		/* **NOTE**
		 * This method is to be used once main class and tank class are created
		 * panW and panH will not be parameters in the future
		 */
	}
}