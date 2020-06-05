import java.awt.Color;
/************************************************
 * Snake class - demonstrates sub-class of Critter
 * 		for the Lab30 Critter Simulation.
 * 
 * @author Steven.Hadfield
 *
 */
public class Snake extends Critter {

	private int slitherLength;
	private int slitherDirection;
	private static String name = "Snake";
	private static Color color = Color.BLACK;
	
	/***********************************
	 * Black constructor - for position
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Snake(int xPos, int yPos) {
		super(xPos, yPos);
		this.size = 2;
		this.moveStep = -1;
		this.slitherLength = 1;
		this.slitherDirection = 0;
	}

	///////////////// getter methods ///////////////
	
	public String getName() { return Snake.name; }
	public Color getColor() { return Snake.color; }
	
	/*************************************************************
	 * move() - provides the abstract move() method from Critter
	 * 
	 * @see Critter#move()
	 */
	@Override
	public void move() {
		
		// pick new direction when on step 0
		
		if (this.moveStep == -1) {	// move south	
			this.setyPos(this.getyPos()+1);
			this.moveStep++;
		} else { 
			if (slitherDirection == 0) {
				this.setxPos(this.getxPos()+1); // east
			} else {
				this.setxPos(this.getxPos()-1); // west
			}			
			this.moveStep++;
			if (this.moveStep == this.slitherLength) {
				this.moveStep = -1; // start new slither move
				this.slitherLength++;
				this.slitherDirection = (this.slitherDirection==0)? 1 : 0;
			}
		}
	} // end move()

} // end Snake class
