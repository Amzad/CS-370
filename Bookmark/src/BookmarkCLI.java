import java.util.ArrayList;
import java.util.Scanner;

public class BookmarkCLI {
	Database db;
	Scanner input;
	String inputFile = null;
	String outputFile = null;
	
	public BookmarkCLI(String[] args0) {

		String[] args = {"-i", "C:\\input.txt"};
		for (int i = 0; i < args.length; i++) {
			//System.out.println(args[i]);
		}
		
		for (int i = 0; i < args.length; i++) { // A loop to cycle through the flags/parameters.
			
			if (args[i].startsWith("-i")) { 
				inputFile = args[++i]; // The name/filepath of the input file.
			}
			
			if (args[i].startsWith("-o")) { 
				outputFile = args[i++]; // The name/filepath of the output file.
			}
			
		}
		
		System.out.println("Creating database");
		// Create a new HashMap database with the contents of the input file.
		db = new Database(inputFile);
		promptOptions();
		
	
	}
	
	public void promptOptions() {
		System.out.println("Welcome to Bookmark! Press H for a list of available options.");
		while (true) {
			System.out.println("Enter a command");
			input = new Scanner(System.in);
			String command = input.next();

			if (command.toLowerCase().equals("h")) {
				System.out.println(" V - Display database items");
				System.out.println(" A - Add new item");
				System.out.println(" M - Modify an item");
				System.out.println(" D - Delete an item");
				System.out.println(" O - Output Database");
				System.out.println(" C - Clear database");
				System.out.println(" E - Exit");
			} 
			
			else if (command.toLowerCase().equals("v")) { // Check for V
				db.printDatabase();
			} 
			
			else if (command.toLowerCase().equals("a")) { // Check for A
				promptAdd();
			} 
			
			else if (command.toLowerCase().equals("m")) { // Check for M
				//prompt();
			} 
			
			else if (command.toLowerCase().equals("d")) { // Check for D
				promptDelete();
			} 
			
			else if (command.toLowerCase().equals("o")) { // Check for O
				promptOutput();
				
			} 
			
			else if (command.toLowerCase().equals("c")) { // Check for C
				System.out.println("Clearing database");
				db.emptyDatabase();
				System.out.println("Database cleared");
			} 
			
			else if (command.toLowerCase().equals("e")) { // Check for E
				System.out.println("Exiting the program");
				System.exit(0); 
			} 
			
			else {
				System.out.println("Invalid input");
			}
			
			
		}	
	}
	
	public void promptDelete() {
		System.out.println("Enter the ISBN10 of the item");
		String isbn;
		isbn = input.next();
		db.remove(isbn);
		
	}
	
	public void promptAdd() {
		String isbn;
		String name;
		System.out.println("Enter the ISBN10 of the item");
		isbn = input.next();
		input.nextLine();

		System.out.println("Enter the name of the book");
		name = input.nextLine();
		
		db.newBook(isbn, name);

	}
	
	public void promptOutput() {
		if (outputFile == null) {
			System.out.println("No valid parameter found for output. Please enter a file name followed by .txt");
			outputFile = input.next();
		}
		db.writeFile(outputFile);
	}

}
