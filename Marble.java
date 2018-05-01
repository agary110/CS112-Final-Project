import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Pair{

    public double x;
    public double y;
    
    public Pair(double initX, double initY){
		x = initX;
		y = initY;
    }
}


public class Marble{

//================================================
/** Member variables **/

	Pair position;
	Pair acceleration;
	int radius;
	double dampening;
	Color color;
	double XposIncrement;
	double YposIncrement;
	double speedIncrement = 0;

//should delete these if don't use them
	static boolean canMoveUp;
	static boolean canMoveRight;
	static boolean canMoveLeft;
	static boolean canMoveDown;


//================================================
/** Constructor  **/

	public Marble(){
		Random rand = new Random(); 
		position = new Pair(Game.WIDTH / 2, 500.0);
		radius = 25;
		XposIncrement = 5.0;
		YposIncrement = 0.6;
		color = Color.BLUE;
		canMoveUp = true;
		canMoveRight = true;
		canMoveLeft = true;
		canMoveDown = true;
    }
//================================================
/** Update method; moves the marble's position based on its velocity and how much time has passed  **/

    public void update(double time){
		position = position;
    }
    
//================================================
/** Draw method **/

    public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval((int)this.position.x, (int)this.position.y, radius, radius);
		g.setColor(c);
    }

