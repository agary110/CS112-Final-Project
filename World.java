import java.awt.Graphics;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class World{
    static double HEIGHT;
    static double WIDTH;
    static Marble marble;
    static Map map;
    //Item item;
    boolean aliveItem;
    double timeUntilNextItem;
    final double originalTimeUntilNextItem;
    static int points;
    static int ammoCount;
//=======================================
//Constructor
    public World(double initHeight, double initWidth){
	HEIGHT = initHeight;
	WIDTH = initWidth;
	marble = new Marble();
   	aliveItem = false;
	//item = newItem();
	originalTimeUntilNextItem = 7;//random num - specified amount based on difficulty level
	ammoCount = 0;
	points = 0;
	map = new Map();
    }
//=======================================
//Draw Methods
	public void drawToScreen(Graphics g){
		drawPath(g);
		//drawPoints(g);
		//drawItem(g);
		drawMarble(g);
		//drawPoints(g);
		//drawAmmoCount(g);

	}

	public void drawMarble(Graphics g){
		marble.draw(g);
	}

	/*public void drawItem(Graphics g){
		item.draw(g);
	}*/

	public void drawPath(Graphics g){
		map.draw(g);
	}

	/*public void drawPoints(Graphics g){
	}*/

   public void drawAmmoCount(Graphics g){
   }

//=======================================
//Update Methods
	public void updateMarble(double time){
		marble.update(this, time);
	}

	/*private void updateItem(){
		item.update();
	}*/

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
		//updateItem();
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
	}
}
