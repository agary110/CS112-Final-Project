public class World{
    int height;
    int width;

    public World(int initHeight, int initwidth){
	height = initheight;
	width = initwidth;
   
    }

    public static void drawToScreen(Graphics g){
	drawPath();
    }

   public static void drawPath(){
	Path [] visiblePaths = getVisiblePaths();
	for(int i = 0; i < visiblePaths.length; i++){
		if(ARGUMENT){
			//drawStraightPath();
		}
		else if(ARGUMENT){
			//drawRightCorner();
		}
		else{
			//drawLeftCorner();
		}
	}
    }

    private void drawStraightPath(Graphics g){
    }

    private void drawRightCorner(Graphics g){
    }

    private void drawLeftCorner(Graphics g){
    }
}
