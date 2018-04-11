import java.awt.Graphics;
import java.util.Random;

public class World{
    static double HEIGHT;
    static double WIDTH;
    static Marble marble;
    static Map map;
    Item item;
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
   	aliveBooster = false;
	booster = newBooster();
	originalTimeUntilNextItem = 7;//random num - specified amount based on difficulty level
	ammoCount = 0;
	points = 0;
    }
//=======================================
//Draw Methods
    public void drawToScreen(Graphics g){
	drawPath();
	drawPoints(g);
	drawItem(g);
	drawMarble(g);
	drawPoints(g);
	drawAmmoCount(g);
    }

    public void drawMarble(Graphics g){
	marble.draw(g);
    }

    public void drawItem(Graphics g){
	item.draw(g);
    }

   public void drawPath(){
	map.draw();
    }

   public void drawPoints(Graphics g){
   }

   public void drawAmmoCount(Graphics g){
   }
//=======================================
//Update Methods
    public void updateMarble(double time){
	marble.update(this, time);
    }

   private void updateItem(){
	item.update();
   }

   private void updateMap(){
	map.update();
    }

   private void updatePoints(){
	points = points + (1 / (double)(FPS));
   }
//=======================================
//Updates Frame and values that change by frame
    public void nextFrame(double INPUT){//What does the input represent exactly? Specified as (1.0 / (double)(FPS)) in Runner class
	updateMarble(time);
	updateItem();
	updateMap();
	updatePoints();
	this.drawToScreen(g);
    }
//=======================================
//Creates new random Item, given aliveItem = false & timeUntilNextItem = 0
    private void newItem(){
	item = generateNextItem();
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
    }


}
