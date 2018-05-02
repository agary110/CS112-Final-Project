import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.LinkedList;
import java.lang.String;

public class World{
	static Marble marble;
	static int ammoCount;
	static double points;
	static boolean ammoReleased;
	static Map map;
	static LinkedList<LinkedList<Path>> visibleScreens;
	static LinkedList<AmmoReleased> ammoActive;
	static boolean bumpersOn;
	static Random rand;
	static LinkedList<Item> itemsActive;

//=======================================
//Constructor
	public World(){
		marble = new Marble();
		ammoCount = 10;
		points = 0;
		ammoReleased = false;
		map = new Map();
		visibleScreens = new LinkedList<LinkedList<Path>>();
		visibleScreens.add(Map.initialScreens);
		ammoActive = new LinkedList<AmmoReleased>();
		bumpersOn = false;
		rand = new Random();	
		itemsActive = new LinkedList<Item>();
		itemsActive.add(Item.generateNextItem(rand.nextInt(6) + 1));
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
		for(int i = 0; i < itemsActive.size(); i++){
			if(itemsActive.get(i).drawn){
				itemsActive.get(i).draw(g);
			}
		}
	}

	public void drawPath(Graphics g){ // should change this to drawMap
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
		if(Game.helpDrawn){

			//Draws Container
			g.setColor(Color.WHITE);
			g.fillRect(Game.WIDTH / 4, Game.HEIGHT / 4, Game.WIDTH / 2, Game.HEIGHT / 2);
			g.setColor(Color.BLACK);
			g.drawRect(Game.WIDTH / 4 + 2, Game.HEIGHT / 4 + 2, Game.WIDTH / 2 - 4, Game.HEIGHT / 2 - 4);

			//Draws General instructions
			g.drawString("Help Menu", Game.WIDTH / 2 - 25, Game.HEIGHT / 4 + 20);
			g.drawString("Objective: Navigate the marble through the maze. Be careful not to fall off the edge! There are some ", Game.WIDTH / 4 + 20, Game.HEIGHT / 4 + 40);
			g.drawString("helpful boosters along the way. For example, pick up coins to increase your points and pick up ammo ", Game.WIDTH / 4 + 20, Game.HEIGHT / 4 + 60);
			g.drawString("to store for later. You will find some mystery boosters along the way as well. These include speed ", Game.WIDTH / 4 + 20, Game.HEIGHT / 4 + 80);
			g.drawString("boosters, size boosters, and bumpers. But watch out! There are also some bombs to avoid and aliens ", Game.WIDTH / 4 + 20, Game.HEIGHT / 4 + 100);
			g.drawString("to kill (10 bonus points for each alien hit).", Game.WIDTH / 4 + 20, Game.HEIGHT / 4 + 120);
			g.drawString("Good luck!", Game.WIDTH / 2 - 25, Game.HEIGHT / 4 + 140);

			//Draws Coin
			Coin coin = new Coin(Game.WIDTH / 4 + 175, Game.HEIGHT / 2);
			coin.draw(g);
			g.setColor(Color.BLACK);
			g.drawString("Coin", Game.WIDTH / 4 + 170, Game.HEIGHT / 2 + coin.width + 20);

			//Draws Ammo
			Ammo ammo = new Ammo(Game.WIDTH / 4 + 225, Game.HEIGHT / 2);
			ammo.draw(g);
			g.setColor(Color.BLACK);
			g.drawString("Ammo", Game.WIDTH / 4 + 220, Game.HEIGHT / 2 + ammo.width + 20);

			//Draws Mystery Booster
			Booster booster = new Booster(Game.WIDTH / 4 + 305, Game.HEIGHT / 2);
			booster.draw(g);
			g.setColor(Color.BLACK);
			g.drawString("Mystery Booster", Game.WIDTH / 4 + 270, Game.HEIGHT / 2 + booster.width + 20);

			//Draws Bomb
			Bomb bomb = new Bomb(Game.WIDTH / 4 + 395, Game.HEIGHT / 2);
			bomb.draw(g);
			g.setColor(Color.BLACK);
			g.drawString("Bomb", Game.WIDTH / 4 + 390, Game.HEIGHT / 2 + bomb.width + 20);

			//Draws Alien
			Alien alien = new Alien(Game.WIDTH / 4 + 450, Game.HEIGHT / 2);
			alien.draw(g);
			g.setColor(Color.BLACK);
			g.drawString("Alien", Game.WIDTH / 4 + 445, Game.HEIGHT / 2 + alien.width + 20);

			//Draws Keys
			g.drawString("'i' = up		'h' = help menu", Game.WIDTH / 4 + 270, Game.HEIGHT / 2 + 70);
			g.drawString("'k' = down		'a' = shoot ammo", Game.WIDTH / 4 + 270, Game.HEIGHT / 2 + 90);
			g.drawString("'l' = right		'enter' = restart", Game.WIDTH / 4 + 270, Game.HEIGHT / 2 + 110);
			g.drawString("'j' = left		'esc' = quit", Game.WIDTH / 4 + 270, Game.HEIGHT / 2 + 130);


			//Draws High Score and Start/Resume Instructions
			g.setColor(Color.RED);
			g.drawString("The high score to beat is " + (int)(Game.currentHighScore), Game.WIDTH / 4 + 270, Game.HEIGHT / 2 + 150);
			g.setColor(Color.BLACK);
			g.drawString("Press 'h' to start/resume game.", Game.WIDTH / 4 + 270, Game.HEIGHT / 2 + 170);

			Game.paused = true;
		}
	}

//=======================================
//Update Methods
	private void updateMarble(){
		marble.update();
		if (!bumpersOn){
			marble.checkDead(this);
		}
	}

	private void updateItem(){
		if(itemsActive.getLast().timeUntilNextItem <= 0){
			itemsActive.add(Item.generateNextItem(rand.nextInt(7)));
		}

		for(int i = 0; i < itemsActive.size(); i++){
			itemsActive.get(i).update();
		}
	}

	private void updateMap(){
		map.update();
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

	private void updateTriggerEvents(){
		if((int)(points) % 25 == 0 && (int)(points) != 0){
			TriggerEvent trigEvent = new TriggerEvent(rand.nextInt(3));
			points++;
		}
	}

//=======================================
//Updates Frame and values that change by frame
	public void nextFrame(double time){
		updateMarble();
		updateItem();
		updateMap();
		updatePoints();
		updateAmmoReleased();
		updateTriggerEvents();
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
		if (bumpersOn){
			marble.checkForBumpers(this);
		}
	}
//=======================================
//When the key “a” is pressed and ammoCount > 0, the marble shoots ammo.

	public void shootAmmo(){
		if(ammoCount > 0){
			ammoReleased = true;
			AmmoReleased.activate();
		}
		
	}

}
