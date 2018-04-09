//Created a new Item class that might replace all of this

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

class Booster{
	public int x;
	public int y;
	public final int width = 15;
	public double timeActive;
	public boolean activated;

	public Booster(int x, int y){
		this.x = x;
		this.y = y;
		timeActive = 10;
		alive = true;
	}

	public void update(){
		if(marble.velocity.y == 0);
		else{
			this.y = this.y + speed;
		}
		if(marble.position.y == y){
			boolean pickedUp;
			if(this.x + this.width / 2 >= marble.position.x && this.x <= marble.position.x + this.width / 2) pickedUp = true;
			else pickedUp = false;
			this.activate();
		if(this.activated){
			timeActive = timeActive - (1 / (double)(FPS));
			if(timeActive == 0){
				this.deactivate();
				double timeUntilNextBooster = originalTimeUntilNextBooster;
			}
		}
		else if(this.y >= -1 * this.width && this.y <= HEIGHT);
		else timeUntilNextBooster = timeUntilNextBooster - (1 / (double)(FPS));
		if(timeUntilNextBooster = 0) world.booster = generateNextBooster();
	}


	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(this.x, this.y, width, width);
		g.setColor(Color.RED);
		g.drawRect(this.x, this.y, width, width);
		g.drawString("?", this.x + width / 3, this.y + width / 3);
	}
//=======================================
//Returns a random type of Booster (Bumpers, changeSpeed, changeSize, Bomb, or Ammo)
//Doesn't work because Ammo and Bomb aren't boosters
	public static Booster generateNextBooster(){
		Path [] visiblePaths = getVisiblePaths();
		int randNum = rand.nextInt(5);
		int x = visiblePaths[visiblePaths.length - 1].x + visiblePaths[visiblePaths.length - 1].width / 2 - width / 2;
		int y = 0 - width;
		if(randNum == 0){
			Bumpers booster = new Bumpers(x, y);
		}
		else if(randNum == 1){
			changeSpeed booster = new changeSpeed(x, y);
		}
		else if(randNum == 2){
			changeSize booster = new changeSize(x, y);
		}
		else if(randNum == 3){
			Bomb booster = new Bomb(x, y);
		}
		else{
			Ammo booster = new Ammo(x, y);
		}
		return booster;
	}
//=======================================
//Interface that relates to subclasses of Booster that are deactivated after a certain amount of time passes
public interface TimeSensitive{
	public void activate(boolean pickedUp);
	public void deactivate();
	public void draw(Graphics g);
}
//=======================================
//Class Bumpers
	class Bumpers extends Booster implements TimeSensitive{

		public Bumpers(int x, int y){
			super(x, y);
		}

		public void activate(boolean pickedUp){
			if(pickedUp); //Turn on bumpers in the Path class
		}

		public void deactivate(){//Runs when timeActive = 0
			//Turn off bumpers in the Path class
			super.activated = false;
		}

		public void draw(Graphics g){
			super(g);
		}
	}
//=======================================
//Class changeSpeed
	class changeSpeed extends Booster implements TimeSensitive{
		boolean increase;

		public changeSpeed(int x, int y){
			super(x, y);
			boolean rand = new Random();
			increase = rand.nextBoolean();
		}

		public void activate(boolean pickedUp){
			if(pickedUp) map.changeSpeed(increase);
		}

		public void deactivate(){//Runs when timeActive = 0
			map.changeSpeed();
			super.activated = false;
		}

		public void draw(Graphics g){
			super(g);
		}
	}
//=======================================
//Class changeSize
	class changeSize extends Booster implements TimeSensitive{
		boolean increase;
		double proportion;

		public changeSize(int x, int y){
			super(x, y);
			boolean rand = new Random();
			increase = rand.nextBoolean();
			proportion = marble.radius * 0.5;
		}

		public void activate(boolean pickedUp){
			if(pickedUp && increase) marble.radius = marble.radius + proportion;
			else if(pickedUp) marble.radius = marble.radius - proportion;
		}

		public void deactivate(){//Runs when timeActive = 0
			//Revert to original radius. Might have to create another variable in the Marble class that holds the original radius.
			super.activated = false;
		}

		public void draw(Graphics g){
			super(g);
		}
	}
//=======================================
//Class Bomb
	class Bomb{
		int x;
		int y;

		public Bomb(int x, int y){
			this.x = x;
			this.y = y;
		}

		public void activate(boolean pickedUp){
			if(pickedUp) alive = false;
		}

		public void draw(Graphics g){
			g.setColor(Color.BLACK);
			g.fillOval(this.x, this.y, width, width);
			g.setColor(Color.WHITE);
			g.drawLine(this.x + width / 2, y, this.x + width, this.y - width / 3);
			g.setColor(Color.ORANGE);
			g.drawOval(this.x + width, this.y - width / 3, 3, 3);
		}
	}
//=======================================
//Class Ammo
	class Ammo{
		int x;
		int y;
		int increase;

		public Ammo(int x, int y){
			this.x = x;
			this.y = y;
			Random rand = new Random();
			increase = rand.nextInt(5);
		}

		public void activate(boolean pickedUp){
			if(pickedUp); //increase ammo count by the variable increase
		}
	}
}