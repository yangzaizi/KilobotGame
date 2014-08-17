

import java.awt.Image;
import java.awt.Rectangle;

public class Flag {
	private int FlagX, FlagY, speedX;
    public Image flagImage;
    private Robot robot = StartingClass.getRobot();
    private Background bg = StartingClass.getBg1();
    private Rectangle r;
    public static int level;
    public Flag(int x, int y) {
        FlagX = x*33;
        FlagY = y*35; 
        r = new Rectangle();
        setFlagImage(StartingClass.flag);
        level=0;
        
    }
    

    public void checkSideCollision(Rectangle rleft, Rectangle rright, Rectangle leftfoot, Rectangle rightfoot) {
        
            if (rleft.intersects(r)) {
            	
               StartingClass.won();
           
    
            }else if (leftfoot.intersects(r)) {
            	
               StartingClass.won();
            }
            
           
           
        
    }

    public void update() {
        // TODO Auto-generated method stub
    	speedX = bg.getSpeedX() * 5;
    	FlagX += speedX;
        r.setBounds(FlagX, FlagY, 100, 100);
        
        if (r.intersects(Robot.yellowRed)) {
			
			checkSideCollision(Robot.rect3, Robot.rect4, Robot.footleft, Robot.footright);
			
		}
    }

    public int getFlagX() {
        return FlagX;
    }

    public void setFlagX(int flagX) {
        this.FlagX = flagX;
    }

    public int getFlagY() {
        return FlagY;
    }

    public void setFlagY(int flagY) {
        this.FlagY = flagY;
    }

    public Image getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(Image flagImage) {
        this.flagImage = flagImage;
    }
    
    public static int getLevel(){
    	return level;
    }

}
