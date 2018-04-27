import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.lang.Math;

class Map{
	public static LinkedList<Path> upcomingPaths;
	public static LinkedList<Path> Map1;
	public static LinkedList<Path> Map2;
	public static LinkedList<Path> Map3;
	public static List<Path> pathTypes;
	public static LinkedList<LinkedList<Path>> allMaps;
	public static Random rand;
//=======================================
//Constructor
	public Map(){
		rand = new Random();
		upcomingPaths = new LinkedList<Path>();
		Map1 = new LinkedList<Path>();
		Map2 = new LinkedList<Path>();
		Map3 = new LinkedList<Path>();
		allMaps = new LinkedList<LinkedList<Path>>();

		upcomingPaths.add(new Straight(Game.WIDTH / 2 - Path.WIDTH / 2));
		upcomingPaths.get(0).y = Game.HEIGHT;
		for(int i = 1; i < 6; i++){
			upcomingPaths.add(new Straight(upcomingPaths.get(i - 1)));
		}

		prototypePaths1();
		addMaps();
		
	}
//=======================================
//add maps to linked list allMaps
	public void addMaps() {
	allMaps.add(upcomingPaths);
	allMaps.add(Map1);
	allMaps.add(Map2);
	allMaps.add(Map3);
	}
//=======================================
//When marble moves up, the screen path will move down. When the lowest instance of Path on screen is no longer visible, a new Path is generated and inserted at the top of the screen.
	public void update(double time){

		for(int i = 0; i < World.mapsOnScreen.size(); i++){
			for(int j = 0; j<World.mapsOnScreen.get(i).size(); j++){
				World.mapsOnScreen.get(i).get(j).y++;
			
			}
		}

		if(World.mapsOnScreen.get(0).getLast().y >= Game.HEIGHT){
			World.mapsOnScreen.remove(0);
			World.mapsOnScreen.add(generateNext());
		}

	}
//=======================================
//Draws Map using draw methods from subclasses of Path
	public void draw(Graphics g){
/*		for(int i = 0; i < World.mapsOnScreen.size(); i++){
			for (int j=0; j<World.mapsOnScreen.get(i).size(); j++) {
				World.mapsOnScreen.get(i).get(j).draw(g);
			}
		}
*/

	for (int i = 0; i<Map1.size(); i++) {
		Map1.get(i).draw(g);
		System.out.println(Map1.get(i).x + " " + Map1.get(i).y);
		}

	}
//=======================================
//Returns an instance of random subclass of Path

public static void prototypePaths1(){
	//option 1

	Map1.add(new RightCorner(650));
	Map1.add(new RightElbow(Map1.getLast()));
	Map1.add(new Straight(Map1.getLast()));
	Map1.add(new LeftCorner(Map1.getLast()));
	Map1.add(new Horizontal(Map1.getLast(), false));
	Map1.add(new Horizontal(Map1.getLast(), false));
	Map1.add(new LeftElbow(Map1.getLast()));
	Map1.add(new Straight(Map1.getLast()));
	Map1.add(new RightCorner(Map1.getLast()));
	Map1.add(new Horizontal(Map1.getLast(), true));
	Map1.add(new Horizontal(Map1.getLast(), true));
	Map1.add(new RightElbow(Map1.getLast()));
	Map1.add(new LeftCorner(Map1.getLast()));
	Map1.add(new LeftElbow(Map1.getLast()));

//option 2
	Map2.add(new LeftCorner(650));
	Map2.add(new Horizontal(Map2.getLast(), false));
	Map2.add(new LeftElbow(Map2.getLast()));
	Map2.add(new RightCorner(Map2.getLast()));
	Map2.add(new RightElbow(Map2.getLast()));
	Map2.add(new Straight(Map2.getLast()));
	Map2.add(new RightCorner(Map2.getLast()));
	Map2.add(new Horizontal(Map2.getLast(), true));
	Map2.add(new RightElbow(Map2.getLast()));
	Map2.add(new Straight(Map2.getLast()));
	Map2.add(new LeftCorner(Map2.getLast()));
	Map2.add(new LeftElbow(Map2.getLast()));
//option 3
	Map3.add(new RightCorner(650));
	Map3.add(new RightElbow(Map3.getLast()));
	Map3.add(new RightCorner(Map3.getLast()));
	Map3.add(new RightElbow(Map3.getLast()));
	Map3.add(new Straight(Map3.getLast()));
	Map3.add(new Straight(Map3.getLast()));
	Map3.add(new LeftCorner(Map3.getLast()));
	Map3.add(new LeftElbow(Map3.getLast()));
	Map3.add(new Straight(Map3.getLast()));
	Map3.add(new LeftCorner(Map3.getLast()));
	Map3.add(new LeftElbow(Map3.getLast()));
/*
	upcomingPaths.add(new RightElbow(upcomingPaths.getLast()));
	upcomingPaths.add(new Straight(upcomingPaths.getLast()));
	upcomingPaths.add(new Straight(upcomingPaths.getLast()));
	upcomingPaths.add(new LeftCorner(upcomingPaths.getLast()));
	upcomingPaths.add(new Horizontal(upcomingPaths.getLast(), false));
	upcomingPaths.add(new LeftElbow(upcomingPaths.getLast()));
	upcomingPaths.add(new Straight(upcomingPaths.getLast()));
	upcomingPaths.add(new RightCorner(upcomingPaths.getLast()));
	upcomingPaths.add(new RightElbow(upcomingPaths.getLast()));
	upcomingPaths.add(new Straight(upcomingPaths.getLast()));
	upcomingPaths.add(new Straight(upcomingPaths.getLast()));
	upcomingPaths.add(new LeftCorner(upcomingPaths.getLast()));
	upcomingPaths.add(new Horizontal(upcomingPaths.getLast(), false));
*/

}

public static LinkedList<Path> generateNext(){
	int randNum = rand.nextInt(allMaps.size());
	LinkedList<Path> toAppend = allMaps.get(randNum);
	//FOR loop that adjusts all y values as follows:
	for(int i = 0; i < toAppend.size(); i++){
		if(i == 0){
			toAppend.get(0).y = -Path.HEIGHT;
		}
		else{
			toAppend.get(i).y = toAppend.get(i - 1).y - toAppend.get(i).HEIGHT;
		}
	}

	return toAppend;
}
//=======================================
//Ensures that the created Path in generateNext() does not go off-screen; Returns true if proposed new Path will stay on-screen

