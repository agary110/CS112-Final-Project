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
		position = position.add(velocity.times(time));
    }
    
//================================================
//if we don't use these methods we should delete them
    public void setPosition(Pair p){
		position = p;
    }
    public void setVelocity(Pair v){
		velocity = v;
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
			velocity.y -= speedIncrement;
			if(position.y > Game.HEIGHT / 4){
				position.y -= 5;
			}
		}

    } 

    public void moveDown(){
		if (canMoveDown){
			velocity.y += speedIncrement;
			if(position.y < Game.HEIGHT - (Game.HEIGHT / 4)){
				position.y += 5;
			}		
		}
    }

    public void moveRight(){
		if (canMoveRight){
			velocity.x += speedIncrement;
	   	 	position.x += 5.0;
			position.y += 0.6;
		}
    }

    public void moveLeft(){
		if (canMoveLeft){
			velocity.x -= speedIncrement;
	    	position.x -= 5.0;
			position.y += 0.6;
		}
    }
//===================================================
/** If the marble has picked up the Bumpers booster, bumpers are on, and the marble should be unable to move off the path (aka you can't die). It does this by checking where the marble is in relation to the current path; if it's about to be off the path, all the move methods (above) do not work. **/


	public static void checkForBumpers(World w){
		Path path = checkPath();
		Pair marb = World.marble.position;
		if(path.name == "Straight"){
			if(marb.x < path.x){
				canMoveRight = true;
				canMoveLeft = false;
				canMoveUp = true;
				canMoveDown = true;
			}
			if (marb.x + World.marble.radius > path.x + path.WIDTH){
				canMoveRight = false;
				canMoveLeft = true;
				canMoveUp = true;
				canMoveDown = true;
			}
	
		}
		
		else if(path.name == "rightCorner"){
			if(marb.y < path.y){
				canMoveRight = true;
				canMoveLeft = true;
				canMoveUp = false;
				canMoveDown = true;
			}
			if(marb.x < path.exitX && marb.y > path.y){
				canMoveRight = true;
				canMoveLeft = false;
				canMoveUp = true;
				canMoveDown = true;
			}

			if(marb.y > path.y + path.WIDTH && marb.x + World.marble.radius > path.exitX + path.WIDTH){
				canMoveRight = false;
				canMoveLeft = true;
				canMoveUp = true;
				canMoveDown = true;
			}
			if (marb.x > path.exitX + path.WIDTH && marb.y > path.y + path.WIDTH){
				canMoveRight = true;
				canMoveLeft = true;
				canMoveUp = true;
				canMoveDown = false;
			}
		}

		else if(path.name == "leftCorner"){
			if(marb.y < path.y){
				canMoveRight = true;
				canMoveLeft = true;
				canMoveUp = false;
				canMoveDown = true;
			}
			if(marb.x + World.marble.radius > path.exitX + path.WIDTH){
				canMoveRight = false;
				canMoveLeft = true;
				canMoveUp = true;
				canMoveDown = true;
			}
			if(marb.x < path.exitX && marb.y > path.y + path.WIDTH){
				canMoveRight = true;
				canMoveLeft = false;
				canMoveUp = true;
				canMoveDown = false;
			}
		}

		else if(path.name == "rightElbow"){
			if (marb.x + World.marble.radius > path.x + path.WIDTH){
				canMoveRight = false;
				canMoveLeft = true;
				canMoveUp = true;
				canMoveDown = true;
			}
			if (marb.y > path.y + path.HEIGHT){
				canMoveRight = true;
				canMoveLeft = true;
				canMoveUp = true;
				canMoveDown = false;
			}
			if (mark.x < path.x && marb.y < path.y + path.WIDTH){
				canMoveRight = true;
				canMoveLeft = true;
				canMoveUp = false;
				canMoveDown = true;
			}
		}


			/*if(marb.y > path.y + path.WIDTH){
				if(marb.x + World.marble.radius > path.exitX + path.HEIGHT){
					canMoveRight = false;
					canMoveLeft = true;
					canMoveUp = true;
					canMoveDown = true;
				}
				/*if(marb.y > path.y + path.HEIGHT){
					canMoveRight = true;
					canMoveLeft = true;
					canMoveUp = true;
					canMoveDown = false;
				}*/
				if(marb.x < path.x){
					if(marb.y < path.y + path.WIDTH){
						canMoveRight = true;
						canMoveLeft = true;
						canMoveUp = false;
						canMoveDown = true;
					}
				}
			}
			else{
				if(marb.x < path.x){
					canMoveRight = true;
					canMoveLeft = false;
					canMoveUp = true;
					canMoveDown = true;
				}
				if (marb.x + World.marble.radius > path.x + path.WIDTH){
					canMoveRight = false;
					canMoveLeft = true;
					canMoveUp = true;
					canMoveDown = true;
				}
			}/*
		//}

		else if(path.name == "leftElbow"){
			if (marb.x < path.x){
				canMoveRight = true;
				canMoveLeft = false;
				canMoveUp = true;
				canMoveDown = true;
			}
			if (marb.y < path.y + path.HEIGHT && marb.x > path.x + path.WIDTH){
				canMoveRight = true;
				canMoveLeft = false;
				canMoveUp = true;
				canMoveDown = true;
			}
			if (marb.y > path.y + path.HEIGHT){
				canMoveRight = true;
				canMoveLeft = true;
				canMoveUp = true;
				canMoveDown = false;
			}
			if (marb.x > path.x + path.WIDTH && marb.y < path.y + path.WIDTH){
				canMoveRight = true;
				canMoveLeft = true;
				canMoveUp = false;
				canMoveDown = true; 
			}
		}

		else{ //Horizontal
			if(marb.y < path.y){
				canMoveRight = true;
				canMoveLeft = true;
				canMoveUp = false;
				canMoveDown = true;
			}
 			if (marb.y > path.y + path.WIDTH){
				canMoveRight = true;
				canMoveLeft = true;
				canMoveUp = true;
				canMoveDown = false;
			}			
		}
	}
//============================================
/** This method checks to see if the marble has gone off the path. If it has, you die. **/

    public static void checkDead(World w){

		Path path = checkPath();
		Pair marb = World.marble.position;
		
		
		if(path.name == "Straight"){
			if(marb.x < path.x || marb.x + World.marble.radius > path.x + path.WIDTH){
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

			if(marb.x < path.exitX && marb.y > path.y + path.WIDTH){
				Game.alive = false;
			}
		}

		else if(path.name == "rightElbow"){
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


		else if(path.name == "leftElbow"){
			if(marb.y > path.y + path.HEIGHT){
				Game.alive = false;
			}

			if(marb.x < path.x){
				Game.alive = false;
			}
			if(marb.y < path.y){
				Game.alive = false;
			}
			if(marb.x + World.marble.radius > path.x + path.WIDTH && marb.y < path.y + path.WIDTH){
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

	private static Path checkPath(){

		Path [] visiblePaths = new Path [10];
		int j = 0;

		for(int i = 0; i < Map.upcomingPaths.size(); i++){
			if(Map.upcomingPaths.get(i).y > 0 && Map.upcomingPaths.get(i).y < Game.HEIGHT){
				visiblePaths [j] = Map.upcomingPaths.get(i);
				j++;
			}
		}

		for(int i = 0; i < Map.upcomingPaths.size(); i++){
				if(World.marble.position.y >= Map.upcomingPaths.get(i).y && World.marble.position.y < Map.upcomingPaths.get(i).y + Map.upcomingPaths.get(i).HEIGHT){
					return Map.upcomingPaths.get(i);
				}
		}

		return null;

	}

}
