import java.awt.Color;
import java.util.Random;
/************************************************
 * Frog class - demonstrates sub-class of Critter
 * 		for the Lab30 Critter Simulation.
 * 
 * @author Steven.Hadfield
 *
 */
public class Mouse extends Critter {

	private static String name = "Mouse";
	private static Color color = Color.DARK_GRAY;
	
	/***********************************
	 * Mouse constructor - for position
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Mouse(int xPos, int yPos) {
		super(xPos, yPos);
		this.size = 4;
		this.moveStep = 0;
	}

	///////////////// getter methods ///////////////
	
	public String getName() { return Mouse.name; }
	public Color getColor() { return Mouse.color; }
	
	/*************************************************************
	 * move() - provides the abstract move() method from Critter
	 * 
	 * @see Critter#move()
	 */
	@Override
	public void move() {
		
		// pick new direction when on step 0
		
		if (this.moveStep == 0) {		
			this.setxPos(this.getxPos()-1);
			this.moveStep = 1;
		} else {
			this.setyPos(this.getyPos()-1);
			this.moveStep = 0;
		}
		
	} // end move()

} // end Mouse class
