import java.awt.Point;
import java.util.Scanner;

public class Hangman {

	/** This is the main method of our program. When the program starts, only
	 * this method is invoked. It is static because it is not attached 
	 * to an instance of the class Hangman. 
	 */
	public static void multiplyByScalar(Vector v, double s){
		v = new Vector(v.x*s, v.y*s, v.z*s);
	}
	
	public static void main(String[] args) {
		Vector v = new Vector(1,2,3);
		multiplyByScalar(v, 2.0);;
		System.out.println(v.x);
		
		int[] A = new int[10];
		int[] B = new int[10];
		for(int i = 0; i<10; i++){
		    A[i] = i+1;
		}
		for(int i = 0; i<10; i++){
		    B[A[i]] = A[i];
		}

		Object o;
		Hangman h;
		o = new Object();
		h = new Hangman("hi");
		o = h;
		System.out.println(o);
		System.out.println(h);
		// create a new instance of our game with the parameter "Programming"

		//Hangman game = new Hangman("Programming");

		// run the method start() on our newly initialised game
		//game.start();
	}
	

// ---- fields ----	
	
	/** The word to be guessed as an array of characters*/
	private char[] word;

	/** An array that indicates which character is visible (has been guessed)*/
	private boolean visible[];

	/** The amount of lives a player has */
	private int lives;

	/** Is true if all characters have been guessed and false otherwise */
	private boolean solved;
	
	/** A helper object that reads the user input from the console */
	private Scanner in;
	
	
// ---- methods ----
	
	/**  Constructor - This is what gets executed when we create an object of this class. 
	 * 		Here "this" is used because we have two variables called "word". 
	 * 		"word" refers to the method argument and "this.word" refers
	 * 		to the field of the object. 
	 * 	In our constructor we generally want to initialise the object. 
	 *  Often this means initialising the fields of the object. */
	public Hangman(String word){
		this.word = word.toCharArray();
		visible = new boolean[word.length()];
		lives = 5;
		solved = false;
		in = new Scanner(System.in);
	

	}
	
	@Override
	public String toString() {
		return "Hangman";
	}
	
	public void start(){
		System.out.println("Hi, welcome to Hangman");
		System.out.println("Try to guess the word!");

		// main game loop - we run this as long as the game is active
		while(lives > 0 && !solved){
			System.out.println("Guess a character!");
			char input = in.next().charAt(0); // reads the user input
			boolean correctGuess = false; // whether the user guessed correctly this turn

			int numberOfGuessedChars = 0; // the number of already guessed characters
			for(int i=0; i<word.length; i++){
				if(word[i] == input){
					visible[i] = true;
					correctGuess = true;
				}
				if(visible[i]){
					System.out.print(word[i]);
					numberOfGuessedChars ++;
				}
				else{
					System.out.print('_');
				}
				System.out.print(' ');
			}
			System.out.println(""); // print a new line

			if(numberOfGuessedChars == word.length){
				solved = true;
			}
			
			if(!correctGuess){
				lives --;
			}
		}
		
		if(solved){
				System.out.println("Great, you won the game!");
		}
		else{
				System.out.println("You have used up all your lives :(");
		}

	}

}
