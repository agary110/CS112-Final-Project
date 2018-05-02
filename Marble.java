//===============================
/** Importing necessary libraries **/
//===============================

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.LinkedList;

//=================================================================
/** The Pair class stores a double x and a double y. These values refer to the x and y coordinates of the top left-hand corner of its corresponding object. **/
//=================================================================

class Pair{

//===============================
/** Member Variables **/
//===============================

    public double x;
    public double y;

//===============================
/** Constructor **/
//===============================
    
    public Pair(double initX, double initY){
		x = initX;
		y = initY;
    }
}

/** END OF PAIR CLASS **/

//=================================================================
/** An instance of the Marble class is created in the World class. The Marble class contains all of the necessary methods to draw and move the marble. The Marble class also contains other methods that check if the marble has exceeded the limits of the path. **/
//=================================================================

public class Marble{

//================================================
/** Member variables **/
//================================================

	Pair position;
	int diameter;
	Color color;
	double XposIncrement;
	double YposIncrement;

//================================================
/** Constructor  **/
//================================================

	public Marble(){
		position = new Pair(Game.WIDTH / 2, 500.0);
		diameter = 25;
		XposIncrement = 5.0;
		YposIncrement = 0.6;
		color = Color.BLUE;
    }
    
//================================================
/** Method: draw(Graphics g)
	Functionality: Draws the marble **/
//================================================

    public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval((int)this.position.x, (int)this.position.y, diameter, diameter);
		g.setColor(c);
    }

//================================================
/** Methods: moveUp(), moveDown(), moveRight(), moveLeft()
	Functionality: Increment or decrement the marble’s ‘x’ and ‘y’ values **/
//================================================

    public void moveUp(){
		if(position.y > Game.HEIGHT / 4){
			position.y -= XposIncrement;
		}
    } 

    public void moveDown(){
		if(position.y < Game.HEIGHT / 4 * 3){
			position.y += XposIncrement;
		}
    }

    public void moveRight(){
	   	position.x += XposIncrement;
		position.y += YposIncrement;
    }

    public void moveLeft(){
	    position.x -= XposIncrement;
		position.y += YposIncrement;		
    }

//===================================================
/** Method: checkForBumpers()
	Functionality: If the marble has exceeded the limits of the path, its ‘x’ and ‘y’ values are adjusted so that the marble is pushed back within the limits of the path **/
