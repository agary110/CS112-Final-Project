/*public class Node{
	Item item;
	Node previous;
	boolean isNull;
	boolean prevNull;

	public Node(){
		item = null;
		previous = null;
		isNull = true;
		prevNull = true;
	}

	public Node(Item item){
		this.item = item;
		previous = null;
		isNull = false;
		prevNull = true;
	}

	public Node append(Item toAppend){
		if(World.itemsActive.item == null){
			World.itemsActive.item = toAppend;
			World.itemsActive.isNull = false;
			return World.itemsActive;
		}
		else{
			Node toReturn = new Node(toAppend);
			toReturn.previous = World.itemsActive;
			prevNull = false;
			return toReturn;
		}
	}
}*/