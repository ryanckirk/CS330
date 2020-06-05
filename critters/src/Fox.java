import java.awt.Color;
import java.util.Random;
/************************************************
 * Frog class - demonstrates sub-class of Critter
 * 		for the Lab30 Critter Simulation.
 * 
 * @author Steven.Hadfield
 *
 */
public class Fox extends Critter {

	private Random rand;
	private static String name = "Fox";
	private static Color color = Color.RED;
	
	/***********************************
	 * Fox constructor - for position
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Fox(int xPos, int yPos) {
		super(xPos, yPos);
		this.size = 10;
		this.moveStep = 0;
		this.rand = new Random();
	}

	///////////////// getter methods ///////////////
	
	public String getName() { return Fox.name; }
	public Color getColor() { return Fox.color; }
	
	/*************************************************************
	 * move() - provides the abstract move() method from Critter
	 * 
	 * @see Critter#move()
	 */
	@Override
	public void move() {
		
		// pick new direction when on step 0
		
		int curDir = rand.nextInt(4);
		
		// move based upon current direction
		
		switch (curDir) {
		case 0: this.setyPos(this.getyPos()-20); break;
		case 1: this.setyPos(this.getyPos()+20); break;
		case 2: this.setxPos(this.getxPos()+20); break;
		case 3: this.setxPos(this.getxPos()-20); break;
		}
		
	} // end move()

} // end Fox class
