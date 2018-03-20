
/* Program to run a game hangman
 * will use a listing of the parts of the hangman that are preseant per round
 */

import java.util.*;
import java.io.*;

public class hangman{

    public static void main(String[] args){
	// Gather words and select one
	//	secretWord = pickWord();
	System.out.println(secretWord);

	// create the display version of the word
	for (int i = 0; i <= secretWord.length(); i++){
	    displayWord += "_ ";
	}

	// Entering th game display rules
	hangmanIntro();

	// begin the game
	startScreen(displayWord);

	// the game loop
	while(keepPlaying) {
	    //player is presented with the situation
	    keepPlaying = displayCurrent(displayWord, guessNum, wrongLetters, rightLetters);

	    if (!keepPlaying) break;
	    // player enters a letter for a guess then check it
	    // if player enters a blank field they means they want to
	    // guess tha answer so will need to process a string
	    guess = keyb.nextLine();
	    if (guess == "") {
		System.out.println("No letter guessed");

		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		clearScreen();
		continue;
	    }
	    char ch = (guess.toLowerCase()).charAt(0);
	    if (Character.isLetter(ch) &&
		guess.length() == 1 &&
		!(wrongLetters.contains(guess.toLowerCase())) &&
		!(rightLetters.contains(guess.toLowerCase()))) {
		guessNum += 1;
		if (secretWord.contains(guess.toLowerCase())) {
		    System.out.println("Letter is in word");
		    displayWord = fixDisplay(displayWord, secretWord, (guess.toLowerCase()).charAt(0));
		    rightLetters += ch;
		} else {
		    System.out.println("Letter not in word");
		    System.out.println("Another limb is added.\n");
		    wrongLetters += ch;
		}
	    }

	    try {
		Thread.sleep(2000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    clearScreen();
	    keepPlaying = checkWord(secretWord, displayWord);
	    
	}

	clearScreen();
	if (checkWord(secretWord, displayWord)){
	    endGameDead();
	} else {
	    endGameAlive();
	}
	System.out.println("The word is: " + secretWord);
    }

    private static boolean checkWord(String sWord, String dWord){
	String word = "";
	for (int i=0; i<dWord.length(); i++){
	    if (Character.isLetter(dWord.charAt(i))){
		word += dWord.charAt(i);
	    }
	}
	if (sWord.equals(word)){
	    return false;
	} else {
	    return true;
	}
    }
    
    private static void endGameDead(){
	System.out.println("\n The game is over and the man is hanged!");
    }

    private static void endGameAlive(){
	System.out.println("\n The game is over and the man is SAVED!");
    }

    
    private static String fixDisplay(String display, String word, char ch){
	String dWord = "";
	// create the new display version of the word
	for (int i=0; i < word.length(); i++) {
	    if (word.charAt(i) == display.charAt(i*2)) {
		dWord += display.charAt(i*2) + " ";
	    } else if (word.charAt(i) == ch) {
		dWord += ch + " ";
	    } else {
		dWord += "_ ";
	    }
	}
	return dWord;
    }
    
    private static boolean displayCurrent(String displayWord,
				       int guessNum,
				       String wrongLetters,
				       String rightLetters){
	System.out.println("The word so far: " + displayWord);
	System.out.println("\nLetters guessed and not in word: " + wrongLetters);
	System.out.println("\nGuess number: " + guessNum);
	return printBody(guessNum, rightLetters);

    }

    private static boolean printBody(int guess, String letters){

	// print out gallows signaling a change
	System.out.println("\n\n ____GALLOWS__");

	// a number 0 through 5
	int body = guess - letters.length();
	switch (body) {
	case 1: System.out.println("\thead");
	    return true;
	case 2: System.out.println("\thead\n\tbody");
	    return true;
	case 3: System.out.println("\thead\n\tbody\n\tArm1");
	    return true;
	case 4: System.out.println("\thead\n\tbody\n\tArm1\n\tArm2");
	    return true;
	case 5: System.out.println("\thead\n\tbody\n\tArm1\n\tArm2\n\tLeg1");
	    return true;
	case 6: System.out.println("\thead\n\tbody\n\tArm1\n\tArm2\n\tLeg1\n\tLeg2");
	    return true;
	case 7: System.out.println("\n\tDEAD MAN\n");
	    return false;
	default: System.out.println("\t|\n\t|\n\tO\n\tDEFAULT\n");
	    return true;
	}
	
    }
    
    private static void startScreen(String word){
	System.out.println("Only the hangmans noose rests upon the");
	System.out.println("lonley gallows. Swaying back and fourth");
	System.out.println("in search of its newest prey.");
	System.out.println("");
	System.out.println("The word to be guessed is: \n\t" + word);
	System.out.println("\n Press Enter to continue");
	keyb.nextLine();
	clearScreen();
	return;
    }
    
    private static void clearScreen(){
	int numRowsInConsole = 60;
	for (int i=0; i<numRowsInConsole; i++)
	    System.out.println("");
	return;
    }
    
    private static void hangmanIntro(){
	//Print out the instruction for the game
	System.out.println("This is a game of hangman");

	// wait for player to hit enter then clear the screen
	System.out.println("Press Enter to start");
	keyb.nextLine();
	clearScreen();
    }

    private static String pickWord(){

	// open file and gather words into an array
	BufferedReader wordFile = null;
	try{
	    wordFile = new BufferedReader(new FileReader(WORD_FILE));
	}catch (IOException ex){
	    System.out.println("BAD FILE");
	}
	    

	// Instanciate the array list to grow with each word added
	ArrayList<String> words = new ArrayList<String>();
	String word=null;

	// Gather the words into the list
	while (true) {
	    try {
		word = wordFile.readLine();
	    } catch (IOException ex) {
		System.out.println("BAD WORD");
	    }
	    if (word == null) break;
	    words.add(word);
	}
	try {
	    wordFile.close();
	} catch (IOException ex) {
	    System.out.println("File not closed");
	}

	// Instanciate random number for selecting word
	Random rand = new Random();

	// return the word and the random number location
	return words.get(rand.nextInt(words.size()));
    }
    
    // Instance variables that will be used throuygh out the prog

    private static String guessWord;
    private static String secretWord = "";
    private static String displayWord = "";
    private static String wrongLetters = "";
    private static String rightLetters = "";
    private static String guess;
    private static boolean keepPlaying = true;
    private static boolean isDead = false;
    private static int guessNum = 0;
    private static Scanner keyb = new Scanner(System.in);
    private static final String WORD_FILE = "word.list";

}
