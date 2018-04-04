/**
 * Book
 * This class is as an object to store information about books used in the database.
 * @author Amzad
 *
 */
public class Book implements Cloneable {
	protected String ISBN13;    // ISBN10 number of the book
	protected String ISBN10 = null;    // ISBN13 number of the book
	protected String title; 		   // Name of the book
	protected String author = null;    // Author of the book
	protected String year = null;	   // Year the book was published
	protected String publisher = null; // Publisher of the book
	protected String link = null;
	protected String pages;
	
	/**
	 * One of the constructors that allows you to create a book object with the title of the book.
	 * @param newISBN10 ISBN10 number of the book
	 * 
	 */
	public Book(String newISBN13) {
		if (newISBN13.length() == 10) {
			newISBN13 = convertToISBN13(newISBN13);
		}
		setISBN13(newISBN13);
	}
	
	/** 
	 * The second constructor that allows you to create a book object with the title and ISBN13 of the book.
	 * @param name Title of the book
	 * @param ISBN13 ISBN13 number of the book
	 */
	public Book(String title, String ISBN13) {
		setTitle(title);
        setISBN13(ISBN13);
	}
	
	public Book(String ISBN13, String title, String author, String year) {
        setTitle(title);
        setISBN13(ISBN13);
        setAuthor(author);
        setYear(year);
    }
	
	public Book(String ISBN13, String ISBN10, String title, String author, String year, String publisher, String link, String pages) {
		setISBN13(ISBN13);
		setISBN10(ISBN10);
		setTitle(title);
		setAuthor(author);
		setYear(year);
		setPublisher(publisher);
		setLink(link);
		setPages(pages);
		
	}
	
	/* WikiPedia: 
	 * Remove ISBN10 check digit. 
	 * Add 987 to the front
	 * Add the ISBN13 check digit to the end.
	 * 
	 * 
	 */
	public String convertToISBN13(String ISBN10) {
		String ISBN13 = ISBN10;
		ISBN13 = "978" + ISBN13.substring(0,9);
	    int checkDigit;
	    int sum = 0;
	    
	    for (int i = 0; i < ISBN13.length(); i++) {
	        checkDigit = ((i % 2 == 0) ? 1 : 3);
	        sum += ((((int) ISBN13.charAt(i)) - 48) * checkDigit);
	    }
	    
	    sum = 10 - (sum % 10);
	    ISBN13 += sum;

	    return ISBN13;
			
	}
	
	public Book clone() throws CloneNotSupportedException {
		return (Book) super.clone();
	}
	
	public String getISBN13() {
		return ISBN13;
	}
	
	public String setISBN13(String newISBN13) {
		if (newISBN13.length() == 10) {
			newISBN13 = convertToISBN13(newISBN13);
		}
		ISBN13 = newISBN13;
		return ISBN13;
	}
	
	public String getISBN10() {
		return ISBN10;
	}
	
	public String setISBN10(String newISBN10) {
		ISBN10 = newISBN10;
		return ISBN10;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String setTitle(String newTitle) {
		title = newTitle;
		return title;
	}
	
	public String getAuthor() {
        return author;
    }
    
    public String setAuthor(String newAuthor) {
        author = newAuthor;
        return author;
    }
	
	public String getYear() {
		return year;
	}
	
	public String setYear(String newYear) {
		year = newYear;
		return year;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public String setPublisher(String newPublisher) {
		publisher = newPublisher;
		return publisher;
	}
	
	public String getLink() {
		return link;
	}
	
	public String setLink(String newLink) {
		publisher = newLink;
		return link;
	}
	
	public String getPages() {
		return pages;
	}
	
	public String setPages(String newPages) {
		pages = newPages;
		return pages;
	}
	
	

}
