import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.lang.Math;

class Map{
	public static LinkedList<Path> initialScreens;
	public static LinkedList<Path> Screen1;
	public static LinkedList<Path> Screen2;
	public static LinkedList<Path> Screen3;
	public static LinkedList<Path> Screen4;
	public static LinkedList<LinkedList<Path>> allScreens;
	public static Random rand;
//=======================================
//Constructor
	public Map(){
		rand = new Random(1);
		initialScreens = new LinkedList<Path>();
		Screen1 = new LinkedList<Path>();
		Screen2 = new LinkedList<Path>();
		Screen3 = new LinkedList<Path>();
		Screen4 = new LinkedList<Path>();
		allScreens = new LinkedList<LinkedList<Path>>();

		initialScreens.add(new BigRect(Game.HEIGHT / 2));
		for(int i = 1; i < 6; i++){
			initialScreens.add(new Straight(initialScreens.get(i - 1)));
		}

		addMaps();
		
	}
//=======================================
//Add maps to linked list allScreens
	public static void addMaps() {
		allScreens.add(initialScreens);
		allScreens.add(Screen1);
		allScreens.add(Screen2);
		allScreens.add(Screen3);
		allScreens.add(Screen4);
	}
//=======================================
//When marble moves up, the screen path will move down. When the lowest instance of Path on screen is no longer visible, a new Path is generated and inserted at the top of the screen.
	public void update(){

		for(int i = 0; i < World.visibleScreens.size(); i++){
			for(int j = 0; j < World.visibleScreens.get(i).size(); j++){
				World.visibleScreens.get(i).get(j).y++;
				/*if(World.visibleScreens.get(i).get(j).name == "Trapezoid"){
					World.visibleScreens.get(i).get(j).update();
				}*/
			}
		}

		if(World.visibleScreens.get(0).getLast().y >= Game.HEIGHT){
			World.visibleScreens.remove(0);
		}

		if(World.visibleScreens.get(0).getLast().y==0) {
			World.visibleScreens.add(generateNext());
			for (int i = 0; i<World.visibleScreens.get(1).size(); i++) {
				World.visibleScreens.get(1).get(i).y += World.visibleScreens.get(0).getLast().WIDTH;
//+World.visibleScreens.get(0).getLast().WIDTH/2;
			}
		System.out.println("we updating");
		}

	}
//=======================================
//Draws Map using draw methods from subclasses of Path
	public void draw(Graphics g){
		for(int i = 0; i < World.visibleScreens.size(); i++){
			for (int j=0; j<World.visibleScreens.get(i).size(); j++) {
				World.visibleScreens.get(i).get(j).draw(g);
			}
		}
		
	}
//=======================================
//Returns an instance of random subclass of Path
 

	public static LinkedList<Path> getScreen1(LinkedList<Path> curr){
		LinkedList<Path> toR = new LinkedList<Path>();
		toR.add(new Straight(curr.getLast()));
		toR.add(new TopRightCorner(toR.getLast()));
		toR.add(new BottomRightCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new TopLeftCorner(toR.getLast()));
		toR.add(new Horizontal(toR.getLast(), false));
		toR.add(new Horizontal(toR.getLast(), false));
		toR.add(new BottomLeftCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new TopRightCorner(toR.getLast()));
		toR.add(new Horizontal(toR.getLast(), true));
		toR.add(new Horizontal(toR.getLast(), true));
		toR.add(new BottomRightCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new TopLeftCorner(toR.getLast()));
		toR.add(new BottomLeftCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		//toR.add(new TopRightCorner(toR.getLast()));
		//toR.add(new BottomLeftCorner(toR.getLast()));		
		return toR;
	}

	public static LinkedList<Path> getScreen2(LinkedList<Path> curr){
		LinkedList<Path> toR = new LinkedList<Path>();
		toR.add(new Straight(curr.getLast()));
		toR.add(new TopLeftCorner(toR.getLast()));
		toR.add(new Horizontal(toR.getLast(), false));
		toR.add(new BottomLeftCorner(toR.getLast()));
		toR.add(new TopRightCorner(toR.getLast()));
		toR.add(new BottomRightCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new TopRightCorner(toR.getLast()));
		toR.add(new Horizontal(toR.getLast(), true));
		toR.add(new BottomRightCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new TopLeftCorner(toR.getLast()));
		toR.add(new BottomLeftCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		//toR.add(new TopRightCorner(toR.getLast()));
		//toR.add(new BottomLeftCorner(toR.getLast()));
		return toR;

	}

	public static LinkedList<Path> getScreen3(LinkedList<Path> curr){
		LinkedList<Path> toR = new LinkedList<Path>();
		toR.add(new Straight(curr.getLast()));
		toR.add(new TopRightCorner(toR.getLast()));
		toR.add(new BottomRightCorner(toR.getLast()));
		//toR.add(new TopRightCorner(toR.getLast()));
		//toR.add(new BottomRightCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		//toR.add(new TopLeftCorner(toR.getLast()));
		//toR.add(new BottomLeftCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new TopLeftCorner(toR.getLast()));
		//Left corner does not work— need to fix
		toR.add(new BottomLeftCorner(toR.getLast()));
		//toR.add(new TopRightCorner(toR.getLast()));
		//toR.add(new BottomLeftCorner(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		return toR;

	}
	public static LinkedList<Path> getScreen4(LinkedList<Path> curr){
		LinkedList<Path> toR = new LinkedList<Path>();
		toR.add(new Straight(curr.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		toR.add(new Straight(toR.getLast()));
		return toR;
    }
//=======================================
public static LinkedList<Path> generateNext(){
	int randNum = rand.nextInt(allScreens.size() - 1) + 1;
	//randNum = 0;x	
	LinkedList<Path> toAppend = new LinkedList<Path>();

	switch (randNum) {
		case 1: toAppend = getScreen1(World.visibleScreens.getLast());
				 break;
		case 2: toAppend = getScreen2(World.visibleScreens.getLast());
				 break;
		case 3: toAppend = getScreen3(World.visibleScreens.getLast());
				 break;
		case 4: toAppend = getScreen4(World.visibleScreens.getLast());
				 break;
	}

	System.out.println(toAppend);
	System.out.println("randNum: " + randNum);
	System.out.flush();
	if (randNum == 5) System.out.println("BAD");
	System.out.println("in generate next and number of allScreens is: " + allScreens.size());
	return toAppend;
}
//=======================================
//Returns an array of the Paths that are visible, where array [0] is the Path at the bottom of the screen
	public static Path[] getVisiblePaths(){
		int cumPathHeight = 0;
		int index = 0;
		while(cumPathHeight <= Game.HEIGHT){
			for (int i=0; i<World.visibleScreens.size(); i++) {
				cumPathHeight += World.visibleScreens.get(0).get(World.visibleScreens.get(i).size() - 1 - index).HEIGHT;
				index++;
			}
		}

		if(cumPathHeight < Game.HEIGHT){
			index++;
		}
		Path [] visiblePaths = new Path [index];
		for(int i = visiblePaths.length - 1; i >= 0; i--){
			for (int j=0; i<World.visibleScreens.size(); j++){
				visiblePaths [i] = World.visibleScreens.get(0).get(World.visibleScreens.get(j).size() - 1 - 1);
			}
		}
		return visiblePaths;
	}
}
