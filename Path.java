import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;

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
    Color color;

    public Path(int exitX) {
	name = "";
	bumperWidth=10; //random for now
	x=0;
	y= - Path.HEIGHT;
	this.exitX=exitX;
	Random rand = new Random();
	color = new Color(rand.nextInt(255), rand.nextInt(255),rand.nextInt(255)); 
    }

    public void draw (Graphics g) {
    }

    public void update (){
	this.y++;
	System.out.println(y + ":)");
    }

    public void setY(int toAdd){
	this.y += toAdd;
    }
}
