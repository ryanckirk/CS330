import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/**
 * This class actually runs the battle simulation.
 * It runs the logic for the battle simulation.
 * This includes managing the red and blue armies
 */
public class battleSimulation  {
    //Is equal to 1/10 of a second
    //Essentially a pause before the redraw
    private static final int TIME_STEP = 100;
    //Creates a 600x600 windows
    private static final int WINDOW_SIZE = 600;

    /*
    Creates the two armies, red and blue
    each army is actually an array of soldiers
    The value in passed through the function is to represent what team the soldiers should be assigned to
    1 for red army and 0 for the blue army
    */
    static soldier[] redArmy = army.createArmy(1);
    static soldier[] blueArmy = army.createArmy(0);


    /**
     * This is the function where all the logic for the battle takes place.
     * It is called from the main function
     */
    static void doBattle() {
        //Variables to hold the remaining number of soldiers for each army
        int numBlue = 1;
        int numRed = 1;

        //Creates the drawing panel from the Java Drawing panel class
        DrawingPanel panel = new DrawingPanel(WINDOW_SIZE, WINDOW_SIZE);
        panel.setBackground(Color.WHITE);
        Graphics2D g = panel.getGraphics();


        boolean simPaused = true;
        boolean firstTime = true;
        //Function to see if the battle should keep on going
        //The exit condition for the battle is that only one team is left standing or the window is clicked
        while (!panel.mouseClickHasOccurred(DrawingPanel.LEFT_BUTTON)&&((numBlue > 0)&&(numRed > 0))) {
            //Pauses the simulation if they keyboard is pressed
            if (panel.keyHasBeenHit(DrawingPanel.SPACE_KEY)){
                if(!simPaused){
                    simPaused = true;
                }
                else {
                    simPaused = false;
                }
            }
            //Runs the simulation while it is not paused
            if (simPaused) {

                panel.setBackground(Color.WHITE);  // clear old display

                if(firstTime){
                    //first time through the loop.
                    //Draws the armies and asks for the user input to start the simulation
                    drawArmies(g, redArmy, blueArmy);
                    panel.copyGraphicsToScreen();
                    JOptionPane.showMessageDialog( null, "Click 'OK' to begin simulation" ,
                            "User Input Required", INFORMATION_MESSAGE );

                    firstTime = false;
                }
                //Every other time through the loop
                else{
                    //
                    moveSoldiers(redArmy, blueArmy);  // moves moves the soldiers according to their attributes
                    soldiersFight(redArmy, blueArmy); //Allows the soldiers to fight if they are within striking distance
                }
                drawArmies(g, redArmy, blueArmy); //Updates the soldiers 2d drawing now that their locations have changed
                //Gets the number of soldiers standing on both sides
                numBlue = getNumberSoldiers(blueArmy);
                numRed = getNumberSoldiers(redArmy);
                //Updates the header with the remaining soldiers in each army
                panel.setWindowTitle("CS330 Battle Simulation: " +
                        numBlue + " Blue Soldiers alive: " + numRed+ " Red Soldiers Alive");//

                panel.copyGraphicsToScreen();  // update display
                panel.sleep(TIME_STEP);  // delay for time step
            }
        }
        //Checks to see if the blue army or red army one
        if(numBlue>numRed){
            //prints to the display the winning team
            g.drawString("Blue team wins!!",250,250);


        }
        else{
            //panel.setBackground(Color.RED);
            g.drawString("Red team wins!!",250,250);
        }
        g.drawString("Click to exit",250,450);
        panel.copyGraphicsToScreen();
        //waits for the user to click to end the simulation

        while (!panel.mouseClickHasOccurred(DrawingPanel.LEFT_BUTTON)){        }
        panel.closeWindow();  // all done
    }

