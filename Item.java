//================================
/** Import necessary libraries **/
//================================
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.lang.String;
import java.lang.StringBuilder;


//=======================================
/** Class Item includes anything that the marble might encounter on the path — aliens, bombs, speed changers or size changers, etc. Item is a parent class which never actually gets instantiated, but its many subclasses do. **/


class Item{

//======================
/** Member variables **/
//======================
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

//======================
/**   Constructor    **/
//======================
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
//======================
/**     Methods      **/
//======================

//==================================================================
/** update():
	Functionality: gets called at every frame. Checks to see if marble is currently over the item's location, or if the marble has passed by it without running over it. Decreases the time until next item. **/
//==================================================================
	public void update(){

		this.y++;

		if(passed){
			if(this.y  - 2 < World.marble.position.y && this.y + this.width + 2 > World.marble.position.y){
				if(this.x + this.width - 2 >= World.marble.position.x){
					if(this.x <= World.marble.position.x + World.marble.diameter - 2){
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

//==================================================================
/** generateNextItem(int randNum):
	Functionality: gets called when it's time for a new item to be spawned. Checks to see if its location would be over a path, and if so spawns a new item at that location. Item subclass is determined randomly by the number passed in. **/
//==================================================================
	public static Item generateNextItem(int randNum){

		int pathX = Game.WIDTH / 2 + Path.WIDTH / 2;
		int pathY = 0;
		for(int i = 0; i < World.visibleScreens.size(); i++){
			for(int j = 0; j < World.visibleScreens.get(i).size(); j++){
				if (World.visibleScreens.get(i).get(j).name == "Horizontal"){
					if(World.visibleScreens.get(i).get(j).y <= 0 && World.visibleScreens.get(i).get(j).y + Path.WIDTH >= 0){
						pathX = World.visibleScreens.get(i).get(j).x;
						pathY = World.visibleScreens.get(i).get(j).y;
						break;
					}
				} else {
					if(World.visibleScreens.get(i).get(j).y <= 0 && World.visibleScreens.get(i).get(j).y + Path.HEIGHT >= 0){
						pathX = World.visibleScreens.get(i).get(j).x;
						pathY = World.visibleScreens.get(i).get(j).y;
						break;
					}
				} 
			}
		}

		Random newRand = new Random();
		int x = pathX + 2 + newRand.nextInt(2) * (Path.WIDTH / 3 - 2);
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

//==================================================================
/** pickUp():
	Functionality: erases the Item from the screen if the marble runs over it. Only gets called in certain marble subclasses. **/
//==================================================================

	public void pickUp(){
		drawn = false;
	}

//==================================================================
/** draw(Graphics g):
	Functionality: draw method, which is called every frame that an Item exists. Gets overridden in each Item subclass. **/
//==================================================================

	public void draw(Graphics g){}

//==================================================================
/** activate():
	Functionality: activate method, which is called when the marble is on top of the Item. Gets overridden in each Item subclass. **/
//==================================================================

	public void activate(){}
}


/** END OF Item CLASS **/
//==============================================

//==============================================
/** Class Bomb is an Item subclass. If bomb is ever activated, the player automatically loses and the game ends. **/

class Bomb extends Item{

//======================
/** Member variables **/
//======================
	boolean exploded;


//======================
/**   Constructor    **/
//======================
	public Bomb(int x, int y){
		super(x, y);
		exploded = false;
	}

//======================
/**     Methods      **/
//======================

//==================================================================
/** draw(Graphics g):
	Functionality: gets called at every frame that a Bomb exists, and overrides Item draw(). Checks if the bomb has been activated—if not, draw a bomb shape, otherwise draw an explosion shape. **/
//==================================================================

	public void draw(Graphics g){

		// Unexploded bomb graphic
		if (!exploded){
			g.setColor(Color.BLACK);
			g.fillOval(this.x, this.y, width, width);
			g.setColor(Color.WHITE);
			g.drawLine(this.x + width / 2, y, this.x + width, this.y - width / 3);
			g.setColor(Color.ORANGE);
			g.drawOval(this.x + width, this.y - width / 3, 3, 3);

		} else {
		// Exploded bomb graphic
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

//==================================================================
/** activate():
	Functionality: activate method, which is called when the marble is on top of the Bomb. Ends the game and changes the Bomb graphics. **/
//==================================================================
	public void activate(){
		if(this.activated){
			Game.alive = false;
			exploded = true;
		}
	}

}

/** END OF Bomb CLASS **/
//==============================================

//==============================================
/** Class Coin is a subclass of Item, which increases your points. **/

class Coin extends Item {

//======================
/** Member variables **/
//======================
	int increase;
	static final int width = Item.width - 2;

//======================
/**   Constructor    **/
//======================
	public Coin(int x, int y){
		super(x, y);
		increase = rand.nextInt(3) + 3;
	}

//======================
/**     Methods      **/
//======================

//==================================================================
/** draw(Graphics g):
	Functionality: gets called at every frame that a Coin exists, and overrides Item draw(). Draws a coin shape. **/
//==================================================================
	public void draw(Graphics g){
		g.setColor(Color.YELLOW.brighter());
		g.fillOval(x, y, width, width);
		g.setColor(Color.ORANGE);
		g.drawOval(x, y, width, width);
		g.fillOval(x + width / 3 - 1, y + width / 3 - 1, width / 2, width / 2);
	}

//==================================================================
/** activate():
	Functionality: activate method, which is called when the marble is on top of the Coin. Increases the user's points by a random integer from 3-6. **/
//==================================================================

	public void activate(){
		World.points += increase;
		this.activated = false;
		this.timeUntilNextItem = 0;
		pickUp();
		deactivate();
	}

//==================================================================
/** deactivate():
	Functionality: deactivate method, which is called when the marble is no longer on top of the Coin. Sets the increase to 0 to stop adding points. **/
//==================================================================

	public void deactivate(){
		increase = 0;
	}
}
/** END OF Coin CLASS **/
//==============================================

//==============================================
/** Class Ammo is a subclass of Item, which increases the user's ammoCount by 1-3. **/

class Ammo extends Item {

//======================
/** Member variables **/
//======================
	int increase;
	static final int counterHeight = 40;
	static final int counterWidth = 60;

//======================
/**   Constructor    **/
//======================
	public Ammo(int x, int y){
		super(x, y);
		increase = rand.nextInt(3) + 1;
	}

//======================
/**     Methods      **/
//======================
	
//==================================================================
/** draw(Graphics g):
	Functionality: gets called at every frame that an Ammo exists, and overrides Item draw(). Draws a box of ammo on the path. **/
//==================================================================
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(this.x, this.y, width, width);
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, width, width);
		g.setColor(Color.GRAY);
		g.fillRect(this.x + width / 2 - 3, this.y + width / 2, width - 10, 5);
	}
//==================================================================
/** activate():
	Functionality: activate method, which is called when the marble is on top of the Ammo. Increases the user's ammoCount by a random integer from 1-3. **/
//==================================================================
	public void activate(){
		World.ammoCount = World.ammoCount + increase;
		this.activated = false;
		this.timeUntilNextItem = 0;
		pickUp();
		deactivate();
	}
//==================================================================
/** deactivate():
	Functionality: deactivate method, which is called when the marble is no longer on top of the Ammo. Sets the increase to 0 to stop adding ammo. **/
//==================================================================

	public void deactivate(){
		increase = 0;
	}

//==================================================================
/** drawAmmoCounter(Graphics g):
	Functionality: gets called at every frame of the game. Draws a box that displays the current ammo count. **/
//==================================================================
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

/** END OF Ammo CLASS **/
//==============================================

//==============================================
/** Class Alien is a subclass of Item, and is an enemy for the marble to fight. If the marble runs into an alien while it's alive, the game is over, but the user can shoot the alien to kill it, which renders it harmless. **/

class Alien extends Item{

//======================
/** Member variables **/
//======================
	boolean deadly;


//======================
/**   Constructor    **/
//======================
	public Alien(int x, int y){
		super(x, y);
		deadly = true;
	}

//======================
/**     Methods      **/
//======================
	
//==================================================================
/** draw(Graphics g):
	Functionality: gets called at every frame that an Alien exists, and overrides Item draw(). Draws a live Alien on the path if the Alien is alive, and draws a dead one if the Alien has been shot by ammo. **/
//==================================================================
	

	public void draw(Graphics g){
		//Body
		g.setColor(Color.GREEN);
		g.fillOval(x, y, width, width);

		//Eyes
		int eyeWidth = width / 3;
		int pupilWidth = eyeWidth / 3 * 2;

		g.setColor(Color.WHITE);
		g.fillOval(x + width / 6, y + width / 6, eyeWidth, eyeWidth);
		g.fillOval(x + width / 2, y + width / 6, eyeWidth, eyeWidth);
		g.setColor(Color.BLACK);
		g.drawOval(x + width / 6, y + width / 6, eyeWidth, eyeWidth);
		g.drawOval(x + width / 2, y + width / 6, eyeWidth, eyeWidth);
		g.fillOval(x + width / 6 + eyeWidth / 8, y + width / 3 + eyeWidth / 8, pupilWidth, pupilWidth);
		g.fillOval(x + width / 2 + eyeWidth / 8, y + width / 3 + eyeWidth / 8, pupilWidth, pupilWidth);

		//Mouth 
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

		// If the alien has been killed, draw a dead alien:
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

//==================================================================
/** update():
	Functionality: gets called at every frame that an Alien exists, and overrides Item update(). From the super, checks to see if the marble is on top of the alien; also checks to see if an AmmoReleased has run over the Alien, which deactivates it. **/
//==================================================================

	public void update(){
		
		// Check if marble running over Alien
		super.update();

		// Check if ammoReleased running over Alien
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

//==================================================================
/** activate():
	Functionality: activate method, which is called when the marble is on top of the Alien. If the Alien is deadly, the game ends; otherwise, nothing happens. **/
//==================================================================

	public void activate(){				
		if(this.activated && deadly){
			Game.alive = false;		
		}
	}

//==================================================================
/** deactivate():
	Functionality: deactivate method, which is called when an AmmoReleased is on top of the Alien. This kills the Alien and renders it harmless, deactivated the AmmoReleased that killed it, and increases the user's score. **/
//==================================================================

	public void deactivate(int ammoActiveIndex){
		deadly = false;
		AmmoReleased.deactivate(ammoActiveIndex);
		World.points += 10;
	}

}

/** END OF Alien CLASS **/
//==============================================

//==============================================
/** Class Booster is a subclass of Item, which has its own subclasses. All Boosters are time-sensitive Items, which means their functionalities deactivate after a given amount of time. Booster is never instantiated, but its subclasses are. **/

class Booster extends Item{

//======================
/** Member variables **/
//======================

	public double deactivateTime;


//======================
/**   Constructor    **/
//======================
	public Booster(int x, int y){
		super(x, y);
		activated = false;
		deactivateTime = 5;
	}

//======================
/**     Methods      **/
//======================

//==================================================================
/** draw(Graphics g):
	Functionality: gets called at every frame that a Booster exists, and overrides Item draw(). Draws a mystery box on the screen. Booster's subclasses do not override this method. **/
//==================================================================

	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(this.x, this.y, width, width);
		g.setColor(Color.GREEN);
		g.drawRect(this.x, this.y, width, width);
		g.drawString("?", this.x + width / 2, this.y + width / 5 * 4);
	}

//==================================================================
/** update():
	Functionality: gets called at every frame that a Booster exists, and overrides Item update(). Uses the super.update() to see if the marble has passed over the Booster, and decreases the time until deactivation with every frame. Erases the Item if it gets picked up/the marble passes over it. **/
//==================================================================

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

//==================================================================
/** activate():
	Functionality: called when the marble passes over the Booster, and makes the changes the Booster specifies. **/
//==================================================================

	public void activate(){
		this.activated = true;
	}

//==================================================================
/** deactivate():
	Functionality: called when the time has run out for the Booster, which resets the settings to normal. **/
//==================================================================
	public void deactivate(){
		this.activated = false;
	}


}

/** END OF Booster CLASS **/
//==============================================

//==============================================
/** Class Bumpers is a subclass of Booster, which activates bumpers that prevent the marble from falling off the path. Bumpers go away after 10 seconds. **/

class Bumpers extends Booster {
	
//======================
/**   Constructor    **/
//======================
	public Bumpers(int x, int y){
		super(x, y);
		deactivateTime = 10;
	}

//======================
/**     Methods      **/
//======================

//==================================================================
/** activate():
	Functionality: called when the marble passes over the Bumpers booster, and renders the marble unable to go off the path. Also tells each Path class to draw red rectangles on its borders. **/
//==================================================================

	public void activate(){
		super.activate();
		World.bumpersOn = true;
	}

//==================================================================
/** deactivate():
	Functionality: called when the Bumpers booster time expires, and allows the marble to go off the path again. Also tells each Path class to stop drawing red rectangles on its borders. **/
//==================================================================
	public void deactivate(){
		super.deactivate();
		World.bumpersOn = false;
	}

//==================================================================
/** update():
	Functionality: gets called at every frame that a Bumpers booster is active. **/
//==================================================================

	public void update(){
		super.update();
		checkTopEdge();
	}

//==================================================================
/** checkTopEdge():
	Functionality: Checks to see if the marble is pushing up against a top horizontal edge of the path, and brings the marble down at the same rate the path is moving down if it is. **/
//==================================================================
	public void checkTopEdge(){
		Path path = Marble.checkPath();
		Pair marb = World.marble.position;

		if(path.name != "Straight"){
			if(path.name == "TopRightCorner" || path.name == "TopLeftCorner" || path.name == "Horizontal"){
				if(marb.y < path.y){
					marb.y++;
				}
			}
			if (path.name == "BottomRightCorner"){
				if (marb.x < path.x && marb.y < path.y + path.WIDTH){
					marb.y++;
				}
			}
			if (path.name == "BottomLeftCorner"){
				if (marb.x > path.x + path.WIDTH && marb.y < path.y + path.WIDTH){
					marb.y++;
				}
			}
		}
	}
}

/** END OF Bumpers CLASS **/
//==============================================

//==============================================
/** Class ChangeSpeed is a subclass of Booster, which changes the speed of the marble by a proportion of the current speed. Whether it is a positive or negative change (speed up or slow down) is based on a Random boolean. **/

class ChangeSpeed extends Booster {

//======================
/** Member variables **/
//======================

	boolean increase;
	double xincrement;
	double yincrement;
	double originalXIncrement;
	double originalYIncrement;


//======================
/**   Constructor    **/
//======================
	public ChangeSpeed(int x, int y){
		super(x, y);
		increase = rand.nextBoolean();
		xincrement = World.marble.XposIncrement * 0.5;
		yincrement = World.marble.YposIncrement * 0.5;

		originalXIncrement = World.marble.XposIncrement;
		originalYIncrement = World.marble.YposIncrement;

		deactivateTime = 7;
	}

//======================
/**     Methods      **/
//======================

//==================================================================
/** activate():
	Functionality: called when the marble passes over the ChangeSpeed booster, and changes the amount by which the marble moves. **/
//==================================================================


	public void activate(){
		super.activate();
		if (increase) {
			// Add to amount marble moves by to speed up
			World.marble.XposIncrement += xincrement;
			World.marble.YposIncrement += yincrement;
		} else {
			// Subtract from amount marble moves by to slow down
			World.marble.XposIncrement -= xincrement;
			World.marble.YposIncrement -= yincrement;
		}
		xincrement = 0;
		yincrement = 0;

	}

//==================================================================
/** deactivate():
	Functionality: called when the ChangeSpeed booster time expires, and return's the marble's speed to normal. **/
//==================================================================

	public void deactivate(){
		super.deactivate();
		World.marble.XposIncrement = originalXIncrement;
		World.marble.YposIncrement = originalYIncrement;
	}

}

/** END OF ChangeSPeed CLASS **/
//==============================================

//==============================================
/** Class ChangeSize is a subclass of Booster, which changes the size of the marble by a proportion of the current size. Whether it is a positive or negative change (get bigger or s smaller) is based on a Random boolean. **/

class ChangeSize extends Booster {

//======================
/** Member variables **/
//======================

	boolean increase;
	double proportion;
	int originalSize;

//======================
/**   Constructor    **/
//======================

	public ChangeSize(int x, int y){
		super(x, y);
		increase = rand.nextBoolean();
		proportion = World.marble.diameter * 0.3;
		originalSize = World.marble.diameter;
		deactivateTime = 7;
	}

//======================
/**     Methods      **/
//======================

//==================================================================
/** activate():
	Functionality: called when the marble passes over the ChangeSize booster, and changes marble's diameter. **/
//==================================================================

	public void activate(){
		super.activate();
		if (increase){
			// Increase marble's size
			World.marble.diameter += proportion;
		} else {
			// Decrease marble's size
			World.marble.diameter -= proportion;
		}
		proportion = 0;
	}
//==================================================================
/** deactivate():
	Functionality: called when the ChangeSize booster time expires, and return's the marble's size to normal. **/
//==================================================================

	public void deactivate(){
		super.deactivate();
		World.marble.diameter = originalSize;		
	}
	
}

/** END OF ChangeSize CLASS **/
//==============================================