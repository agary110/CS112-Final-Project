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
	this..name="leftCorner";
	this.x=exitX-super.WIDTH;
    }
    public void draw(Graphics g0ri){
	Graphics2D g = (Graphics2D) g0ri;
	    g.setColor(Color.GREEN);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_ON);
 
	g.fill (new Rectangle2D.Double(super.x, super.y, 2*super.WIDTH, super.HEIGHT/2));
	g.fill (new Rectangle2D.Double(super.x+super.WIDTH/2, super.y+super.HEIGHT/2, super.WIDTH, super.HEIGHT/2));
    }
}
