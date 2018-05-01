import java.awt.Graphics;
import java.awt.Color;

public class Trapezoid implements pathType{
	final int height = Game.HEIGHT / 2;
	int [] [] triangle1;
	int [] [] triangle2;

	public Trapezoid(){
		triangle1 = new int [2][3];
		triangle1 [0][0] = 0;
		triangle1 [0][1] = Game.WIDTH / 2 - Path.WIDTH / 2;
		triangle1 [0][2] = triangle1 [1][0];
		triangle1 [1][0] = Game.HEIGHT;
		triangle1 [1][1] = Game.HEIGHT;
		triangle1 [1][2] = Game.HEIGHT / 2;

		triangle2 = new int [3][2];
		triangle2 [0][0] = Game.WIDTH;
		triangle2 [0][1] = triangle1 [1][0] + Path.WIDTH;
		triangle2 [0][2] = triangle2 [1][0];
		triangle2 [1][0] = Game.HEIGHT;
		triangle2 [1][1] = Game.HEIGHT;
		triangle2 [1][2] = Game.HEIGHT / 2;
		
	}

	public void draw(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.fillPolygon(triangle1[0], triangle1[1], triangle1[0].length);
		g.fillPolygon(triangle2[0], triangle2[1], triangle2[0].length);
		g.fillRect(triangle1[0][2], triangle1[1][2], Path.WIDTH, Game.HEIGHT / 2);
	}

	public void update(){
		for(int i = 0; i < triangle1.length; i++){
			for(int j = 0; i < triangle1[0].length; j++){
				triangle1[i][j]++;
				triangle2[i][j]++;
			}
		}
	}
}
