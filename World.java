public class World{
    int height;
    int width;
    Marble marble;

    public World(int initHeight, int initwidth){
	height = initheight;
	width = initwidth;
   
    }



    public void drawMarble(Graphics g){
	g.fillOval()
    }

    public void updateMarble(double time){
	marble.update(this, time);
    }


    public void nextFrame(double INPUT){//What does the input represent exactly?

    }

    public void drawToScreen(Graphics g){
	drawPath();
    }

   private void drawPath(){
	Path [] visiblePaths = getVisiblePaths();
	for(int i = 0; i < visiblePaths.length; i++){
		if(ARGUMENT) drawStraightPath();
		else if(ARGUMENT) drawRightCorner();
		else drawLeftCorner();
	}
    }

    private void drawStraightPath(Graphics g){
    }

    private void drawRightCorner(Graphics g){
    }

    private void drawLeftCorner(Graphics g){
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
