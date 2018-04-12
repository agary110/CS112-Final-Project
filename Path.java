public class Path {
    boolean bumpersOn=false;
    final int bumperWidth; //need to decide what this is
    public static final int WIDTH = 200;
    public static final int HEIGHT = 300;
    int x; //corresponds to path's upper left corner
    int y; //corresponds to path's upper left corner
    //  final int gapLength; //are we still doing this?
    String name;

    public Path(int x) {
	name = "";
	bumperWidth=10; //random for now
	x=x;
	y=0;
    }
}