package movingObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import fixedObjects.Tile;
import mitraMario.Game;
import mitraMario.Handler;
import mitraMario.Id;


public class Player extends Entity{
	private int frame = 0;
	private int frameDelay = 0;
	private boolean animate = false;
	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if(facing == 0) {
			g.drawImage(Game.player[frame].getBufferedImage(),x,y,width,height,null);
		}else if(facing == 1) {
			g.drawImage(Game.player[frame+4].getBufferedImage(),x,y,width,height,null);
		}
		
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x+= velX;
		y+= velY;
		if(velX != 0) {
			animate = true;
		}else {
			animate = false;
		}
		for(Tile t: handler.tile) {
			if(!t.isSolid()) {
				break;
			}
			if(t.getId() == Id.wall) {
				if(getBoundsTop().intersects(t.getBounds())) {
					setVelY(0);
					if(jumping) {
						jumping = false;
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBoundsBottom().intersects(t.getBounds())) {
					setVelY(0);
					if(falling) {
						falling = false;	
					}
				}
				else {
					if(!falling && !jumping) {
						gravity = 0.0;
						falling = true;
					}
				}
				if(getBoundsLeft().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX()+ t.width;
				}
				if(getBoundsRight().intersects(t.getBounds())) {
					setVelX(0);
					x = t.getX()-t.width*2;
				}
				
			}
		}
		if(jumping) {
			gravity -= 0.2;
			setVelY((int)-gravity);
			if(gravity <= 0.0) {
				jumping = false;
				falling = true;
			}
		}
		if(falling) {
			gravity += 0.2;
			setVelY((int)gravity);
		}
		if(animate) {
			frameDelay++;
			if(frameDelay >=5) {
				frame++;
				if(frame+4>= Game.player.length) {
					frame = 0;
				}
				frameDelay = 0;
			}
		}
	}

}
