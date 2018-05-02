
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;


public class Horizontal extends Path{
    boolean direction;

	public Horizontal(Path previous, boolean direction){
		super(previous);
		name = "Horizontal";
		enterX = previous.x;
		this.direction = direction;
		if(direction){
			x = enterX + Path.HEIGHT;
		}
		else{
			x = enterX - Path.HEIGHT;
		}
		y = previous.y;
	}

	public void draw(Graphics g0ri){
		Graphics2D g = (Graphics2D) g0ri;
		g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		if(direction){
			g.fill(new Rectangle2D.Double(enterX, y, HEIGHT, WIDTH));
		}
		else{
			g.fill(new Rectangle2D.Double(x, y, HEIGHT, WIDTH));
		}
		if (World.bumpersOn){
			g.setColor(Color.RED);
			if(direction){
				g.fill(new Rectangle2D.Double(enterX, y - bumperWidth, HEIGHT, bumperWidth));
				g.fill(new Rectangle2D.Double(enterX, y + WIDTH, HEIGHT, bumperWidth));
			}
			else{
				g.fill(new Rectangle2D.Double(x, y - bumperWidth, HEIGHT, bumperWidth));
				g.fill(new Rectangle2D.Double(x, y + WIDTH, HEIGHT, bumperWidth));
			}
		} 
		
    }
}
