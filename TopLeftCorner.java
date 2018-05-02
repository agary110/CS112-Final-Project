//===================================
/** Importing necessary libraries **/
//==================================
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;

//==================================================================================
/** The TopLeftCorner class is a subclass of Path. It represents a corner in which the marble is traveling up the screen in a straight direction, and must turn left. It’s main (and only) method is a draw method, that overrides Path’s draw method, and draws the a TopLeftCorner Path on the Jpanel. **/
//==================================================================================

public class TopLeftCorner extends Path {

//======================
/** Constructor **/
//======================

	public TopLeftCorner(Path previous) {
		super(previous);
		name="TopLeftCorner";
		enterX=previous.x;
		x = enterX - Path.WIDTH;
		y=previous.y-Path.HEIGHT;
	}
	
//=================================================
/** Method: draw (Graphics g0ri)
    Functionality: Draws the TopRightCorner Path on the screen **/
//=================================================

    public void draw(Graphics g0ri){

	//Overrides Path’s draw method

		Graphics2D g = (Graphics2D) g0ri;
	    g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fill (new Rectangle2D.Double(x, y, WIDTH, WIDTH));
		g.fill (new Rectangle2D.Double(x + WIDTH, y, WIDTH, HEIGHT));
		if (World.bumpersOn){

			//Draws bumpers if bumpersOn is true

			g.setColor(Color.RED);
			g.fill(new Rectangle2D.Double(x, y - bumperWidth, HEIGHT, bumperWidth));
			g.fill(new Rectangle2D.Double(x + HEIGHT, y - bumperWidth, bumperWidth, HEIGHT + bumperWidth));
			g.fill(new Rectangle2D.Double(x, y + WIDTH, WIDTH, bumperWidth));
			g.fill(new Rectangle2D.Double(x + WIDTH - bumperWidth, y + WIDTH, bumperWidth, WIDTH));
		}
    }
}
/** END OF TOPLEFTCORNER CLASS**/
//=================================================