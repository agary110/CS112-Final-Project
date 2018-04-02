import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Random;

public class Game extends JPanel{
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 750;
	public static final int FPS = 60;
	World world;

	public static void main(String [] args){
		JFrame frame = new JFrame(“aMAZE-ing Maze”);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game mainInstance = new Game();
		frame.setContentPane(mainInstance);
		frame.pack();
		frame.setVisible(true);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(G);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//INSERT: world.METHOD TO DRAW SHIT
	}
}
