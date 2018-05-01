//================================================
/** Import necessary libraries **/
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

public class Game extends JPanel implements KeyListener{

//================================================
/** Member variables **/
	public static final int WIDTH = 1400;
	public static final int HEIGHT = 750;
	public static final int FPS = 60;
	public static World world;
	public static boolean alive;
	public static boolean helpDrawn;
	public static boolean ipressed;
	public static boolean jpressed;
	public static boolean kpressed;
	public static boolean lpressed;
	public static boolean pressed;


	public static boolean hasGameStarted;
	public static boolean paused;
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
		alive = true;
		helpDrawn = true;
		hasGameStarted=false;
		paused = false;
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
					if(paused == false){

						world.nextFrame(1.0 / (double)(FPS));
						if (pressed){
							world.moveMarble();
						}
						repaint();
					}
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
		if (c == 'a' || c == 'A'){
			world.shootAmmo();
		}
		if (c == 'i' || c == 'I'){
			ipressed = true;
		}
		if (c == 'j'|| c == 'J'){
			jpressed = true;
		}
		if (c == 'k' || c == 'K'){
			kpressed = true;
		}
		if (c == 'l' || c == 'L'){
			lpressed = true;
		}
		if(c == 'h' || c == 'H'){
			if(paused == false){
				helpDrawn = true;
			}
			else{
				paused = false;
				helpDrawn = false;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		c=e.getKeyChar();
		pressed=false;
		if (c == 'i' || c == 'I'){
			ipressed = false;
		}
		if (c == 'j' || c == 'J'){
			jpressed = false;
		}
		if (c == 'k' || c == 'K'){
			kpressed = false;
		}
		if (c == 'l' || c == 'L'){
			lpressed = false;
		}
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
		else{
			g.setColor(Color.GREEN);
			g.drawString("Hello! To begin playing, press the space bar.", WIDTH / 3, HEIGHT / 2);
		}
		if(!alive){
			g.setColor(Color.GREEN);
			g.drawString("ur dead! Press escape to close or enter to restart.", WIDTH / 3, HEIGHT / 2);
		}
	}

}
