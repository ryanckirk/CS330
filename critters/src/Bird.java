import java.awt.Color;
import java.util.Random;
/************************************************
 * Bird class - demonstrates sub-class of Critter
 * 		for the Lab30 Critter Simulation.
 * 
 * @author Steven.Hadfield
 *
 */
public class Bird extends Critter {

	private Random rand;
	private static String name = "Bird";
	private static Color color = Color.BLUE;
	
	/***********************************
	 * Bird constructor - for position
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Bird(int xPos, int yPos) {
		super(xPos, yPos);
		this.size = 2;
		this.moveStep = 0;
		this.rand = new Random();
	}

	///////////////// getter methods ///////////////
	
	public String getName() { return Bird.name; }
	public Color getColor() { return Bird.color; }
	
	/*************************************************************
	 * move() - provides the abstract move() method from Critter
	 * 
	 * @see Critter#move()
	 */
	@Override
	public void move() {
		
		// move a new direction each step
		
		int curDir = rand.nextInt(4);
		
		// move based upon current direction
		
		switch (curDir) {
		case 0: this.setyPos(this.getyPos()-1); break;
		case 1: this.setyPos(this.getyPos()+1); break;
		case 2: this.setxPos(this.getxPos()+1); break;
		case 3: this.setxPos(this.getxPos()-1); break;
		}
		
	} // end move()

} // end Bird class
