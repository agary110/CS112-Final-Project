
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

    public Straight(int enterX){
		super(enterX);
		this.name="Straight";
		this.x=enterX;
    }

	public Straight(Path previous){
		super(previous);
		name = "Straight";
		enterX = previous.x;
		x = this.enterX;
		y = previous.y - Path.HEIGHT;
	}

	public void draw(Graphics g0ri){
		Random rand = new Random();
		Graphics2D g = (Graphics2D) g0ri;
		g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
		g.fill(new Rectangle2D.Double(x, y, WIDTH, HEIGHT));

		if (World.bumpersOn){
			g.setColor(Color.RED);
			g.fill(new Rectangle2D.Double(x - bumperWidth, y, bumperWidth, HEIGHT));
			g.fill(new Rectangle2D.Double(x + WIDTH, y, bumperWidth, HEIGHT));
			

		} g.setColor(color);
    }
}
