import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;


public class RightCorner extends Path {

    public RightCorner(int exitX) {
	super(exitX);
	this.name="rightCorner";
	this.x=exitX;
    }
    public void draw(Graphics g0ri){
	Graphics2D g = (Graphics2D) g0ri;
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_ON);
 g.setColor(Color.GREEN);
	g.fill (new Rectangle2D.Double(super.x, super.y, 2*super.WIDTH, super.HEIGHT/2));
	g.fill (new Rectangle2D.Double(super.x, super.y+super.HEIGHT/2, super.WIDTH, super.HEIGHT/2));
    }
}
