import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Database {
	HashMap<String, Object> hmap = new HashMap<String, Object>();
	FileReader loadFile;
	BufferedReader readFile;
	FileWriter makeFile;
	BufferedWriter writeFile;
	

	public Database(String file) {
		readFile(file);
	}
	
	/**
	 * Add a new book to the database
	 * @param item Book object to be added
	 */
	public Book add(Book item) {
		hmap.put(item.getISBN(), item);
		return item;
	}
	
	/**
	 * Creates a new book object with the ISBN10 and book name
	 * @param isbn The ISBN10 number of the book
	 * @param name Title of the book
	 */
	public Book newBook(String isbn, String name) {
		Book newBook = new Book(name,isbn);
		add(newBook);
		return newBook;
		
	}
	
	/**
	 * Modifies the ISBN10 number of an existing book
	 * @param isbn The current ISBN10 number
	 * @param newISBN10 The new ISBN10 number
	 */
	public Book modifyBookISBN(String isbn, String newISBN) {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			// Find the isbn10 number to change.
			if (temp.getISBN().equals(isbn)) {
				Book oldBook = new Book(temp.getISBN()); // Get current book object to return for later.
				
				temp.setISBN10(newISBN); // Set new isbn10 number
				return oldBook;
				
			}
		}
		return null;
		
	}
	
	/**
	 * Modifies the book title of a Book
	 * @param isbn ISBN10 number of the book to modify
	 * @param newName New title for the book
	 */
	public Book modifyBookName(String isbn, String newName) {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			// Find the isbn10 number to change.
			if (temp.getISBN().equals(isbn)) {
				Book oldBook = new Book(temp.getbookName(), temp.getISBN()); // Get current book object to return for later.

				temp.setName(newName); // Set new isbn10 number
				//System.out.println("Title:" + oldName + " changed to Title:" + newName + " for " + temp.getISBN());
				return oldBook;
			}
		}
		return null;
	}
	
	/**
	 * Remove a book object
	 * @param isbn ISBN10 number of the book to remove
	 */
	public void remove(String isbn) {

		Book temp = (Book) hmap.remove(isbn);
		System.out.println(temp.getbookName() + " deleted");
		

	}
	
	public boolean ifExists(String isbn) {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			if (temp.getISBN().equals(isbn)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Reads the input txt file and imports the data into the database
	 * @param file The name or location/name of the input file.
	 */
	public void readFile(String file) {
		String name;
		String isbn;
		
		//File inputFile = new File(file);
		try {
			loadFile = new FileReader(file); // Load file into the FileReader
			readFile = new BufferedReader(loadFile); // Read file into BufferedReader
			System.out.println("File Found");
			
			String inputLine; // The current line being read.
			String delimiter = "[|]"; // Book titles rarely have separators.
			
			// If the line isn't empty, process the data.
			while ((inputLine = readFile.readLine()) != null) {
				String[] info = inputLine.split(delimiter);
				isbn = info[0].trim(); // Remove starting and trailing white spaces.
				name = info[1].trim(); // Remove starting and trailing white spaces.
				
				Book tempBook = new Book(name, isbn); // Create a new Book object with the name and isbn10 number
				this.add(tempBook); // Add the new Book object to the database.
				System.out.println(tempBook.getbookName() + " added to the database");
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println(file);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				readFile.close();
				loadFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Database creation completed");
		
	}
	
	/**
	 * Writes the database entries into a .txt file saved to the file system.
	 * @param outputFile The name or location/name of the output file.
	 */
	public void writeFile(String outputFile) {
		try {
			makeFile = new FileWriter(outputFile);
			writeFile = new BufferedWriter(makeFile);
			
			Iterator it = hmap.entrySet().iterator();
			while (it.hasNext()) {

				Map.Entry pair = (Map.Entry)it.next();
				Book temp = (Book) pair.getValue();
				
				writeFile.write(temp.getISBN() + "|" + temp.getbookName());
				writeFile.newLine();
			}
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				writeFile.close();
				makeFile.close();
				System.out.println("Output file written to " + outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	/** 
	 * Empty the database
	 */
	public void emptyDatabase() {
		hmap.clear();
	}
	
	/**
	 * Print all key and value data of the database
	 */
	public void printDatabase() {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			System.out.println(temp.getISBN() + " " + temp.getbookName());
		}
	}
}
