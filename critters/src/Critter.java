import java.awt.*;
/*************************************************
 * Critter - base class for Critter simulation
 * 
 * @author Steven.Hadfield
 *
 */
public abstract class Critter {

	private int xPos;
	private int yPos;	
	protected int moveStep = 0;   // each move() call is one step
	protected int size;           // radius size for drawing and colocation

	/*********************************************
	 * Critter constructor - default
	 */
	public Critter() {
		this.xPos = 0;
		this.yPos = 0;
		this.moveStep = 0;
	}

	/*********************************************
	 * Critter constructor - sets x & y positions
	 * 
	 * @param xPos
	 * @param yPos
	 */
	public Critter( int xPos, int yPos ) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.moveStep = 0;
	}

	// getter methods
	
	public int getxPos() { return this.xPos; }
	public int getyPos() { return this.yPos; }
	public int getSize() { return this.size; }
	public int getMoveStep() { return this.moveStep; }
	public abstract String getName(); 
	public abstract Color getColor();
	
	// setter methods
	
	public void setxPos(int xPos) { this.xPos = xPos; }
	public void setyPos(int yPos) { this.yPos = yPos; }
	protected void setMoveStep(int step) { this.moveStep = step; }
	
	/*************************************************************
	 * move() - abstract method to be implemented by all Critters
	 */
	public abstract void move();
	
	/*************************************************************
	 * draw() - abstract method to have Critters draw themselves
	 * 
	 * @param g - Graphics2D object to draw on
	 */
	public void draw(Graphics2D g) {
		
		g.setColor(this.getColor());
		g.fillOval(this.xPos-this.size, this.yPos-this.size,
				this.size*2, this.size*2);
	}
	
	/*************************************************************
	 * isColocated - determines if Critter c overlaps position
	 * 		with the current (this) Critter
	 * 
	 * @param c - other critter to check
	 * @return - true if colocated, else false
	 */
	public boolean isColocated( Critter c ) {
		
		double distance = 
				Math.sqrt(Math.pow(this.xPos-c.xPos, 2) + 
						  Math.pow(this.yPos-c.yPos,2));
		
		return distance <= (this.size+c.size);
		
	}
	
	/*****************************************************************
	 * dies() - logs a critter death with reason
	 * 
	 * @param reason - reason for the death
	 */
	public void dies(String reason) {
		
		System.out.println( this.getName() + " dies by " + reason);
		
	}
	
} // end Critter class
