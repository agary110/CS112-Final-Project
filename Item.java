import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.lang.String;
import java.lang.StringBuilder;

interface Deactivatable{
	public void deactivate();
}

//=======================================
//Class Item (includes anything that the marble might encounter on the path)

class Item{
	public int x;
	public int y;
	public static int width = (int)(Path.WIDTH / 3 - 10) + 2;
	public static final double originalTimeUntilNextItem = 4;
	public double timeUntilNextItem;
	public boolean activated;
	public boolean deactivated;
	public boolean passed;
	public boolean drawn;
	static Random rand;

//Item Constructor
	public Item(int x, int y){
		this.x = x;
		this.y = y;
		rand = new Random();
		timeUntilNextItem = originalTimeUntilNextItem + rand.nextInt(3);;
		activated = false;
		deactivated = false;
		passed = true;
		drawn = true;
	}

//Item update()
	public void update(){

		this.y++;

		if(passed){
			if(this.y  - 2 < World.marble.position.y && this.y + this.width + 2 > World.marble.position.y){
				if(this.x + this.width - 2 >= World.marble.position.x){
					if(this.x <= World.marble.position.x + World.marble.radius - 2){
						this.activated = true;
						this.activate();
					}
				}
			}
			if(this.y >= World.marble.position.y){
				passed = false;
			}
		}
		else{
			if(this.timeUntilNextItem > 0){
				this.timeUntilNextItem -= 1 / (double)(Game.FPS);
			}
		}

	}

//Item generateNextItem()
	public static Item generateNextItem(int randNum){

		int pathX = Game.WIDTH / 2 + Path.WIDTH / 2;
		int pathY = 0;
		for(int i = 0; i < World.mapsOnScreen.size(); i++){
			for(int j = 0; j < World.mapsOnScreen.get(i).size(); j++){
				if (World.mapsOnScreen.get(i).get(j).name == "Horizontal"){
					if(World.mapsOnScreen.get(i).get(j).y <= 0 && World.mapsOnScreen.get(i).get(j).y + Path.WIDTH >= 0){
						pathX = World.mapsOnScreen.get(i).get(j).x;
						pathY = World.mapsOnScreen.get(i).get(j).y;
						break;
					}
				} else {
					if(World.mapsOnScreen.get(i).get(j).y <= 0 && World.mapsOnScreen.get(i).get(j).y + Path.HEIGHT >= 0){
						pathX = World.mapsOnScreen.get(i).get(j).x;
						pathY = World.mapsOnScreen.get(i).get(j).y;
						break;
					}
				} 
			}
		}

		Random newRand = new Random();
		int x = pathX + 2 + newRand.nextInt(2) * (Path.WIDTH / 3 - 2);		//NullPointerException at “rand.nextInt(2)”
		int y = pathY;
	
		//Bumpers
		if(randNum == 0){
			return new Bumpers(x, y);
		}
		
		//Bomb
		else if(randNum == 1){
			return new Bomb(x, y);
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

		//Coin
		else if(randNum == 5){
			return new Coin(x, y);
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
	boolean exploded;
	public Bomb(int x, int y){
		super(x, y);
		exploded = false;
	}

	public void draw(Graphics g){
		if (!exploded){
			g.setColor(Color.BLACK);
			g.fillOval(this.x, this.y, width, width);
			g.setColor(Color.WHITE);
			g.drawLine(this.x + width / 2, y, this.x + width, this.y - width / 3);
			g.setColor(Color.ORANGE);
			g.drawOval(this.x + width, this.y - width / 3, 3, 3);
		} else {
			int orangenpoints = 12;
			int[] orangexpoints = {this.x - width/5, this.x, this.x - width/3, this.x + width/4, this.x + width/4, this.x + 2* width/3, this.x + width + width/5, this.x + width, this.x + 2*width - width/4, this.x + 2*width/3, this.x + 3*width/4, this.x + width/3};
			int[] orangeypoints = {this.y - width/2, this.y, this.y + width/2, this.y + width/3, this.y + 2*width - width/2, this.y + 2*width/3, this.y + width - width/5, this.y + width/2, this.y, this.y + width/6, this.y - width/4, this.y};
			g.setColor(Color.ORANGE);
			g.fillPolygon(orangexpoints, orangeypoints, orangenpoints);

			int smallw = width/3;
			int smallx = this.x + width/3;
			int smally = this.y + width/3;
			int rednpoints = 12;
			
			int[] redxpoints = {smallx - smallw/5, smallx, smallx - smallw/3, smallx + smallw/4, smallx + smallw/4, smallx + 2* smallw/3, smallx + smallw + smallw/5, smallx + smallw, smallx + 2*smallw - smallw/4, smallx + 2*smallw/3, smallx + 3*smallw/4, smallx + smallw/3};
			int[] redypoints = {smally - smallw/2, smally, smally + smallw/2, smally + smallw/3, smally + 2*smallw - smallw/2, smally + 2*smallw/3, smally + smallw - smallw/5, smally + smallw/2, smally, smally + smallw/6, smally - smallw/4, smally};

			g.setColor(Color.RED);
			g.fillPolygon(redxpoints, redypoints, rednpoints);
		}	
	}

	public void activate(){
		if(this.activated){
			Game.alive = false;
			exploded = true;
			System.out.println("dying because an item has been activated");
		}
	}

}

//=======================================
//Class Coin extends Item (increased points by 3-5)

class Coin extends Item implements Deactivatable{
	int increase;
	static final int width = Item.width - 2;

	public Coin(int x, int y){
		super(x, y);
		increase = rand.nextInt(3) + 3;
	}

	public void draw(Graphics g){
		g.setColor(Color.YELLOW.brighter());
		g.fillOval(x, y, width, width);
		g.setColor(Color.ORANGE);
		g.drawOval(x, y, width, width);
		g.fillOval(x + width / 3 - 1, y + width / 3 - 1, width / 2, width / 2);
	}

	public void activate(){
		World.points += increase;
		this.activated = false;
		this.timeUntilNextItem = 0;
		pickUp();
		deactivate();
	}

	public void deactivate(){
		increase = 0;
	}
}

//=======================================
//Class Ammo extends Item (increases ammoCount by 1-3)

class Ammo extends Item implements Deactivatable{
	int increase;
	static final int counterHeight = 40;
	static final int counterWidth = 60;

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
		g.fillRect(this.x + width / 2 - 3, this.y + width / 2, width - 10, 5);

		/*if(World.ammoReleased && World.ammo.y < -width){
			World.ammo.draw(g);
			World.ammo.y -= 2;
			if(World.ammo.y < -width - 10){
				World.ammoReleased = false;
			}
		}*/
	}

	public void activate(){
		World.ammoCount = World.ammoCount + increase;
		this.activated = false;
		this.timeUntilNextItem = 0;
		pickUp();
		deactivate();
	}

	public void deactivate(){
		increase = 0;
	}

	public static void drawAmmoCounter(Graphics g){
		g.setColor(Color.WHITE);
		g.drawString("Ammo:", 5, 25);

		g.fillRect(3, 33, counterWidth, counterHeight);
		g.setColor(Color.BLACK);
		g.drawRect(2, 32, counterWidth + 2, counterHeight + 2);
		g.setColor(Color.WHITE);
		g.drawRect(1, 31, counterWidth + 3, counterHeight + 3);
		g.setColor(Color.BLACK);

		//Determines how many place values are in ammoCount
		int dataIndex = 0;
		double i = World.ammoCount;
		while(i >= 1){
			dataIndex++;
			i = i / 10;
		}

		StringBuilder stringBuilder = new StringBuilder();
		for(int j = 0; j < dataIndex; j++){
			stringBuilder.append("0");
		}
		stringBuilder.append(World.ammoCount);
		String stringAmmoCount = stringBuilder.toString();

		g.drawString(stringAmmoCount, 15, 58);
	}
}

//=======================================
//Class Alien extends Item

class Alien extends Item{
	boolean deadly;
	final int eyeWidth = width / 3;
	final int pupilWidth = eyeWidth / 3 * 2;
	
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
		g.fillOval(x + width / 6, y + width / 6, eyeWidth, eyeWidth);
		g.fillOval(x + width / 2, y + width / 6, eyeWidth, eyeWidth);
		g.setColor(Color.BLACK);
		g.drawOval(x + width / 6, y + width / 6, eyeWidth, eyeWidth);
		g.drawOval(x + width / 2, y + width / 6, eyeWidth, eyeWidth);
		g.fillOval(x + width / 6 + eyeWidth / 8, y + width / 3 + eyeWidth / 8, pupilWidth, pupilWidth);
		g.fillOval(x + width / 2 + eyeWidth / 8, y + width / 3 + eyeWidth / 8, pupilWidth, pupilWidth);

		//Mouth (unsure about which angles to use; set 45 and 45 as default)
		g.drawArc(x + width / 6, y + width / 4 * 3, width / 2, 3, 45, 45);

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
		g.fillRect(x + width + width / 4, y + width / 2 - width / 8, 2, width / 8);

		//Legs
		g.fillRect(x + width / 3, y + width, 2, width / 2);
		g.fillRect(x + width / 3 * 2, y + width, 2, width / 2);
		g.fillRect(x + width / 6, y + width + width / 2, width / 4, 2);
		g.fillRect(x + width / 3 * 2, y + width + width / 2, width / 4, 2);

		if(deadly == false){
			g.fillOval(x, y, width, width);

			g.setColor(Color.BLACK);
			g.drawString("X", x + width / 2 - eyeWidth - 1, y + width / 2);
			g.drawString("X", x + width / 2 + 1, y + width / 2);
			g.setColor(Color.PINK);
			g.fillOval(x + width / 4 * 3 - 3, y + width / 4 * 3, 4, 4);
			g.setColor(Color.BLACK);
			g.drawLine(x + width / 4, y + width / 4 * 3, x + width / 4 * 3, y + width / 4 * 3);
		}
	}

	public void update(){
		super.update();

		if(deadly){
			if(World.ammoReleased){
				for(int i = 0; i < World.ammoActive.size(); i++){
					if(World.ammoActive.get(i).y >= this.y && World.ammoActive.get(i).y <= this.y + width){
						if(this.x + this.width - 2 >= World.ammoActive.get(i).x){
							if(this.x <= World.ammoActive.get(i).x + World.ammoActive.get(i).width - 2){
								this.deactivate(i);
							}
						}
					}
				}
			}
		}
	}

	public void activate(){				
		if(this.activated && deadly){
			Game.alive = false;
			System.out.println("died bc of alien");
		}
	}

	public void deactivate(int ammoActiveIndex){
		deadly = false;
		AmmoReleased.deactivate(ammoActiveIndex);
		World.points += 10;
	}

}

//=======================================
//Class Booster extends Item (includes all time-sensitive Items)

class Booster extends Item{
	public double deactivateTime;

