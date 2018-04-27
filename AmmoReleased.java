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
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, length);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, length);
	}

	public void update(Node node){
		this.y--;

		if(this.y < -length){
			this.deactivate(node.index);
		}

		if(World.ammoActiveCount == 0){
			World.ammoReleased = false;
		}
	}

	public static void activate(){
		AmmoReleased newAmmoReleased = new AmmoReleased((int)(World.marble.position.x) + World.marble.radius / 2, (int)(World.marble.position.y));
		if(World.ammoActiveCount == 0){
			Node n = new Node(newAmmoReleased);
			World.ammoActiveLast = n;
		}
		else{
			World.ammoActiveLast.append(newAmmoReleased);
		}
		World.ammoCount--;
		World.ammoActiveCount++;
	}

	public static void deactivate(int index){
		World.ammoActiveLast.remove(index);
		World.ammoActiveCount--;
	}

}

class Node{

	AmmoReleased ammoReleased;
	Node previous;
	int index;

	public Node(AmmoReleased ammoReleased){
		this.ammoReleased = ammoReleased;
		this.index = World.ammoActiveCount;
	}

	public void append(AmmoReleased ammoReleased){
		Node toAppend = new Node(ammoReleased);
		toAppend.previous = World.ammoActiveLast;
		World.ammoActiveLast = toAppend;
	}

	public AmmoReleased get(int index){
		int j;
		Node n = World.ammoActiveLast;
		for(int i = World.ammoActiveCount; i > index; i--){
			n = n.previous;
		}

		AmmoReleased toReturn = n.ammoReleased;
		return toReturn;
	}

	public Node getNode(int index){
		int j;
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

}
