import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.lang.Character;

//=======================================
//Class Item (includes anything that the marble might encounter on the path)

class Item{
	public int x;
	public int y;
	public static final int width = (int)(Path.WIDTH / 3 - 20);
	public boolean activated;
	public boolean passed;
	static Random rand;

	public Item(int x, int y){
		this.x = x;
		this.y = y;
		passed = false;
		rand = new Random();
	}

	public void update(){
		this.y++;
		if(World.marble.position.y == y){
			this.passed = true;
		}
		if(this.x + this.width / 2 >= World.marble.position.x && this.x <= World.marble.position.x + this.width / 2){
			activated = true;
			this.activate();
		}
		if(this.passed == true && this.activated == false){
			World.timeUntilNextItem -= 1 / (double)(Game.FPS);
		}
		if(World.timeUntilNextItem == 0){
			World.item = generateNextItem(rand.nextInt(6));
		}
		if(World.ammoReleased){
			AmmoReleased.update();
			AmmoReleased.deactivate();	
		}
	}

	public static Item generateNextItem(int randNum){
		Path [] visiblePaths = Map.getVisiblePaths();
		int x = visiblePaths[visiblePaths.length - 1].x + visiblePaths[visiblePaths.length - 1].WIDTH / 2 - width / 2;
		int y = 0 - width;

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
		if(this.activated == true){
			Game.alive = false;
		}
	}
}

//=======================================
//Class Ammo extends Item (increases ammoCount by 1-3)
//This version does not yet kill any Aliens that it meets in the Path.
//Maybe think about having a separate class for Ammo that are being released (?)

class Ammo extends Item{
	int increase;
	static final int counterHeight = 15;
	static final int counterWidth = 48;
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
		g.fillRect(this.x + width / 2 - width - 2, this.y + width / 10, width - 10, 5);

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
	}

	public static void drawAmmoCounter(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(3, 3, counterHeight, counterWidth);
		g.setColor(Color.BLACK);
		g.drawRect(2, 2, counterHeight + 2, counterWidth + 2);
		g.setColor(Color.WHITE);
		g.drawRect(1, 1, counterHeight + 3, counterWidth + 3);
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
		for(int j = 4; j > 0; j--){
			if(dataIndex < j){
				data [data.length - j] = '0';
			}
		}
		if(dataIndex == 4){
			data [0] = toString(World.ammoCount%1000);
			dataIndex--;
		}
		if(dataIndex == 3){
			data [1] = toString(World.ammoCount%100);
			dataIndex--;
		}
		if(dataIndex == 2){
			data [2] = toString(World.ammoCount%10);
			dataIndex--;
		}
		if(dataIndex == 1){
			data [3] = forDigit(World.ammoCount, 10);
			//or maybe… data [3] = World.ammoCount.charValue();
		}

		//Draws data
		drawChars(data, offset, numberLength, 4, 4);

	}
}

//=======================================
//Class AmmoReleased extends Item

class AmmoReleased extends Item{
	static final int width = 2;
	static final int length = 4;

	public AmmoReleased(int x, int y){
		super(x, y);
	}

	public void draw(Graphics g){
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, length);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, length);
	}

	public static void update(){
		super();
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
		if(World.ammoReleased){
			this.deactivate();
		}
	}

	/*pubic void activate(){				//Receiving an <identifier> expected error message (???)
		if(this.activated && deadly){
			Game.alive = false;
		}
	}*/

	public void deactivate(){
		//When hit with ammo, Alien dies
		for(int i = 0; i < World.ammoActive.size(); i++){
			if(World.ammoActive.get(i).x < this.x + width && World.ammoActive.get(i).x > this.x && World.ammoActive.get(i).y < this.y + width){
				deadly = false;
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
		super();
		if(this.activated == true) timeActive = timeActive - (1 / (double)(Game.FPS));
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
		increase = rand.nextBoolean();
	}

	public void activate(){
		//How to actually change the speed
	}

	public void deactivate(){
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
		//How to actually change the size of the marble
	}

	public void deactivate(){
		this.activated = false;
		//How to actually make the size of the marble normal again
	}
}