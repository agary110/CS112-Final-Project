import java.awt.Graphics;
import java.awt.Color;

public class AmmoReleased{

	int x;
	int y;
	int direction; //[1] up; [2] right; [3] down; [4] left;
	static final int width = 2;
	static final int length = 8;

	public AmmoReleased(int x, int y, int direction){
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public void draw(Graphics g){
		for(int i = 0; i < World.ammoActive.size(); i++){
			g.setColor(Color.GRAY);
			g.fillRect(World.ammoActive.get(i).x, World.ammoActive.get(i).y, width, length);
			g.setColor(Color.BLACK);
			g.drawRect(World.ammoActive.get(i).x, World.ammoActive.get(i).y, width, length);
		}
	}

	public void update(){
		if(World.ammoActive.size() == 0){
			World.ammoReleased = false;
		}

		if(direction == 1){
			this.y -= 2;
			if(this.y < -length){
				this.deactivate();
			}
		}

		else if(direction == 2){
			this.x += 4;
			if(this.x > Game.WIDTH){
				this.deactivate();
			}
		}

		else if(direction == 3){
			this.y += 4;
			if(this.y > Game.HEIGHT){
				this.deactivate();
			}
		}

		else if(direction == 4){
			this.x -= 4;
			if(this.x < -length){
				this.deactivate();
			}
		}
	}

	public static void activate(char c){
		int direction = 1;
		if(c == 'l'){
			direction = 2;
		}
		else if(c == 'k'){
			direction = 3;
		}
		else if(c == 'j'){
			direction = 4;
		}

		AmmoReleased toAppend = new AmmoReleased((int)(World.marble.position.x) + World.marble.radius / 2, (int)(World.marble.position.y), direction);
		World.ammoActive.add(toAppend);
		World.ammoCount--;
	}

	public static void deactivate(){
		World.ammoActive.remove();
	}

	public static void deactivate(int index){
		World.ammoActive.remove(index);
	}

}