	public Booster(int x, int y){
		super(x, y);
		activated = false;
		deactivateTime = 5;
	}

	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(this.x, this.y, width, width);
		g.setColor(Color.GREEN);
		g.drawRect(this.x, this.y, width, width);
		g.drawString("?", this.x + width / 2, this.y + width / 5 * 4);
	}

	public void update(){
		super.update();

		if(this.activated){
			this.pickUp();
			this.deactivateTime -= 1 / (double)(Game.FPS);
		}

		if(this.deactivateTime <= 0){
			this.deactivate();
		}
	}

	public void activate(){
		this.activated = true;
	}

	public void deactivate(){
		this.activated = false;
	}
}

//=======================================
//Class Bumpers extends Booster (activates bumpers that prevent the marble from falling off the path)

class Bumpers extends Booster implements Deactivatable{
	double deactivateTime;
	
	public Bumpers(int x, int y){
		super(x, y);
		deactivateTime = 15;
	}

	public void activate(){
		super.activate();
		World.bumpersOn = true;
	}

	public void deactivate(){
		super.deactivate();
		World.bumpersOn = false;
	}

	public void update(){
		/*if(this.activated){
			this.pickUp();
			deactivateTime -= (1 / (double)(Game.FPS));
		}
		if(deactivateTime <= 0){
			this.deactivate();
		}*/
		super.update();
		checkTopEdge();
	}

