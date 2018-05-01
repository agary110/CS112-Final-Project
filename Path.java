import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;

public class Path implements pathType{
   // boolean bumpersOn=false;
    final int bumperWidth = 5; //need to decide what this is
    static final int WIDTH = 100;
    static final int HEIGHT = 200;
    int x; //corresponds to path's upper left corner
    int y; //corresponds to path's upper left corner
    //  final int gapLength; //are we still doing this?
    String name;
    int exitX; //corresponds to path's bottom left corner
    final Color color;

    public Path(int exitX) {
		name = "";
		x=0;
		y= - Path.HEIGHT;
		this.exitX=exitX;
		Random rand = new Random();
		color = Color.LIGHT_GRAY;

    }

	public Path(Path previous){
		name = "";
		Random rand = new Random();
		color = Color.LIGHT_GRAY; 
		x = 0;
		y = 0;
		exitX = 0;
	}

    public void draw (Graphics g) {
    }

    public void update(){
		this.y++;
    }

    public void setY(int toAdd){
		this.y += toAdd;
    }
}
