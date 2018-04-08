public class Path {
    boolean bumpersOn=false;
    final int bumperWidth; //need to decide what this is
    final int WIDTH;
    final int HEIGHT;
    int x; //corresponds to path's upper left corner
    int y; //corresponds to path's upper left corner
    //  final int gapLength; //are we still doing this?
    String name;

    public Path(int x) {
	name = "";
	bumperWidth=10; //random for now
	WIDTH=200;
	HEIGHT=300;
	x=x;
	y=0;
    }
}