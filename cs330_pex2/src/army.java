import java.awt.*;
import java.util.Random;

/**
 * Class that contains the structure for an army
 * An army is made up of soldiers
 * the army class is where the soldiers are created and given their attributes.
 */

public  class army {
    private static final int ARMY_SIZE = 50;
    private static final int leftBounds = 400;
    private static final int rightBounds = 400;
    private static final int blueTeamU = 50;
    private static final int blueTeamL= 150;
    private static final int redTeamU = 550;
    private static final int redTeamL= 450;
    private static final int maxAttribute= 4;
    private static final int maxStrength= 20;




    boolean blueTeam;

    /**
     * this is the function that creates the army
     * THis function is called from the battleSimulation class
     * @param armyVal parameter for either red or blue team
     * @return the completed army
     */
     public static soldier[] createArmy(int armyVal){

        Random rand = new Random();
        soldier[] army = new soldier[ARMY_SIZE];
        //declaeres an army of new soldiers
        //army army = new army;

         //Goes through and initiates a soldier for each spot in the array
        for(int i=0; i<ARMY_SIZE; i++) {
            //Red Team
            if(armyVal == 1) {
                //creates a soldier at index i in the army
                army[i] = new soldier();
                army[i].location = new Vector330(0,0);

                //Set inital location and team
                army[i].redTeam = true;
                army[i].alive = true;
                army[i].color= Color.red;
                //randomly assigns a starting location for the soldier that is in the respective armies "box"
                army[i].location.setxVector(rand.nextInt(leftBounds)+100);
                army[i].location.setyVector(rand.nextInt(100)+50);

                //set attributes of the soldier.
                army[i].courage = rand.nextInt(maxAttribute)+1;
                army[i].speed = rand.nextInt(maxAttribute)+1;
                army[i].strength= rand.nextInt(maxStrength)+10;


            }
            //Blue Team
            else{
                //army[i] = new soldier(rand.nextInt(leftBounds)+200,rand.nextInt(100)+450);
                army[i] = new soldier();
                army[i].location = new Vector330(0,0);
                army[i].redTeam = false;
                army[i].color= Color.blue;
                army[i].location.setxVector(rand.nextInt(leftBounds)+100);
                army[i].location.setyVector(rand.nextInt(100)+450);

                army[i].courage = rand.nextInt(maxAttribute)+1;
                army[i].speed = rand.nextInt(maxAttribute)+1;

                army[i].strength= rand.nextInt(maxStrength)+10;
            }
        }

        return army;

    }

}
