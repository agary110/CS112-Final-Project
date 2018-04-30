public class Node{
	Item item;
	Node previous;

	public Node(){
		item = null;
		previous = null;
	}

	public Node(Item item){
		this.item = item;
		previous = null;
	}

	public Node append(Item toAppend){
		if(World.itemsActive.item == null){
			World.itemsActive.item = toAppend;
			return World.itemsActive;
		}
		else{
			Node toReturn = new Node(toAppend);
			toReturn.previous = World.itemsActive;
			return toReturn;
		}
	}
}
