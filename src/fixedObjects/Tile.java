package fixedObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import mitraMario.Handler;
import mitraMario.Id;

public abstract class Tile {
	public int x,y,width,height;
	public boolean solid;
	public Id id;
	public Handler handler;
	public Tile(int x, int y, int width, int height, boolean solid, Id id,Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}
	
	public Id getId() {
		return id;
	}
	public abstract void render(Graphics g);
	public abstract void tick();
	
	public void die() {
		handler.removeTile(this);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isSolid() {
		return solid;
	}
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
}
