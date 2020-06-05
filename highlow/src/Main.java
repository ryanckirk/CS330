import java.awt.*;
import java.util.*;
import java.lang.*;


public class Main {

    public static int maxRange = 10;
    //public static int targetNumber = (int) (Math.random()*maxRange + 1);


    /**THis is the main function that runs the High Low game.
     * I have removed a lot of the functionality from the main into other sub functions
     * Here important variables are initialized like the number of games played and the number of guesses for that
     * specific game.
     * @param args
     */
    public static void main(String[] args) {
        int numGuesses = 0;
        int totalGuess = 0;
        int numGames = 0;
        int optimalNum;
        optimalNum = (int) (Math.log(maxRange)/Math.log(2));
        double userAverage =0;

        // write your code here
        //System.out.println("Hello World!\n");

        String userName = new String(playerName());

        int gameStatus;

        do {
            numGuesses = 0;
            int targetNumber = (int) (Math.random() * maxRange + 1);
            numGuesses = playGame(targetNumber, userName);
            System.out.println(userName +", It took you " + numGuesses +" tries to find the mystery number. ");
            totalGuess+=numGuesses;
            numGames++;
            gameStatus = continuePlaying();


        } while (gameStatus == 1);
        System.out.println(userName +", you played a total of " + numGames +" games with a total of "+totalGuess+" guesses.");
        userAverage = totalGuess / numGames;
        System.out.println(userName +", it took on average " + (double) totalGuess/numGames +" guesses to find the mystery number.");
        System.out.println(userName +", The optimal number of guesses was " + optimalNum +".");
        optimalNum = optimalNum*numGames;


        endGame(userName,optimalNum,userAverage);
    }

    /**
     * THis is the function that drives the game.
     * While the game status does not equal one (exit condition) the High Low game continues to be played
     * It has nested while loops to allow for multiple guesses and for multiple plays of the game.
     *
     *
     * @param targetNumber This is the mystery number that has been created at the start of the round
     * @param playerName This is the user input name generated at the start of the program
     */

    public static int playGame(int targetNumber, String playerName) {

        int gameStatus = 0;
        int numGuess = 0;
        int guess;
        int validGuess = 0;
        int lastGuess =0;

        System.out.println("Enter guess between 1 and " + maxRange);

        do {

            Scanner input = new Scanner(System.in);
            do {
                System.out.println("Enter guess " + playerName + ":");
                guess = input.nextInt();
                if (guess-guess==0){
                    validGuess = 1;

                }

            }while(validGuess!=1);

            numGuess++;
            if (numGuess==0){
                lastGuess = guess;
            }


            if (guess > 0 && guess <= maxRange) {

                gameStatus = highLow(guess, targetNumber, playerName, lastGuess);


            } else {
                System.out.println("Input out of bounds " + playerName + ". Please enter between 1 and " + maxRange);
            }
            lastGuess = guess;


        } while (gameStatus != 1);
        return numGuess;

    }

    /**
     * This function contains the logic for the high low game
     * It takes the user input number ("guess") and compares it against the target number.
     * It returns a zero if the guess was lower than the target number
     * It returns a two if the guess was higher that the target number
     * It returns a one if  the guess matches the target number
     * @param guess this is the number that the user inputted
     * @param targetNumber this is the mystery number the user is trying to guess
     * @param playerName this is the user inputted name ( Player 1 if no input)
     * @param lastGuess used to evaluate whether or not the users guesss is headed in the right direction
     * @return returns the int value for the playGame function
     */
    public static int highLow(int guess, int targetNumber, String playerName, int lastGuess) {
        //returns 1 for correct answer
        //0 for low
        //2 for high
        int previousGuess;
        int result;
        if (guess == targetNumber) {
            System.out.println("You got it " + playerName + "!");
            result = 1;
        } else if (guess > targetNumber) {
            if (guess> lastGuess){
                System.out.println("Guess was even higher then last guess " + playerName + "! Try a lower number.");
            }
            else {
                System.out.println("Guess was too high " + playerName + "!");
            }
            result = 2;
        } else {
            if(guess < lastGuess){
                System.out.println("Guess was even lower then last guess " + playerName + "! Try a higher number.");
            }
            else {
                System.out.println("Guess was low " + playerName + "!");
            }
            result = 0;
        }


        return result;
    }

    /**
     * This function allowd for the user to play multiple games.
     * Depending on the input of the user, the game session continues untill the user inputs "no" or "n"
     * Returns a 1 if the user wants to keep playing
     * Returs a 0 and the game/ program will end
     *
     * @return returns a zero when the user wants to keep playing and a 1 when they want the game session to end
     */

    public static int continuePlaying() {
        int validInput = 0;

        int returnVal = 0;
        System.out.println("Would you like to play another game?\n");
        //System.out.println("Enter 'yes' to play again. Enter 'no' to quit game\n");
        Scanner input = new Scanner(System.in);
        //int playerInput = input.nextInt();

        //playerInput.toUpperCase();

        do {
            System.out.println("Enter 'yes' to play again. Enter 'no' to quit game\n");
            String playerInput = new String(input.nextLine());
            playerInput = playerInput.toUpperCase();
            if ((playerInput.compareTo("YES") == 0) || (playerInput.compareTo("Y")) == 0) {
               returnVal = 1;
               validInput = 1;
            }
            else if ((playerInput.compareTo("NO") == 0) || (playerInput.compareTo("N")) == 0) {
                returnVal = 0;
                validInput = 1;

            }

        }while(validInput == 0);


        return returnVal;

    }

    /**
     * Function that primpts the user to enter there name
     * @return the user inputed name (or Player1 if blank). This value is used throught the function
     */

    public static String playerName() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Name:");
        String userName = new String(input.nextLine());
        if (userName.isBlank()) {
            userName = "Player 1";
        }
        System.out.println("User Name: " + userName);
        return userName;

    }

    /**
     * This function handles the end game logic.
     * In addition to saying goodbye to the user, it sees how close the user was to the optimal number of guesses
     *
     * @param playerName player name
     * @param optimalNum this is the value of log base 2 of x where x is the max number allowed in the program
     * @param userAverage this is the average number of user guesses over the course of the game
     *                    These values are compared to if the user was better than or worse than the optimal number of guesses
     */
    public static void endGame(String playerName, int optimalNum,double userAverage) {

        if (((double)optimalNum -userAverage) > 2){
            System.out.println("You were more than two under the optimal number of guesses " + playerName);
        }
        else if(((double)optimalNum -userAverage) > 1)
        {
            System.out.println("You were under the optimal number of guesses by one " + playerName);
        }
        else if(((double)optimalNum -userAverage) > -1)
        {
            System.out.println("You were @ or above the optimal number of guesses by one " + playerName);
        }
        else{
            System.out.println("You were off the optimal by two or more guesses " + playerName);
        }


        System.out.println("Thank you for playing " + playerName);
        System.out.println("Have a Great Air Force Day!");

    }


}



