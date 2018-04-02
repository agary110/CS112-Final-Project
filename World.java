public class World{
    int height;
    int width;

    public World(int initHeight, int initwidth){
	height = initheight;
	width = initwidth;
   
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
}
