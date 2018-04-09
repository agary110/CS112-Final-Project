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


class Marble{
    Pair position;
    Pair velocity;
    Pair acceleration;
    double radius;
    double dampening;
    Color color;
    public Marble(){
	Random rand = new Random(); 
	position = new Pair(500.0, 500.0);
	velocity = new Pair(0.0, 0.0);
	radius = 25;
	dampening = 1.3;
	color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }
    public void update(World w, double time){
	position = position.add(velocity.times(time));
	bounce(w);
    }
    
    public void setPosition(Pair p){
	position = p;
    }
    public void setVelocity(Pair v){
	velocity = v;
    }
    public void draw(Graphics g){
	Color c = g.getColor();
	
	g.setColor(Color.RED);
	g.fillOval(100,100,100,100);
	g.setColor(c);
    }

    public void moveUp(){
	marble.velocity.y -= 25.0;
    }

    public void moveDown(){
	marble.velocity.y += 25.0;
    }

    public void moveRight(){
	marble.velocity.x += 25.0;
    }

    public void moveLeft(){
	marble.velocity.x -= 25.0;
    }

    private void bounce(World w){
	if (position.x - radius < 0){
	    velocity.x = 0.0;
	    position.x = radius;
	}
	else if (position.x + radius > w.WIDTH){
	    velocity.x = 0.0;
	    position.x = w.WIDTH - radius;
	 
	}
	if (position.y - radius < 0){
	    velocity.y = 0.0;
	    position.y = radius;
	}
	else if(position.y + radius >  w.HEIGHT){
	    velocity.y = 0.0;
	    position.y = w.HEIGHT - radius;
	}
    }

}


