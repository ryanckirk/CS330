import java.awt.Color;
/************************************************
 * Turtle class - demonstrates sub-class of Critter
 * 		for the Lab30 Critter Simulation.
 * 
 * @author Steven.Hadfield
 *
 */
public class Turtle extends Critter {

	private static String name = "Turtle";
	private static Color color = new Color(0,100,0);
	
	/***********************************
	 * Frog constructor - for position
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Turtle(int xPos, int yPos) {
		super(xPos, yPos);
		this.size = 8;
		this.moveStep = 0;
	}

	///////////////// getter methods ///////////////
	
	public String getName() { return Turtle.name; }
	public Color getColor() { return Turtle.color; }
	
	/*************************************************************
	 * move() - provides the abstract move() method from Critter
	 * 
	 * @see Critter#move()
	 */
	@Override
	public void move() {

		if (this.moveStep < 5) {
			this.setyPos(this.getyPos()+1); // south
		} else if (this.moveStep < 10) {
			this.setxPos(this.getxPos()+1);
		} else if (this.moveStep < 15) {
			this.setyPos(this.getyPos()-1);
		} else if (this.moveStep < 20) {
			this.setxPos(this.getxPos()-1);
		}
		
		this.moveStep++;
		
		if (this.moveStep==20)
			this.moveStep = 0;

	} // end move()

} // end Turtle class
