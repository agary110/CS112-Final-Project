import java.awt.Graphics;
import java.util.Random;

public class World{
    double height;
    double width;
    Marble marble;
    Map map;
    Booster booster;
    double timeUntilNextBooster;
    final double originalTimeUntilNextBooster;
//=======================================
//Constructor
    public World(double initHeight, double initwidth){
	height = initheight;
	width = initwidth;
   	aliveBooster = false;
	booster = newBooster();
	originalTimeUntilNextBooster = ;//specified amount based on difficulty level
    }
//=======================================
//Draw Methods
    public void drawToScreen(Graphics g){
	drawPath();
    }

    public void drawMarble(Graphics g){
	marble.draw(g);
    }

    public void drawBooster(Graphics g){
	booster.draw(g);
    }

   private void drawPath(){
	Random rand = new Random();
	pathInt = rand.nextInt(3);
	Path [] visiblePaths = getVisiblePaths();
	for(int i = 0; i < visiblePaths.length; i++){
		if(pathInt == 0) drawStraightPath();
		else if(pathInt == 1) drawRightCorner();
		else drawLeftCorner();
	}
    }

    private void drawStraightPath(Graphics g){
    }

    private void drawRightCorner(){
    }

    private void drawLeftCorner(){
    }
//=======================================
//Update Methods
    public void updateMarble(double time){
	marble.update(this, time);
    }

   private void updateBooster(){
	booster.update();
   }

   private void updateMap(){
	map.update();
    }
//=======================================
//Updates Frame and values that change by frame
    public void nextFrame(double INPUT){//What does the input represent exactly? Specified as (1.0 / (double)(FPS)) in Runner class
	points = points + 1/60;//Awards one point per second
	if(booster.activated) timeActive - 1/60;
		if(timeActive == 0){
			booster.deactivate();
			double timeUntilNextBooster = originalTimeUntilNextBooster;
		}
	}
	else if(booster.y >= 0 - booster.width && booster.y <= HEIGHT + booster.width);
	else timeUntilNextBooster = timeUntilNextBooster - 1/60;
	if(timeUntilNextBooster = 0) newBooster();
    }
//=======================================
//Creates new random Booster, given aliveBooster = false & points have increased by a given amount based on the difficulty setting
    private void newBooster(){
	booster = generateNextBooster();
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
