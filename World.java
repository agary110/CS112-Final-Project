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
	static LinkedList<LinkedList<Path>> mapsOnScreen;
	boolean aliveItem;
	static double timeUntilNextItem;
	static final double originalTimeUntilNextItem = 7;
	static int points;
	static int ammoCount;
	static boolean ammoReleased;
	static Node ammoActiveLast;
	static int ammoActiveCount;
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
		ammoCount = 100;
		points = 0;
		ammoReleased = false;
		rand = new Random(1);
		map = new Map();
		mapsOnScreen = new LinkedList<LinkedList<Path>>();
		mapsOnScreen.add(Map.upcomingPaths);
		mapsOnScreen.add(Map.Map1);
	    //this will change soon
		ammoActiveLast = null;
		ammoActiveCount = 0;
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

//we should move this into AmmoReleased class

	public void drawAmmoReleased(Graphics g){
		int j;
		Node index;
		for(int i = 0; i < ammoActiveCount; i++){
			j = i;
			index = ammoActiveLast;
			while(j > 0){
				index = index.previous;
			}
			index.ammoR.draw(g);
		}
	}

//=======================================
//Update Methods
	public void updateMarble(double time){
		marble.update(time);
		if (!bumpersOn){
			//marble.checkForBumpers(this);
		//} else {
			marble.checkDead(this);
		}
		
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
		if(ammoReleased){
			for(int i = 0; i < ammoActiveCount; i++){
				if (ammoActiveLast.getNode(i).ammoR != null){
					AmmoReleased a = ammoActiveLast.getNode(i).ammoR;
					a.update(ammoActiveLast.getNode(i));
				}
			}
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
		if (c == 'a') {
			if(ammoCount > 0){
				ammoReleased = true;
				AmmoReleased.activate();
			}
		}
		if (bumpersOn)
			marble.checkForBumpers(this, c);
	}
}
