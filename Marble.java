import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Pair{

/** NOTE: let's make sure to get rid of unnecessary methods **/

    public double x;
    public double y;
    
    public Pair(double initX, double initY){
		x = initX;
		y = initY;
    }

    public Pair add(Pair toAdd){
		return new Pair(x + toAdd.x, y + toAdd.y);
	}

	public Pair divide(double denom){
		return new Pair(x / denom, y / denom);
	}

	public Pair times(double val){
		return new Pair(x * val, y * val);
	}

    public void flipX(){
		x = -x;
	}
    
	public void flipY(){
		y = -y;
	}
}


public class Marble{

//================================================
/** Member variables **/

	Pair position;
	Pair velocity;
	Pair acceleration;
	int radius;
	double dampening;
	Color color;
	double speedIncrement;
	static boolean canMoveUp;
	static boolean canMoveRight;
	static boolean canMoveLeft;
	static boolean canMoveDown;

//================================================
/** Constructor  **/

	public Marble(){
		Random rand = new Random(); 
		position = new Pair(Game.WIDTH / 2, 500.0);
		velocity = new Pair(0.0, 0.0);
		radius = 25;
		dampening = 1.3;
		double speedIncrement = 25.0;
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
		if (canMoveUp){
			if(position.y > Game.HEIGHT / 4){
				position.y -= 5;
			}
		} else {
			position.y += 0.6;
		}

    } 

    public void moveDown(){
		if(position.y < Game.HEIGHT - (Game.HEIGHT / 4)){
			position.y += 5;
		}
		canMoveUp = true;
    }

    public void moveRight(){
	   	position.x += 5.0;
		position.y += 0.6;
    }

    public void moveLeft(){
	    position.x -= 5.0;
		position.y += 0.6;		
    }
//===================================================
/** If the marble has picked up the Bumpers booster, bumpers are on, and the marble should be unable to move off the path (aka you can't die). It does this by checking where the marble is in relation to the current path; if it's about to be off the path, all the move methods (above) do not work. **/