//================================================
/** Takes the move commands from the KeyboardEvent in Game.java. If the marble is able to move (i.e. the bumpers are down, or the bumpers are down but the marble isn't hitting the sides), then it will move. **/

    public void moveUp(){
		if(position.y > Game.HEIGHT / 4){
			position.y -= XposIncrement;
		}
    } 

    public void moveDown(){
		if(position.y < Game.HEIGHT - (Game.HEIGHT / 4)){
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
/** If the marble has picked up the Bumpers booster, bumpers are on, and the marble should be unable to move off the path (aka you can't die). It does this by checking where the marble is in relation to the current path; if it's about to be off the path, all the move methods (above) do not work. **/


	public static void checkForBumpers(World w){
		Path path = checkPath();
		Pair marb = World.marble.position;
		if(path.name == "Straight"){
			if(marb.x < path.x){
				if (Game.jpressed){
					World.marble.moveRight();
				}
			}
			if (marb.x + World.marble.radius > path.x + path.WIDTH){
				if (Game.lpressed){
					World.marble.moveLeft();
				}
			}
	
		}
		
		else if(path.name == "rightCorner"){
			if(marb.y < path.y){
				if (Game.ipressed){
					World.marble.moveDown();
					//canMoveUp = false;
				}
			}
			if(marb.x < path.x - path.HEIGHT){
				if (Game.jpressed){
					World.marble.moveRight();
				}
			}

			if (marb.y + World.marble.radius > path.y + path.WIDTH && marb.x + World.marble.radius > path.x - path.WIDTH){
				if (Game.kpressed){
					World.marble.moveUp();
				}
				if (Game.lpressed){
					World.marble.moveLeft();
				}
			}
		}

		else if(path.name == "leftCorner"){
			if(marb.y < path.y){
				if (Game.ipressed){
					World.marble.moveDown();
					//canMoveUp = false;
				}
			}
			if(marb.x + World.marble.radius > path.exitX + path.WIDTH){
				if (Game.lpressed){
					World.marble.moveLeft();
				}
			}
			if(marb.x < path.exitX && marb.y + World.marble.radius > path.y + path.WIDTH){
				if (Game.kpressed){
					World.marble.moveUp();
				}
				if (Game.jpressed){
					World.marble.moveRight();
				}
			}
		}

		else if(path.name == "rightElbow"){
			if (marb.x + World.marble.radius >= path.exitX + path.HEIGHT){ // going out of right side
				if (Game.lpressed){
					World.marble.moveLeft();
					//System.out.println("rightElbow right side");
				}
			}
			if (marb.x + World.marble.radius > path.x + path.HEIGHT && marb.y > path.y + path.WIDTH){
				if (Game.lpressed){
					World.marble.moveLeft();
					//System.out.println("rightElbow right side");
				}
			}
			if (marb.y + World.marble.radius > path.y + path.HEIGHT){ // going out of bottom
				if (Game.kpressed){
					World.marble.moveUp();
				}
			}
			if (marb.x < path.x && marb.y < path.y + path.WIDTH){ // going into empty space
				if (Game.ipressed){
					World.marble.moveDown();
					//canMoveUp = false;
				}
				if (Game.jpressed){
					World.marble.moveRight();
				}
			}
			
		}


		else if(path.name == "leftElbow"){
			/*if (marb.x < path.x){
				if (Game.jpressed){
					World.marble.moveRight();
				}
			}
			if (marb.y + World.marble.radius > path.y + path.HEIGHT){
				if (Game.kpressed){
					World.marble.moveUp();
				}
			}
			if (marb.x > path.x + path.WIDTH && marb.y < path.y + path.WIDTH){
				if (Game.ipressed){
					World.marble.moveDown();
					//canMoveUp = false;
				}
				if (Game.lpressed){
					World.marble.moveLeft();
				}
			} */
			if (marb.x>path.x+path.WIDTH) {
				System.out.println("in 1 and 2");
				if (marb.y<path.y+path.WIDTH) {
				System.out.println("going out top of 1 and 2");
					if (Game.ipressed) {
						World.marble.moveDown();
					}
				}
				else if (marb.y>path.y+path.HEIGHT) {
				System.out.println("going out bottom of 1 and 2");
					if (Game.kpressed) {
						World.marble.moveUp();
					}
				}
			}
			else if (marb.y>path.y+path.WIDTH) {
				System.out.println("in 2");
				if (marb.x<path.x) {
				System.out.println("going out side of 2");
					if (Game.jpressed) {
					System.out.println("is j pressed?");
						World.marble.moveRight();
					}
				}
				else if (marb.y>path.y+path.HEIGHT) {
				System.out.println("going out bottom of 2");
					if(Game.kpressed) {
						World.marble.moveUp();
					}
				}
			}
			else {
			System.out.println("in 3");
				if (marb.x == path.x) {
				System.out.println("going out left side of 3");
					if (Game.jpressed) {
						World.marble.moveRight();
					}
				}
				if (marb.x==path.x+path.WIDTH) {
				System.out.println("going out right side of 3");

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
					//canMoveUp = false;

				}
			}
 			if (marb.y + World.marble.radius > path.y + path.WIDTH){
				if (Game.kpressed){
					World.marble.moveUp();
				}
			}			
		}
	}
//============================================
/** This method checks to see if the marble has gone off the path. If it has, you die. **/

    public static void checkDead(World w){

		Path path = checkPath();
		Pair marb = World.marble.position;
		

		/*** Null pointer exception occurs here because checkPath() returns a null ***/
		if(path.name == "Straight"){
			if(marb.x + World.marble.radius / 3 < path.x || marb.x + World.marble.radius / 3 * 2 > path.x + path.WIDTH){
				Game.alive = false;
				System.out.println("died bc straight");
			}
		}

		else if(path.name == "rightCorner"){
			if(marb.y + World.marble.radius / 3 < path.y){
				Game.alive = false;
				System.out.println("died bc rightCorner");

			}
			if(marb.x + World.marble.radius / 3 < path.exitX){
				Game.alive = false;
				System.out.println("died bc rightCorner");
			}

			if(marb.y - World.marble.radius / 3 > path.y + path.WIDTH && marb.x + World.marble.radius > path.exitX + path.WIDTH){
				Game.alive = false;
				System.out.println("died bc rightCorner");
			} 
			/*if (marb.x<path.x+path.WIDTH) {
				if (marb.x<path.exitX || marb.y>path.y+path.HEIGHT || marb.y<path.y) {
				Game.alive=false;
	} 
	}
			else {
				if(marb.y<path.y || marb.y>path.y+path.WIDTH) {
				Game.alive=false;
			}
		} */

		} 

		else if(path.name == "leftCorner"){
			if(marb.y + World.marble.radius / 3 < path.y){
				Game.alive = false;	
				System.out.println("died bc leftCorner");			
			}
			if(marb.x + World.marble.radius / 3 * 2 > path.exitX + path.WIDTH){
				Game.alive = false;
				System.out.println("died bc leftCorner");
			}
			if(marb.x + World.marble.radius / 3 * 2 < path.exitX && marb.y + World.marble.radius > path.y + path.WIDTH){
				Game.alive = false;
				System.out.println("died bc leftCorner");
			}
		}

		else if(path.name == "rightElbow"){ // DOESN'T DIE WHEN YOU GO OUT THE RIGHT SIDE
			if(marb.y - World.marble.radius / 3 > path.y + path.WIDTH){
				if(marb.x + World.marble.radius / 3 * 2 > path.exitX + path.HEIGHT){
					Game.alive = false;
				System.out.println("died bc rightElbow");
				}

				if(marb.y + World.marble.radius / 3 * 2 > path.y + path.HEIGHT){
					Game.alive = false;
				System.out.println("died bc rightElbow");

				}

				if(marb.x - World.marble.radius / 3 < path.x){
					if(marb.y + World.marble.radius / 3 < path.y + path.WIDTH){
						Game.alive = false;
				System.out.println("died bc rightElbow");

					}
				}
			}

			else{
				if(marb.x + World.marble.radius / 3 < path.x || marb.x + World.marble.radius / 3 * 2 > path.x + path.WIDTH){
					Game.alive = false;
				System.out.println("died bc rightElbow");

				}
			}
		}
	/*	else if(path.name == "rightElbow"){
			if (marb.x + World.marble.radius >= path.exitX + path.WIDTH){ // going out of right side
				Game.alive = false;
			}
			if (marb.y + World.marble.radius > path.y + path.HEIGHT){ // going out of bottom
				Game.alive = false;
			}
			if (marb.x < path.x && marb.y < path.y + path.WIDTH){ // going into empty space
				Game.alive = false;
			}
			
		}*/



		else if(path.name == "leftElbow"){
/*			if (marb.x + World.marble.radius / 3 < path.x){
				Game.alive = false;
				System.out.println("died bc leftElbow");

			}
			if (marb.y + World.marble.radius / 3 * 2 > path.y + path.HEIGHT){
				Game.alive = false;
				System.out.println("died bc leftElbow");

			}
			if (marb.x - World.marble.radius / 3 > path.x + path.WIDTH && marb.y + World.marble.radius / 3 < path.y + path.WIDTH){
				Game.alive = false;
				System.out.println("died bc leftElbow");

			}
*/
			if (marb.x<path.exitX && marb.x>path.exitX+path.WIDTH) {
			    if (marb.y>path.y+path.HEIGHT || marb.y<path.y+path.WIDTH){
				Game.alive=false;
				System.out.println("died bc leftElbow");

			}
		    }
			else if (marb.x<path.x || marb.y>path.y+path.HEIGHT || marb.y<path.y) {
				Game.alive=false;
				System.out.println("died bc leftElbow");

}
		}



		else if(path.name == "Horizontal"){
			if(marb.y + World.marble.radius / 3 < path.y || marb.y - World.marble.radius / 3 > path.y + path.WIDTH){
				Game.alive = false;
				System.out.println("died bc horizontal");

			}
		}

		else{//BigRect
			if(marb.x + World.marble.radius / 2 < World.mapsOnScreen.get(0).get(1).x || marb.x - World.marble.radius / 2 > World.mapsOnScreen.get(0).get(1).x + Path.WIDTH){
				if(marb.y - World.marble.radius / 3 < path.y){
					Game.alive = false;
				}
			}
		}
    }
//============================================
/** Get all the paths that currently exist, check which one the marble is currently on, and return it. **/

	public static Path checkPath(){

		Path [] visiblePaths = new Path [100];
		int n = 0;


		/*
		//Puts all paths that are currently visible into visiblePaths
		for(int i = 0; i < World.mapsOnScreen.size(); i++){
			for(int j = 0; j < World.mapsOnScreen.get(i).size(); j++){
				if(World.mapsOnScreen.get(i).get(j).y > 0 && World.mapsOnScreen.get(i).get(j).y < Game.HEIGHT){
					visiblePaths [n] = World.mapsOnScreen.get(i).get(j);
					n++;
				}
			}
		}
		*/
		for(int i = 0; i < World.mapsOnScreen.size(); i++){
			for(int j = 0; j < World.mapsOnScreen.get(i).size(); j++){
				if (i==0 && j==0) {
				j++;
				}
				else if(World.marble.position.y >= World.mapsOnScreen.get(i).get(j).y && World.marble.position.y < World.mapsOnScreen.get(i).get(j).y + World.mapsOnScreen.get(i).get(j).HEIGHT){
					return World.mapsOnScreen.get(i).get(j);
				}
			}
		}

		//Attempt to rewrite
		/*for(int i = 0; i < visiblePaths.length; i++){
			if(visiblePaths[i] == null){
				break;
			}
			if(World.marble.position.y >= visiblePaths[i].y){
				if(visiblePaths [i].name == "Horizontal" && World.marble.position.y <= visiblePaths [i].y + Path.WIDTH){
					return visiblePaths [i];
				}
				else if(World.marble.position.y <= visiblePaths [i].y + Path.HEIGHT){
					return visiblePaths [i];
				}
			}
		}*/

		System.out.println("checkPath() is returning a null");
		System.out.println("World.mapsOnScreen: " +  World.mapsOnScreen.size());
		return null;

	}

}
