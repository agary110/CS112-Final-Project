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
//=======================================
//Constructor
    public World(double initHeight, double initWidth){
	HEIGHT = initHeight;
	WIDTH = initWidth;
	marble = new Marble();
   	aliveItem = false;
	item = new Item();
	timeUntilNextItem = 14;
	ammoCount = 0;
	points = 0;
	ammoReleased = false;
	map = new Map();
	ammoActive = new LinkedList<AmmoReleased>();
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
		item.draw(g);
	}

	public void drawPath(Graphics g){
		map.draw(g);
	}

	/*public void drawPoints(Graphics g){
	}*/

   public void drawAmmoCount(Graphics g){
		item.drawAmmoCounter(g);
   }

	public void drawAmmoReleased(Graphics g){
		for(int i = 0; i < ammoActive.size(); i++){
			ammoActive.get(i).draw(g);
		}
	}

//=======================================
//Update Methods
	public void updateMarble(double time){
		marble.update(this, time);
	}

	private void updateItem(){
		item.update();
	}

	private void updateMap(double time){
		map.update(time);
	}

	/*private void updatePoints(double time){
		points = points + time;
	}*/
//=======================================
//Updates Frame and values that change by frame
	public void nextFrame(double time){
		updateMarble(time);
		updateItem();
		updateMap(time);
		//updatePoints(time);
		marble.checkDead(this);
		//this.drawToScreen(g);
		

	}
//=======================================
//Creates new random Item, given aliveItem = false & timeUntilNextItem = 0
    /*private void newItem(){
	item = generateNextItem();
    }*/
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
