//=======================================
/** Importing necessary libraries **/
//=======================================

import java.awt.Graphics;
import java.awt.Color;

//==================================================================================
/** An instance of the AmmoReleased class is appended to World.ammoActive whenever ‘a’ is pressed and World.ammoCount > 0. Instances of AmmoReleased are not to be confused with instances of Ammo (a subclass of Item), which only represent items that randomly appear on the path, not objects that are shoot using KeyEvents. **/
//==================================================================================

public class AmmoReleased{

//=======================================
/** Member Variables **/
//=======================================

	int x;
	int y;
	int direction; //[1] up; [2] right; [3] down; [4] left;
	int width;
	int length;

//=======================================
/** Constructor **/
//=======================================

	public AmmoReleased(int x, int y, int direction){
		this.x = x;
		this.y = y;
		this.direction = direction;
		if(direction == 1 || direction == 3){
			width = 2;
			length = 8;
		}
		else{
			width = 8;
			length = 2;
		}
	}

//=======================================
/** Method: draw(Graphics g)
	Functionality: Loops through World.ammoActive and draws each instance of AmmoReleased **/
//=======================================

	public void draw(Graphics g){
		for(int i = 0; i < World.ammoActive.size(); i++){
			g.setColor(Color.GRAY);
			g.fillRect(World.ammoActive.get(i).x, World.ammoActive.get(i).y, World.ammoActive.get(i).width, World.ammoActive.get(i).length);
			g.setColor(Color.BLACK);
			g.drawRect(World.ammoActive.get(i).x, World.ammoActive.get(i).y, World.ammoActive.get(i).width, World.ammoActive.get(i).length);
		}
	}

//=======================================
/** Method: update()
	Functionality: Sets World.ammoReleased to false if all instances of AmmoReleased have been deactivated; increments or decrements the x or y values depending on the value of direction **/
//=======================================

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

//=======================================
/** Method: activate()
	Functionality: Adds a new instance of AmmoReleased to World.ammoActive and decrements World.ammoCount by 1 **/
//=======================================

	public static void activate(){
		int direction = 1;
		if(Game.lpressed){
			direction = 2;
		}
		else if(Game.kpressed){
			direction = 3;
		}
		else if(Game.jpressed){
			direction = 4;
		}

		AmmoReleased toAppend = new AmmoReleased((int)(World.marble.position.x) + World.marble.diameter / 2, (int)(World.marble.position.y), direction);
		World.ammoActive.add(toAppend);
		World.ammoCount--;
	}

//=======================================
/** Method: deactivate()
	Functionality: Removes the first element in World.ammoActive **/
//=======================================

	public static void deactivate(){
		World.ammoActive.remove();
	}

//=======================================
/** Method: deactivate(int index)
	Functionality: Removes the element based on the specified index from World.ammoActive **/
//=======================================

	public static void deactivate(int index){
		World.ammoActive.remove(index);
	}

}

/** END OF AMMORELEASED CLASS **/
//=======================================