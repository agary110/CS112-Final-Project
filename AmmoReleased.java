import java.awt.Graphics;
import java.awt.Color;

public class AmmoReleased{

	int x;
	int y;
	static final int width = 2;
	static final int length = 4;

	public AmmoReleased(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, length);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, length);
	}

	public static void update(){
		for(int i = 0; i < World.ammoActive.size(); i++){
			World.ammoActive.get(i).y--;
		}
		if(World.ammoActive.size() == 0){
			World.ammoReleased = false;
		}
	}

	public static void activate(){
		World.ammoActive.add(new AmmoReleased((int)(World.marble.position.x) + World.marble.radius / 2, (int)(World.marble.position.y)));
		World.ammoCount--;
	}

	public static void deactivate(){
		for(int i = 0; i < World.ammoActive.size(); i++){
			if(World.ammoActive.get(i).y + length < 0){
				World.ammoActive.remove(i);
				break;
			}
		}
	}

}
