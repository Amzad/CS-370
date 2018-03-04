import java.util.Scanner;

public class BookmarkCLI {
	Database db; // Create database
	Scanner input;
	String inputFile = null; // Stores input file name/location
	String outputFile = null; // Stores output name/location

	public BookmarkCLI(String[] args) {



		for (int i = 0; i < args.length; i++) { // A loop to cycle through the flags/parameters.

			if (args[i].startsWith("-i")) {  // Flag for input
				inputFile = args[++i]; // The name/filepath of the input file.
			}

			if (args[i].startsWith("-o")) {  // Flag for output
				outputFile = args[i++]; // The name/filepath of the output file.
			}

		}

		System.out.println("Creating database");
		// Create a new HashMap database with the contents of the input file.
		db = new Database(inputFile);

		System.out.println("Welcome to Bookmark! Press H for a list of available options.");
		while (true) {
			System.out.println("Enter a command");
			input = new Scanner(System.in); // Start scanner
			String command = input.next(); // Get command

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
				String isbn; // Sting to store the new isbn10 number
				String name; // Sting to store the new book title.
				System.out.println("Enter the ISBN10 of the item");
				isbn = input.next();
				input.nextLine();

				System.out.println("Enter the name of the book");
				name = input.nextLine();

				db.newBook(isbn, name);
			} 

			else if (command.toLowerCase().equals("m")) { // Check for M
				String isbn;
				String option;
				String newISBN;
				String newName;

				System.out.println("Enter the current ISBN10 to modify");
				isbn = input.next();
				input.nextLine();
				if (!db.ifExists(isbn)) {
					System.out.println("A book containing ISBN10: " + " doesn't exist.");
				} else {
					System.out.println("Pick an option \n I - Change ISBN10 Number \n N - Change book name");
					option = input.next();
					input.nextLine();
					if (option.toLowerCase().equals("i")) {
						System.out.println("Enter the new ISBN10 number");
						newISBN = input.next();
						input.nextLine();
						
						
						Book oldBook = db.modifyBookISBN(isbn, newISBN);
						System.out.println("ISBN10:" + isbn + " changed to ISBN10:" + newISBN + " for " + oldBook.getbookName());
						
						
					} else if (option.toLowerCase().equals("n")) {
						System.out.println("Enter the new book title");
						newName = input.nextLine();
						Book oldBook = db.modifyBookName(isbn, newName);
						System.out.println("Title:" + oldBook.getbookName() + " changed to Title:" + newName + " for " + isbn);
					} else {
						System.out.println("Invalid input. Try again.");
					}
				}
			}



			else if (command.toLowerCase().equals("d")) { // Check for D
				System.out.println("Enter the ISBN10 of the item");
				String isbn;
				isbn = input.next();
				db.remove(isbn);
			} 

			else if (command.toLowerCase().equals("o")) { // Check for O
				if (outputFile == null) {
					System.out.println("No valid parameter found for output. Please enter a file name followed by .txt");
					outputFile = input.next();
				}
				db.writeFile(outputFile);

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

}
