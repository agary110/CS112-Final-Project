//======================
/** Importing necessary libraries **/
//======================
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;


public class TopRightCorner extends Path {

	public TopRightCorner(Path previous) {
		super(previous);
		name="TopRightCorner";
		enterX=previous.x;
		x = enterX + Path.HEIGHT;
		y=previous.y - Path.HEIGHT;
	}
	
    public void draw(Graphics g0ri){
		Graphics2D g = (Graphics2D) g0ri;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_ON);
	 	g.setColor(color);
		g.fill (new Rectangle2D.Double(x-WIDTH, y, WIDTH, WIDTH)); 
		g.fill (new Rectangle2D.Double(x-HEIGHT, y, WIDTH, HEIGHT)); 
		if (World.bumpersOn){
			g.setColor(Color.RED);
			
			g.fill(new Rectangle2D.Double(x - HEIGHT, y - bumperWidth, HEIGHT, bumperWidth));
			g.fill(new Rectangle2D.Double(x - HEIGHT - bumperWidth, y - bumperWidth, bumperWidth, HEIGHT + bumperWidth)); 
			g.fill(new Rectangle2D.Double(x - WIDTH, y + WIDTH, WIDTH, bumperWidth)); 
			g.fill(new Rectangle2D.Double(x - WIDTH, y + WIDTH, bumperWidth, WIDTH));
		}
    }	
}
