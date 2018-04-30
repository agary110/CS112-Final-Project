//================================================
/** Import necessary libraries **/
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;

public class Game extends JPanel implements KeyListener{

//================================================
/** Member variables **/
	public static final int WIDTH = 1400;
	public static final int HEIGHT = 750;
	public static final int FPS = 60;
	public static World world;
	public static boolean alive;
	public static boolean pressed;
	public static boolean hasGameStarted;
	char c;

	public static double tempHighScore;

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
		hasGameStarted=false;
		pressed=false;
		c = ' ';
		tempHighScore = 100;
	}

//================================================
/** Run method; **/
	class Runner implements Runnable{
		public void run(){

		    while(true){//alive){
				if(hasGameStarted){
					world.nextFrame(1.0 / (double)(FPS));
					if (pressed){
						world.moveMarble(c);
					}
					repaint();
					try{
						Thread.sleep(1000 / FPS);
					}
					catch(InterruptedException e){}
				}

				Logger.writeHighScore(World.points, "highscore.txt");
			}
		
		}
	}

	public void keyPressed(KeyEvent e) {
      	c = e.getKeyChar();
		pressed=true;
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		else if (c==' ') {
			hasGameStarted=true;
		}
		else if (e.getKeyCode()==KeyEvent.VK_ENTER) {
			JFrame frame = new JFrame("aMAZE-ing Maze");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Game mainInstance = new Game();
			frame.setContentPane(mainInstance);
			frame.pack();
			frame.setVisible(true);
//could probably just call main() here but I'm not positive how to do that?
//we should modify this so the whole thing totally resets (we're getting the weird floaty thing)
		}
    }

	public void keyTyped(KeyEvent e) {
		c = e.getKeyChar();
		world.shootAmmo(c);
	}

	public void keyReleased(KeyEvent e) {
		c=e.getKeyChar();
		pressed=false;
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
		if (hasGameStarted) {
			world.drawToScreen(g);
		}
		/*else {
			g.setColor(Color.GREEN);
			char[] welcome={'h', 'e', 'l', 'l', 'o', '!', ' ', 't', 'o', ' ', 'b', 'e', 'g', 'i', 	'n', ' ', 'p', 'l', 'a', 'y', 'i', 'n', 'g', ',', ' ', 'p', 'r', 'e', 's', 	's', ' ', 't', 'h', 'e', ' ', 's', 'p', 'a', 'c', 'e', ' ', 'b', 'a', 'r', 	'.'};
			g.drawChars(welcome, 0, welcome.length, 400, 400);
}
		if (!alive) {
			g.setColor(Color.GREEN);
			char[] data={'u', 'r', ' ', 'd', 'e', 'a', 'd', '!', ' ', 'p', 'r', 'e', 's', 's', ' ', 'e', 's', 'c', 'a', 'p', 'e', ' ', 't', 'o', ' ', 'c', 'l', 'o', 's', 'e', ' ', 'o', 'r', ' ',  'e', 'n', 't', 'e', 'r', ' ', 't', 'o', ' ', 'r', 'e', 's', 't', 'a', 'r', 't', '.'};
			g.drawChars(data, 0, data.length, 400, 400);
		}*/

		else{
			g.setColor(Color.GREEN);
			g.drawString("Hello! To begin playing, press the space bar. Press 'h' for help.", WIDTH / 3, HEIGHT / 2);
		}
		if(!alive){
			g.setColor(Color.GREEN);
			g.drawString("ur dead! Press escape to close or enter to restart.", WIDTH / 3, HEIGHT / 2);
		}
	}

}
