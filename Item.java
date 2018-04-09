import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

class Item{
	public int x;
	public int y;
	public final int width = (int)(path.WIDTH / 3 - 20);
	public boolean activated;
	public boolean passed;

	public Item(int x, int y){
		this.x = x;
		this.y = y;
		passed = false;
	}

	public void update(){
		if(marble.velocity.y == 0);
		else{
			this.y = this.y + speed;
		}
		if(marble.position.y == y){
			this.passed = true;
		}
		if(this.x + this.width / 2 >= marble.position.x && this.x <= marble.position.x + this.width / 2){
			activated = true;
			this.activate();
		}
		if(this.passed == true && this.activated == false) timeUntilNextItem = timeUntilNextItem - (1 / (double)(FPS));
		if(timeUntilNextItem == 0){
			world.item = generateNewItem();
		}
	}

	public void draw(Graphics g){
	}

	public void activate(){
	}
}

class Bomb extends Item{
	public Bomb(int x, int y){
		super(x, y);
	}

	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillOval(this.x, this.y, width, width);
		g.setColor(Color.WHITE);
		g.drawLine(this.x + width / 2, y, this.x + width, this.y - width / 3);
		g.setColor(Color.ORANGE);
		g.drawOval(this.x + width, this.y - width / 3, 3, 3);
	}

	public void activate(){
		if(this.activated == true) alive = false;
	}
}

class Ammo extends Item{
	int increase;

	public Ammo(int x, int y){
		super(x, y);
		Random rand = new Random();
		increase = rand.nextInt(3);
	}

	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(this.x, this.y, width, width);
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, width, width);
		g.setColor(Color.GREY);
		g.fillRect(this.x + width / 2 - width - 2, this.y + width / 10, width - 10, 5);
	}

	public void activate(){
		ammoCount = ammoCount + increase;
		this.activated = false;
		timeUntilNextItem = originalTimeUntilNextItem;
	}
}

class Booster extends Item{
	public double timeActive;

	public Booster(int x, int y){
		super(x, y);
		activated = false;
		timeActive = 5;
	}

	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(this.x, this.y, width, width);
		g.setColor(Color.RED);
		g.drawRect(this.x, this.y, width, width);
		g.drawString("?", this.x + width / 3, this.y + width / 3);
	}

	public void update(){
		super();
		if(this.activated == true) timeActive = timeActive - (1 / (double)(FPS));
		if(timeActive == 0){
			this.deactivate();
		}
	}

	public void deactivate(){
		this.activated = false;
	}
}