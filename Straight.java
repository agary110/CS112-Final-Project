//===================================
/** Importing necessary libraries **/
//==================================
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;

//==================================================================================
/** The Straight class is a subclass of Path. It represents a straight, vertical path. It’s main (and only) method is a draw method, that overrides Path’s draw method, and draws the a Straight Path on the Jpanel. **/
//==================================================================================

public class Straight extends Path{

//======================
/** Constructor **/
//======================

	public Straight(int enterX){

	//Constructor for when parameter is an int

		super(enterX);
		this.name="Straight";
		this.x=enterX;
    }

	public Straight(Path previous){

	//Constructor for when parameter is a Path

		super(previous);
		name = "Straight";
		enterX = previous.x;
		x = this.enterX;
		y = previous.y - Path.HEIGHT;
	}

//=================================================
/** Method: draw (Graphics g)
    Functionality: Draws the Straight Path on the screen **/
//=================================================

	public void draw(Graphics g0ri){

	//Overrides Path’s draw method

		Graphics2D g = (Graphics2D) g0ri;
		g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
		g.fill(new Rectangle2D.Double(x, y, WIDTH, HEIGHT));

		if (World.bumpersOn){

			//Draws bumpers if bumpersOn is true

			g.setColor(Color.RED);
			g.fill(new Rectangle2D.Double(x - bumperWidth, y, bumperWidth, HEIGHT));
			g.fill(new Rectangle2D.Double(x + WIDTH, y, bumperWidth, HEIGHT));
		}
    }
}
/** END OF Straight CLASS**/
//=================================================
