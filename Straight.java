
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.util.Random;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;

public class Straight extends Path{

    public Straight(int exitX){
	super(exitX);
	this.name="Straight";
	this.x=exitX;
    }

    public void draw(Graphics g0ri){
	Random rand = new Random();
	Graphics2D g = (Graphics2D) g0ri;
	g.setColor(color);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
	g.fill(new Rectangle2D.Double(this.x, this.y, this.WIDTH, this.HEIGHT));

	System.out.println(y);
    }
}
