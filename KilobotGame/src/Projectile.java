

import java.awt.Rectangle;

public class Projectile {

	private int x, y, speedX;
	private boolean visible;
	private Rectangle r;

	public Projectile(int startX, int startY) {
		x = startX;
		y = startY;
		speedX = 7;
		visible = true;
		r = new Rectangle(0, 0, 0, 0);
	}

	public void update() {
		x += speedX;
		r.setBounds(x, y, 10, 5);
		if (x > 800) {
			visible = false;
			r = null;
		}
		if (x < 800) {
			checkCollision();
		}

	}

	private void checkCollision() {
		
		for(Heliboy b : StartingClass.getHeliboys()){
			if(r.intersects(b.r)){
				visible=false;
				if(b.health>0)
					b.health-=1;
				else{
					b.setCenterY(-100);
					StartingClass.score+=5;
				}	
			}
		}
		for(Monster m : StartingClass.getMonsters()){
			if(r.intersects(m.r)){
				visible=false;
				if(m.health>0)
					m.health-=1;
				else{
					m.setCenterY(-100);
					StartingClass.score+=10;
				}
			}
		}
		
		/**
		if (r.intersects(StartingClass.hb.r)) {
			visible = false;
			if (StartingClass.hb.health > 0) {
				StartingClass.hb.health -= 1;
			}
			if (StartingClass.hb.health == 0) {
				StartingClass.hb.setCenterX(-100);
				StartingClass.score += 5;

			}
		}

		if (r.intersects(StartingClass.hb2.r)) {
			visible = false;
			if (StartingClass.hb2.health > 0) {
				StartingClass.hb2.health -= 1;
			}
			if (StartingClass.hb2.health == 0) {
				StartingClass.hb2.setCenterX(-100);
				StartingClass.score += 5;

			}
		}
		if(r.intersects(StartingClass.monster.r)){
			visible = false;
			if (StartingClass.monster.health > 0) {
				StartingClass.monster.health -= 1;
			}
			if (StartingClass.monster.health == 0) {
				StartingClass.monster.setCenterX(-100);
				StartingClass.score += 15;

			}
		}
		*/
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the speedX
	 */
	public int getSpeedX() {
		return speedX;
	}

	/**
	 * @param speedX
	 *            the speedX to set
	 */
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
