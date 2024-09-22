package mitraMario;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

import fixedObjects.Coin;
import fixedObjects.Tile;
import fixedObjects.Wall;
import movingObjects.Dragon;
import movingObjects.Entity;
import movingObjects.Gomba;
import movingObjects.Mushroom;
import movingObjects.Player;



public class Handler {
	public LinkedList<Entity> entity = new LinkedList<Entity>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();
	public LinkedList<Coin> coins = new LinkedList<Coin>();
	private Game game;
	public int totalCoins = 0;
	public int totalMushrooms = 0;
	public int totalGombas = 0;
	public String s;
	public Entity player;
	public int lives = 0;
    public Handler(Game game) {
        this.game = game;
    }
	public void render(Graphics g) {
		g.drawImage(Game.castle,155*32,12*32+8,null);
		for(Entity en: entity) {
			en.render(g);
			if(en.getId() == Id.player) {
				player = en;
			}
		}
		for(Tile ti: tile) {
			ti.render(g);
		}
		for(Coin c: coins) {
			c.render(g);
		}
		 g.setFont(new Font("Arial", Font.BOLD, 18));
	        g.setColor(Color.BLACK);

	        java.awt.geom.AffineTransform originalTransform = ((java.awt.Graphics2D) g).getTransform();
	        ((java.awt.Graphics2D) g).setTransform(new java.awt.geom.AffineTransform());
	        
	        
	        g.setFont(new Font("Arial", Font.BOLD, 30));
	        g.setColor(Color.BLACK);

	        
	        g.drawString("Total Score(Coins Collected): " + totalCoins, 500,40);
	        g.drawString("Total Mushrooms Smashed: " + totalMushrooms,500,80);
	        g.drawString("Total Goombas Defeated: " + totalGombas,500,120);
	        g.drawString("Lives: " + lives, 500, 160);
	        
	        ((java.awt.Graphics2D) g).setTransform(originalTransform);
	}
	public void update() {
		 LinkedList<Entity> toRemove = new LinkedList<>();
		 LinkedList<Coin> toRemoveCoin = new LinkedList<>();
		for(Entity en: entity) {
			en.tick();
			 if (en instanceof Player) {
	                Player player = (Player) en;
	                if(player.getX() >= 5056) {
	                	game.gameOver("Congrats!! You have reached the destination!!");
	                }
	                Iterator<Entity> iterator = entity.iterator();
	                Iterator<Coin> coiniterator = coins.iterator();
	                while (iterator.hasNext()) {
	                    Entity e = iterator.next();
	                    if ((e.getId() == Id.gomba || e.getId() == Id.mushroom) && player.getBounds().intersects(e.getBoundsTop())) {
	                        toRemove.add(e);
	                        if(e.getId() == Id.gomba) {
	                        	totalGombas++;
	                        }
	                        if(e.getId() == Id.mushroom) {
	                        	totalMushrooms++;
	                        	lives++;
	                        }
	                    }
	                    else if ((e.getId() == Id.gomba || e.getId() == Id.mushroom || e.getId() == Id.dragon) && player.getBounds().intersects(e.getBounds())) {
	                    	 if (lives > 0) {
	                             lives--;
	                             player.setX(7 * 32);
	                             player.setY(18 * 32);
	                         } else {
	                             toRemove.add(en);
	                             game.gameOver("Oops!! You couldn't reach the destination!!");
	                         }
	                    }
	                }
	                while (coiniterator.hasNext()) {
	                	Coin c = coiniterator.next();
	                    if (c.getId() == Id.coin && player.getBounds().intersects(c.getBounds())) {
	                        toRemoveCoin.add(c);
	                        totalCoins++;
	                    }
	                }

			 }
		}
		 for (Entity en : toRemove) {
	            removeEntity(en);
	      }
		 for (Coin c : toRemoveCoin) {
	            removeCoin(c);
	      }
		for(Tile ti: tile) {
			ti.tick();
		}
		for(Coin c: coins) {
			c.tick();
		}
		
	}
	public Iterator<Entity> getEntityIterator() {
	    return entity.iterator();
	}
	public Iterator<Coin> getCoinIterator() {
	    return coins.iterator();
	}
	public void addEntity(Entity en) {
		entity.add(en);
	}
	public void removeEntity(Entity en) {
		entity.remove(en);
	}
	public void addTile(Tile ti) {
		tile.add(ti);
	}
	public void removeTile(Tile ti) {
		tile.remove(ti);
	}
	public void addCoin(Coin c) {
		coins.add(c);
	}
	public void removeCoin(Coin c) {
		coins.remove(c);
	}
	public void createlevel(int[][] tileArr) {

		for(int r=0;r<tileArr.length;r++) {
			for(int c=0;c<tileArr[0].length;c++) {
				if(tileArr[r][c]==1) {
					addTile(new Wall(c*32, r*32, 32,32, true, Id.wall,this));
				}else if(tileArr[r][c]==2) {
					addEntity(new Mushroom(c*32, r*32, 32,32, false, Id.mushroom,this));
				}else if(tileArr[r][c]==3) {
					addEntity(new Gomba(c*32, r*32, 32,32, false, Id.gomba,this));
				}else if(tileArr[r][c]==4) {
					addEntity(new Dragon(c*32, r*32, 64,64, false, Id.dragon,this));
				}else if(tileArr[r][c]==5) {
					addCoin(new Coin(c*32, r*32, 32,32, false, Id.coin,this));
				}
			}
		}
		addEntity(new Player(7*32,18*32,64,64,false,Id.player,this));
	}
}