	public static boolean checkOnScreen(Path proposedPath){
		/*if(proposedPath.x > proposedPath.exitX){
			if(proposedPath.exitX > 0 && proposedPath.x  + proposedPath.WIDTH < Game.WIDTH){
				return true;
			}
			else {
				return false;
			}
	    }
		else {
			if(proposedPath.x > 0 && proposedPath.exitX + proposedPath.WIDTH < Game.WIDTH){
				return true;
			}
			else {
				return false;
			}
		}*/

		if(proposedPath.name == "Horizontal"){//Horizontal
			if(proposedPath.x > Path.WIDTH * 2 && proposedPath.exitX > Path.WIDTH * 2 && proposedPath.x + Path.WIDTH * 2 < Game.WIDTH && proposedPath.exitX + Path.WIDTH * 2 < Game.WIDTH){
				return true;
			}
			else {
				return false;
			}

		}

		else if(proposedPath.name == "Straight" || proposedPath.name == "rightElbow" || proposedPath.name == "leftElbow"){//Straight, RightElbow, LeftElbow
			if(proposedPath.x + Path.WIDTH * 2 < Game.WIDTH && proposedPath.x > Path.WIDTH * 2){
				return true;
			}
			else {
				return false;
			}
		}

		else{//RightCorner, LeftCorner
			if(proposedPath.x > Path.WIDTH * 2 && proposedPath.exitX > Path.WIDTH * 2 && proposedPath.x + Path.WIDTH * 2 < Game.WIDTH && proposedPath.exitX + Path.WIDTH * 2 < Game.WIDTH){
				return true;
			}
			else {
				return false;
			}
		}
	}
//=======================================
//Returns an array of the Paths that are visible, where array [0] is the Path at the bottom of the screen
	public static Path[] getVisiblePaths(){
		int cumPathHeight = 0;
		int index = 0;
		while(cumPathHeight <= Game.HEIGHT){
			for (int i=0; i<World.mapsOnScreen.size(); i++) {
			cumPathHeight += World.mapsOnScreen.get(0).get(World.mapsOnScreen.get(i).size() - 1 - index).HEIGHT;
			index++;
		}
	}
		if(cumPathHeight < Game.HEIGHT){
			index++;
		}
		Path [] visiblePaths = new Path [index];
		for(int i = visiblePaths.length - 1; i >= 0; i--){
			for (int j=0; i<World.mapsOnScreen.size(); j++){
			visiblePaths [i] = World.mapsOnScreen.get(0).get(World.mapsOnScreen.get(j).size() - 1 - 1);
		}
	}
		return visiblePaths;
	}
}
