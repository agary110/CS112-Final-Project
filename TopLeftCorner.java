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

public class TopLeftCorner extends Path {

    public TopLeftCorner(int enterX) {
		super(enterX);
		this.name="TopLeftCorner";
		this.x=enterX-WIDTH;
    }
	public TopLeftCorner(Path previous) {
		super(previous);
		name="TopLeftCorner";
		enterX=previous.x;
		x = enterX - Path.WIDTH;	//was originally “x = enterX - Path.WIDTH;”
		y=previous.y-Path.HEIGHT;
	}
	
    public void draw(Graphics g0ri){
		Graphics2D g = (Graphics2D) g0ri;
	    g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fill (new Rectangle2D.Double(x, y, WIDTH, WIDTH));
		g.fill (new Rectangle2D.Double(x + WIDTH, y, WIDTH, HEIGHT));
		if (World.bumpersOn){
			g.setColor(Color.RED);
			g.fill(new Rectangle2D.Double(x, y - bumperWidth, HEIGHT, bumperWidth));
			g.fill(new Rectangle2D.Double(x + HEIGHT, y - bumperWidth, bumperWidth, HEIGHT + bumperWidth));
			g.fill(new Rectangle2D.Double(x, y + WIDTH, WIDTH, bumperWidth));
			g.fill(new Rectangle2D.Double(x + WIDTH - bumperWidth, y + WIDTH, bumperWidth, WIDTH));
		}
		g.setColor(color);
    }
}
