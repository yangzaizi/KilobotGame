

import java.awt.Image;
import java.awt.Rectangle;

public class Coin {
	
	private int CoinX, CoinY, speedX;
    public Image coinImage;
    private Robot robot = StartingClass.getRobot();
    private Background bg = StartingClass.getBg1();
    private Rectangle r;
    
    public Coin(int x, int y) {
        CoinX = x*33;
        CoinY = y*35; 
        r = new Rectangle();
        setCoinImage(StartingClass.coin);
    }
    

    public void checkSideCollision(Rectangle rleft, Rectangle rright, Rectangle leftfoot, Rectangle rightfoot) {
        
            if (rleft.intersects(r)) {
               StartingClass.score+=5;
               setCoinX(-100);
           
    
            }else if (leftfoot.intersects(r)) {
               StartingClass.score+=5;
               setCoinX(-100);
            }
        
    }

    public void update() {
        // TODO Auto-generated method stub
    	speedX = bg.getSpeedX() * 5;
        CoinX += speedX;
        r.setBounds(CoinX, CoinY, 33, 35);
        
        if (r.intersects(Robot.yellowRed)) {
			
			checkSideCollision(Robot.rect3, Robot.rect4, Robot.footleft, Robot.footright);
			
		}
    }

    public int getCoinX() {
        return CoinX;
    }

    public void setCoinX(int tileX) {
        this.CoinX = tileX;
    }

    public int getCoinY() {
        return CoinY;
    }

    public void setCoinY(int tileY) {
        this.CoinY = tileY;
    }

    public Image getCoinImage() {
        return coinImage;
    }

    public void setCoinImage(Image coinImage) {
        this.coinImage = coinImage;
    }

}
