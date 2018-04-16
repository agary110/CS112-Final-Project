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
	public static final int FPS = 60;
	public static World world;
	public static boolean alive;
	public static double points;

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
	}

	class Runner implements Runnable{
		public void run(){

			while(alive){

				world.nextFrame(1.0 / (double)(FPS));
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
       char c = e.getKeyChar();
		world.moveMarble(c);
    }

	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		System.out.println(c);
	}

	public void keyReleased(KeyEvent e) {
		char c=e.getKeyChar();
		System.out.println(c);
		
    }


	public static void main(String [] args){
		JFrame frame = new JFrame("aMAZE-ing Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game mainInstance = new Game();
		frame.setContentPane(mainInstance);
		frame.pack();
		frame.setVisible(true);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		world.drawToScreen(g);
	}
	/*public static void youLose(Graphics g) {
		if (alive=false) {
			char[] data={'u', 'r', 'd', 'e', 'a', 'd'};
	g.drawChars(data, 0, 6, 400, 400);
		}
	}*/
}
