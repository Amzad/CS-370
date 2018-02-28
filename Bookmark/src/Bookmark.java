/**
 * Bookmark
 * A program used to manage a collection of books.
 * 
 * @author Amzad
 * @version Phase 1
 * @Date 2-28-2018
 */


public class Bookmark {

	/** 
	 * This is the main method. It is used to start up the program. If the length of the args String array is greater than 0,
	 * the command line interface will start up.
	 * @param args Flags passed in the command line during startup.
	 */
	public static void main(String[] args) {
		
		if (args.length > 0) { 
			new BookmarkCLI(args); // Start the CLI if arguments/flags are present.
		} else {
			new GUI(); // Start the GUI is no arguments/flags are found.
		}
	}

}
