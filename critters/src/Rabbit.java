import java.awt.Color;
/************************************************
 * Rabbit class - demonstrates sub-class of Critter
 * 		for the Lab30 Critter Simulation.
 * 
 * @author Steven.Hadfield
 *
 */
public class Rabbit extends Critter {

	private int curDir;
	private static String name = "Rabbit";
	private static Color color = Color.LIGHT_GRAY;
	
	/***********************************
	 * Frog constructor - for position
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Rabbit(int xPos, int yPos) {
		super(xPos, yPos);
		this.size = 7;
		this.moveStep = 0;
	}

	///////////////// getter methods ///////////////
	
	public String getName() { return Rabbit.name; }
	public Color getColor() { return Rabbit.color; }
	
	/*************************************************************
	 * move() - provides the abstract move() method from Critter
	 * 
	 * @see Critter#move()
	 */
	@Override
	public void move() {
		
		// move based upon step number
		
		switch (this.moveStep) {
		case 0: this.setyPos(this.getyPos()-1); break;
		case 1: this.setyPos(this.getyPos()-1); break;
		case 2: this.setxPos(this.getxPos()+1); break;
		case 3: this.setxPos(this.getxPos()+1); break;
		case 4: this.setyPos(this.getyPos()+1); break;
		case 5: this.setyPos(this.getyPos()+1); break;
		}
		
		// move to next step
		
		this.moveStep++;
		
		// reset move steps every three moves
		
		if (this.moveStep == 6)
			this.moveStep = 0;

	} // end move()

} // end Rabbit class
