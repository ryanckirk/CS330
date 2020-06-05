import java.awt.*;
import java.awt.*;
import java.util.Random;

/**
 * @author Ryan Kirk
 *
 * This is the main class for my battle simulator pex.
 *
 *
 */

/**
 * The public class main starts the program off.
 */
public class main {

    private static final int WINDOW_SIZE = 600;

    /**
     * The main function has the job of calling the all important battle function.
     * This function is located in the battleSimulation class
     * @param args
     */
    public static void main(String[] args) {
        battleSimulation.doBattle();

    }

}
