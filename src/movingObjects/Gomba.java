package movingObjects;

import java.awt.Graphics;
import java.util.Random;

import fixedObjects.Tile;
import mitraMario.Game;
import mitraMario.Handler;
import mitraMario.Id;

public class Gomba extends Entity{
	private int frame = 0;
	private int frameDelay = 0;
	private Random random = new Random();
	public Gomba(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		int dir = random.nextInt(2);
		switch (dir) {
		case 0: 
			setVelX(-2);
			facing = 0;
			break;
		case 1: 
			setVelX(2);
			facing = 1;
			break;
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if(facing == 0) {
			g.drawImage(Game.gomba[frame].getBufferedImage(),x,y,width,height,null);
		}else if(facing == 1) {
			g.drawImage(Game.gomba[frame+5].getBufferedImage(),x,y,width,height,null);
		}
		
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x+= velX;
		y+= velY;
		
		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if(!t.isSolid()) {
				break;
			}
			if(getBoundsBottom().intersects(t.getBounds())) {
				setVelY(0);
				if(falling) {
					falling = false;	
				}
			}
			else if(!falling) {
					gravity = 0.0;
					falling = true;
			}
			if(getBoundsLeft().intersects(t.getBounds())) {
				setVelX(2);
				facing = 1;
				
			}
			if(getBoundsRight().intersects(t.getBounds())) {
				setVelX(-2);
				facing = 0;
				
			}
		}
		if(falling) {
			gravity += 0.3;
			setVelY((int)gravity);
		}
		if(velX != 0) {
			frameDelay++;
			if(frameDelay >=5) {
				frame++;
				if(frame+5>= Game.gomba.length) {
					frame = 0;
				}
				frameDelay = 0;
			}
		}
	}
}
