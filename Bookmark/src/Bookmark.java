/**
 * Bookmark
 * A program used to manage a collection of books.
 * 
 * @author Amzad
 */


public class Bookmark {

	/** 
	 * This is the main method. It is used to start up the program. If the length of the args String array is greater than 0,
	 * the command line interface will start up.
	 * @param args Flags passed in the command line during startup.
	 */
	public static void main(String[] args) {
		
		args = new String[2]; // Fake cli args
		args[0] = "-i";
		args[1] = "input.txt";
		
		if (args.length >= 0) { 
			new BookmarkCLI(args); // Start the CLI if arguments/flags are present.
		} else {
			new GUI(); // Start the GUI is no arguments/flags are found.
		}
	}

}
