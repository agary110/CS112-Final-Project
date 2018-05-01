import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.LinkedList;
import java.lang.String;

public class World{
	static double HEIGHT;
	static double WIDTH;
	static Marble marble;
	static Map map;
	static Item item;
	static Ammo ammo;
	static LinkedList<LinkedList<Path>> mapsOnScreen;
	static double timeUntilNextItem;
	static double points;
	static int ammoCount;
	static boolean ammoReleased;
	static LinkedList<AmmoReleased> ammoActive;
	static Random rand;
	static boolean bumpersOn;

	//static Node itemsActive;
//=======================================
//Constructor
	public World(double initHeight, double initWidth){
		HEIGHT = initHeight;
		WIDTH = initWidth;
		marble = new Marble();
		timeUntilNextItem = 0;
		item = new Item(Game.WIDTH / 2, Game.HEIGHT / 2);
		ammoCount = 0;
		points = 0;
		ammoReleased = false;
		rand = new Random();
		map = new Map();
		mapsOnScreen = new LinkedList<LinkedList<Path>>();
		mapsOnScreen.add(Map.upcomingPaths);
		ammoActive = new LinkedList<AmmoReleased>();
		bumpersOn = false;

		//itemsActive = new Node();
	}
//=======================================
//Draw Methods
	public void drawToScreen(Graphics g){
		drawPath(g);
		drawItem(g);
		drawMarble(g);
		drawPoints(g);
		drawAmmoCount(g);
		drawAmmoReleased(g);
		drawHelpMenu(g);
	}

	public void drawMarble(Graphics g){
		marble.draw(g);
	}

	public void drawItem(Graphics g){
		if(item.drawn){
			item.draw(g);
		}

		/*while(itemsActive.isNull == false){
			itemsActive.item.draw(g);
			itemsActive = itemsActive.previous;
		}*/
	}

	public void drawPath(Graphics g){
		map.draw(g);
	}

	public void drawPoints(Graphics g){
		g.setColor(Color.WHITE);
		g.drawString("Points:", 5, 96);
		g.fillRect(3, 108, Ammo.counterWidth, Ammo.counterHeight);
		g.setColor(Color.BLACK);
		g.drawRect(2, 107, Ammo.counterWidth + 2, Ammo.counterHeight + 2);
		g.setColor(Color.WHITE);
		g.drawRect(1, 106, Ammo.counterWidth + 3, Ammo.counterHeight + 3);
		g.setColor(Color.BLACK);
		String stringPoints = String.valueOf((int)(points));
		g.drawString(stringPoints, 15, 133);
	}

   public void drawAmmoCount(Graphics g){
		Ammo.drawAmmoCounter(g);
   }

	public void drawAmmoReleased(Graphics g){
		for(int i = 0; i < ammoActive.size(); i++){
			ammoActive.get(i).draw(g);
		}
	}

	public void drawHelpMenu(Graphics g){
		if(Game.paused){
			g.setColor(Color.WHITE);
			g.fillRect(Game.WIDTH / 4, Game.HEIGHT / 4, Game.WIDTH / 2, Game.HEIGHT / 2);
			g.setColor(Color.BLACK);
			g.drawRect(Game.WIDTH / 4 + 2, Game.HEIGHT / 4 + 2, Game.WIDTH / 2 - 4, Game.HEIGHT / 2 - 4);
		}
	}

//=======================================
//Update Methods
	public void updateMarble(double time){
		marble.update(time);
		if (!bumpersOn){
			//marble.checkForBumpers(this);
		//} else {
			marble.checkDead(this);
		}
		
	}

	private void updateItem(){
		if(timeUntilNextItem <= 0){
			item = Item.generateNextItem(rand.nextInt(7));
			timeUntilNextItem = rand.nextInt(4) + 2;
		}

		item.update();

		/*if(timeUntilNextItem <= 0){
			itemsActive = itemsActive.append(Item.generateNextItem(rand.nextInt(7)));
			timeUntilNextItem = rand.nextInt(4) + 2;
		}

		Node n = itemsActive;
		while(n.isNull == false){
			n.item.update();
			n = n.previous;
			if(n.prevNull){
				break;
			}
		}*/
	}

	private void updateMap(double time){
		map.update(time);
	}

	private void updatePoints(){
		if(Game.hasGameStarted){
			points += 1 / (double)(Game.FPS);
		}
	}

	private void updateAmmoReleased(){
		if(ammoReleased){
			for(int i = 0; i < ammoActive.size(); i++){
				ammoActive.get(i).update();
			}
		}
	}
//=======================================
//Updates Frame and values that change by frame
	public void nextFrame(double time){
		updateMarble(time);
		updateItem();
		updateMap(time);
		updatePoints();
		updateAmmoReleased();
	}
//=======================================
// When the key (char c) is pressed, the marble will start moving in that direction. The more times you press the key, the faster the marble will go in that direction.

    public void moveMarble(){
		if (Game.ipressed) {
	    	marble.moveUp();
		}
		if (Game.jpressed) {
		    marble.moveLeft();
		}
		if (Game.kpressed) {
		    marble.moveDown();
		}
		if (Game.lpressed) {
		    marble.moveRight();
		}
		if (bumpersOn)
			marble.checkForBumpers(this);
	}
//=======================================
//When the key “a” is pressed and ammoCount > 0, the marble shoots ammo.

	public void shootAmmo(char c){
		if(ammoCount > 0){
			ammoReleased = true;
			AmmoReleased.activate(c);
		}
		
	}
//=======================================
//When the key “h” is pressed, the help menu pops up on the screen.

	public void helpMenu(boolean paused, Graphics g){
		if(paused){
			
		}
	}

}
