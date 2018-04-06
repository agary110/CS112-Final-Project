public class Path {
    boolean bumpersOn=false;
    final int bumperWidth = 10; //need to decide what this is
    final int WIDTH;
    final int HEIGHT;
    int x; //corresponds to path's upper left corner
    int y; //corresponds to path's upper left corner
    //  final int gapLength; //are we still doing this?
    String name;

    public Path() {
	name = "";
	bumperWidth=10; //random for now
	WIDTH=200;
	HEIGHT=300;
	x=800;
	y=450;
    }

    public void changeX(){
	//not sure what we're putting here?
    }

    public void changeY(){

	//same as changeX
    }
}
