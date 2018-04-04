class Booster{
	public int x;
	public int y;
	public final int width = 15;
	int timeActive;

	public Booster(int x, int y){
		this.x = x;
		this.y = y;
		timeActive = ;//How long should the time-based boosters stay active?
	}

	public void update(){
		if(marble.velocity.y == 0);
		else{
			this.y = this.y + speed;
		}
	}
//=======================================
//Returns a random type of Booster (Bumpers, changeSpeed, changeSize, Bomb, or Ammo)
	public static Booster generateNextBooster(){//Insert Booster subclasses & should be run whenever Booster is passed or collected
		int randNum = rand.nextInt(5);
		if(randNum == 0){
			Bumpers booster = new Bumpers();
		}
		else if(randNum == 1){
			changeSpeed booster = new changeSpeed();
		}
		else if(randNum == 2){
			changeSize booster = new changeSize();
		}
		else if(randNum == 3){
			Bomb booster = new Bomb();
		}
		else{
			Ammo booster = new Ammo();
		}
		int distanceToNext = rand.nextInt(500) + RANGEBASEDONDIFFICULTY;
		booster.y = marble.position.y - distanceToNext;
		booster.x = LEFT-SIDE-OF-PATH-AT-booster.y + rand.nextInt(3) * booster.width;
		return booster;
	}
//=======================================
//Class Bumpers extends Booster
	class Bumpers extends Booster{//needs to be able to keep track of time to “wear off” after given time

		public Bumpers(){
		}

		public void activate(boolean pickedUp){
			if(pickedUp); //Turn on bumpers in the Path class
		}

		public void deactivate(){//Runs when timeActive = 0
			//Turn off bumpers in the Path class
		}
	}
//=======================================
//Class changeSpeed extends Booster
	class changeSpeed extends Booster{//needs to be able to keep track of time to “wear off” after given time
		boolean increase;

		public changeSpeed(){
			boolean rand = new Random();
			increase = rand.nextBoolean();
		}

		public void activate(boolean pickedUp){
			if(pickedUp) map.changeSpeed(increase);
		}

		public void deactivate(){//Runs when timeActive = 0
			map.changeSpeed();
		}
	}
//=======================================
//Class changeSize extends Booster
	class changeSize extends Booster{//needs to be able to keep track of time to “wear off” after given time
		boolean increase;
		double proportion;

		public changeSize(){
			boolean rand = new Random();
			increase = rand.nextBoolean();
			proportion = marble.radius * 0.5;
		}

		public void activate(boolean pickedUp){
			if(pickedUp && increase) marble.radius = marble.radius + proportion;
			else if(pickedUp) marble.radius = marble.radius - proportion;
		}

		public void deactivate(){//Runs when timeActive = 0
			//Revert to original radius. Might have to create another variable in the Marble class that holds the original radius.
		}
	}
//=======================================
//Class Bomb extends Booster
	class Bomb extends Booster{
		public Bomb(){
		}

		public void activate(boolean pickedUp){
			if(pickedUp) alive = false;
		}
	}
//=======================================
//Class Ammo extends Booster
	class Ammo extends Booster{
		int increase;

		public Ammo(){
			Random rand = new Random();
			increase = rand.nextInt(5);
		}

		public void activate(boolean pickedUp){
			if(pickedUp); //increase ammo count by the variable increase
		}
	}
}