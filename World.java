import java.awt.Graphics;
import java.util.Random;

public class World{
    double height;
    double width;
    Marble marble;
    Map map;
    Boolean aliveBooster;

    public World(double initHeight, double initwidth){
	height = initheight;
	width = initwidth;
   	aliveBooster = false;
    }

    public void drawMarble(Graphics g){
	marble.draw(g);

    }

    public void drawPoints(Graphics g){

    }

    public void updateMarble(double time){
	marble.update(this, time);
    }


    public void nextFrame(double INPUT){//What does the input represent exactly? Specified as (1.0 / (double)(FPS)) in Runner class
	points = points + 1/60;//Awards one point per second
    }

    public void drawToScreen(Graphics g){
<<<<<<< HEAD
	drawPath();
    }

   private void drawPath(){
	Random rand = new Random();
	rand.setSeed(112);
	pathInt = rand.nextInt(2);
	Path [] visiblePaths = getVisiblePaths();
	for(int i = 0; i < visiblePaths.length; i++){
		if(pathInt == 0) drawStraightPath();
		else if(pathInt == 1) drawRightCorner();
		else drawLeftCorner();
	}
    }

    private void drawStraightPath(Graphics g){
    }

    private void drawRightCorner(Graphics g){
=======
>>>>>>> 7222cef79abe6429d19e80d956613029c8f048fd
    }
//=======================================
//Creates new random Booster, given aliveBooster = false
    private void newBooster(){
		visiblePaths = getVisiblePaths();
		Booster booster = new Booster(visiblePaths[visiblePaths.length - 1].x + path.width / 2 - booster.width / 2, 0);
    }
//=======================================
//
   private void updateBooster(){
	booster.update();
   }

//=======================================
//Updates Paths visible on the screen
   private void updateMap(){
	map.update();
    }
//=======================================
// When the key (char c) is pressed, the marble will start moving in that direction.

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
//=======================================
// When the key (char c) is released, the marble will stop moving in that direction.

    public void stopMarble(char c){
	if (c == 'i') {
	    marble.stopUp();
	}
	if (c == 'j') {
	    marble.stopLeft();
	}
	if (c == 'k') {
	    marble.stopDown();
	}
	if (c == 'l') {
	    marble.stopRight();
	}
    }
//=======================================

}
