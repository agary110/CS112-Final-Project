import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;

public class LeftElbow extends Path{

    public LeftElbow(int exitX){
		super(exitX);
		super.name="leftElbow";
		this.x=exitX-HEIGHT;
    }

	public LeftElbow(Path previous){
		super(previous);
		name = "leftElbow";
		exitX = previous.x;
		x = exitX - Path.WIDTH;
		y = previous.y - Path.WIDTH;
	}

    public void draw(Graphics g0ri){
		Graphics2D g = (Graphics2D) g0ri;
	    g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        					RenderingHints.VALUE_ANTIALIAS_ON);
		g.fill(new Rectangle2D.Double(x, y, WIDTH, HEIGHT));
		g.fill(new Rectangle2D.Double(x+WIDTH, y+WIDTH, WIDTH, WIDTH));
		if (World.bumpersOn){
			g.setColor(Color.RED);
			g.fill(new Rectangle2D.Double(x + WIDTH, y, bumperWidth, WIDTH)); // short vert side 
			g.fill(new Rectangle2D.Double(x + WIDTH, y + WIDTH - bumperWidth, WIDTH, bumperWidth)); // short bottom side
			g.fill(new Rectangle2D.Double(x - bumperWidth, y, bumperWidth, HEIGHT)); //long vert side
			g.fill(new Rectangle2D.Double(x - bumperWidth, y + HEIGHT, HEIGHT + bumperWidth, bumperWidth)); // long bottom side
		}
		g.setColor(color);
    }
}
