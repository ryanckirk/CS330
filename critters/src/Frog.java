import java.awt.Color;
import java.util.Random;
/************************************************
 * Frog class - demonstrates sub-class of Critter
 * 		for the Lab30 Critter Simulation.
 * 
 * @author Steven.Hadfield
 *
 */
public class Frog extends Critter {

	private Random rand;
	private int curDir;
	private static String name = "Frog";
	private static Color color = Color.green;
	
	/***********************************
	 * Frog constructor - for position
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Frog(int xPos, int yPos) {
		super(xPos, yPos);
		this.size = 5;
		this.moveStep = 0;
		this.rand = new Random();
	}

	///////////////// getter methods ///////////////
	
	public String getName() { return Frog.name; }
	public Color getColor() { return Frog.color; }
	
	/*************************************************************
	 * move() - provides the abstract move() method from Critter
	 * 
	 * @see Critter#move()
	 */
	@Override
	public void move() {
		
		// pick new direction when on step 0
		
		if (this.moveStep == 0) {		
			curDir = rand.nextInt(4);

		}
		
		// move based upon current direction
		
		switch (curDir) {
		case 0: this.setyPos(this.getyPos()-1); break;
		case 1: this.setyPos(this.getyPos()+1); break;
		case 2: this.setxPos(this.getxPos()+1); break;
		case 3: this.setxPos(this.getxPos()-1); break;
		}
		
		// move to next step
		
		this.moveStep++;
		
		// reset move steps every three moves
		
		if (this.moveStep == 3)
			this.moveStep = 0;

	} // end move()

} // end Frog class