//================================================

	public static void checkForBumpers(){
		Path path = checkPath();
		Pair marb = World.marble.position;

		if(marb.y > Game.HEIGHT){
			Game.alive = false;
		}

		if(path.name == "Straight"){
			if(marb.x < path.x){
				if (Game.jpressed){
					World.marble.moveRight();
				}
			}
			if (marb.x + World.marble.diameter > path.x + path.WIDTH){
				if (Game.lpressed){
					World.marble.moveLeft();
				}
			}
	
		}
		
		else if(path.name == "TopLeftCorner"){
			if(marb.y < path.y){
				if (Game.ipressed){
					World.marble.moveDown();
				}
			}
			if(marb.x < path.enterX){
				if (Game.jpressed){
					World.marble.moveRight();
				}
			}

			if (marb.y + World.marble.diameter > path.y + path.WIDTH && marb.x + World.marble.diameter > path.enterX + path.WIDTH){
				if (Game.kpressed){
					World.marble.moveUp();
				}
				if (Game.lpressed){
					World.marble.moveLeft();
				}
			}

		}

		else if(path.name == "TopRightCorner"){
			if(marb.y < path.y){
				if (Game.ipressed){
					World.marble.moveDown();
				}
			}
			if(marb.x + World.marble.diameter > path.enterX + path.WIDTH){
				if (Game.lpressed){
					World.marble.moveLeft();
				}
			}
			if(marb.x < path.enterX && marb.y + World.marble.diameter > path.y + path.WIDTH){
				if (Game.kpressed){
					World.marble.moveUp();
				}
				if (Game.jpressed){
					World.marble.moveRight();
				}
			}
		}

		else if(path.name == "BottomRightCorner"){
			if (marb.x + World.marble.diameter >= path.enterX + path.HEIGHT){
				if (Game.lpressed){
					World.marble.moveLeft();
				}
			}
			if (marb.x + World.marble.diameter > path.x + path.HEIGHT && marb.y > path.y + path.WIDTH){
				if (Game.lpressed){
					World.marble.moveLeft();
				}
			}
			if (marb.y + World.marble.diameter > path.y + path.HEIGHT){
				if (Game.kpressed){
					World.marble.moveUp();
				}
			}
			if (marb.x < path.x && marb.y < path.y + path.WIDTH){
				if (Game.ipressed){
					World.marble.moveDown();
				}
				if (Game.jpressed){
					World.marble.moveRight();
				}
			}
			
		}


		else if(path.name == "BottomLeftCorner"){
			if (marb.x>path.x+path.WIDTH) {
				if (marb.y<path.y+path.WIDTH) {
					if (Game.ipressed) {
						World.marble.moveDown();
					}
				}
				else if (marb.y>path.y+path.HEIGHT) {
					if (Game.kpressed) {
						World.marble.moveUp();
					}
				}
			}
			else if (marb.y>path.y+path.WIDTH) {
				if (marb.x<path.x) {
					if (Game.jpressed) {
						World.marble.moveRight();
					}
				}
				else if (marb.y>path.y+path.HEIGHT) {
					if(Game.kpressed) {
						World.marble.moveUp();
					}
				}
			}
			else {
				if (marb.x == path.x) {
					if (Game.jpressed) {
						World.marble.moveRight();
					}
				}
				if (marb.x==path.x+path.WIDTH) {
					if (Game.lpressed) {
						World.marble.moveLeft();
					}
				}
			}
		}

		else{ //Horizontal
			if(marb.y < path.y){
				if (Game.ipressed){
					World.marble.moveDown();
				}
			}
 			if (marb.y + World.marble.diameter > path.y + path.WIDTH){
				if (Game.kpressed){
					World.marble.moveUp();
				}
			}			
		}
	}

//============================================
/** Method: checkDead()
	Functionality: If the marble has exceeded the limits of the path, Game.alive is set to false **/
