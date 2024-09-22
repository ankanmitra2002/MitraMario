package movingObjects;

import java.awt.Graphics;
import java.util.Random;

import fixedObjects.Tile;
import mitraMario.Game;
import mitraMario.Handler;
import mitraMario.Id;
import movingObjects.Entity;

public class Mushroom extends Entity{

	private Random random = new Random();
	public Mushroom(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		int dir = random.nextInt(2);
		switch (dir) {
		case 0: 
			setVelX(-3);
			break;
		case 1: 
			setVelX(3);
			break;
		}
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.mushroom,x,y,width,height,null);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x+= velX;
		for(int i=0;i<handler.tile.size();i++) {
			Tile t = handler.tile.get(i);
			if(!t.isSolid()) {
				break;
			}
			if(getBoundsLeft().intersects(t.getBounds())) {
				setVelX(3);
				
			}
			if(getBoundsRight().intersects(t.getBounds())) {
				setVelX(-3);
				
			}
		}
	}

}
