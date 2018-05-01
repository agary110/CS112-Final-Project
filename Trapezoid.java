import java.awt.Graphics;
import java.awt.Color;

public class Trapezoid extends Path{
	static final int HEIGHT = Game.HEIGHT / 2;
	int [] [] coordinates;

	public Trapezoid(int y){
		super(0);
		name = "Trapezoid";
		x = Game.WIDTH / 2 - Path.WIDTH / 2;
		this.y = y;

		coordinates = new int [2] [4];
		coordinates [0][0] = exitX;
		coordinates [0][1] = Game.WIDTH / 2 - Path.WIDTH / 2;
		coordinates [0][2] = Game.WIDTH / 2 + Path.WIDTH / 2;
		coordinates [0][3] = Game.WIDTH;
		coordinates [1][0] = Game.HEIGHT;
		coordinates [1][1] = y;
		coordinates [1][2] = y;
		coordinates [1][3] = Game.HEIGHT;

		/*triangle1 = new int [2][3];
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
		triangle2 [1][2] = y;*/
		
	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillPolygon(coordinates[0], coordinates[1], coordinates[0].length);
		/*g.fillPolygon(triangle2[0], triangle2[1], triangle2[0].length);
		g.fillRect(triangle1[0][2], triangle1[1][2], Path.WIDTH, Game.HEIGHT / 2);*/
	}

	public void update(){
		for(int i = 0; i < coordinates[1].length; i++){
			coordinates[1][i]++;
System.out.println("trap y: " + coordinates[1][1]);
			//triangle2[1][i]++;
		}
	}
}
