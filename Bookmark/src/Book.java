/**
 * Book
 * This class is as an object to store information about books used in the database.
 * @author Amzad
 *
 */
public class Book {
	protected String bookName; 		// Name of the book
	protected String author = null; // Author of the book
	protected String ISBN13 = null; // ISBN13 number of the book
	protected String ISBN10 = null; // ISBN10 number of the book
	protected String year = null;	// Year the book was published
	
	/**
	 * One of the constructors that allows you to create a book object with the title of the book.
	 * @param name Title of the book
	 * 
	 */
	
	public Book(String name) {
		bookName = name;
	}
	
	/** 
	 * The second construtor that allows you to create a book object with the title and ISBN10 of the book.
	 * @param name Title of the book
	 * @param ISBN ISBN10 number of the book
	 */
	public Book(String name, String ISBN) {
		bookName = name;
		ISBN10 = ISBN;
	}
	
	public String getbookName() {
		return bookName;
	}
	
	public String getISBN() {
		return ISBN10;
	}
	
	public String setName(String newName) {
		bookName = newName;
		return bookName;
	}
	
	public String setISBN10(String newISBN10) {
		ISBN10 = newISBN10;
		return ISBN10;
	}
	

}
