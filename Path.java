//===================================
/** Importing necessary libraries **/
//===================================

import java.awt.Graphics;
import java.awt.Color;

//==================================================================================
/** The Path class gets created when screens are generated in the World class. They represent a type of “path” that the user must navigate. It’s only method is a draw method, which draws the path on the JPanel. Most of it’s information (member variables, method) is overridden in Path’s subclasses. **/
//==================================================================================

public class Path{

//======================
/** Member Variables **/
//======================

    final int bumperWidth = 5;
    static final int WIDTH = 100; 
    static final int HEIGHT = 200;
    int x; //corresponds to path's upper left corner
    int y; //corresponds to path's upper left corner
    int enterX; //corresponds to path's bottom left corner
    String name;
    final Color color;

//======================
/** Constructors **/
//======================

    public Path(int enterX) {

	//Constructor for when parameter is an int

		x = 0;
		y = - Path.HEIGHT;
		this.enterX = enterX;
		name = "";
		color = Color.LIGHT_GRAY;

    }

	public Path(Path previous){

	//Constructor for when parameter is a Path

		x = 0;
		y = 0;
		enterX = 0;
		name = "";
		color = Color.LIGHT_GRAY; 
	}
//=================================================
/** Method: draw (Graphics g)
    Functionality: Draws the Path on the screen **/
//=================================================
    public void draw (Graphics g) {
	//Method to be overridden in Path’s subclasses
    }

}
/** END OF Path CLASS**/
//=================================================
