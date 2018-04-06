//////////////////////
//For this class to work properly, the Path class must… [1] have an x and y coordinate that corresponds to the Path's upper-left-most corner; [2] have one public, non-static method to change x and another to change y; [3] an int width variable that holds the entire width of the Path; [4] a String name that denotes the name of the subclass (i.e. in Straight subclass, super.name = "Straight"; in rightCorner subclass, super.name = "rightCorner"; in leftCorner subclass, super.name = "leftCorner")
//Also, just realized that within the Path class, we will need two more subclasses: leftElbow (L) and rightElbow(reflection of L). These subclasses will always precede leftCorner and rightCorner instances of the Path class, respectively. Also, we need a Horizontal subclass of Path :)
//////////////////////

import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.util.Math;

class Map{
	public static List<Path> upcomingPaths;
	public final double originalSpeed;
	public static double speed;
	private final double boosterSpeedAlt;
	public static double speed;//x or y values moved per frame
	private List<Path> pathTypes;
	private Random rand;
//=======================================
//Constructor
	public Map(){
		rand = new Random();
		originalSpeed = ;//What speed? (This is dependent on the difficulty level)
		speed = originalSpeed;
		boosterSpeedAlt = originalSpeed * 0.3;
		upcomingPaths = new LinkedList<Path>();
		for(int i = 0; i < HEIGHT * 2; i++){
			upcomingPaths.add(straight);
		}
		initializePathTypes();
	}
//=======================================
//Fills pathTypes with instances of each subclass of Path
	private void initializePathTypes(){
		pathTypes<Path> = new LinkedList<Path>();
		Straight straight = new Straight();
		rightCorner rightC = new rightCorner();
		leftCorner leftC = new leftCorner();
		rightElbow rightE = new rightElbow();
		leftElbow leftE = new leftElbow();
		Horizontal horizontal = new Horizontal();
		pathTypes.add(straight);//Index 0
		pathTypes.add(rightC);//Index 1
		pathTypes.add(leftC);//Index 2
		pathTypes.add(rightE);//Index 3
		pathTypes.add(leftE);//Index 4
		pathTypes.add(horizontal);//Index 5
	}
//=======================================
//When marble moves up, the screen path will move down. When the lowest instance of Path on screen is no longer visible, a new Path is generated and inserted at the top of the screen.
	public void update(){
		if(marble.velocity.y == 0);
		else{
			Path [] visiblePaths= getVisiblePaths();
			for(int i = 0; i < visiblePaths.length; i++){
				visiblePaths [i].y = visiblePaths [i].y + speed;
			}
			if(visiblePaths [visiblePaths.length - 1].y > HEIGHT){
				addNewPath();
				this.update();
			}
		}
	}
//=======================================
//Draws Map using draw methods from subclasses of Path
	public void draw(){
		Path [] visiblePaths = getVisiblePaths();
		for(int i = 0; i < visiblePaths.length; i++){
			if(upcomingPaths.get(upcomingPaths.size()) - visiblePaths.length + i).name == "Straight") drawStraight(upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).x, upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).y);
			else if(upcomingPaths.get(upcomingPaths.size()) - visiblePaths.length + i).name == "rightCorner") drawRightCorner(upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).x, upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).y);
			else if(upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).name == "leftCorner") drawLeftCorner(upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).x, upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).y);
			else if(upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).name == "rightElbow") drawRightElbow(upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).x, upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).y);
			else if(upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).name == "leftElbow") drawLeftElbow(upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).x, upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).y);
			else drawHorizontal(upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).x, upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).y);
		}
	}
//=======================================
//Returns a random type of Path (Straight, rightCorner, or leftCorner)
	private Path<P> generateNext(){
		if(upcomingPaths.getLast().name != "Horizontal){
			if(upcomingPaths.getLast().name == "Straight"){
				int randNum = rand.nextInt(5);//includes all except Horizontal
				P toReturn = pathTypes.get(randNum);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(5);
					toReturn = pathTypes.get(randNum);
				}
			}
			else if(upcomingPaths.getLast().name == "rightCorner"){//includes rightElbow and Horizontal
				int randNum = rand.nextInt(2);
				if(randNum == 0) P toReturn = pathTypes.get(3);
				else P toReturn = pathTypes.get(5);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(2);
					if(randNum == 0) toReturn = pathTypes.get(3);
					else toReturn = pathTypes.get(5);
				}
			}
			else if(upcomingPaths.getLast().name == "leftCorner"){//includes leftElbow and Horizontal
				int randNum = rand.nextInt(2) + 4;
				P toReturn = pathTypes.get(randNum);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(2) + 4;
					toReturn = pathTypes.get(randNum);
				}
			}
			else if(upcomingPaths.getLast().name == "rightElbow"){//includes Straight, rightCorner, and leftCorner
				int randNum = rand.nextInt(3);
				P toReturn = pathTypes.get(randNum);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(3);
					toReturn = pathTypes.get(randNum);
				}
			}
			else if(upcomingPaths.getLast().name == "leftElbow"){//includes Straight, rightCorner, and leftCorner
				int randNum = rand.nextInt(3);
				P toReturn = pathTypes.get(randNum);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(3);
					toReturn = pathTypes.get(randNum);
				}
			}
			toReturn.x = upcomingPaths.getLast().x;
			return toReturn;
		}
		else{//if upcomingPaths.getLast() is a Horizontal, returns Horizontal or an elbow (left vs. right is dependent on previous Path direction
			int randNum = rand.nextInt(2);
			if(randNum == 0) P toReturn = pathTypes.get(5);
			else if(upcomingPaths.get(size() - 2).x < upcomingPaths.getLast().x) P toReturn = pathTypes.get(3);
			else P toReturn = pathTypes.get(4);
			while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(2);
					if(ranNum == 0) toReturn = pathTypes.get(5);
					else if(upcomingPaths.get(size() - 2).x < upcomingPaths.getLast().x) P toReturn = pathTypes.get(3);
					else P toReturn = pathTypes.get(4);
			}
			return toReturn;
		}
	}
//=======================================
//Ensures that the created Path in generateNext() does not go off-screen; Returns true if proposed new Path will stay on-screen
	private boolean checkOnScreen(Path<P> proposedPath){
		if(proposedPath.x > 0 && proposedPath.x + proposedPath.width < WIDTH) return true;
		else return false;
	}
//=======================================
//Appends a random Path to the end of LinkedList<Path> upcomingPaths
	private static void addNewPath(){
		upcomingPaths.add(generateNext());
	}
//=======================================
//Returns an array of the Paths that are visible, where array [0] is the Path at the bottom of the screen
	public static Path[] getVisiblePaths(){
		int cumPathHeight = 0;
		int index = 0;
		while(cumPathHeight <= HEIGHT){
			cumPathHeight = cumPathHeight + upcomingPaths.get[upcomingPaths.size() - 1 - index].height;
			index++;
		}
		if(cumPathHeight < HEIGHT) index++;
		Path [] visiblePaths = new Path [index];
		for(int i = visiblePaths.length; i > 0; i++){
			visiblePaths [i] = upcomingPaths.get(upcomingPaths.size() - i);
		}
		return visiblePaths;
	}
//=======================================
//Increases or decreases speed by a specified constant
	public void changeSpeed(boolean faster){
		if(faster) speed = speed + boosterSpeedAlt;
		else speed = speed - boosterSpeedAlt;
	}
//=======================================
//Reverts speed back to default
	public void changeSpeed(){
		speed = originalSpeed;
	}
}
