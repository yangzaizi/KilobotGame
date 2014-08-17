



import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import kilobotgame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6180548797322738069L;
	private static Robot robot;
	//public static Heliboy hb, hb2;
	//public static Monster monster;
	private Image image, currentSprite, character, character2, character3, character4, character5,character6,character7,character8,
			characterDown, characterJumped, background, heliboy, heliboy2,
			heliboy3, heliboy4, heliboy5, dragon;
	public static Image tilegrassTop, tilegrassBot, tilegrassLeft,
			tilegrassRight, tiledirt, coin, flag;
	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private Animation anim, hanim;
	
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private ArrayList<Flag>flags = new ArrayList<Flag>();
	private static ArrayList<Heliboy>heliboys = new ArrayList<Heliboy>();
	private static ArrayList<Monster>monsters = new ArrayList<Monster>();
	public static int score = 0;
	private Font font = new Font(null, Font.BOLD, 30);
	private Thread thread;
	
	private ArrayList<String>maps = new ArrayList<String>();
	
	enum GameState {
		Running, Dead, Won
	}

	public static GameState state = GameState.Running;

	
	public static void main(String []args){
		StartingClass game = new StartingClass();
		game.init();
	}
	@Override
	public void init() {
		setSize(800, 480);
		setFocusable(true);
		setBackground(Color.BLACK);
		addKeyListener(this);
		//Frame frame = (Frame) this.getParent().getParent();
		setName("Q-bot Alpha");

		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}
		maps.add("map1");
		maps.add("map2");
		BufferedImage marios=null;
		BufferedImage[] sprites= new BufferedImage[8];
		try {
			marios = ImageIO.read(new File("data/agent.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width=150;
		int height=210;
		for(int i=0; i<2; i++){
			for(int j=0; j<4; j++){
				sprites[i*4+j]=marios.getSubimage(j*width, i*height, width, height);
			}
		}
		
		character= sprites[0];
		character2 = sprites[1];
		character3=sprites[2];
		character4=sprites[3];
		character5=sprites[4];
		character6=sprites[5];
		character7=sprites[6];
		character8=sprites[7];
		
		characterDown = getImage(base, "data/down.png");
		characterJumped = character;

		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");

		dragon = getImage(base, "data/monster.gif");
		background = getImage(base, "data/background.png");

		tiledirt = getImage(base, "data/tiledirt.png");
		tilegrassTop = getImage(base, "data/tilegrasstop.png");
		tilegrassBot = getImage(base, "data/tilegrassbot.png");
		tilegrassLeft = getImage(base, "data/tilegrassleft.png");
		tilegrassRight = getImage(base, "data/tilegrassright.png");
		coin= getImage(base, "data/coin.png");
		flag = getImage(base, "data/flag.png");
		anim= new Animation();
		hanim = new Animation();
		addAnimation();

		currentSprite=anim.getImage();

	}
	
	private void addAnimation(){
		
		anim.addFrame(character, 50);
		anim.addFrame(character2, 50);
		anim.addFrame(character3,50);
		anim.addFrame(character4,50);
		anim.addFrame(character5,50);
		anim.addFrame(character6,50);
		anim.addFrame(character7,50);
		anim.addFrame(character8,50);
			
		
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		robot = new Robot();
		try {
			loadMap("data/"+maps.get(Flag.getLevel())+".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//hb = new Heliboy(900, 360);
		//hb2 = new Heliboy(3000, 360);
		//monster = new Monster(5000, 340);
		thread = new Thread(this);
		thread.start();
		
	}

	private void loadMap(String filename) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
			// no more lines to read
			if (line == null) {
				reader.close();
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		height = lines.size();

		for (int j = 0; j < 12; j++) {
			String line = lines.get(j);
			for (int i = 0; i < width; i++) {
				

				if (i < line.length()) {
					char ch = line.charAt(i);
					int value=Character.getNumericValue(ch);
					if(value==1){
						Coin c = new Coin(i,j);
						coins.add(c);
					}
					else if(value==0){
						Flag f = new Flag(i+20,j-2);
						flags.add(f);
					}
					else if(value==3){
						Heliboy b = new Heliboy(i*50,340);
						heliboys.add(b);
					}
					else if(value==7){
						Monster m = new Monster(i*50,280);
						monsters.add(m);
					}
					else{	
						Tile t = new Tile(i, j,value );
						tilearray.add(t);
					}
				}

			}
		}
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void run() {
		if (state == GameState.Running) {
			while (true) {
				robot.update();
				if (robot.isJumped()) {
					currentSprite = characterJumped;
				} else if (robot.isJumped() == false
						&& robot.isDucked() == false) {
					currentSprite = anim.getImage();
				}

				ArrayList<Projectile> projectiles = robot.getProjectiles();
				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = projectiles.get(i);
					if (p.isVisible() == true) {
						p.update();
					} else {
						projectiles.remove(i);
						i--;
					}
				}
				updateTiles();
				updateCoins();
				updateFlags();
				//hb.update();
				//hb2.update();
				updateHeliboys();
				updateMonsters();
				//monster.update();
				bg1.update();
				bg2.update();
				animate();
				repaint();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (robot.getCenterY() > 500) {
					state = GameState.Dead;
				}
				
			}
		}
	}

	public void animate() {
		anim.update(10);
		hanim.update(50);
	}

	@Override
	public void update(Graphics g) {
		
		
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		if (state == GameState.Running) {
			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
			paintCoins(g);
			paintTiles(g);
			paintFlags(g);
			paintHeliboys(g);
			paintMonsters(g);
			ArrayList<Projectile> projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = projectiles.get(i);
				g.setColor(Color.YELLOW);
				g.fillRect(p.getX(), p.getY(), 10, 5);
			}
			
			g.drawImage(currentSprite, robot.getCenterX() - 50,
					robot.getCenterY()-11,91,78,this);
			
			//g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
					//hb.getCenterY() - 48, this);
			//g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
					//hb2.getCenterY() - 48, this);
			//g.drawImage(dragon, monster.getCenterX()-140, monster.getCenterY()-66, this);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
		} else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);
		}
		else if(state==GameState.Won){
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Level " + Flag.getLevel()+ " Complete", 240, 240);
		}

	}
	
	private void updateTiles() {

		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = tilearray.get(i);
			t.update();
		}

	}
	
	private void updateCoins(){
		for(int i=0; i<coins.size(); i++){
			Coin c = coins.get(i);
			c.update();
		}
	}
	
	private void updateFlags(){
		Flag f = flags.get(0);
		f.update();
	}
	
	private void updateHeliboys(){
		for(Heliboy b : heliboys){
			b.update();
		}
	}
	
	private void updateMonsters(){
		for(Monster m : monsters){
			m.update();
		}
	}
	
	private void paintCoins(Graphics g){
		for(int i=0; i<coins.size();i++){
			Coin c = coins.get(i);
			g.drawImage(c.getCoinImage(), c.getCoinX(), c.getCoinY(),this);
		}
	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}
	
	private void paintFlags(Graphics g){
		Flag f = flags.get(0);
		g.drawImage(f.getFlagImage(), f.getFlagX(), f.getFlagY(), 100, 160, this);
	}
	
	private void paintHeliboys(Graphics g){
		for(int i=0; i<heliboys.size(); i++){
			Heliboy b = heliboys.get(i);
			g.drawImage(hanim.getImage(), b.getCenterX(), b.getCenterY(), this);
		}
	}
	
	private void paintMonsters(Graphics g){
		for(Monster m : monsters){
			g.drawImage(dragon, m.getCenterX(), m.getCenterY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if (robot.isJumped() == false) {
				robot.setDucked(true);
				robot.setSpeedX(0);
			}
			break;
		case KeyEvent.VK_LEFT:
			
			robot.moveLeft();
			robot.setMovingLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			robot.setMovingRight(true);
			break;
		case KeyEvent.VK_SPACE:
			robot.jump();
			break;

		case KeyEvent.VK_CONTROL:
			if (robot.isDucked() == false) {
				robot.shoot();
				robot.setReadyToFire(false);
			}
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_DOWN:
			currentSprite = anim.getImage();
			robot.setDucked(false);
			break;
		case KeyEvent.VK_LEFT:
			robot.stopLeft();
			break;
		case KeyEvent.VK_RIGHT:
			robot.stopRight();
			break;
		case KeyEvent.VK_SPACE:
			break;
		case KeyEvent.VK_CONTROL:
			robot.setReadyToFire(true);
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Robot getRobot() {
		return robot;
	}
	
	public static void won(){
		state=GameState.Won;
	}
	
	public static ArrayList<Heliboy>getHeliboys(){
		return heliboys;
	}
	
	public static ArrayList<Monster>getMonsters(){
		return monsters;
	}
	
	
}
