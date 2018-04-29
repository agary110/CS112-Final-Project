import java.awt.Graphics;
import java.awt.Color;

public class AmmoReleased{

	int x;
	int y;
	static final int width = 2;
	static final int length = 4;

	public AmmoReleased(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g){
		for(int i = 0; i < World.ammoActive.size(); i++){
			g.setColor(Color.GRAY);
			g.fillRect(World.ammoActive.get(i).x, World.ammoActive.get(i).y, width, length);
			g.setColor(Color.BLACK);
			g.drawRect(World.ammoActive.get(i).x, World.ammoActive.get(i).y, width, length);
		}
	}

	public void update(){
		this.y-= 2;

		if(this.y < -length){ //when bottom of ammo goes offscreen
			this.deactivate();
		}

		if(World.ammoActive.size() == 0){
			World.ammoReleased = false;
		}
	}

	public static void activate(){
		AmmoReleased newAmmoReleased = new AmmoReleased((int)(World.marble.position.x) + World.marble.radius / 2, (int)(World.marble.position.y));
		World.ammoActive.add(newAmmoReleased);
		World.ammoCount--;
	}

	public static void deactivate(){
		World.ammoActive.remove();
	}

}

/*class Node{

	AmmoReleased ammoR;
	Node previous;
	int index;

	public Node(AmmoReleased ammoR){
		this.ammoR = ammoR;
		this.index = World.ammoActiveCount;
		previous = null;
	}

	public void append(AmmoReleased ammoR){
		Node toAppend = new Node(ammoR);
		toAppend.previous = World.ammoActiveLast;
		World.ammoActiveLast = toAppend;
	}

	public AmmoReleased get(int index){
		Node n = World.ammoActiveLast;
		for(int i = World.ammoActiveCount; i > index; i--){
			n = n.previous;
		}

		AmmoReleased toReturn = n.ammoR;
		return toReturn;
	}

	public Node getNode(int index){
		Node toReturn = World.ammoActiveLast;
		for(int i = World.ammoActiveCount; i > index; i--){
			toReturn = toReturn.previous;
		}

		return toReturn;
	}

	public void remove(int index){
		if(index == World.ammoActiveCount - 1){
			World.ammoActiveLast = World.ammoActiveLast.previous;
		}
		else if(index == 0){
			World.ammoActiveLast.getNode(1).previous = null;
		}
		else{
			World.ammoActiveLast.getNode(index + 1).previous = World.ammoActiveLast.getNode(index - 1);
		}
	}

}*/
