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
	
	public void add(Book item) {
		hmap.put(item.getISBN(), item);
	}
	
	public void newBook(String isbn, String name) {
		Book newBook = new Book(name,isbn);
		add(newBook);
	}
	
	public void remove(String isbn) {

		Book temp = (Book) hmap.remove(isbn);
		System.out.println(temp.getbookName() + " deleted");
		

	}
	
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
				isbn = info[0].trim();
				name = info[1].trim();
				
				Book tempBook = new Book(name, isbn);
				this.add(tempBook);
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
	
	public void emptyDatabase() {
		hmap.clear();
	}
	
	public void printDatabase() {
		Iterator it = hmap.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pair = (Map.Entry)it.next();
			Book temp = (Book) pair.getValue();
			
			System.out.println(temp.getISBN() + " " + temp.getbookName());
		}
	}
}
