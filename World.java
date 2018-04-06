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
	drawBooster(g);
	drawMarble(g);
    }

    public void drawMarble(Graphics g){
	marble.draw(g);
    }

    public void drawBooster(Graphics g){
	booster.draw(g);
    }

   public void drawPath(){
	map.draw();
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
	updateMarble(time);
	updateBooster();
	updateMap();
	drawToScreen(g);
	points = points + 1/60;//Awards one point per second
	if(booster.activated) timeActive = timeActive - 1/60;
		if(timeActive == 0){
			booster.deactivate();
			double timeUntilNextBooster = originalTimeUntilNextBooster;
		}
	}
	else if(booster.y >= (-)(booster.width) && booster.y <= HEIGHT);
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
