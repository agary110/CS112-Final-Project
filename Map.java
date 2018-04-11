//////////////////////
//Also, just realized that within the Path class, we will need two more subclasses: leftElbow (L) and rightElbow(reflection of L). These subclasses will always precede leftCorner and rightCorner instances of the Path class, respectively.
//////////////////////

import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.lang.Math;

class Map{
	public static List<Path> upcomingPaths;
	public final double originalSpeed;
	private final double boosterSpeedAlt;
	public double speed;//x or y values moved per frame
	private List<Path> pathTypes;
	private Random rand;
//=======================================
//Constructor
	public Map(){
		rand = new Random();
		originalSpeed = 20;//Random num for now - (This is dependent on the difficulty level)
		speed = originalSpeed;
		boosterSpeedAlt = originalSpeed * 0.3;
		Straight straight = new Straight();
		upcomingPaths = new LinkedList<Path>();
		for(int i = 0; i < HEIGHT * 2; i++){
			upcomingPaths.add(straight);
		}
		initializePathTypes();
	}
//=======================================
//Fills pathTypes with instances of each subclass of Path
	private void initializePathTypes(){
	/* all of these instances need to take int x in the constructor */
		pathTypes = new LinkedList<Path>();
		Straight straight = new Straight();
		RightCorner rightC = new RightCorner();
		LeftCorner leftC = new LeftCorner();
		RightElbow rightE = new RightElbow();
		LeftElbow leftE = new LeftElbow();
		Horizontal horizontal = new Horizontal(); // constructor takes int
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
				visiblePaths [i].y = visiblePaths [i].y + (int)(speed);
			}
			if(visiblePaths [visiblePaths.length - 1].y > HEIGHT){
				addNewPath();
				this.update();
			}
		}
	}
//=======================================
//Draws Map using draw methods from subclasses of Path
//draw methods should be named draw(Graphics g0ri) only (?)
	public void draw(){
		Path [] visiblePaths = getVisiblePaths();
		Path visible;
		for(int i = 0; i < visiblePaths.length; i++){
			visible = upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i);
			if(visible.name == "Straight") visible = new Straight();
			else if(visible.name == "rightCorner") visible = new RightCorner();
			else if(visible.name == "leftCorner") visible = new LeftCorner();
			else if(visible.name == "rightElbow") visible = new RightElbow();
			else if(visible.name == "leftElbow") visible = new LeftElbow();
			else visible = new Horizontal();
			visible.draw(g);// We need a draw method for class path/the subclasses
		}
	}
//=======================================
//Returns a random type of Path (Straight, rightCorner, or leftCorner)
	private Path generateNext(){
		Path toReturn;
		if(upcomingPaths.getLast().name != "Horizontal"){
			if(upcomingPaths.getLast().name == "Straight"){
				int randNum = rand.nextInt(5);//includes all except Horizontal
				toReturn = pathTypes.get(randNum);
				while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(5);
					toReturn = pathTypes.get(randNum);
				}
			}
			else if(upcomingPaths.getLast().name == "rightCorner"){//includes rightElbow and Horizontal
				int randNum = rand.nextInt(2);
				if(randNum == 0) toReturn = pathTypes.get(3);
				else toReturn = pathTypes.get(5);
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
			if(randNum == 0) toReturn = pathTypes.get(5);
			else if(upcomingPaths.get(size() - 2).x < upcomingPaths.getLast().x) toReturn = pathTypes.get(3);
			else toReturn = pathTypes.get(4);
			while(checkOnScreen(toReturn) == false){
					randNum = rand.nextInt(2);
					if(randNum == 0) toReturn = pathTypes.get(5);
					else if(upcomingPaths.get(size() - 2).x < upcomingPaths.getLast().x) toReturn = pathTypes.get(3);
					else toReturn = pathTypes.get(4);
			}
			return toReturn;
		}
	}
//=======================================
//Ensures that the created Path in generateNext() does not go off-screen; Returns true if proposed new Path will stay on-screen
	private boolean checkOnScreen(Path proposedPath){
		if(proposedPath.x > 0 && proposedPath.x + proposedPath.WIDTH < WIDTH) return true;
		else return false;
	}
//=======================================
//Appends a random Path to the end of LinkedList<Path> upcomingPaths
	private static void addNewPath(){
		Path next = generateNext(); // gives error: non-static method generateNext() cannot be referenced from a static context
		next.x = upcomingPaths.getLast().x;
		upcomingPaths.add(next);
	}
//=======================================
//Returns an array of the Paths that are visible, where array [0] is the Path at the bottom of the screen
	public static Path[] getVisiblePaths(){
		int cumPathHeight = 0;
		int index = 0;
		while(cumPathHeight <= HEIGHT){
			cumPathHeight = cumPathHeight + upcomingPaths.get(upcomingPaths.size() - 1 - index).HEIGHT;
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
