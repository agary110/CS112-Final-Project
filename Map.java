import java.awt.Color;
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
		upcomingPaths = new LinkedList<Path>();

		upcomingPaths.add(new Straight(Game.WIDTH / 2 - Path.WIDTH / 2));
		upcomingPaths.get(0).y = Game.HEIGHT;

		for(int i = 1; i < 6; i++){
			upcomingPaths.add(new Straight(upcomingPaths.get(i - 1)));
		}

		prototypePaths();

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
		/*Path [] visiblePaths = getVisiblePaths();
		for (int i = 0; i < visiblePaths.length; i++){
			//visiblePaths [i].y = visiblePaths [i].y + (int)(time);
			upcomingPaths.get(upcomingPaths.size() - visiblePaths.length + i).y += (int)(time) * 25;
		}
		if (visiblePaths [visiblePaths.length - 1].y > Game.HEIGHT){
			addNewPath();
			this.update(time);
		}

		for(int i = 0; i < upcomingPaths.size(); i++){
			upcomingPaths.get(i).setY(10);
		}

		if(upcomingPaths.getLast().y > 0){
			addNewPath();
		}*/

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
		//Path [] visiblePaths = getVisiblePaths();
		/*Path visible;
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
				if(visiblePaths [visiblePaths.length - 2].x < visiblePaths [visiblePaths.length - 1].x){
					visible = new Horizontal(previousVisible.x, true);//true return value indicates that exitX < x
				}
				else{
					visible = new Horizontal(previousVisible.x, false);//false return values indicates that x < exitX
				}
			}
			g.setColor(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
			visible.draw(g);
		}*/

		for(int i = 0; i < upcomingPaths.size(); i++){
			upcomingPaths.get(i).draw(g);
		}
	}
//=======================================
//Returns an instance of random subclass of Path

public static void prototypePaths(){
	upcomingPaths.add(new RightCorner(upcomingPaths.getLast()));
	upcomingPaths.add(new RightElbow(upcomingPaths.getLast()));
	upcomingPaths.add(new Straight(upcomingPaths.getLast()));
	upcomingPaths.add(new LeftCorner(upcomingPaths.getLast()));
	upcomingPaths.add(new Horizontal(upcomingPaths.getLast(), false));
	upcomingPaths.add(new Horizontal(upcomingPaths.getLast(), false));
	upcomingPaths.add(new LeftElbow(upcomingPaths.getLast()));
	upcomingPaths.add(new Straight(upcomingPaths.getLast()));
	upcomingPaths.add(new RightCorner(upcomingPaths.getLast()));
	upcomingPaths.add(new Horizontal(upcomingPaths.getLast(), true));
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
		else if(upcomingPaths.get(upcomingPaths.size() - 3).x < (upcomingPaths.get(upcomingPaths.size() - 2).x){
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