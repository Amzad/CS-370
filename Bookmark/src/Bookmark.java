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
import java.util.Map.Entry;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

/**
 * Bookmark
 * A program used to manage a collection of books.
 * 
 * @author Amzad
 */


public class Bookmark extends Thread {

    /** 
     * This is the main method. It is used to start up the program. If the length of the args String array is greater than 0,
     * the command line interface will start up.
     * @param args Flags passed in the command line during startup.
     */

    static GUI gui;
    static MYSql netdb;
    static Database db;
    static URLProcessor urlP;
    static FileReader loadFile;
    static BufferedReader readFile;
    static FileWriter makeFile;
    static BufferedWriter writeFile;

    public static void main(String[] args) throws CloneNotSupportedException {

        /*args = new String[2]; // Fake cli args
        args[0] = "-i";
        args[1] = "input.txt";*/
    	
    	/* Order for elements
    	ISBN13, ISBN10, Title, Author, Year, Publisher, Pages, Link
    	*/

        if (args.length > 0) { 
            new BookmarkCLI(args); // Start the CLI if arguments/flags are present.
        } else {
            setSystemLook();

            new Thread() {
                public void run() {
                    gui = new GUI(); 
                    loadCache();
                    netdb = new MYSql();
                    urlP = new URLProcessor();
                }
            }.start();
            
            //gui = new GUI(); // Start the GUI is no arguments/flags are found.
            db = new Database();
            //loadCache();

        }
    }

    public static void loadCache() {

        String file = "database.txt";
        try {

            String isbn13, isbn10, title, author, year, publisher, link, pages;

            loadFile = new FileReader(file); // Load file into the FileReader
            readFile = new BufferedReader(loadFile); // Read file into BufferedReader
            gui.print("Cache Found. Loading Cache");

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
                
                gui.addRow(data);
            }
            gui.print("Cache loaded");


        } catch (FileNotFoundException e) {
            gui.print("Cache not found.");
        } catch (IOException e) {
            gui.print("Unable to read or write to file.");
        }

        finally {
            try {
                readFile.close();
                loadFile.close();
            } catch (IOException e) {
                gui.print("Unable to close files.");
            } catch (NullPointerException e) {
                gui.print("Unable to find cache");
            }

        }
    }

    public static void saveCurrentState() {
        gui.print("Starting state save.");

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
                gui.print("Current state saved");
            } catch (IOException e) {
                gui.print("Unable to access file");
                System.out.println(e);
            }


        }
    }

    public static void setSystemLook() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean bookExists(Book checkBook) {
    	boolean exists = false;
    	DefaultTableModel tModel = gui.getModel();
    	
    	for (int count = 0; count < tModel.getRowCount(); count++) {
    		if (checkBook.getISBN13().equals(tModel.getValueAt(count, 0))) {
    			exists = true;
    		}
    	}
    	return exists;
    }

    public static void importFile() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.changeToParentDirectory();
        int fileChosen = fileChooser.showOpenDialog(null);

        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            gui.print(selectedFile.getAbsolutePath() + " file picked.");
            try {

            	String isbn13, isbn10, title, author, year, publisher, link, pages;
                loadFile = new FileReader(selectedFile); // Load file into the FileReader
                readFile = new BufferedReader(loadFile); // Read file into BufferedReader


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

                    Book newBook = new Book(isbn13, isbn10, title, author, year, publisher, link, pages); // Create a new Book object with the name and isbn10 number

                    //Book newBook = new Book(isbn, name, author, year); // Create a new Book object with the name and isbn10 number
                    db.add(newBook); // Add the new Book object to the database.
                    
                    

                    if (bookExists(newBook)) {
                    	gui.print("Existing Book found. Skipping book");
                    }
                    else {
                    	String[] data = {newBook.getISBN13(), newBook.getTitle(), newBook.getAuthor()};
                    	//String[] data = {newBook.getISBN13(), newBook.getISBN10(), newBook.getTitle(), newBook.getAuthor(), newBook.getYear(), newBook.getPublisher(), newBook.getLink(), newBook.getPages()};
                    	gui.addRow(data);
                    	gui.print(newBook.getTitle() + " added to the database");
                    }

            	   
            	    	
            	    
                    
                }


            } catch (FileNotFoundException e) {
                gui.print("Database doesn't exist");
            } catch (IOException e) {
                gui.print("Unable to read or write to file.");
            } catch (ArrayIndexOutOfBoundsException e) {
            	gui.print("Invalid file imported.");
            }

            finally {
                try {
                    readFile.close();
                    loadFile.close();
                    saveCurrentState();
                } catch (IOException e) {
                    gui.print("Unable to close files.");
                }
            }

        }
    }

    public static void exportFile() {

        JFileChooser fileChooser = new JFileChooser();
        int fileChosen = fileChooser.showSaveDialog(null);

        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            gui.print(selectedFile.getAbsolutePath() + " file picked.");

            try {
                makeFile = new FileWriter(selectedFile);
                writeFile = new BufferedWriter(makeFile);

                HashMap hmap = db.getDatabase();

                Iterator it = hmap.entrySet().iterator();
                while (it.hasNext()) {

                    Map.Entry pair = (Map.Entry)it.next();
                    Book temp = (Book) pair.getValue();

                    writeFile.write(temp.getISBN13() + "|" + 
                    				temp.getISBN13() + "|" + 
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
                    gui.print("Output file written to " + selectedFile.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
