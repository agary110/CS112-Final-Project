//Need to figure out update and draw methods for Ammo and Bomb (not subclasses of Booster because they don’t have a time limit quality)

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
//Class Bumpers extends Booster
	class Bumpers extends Booster{//needs to be able to keep track of time to “wear off” after given time

		public Bumpers(int x, int y){
			super(x, y);
		}

		public void activate(boolean pickedUp){
			if(pickedUp); //Turn on bumpers in the Path class
		}

		public void deactivate(){//Runs when timeActive = 0
			//Turn off bumpers in the Path class
		}
	}
//=======================================
//Class changeSpeed extends Booster
	class changeSpeed extends Booster{//needs to be able to keep track of time to “wear off” after given time
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
		}
	}
//=======================================
//Class changeSize extends Booster
	class changeSize extends Booster{//needs to be able to keep track of time to “wear off” after given time
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