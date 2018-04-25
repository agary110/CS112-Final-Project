import java.awt.Graphics;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.LinkedList;

public class World{
	static double HEIGHT;
	static double WIDTH;
	static Marble marble;
	static Map map;
	static Item item;
	static Ammo ammo;
	boolean aliveItem;
	static double timeUntilNextItem;
	static final double originalTimeUntilNextItem = 7;
	static int points;
	static int ammoCount;
	static boolean ammoReleased;
	static LinkedList<AmmoReleased> ammoActive;
	static Random rand;
	static boolean bumpersOn;
//=======================================
//Constructor
	public World(double initHeight, double initWidth){
		HEIGHT = initHeight;
		WIDTH = initWidth;
		marble = new Marble();
   		aliveItem = false;
		item = new Item(Game.WIDTH / 2, Game.HEIGHT);
		timeUntilNextItem = 2;
		ammoCount = 0;
		points = 0;
		ammoReleased = false;
		rand = new Random();
		map = new Map(0);
	    //this will change soon
		ammoActive = new LinkedList<AmmoReleased>();
		bumpersOn = true;
	}
//=======================================
//Draw Methods
	public void drawToScreen(Graphics g){
		drawPath(g);
		drawItem(g);
		drawMarble(g);
		//drawPoints(g);
		drawAmmoCount(g);
		drawAmmoReleased(g);
	}

	public void drawMarble(Graphics g){
		marble.draw(g);
	}

	public void drawItem(Graphics g){
		if(item.drawn){
			item.draw(g);
		}
	}

	public void drawPath(Graphics g){
		map.draw(g);
	}

	/*public void drawPoints(Graphics g){
	}*/

   public void drawAmmoCount(Graphics g){
		Ammo.drawAmmoCounter(g);
   }

	public void drawAmmoReleased(Graphics g){
		for(int i = 0; i < ammoActive.size(); i++){
			ammoActive.get(i).draw(g);
		}
	}

//=======================================
//Update Methods
	public void updateMarble(double time){
		marble.update(time);
		marble.checkDead(this);
		marble.checkForBumpers(this);
	}

	private void updateItem(){
		if(timeUntilNextItem <= 0){
			item = Item.generateNextItem(rand.nextInt(6));
			timeUntilNextItem = originalTimeUntilNextItem;
		}
		item.update();
		
	}

	private void updateMap(double time){
		map.update(time);
	}

	/*private void updatePoints(double time){
		points = points + time;
	}*/

	private void updateAmmoReleased(){
		for(int i = 0; i < ammoActive.size(); i++){
			ammoActive.get(i).update();
		}
	}
//=======================================
//Updates Frame and values that change by frame
	public void nextFrame(double time){
		updateMarble(time);
		updateItem();
		updateMap(time);
		//updatePoints(time);
		updateAmmoReleased();
	}
//=======================================
// When the key (char c) is pressed, the marble will start moving in that direction. The more times you press the key, the faster the marble will go in that direction.

    public void moveMarble(char c){
		if (c == 'i') {
	    	marble.moveUp();
		}
		if (c == 'j') {
		    marble.moveLeft();
		}
		if (c == 'k') {
		    marble.moveDown();
		}
		if (c == 'l') {
		    marble.moveRight();
		}
		if (c == ' ') {
			if(ammoCount > 0){
				ammoReleased = true;
				AmmoReleased.activate();
			}
		}
	}
}
