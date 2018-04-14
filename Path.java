import java.awt.Graphics;

public class Path {
    boolean bumpersOn=false;
    final int bumperWidth; //need to decide what this is
    static final int WIDTH = 200;
    static final int HEIGHT = 300;
    int x; //corresponds to path's upper left corner
    int y; //corresponds to path's upper left corner
    //  final int gapLength; //are we still doing this?
    String name;
    int exitX; //corresponds to path's bottom left corner

    public Path(int exitX) {
	name = "";
	bumperWidth=10; //random for now
	x=0;
	y=0;
	this.exitX=exitX;
    }

    public void draw (Graphics g) {
    }
}
