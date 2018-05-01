import java.awt.Graphics;
import java.awt.Color;
/*import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Dimension;
import java.awt.RenderingHints;*/

public class BigRect extends Path{
	static final int WIDTH = Game.WIDTH;
	static final int HEIGHT = Game.HEIGHT / 2;

	public BigRect(int y){
		super(0);
		name = "BigRect";
		x = Game.WIDTH / 2 - Path.WIDTH / 2;
		this.y = y;
	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(enterX + WIDTH/4, y, WIDTH/2, HEIGHT*2);

	}
}
