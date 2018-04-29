
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;


public class Horizontal extends Path{
    boolean direction;

    public Horizontal(int exitX, boolean direction){
		super(exitX);
		this.name="Horizontal";
		if (direction) {
	 		this.x=exitX+HEIGHT;
		}
		else {
	   		this.x=exitX-HEIGHT;
		}
    }

	public Horizontal(Path previous, boolean direction){
		super(previous);
		name = "Horizontal";
		exitX = previous.x;
		this.direction = direction;
		if(direction){
			x = exitX + Path.HEIGHT;
		}
		else{
			x = exitX - Path.HEIGHT;
		}
		y = previous.y;
	}

	public void draw(Graphics g0ri){
		Graphics2D g = (Graphics2D) g0ri;
		g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		System.out.println(direction);
		if(direction){
			System.out.println("got in");
			g.fill(new Rectangle2D.Double(exitX, y, HEIGHT, WIDTH));
		}
		else{
			g.fill(new Rectangle2D.Double(x, y, HEIGHT, WIDTH));
		}
		if (World.bumpersOn){
			g.setColor(Color.RED);
			if(direction){
				g.fill(new Rectangle2D.Double(exitX, y - bumperWidth, HEIGHT, bumperWidth));
				g.fill(new Rectangle2D.Double(exitX, y + WIDTH, HEIGHT, bumperWidth));
			}
			else{
				g.fill(new Rectangle2D.Double(x, y - bumperWidth, HEIGHT, bumperWidth));
				g.fill(new Rectangle2D.Double(x, y + WIDTH, HEIGHT, bumperWidth));
			}
		} 
		g.setColor(color);
		
    }
}
