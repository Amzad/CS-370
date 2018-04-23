import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class URLProcessor {
	
	
	public URLProcessor() {

	}
	
	public void displayURLInfo(String url) {
			
	}
	
	private URL generateURL(String searchTerm) {
		String defaultURL = "https://www.bookdepository.com/search?searchTerm=";
		String[] term = searchTerm.split(" ");
		URL url = null;
;
		for (int i = 0; i < term.length; i++) {
			defaultURL = defaultURL + term[i];
			if(!(i == term.length-1)) {
				defaultURL = defaultURL + "+";
			}
		}
		defaultURL = defaultURL + "&search=Find+book";
		try {
			url = new URL(defaultURL);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		return url;
	}
	
	public ArrayList<Book> findBook(String searchTerm) {
		
		ArrayList<Book> bookList = new ArrayList<Book>();

		URL url = generateURL(searchTerm);

		InputStream content = (InputStream) getURLInputStream(url);
		BufferedReader bReader = new BufferedReader (new InputStreamReader(content));
		
		String resultCount;
		String image;
		String isbn13 = null;
		String isbn10 = null;
		String bookName = null;
		String bookAuthor = null;
		String bookPublished = null;
		double price;
		String type;
		String imgLink;
		
		String searchSection = "<div class=\"tab search\">";
		String dimage = "<img class=\"lazy\" data-lazy=";
		String resultNum = "of  <span class=\"search-count\"";
		String title = "<meta itemprop=\"name\" content=";				
		String dauthor = "<span itemprop=\"author\"";
		String dpublished = "<p class='published' itemprop=\"datePublished\">";
		String dformat = "<p class=\"format\">";
		String disbn13 = "<meta itemprop=\"isbn\" content=\"";
		String dtype = "<p class='published'";
		
		try {
			String line = bReader.readLine();
				

			while (line != null) {
				// System.out.println(line);
				if (line.trim().startsWith(resultNum)) { // Find the results section.
					resultCount = line.substring(line.indexOf(">") + 1, line.indexOf(">") + 2); // Gets the result number
					System.out.println(resultCount);
					int count = 0;

					while (count < Integer.parseInt(resultCount)) {
						if (line.trim().startsWith(dimage)) {
							if (line.contains(".jpg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpg") + 4);
								System.out.println(imgLink);
							} else if (line.contains(".jpeg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpeg") + 5);
								System.out.println(imgLink);
							} else if (line.contains(".png")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".png") + 4);
								System.out.println(imgLink);
							}

						}

						if (line.trim().startsWith(disbn13)) {
							isbn13 = line.substring(line.indexOf(disbn13) + 31, line.lastIndexOf("\""));
							System.out.println(isbn13);
						}

						if (line.trim().startsWith(title)) {
							bookName = line.substring(line.indexOf(title) + 31, line.lastIndexOf("\""));
							System.out.println(bookName);
						}

						if (line.trim().startsWith(dauthor)) {
							bookAuthor = line.substring(line.indexOf("itemscope=\"") + 11, line.lastIndexOf("\""));
							System.out.println(bookAuthor);
						}

						if (line.trim().startsWith(dpublished)) {
							line = bReader.readLine();
							line = line.trim();
							bookPublished = line.substring(0, line.lastIndexOf("<"));
							bookPublished = bookPublished.split(" ")[2];
							System.out.println(bookPublished);
						}

						if (line.trim().startsWith(dformat)) {
							line = bReader.readLine();
							line = line.trim();
							type = line.substring(0, line.lastIndexOf("<"));
							System.out.println(type + "\n");							
							Book searchBook = new Book(isbn13, isbn10, bookName, bookAuthor, bookPublished, null, type, null);	
							bookList.add(searchBook);
							count++;
						}
						
						line = bReader.readLine();  // Goto next line

					}
				}
				
				
				
				line = bReader.readLine();  // Goto next line
			}
			
			
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		

		return bookList;
	}
	
	 

	private InputStream getURLInputStream(URL url) {
        URLConnection openConnection;
		try {
			openConnection = url.openConnection();	
			String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2) Gecko/20100115 Firefox/3.6"; 
			openConnection.setRequestProperty("User-Agent", USER_AGENT); 
			return openConnection.getInputStream(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		
		return null;
	}
	

}
