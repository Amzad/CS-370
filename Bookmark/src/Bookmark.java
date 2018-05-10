import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
	static String currentUser;
    static GUI gui;
    static Database db;
    static URLProcessor urlP;
    static FileReader loadFile;
    static BufferedReader readFile;
    static FileWriter makeFile;
    static BufferedWriter writeFile;
    static DateTimeFormatter format; 
    static LocalDateTime timeNow;

    public static void main(String[] args) throws CloneNotSupportedException {
    	format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    	timeNow = LocalDateTime.now();  
    	
    	
        /*args = new String[2]; // Fake cli args
        args[0] = "-i";
        args[1] = "input.txt";*/
    	

    	// If arguments exists, the program will start the CLI instead of the GUI.
        if (args.length > 0) { 
			new BookmarkCLI(args); // Start the CLI if arguments/flags are present.
		} else {
			setSystemLook(); // Changes the default java look.
			new Login();
		}
	}
    
    /**
     * Loads the essential components of the main GUI.
     * @param value
     */
    public static void loadComponents(int value) {
    	setSystemLook();
        db = new Database();
        urlP = new URLProcessor();
        
        // Runs the main gui in a seperate thread for a more responsive feel.
    	 new Thread() {
             public void run() {
                 gui = new GUI(value);
                 loadCache();
                 openTransactionLog();
             }
         }.start();
    }

    
    /**
     * This method loads the existing database for the current user.
     */
    public static void loadCache() {
    	
        String file = Bookmark.currentUser + "cache.txt"; // Unique filename specific for each user.
        try {

            String isbn13, isbn10, title, author, year, publisher, link, pages, price, img, type, location;

            loadFile = new FileReader(file); // Load file into the FileReader
            readFile = new BufferedReader(loadFile); // Read file into BufferedReader

            String inputLine; // The current line being read.
            String delimiter = "[|]"; // Book titles rarely have separators.

            // If the line isn't empty, process the data.
            
            // Trim removes the whitespaces for better data manipulation.
            // The while loop parses the cache file and retrieves the data.
            while ((inputLine = readFile.readLine()) != null) {
                String[] info = inputLine.split(delimiter);
                isbn13 = info[0].trim();
                isbn10 = info[1].trim(); 
                title = info[2].trim();
                author = info[3].trim();
                year = info[4].trim();
                publisher = info[5].trim();
                link = info[6].trim();
                pages = info[7].trim();
                type = info[8].trim();
                img = info[9].trim();
                price = info[10].trim();
                location = info[11].trim();

                // A new book object is created with the data being set from the previous parse results.
                Book tempBook = new Book(); 
                tempBook.setISBN13(isbn13);
                tempBook.setISBN10(isbn10);
                tempBook.setTitle(title);
                tempBook.setAuthor(author);
                tempBook.setYear(year);
                tempBook.setPublisher(publisher);
                tempBook.setLink(link);
                tempBook.setPages(pages);
                tempBook.setType(type);
                tempBook.setImage(img);
                tempBook.setPrice(Double.parseDouble(price));
                tempBook.setLocation(location);
                db.add(tempBook); // Add the new Book object to the database.
                gui.addBookRow(tempBook); // Add the new Book object to the GUI.
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        finally {
            try {
            	saveCurrentState(); // Saves the current data in the software for Persistence of Data
                readFile.close(); // Closes open resources.
                loadFile.close(); // Closes open resources.
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }

        }
    }

    /**
     * This method saves the current data loaded in the software to prevent data loss in unexpected circumstances.
     */
    public static void saveCurrentState() {

        try {
        	String file = Bookmark.currentUser + "cache.txt";
            makeFile = new FileWriter(file, false);
            writeFile = new BufferedWriter(makeFile);

            // The for loops iterates through the library and retrieves each Book object and writes it into a unique cache for storage.
            for (int i = 0; i < gui.library.size(); i++) {       
                Book temp = gui.library.get(i);
                writeFile.write(temp.getISBN13() + "|" + 
                        temp.getISBN10() + "|" +
                        temp.getTitle() + "|" +
                        temp.getAuthor() + "|" +
                        temp.getYear() + "|" +
                        temp.getPublisher() + "|" +
                        temp.getLink() + "|" +
                        temp.getPages() + "|" +
                        temp.getType() + "|" +
                        temp.getImage() + "|" +
                        temp.getPrice() + "|" +
                        temp.getLocation()
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
            } catch (IOException e) {
            }


        }
    }

    /**
     * This method sets the default java look and feel from Metal to System.
     */
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
    
    /**
     * This method checks if a current Book exists in the GUI tablemodel.
     * @param checkBook The book to check with the model.
     * @return if books exists
     */
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

    /**
     * This method imports a file from the system as an alternative method of inserting data into the application.
     */
    public static void importFile() {

    	// Obtaining the location and name of the import file for processing.
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.changeToParentDirectory();
        int fileChosen = fileChooser.showOpenDialog(null);

        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
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
                    title = info[2].trim(); // Remove starting and trailing white spaces.
                    author = info[3].trim();
                    year = info[4].trim();
                    publisher = info[5].trim();

                    // Create a new Book object with the name and isbn10 number
                    Book newBook = new Book(); // Create a new Book object with the name and isbn10 number
                    newBook.setISBN13(isbn13);
                    newBook.setTitle(title);
                    newBook.setAuthor(author);
                    newBook.setYear(year);
                    newBook.setPublisher(publisher);

                    //Book newBook = new Book(isbn, name, author, year); // Create a new Book object with the name and isbn10 number
                    db.add(newBook); // Add the new Book object to the database. Due to the nature of hashmap, checking for duplicate isbn13 isnt necessary.

                    // If the book object exists, it will be skipped.
                    if (bookExists(newBook)) {
                    }
                    else {
                    	gui.addBookRow(newBook);
                    }    
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            finally {
                try {
                    readFile.close(); // Close resources.
                    loadFile.close(); // Close resources.
                    saveCurrentState(); // Save current loaded data.
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * This method exports a file containing the current Book objects in the library limited to the Title, Author, Publisher and Year Published.
     */
    public static void exportFile() {

    	// Get the location of the export file.
        JFileChooser fileChooser = new JFileChooser();
        int fileChosen = fileChooser.showSaveDialog(null);

        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
            	// Create the export file.
                makeFile = new FileWriter(selectedFile);
                writeFile = new BufferedWriter(makeFile);

                HashMap hmap = db.getDatabase();
                
                // Iterate through the hashmap to get all the objects.
                Iterator it = hmap.entrySet().iterator();
                while (it.hasNext()) {

                    Map.Entry pair = (Map.Entry)it.next();
                    Book temp = (Book) pair.getValue();

                    writeFile.write(temp.getISBN13() + "|" + 
                    				temp.getISBN10() + "|" + 
                    				temp.getTitle() + "|" + 
                    				temp.getAuthor() + "|" + 
                    				temp.getYear() + "|" + 
                    				temp.getPublisher()
                    				);
                    writeFile.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    writeFile.close(); // Close resources.
                    makeFile.close(); // Close resources.
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    
    /**
     * This method creates a new user for the program.
     * @param username Username
     * @param password Password
     * @return True if account creation was successful.
     */
	public static boolean newUser(String username, String password) {
		FileWriter makeUser = null;
		BufferedWriter writeUser = null;
		File check = new File("users.txt");

		// Checks if the user file already exists. 
		if (check.exists()) {
			// Checks if username already exists.
			if (userExists(username)) {
				JOptionPane.showMessageDialog(null, "Username already exists");
				return false;
			}
			
			// Adds new user.
			else {
				try {
					makeUser = new FileWriter("users.txt", true);
					writeUser = new BufferedWriter(makeUser);
					writeUser.write(username + " | " + password + " | User ");
					writeUser.newLine();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Unable to access files");
				} finally {
					try {
						writeUser.close(); // Close resources.
						makeUser.close(); // Close resources.
						return true;
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Unable to access files");
					}

				}
				
			}
			// If user file doesn't exist, creates the file and adds the user.
		} else {
			try {;
				makeUser = new FileWriter("users.txt", false);
				writeUser = new BufferedWriter(makeUser);
				writeUser.write("Admin | Admin | Admin");
				writeUser.newLine();
				writeUser.write(username + " | " + password + " | User ");
				writeUser.newLine();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Unable to access files");
			} finally {
				try {
					writeUser.close(); // Close resources.
					makeUser.close(); // Close resources.
					return true;
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Unable to access files");
				}

			}
		}
		return false;
	}
    
	/**
	 * Checks if user already exists in the users file.
	 * @param username Username to check
	 * @return True if exists.
	 */
    public static boolean userExists(String username) {
		String file = "users.txt";
		FileReader loadUser = null;
		BufferedReader readUser = null;
		try {

			String checkUsername;

			loadUser = new FileReader(file); // Load file into the FileReader
			readUser = new BufferedReader(loadUser); // Read file into BufferedReader
			String inputLine = readUser.readLine(); // The current line being read.

			// Parses users file to see if user exists.
			while (inputLine != null) {
				String[] info = inputLine.split("[|]");
				checkUsername = info[0].trim();
				if (checkUsername.equals(username)) {
					readUser.close(); // Close resources.
					loadUser.close(); // Close resources.
					return true;
				}
				inputLine = readUser.readLine();
			}
			readUser.close(); // Close resources.
			loadUser.close(); // Close resources.
 
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "User file missing.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to access User file.");
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Invalid user file.");
		}
		try {
			readUser.close(); // Close resources.
			loadUser.close(); // Close resources.
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
    }
    
    /**
     * This method attempts the user to login if credentials are valid.
     * @param username Username
     * @param password Password
     * @return Value based on the event or error.
     */
    public static int tryLogin(String username, String password) {

    	String file = "users.txt";
        try {

            String checkUsername, checkPassword, status;

            FileReader loadUser = new FileReader(file); // Load file into the FileReader
            BufferedReader readUser = new BufferedReader(loadUser); // Read file into BufferedReader
            String inputLine = readUser.readLine(); // The current line being read.
            
            // Login for guest.
            if (username.equalsIgnoreCase("guest") && (password.equalsIgnoreCase("guest"))) {
            	readUser.close(); // Close resources.
    			loadUser.close(); // Close resources.
            	return 5;
            }

            while (inputLine != null) {
                String[] info = inputLine.split("[|]");
                checkUsername = info[0].trim();
                checkPassword = info[1].trim();
                status = info[2].trim();
                
                if (checkUsername.equals(username)) {
                	if (checkPassword.equals(password)) {
                		// Login for admin
                		if (status.equals("Admin")) {
                			readUser.close(); // Close resources.
                			loadUser.close(); // Close resources.
                			return 1;
                		}
                		// Login for normal users.
                		else {
                			readUser.close(); // Close resources.
                			loadUser.close(); // Close resources.
                			return 0;
                		}
                	}
                	else {
                		// Return invalid password
                		readUser.close(); // Close resources.
                		loadUser.close(); // Close resources.
                		return 3;
                	}
                }
 
                inputLine = readUser.readLine();
            }
            readUser.close(); // Close resources.
            loadUser.close(); // Close resources.
            return 2;
            
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No users available. Create a new user.");
            return 4;
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "Unable to access User file.");
    	} catch (NullPointerException e) {
    		JOptionPane.showMessageDialog(null, "Invalid user file.");
    	} catch (ArrayIndexOutOfBoundsException e) {
    		
    	}
    	return 3;
    }

    /**
     * This method updates the current book object with updated values in itself.
     * @param book Book object.
     */
    public static void updateBook(Book book) {
    	ArrayList<Book> temp = gui.library;
    	DefaultTableModel dModel = gui.tModel;
    	// Find the right book and update pages and location.
    	for (int i = 0; i < temp.size(); i++) {
    		if (dModel.getValueAt(i, 0) == book.getISBN13()) {
    			temp.set(i, book);
    			dModel.setValueAt(book.getPages(), i, 3);
    			dModel.setValueAt(book.getLocation(), i, 4);
    		}
    	}
    	saveCurrentState(); // Save state since data was manipulated.
    }
    
    /**
     * This method delete the given book from the library and database.
     * @param book Book
     */
    public static void deleteBook(Book book) {
    	ArrayList<Book> temp = gui.library;
    	DefaultTableModel dModel = gui.tModel;
    	for (int i = 0; i < temp.size(); i++) {
    		if (dModel.getValueAt(i, 0) == book.getISBN13()) {
    			temp.remove(i);
    			dModel.removeRow(i);
    		}
    	}
    	saveCurrentState(); // Book was deleted so save state.
    }

    /**
     * This method adds the input string into the transaction log. Useful for data manipulation messages.
     * @param value String
     */
    public static void println(String value) {
    	
    	FileWriter makeUser = null;
		BufferedWriter writeUser = null;
		// try {
		File check = new File("transactionlog.txt");
		
		// Append into existing file
		if (check.exists()) {
				try {
					makeUser = new FileWriter("transactionlog.txt", true);
					writeUser = new BufferedWriter(makeUser);
					writeUser.write(format.format(timeNow) + ": " + value);
					writeUser.newLine();
					gui.print(format.format(timeNow) + ": " + value); // Prints the current date/time for security purposes.
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Unable to access files");
				} finally {
					try {
						writeUser.close(); // Close resources.
						makeUser.close(); // Close resources.
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Unable to access files");
					}

				}
			
		// Creates new file and appends message.
		} else {
			try {
				makeUser = new FileWriter("transactionlog.txt", false);
				writeUser = new BufferedWriter(makeUser);
				writeUser.write(format.format(timeNow) + ": " + value);
				writeUser.newLine();
				gui.print(format.format(timeNow) + ": " + value); // Prints the current date/time for security purposes.
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Unable to access files");
			} finally {
				try {
					writeUser.close(); // Close resources.
					makeUser.close(); // Close resources.
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Unable to access files");
				}
			}
		}
   	}

    /**
     * This method loads an existing transactionlog into the admin tab for easy viewing.
     */
    public static void openTransactionLog() {
    	String file = "transactionlog.txt";
    	FileReader loadLog = null;
    	BufferedReader readLog = null;
        try {

            loadLog = new FileReader(file); // Load file into the FileReader
            readLog = new BufferedReader(loadLog); // Read file into BufferedReader

            String inputLine = readLog.readLine(); // The current line being read.

            // If the line isn't empty, process the data.
            
            while (inputLine != null) {
                gui.print(inputLine);
                inputLine = readLog.readLine();
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        finally {
            try {
            	saveCurrentState();
            	readLog.close(); // Close resources.
                loadLog.close(); // Close resources.
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }

        }
    }
}





