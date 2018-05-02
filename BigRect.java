//===================================
/** Importing necessary libraries **/
//==================================
import java.awt.Graphics;
import java.awt.Color;

//==================================================================================
/** The BigRect class is a subclass of Path. It represents a large rectangle that it is only drawn in the beginning of the game. It’s purpose is to give the user some space to get used to the controls. It’s main (and only) method is a draw method, that overrides Path’s draw method, and draws the a large rectangular Path on the Jpanel. **/
//==================================================================================

public class BigRect extends Path{

//======================
/** Member variables **/
//======================

	static final int WIDTH = Game.WIDTH;
	static final int HEIGHT = Game.HEIGHT / 2;

//======================
/** Constructor **/
//======================

	public BigRect(int y){
		super(0);
		name = "BigRect";
		x = Game.WIDTH / 2 - Path.WIDTH / 2;
		this.y = y;
	}

//=================================================
/** Method: draw (Graphics g)
    Functionality: Draws the large rectangular Path on the screen **/
//=================================================


	public void draw(Graphics g){
	
	//Overrides Path’s draw method

		g.setColor(color);
		g.fillRect(enterX + WIDTH/4, y, WIDTH/2, HEIGHT*2);

	}
}
/** END OF BigRect CLASS**/
//=================================================
