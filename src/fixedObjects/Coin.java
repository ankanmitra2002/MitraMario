package fixedObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import mitraMario.Game;
import mitraMario.Handler;
import mitraMario.Id;

public class Coin {
	
	public int x,y,width,height;
	public boolean solid;
	public Id id;
	public Handler handler;
	private int frame = 0;
	private int frameDelay = 0;
	public Coin(int x, int y, int width, int height, boolean solid, Id id,Handler handler) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.id = id;
		this.handler = handler;
	}
	public void render(Graphics g) {
		g.drawImage(Game.coin,x,y,width,height,null);
	
	}
	public void tick() {
		
	}
	public Id getId() {
		return id;
	}
	public void die() {
		handler.removeCoin(this);
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
