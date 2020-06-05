import java.awt.*;
import java.util.Random;

/******************************************************************
 * CritterSimulation - demonstrates polymorphism with a
 * 		simulation of various types of critters.  Built upon idea
 * 		of BJP Chap 9, Programming Problem 2, p 646-647.
 * 
 * @author Steven.Hadfield
 * 
 */
public class CritterSimulation {

	private static final int SIZE = 600;
	private static final int NUM_CRITTERS = 100;
	private static final int TIME_STEP = 100;

	/***************************************************************
	 * main - sets up and drives the simulation
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// establish the graphics window
		
		DrawingPanel panel = new DrawingPanel(SIZE, SIZE);
		panel.setBackground(Color.WHITE);
		Graphics2D g = panel.getGraphics();

		// generate the animals
		
		Critter[] animals = createAnimals();

		// animate until the left mouse button is clicked
		
		while (!panel.mouseClickHasOccurred(DrawingPanel.LEFT_BUTTON)) {

			panel.setWindowTitle("CS330 Critter Simulation: " +
					getNumberAnimals(animals) + " animals alive");

			panel.setBackground(Color.WHITE);  // clear old display

			moveAnimals(animals);  // moves animals per their motions

			eatOtherAnimals(animals);   // checks for animals eating each other

			drawAnimals(g, animals);  // draw remaining animals

			panel.copyGraphicsToScreen();  // update display
			panel.sleep(TIME_STEP);  // delay for time step
			
		} // end of animation while loop
		
		panel.closeWindow();  // all done
		
	}

	/**********************************************************
	 * createAnimals() - creates the collection of animals
	 * 
	 * @param animals - array of the Critters
	 */
	private static Critter[] createAnimals() {
		
		Random rand = new Random();
		
		Critter[] animals = new Critter[NUM_CRITTERS];
		
		for(int i=0; i<animals.length; i++) {
			
			int choiceOfAnimal = rand.nextInt(7);
			
			switch (choiceOfAnimal) {
			case 0: animals[i] = 
					new Frog(rand.nextInt(SIZE),rand.nextInt(SIZE)); break;
			case 1: animals[i] = 
					new Bird(rand.nextInt(SIZE),rand.nextInt(SIZE)); break;
			case 2: animals[i] = 
					new Mouse(rand.nextInt(SIZE),rand.nextInt(SIZE)); break;
			case 3: animals[i] = 
					new Rabbit(rand.nextInt(SIZE),rand.nextInt(SIZE)); break;
			case 4: animals[i] = 
					new Snake(rand.nextInt(SIZE),rand.nextInt(SIZE)); break;
			case 5: animals[i] = 
					new Turtle(rand.nextInt(SIZE),rand.nextInt(SIZE)); break;
			case 6: animals[i] = 
					new Fox(rand.nextInt(SIZE),rand.nextInt(SIZE)); break;		
			}
		}
		
		return animals;
		
	} // end createAnimals()

	/**********************************************************
	 * moveAnimals() - move existing animals. If they fall off
	 * 		the display, they die.
	 * 
	 * @param animals - array of the Critters
	 */
	private static void moveAnimals(Critter[] animals) {

		for (int i = 0; i < NUM_CRITTERS; i++) {  // loop thru all animals

			if (animals[i] != null) {  // only move existing animals
				
				animals[i].move();  // polymorphic move of each animal
				
				// check for animals falling off display and dying
				
				if ((animals[i].getxPos() < 0)
						|| (animals[i].getxPos() >= SIZE)
						|| (animals[i].getyPos() < 0)
						|| (animals[i].getyPos() >= SIZE)) {
				
					animals[i].dies("falling off of the world");
					animals[i] = null; // animal falls off and dies
				
				}
			}
		} // end for each animal

	} // end moveAnimals()

	/********************************************************************
	 * eatOtherAnimals() - check for colocated animals eating each other
	 * 
	 * @param animals - array of the Critters
	 */
	private static void eatOtherAnimals(Critter[] animals) {

		// loop through first n-1 animals
		
		for (int first = 0; first < animals.length - 1; first++) {
			
			if (animals[first] != null) { // if current animal exists
				
				// check this first animal with all that come after him

				for(int second = first + 1; second < animals.length; second++) {

					if ((animals[first] != null) &&   // first could have died
						(animals[second] != null)) {  // second animal exists
						
						// check for colocated animals, smaller one will die
						
						if (animals[first].isColocated(animals[second])) {

							if (animals[first].getSize() > animals[second]
									.getSize()) {

								animals[second].dies("getting eaten by " + 
											animals[first].getName() );
								animals[second] = null; // second animal dies

							} else {

								animals[first].dies("getting eaten by " + 
										animals[second].getName() );
								animals[first] = null; // first animal dies

							}
						}
					}
				}
			}
		}

	} // end eatOtherAnimals()
	
	/******************************************************************
	 * drawAnimals() - puts existing animals on the display
	 * 
	 * @param g - Graphics2D object to draw upon
	 * @param animals - array of Critters
	 */
	private static void drawAnimals(Graphics2D g, Critter[] animals) {
		
		for(int i=0; i<animals.length; i++) {  // for each animal
			
			if (animals[i] != null) {  // if animal exists (not dead)
				animals[i].draw(g);    // draw it
			}
		}
	} // end drawAnimals
	
	/**********************************************************
	 * getNumberAnimals() - counts the number of animals that
	 * 		are currently alive
	 * 
	 * @param animals - array of critters
	 * @return - count of number of animals that are alive
	 */
	private static int getNumberAnimals(Critter[] animals) {
		
		int numAnimals = 0;
		for(int i=0; i<animals.length; i++) {	
			if (animals[i] != null) numAnimals++;			
		}		
		return numAnimals;		
	}
	
} // end CritterSimulation
