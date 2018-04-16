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
	double radius;
	double dampening;
	Color color;
	double speedIncrement;
	public Marble(){
		Random rand = new Random(); 
		position = new Pair(500.0, 500.0);
		velocity = new Pair(0.0, 0.0);
		radius = 25;
		dampening = 1.3;
		double speedIncrement = 25.0;
		color = Color.RED;
    }
    public void update(World w, double time){
		position = position.add(velocity.times(time));
		hitwalls(w);
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
		g.fillOval((int)this.position.x, (int)this.position.y, 100, 100);
		g.setColor(c);
    }

    public void moveUp(){
		velocity.y -= speedIncrement;
	    System.out.println("Inside moveUp");
    }

    public void moveDown(){
		velocity.y += speedIncrement;
    }

    public void moveRight(){
		velocity.x += speedIncrement;
	    position.x+=5.0;
	    System.out.println("Inside moveRight");
    }

    public void moveLeft(){
		velocity.x -= speedIncrement;
	    position.x-=5.0;
    }

    private void hitwalls(World w){
		if (position.x - radius < 0){
			velocity.x = 0.0;
			position.x = radius;
		}
		if (position.x + radius > w.WIDTH){
			velocity.x = 0.0;
			position.x = w.WIDTH - radius;
	 
		}
		if (position.y - radius < 0){
			velocity.y = 0.0;
			position.y = radius;
		}
		if(position.y + radius >  w.HEIGHT){
			velocity.y = 0.0;
			position.y = w.HEIGHT - radius;
		}
    }

}


