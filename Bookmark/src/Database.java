import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;


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
		hmap.put(item.getISBN13(), item);
		Bookmark.saveCurrentState();
		return item;
	}
	
	/**
	 * Creates a new book object with the ISBN13 and book name
	 * @param isbn13 The ISBN13 number of the book
	 * @param name Title of the book
	 */
	public Book newBook(String isbn13, String name) {
		Book newBook = new Book(name,isbn13);
		add(newBook);
		return newBook;
		
	}
	
	public Book newBook(String isbn13, String name, String author, String year) {
	    Book newBook = new Book();
	    newBook.setISBN13(isbn13);
	    newBook.setTitle(name);
	    newBook.setAuthor(author);
	    newBook.setYear(year);
	    return newBook;
	}
	
	/**
	 * Modifies the ISBN13 number of an existing book
	 * @param isbn The current ISBN13 number
	 * @param newISBN13 The new ISBN13 number
	 * @throws CloneNotSupportedException 
	 */
	public Book modifyBookISBN(String isbn13, String newISBN13) throws CloneNotSupportedException {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			// Find the isbn13 number to change.
			if (temp.getISBN13().equals(isbn13)) {
				Book oldBook = temp.clone();
				temp.setISBN13(newISBN13); // Set new isbn13 number
				return oldBook;
				
			}
		}
		return null;
	}
	
	/**
	 * Modifies the book title of a Book
	 * @param isbn ISBN13 number of the book to modify
	 * @param newName New title for the book
	 */
	public Book modifyBookName(String isbn13, String newTitle) {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			// Find the isbn13 number to change.
			if (temp.getISBN13().equals(isbn13)) {
				Book oldBook = new Book(temp.getTitle(), temp.getISBN13()); // Get current book object to return for later.

				temp.setTitle(newTitle); // Set new isbn13 number
				
				return oldBook;
			}
		}
		return null;
	}
	
	public Book replaceBook(Book book) {
		hmap.put(book.getISBN13(), book);	
		Bookmark.println("MODIFED: " + book.getTitle() + ":" + book.getAuthor());
		return book;
	}
	
	/**
	 * Remove a book object
	 * @param isbn ISBN13 number of the book to remove
	 */
	public Book remove(String isbn13) {
		
		Book temp = (Book) hmap.remove(isbn13);
		Bookmark.saveCurrentState();
		
		return temp;
	}
	
	/**
	 * Check of the ISBN13 is in the database
	 * @param isbn The ISBN13 number to check
	 * @return Returns true if it exists
	 */
	public boolean ifExists(String isbn13) {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			if (temp.getISBN13().equals(isbn13)) {
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
			System.out.println(temp.getISBN13() + " " + temp.getTitle());
		}
	}
	
	public HashMap getDatabase() {
		return hmap;
	}
	
	public void saveImage(String link) {
		URL url;
		try {
			String workingDirectory = System.getProperty("user.dir");
			url = new URL(link);
			BufferedImage img = ImageIO.read(url);
			String file = url.getPath();
			file = file.substring(file.lastIndexOf("/")+1, file.length());
			File imgFile = new File(workingDirectory + "\\Image\\" + file);
			String extension = file.substring(file.lastIndexOf(".")+1, file.length());
			ImageIO.write(img, extension, imgFile);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
