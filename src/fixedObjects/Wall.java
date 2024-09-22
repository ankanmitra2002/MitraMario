package fixedObjects;

import java.awt.Color;
import java.awt.Graphics;

import mitraMario.Game;
import mitraMario.Handler;
import mitraMario.Id;

public class Wall extends Tile{

	public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Game.bricks, x, y,null);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
