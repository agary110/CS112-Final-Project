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
	this.x=exitX+HEIGHT;
    }
	public RightCorner(Path previous) {
		super(previous);
		name="rightCorner";
		exitX=previous.x;
		x = exitX + Path.HEIGHT;
		y=previous.y - Path.HEIGHT;
	}
	
    public void draw(Graphics g0ri){
	Graphics2D g = (Graphics2D) g0ri;
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_ON);
 g.setColor(Color.GREEN);
	g.fill (new Rectangle2D.Double(x-WIDTH, y, WIDTH, WIDTH));
	g.fill (new Rectangle2D.Double(x-HEIGHT, y, WIDTH, HEIGHT));
    }
}
