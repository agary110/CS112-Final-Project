import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

//=======================================
//Class Item (includes anything that the marble might encounter on the path)

class Item{
	public int x;
	public int y;
	public final int width = (int)(path.WIDTH / 3 - 20);
	public boolean activated;
	public boolean passed;

	public Item(int x, int y){
		this.x = x;
		this.y = y;
		passed = false;
	}

	public void update(){
		if(marble.velocity.y == 0);
		else{
			this.y = this.y + speed;
		}
		if(marble.position.y == y){
			this.passed = true;
		}
		if(this.x + this.width / 2 >= marble.position.x && this.x <= marble.position.x + this.width / 2){
			activated = true;
			this.activate();
		}
		if(this.passed == true && this.activated == false) timeUntilNextItem = timeUntilNextItem - (1 / (double)(FPS));
		if(timeUntilNextItem == 0){
			world.item = generateNewItem();
		}
	}

	public static Item generateNextItem(){
		Path [] visiblePaths = getVisiblePaths();
		int randNum = rand.nextInt(5);
		int x = visiblePaths[visiblePaths.length - 1].x + visiblePaths[visiblePaths.length - 1].width / 2 - width / 2;
		int y = 0 - width;

		//Bomb
		if(randNum == 0){
			Bomb bomb = new Bomb(x, y);
		}

		//Ammo
		else if(randNum == 1){
			Ammo ammo = new Ammo(x, y);
		}

		//Bumpers
		else if(randNum == 2){
			Bumpers bumpers = new Bumpers(x, y);
		}

		//ChangeSpeed
		else if(randNum == 3){
			ChangeSpeed changeSpeed = new ChangeSpeed(x, y);
		}

		//ChangeSize
		else{
			ChangeSize changeSize = new ChangeSize(x, y);
		}
	}

	public void draw(Graphics g){
	}

	public void activate(){
	}
}

//=======================================
//Class Bomb extends Item (if bomb is ever activated, the player automatically loses)

class Bomb extends Item{
	public Bomb(int x, int y){
		super(x, y);
	}

	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillOval(this.x, this.y, width, width);
		g.setColor(Color.WHITE);
		g.drawLine(this.x + width / 2, y, this.x + width, this.y - width / 3);
		g.setColor(Color.ORANGE);
		g.drawOval(this.x + width, this.y - width / 3, 3, 3);
	}

	public void activate(){
		if(this.activated == true) alive = false;
	}
}

//=======================================
//Class Ammo extends Item (increases ammoCount by 1-3)

class Ammo extends Item{
	int increase;

	public Ammo(int x, int y){
		super(x, y);
		Random rand = new Random();
		increase = rand.nextInt(3) + 1;
	}

	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(this.x, this.y, width, width);
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, width, width);
		g.setColor(Color.GREY);
		g.fillRect(this.x + width / 2 - width - 2, this.y + width / 10, width - 10, 5);
	}

	public void activate(){
		ammoCount = ammoCount + increase;
		this.activated = false;
		timeUntilNextItem = originalTimeUntilNextItem;
	}
}

//=======================================
//Class Booster extends Item (includes all time-sensitive Items)

class Booster extends Item{
	public double timeActive;

	public Booster(int x, int y){
		super(x, y);
		activated = false;
		timeActive = 5;
	}

	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(this.x, this.y, width, width);
		g.setColor(Color.RED);
		g.drawRect(this.x, this.y, width, width);
		g.drawString("?", this.x + width / 3, this.y + width / 3);
	}

	public void update(){
		super();
		if(this.activated == true) timeActive = timeActive - (1 / (double)(FPS));
		if(timeActive == 0){
			this.deactivate();
		}
	}

	public void activate(){
	}

	public void deactivate(){
		this.activated = false;
	}
}

//=======================================
//Class Bumpers extends Booster (activates bumpers that prevent the marble from falling off the path)

class Bumpers extends Booster{
	
	public Bumpers(int x, int y){
		super(x, y);
	}

	public void activate(){
	//How to actually turn the bumpers on
	}

	public void deactivate(){
		this.activated = false;
		//How to actually turn the bumpers off
	}
}

//=======================================
//Class ChangeSpeed extends Booster (changes the speed of the path by a constant - positive or negative change is based on a Random)

class ChangeSpeed extends Booster{
	boolean increase;

	public ChangeSpeed(int x, int y){
		super(x, y);
		Random rand = new Random();
		increase = rand.nextBoolean();
	}

	public void activate(){
		if(increase){
			map.speed = map.speed + map.boosterSpeedAlt;
		}
		else{
			map.speed = map.speed - map.boosterSpeedAlt;
		}
	}

	public void deactivate(){
		this.activated = false;
		map.speed = map.originalSpeed;
	}
}

//=======================================
//ChangeSize extends Booster (changes the size of the marble by a constant - positive or negative change is based on a Random)

class ChangeSize extends Booster{
	boolean increase;
	double proportion;

	public ChangeSize(int x, int y){
		super(x, y);
		Random rand = new Random();
		increase = rand.nextBoolean();
		proportion = marble.radius * 0.5;
	}

	public void activate(){
		//How to actually change the size of the marble
	}

	public void deactivate(){
		this.activated = false;
		//How to actually make the size of the marble normal again
	}
}