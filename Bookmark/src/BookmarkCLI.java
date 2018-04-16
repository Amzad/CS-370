import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.swing.table.DefaultTableModel;

public class BookmarkCLI {
	static Database db; // Create database
	Scanner input;
	String inputFile = null; // Stores input file name/location
	String outputFile = null; // Stores output name/location

	static FileReader loadFile;
	static BufferedReader readFile;
	static FileWriter makeFile;
	static BufferedWriter writeFile;

	public BookmarkCLI(String[] args) throws CloneNotSupportedException {

		
		URLProcessor handler = new URLProcessor();

		for (int i = 0; i < args.length; i++) {
			if(args[i].startsWith("-i")) {
				inputFile = args[++i];
				
			}
			
			if(args[i].startsWith("-o")) {
				outputFile = args[++i];
			}
			
		}
		
		try {
					makeFile = new FileWriter(outputFile, false);
					writeFile = new BufferedWriter(makeFile);
					loadFile = new FileReader(inputFile);
					readFile = new BufferedReader(loadFile);
					String inputLine = null;
					while ((inputLine = readFile.readLine()) != null) {
						handler.getInfo(inputLine);	
					}
					writeFile.close();
					
					
					
					
				} catch (FileNotFoundException e) {
					System.out.println("File not found");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Error accessing file");
					e.printStackTrace();
				}

		//db = new Database();
		//loadCache();

		/*
		for (int i = 0; i < args.length; i++) { // A loop to cycle through the flags/parameters.
			if (args[i].startsWith("-i")) { // Flag for input
				int index = i;
				index++;

				while (!(args[index].startsWith("-")) | !(index <= args.length)) {

					// If the argument is a URL, process it
					if (args[index].startsWith("http") | (args[index].startsWith("www"))) {
						try {
							URL tempURL;
							tempURL = new URL(args[index]);
							new URLProcessor(tempURL);

						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						index++;
						i++;
						if (index == args.length | i == args.length)
							break;
					} else { // If the argument is a file
						inputFile = args[index]; // The name/filepath of the input file.
						if (inputFile.length() > 0) {
							System.out.println(inputFile);
							readFile(inputFile);
							index++;
							i++;
							if (index == args.length | i == args.length)
								break;
							
						}

					}

					if (args[i].startsWith("-o")) { // Flag for output
						outputFile = args[i++]; // The name/filepath of the output file.
					}
				}
			}
		}*/

		
		//System.out.println("Creating database");
		// Create a new HashMap database with the contents of the input file.
		

		//System.out.println("Welcome to Bookmark! Press H for a list of available options.");
		/*while (true) {
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
						System.out.println("ISBN10:" + isbn + " changed to ISBN10:" + newISBN + " for " + oldBook.getTitle());
						
						
					} else if (option.toLowerCase().equals("n")) {
						System.out.println("Enter the new book title");
						newName = input.nextLine();
						Book oldBook = db.modifyBookName(isbn, newName);
						System.out.println("Title:" + oldBook.getTitle() + " changed to Title:" + newName + " for " + isbn);
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
				writeFile(outputFile);

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


		}*/
		
	}
	/*
	public void readFile(String file) {
		String isbn13, isbn10, title, author, year, publisher, link, pages;;

		// File inputFile = new File(file);
		try {
			loadFile = new FileReader(file); // Load file into the FileReader
			readFile = new BufferedReader(loadFile); // Read file into BufferedReader
			System.out.println("File Found");

			String inputLine; // The current line being read.
			String delimiter = "[|]"; // Book titles rarely have separators.

			// If the line isn't empty, process the data.
			while ((inputLine = readFile.readLine()) != null) {
				String[] info = inputLine.split(delimiter);
				isbn13 = info[0].trim();
                isbn10 = info[1].trim(); // Remove starting and trailing white spaces.
                title = info[2].trim(); // Remove starting and trailing white spaces.
                author = info[3].trim();
                year = info[4].trim();
                publisher = info[5].trim();
                link = info[6].trim();
                pages = info[7].trim();

				// Create a new Book object with the name and isbn10 number
				Book tempBook = new Book(isbn13, isbn10, title, author, year, publisher, link, pages); 
				

				// Book newBook = new Book(isbn, name, author, year); // Create a new Book
				// object with the name and isbn10 number
				

				if (bookExists(tempBook)) {
					System.out.println("Existing Book found. Skipping book");
				} else {
					String[] data = { tempBook.getISBN13(), tempBook.getTitle(), tempBook.getAuthor() };
					db.add(tempBook); // Add the new Book object to the database.
					System.out.println(tempBook.getTitle() + " added to the database");
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			// e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to access file");
			// e.printStackTrace();
		} finally {
			try {
				readFile.close();
				loadFile.close();
				saveCurrentState();
				System.out.println("Database Updated");
			} catch (IOException e) {
				System.out.println("Unable to close file");
				// e.printStackTrace();
			} catch (NullPointerException e) {
				// Unable to close file that was never found
			}

		}
		
	} // END:readFile

	public static void writeFile(String outputFile) {
		try {
			makeFile = new FileWriter(outputFile);
			writeFile = new BufferedWriter(makeFile);
			HashMap hmap = db.getDatabase();
			Iterator it = hmap.entrySet().iterator();
			while (it.hasNext()) {

				Map.Entry pair = (Map.Entry) it.next();
				Book temp = (Book) pair.getValue();

				writeFile.write(temp.getISBN10() + "|" + temp.getTitle());
				writeFile.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writeFile.close();
				makeFile.close();
				System.out.println("Output file written to " + outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	} // END: writeFile
	
	public static void loadCache() {

        String file = "database.txt";
        try {

            String isbn13, isbn10, title, author, year, publisher, link, pages;

            loadFile = new FileReader(file); // Load file into the FileReader
            readFile = new BufferedReader(loadFile); // Read file into BufferedReader
            System.out.println("Cache Found. Loading Cache");

            String inputLine; // The current line being read.
            String delimiter = "[|]"; // Book titles rarely have separators.

            // If the line isn't empty, process the data.
            
            while ((inputLine = readFile.readLine()) != null) {
                String[] info = inputLine.split(delimiter);
                isbn13 = info[0].trim();
                isbn10 = info[1].trim(); // Remove starting and trailing white spaces.
                title = info[2].trim(); // Remove starting and trailing white spaces.
                author = info[3].trim();
                year = info[4].trim();
                publisher = info[5].trim();
                link = info[6].trim();
                pages = info[7].trim();

                Book tempBook = new Book(isbn13, isbn10, title, author, year, publisher, link, pages); // Create a new Book object with the name and isbn10 number
                db.add(tempBook); // Add the new Book object to the database.
                //String[] data = {tempBook.getISBN13(), tempBook.getISBN10(), tempBook.getTitle(), tempBook.getAuthor(), tempBook.getYear(), tempBook.getPublisher(), tempBook.getLink(), tempBook.getPages()};
                String[] data = {tempBook.getISBN13(), tempBook.getTitle(), tempBook.getAuthor()};
                
                //gui.addRow(data);
            }
            System.out.println("Cache loaded");


        } catch (FileNotFoundException e) {
        	System.out.println("Cache not found.");
        } catch (IOException e) {
        	System.out.println("Unable to read or write to file.");
        }

        finally {
            try {
                readFile.close();
                loadFile.close();
            } catch (IOException e) {
            	System.out.println("Unable to close files.");
            } catch (NullPointerException e) {
            	System.out.println("Unable to find cache");
            }

        }
    }
	
	public static boolean bookExists(Book checkBook) {
    	boolean exists = false;
    	if (db.ifExists(checkBook.getISBN13())) {
    		exists = true;
    	}
    	return exists;
    }
	
	 public static void saveCurrentState() {
	        System.out.println("Starting state save.");

	        try {
	        	//File check = new File()
	            makeFile = new FileWriter("database.txt", false);
	            writeFile = new BufferedWriter(makeFile);


	            HashMap<String, Book> hmap = db.getDatabase();

	            Iterator<Entry<String, Book>> it = hmap.entrySet().iterator();
	            while (it.hasNext()) {
	                Map.Entry pair = (Map.Entry)it.next();
	                Book temp = (Book) pair.getValue();
	                //gui.print(temp.getTitle());
	                writeFile.write(temp.getISBN13() + "|" + 
	                        temp.getISBN10() + "|" +
	                        temp.getTitle() + "|" +
	                        temp.getAuthor() + "|" +
	                        temp.getYear() + "|" +
	                        temp.getPublisher() + "|" +
	                        temp.getLink() + "|" +
	                        temp.getPages()
	                        );
	                writeFile.newLine();
	                
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        finally {
	            try {
	                writeFile.close();
	                makeFile.close();
	                System.out.println("Current state saved");
	            } catch (IOException e) {
	            	 System.out.println("Unable to access file");
	                System.out.println(e);
	            }


	        }
	    }*/

}