    /**
     * Function that has the soldiers fight each other if they are within range of each other.
     * As the soldiers fight, they take down their opponents damage.
     * Once their damage equals zero.. they die.
     * @param redArmy the red army array of soldiers
     * @param blueArmy the blue army array of soldiers
     */
    private static void soldiersFight(soldier[] redArmy, soldier[] blueArmy){
        int firstVal = 0;
        int secondVal = 0;

        //Runs through every combination of soldiers on the two armies to make sure none are within strkiking distance
        for (int first = 0; first < redArmy.length; first++){
            //Makes sure it does not try and access a soldier that has allready been killed
            if (redArmy[first] !=null){
                for (int second = 0; second < blueArmy.length; second++) {
                    if (blueArmy[second] != null){
                        if (blueArmy[second] != null) {
                            //Once they are within strinking distance, damage is done to tthe soldiers as they fight
                            if (redArmy[first].isColocated(blueArmy[second])) {
                                if (redArmy[first].strength > blueArmy[second].strength) {
                                    blueArmy[second].strength = blueArmy[second].strength - 5;
                                    redArmy[first].strength = redArmy[first].strength - 3;
                                } else if (redArmy[first].strength < blueArmy[second].strength) {
                                    redArmy[first].strength = redArmy[first].strength - 5;
                                    blueArmy[second].strength = blueArmy[second].strength - 3;
                                } else {
                                    blueArmy[second].strength = blueArmy[second].strength - 5;
                                    redArmy[first].strength = redArmy[first].strength - 5;

                                }
                            }


                            }
                        }
                    secondVal = second;

                }

            }


            firstVal = first;
        }
        //This is the part of the function that kills soldiers
        //If their strength falls below zero, they are "killed"
        for (int i =0; i< blueArmy.length; i++){
            if (blueArmy[i]!=null) {
                if (blueArmy[i].strength <= 0) {
                    blueArmy[i] = null;
                }
            }

        }
        for (int i =0; i< blueArmy.length; i++) {
            if (redArmy[i]!=null) {
                if (redArmy[i].strength <= 0) {
                    redArmy[i] = null;
                }
            }

        }






    }

    /**
     * This function draws the armies and the soldiers to the window.
     * @param g the graphics window
     * @param redArmy
     * @param blueArmy
     */
    private static void drawArmies(Graphics2D g, soldier[] redArmy, soldier[] blueArmy) {

        for(int i=0; i<redArmy.length; i++) {  // for each soldier

            if (redArmy[i] != null) {  // if soldier exists (not dead)
                redArmy[i].draw(g);
            }// draw it

        }

        for(int i=0; i<blueArmy.length; i++) {  // for each soldier

            if (blueArmy[i] != null) {  // if soldier exists ()
                blueArmy[i].draw(g);    // draw it
            }
        }
    }

    /**
     * Function that moves the soldier to their nex coordinate.
     * This function uses the magnitude function from the Vector330 class.
     * Each soldier naturally wants to attack the enemy that is cloeset to it
     * It calculates the magnitude, or distance between itself and each enemy
     * It goes after the one closest to it after each move
     * @param redArmy
     * @param blueArmy
     */

