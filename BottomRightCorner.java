import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;

public class BottomRightCorner extends Path{

	public BottomRightCorner(Path previous){
		super(previous);
		name = "BottomRightCorner";
		enterX = previous.x;
		x = enterX + Path.WIDTH;
		y = previous.y - Path.WIDTH;
	}

    public void draw(Graphics g0ri){
		Graphics2D g = (Graphics2D) g0ri;
	    g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        					RenderingHints.VALUE_ANTIALIAS_ON);
		g.fill(new Rectangle2D.Double(x, y, WIDTH, HEIGHT));
		g.fill(new Rectangle2D.Double(enterX, y + Path.WIDTH, WIDTH, WIDTH));
		if (World.bumpersOn){
			g.setColor(Color.RED);
			g.fill(new Rectangle2D.Double(x - bumperWidth, y, bumperWidth, WIDTH));
			g.fill(new Rectangle2D.Double(enterX, y + WIDTH - bumperWidth, WIDTH, bumperWidth));
			g.fill(new Rectangle2D.Double(x + WIDTH, y, bumperWidth, HEIGHT));
			g.fill(new Rectangle2D.Double(enterX, y + HEIGHT, HEIGHT + bumperWidth, bumperWidth));
		}
    }
}
