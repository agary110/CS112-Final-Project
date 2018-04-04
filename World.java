public class World{
    int height;
    int width;
    Marble marble;
    Map map;

    public World(int initHeight, int initwidth){
	height = initheight;
	width = initwidth;
   
    }



    public void drawMarble(Graphics g){
	g.fillOval();
    }

    public void updateMarble(double time){
	marble.update(this, time);
    }


    public void nextFrame(double INPUT){//What does the input represent exactly? Specified as (1.0 / (double)(FPS)) in Runner class
    }

    public void drawToScreen(Graphics g){
    }

//=======================================
//Updates Paths visible on the screen
   private void updatePath(){
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
