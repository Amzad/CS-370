import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Database {
	HashMap<String, Object> hmap = new HashMap<String, Object>();
	
	/* Database Array Index Order
	 * 
	 * 0 - ISBN10
	 * 1 - ISBN13
	 * 2 - Title
	 * 3 - Author
	 * 4 - Published Year
	 * 5 - Publisher
	 */
	public Database() {
		
	}

	/**
	 * Add a new book to the database
	 * @param item Book object to be added
	 */
	public Book add(Book item) {
		hmap.put(item.getISBN10(), item);
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
	
	public Book newBook(String isbn10, String name, String author, String year) {
	    Book newBook = new Book(isbn10, name, author, year);
	    return newBook;
	}
	
	/**
	 * Modifies the ISBN10 number of an existing book
	 * @param isbn The current ISBN10 number
	 * @param newISBN10 The new ISBN10 number
	 * @throws CloneNotSupportedException 
	 */
	public Book modifyBookISBN(String isbn, String newISBN) throws CloneNotSupportedException {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			// Find the isbn10 number to change.
			if (temp.getISBN10().equals(isbn)) {
				Book oldBook = temp.clone();
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
	public Book modifyBookName(String isbn, String newTitle) {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			// Find the isbn10 number to change.
			if (temp.getISBN10().equals(isbn)) {
				Book oldBook = new Book(temp.getTitle(), temp.getISBN10()); // Get current book object to return for later.

				temp.setTitle(newTitle); // Set new isbn10 number
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
		System.out.println(temp.getTitle() + " deleted");
		

	}
	
	/**
	 * Check of the ISBN13 is in the database
	 * @param isbn The ISBN13 number to check
	 * @return Returns true if it exists
	 */
	public boolean ifExists(String isbn) {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			if (temp.getISBN13().equals(isbn)) {
				return true;
			}
		}
		return false;
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
			
			System.out.println(temp.getISBN10() + " " + temp.getTitle());
		}
	}
	
	public HashMap getDatabase() {
		return hmap;
	}
}
