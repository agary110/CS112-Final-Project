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
		addMaps();
		prototypePaths1();
		
		initializePathTypes();
	}
//=======================================
//add maps to linked list allMaps
	public void addMaps() {
	allMaps.add(upcomingPaths);
	allMaps.add(Map1);
	allMaps.add(Map2);
	}
//=======================================
//Fills pathTypes with instances of each subclass of Path
	private void initializePathTypes(){
		pathTypes = new LinkedList<Path>();
		Straight straight = new Straight(0);
		RightCorner rightC = new RightCorner(0);
		LeftCorner leftC = new LeftCorner(0);
		RightElbow rightE = new RightElbow(0);
		LeftElbow leftE = new LeftElbow(0);
		Horizontal horizontal = new Horizontal(0, true);
		pathTypes.add(straight);//Index 0
		pathTypes.add(rightC);//Index 1
		pathTypes.add(leftC);//Index 2
		pathTypes.add(rightE);//Index 3
		pathTypes.add(leftE);//Index 4
		pathTypes.add(horizontal);//Index 5
	}
//=======================================
//When marble moves up, the screen path will move down. When the lowest instance of Path on screen is no longer visible, a new Path is generated and inserted at the top of the screen.
	public void update(double time){

		for(int i = 0; i < upcomingPaths.size(); i++){
			upcomingPaths.get(i).update();	
		}
		if(upcomingPaths.getLast().y > 0){
			//addNewPath();
			upcomingPaths.add(generateNext());
		}

	}
//=======================================
//Draws Map using draw methods from subclasses of Path
	public void draw(Graphics g){
		for(int i = 0; i < upcomingPaths.size(); i++){
			upcomingPaths.get(i).draw(g);
		}
	}
//=======================================
//Returns an instance of random subclass of Path

public static void prototypePaths1(){
	//option 1

	Map1.add(new RightCorner(upcomingPaths.getLast()));
	Map1.add(new RightElbow(upcomingPaths.getLast()));
	Map1.add(new Straight(upcomingPaths.getLast()));
	Map1.add(new LeftCorner(upcomingPaths.getLast()));
	Map1.add(new Horizontal(upcomingPaths.getLast(), false));
	Map1.add(new Horizontal(upcomingPaths.getLast(), false));
	Map1.add(new LeftElbow(upcomingPaths.getLast()));
	Map1.add(new Straight(upcomingPaths.getLast()));
	Map1.add(new RightCorner(upcomingPaths.getLast()));
	Map1.add(new Horizontal(upcomingPaths.getLast(), true));
	Map1.add(new Horizontal(upcomingPaths.getLast(), true));
	Map1.add(new RightElbow(upcomingPaths.getLast()));
	Map1.add(new LeftCorner(upcomingPaths.getLast()));
	Map1.add(new LeftElbow(upcomingPaths.getLast()));

//option 2
	Map2.add(new LeftCorner(upcomingPaths.getLast()));
	Map2.add(new Horizontal(upcomingPaths.getLast(), false));
	Map2.add(new LeftElbow(upcomingPaths.getLast()));
	Map2.add(new RightCorner(upcomingPaths.getLast()));
	Map2.add(new RightElbow(upcomingPaths.getLast()));
	Map2.add(new Straight(upcomingPaths.getLast()));
	Map2.add(new RightCorner(upcomingPaths.getLast()));
	Map2.add(new Horizontal(upcomingPaths.getLast(), true));
	Map2.add(new RightElbow(upcomingPaths.getLast()));
	Map2.add(new Straight(upcomingPaths.getLast()));
	Map2.add(new LeftCorner(upcomingPaths.getLast()));
	Map2.add(new LeftElbow(upcomingPaths.getLast()));
//option 3
	Map3.add(new RightCorner(upcomingPaths.getLast()));
	Map3.add(new RightElbow(upcomingPaths.getLast()));
	Map3.add(new RightCorner(upcomingPaths.getLast()));
	Map3.add(new RightElbow(upcomingPaths.getLast()));
	Map3.add(new Straight(upcomingPaths.getLast()));
	Map3.add(new Straight(upcomingPaths.getLast()));
	Map3.add(new LeftCorner(upcomingPaths.getLast()));
	Map3.add(new LeftElbow(upcomingPaths.getLast()));
	Map3.add(new Straight(upcomingPaths.getLast()));
	Map3.add(new LeftCorner(upcomingPaths.getLast()));
	Map3.add(new LeftElbow(upcomingPaths.getLast()));
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

public static Path generateNext(){
	int randNum;
	String lastName = upcomingPaths.getLast().name;
	String nextName;

	if(lastName != "Horizontal"){
		
		if(lastName == "Straight"){//Straight
			randNum = rand.nextInt(3);
			nextName = pathTypes.get(randNum).name;
		}

		else if(lastName == "rightCorner"){//rightCorner
			randNum = rand.nextInt(2);
			if(randNum == 0){
				nextName = pathTypes.get(3).name;
			}
			else{
				nextName = pathTypes.get(5).name;
			}
		}

		else if(lastName == "leftCorner"){//leftCorner
			randNum = rand.nextInt(2) + 4;
			nextName = pathTypes.get(randNum).name;
		}

		else{//rightElbow and leftElbow
			randNum = rand.nextInt(3);
			nextName = pathTypes.get(randNum).name;
		}
	}

	else{//Horizontal
		randNum = rand.nextInt(2);
		if(randNum == 0){
			nextName = pathTypes.get(5).name;
		}
		else if(upcomingPaths.get(upcomingPaths.size() - 3).x < (upcomingPaths.get(upcomingPaths.size() - 2).x)){
//upcomingPaths.getLast().x){
			nextName = pathTypes.get(3).name;
		}
		else{
			nextName = pathTypes.get(4).name;
		}
	}

	Path toReturn;
	int exitX = upcomingPaths.getLast().x;

	if(nextName == "Straight"){
		toReturn = new Straight(exitX);
	}
	else if(nextName == "rightCorner"){
		toReturn = new RightCorner(exitX);
		toReturn.y += Path.WIDTH;
	}
	else if(nextName == "leftCorner"){
		toReturn = new LeftCorner(exitX);
		toReturn.y += Path.WIDTH;
	}
	else if(nextName == "rightElbow"){
		toReturn = new RightElbow(exitX);
	}
	else if(nextName == "leftElbow"){
		toReturn = new LeftElbow(exitX);
	}
	else{
		boolean direction;
		if(upcomingPaths.getLast().x > upcomingPaths.get(upcomingPaths.size() - 2).x){
			direction = true;
		}
		else{
			direction = false;
		}
		toReturn = new Horizontal(exitX, direction);
		toReturn.y += Path.WIDTH;
	}

	if(checkOnScreen(toReturn) == false){
		if(nextName == "Straight" || nextName == "rightElbow" || nextName == "leftElbow"){
			toReturn = new Straight(exitX);
		}
		else{
			boolean direction;
			if(upcomingPaths.getLast().x > upcomingPaths.get(upcomingPaths.size() - 2).x){
				direction = true;
			}
			else{
				direction = false;
			}
			toReturn = new Horizontal(exitX, direction);
		}
	}
	
	return toReturn;

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
			cumPathHeight += upcomingPaths.get(upcomingPaths.size() - 1 - index).HEIGHT;
			index++;
		}
		if(cumPathHeight < Game.HEIGHT){
			index++;
		}
		Path [] visiblePaths = new Path [index];
		for(int i = visiblePaths.length - 1; i >= 0; i--){
			visiblePaths [i] = upcomingPaths.get(upcomingPaths.size() - 1 - i);
		}
		return visiblePaths;
	}
}
