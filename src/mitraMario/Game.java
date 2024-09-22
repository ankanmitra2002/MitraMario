package mitraMario;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fixedObjects.Wall;
import graphics.Sprite;
import graphics.SpriteSheet;
import input.KeyInput;
import movingObjects.Entity;
import movingObjects.Player;



public class Game extends Canvas implements Runnable{

	public static final String TITLE = "MitraMario";
	private Thread thread;
	private boolean running = false;
	public static Handler handler;
	public static SpriteSheet charSheet;
	public static SpriteSheet gombaSheet;
	public static SpriteSheet dragonSheet;
	public static SpriteSheet coinSheet;
	public static Image background; 
	public static Image bricks; 
	public static Image mushroom;
	public static Image castle;
	public static Sprite player[] = new Sprite[8];
	public static Sprite gomba[] = new Sprite[10];
	public static Sprite dragon[] = new Sprite[8];
	public static Image coin; 
	public static Camera cam;
	public static int[][]tile;
	 private JFrame frame;
	public Game() {
        frame = new JFrame(TITLE);
        frame.add(this);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

	}
	private void init() {
		handler = new Handler(this);
		cam = new Camera();
		background = new ImageIcon(getClass().getResource("/bg.jpg")).getImage();
		charSheet = new SpriteSheet("/characterspritesheet.png");
		bricks =  new ImageIcon(getClass().getResource("/brick.png")).getImage();
		mushroom =  new ImageIcon(getClass().getResource("/mushroom.png")).getImage();
		castle = new ImageIcon(getClass().getResource("/castle.png")).getImage();
		dragonSheet = new SpriteSheet("/dragon.png");
		gombaSheet = new SpriteSheet("/gomba.png");
		coin = new ImageIcon(getClass().getResource("/coin.png")).getImage();
		
		
			for(int i=0;i<player.length;i++) {
				player[i] = new Sprite(charSheet,i+1,0);
			}
			for(int x=0;x<gomba.length;x++) {
				gomba[x] = new Sprite(gombaSheet,x,0);
			}
			for(int x=0;x<dragon.length;x++) {
				dragon[x] = new Sprite(dragonSheet,x,0);
			}
			

		addKeyListener(new KeyInput());

		try {
			Scanner sc = new Scanner(new File("src//maps//map_1.csv"));
			String line[] = sc.next().split(",");
			int col = line.length;
			int row=1;
			while(sc.hasNext()) {
				sc.next();
				row++;
			}
			
			tile = new int[row][col];
			sc = new Scanner(new File("src//maps//map_1.csv"));
			int r=0,c=0;
			while(sc.hasNext()) {
				line = sc.next().split(",");
				for(String l : line) {
					try {
						tile[r][c++] = Integer.parseInt(l);
					}catch (Exception e) {
//						e.printStackTrace();
					}
				}
				r++;c=0;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		handler.createlevel(tile);
	}
	public synchronized void start() {
		if(running) {
			return;
		}
		running = true;
		thread = new Thread(this,"Thread");
		thread.start();
	}
	public synchronized void stop() {
		if(!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(background,0,0,this.getWidth(),this.getHeight(),null);
		g.translate(cam.getX(),cam.getY());
		handler.render(g);
		g.dispose();
		bs.show();
	}
	public void update() {
		handler.update();
		for(Entity e: handler.entity) {
			if(e.getId()==Id.player) {
				cam.update(e,this);
			}
		}
		
	}
	public int getFrameWidth() {
		return  this.getWidth();
	}
	public int getFrameHeight() {
		return  this.getHeight();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0.0;
		double ns = 1000000000.0/60.0;
		int frames = 0;
		int updates = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime = now;
			while(delta>=1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis()-timer > 1000) {
				timer += 1000;
//				System.out.println(frames+ " Frames Per Second " + updates + " Updates Per Second");
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	 public void gameOver(String s) {
	        new GameOverScreen(this,s,handler);
	        stop();
	    }
	 public void restart() {
	        frame.dispose();
	        Game newGame = new Game();
	        newGame.start();
	    }
	public static void main(String[] args){
		Game game = new Game();
		game.start();
	}
	
}

