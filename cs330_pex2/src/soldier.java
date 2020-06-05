import java.awt.*;

/**
 * Class that creates the soldiers
 * It is important because each army is made up of soldiers
 * In future pexes sublcalsses could be made so their are different types of soldiers.
 */
public class soldier  {
    public Vector330 location;
    Color color;
    String soldierName;
    boolean redTeam;
    boolean alive;
    //parameters of the soldiers
    //The higher these values, the better they do in battle.
    public int courage;
    public int stamina;
    public int speed;
    public int boost;
    public int size = 7;
    public int strength;
    private static Graphics2D g = null;


    /**
     * Function to create a soldier
     * Gives the soldier its inital starting location.
     * Locations are based off the Vector330 class
     *
     * @param xPos
     * @param yPos
     */
    public void soldier (int xPos, int yPos){
        Vector330 location = new Vector330(xPos,yPos);

    }

    /**
     * Constructor if no inital coordinates are given to the soldier.
     */
    public void soldier() {
        Vector330 location = new Vector330(0,0);
        //this.location.setyVector(0);
        //this.location.setxVector(0);
    }

    /**
     * Function to see if the soldier is within striking distance of an enemy soldier
     * @param enemy enemy that it is checking to see if it is within striking ddistance of.
     * @return a boolean if it is in range or not.
     */

    public boolean isColocated(soldier enemy){
        //Calculates the distance between the two soldiers.
        //If it is within the sizes of the two soldiers, they can begin fighting each other
        double distance =
                Math.sqrt(Math.pow(this.location.getxVectorInt()-enemy.location.getxVectorInt(), 2) +
                        Math.pow(this.location.getyVectorInt()-enemy.location.getyVectorInt(),2));

        return distance <= (this.size+enemy.size);
    }




    //getter methods
    public int getxPos() { return this.location.getxVectorInt(); }
    public int getyPos() { return this.location.getyVectorInt(); }


    // setter methods

    public void setxPos(int xPos) { this.location.setxVector(xPos); }
    public void setyPos(int yPos) { this.location.setyVector(yPos); }
    //protected void setMoveStep(int step) { this.moveStep = step; }

    /**
     * Function that draws the soldier to the battlefield or window
     * Uses the color of the soldier.
     * @param g
     */
    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fillOval(this.location.getxVectorInt()-this.size, this.location.getyVectorInt()-this.size,
                this.size*2, this.size*2);
    }


}
