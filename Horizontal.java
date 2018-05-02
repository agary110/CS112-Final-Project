//===================================
/** Importing necessary libraries **/
//==================================
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;

//==================================================================================
/** The Horizontal class is a subclass of Path. It represents a straight, horizontal path. It’s main (and only) method is a draw method, that overrides Path’s draw method, and draws the a Horizontal Path on the Jpanel. **/
//==================================================================================

public class Horizontal extends Path{

//======================
/** New member variables **/
//======================

    boolean direction; //member variable that is true when the marble is entering 				  horizontal from the right; determines x value and how to 				  draw the horizontal

//======================
/** Constructor **/
//======================

	public Horizontal(Path previous, boolean direction){
		super(previous);
		name = "Horizontal";
		enterX = previous.x;
		this.direction = direction;
		if(direction){
	
		//sets x depending on direction boolean

			x = enterX + Path.HEIGHT;
		}
		else{
			x = enterX - Path.HEIGHT;
		}
		y = previous.y;
	}
//=================================================
/** Method: draw (Graphics g0ri)
    Functionality: Draws the Horizontal Path on the screen **/
//=================================================

	public void draw(Graphics g0ri){

	//Overrides Path’s draw method

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

			//Draws bumpers if bumpersOn is true

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
/** END OF Horizontal CLASS**/
//=================================================
