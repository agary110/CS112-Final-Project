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
	acceleration = new Pair(0.0, 0.0);
	radius = 25;
	dampening = 1.3;
	color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }
    public void update(World w, double time){
	position = position.add(velocity.times(time));
	velocity = velocity.add(acceleration.times(time));
	bounce(w);
    }
    
    public void setPosition(Pair p){
	position = p;
    }
    public void setVelocity(Pair v){
	velocity = v;
    }
    public void setAcceleration(Pair a){
	acceleration = a;
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
	else if (position.x + radius > w.width){
	    velocity.x = 0.0;
	    position.x = w.width - radius;
	 
	}
	if (position.y - radius < 0){
	    velocity.y = 0.0;
	    position.y = radius;
	}
	else if(position.y + radius >  w.height){
	    velocity.y = 0.0;
	    position.y = w.height - radius;
	}
    }

}

public class KeyMarble extends JPanel implements KeyListener{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    World world;

    class Runner implements Runnable{
	public void run()
	{
	    while(true){
		world.updateMarble(1.0 / (double)FPS);
		repaint();
		try{
		    Thread.sleep(1000/FPS);
		}
		catch(InterruptedException e){}
	    }

	}
    
    }

    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
	System.out.println("You pressed down: " + c);
	world.moveMarble(c);			

    }
    public void keyReleased(KeyEvent e) {
        char c=e.getKeyChar();
	System.out.println("\tYou let go of: " + c);
	world.stopMarble(c);
	
    }

    public void keyTyped(KeyEvent e) {
	char c = e.getKeyChar();
	System.out.println("You typed: " + c);
	
    }
     public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public KeyMarble(){
	world = new World(WIDTH, HEIGHT);
	addKeyListener(this);
	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	Thread mainThread = new Thread(new Runner());
	mainThread.start();
    }
    
    public static void main(String[] args){
	JFrame frame = new JFrame("Physics!!!");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	KeyMarble mainInstance = new KeyMarble();
	frame.setContentPane(mainInstance);
	frame.pack();
	frame.setVisible(true);
    }


    public void paintComponent(Graphics g) {
	super.paintComponent(g);    	

	g.setColor(Color.BLACK);
	g.fillRect(0, 0, WIDTH, HEIGHT);

	world.drawMarble(g);

    }

    
}

