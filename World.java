public class World{
    int height;
    int width;
    Marble marble;
    Map map;
    Boolean aliveBooster;

    public World(int initHeight, int initwidth){
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