	public static void checkForBumpers(World w, char c){
		Path path = checkPath();
		Pair marb = World.marble.position;
		if(path.name == "Straight"){
			if(marb.x < path.x){
				if (c == 'j'){
					World.marble.moveRight();
				}
			}
			if (marb.x + World.marble.radius > path.x + path.WIDTH){
				if (c == 'l'){
					World.marble.moveLeft();
				}
			}
	
		}
		
		else if(path.name == "rightCorner"){
			if(marb.y < path.y){
				if (c == 'i'){
					World.marble.moveDown();
					//canMoveUp = false;
				}
			}
			if(marb.x < path.x - path.HEIGHT){
				if (c == 'j'){
					World.marble.moveRight();
				}
			}

			if (marb.y + World.marble.radius > path.y + path.WIDTH && marb.x + World.marble.radius > path.x - path.WIDTH){
				if (c == 'k'){
					World.marble.moveUp();
				}
				if (c == 'l'){
					World.marble.moveLeft();
				}
			}
		}

		else if(path.name == "leftCorner"){
			if(marb.y < path.y){
				if (c == 'i'){
					World.marble.moveDown();
					//canMoveUp = false;
				}
			}
			if(marb.x + World.marble.radius > path.exitX + path.WIDTH){
				if (c == 'l'){
					World.marble.moveLeft();
				}
			}
			if(marb.x < path.exitX && marb.y + World.marble.radius > path.y + path.WIDTH){
				if (c == 'k'){
					World.marble.moveUp();
				}
				if (c == 'j'){
					World.marble.moveRight();
				}
			}
		}

		else if(path.name == "rightElbow"){
			if (marb.x + World.marble.radius >= path.exitX + path.WIDTH){ // going out of right side
				if (c == 'l'){
					World.marble.moveLeft();
					System.out.println("rightElbow right side");
				}
			}
			if (marb.y + World.marble.radius > path.y + path.HEIGHT){ // going out of bottom
				if (c == 'k'){
					World.marble.moveUp();
				}
			}
			if (marb.x < path.x && marb.y < path.y + path.WIDTH){ // going into empty space
				if (c == 'i'){
					World.marble.moveDown();
					//canMoveUp = false;
				}
				if (c == 'j'){
					World.marble.moveRight();
				}
			}
			
		}


		else if(path.name == "leftElbow"){
			if (marb.x < path.x){
				if (c == 'j'){
					World.marble.moveRight();
				}
			}
			if (marb.y + World.marble.radius > path.y + path.HEIGHT){
				if (c == 'k'){
					World.marble.moveUp();
				}
			}
			if (marb.x > path.x + path.WIDTH && marb.y < path.y + path.WIDTH){
				if (c == 'i'){
					World.marble.moveDown();
					//canMoveUp = false;
				}
				if (c == 'l'){
					World.marble.moveLeft();
				}
			}
		}

		else{ //Horizontal
			if(marb.y < path.y){
				if (c == 'i'){
					World.marble.moveDown();
					//canMoveUp = false;

				}
			}
 			if (marb.y + World.marble.radius > path.y + path.WIDTH){
				if (c == 'k'){
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
			}
		}

		else if(path.name == "rightCorner"){
			if(marb.y < path.y){
				Game.alive = false;
			}
			if(marb.x < path.exitX){
				Game.alive = false;
			}

			if(marb.y > path.y + path.WIDTH && marb.x + World.marble.radius > path.exitX + path.WIDTH){
				Game.alive = false;
			}

		}

		else if(path.name == "leftCorner"){
			if(marb.y < path.y){
				Game.alive = false;				
			}
			if(marb.x + World.marble.radius > path.exitX + path.WIDTH){
				Game.alive = false;
			}
			if(marb.x < path.exitX && marb.y + World.marble.radius > path.y + path.WIDTH){
				Game.alive = false;
			}
		}

		else if(path.name == "rightElbow"){ // DOESN'T DIE WHEN YOU GO OUT THE RIGHT SIDE
			if(marb.y > path.y + path.WIDTH){
				if(marb.x + World.marble.radius > path.exitX + path.HEIGHT){
					Game.alive = false;
				}

				if(marb.y > path.y + path.HEIGHT){
					Game.alive = false;
				}

				if(marb.x < path.x){
					if(marb.y < path.y + path.WIDTH){
						Game.alive = false;
					}
				}
			}

			else{
				if(marb.x < path.x || marb.x + World.marble.radius > path.x + path.WIDTH){
					Game.alive = false;
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
			if (marb.x < path.x){
				Game.alive = false;
			}
			if (marb.y + World.marble.radius > path.y + path.HEIGHT){
				Game.alive = false;
			}
			if (marb.x > path.x + path.WIDTH && marb.y < path.y + path.WIDTH){
				Game.alive = false;
			}
		}

		else{//Horizontal
			if(marb.y < path.y || marb.y > path.y + path.WIDTH){
				Game.alive = false;
			}
		}
    }
//============================================
/** Get all the paths that currently exist, check which one the marble is currently on, and return it. **/

	public static Path checkPath(){

		Path [] visiblePaths = new Path [100];
		int n = 0;

		//Puts all paths that are currently visible into visiblePaths
		for(int i = 0; i < World.mapsOnScreen.size(); i++){
			for(int j = 0; j < World.mapsOnScreen.get(i).size(); j++){
				if(World.mapsOnScreen.get(i).get(j).y > 0 && World.mapsOnScreen.get(i).get(j).y < Game.HEIGHT){
					visiblePaths [n] = World.mapsOnScreen.get(i).get(j);
					n++;
				}
			}
		}

		for(int i = 0; i < World.mapsOnScreen.size(); i++){
			for(int j = 0; j < World.mapsOnScreen.get(i).size(); j++){
				if(World.marble.position.y >= World.mapsOnScreen.get(i).get(j).y && World.marble.position.y < World.mapsOnScreen.get(i).get(j).y + World.mapsOnScreen.get(i).get(j).HEIGHT){
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
		return null;

	}

}
