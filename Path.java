import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;

public class Path{
    final int bumperWidth = 5;
    static final int WIDTH = 100;
    static final int HEIGHT = 200;
    int x; //corresponds to path's upper left corner
    int y; //corresponds to path's upper left corner
    int enterX; //corresponds to path's bottom left corner
    String name;
    final Color color;

    public Path(int enterX) {
		x = 0;
		y = - Path.HEIGHT;
		this.enterX = enterX;
		name = "";
		color = Color.LIGHT_GRAY;

    }

	public Path(Path previous){
		x = 0;
		y = 0;
		enterX = 0;
		name = "";
		color = Color.LIGHT_GRAY; 
	}

    public void draw (Graphics g) {
    }

}