//============================================

    public static void checkDead(){

		Path path = checkPath();
		Pair marb = World.marble.position;
		
		if(path.name == "Straight"){
			if(marb.x + World.marble.diameter / 3 < path.x || marb.x + World.marble.diameter / 3 * 2 > path.x + path.WIDTH){
				Game.alive = false;
			}
		}

		else if(path.name == "topLeftCorner"){
			if(marb.y + World.marble.diameter / 3 < path.y){
				Game.alive = false;

			}
			if(marb.x + World.marble.diameter / 3 < path.enterX){
				Game.alive = false;
			}

			if(marb.y - World.marble.diameter / 3 > path.y + path.WIDTH && marb.x + World.marble.diameter > path.enterX + path.WIDTH){
				Game.alive = false;
			} 
		} 

		else if(path.name == "TopRightCorner"){
			if(marb.y + World.marble.diameter / 3 < path.y){
				Game.alive = false;	
			}
			if(marb.x + World.marble.diameter / 3 * 2 > path.enterX + path.WIDTH){
				Game.alive = false;
			}
			if(marb.x + World.marble.diameter / 3 * 2 < path.enterX && marb.y + World.marble.diameter > path.y + path.WIDTH){
				Game.alive = false;
			}
		}

		else if(path.name == "BottomRightCorner"){

			if(marb.x - World.marble.diameter / 3 > path.enterX + path.HEIGHT){
				Game.alive = false;
			}

			if(marb.x + World.marble.diameter < path.x){
				if(marb.y + World.marble.diameter / 3 < path.y + path.WIDTH || marb.y - World.marble.diameter / 3 > path.y + path.HEIGHT){
					Game.alive = false;
				}
			}
			else if (marb.x > path.x){
				if (marb.y - World.marble.diameter > path.y + path.HEIGHT || marb.x + World.marble.diameter > path.x + path.WIDTH){
					Game.alive = false;
				}
			}
			if(marb.y - World.marble.diameter / 3 < path.y + path.WIDTH){
				if(marb.x + World.marble.diameter / 3 < path.x){
					Game.alive = false;
				}
			}
					
		}

		else if(path.name == "BottomLeftCorner"){
			if (marb.x > path.x + path.WIDTH) {
			    if (marb.y + World.marble.diameter / 3 < path.y + path.WIDTH){
					Game.alive=false;
				}
		   	 }
			else if (marb.x + World.marble.diameter / 3 < path.x || marb.y + World.marble.diameter / 3 > path.y + path.HEIGHT) {
				Game.alive=false;
			}
		}

		else if(path.name == "Horizontal"){
			if(marb.y + World.marble.diameter / 3 < path.y || marb.y - World.marble.diameter / 3 > path.y + path.WIDTH){
				Game.alive = false;
			}
		}

		else{//BigRect
			if(marb.x + World.marble.diameter / 2 < World.visibleScreens.get(0).get(1).x || marb.x - World.marble.diameter / 2 > World.visibleScreens.get(0).get(1).x + Path.WIDTH){
				if(marb.y - World.marble.diameter / 3 < path.y){
					Game.alive = false;
				}
			}
		}
    }

//============================================
/** Method: checkPath()
	Functionality: Uses the marble’s current ‘x’ and ‘y’ values to determine which segment of path the marble is on and then returns that path **/
//============================================

	public static Path checkPath(){

		LinkedList<Path> pathsWithSameYVals = new LinkedList<Path>();
		Path path;

		for(int i = 0; i < World.visibleScreens.size(); i++){
			for(int j = 0; j < World.visibleScreens.get(i).size(); j++){

				path = World.visibleScreens.get(i).get(j);

				if(path.name == "Horizontal"){
					if(World.marble.position.y - World.marble.diameter / 3 >= path.y && World.marble.position.y + World.marble.diameter / 3 < path.y + path.WIDTH){
						pathsWithSameYVals.add(path);
					}
				}
				else{
					if(World.marble.position.y >= path.y && World.marble.position.y < path.y + path.HEIGHT){
						pathsWithSameYVals.add(path);
					}
				}
			}
		}

		if(pathsWithSameYVals.size() == 1){
			return pathsWithSameYVals.get(0);
		}

		else{
			Path path2;
			for(int i = 0; i < pathsWithSameYVals.size(); i++){
				path2 = pathsWithSameYVals.get(i);

					int minX;
					int maxX;
					if(path2.x < path2.enterX){
						minX = path2.x;
						if(path2.name == "Straight"){
							maxX = minX + Path.WIDTH;
						}
						else if(path2.name == "BigRect"){
							maxX = minX + Game.WIDTH;
						}
						else{
							maxX = minX + Path.HEIGHT;
						}
						if(World.marble.position.x >= minX && World.marble.position.x + World.marble.diameter <= maxX){
							return path2;
						}
					}
					else{
						minX = path2.enterX;
						if(path2.name == "Straight"){
							maxX = minX + Path.WIDTH;
						}
						else if(path2.name == "BigRect"){
							maxX = minX + Game.WIDTH;
						}
						else{
							maxX = minX + Path.HEIGHT;
						}
					}
					if(World.marble.position.x >= minX && World.marble.position.x + World.marble.diameter / 2 <= maxX){
						return path2;
					}
			}
		}

		return pathsWithSameYVals.get(0);

	}

}

/** END OF MARBLE CLASS **/
//============================================
