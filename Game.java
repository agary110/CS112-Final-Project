import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;

public class Game extends JPanel implements KeyListener{
	public static final int WIDTH = 1400;
	public static final int HEIGHT = 750;
	public static final int FPS = 30;
	public static World world;
	public static boolean alive;
	public static double points;
	public static boolean pressed;
	char c;

	public Game(){
		world = new World(WIDTH, HEIGHT);
		this.addKeyListener(this); 
		this.setFocusable(true);
		this.requestFocus();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		Thread mainThread = new Thread(new Runner());
		mainThread.start();
		Random rand = new Random();
		alive = true;
		points = 0;
		pressed=false;
		c = ' ';
	}

	class Runner implements Runnable{
		public void run(){

			while(true){

				world.nextFrame(1.0 / (double)(FPS));
				if (pressed){
					world.moveMarble(c);
				}
				if(world.map.upcomingPaths.getLast().y > 0){
					alive = false;
				}

				repaint();
				try{
					Thread.sleep(1000 / FPS);
				}
				catch(InterruptedException e){}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
      		c = e.getKeyChar();
		pressed=true;
		if (c=='c') {
		System.exit(0);
		}
		
		
    }

	public void keyTyped(KeyEvent e) {
		c = e.getKeyChar();
		System.out.println(c);
	}

	public void keyReleased(KeyEvent e) {
		c=e.getKeyChar();
		System.out.println(c);
		pressed=false;
		
    }


	public static void main(String [] args){
		JFrame frame = new JFrame("aMAZE-ing Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game mainInstance = new Game();
		frame.setContentPane(mainInstance);
		frame.pack();
		frame.setVisible(true);
		/*if (alive=false) {
			System.out.println("hjkasd");
			frame.dispose();
		}*/
	}

	public void paintComponent(Graphics g){
		if(alive){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		world.drawToScreen(g);
		}
		else{
			char[] data={'u', 'r', ' ', 'd', 'e', 'a', 'd', '!', ' ', 'p', 'r', 'e', 's', 's', ' ', 'C', ' ', 't', 'o', ' ', 'c', 'l', 'o', 's', 'e'};
			g.drawChars(data, 0, data.length, 400, 400);
			//draw You Lose here
		}
	}

}