    public static void moveSoldiers(soldier[] redArmy, soldier[] blueArmy) {
        for (int i = 0; i < redArmy.length; i++) {
            if(redArmy[i]!= null) {
                int newX;
                int newY;
                Vector330 magnitude = new Vector330(0, 0);

                int target = 0;
                double maxMagnitude = 500000.0;
                //For loop to cycle through each enemy
                for (int j = 0; j < blueArmy.length; j++) {
                    if(blueArmy[j]!= null){
                    double currMagnitude = 0.0;

                    magnitude.setxVector(Math.abs(redArmy[i].location.getxVectorInt() - blueArmy[j].location.getxVectorInt()));
                    magnitude.setyVector(Math.abs(redArmy[i].location.getyVectorInt() - blueArmy[j].location.getyVectorInt()));
                    //Magnitude calculation for each enemy
                    currMagnitude = magnitude.magnitude();

                    if (currMagnitude < maxMagnitude) {
                        maxMagnitude = currMagnitude;
                        target = j;
                        //Sets the target as the closest enemy
                    }

                }
                }

                //////////////////////X DIRECTION////////////
                //Setts the next x coordinate for the soldier. It uses the closest enemy as a target and uses the
                //Individual speed of the soldier to change direction
                //Faster soldiers can get to the enemy army faster
                if((redArmy[i]!=null)&&(blueArmy[target]!=null)) {
                    if ((redArmy[i].location.getxVectorInt() - blueArmy[target].location.getxVectorInt()) < 0) {
                        redArmy[i].location.setxVector(redArmy[i].location.getxVectorInt() + redArmy[i].speed);
                    } else if ((redArmy[i].location.getxVectorInt() - blueArmy[target].location.getxVectorInt()) == 0) {
                        redArmy[i].location.setxVector(redArmy[i].location.getxVectorInt());
                    } else {
                        redArmy[i].location.setxVector(redArmy[i].location.getxVectorInt() - redArmy[i].speed);
                    }

                    /////////////////////////////Y DIRECTION/////////////////////////
                    //Setts the next x coordinate for the soldier.
                    if ((redArmy[i].location.getyVectorInt() - blueArmy[target].location.getyVectorInt()) < 0) {
                        redArmy[i].location.setyVector(redArmy[i].location.getyVectorInt() + redArmy[i].speed);
                    } else if ((redArmy[i].location.getyVectorInt() - blueArmy[target].location.getyVectorInt()) == 0) {
                        redArmy[i].location.setyVector(redArmy[i].location.getyVectorInt());
                    } else {
                        redArmy[i].location.setyVector(redArmy[i].location.getyVectorInt() - redArmy[i].speed);
                    }
                    if ((redArmy[i].location.getxVectorInt() < 0) || (redArmy[i].location.getxVectorInt() > WINDOW_SIZE)
                            || ((redArmy[i].location.getyVectorInt() < 0) || (redArmy[i].location.getyVectorInt() > WINDOW_SIZE))) {
                        redArmy[i] = null;
                        return;
                    }
                }
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////BLUE ARMY//////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < blueArmy.length; i++) {
            if(blueArmy[i]!= null) {
                int newX;
                int newY;
                Vector330 magnitude = new Vector330(0, 0);

                int target = 0;
                double maxMagnitude = 500000.0;

                for (int j = 0; j < redArmy.length; j++) {
                    if (redArmy[j] != null) {
                        double currMagnitude = 0.0;
                        magnitude.setxVector(Math.abs(blueArmy[i].location.getxVectorInt() - redArmy[j].location.getxVectorInt()));
                        magnitude.setyVector(Math.abs(blueArmy[i].location.getyVectorInt() - redArmy[j].location.getyVectorInt()));

                        currMagnitude = magnitude.magnitude();

                        if (currMagnitude < maxMagnitude) {
                            maxMagnitude = currMagnitude;
                            target = j;
                        }
                    }


                        ///Calculate the new direction to go to the closest target
                        // X direction
                        //newY = (redArmy[i].location.getyVectorInt()) + redArmy[i].speed;


                        //////////////////////X DIRECTION////////////
                    if((blueArmy[i]!=null)&&(redArmy[target]!=null)) {
                        if ((blueArmy[i].location.getxVectorInt() - redArmy[target].location.getxVectorInt()) < 0) {
                            blueArmy[i].location.setxVector(blueArmy[i].location.getxVectorInt() + blueArmy[i].speed);
                        } else if ((blueArmy[i].location.getxVectorInt() - redArmy[target].location.getxVectorInt()) == 0) {
                            blueArmy[i].location.setxVector(blueArmy[i].location.getxVectorInt());
                        } else {
                            blueArmy[i].location.setxVector(blueArmy[i].location.getxVectorInt() - blueArmy[i].speed);
                        }

                        /////////////////////////////Y DIRECTION/////////////////////////

                        if ((blueArmy[i].location.getyVectorInt() - redArmy[target].location.getyVectorInt()) < 0) {
                            blueArmy[i].location.setyVector(blueArmy[i].location.getyVectorInt() + blueArmy[i].speed);
                        } else if ((blueArmy[i].location.getyVectorInt() - redArmy[target].location.getyVectorInt()) == 0) {
                            blueArmy[i].location.setyVector(blueArmy[i].location.getyVectorInt());
                        } else {
                            blueArmy[i].location.setyVector(blueArmy[i].location.getyVectorInt() - blueArmy[i].speed);
                        }
                        //Check if they went off the map

                        if ((blueArmy[i].location.getxVectorInt() < 0) || (blueArmy[i].location.getxVectorInt() > WINDOW_SIZE)
                                || ((blueArmy[i].location.getyVectorInt() < 0) || (blueArmy[i].location.getyVectorInt() > WINDOW_SIZE))) {
                            blueArmy[i] = null;
                            return;
                        }
                    }


                    }
                }
            }

        }


    /**
     * Calculates how many soldiers are alive in a given army
     * @param army passed through army (either red or blue)
     * @return the number of soldiers alive. Simulation ends if one of the armies returns zero
     */
    private static int getNumberSoldiers(soldier[] army) {

        int numAlive = 0;
        for(int i=0; i<army.length; i++) {
            if (army[i] != null){
                numAlive++;
            }
        }

        return numAlive;
    }

}

