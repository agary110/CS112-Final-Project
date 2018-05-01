import java.awt.Graphics;
import java.awt.Color;

public class Trapezoid extends Path{
	static final int HEIGHT = Game.HEIGHT / 2;
	int [] [] triangle1;
	int [] [] triangle2;

	public Trapezoid(int y){
		super(0);
		name = "Trapezoid";
		x = Game.WIDTH / 2 - Path.WIDTH / 2;
		this.y = y;

		triangle1 = new int [2][3];
		triangle1 [0][0] = 0;
		triangle1 [0][1] = x;
		triangle1 [0][2] = x;
		triangle1 [1][0] = y + Game.HEIGHT / 2;
		triangle1 [1][1] = y + Game.HEIGHT / 2;
		triangle1 [1][2] = y;

		triangle2 = new int [2][3];
		triangle2 [0][0] = Game.WIDTH;
		triangle2 [0][1] = triangle1 [0][1] + Path.WIDTH;
		triangle2 [0][2] = triangle1 [0][1] + Path.WIDTH;
		triangle2 [1][0] = y + HEIGHT;
		triangle2 [1][1] = y + HEIGHT;
		triangle2 [1][2] = y;
		
	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillPolygon(triangle1[0], triangle1[1], triangle1[0].length);
		g.fillPolygon(triangle2[0], triangle2[1], triangle2[0].length);
		g.fillRect(triangle1[0][2], triangle1[1][2], Path.WIDTH, Game.HEIGHT / 2);
	}

	public void update(){
		for(int i = 0; i < triangle1[1].length; i++){
			System.out.println("updating");
			triangle1[1][i]++;
			triangle2[1][i]++;
		}
	}
}
