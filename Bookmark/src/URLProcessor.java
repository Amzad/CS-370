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
	
	public ArrayList findBook(String searchTerm) {
		
		ArrayList bookList = new ArrayList();

		URL url = generateURL(searchTerm);

		InputStream content = (InputStream) getURLInputStream(url);
		BufferedReader bReader = new BufferedReader (new InputStreamReader(content));
		
		String image;
		String isbn13;
		//String isbn10;
		String bookName;
		String bookAuthor;
		String bookPublished;
		double price;
		String type;
		
		try {
			String line = bReader.readLine();
			String searchSection = "<div class=\"tab search\">";
			String dimage = "  <img class=\"lazy\" data-lazy=";
			String resultNum = "";
			String dauthor = "<p class=\"author\">";
			String dpublished = "<p class='published' itemprop=\"datePublished\">";
			String dformat = "<p class=\"format\">";
			
			
			int resultCount = 0;
			
			
			while (line != null) {
				if (line.startsWith(searchSection)) {
					
					
				}
				
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
