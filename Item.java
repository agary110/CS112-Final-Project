import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.lang.Integer;
import java.lang.String;

//=======================================
//Class Item (includes anything that the marble might encounter on the path)

class Item{
	public int x;
	public int y;
	public static int width = (int)(Path.WIDTH / 3 - 10);
	public boolean activated;
	public boolean deactivated;
	public boolean onScreen;
	public boolean drawn;
	static Random rand;

//Item Constructor
	public Item(int x, int y){
		this.x = x;
		this.y = y;
		activated = false;
		deactivated = false;
		onScreen = true;
		drawn = true;
		rand = new Random();
	}

//Item update()
	public void update(){

		this.y++;

		if(onScreen){
			if(this.y  - 2 < World.marble.position.y && this.y + this.width + 2 > World.marble.position.y){
				if(this.x + this.width - 2 >= World.marble.position.x){
					if(this.x <= World.marble.position.x + World.marble.radius - 2){
						this.activated = true;
						this.activate();
					}
				}
			}
			if(this.y >= Game.HEIGHT){
				onScreen = false;
			}
		}
		else{
			World.timeUntilNextItem -= 1 / (double)(Game.FPS);
		}

	}

//Item generateNextItem()
	public static Item generateNextItem(int randNum){

		int x = Game.WIDTH / 2 + 1;
		int xPlus = rand.nextInt(2);
		x = x + xPlus * (Path.WIDTH / 3 - 1);

		int y = 0 - width;

		randNum = 2;

		//Bomb
		if(randNum == 0){
			return new Bomb(x, y);
		}

		//Bumpers
		else if(randNum == 1){
			return new Bumpers(x, y);
		}

		//ChangeSpeed
		else if(randNum == 2){
			return new ChangeSpeed(x, y);
		}

		//ChangeSize
		else if(randNum == 3){
			return new ChangeSize(x, y);
		}

		//Alien
		else if(randNum == 4){
			return new Alien(x, y);
		}

		//Ammo
		else{
			if(World.ammoCount > 9999){
				generateNextItem(rand.nextInt(5));
			}
		}

		return new Ammo(x, y);
	}

//Item pickUp()
	public void pickUp(){
		drawn = false;
	}

//Item draw(Graphics g)
	public void draw(Graphics g){
	}

//Item activate()
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
		if(this.activated){
			Game.alive = false;
		}
	}

}

//=======================================
//Class Ammo extends Item (increases ammoCount by 1-3)
//Add something that says “Ammo Counter:” next to the counter
//Fix draw(g)

class Ammo extends Item{
	int increase;
	static final int counterHeight = 40;
	static final int counterWidth = 120;
	static final int offset = 2;
	static final int numberLength = counterWidth - 4;

	public Ammo(int x, int y){
		super(x, y);
		increase = rand.nextInt(3) + 1;
	}

	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(this.x, this.y, width, width);
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, width, width);
		g.setColor(Color.GRAY);
		g.fillRect(this.x + width / 2 - 3, this.y + width / 10, width - 10, 5);

		if(World.ammoReleased && World.ammo.y < -width - 10){
			World.ammo.draw(g);
			World.ammo.y -= 2;
			if(World.ammo.y < -width - 10){
				World.ammoReleased = false;
			}
		}
	}

	public void activate(){
		World.ammoCount = World.ammoCount + increase;
		this.activated = false;
		World.timeUntilNextItem = World.originalTimeUntilNextItem;
		pickUp();
	}

	public static void drawAmmoCounter(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(3, 3, counterWidth, counterHeight);
		g.setColor(Color.BLACK);
		g.drawRect(2, 2, counterWidth + 2, counterHeight + 2);
		g.setColor(Color.WHITE);
		g.drawRect(1, 1, counterWidth + 3, counterHeight + 3);
		g.setColor(Color.BLACK);

		//Determines how many place values are in ammoCount
		int dataIndex = 0;
		double i = World.ammoCount;
		while(i >= 1){
			dataIndex++;
			i = i / 10;
		}

		//Fills data with chars that match ammoCount
		char [] data = new char [4];
		String countString = Integer.toString(World.ammoCount);
		for(int j = 4; j > 0; j--){
			if(dataIndex < j){
				data [data.length - j] = '0';
			}
		}
		for(int j = 0; j < dataIndex; j++){
			data [data.length - dataIndex + j] = countString.charAt(j);
		}

//ArrayindexOutofBoundsException when this line is in (“sun.java2d.SunGraphics2D.drawChars(SunGraphics2D.java:3024))
//		g.drawChars(data, offset, numberLength, 4, 4);
	}
}

//=======================================
//Class Alien extends Item

class Alien extends Item{
	boolean deadly;
	final int eyeWidth = 3;
	final int pupilWidth = 1;
	final String string = "X";
	
	public Alien(int x, int y){
		super(x, y);
		deadly = true;
		width += 2;
	}