	public void checkTopEdge(){
		Path path = Marble.checkPath();
		Pair marb = World.marble.position;

		if(path.name != "Straight"){
			if(path.name == "leftCorner" || path.name == "rightCorner" || path.name == "Horizontal"){
				if(marb.y < path.y){
					marb.y++;
				}
			}
			if (path.name == "rightElbow"){
				if (marb.x < path.x && marb.y < path.y + path.WIDTH){
					marb.y++;
				}
			}
			if (path.name == "leftElbow"){
				if (marb.x > path.x + path.WIDTH && marb.y < path.y + path.WIDTH){
					marb.y++;
				}
			}
		}
	}
}

//=======================================
//Class ChangeSpeed extends Booster (changes the speed of the path by a constant - positive or negative change is based on a Random)

class ChangeSpeed extends Booster implements Deactivatable{
	boolean increase;
	double xincrement;
	double yincrement;
	double originalXIncrement;
	double originalYIncrement;

	double originalIncrement;
	double deactivateTime;

	public ChangeSpeed(int x, int y){
		super(x, y);
		increase = rand.nextBoolean();
		xincrement = World.marble.XposIncrement * 0.5;
		yincrement = World.marble.YposIncrement * 0.5;

		originalXIncrement = World.marble.XposIncrement;
		originalYIncrement = World.marble.YposIncrement;

		deactivateTime = 10;
	}

	public void activate(){
		super.activate();
		if (increase) {
			World.marble.XposIncrement += xincrement;
			World.marble.YposIncrement += yincrement;
		} else {
			World.marble.XposIncrement -= xincrement;
			World.marble.YposIncrement -= yincrement;
		}
		xincrement = 0;
		yincrement = 0;

	}

	public void deactivate(){
		super.deactivate();
		World.marble.XposIncrement = originalXIncrement;
		World.marble.YposIncrement = originalYIncrement;
		//How to actually make the speed normal again
	}
	public void update(){
		super.update();
	}
}

//=======================================
//ChangeSize extends Booster (changes the size of the marble by a constant - positive or negative change is based on a Random)


class ChangeSize extends Booster implements Deactivatable{
	boolean increase;
	double proportion;
	int originalSize;
	double deactivateTime;

	public ChangeSize(int x, int y){
		super(x, y);
		increase = rand.nextBoolean();
		proportion = World.marble.radius * 0.3;
		originalSize = World.marble.radius;
		deactivateTime = 10;
	}

	public void activate(){
		super.activate();
		if (increase){
			World.marble.radius += proportion;
		} else {
			World.marble.radius -= proportion;
		}
		proportion = 0;
	}

	public void deactivate(){
		super.deactivate();
		World.marble.radius = originalSize;		
	}
	public void update(){
		super.update();
	}
}