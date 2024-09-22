package mitraMario;



import movingObjects.Entity;

public class Camera {
	 private int x, y;
	    private int targetX, targetY;
	    private final double easeAmount = 0.04;

	    public void update(Entity player, Game game) {
	       
	    	 int playerX = player.getX();
	         int playerY = player.getY();
	         
	         
	         int screenWidthMiddle = game.getFrameWidth() / 2;
	         int screenHeightMiddle = game.getFrameHeight() / 2;
	         
	         
	         int targetX = x;
	         int targetY = y;

	         if (playerX > screenWidthMiddle) {
	             targetX = -playerX + screenWidthMiddle;
	         } else {
	             targetX = 0;
	         }
	         if (playerY < screenHeightMiddle) {
	             targetY = -playerY + screenHeightMiddle;
	         } else {
	             targetY = -160;
	         }
	         x += (targetX - x) * easeAmount;
	         y += (targetY - y) * easeAmount;
	     
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
}
