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
	Pair position;
	Pair velocity;
	Pair acceleration;
	int radius;
	double dampening;
	Color color;
	double speedIncrement;
	public Marble(){
		Random rand = new Random(); 
		position = new Pair(Game.WIDTH / 2, 500.0);
		velocity = new Pair(0.0, 0.0);
		radius = 6;
		dampening = 1.3;
		double speedIncrement = 25.0;
		color = Color.BLUE;
    }
    public void update(World w, double time){
		position = position.add(velocity.times(time));
    }
    
    public void setPosition(Pair p){
		position = p;
    }
    public void setVelocity(Pair v){
		velocity = v;
    }
    public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval((int)this.position.x, (int)this.position.y, radius, radius);
		g.setColor(c);
    }

    public void moveUp(){

		velocity.y -= speedIncrement;
		if(position.y > Game.HEIGHT / 4){
			position.y -= 5;
		}

    } 

    public void moveDown(){
		velocity.y += speedIncrement;
    }

    public void moveRight(){
		velocity.x += speedIncrement;
	    position.x+=5.0;
		position.y += 0.6;
    }

    public void moveLeft(){
		velocity.x -= speedIncrement;
	    position.x-=5.0;
		position.y+= 0.6;
    }

    public static void checkDead(World w){

		Path path = checkPath();

		System.out.println("Name: " + path.name);
		System.out.println("X: " + path.x);
		System.out.println("exitX: " + path.exitX);


		Pair marb = World.marble.position;

		System.out.println("Marb: " + marb.x);
		System.out.println("Path: " + path.x + " " + path.exitX);

		if(path.name == "Straight"){
			if(marb.x < path.x || marb.x > path.x + path.WIDTH){
				Game.alive = false;
			}
		}

		else if(path.name == "rightCorner"){
			if(marb.y < path.y){
	System.out.println("you died b/c you're on top");

				Game.alive = false;
			}
			if(marb.x < path.exitX){
System.out.println("you died b/c you're too far left");

				Game.alive = false;
			}

			if(marb.y > path.y + path.WIDTH && marb.x > path.exitX + path.WIDTH){
				System.out.println("you died b/c you're in the weird gap place");

				Game.alive = false;
			}

		}

		else if(path.name == "leftCorner"){
			if(marb.y < path.y){
				Game.alive = false;
			}

			if(marb.x > path.exitX + path.WIDTH){
				Game.alive = false;
			}

			if(marb.x < path.exitX && marb.y > path.y + path.WIDTH){
				Game.alive = false;
			}
		}

		else if(path.name == "rightElbow"){
			if(marb.y > path.y + path.WIDTH){
				if(marb.x > path.exitX + path.HEIGHT){
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
				if(marb.x < path.x || marb.x > path.x + path.WIDTH){
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
			if(marb.y < path.y + path.WIDTH){
				Game.alive = false;
			}
			if(marb.x > path.x + path.WIDTH && marb.y < path.y + path.WIDTH){
				Game.alive = false;
			}
		}

		else{//Horizontal
			if(marb.y < path.y || marb.y > path.y + path.WIDTH){
				Game.alive = false;
			}
		}

		if(Game.alive == false) System.out.println("you dead");

    }

	private static Path checkPath(){

		Path [] visiblePaths = new Path [10];
		int j = 0;

		for(int i = 0; i < Map.upcomingPaths.size(); i++){
			if(Map.upcomingPaths.get(i).y > 0 && Map.upcomingPaths.get(i).y < Game.HEIGHT){
				visiblePaths [j] = Map.upcomingPaths.get(i);
				j++;
			}
		}

		/*for(int i = 1; i < visiblePaths.length - 1; i++){
			if(visiblePaths[i] != null){
				if(World.marble.position.y >= visiblePaths [i].y && World.marble.position.y < visiblePaths [i].y + visiblePaths [i].HEIGHT && World.marble.position.x>= visiblePaths[i].x && World.marble.position.x < visiblePaths[i].x+visiblePaths[i].WIDTH){
					return visiblePaths [i];
				}
			}
		}*/

		for(int i = 0; i < Map.upcomingPaths.size(); i++){
				if(World.marble.position.y >= Map.upcomingPaths.get(i).y && World.marble.position.y < Map.upcomingPaths.get(i).y + Map.upcomingPaths.get(i).HEIGHT 

/*&& (World.marble.position.x>= Map.upcomingPaths.get(i).x || World.marble.position.x>= Map.upcomingPaths.get(i).exitX) && (World.marble.position.x < Map.upcomingPaths.get(i).exitX+Map.upcomingPaths.get(i).WIDTH || World.marble.position.x < Map.upcomingPaths.get(i).x+Map.upcomingPaths.get(i).WIDTH)*/

){
					return Map.upcomingPaths.get(i);
				}
		}

		return new Path(0);

	}

}
