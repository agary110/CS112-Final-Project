import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.RenderingHints;

public class LeftCorner extends Path {

    public LeftCorner(int exitX) {
	super(exitX);
	this.name="leftCorner";
	this.x=exitX-WIDTH;
    }
	public LeftCorner(Path previous) {
		super(previous);
		name="leftCorner";
		exitX=previous.x;
		x = exitX - WIDTH;
		y=previous.y-Path.HEIGHT;
	}
	
    public void draw(Graphics g0ri){
	Graphics2D g = (Graphics2D) g0ri;
	    g.setColor(color);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_ON);
 
	g.fill (new Rectangle2D.Double(x, y, WIDTH, WIDTH));
	g.fill (new Rectangle2D.Double(exitX, y, WIDTH, HEIGHT));
    }
}
