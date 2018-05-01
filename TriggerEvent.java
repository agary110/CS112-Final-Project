import java.util.Random;

public class TriggerEvent{

	public TriggerEvent(int randNum){
		randNum = 2;

		//Speed up
		if(randNum == 0){
			Game.FPS += 10;
		}

		//Alien Army
		else if(randNum == 1){
			Random rand = new Random();
			for(int i = 0; i < 3; i++){
				World.itemsActive.add(Item.generateNextItem(4));
				int randYValue = rand.nextInt(Path.WIDTH - Alien.width);
				World.itemsActive.getLast().y += randYValue;
			}
		}

		//Coin Madness
		else{
			Random rand = new Random();
			for(int i = 0; i < 10; i++){
				World.itemsActive.add(Item.generateNextItem(5));
				int randYValue = rand.nextInt(Path.WIDTH - Coin.width);
				World.itemsActive.getLast().y += randYValue;
			}
		}
	}

}