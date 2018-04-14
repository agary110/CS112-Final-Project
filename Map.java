//////////////////////
//Also, just realized that within the Path class, we will need two more subclasses: leftElbow (L) and rightElbow(reflection of L). These subclasses will always precede leftCorner and rightCorner instances of the Path class, respectively.

//Still need to edit the following methods: addNewPath() and draw(Graphics g)
//////////////////////

import java.awt.Graphics;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.lang.Math;

class Map{
	public static LinkedList<Path> upcomingPaths;
	public static List<Path> pathTypes;
	public static Random rand;
//=======================================
//Constructor
	public Map(){
		rand = new Random();
		Straight straight = new Straight(Game.WIDTH / 2 - Path.WIDTH / 2);
		upcomingPaths = new LinkedList<Path>();
		for(int i = 0; i < Game.HEIGHT * 2; i++){
			upcomingPaths.add(straight);
		}
		initializePathTypes();
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
		Horizontal horizontal = new Horizontal(0);
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
		Path [] visiblePaths = getVisiblePaths();
		for (int i = 0; i < visiblePaths.length; i++){
			visiblePaths [i].y = visiblePaths [i].y + (int)(time);
		}
		if (visiblePaths [visiblePaths.length - 1].y > Game.HEIGHT){
			addNewPath();
			this.update(time);
		}
	}
//=======================================
//Draws Map using draw methods from subclasses of Path
	public void draw(Graphics g){
		Path [] visiblePaths = getVisiblePaths();
		Path visible;
		Path previousVisible;
		for(int i = 0; i < visiblePaths.length; i++){
			visible = upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i);
			previousVisible = upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i - 1);
			if(visible.name == "Straight"){
				visible = new Straight(previousVisible.x);
			}
			else if(visible.name == "rightCorner"){
				visible = new RightCorner(previousVisible.x);
			}
			else if(visible.name == "leftCorner"){
				visible = new LeftCorner(previousVisible.x);
			}
			else if(visible.name == "rightElbow"){
				visible = new RightElbow(previousVisible.x);
			}
			else if(visible.name == "leftElbow"){
				visible = new LeftElbow(previousVisible.x);
			}
			else{
				visible = new Horizontal(previousVisible.x);
			}
			visible.draw(g);
		}
	}
//=======================================
//Returns an instance of random subclass of Path
	public static Path generateNext(){
		Path toReturn;
		if(upcomingPaths.getLast().name != "Horizontal"){
			if(upcomingPaths.getLast().name == "Straight"){//includes all except Horizontal
				int randNum = rand.nextInt(5);
				toReturn = pathTypes.get(randNum);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(5);
					toReturn = pathTypes.get(randNum);
				}
			}
			else if(upcomingPaths.getLast().name == "rightCorner"){//includes rightElbow and Horizontal
				int randNum = rand.nextInt(2);
				if(randNum == 0){
					toReturn = pathTypes.get(3);
				}
				else{
					toReturn = pathTypes.get(5);
				}
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(2);
					if(randNum == 0) toReturn = pathTypes.get(3);
					else toReturn = pathTypes.get(5);
				}
			}
			else if(upcomingPaths.getLast().name == "leftCorner"){//includes leftElbow and Horizontal
				int randNum = rand.nextInt(2) + 4;
				toReturn = pathTypes.get(randNum);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(2) + 4;
					toReturn = pathTypes.get(randNum);
				}
			}
			else if(upcomingPaths.getLast().name == "rightElbow"){//includes Straight, rightCorner, and leftCorner
				int randNum = rand.nextInt(3);
				toReturn = pathTypes.get(randNum);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(3);
					toReturn = pathTypes.get(randNum);
				}
			}
			else if(upcomingPaths.getLast().name == "leftElbow"){//includes Straight, rightCorner, and leftCorner
				int randNum = rand.nextInt(3);
				toReturn = pathTypes.get(randNum);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(3);
					toReturn = pathTypes.get(randNum);
				}
			}
			toReturn.x = upcomingPaths.getLast().x;
			return toReturn;
		}
		else{//if Path.name == "Horizontal"
			int randNum = rand.nextInt(2);
			if(randNum == 0){
				toReturn = pathTypes.get(5);
			}
			else if(upcomingPaths.get(upcomingPaths.size() - 2).x < upcomingPaths.getLast().x){
				toReturn = pathTypes.get(3);
			}
			else{
				toReturn = pathTypes.get(4);
			}
			while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(2);
					if(randNum == 0){
						toReturn = pathTypes.get(5);
					}
					else if(upcomingPaths.get(upcomingPaths.size() - 2).x < upcomingPaths.getLast().x){
						toReturn = pathTypes.get(3);
					}
					else{
						toReturn = pathTypes.get(4);
					}
			}
			return toReturn;
		}
	}
//=======================================
//Ensures that the created Path in generateNext() does not go off-screen; Returns true if proposed new Path will stay on-screen

	public static boolean checkOnScreen(Path proposedPath){
		if(proposedPath.x > proposedPath.exitX){
			if(proposedPath.exitX > 0 && proposedPath.x  + proposedPath.WIDTH < Game.WIDTH){
				return true;
			}
			else return false;
	    }
		else {
			if(proposedPath.x > 0 && proposedPath.exitX + proposedPath.WIDTH < Game.WIDTH){
				return true;
			}
			else return false;
		}
	}
//=======================================
//Appends a random Path to the end of LinkedList<Path> upcomingPaths

/* Will need to edit this method so that next.BOTTUM_X = upcomingPaths.getLast().TOP_OPENNING_X (current implementation does not account for the fact that the Path.x value at the top is different from that of the bottom */

	private static void addNewPath(){
		Path next = generateNext();
		next.x = upcomingPaths.getLast().x;
		upcomingPaths.add(next);
	}
//=======================================
//Returns an array of the Paths that are visible, where array [0] is the Path at the bottom of the screen
	public static Path[] getVisiblePaths(){
		int cumPathHeight = 0;
		int index = 0;
		while(cumPathHeight <= Game.HEIGHT){
			cumPathHeight = cumPathHeight + upcomingPaths.get(upcomingPaths.size() - 1 - index).HEIGHT;
			index++;
		}
		if(cumPathHeight < Game.HEIGHT){
			index++;
		}
		Path [] visiblePaths = new Path [index];
		for(int i = visiblePaths.length; i > 0; i++){
			visiblePaths [i] = upcomingPaths.get(upcomingPaths.size() - i);
		}
		return visiblePaths;
	}
}