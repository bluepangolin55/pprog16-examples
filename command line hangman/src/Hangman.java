import java.util.Scanner;

public class Hangman {

// ---- main method ----
	public static void main(String[] args) {
		Hangman game = new Hangman("Programming");
		game.start();
	}
	
// ---- fields ----	
	
	/** The word to be guessed as an array of characters*/
	private char[] word;

	/** An array that indicates which character is visible (has been guessed)*/
	private boolean visible[];

	/** An array that indicates which character is visible (has been guessed)*/
	private int lives;
	private boolean correct;
	private Scanner in;
// ---- methods ----
	
	// -- constructor -- 
	public Hangman(String word){
		this.word = word.toCharArray();
		this.visible = new boolean[word.length()];
		this.lives = 5;
		this.correct = false;
		this.in = new Scanner(System.in);

	}
	
	public void start(){
		System.out.println("Hi, welcome to Hangman");
		System.out.println("Try to guess the word!");

		// game loop
		while(lives > 0 && !correct){
			System.out.println("Guess a character!");
			char input = in.next().charAt(0);

			// print the guessed characters
			for(int i=0; i<word.length; i++){
				if(word[i] == input){
					visible[i] = true;
				}
				if(visible[i]){
					System.out.print(word[i]);
				}
				else{
					System.out.print('_');
				}
				System.out.print(' ');
			}
			System.out.println("");

		}

	}


}
