import java.util.List;
import java.util.LinkedList;
import java.util.Random;

class Map{
	public static List<Path> upcomingPaths;
	public static final int pathsPerFrame = HEIGHT / path.length;//Check variable name for path length
	public final double originalSpeed;
	public static double speed;//x or y values moved per frame
	private final double boosterSpeedAlt = 666;//How much should boosters alter the map speed (positively or negatively)?
	private final STRAGIHTPATH straight;//Check class name
	private final RIGHTCORNER rightCorner;//Check class name
	private final LEFTCORNER leftCorner;//Check class name
	private Random rand;
//=======================================
//Constructor
	public Map(){
		rand = new Random();
		originalSpeed = ;//What speed? (This is dependent on the difficulty level)
		speed = originalSpeed;
		upcomingPaths = new LinkedList<Path>();
		for(int i = 0; i < HEIGHT * 2; i++){
			upcomingPaths.add(straight);
		}
	}
//=======================================
//When marble moves up, the screen path will move down. When the lowest instance of Path on screen is no longer visible, a new Path is generated and inserted at the top of the screen.
	public void update(){
		Path [] visiblePaths = getVisiblePaths();
		for(int i = 0; i < visiblePaths.length; i++){
			if(ARGUMENT) CREATE NEW STRAIGHTPATH;
			else if(ARGUMENT) CREATE NEW RIGHTCORNER;
			else CREATE NEW LEFTCORNER;
		}
		if(marble.velocity.y == 0);
		else{
			for(int i = 0; i < visiblePaths.length; i++){
				visiblePaths [i].position.y = visiblePaths [i].position.y + speed;
				if(i == 0 && visiblePaths [i].position.y > HEIGHT){
					moveScreenForward();
					this.update();
				}
			}
		}
	}
//=======================================
//Returns a random type of Path (Straight, rightCorner, or leftCorner)
	private Path generateNext(){
		Random rand = new Random(key);
		int randNum = rand.nextInt(3);
		if(randNum == 0) return straight;
		else if(randNum == 1) return rightCorner;
		else return leftCorner;
	}
//=======================================
//Returns the index within upcomingPaths that corresponds to the visible Path at the top of the screen
	private static int moveScreenForward(){
		upcomingPaths.add(generateNext());
		int firstDisplayIndex = upcomingPaths.size() - pathsPerFrame;
		return firstDisplayIndex;
	}
//=======================================
//Returns an array of the Paths that are visible, where array [0] is the Path at the bottom of the screen
	public static Path[] getVisiblePaths(){
		Path [] visiblePaths = new Path [pathsPerFrame];
		for(int i = 0; i < visiblePaths.length; i++){
			visiblePaths [i] = upcomingPaths.get(moveScreenForward() + i);
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
//=======================================
//Returns a random type of Booster (BOOSTER TYPES)
	public static Booster generateNextBooster(){//Insert Booster subclasses & should be run whenever Booster is passed or collected
		int randNum = rand.nextInt(NUMBER-OF-BOOSTERS);
		if(randNum == 0){
			TYPEOFBOOSTER booster = new TYPEOFBOOSTER();
		}
		else{
			TYPEOFBOOSTER booster = new TYPEOFBOOSTER();
		}
		int distanceToNext = rand.nextInt(500) + RANGEBASEDONDIFFICULTY;
		booster.position.y = marble.position.y + distanceToNext;
		booster.position.x = LEFT-SIDE-OF-PATH-AT-booster.position.y + rand.nextInt(PATHWIDTH - booster.width);
		return booster;
	}
}
