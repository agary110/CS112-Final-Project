import java.util.List;
import java.util.LinkedList;
import java.util.Random;

class Map{
	public static List<Path> upcomingPaths;
	public static final pathsPerFrame = HEIGHT / path.length;//Check variable name for path length
	public final double originalSpeed;
	public static speed;
	private final double boosterSpeedAlt = 666;//How much should boosters alter the map speed (positively or negatively?
	private final STRAGIHTPATH straight;//Check class name
	private final RIGHTCORNER rightCorner;//Check class name
	private final LEFTCORNER leftCorner;//Check class name
	private Random rand;

	public Map(){
		rand = new Random();
		originalSpeed = //What speed? (This is dependent on the difficulty level)
		speed = originalSpeed;
		//Create straight path using Path class
		upcomingPaths = new LinkedList<Path>();
		for(int i = 0; i < HEIGHT * 2; i++){
			upcomingPaths.add(straight);
		}
	}

	private Path generateNext(){
		Random rand = new Random(key);
		int randNum = rand.nextInt(3);
		if(randNum == 0) return straight;
		else if(randNum == 1) return rightCorner;
		else return leftCorner;
	}

	private int moveScreenForward(){
		upcomingPaths.add(generateNext());
		int firstDisplayIndex = upcomingPaths.size() - pathsPerFrame;
		return firstDisplayIndex;
	}

	public static Path[] getVisiblePaths(){
		Path [] visiblePaths = new Path [pathsPerFrame];
		for(int i = 0; i < visiblePaths.length; i++){
			visiblePaths [i] = upcomingPaths.get(moveScreenForward() + i);
		}
		return visiblePaths;
	}

	public void changeSpeed(boolean faster){//Changes speed as a result of booster
		if(faster) speed = speed + boosterSpeedAlt;
		else speed = speed - boosterSpeedAlt;
	}

	public void changeSpeed(){//Reverts speed back to default
		speed = originalSpeed;
	}
}