	public void draw(Graphics g){
		//Body
		g.setColor(Color.GREEN);
		g.fillOval(x, y, width, width);

		//Eyes
		g.setColor(Color.WHITE);
		g.fillOval(x + width / 2 - eyeWidth - 1, y + width / 2 - eyeWidth, eyeWidth, eyeWidth);
		g.fillOval(x + width / 2 + 1, y + width / 2 - eyeWidth, eyeWidth, eyeWidth);
		g.setColor(Color.BLACK);
		g.drawOval(x + width / 2 - eyeWidth - 1, y + width / 2 - eyeWidth, eyeWidth, eyeWidth);
		g.drawOval(x + width / 2 + 1, y + width / 2 - eyeWidth, eyeWidth, eyeWidth);
		g.fillOval(x + width / 2 + 2, y + width / 2 - eyeWidth + 1, pupilWidth, pupilWidth);

		//Mouth (unsure about which angles to use; set 45 and 45 as default)
		g.drawArc(x + width / 4, y + width / 4 * 3, width / 2, 3, 45, 45);

		//Nose
		g.fillOval(x + width / 2, y + width / 2, 1, 1);

		//Antennae
		int [] xPoints = new int [4];
		xPoints [0] = x + width / 3;
		xPoints [1] = xPoints [0] - 1;
		xPoints [2] = x;
		xPoints [3] = x - width / 3;

		int [] yPoints = new int [xPoints.length];
		yPoints [0] = y;
		yPoints [1] = y + 1;
		yPoints [2] = y - width / 5 * 3;
		yPoints [3] = y - width / 3;

		g.fillPolygon(xPoints, yPoints, xPoints.length);

		xPoints [0] = x + width / 3 * 2;
		xPoints [1] = xPoints [0] - 1;
		xPoints [2] = x + width;
		xPoints [3] = x + width * 4 / 3;

		//yPoints stays the same

		g.fillPolygon(xPoints, yPoints, xPoints.length);

		//Arms
		g.setColor(Color.GREEN);
		g.fillRect(x - width / 4, y + width / 2, width / 4, 2);
		g.fillRect(x - width / 4, y + width / 2 - width / 8, 2, width / 8);
		g.fillRect(x + width, y + width / 2, width / 4, 2);
		g.fillRect(x + width, y + width / 2 - width / 8, 2, width / 8);

		//Legs
		g.fillRect(x + width / 3, y + width / 3, 2, width / 2);
		g.fillRect(x + width / 3 * 2, y + width / 3, 2, width / 2);
		g.fillRect(x + width / 6, y + width / 3 + width / 2, width / 4, 2);
		g.fillRect(x + width / 3 * 2, y + width / 3 + width / 2, width / 4, 2);

		if(deadly == false){
			g.fillOval(x + width / 2 - eyeWidth - 1, y + width / 2 - eyeWidth, eyeWidth, eyeWidth);
			g.fillOval(x + width / 2 + 1, y + width / 2 - eyeWidth, eyeWidth, eyeWidth);
			g.fillRect(x + width / 4, y + width / 4 * 3, width / 2, 3);

			g.setColor(Color.BLACK);
			g.drawString(string, x + width / 2 - eyeWidth - 1, y + width / 2 - eyeWidth);
			g.drawString(string, x + width / 2 + 1, y + width / 2 - eyeWidth);
			g.setColor(Color.PINK);
			g.fillOval(x + width / 4 * 3 - 3, y + width / 4 * 3, 3, 3);
			g.setColor(Color.BLACK);
			g.drawLine(x + width / 4, y + width / 4 * 3, x + width / 4 * 3, y + width / 4 * 3);
		}
	}

	public void update(){
		super.update();

		if(World.ammoReleased){
			for(int i = 0; i < World.ammoActiveCount; i++){
				if(World.ammoActiveLast.get(i).y == this.y){
					if(this.x + this.width - 2 >= World.ammoActiveLast.get(i).x){
						if(this.x <= World.ammoActiveLast.get(i).x + AmmoReleased.width - 2){
							this.deactivate();
						}
					}
				}
			}
		}
	}

	public void activate(){				
		if(this.activated && deadly){
			Game.alive = false;
		}
	}

	public void deactivate(){
		//When hit with ammo, Alien dies
		for(int i = 0; i < World.ammoActiveCount; i++){
			if(World.ammoActiveLast.get(i).x < this.x + width && World.ammoActiveLast.get(i).x > this.x && World.ammoActiveLast.get(i).y < this.y + width){
				deadly = false;
				AmmoReleased.deactivate(i);
			}
		}
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
		super.update();

		if(this.activated){
			this.pickUp();
			timeActive -= (1 / (double)(Game.FPS));
		}
		if(timeActive <= 0){
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
		this.activated = true;
		World.bumpersOn = true;
	}

	public void deactivate(){
		World.bumpersOn = false;
		this.activated = false;
		//How to actually turn the bumpers off
		
	}

	public void update(){
		super.update();
		//World.marble.canMove = true;
		//Game.alive = true;
	}
	//NEED: a draw method which will add some color to the edge of the path. It can do this by going through all the paths 
}

//=======================================
//Class ChangeSpeed extends Booster (changes the speed of the path by a constant - positive or negative change is based on a Random)

class ChangeSpeed extends Booster{
	boolean increase;

	public ChangeSpeed(int x, int y){
		super(x, y);
		increase = rand.nextBoolean();
	}

	public void activate(){
		//How to actually change the speed
		//NOTE: when we do this we also have to change the y position increments — do the physics on this
		this.activated = true;

		if (increase) {
			World.marble.speedIncrement += 10;
		} else {
			World.marble.speedIncrement -= 10;
		}
	}

	public void deactivate(){
		if (increase) {
			World.marble.speedIncrement -= 10;
		} else {
			World.marble.speedIncrement += 10;
		}
		this.activated = false;
		//How to actually make the speed normal again
	}
}

//=======================================
//ChangeSize extends Booster (changes the size of the marble by a constant - positive or negative change is based on a Random)

class ChangeSize extends Booster{
	boolean increase;
	double proportion;

	public ChangeSize(int x, int y){
		super(x, y);
		increase = rand.nextBoolean();
		proportion = World.marble.radius * 0.5;
	}

	public void activate(){
		this.activated = true;
		if (increase){
			World.marble.radius += proportion;
		} else {
			World.marble.radius -= proportion;
		}
	}

	public void deactivate(){
		if (increase){
			World.marble.radius -= proportion;
		} else {
			World.marble.radius += proportion;
		}
		this.activated = false;
		//How to actually make the size of the marble normal again
		
	}
}