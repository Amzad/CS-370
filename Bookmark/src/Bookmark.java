import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
    	
    	/* Order for elements
    	ISBN13, ISBN10, Title, Author, Year, Publisher, Pages, Link
    	*/

        if (args.length > 0) { 
			new BookmarkCLI(args); // Start the CLI if arguments/flags are present.
		} else {
			setSystemLook();
			new Login();
		}
	}
    public static void loadComponents(int value) {
    	setSystemLook();
        db = new Database();
        urlP = new URLProcessor();
    	 new Thread() {
             public void run() {
                 gui = new GUI(value);
                 loadCache();
                 openTransactionLog();
             }
         }.start();
    }

    public static void loadCache() {
    	
        String file = Bookmark.currentUser + "cache.txt";
        try {

            String isbn13, isbn10, title, author, year, publisher, link, pages, price, img, type, location;

            loadFile = new FileReader(file); // Load file into the FileReader
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
                type = info[8].trim();
                img = info[9].trim();
                price = info[10].trim();
                location = info[11].trim();

                Book tempBook = new Book(); // Create a new Book object with the name and isbn10 number
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
                gui.addBookRow(tempBook);
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        finally {
            try {
            	saveCurrentState();
                readFile.close();
                loadFile.close();
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }

        }
    }

    public static void saveCurrentState() {

        try {
        	String file = Bookmark.currentUser + "cache.txt";
            makeFile = new FileWriter(file, false);
            writeFile = new BufferedWriter(makeFile);

            for (int i = 0; i < gui.library.size(); i++) {
  
                Book temp = gui.library.get(i);
                /*
                isbn13 = info[0].trim();
                isbn10 = info[1].trim(); // Remove starting and trailing white spaces.
                title = info[2].trim(); // Remove starting and trailing white spaces.
                author = info[3].trim();
                year = info[4].trim();
                publisher = info[5].trim();
                link = info[6].trim();
                pages = info[7].trim();
                type = info[8].trim();
                img = info[9].trim();
                price = info[10].trim();
                 */
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
                System.out.println("SAVED");
                
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
                    db.add(newBook); // Add the new Book object to the database.
                    
                    

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
                    readFile.close();
                    loadFile.close();
                    saveCurrentState();
                } catch (IOException e) {
                }
            }

        }
    }

    public static void exportFile() {

        JFileChooser fileChooser = new JFileChooser();
        int fileChosen = fileChooser.showSaveDialog(null);

        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                makeFile = new FileWriter(selectedFile);
                writeFile = new BufferedWriter(makeFile);

                HashMap hmap = db.getDatabase();

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
                    writeFile.close();
                    makeFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    
	public static boolean newUser(String username, String password) {
		FileWriter makeUser = null;
		BufferedWriter writeUser = null;
		// try {
		File check = new File("users.txt");

		if (check.exists()) {
			if (userExists(username)) {
				JOptionPane.showMessageDialog(null, "Username already exists");
				return false;
			}
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
						writeUser.close();
						makeUser.close();
						return true;
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Unable to access files");
					}

				}
				
			}
		} else {
			try {;
				makeUser = new FileWriter("users.txt", false);
				writeUser = new BufferedWriter(makeUser);
				writeUser.write(username + " | " + password + " | User ");
				writeUser.newLine();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Unable to access files");
			} finally {
				try {
					writeUser.close();
					makeUser.close();
					return true;
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Unable to access files");
				}

			}
		}
		return false;
	}
    
    public static boolean userExists(String username) {
		String file = "users.txt";
		FileReader loadUser = null;
		BufferedReader readUser = null;
		try {

			String checkUsername;

			loadUser = new FileReader(file); // Load file into the FileReader
			readUser = new BufferedReader(loadUser); // Read file into BufferedReader
			String inputLine = readUser.readLine(); // The current line being read.

			while (inputLine != null) {
				String[] info = inputLine.split("[|]");
				checkUsername = info[0].trim();
				if (checkUsername.equals(username)) {
					readUser.close();
					loadUser.close();
					return true;
				}
				inputLine = readUser.readLine();
			}
			readUser.close();
			loadUser.close();

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "User file missing.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to access User file.");
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Invalid user file.");
		}
		try {
			readUser.close();
			loadUser.close();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
    }
    
    public static int tryLogin(String username, String password) {

    	String file = "users.txt";
        try {

            String checkUsername, checkPassword, status;

            FileReader loadUser = new FileReader(file); // Load file into the FileReader
            BufferedReader readUser = new BufferedReader(loadUser); // Read file into BufferedReader
            String inputLine = readUser.readLine(); // The current line being read.
            
            if (username.equalsIgnoreCase("guest") && (password.equalsIgnoreCase("guest"))) {
            	readUser.close();
    			loadUser.close();
            	return 5;
            }

            while (inputLine != null) {
                String[] info = inputLine.split("[|]");
                checkUsername = info[0].trim();
                checkPassword = info[1].trim();
                status = info[2].trim();
                
                if (checkUsername.equals(username)) {
                	if (checkPassword.equals(password)) {
                		if (status.equals("Admin")) {
                			readUser.close();
                			loadUser.close();
                			return 1;
                		}
                		else {
                			readUser.close();
                			loadUser.close();
                			return 0;
                		}
                	}
                	else {
                		readUser.close();
                		loadUser.close();
                		return 3;
                	}
                }
 
                inputLine = readUser.readLine();
            }
            readUser.close();
            loadUser.close();
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

    public static void updateBook(Book book) {
    	ArrayList<Book> temp = gui.library;
    	DefaultTableModel dModel = gui.tModel;
    	for (int i = 0; i < temp.size(); i++) {
    		if (dModel.getValueAt(i, 0) == book.getISBN13()) {
    			temp.set(i, book);
    			dModel.setValueAt(book.getPages(), i, 3);
    			dModel.setValueAt(book.getLocation(), i, 4);
    			System.out.println("WORKED");
    		}
    	}
    	saveCurrentState();
    }
    
    public static void deleteBook(Book book) {
    	ArrayList<Book> temp = gui.library;
    	DefaultTableModel dModel = gui.tModel;
    	for (int i = 0; i < temp.size(); i++) {
    		if (dModel.getValueAt(i, 0) == book.getISBN13()) {
    			temp.remove(i);
    			dModel.removeRow(i);
    		}
    	}
    	saveCurrentState();
    }

    public static void println(String value) {
    	
    	FileWriter makeUser = null;
		BufferedWriter writeUser = null;
		// try {
		File check = new File("transactionlog.txt");

		if (check.exists()) {
				try {
					makeUser = new FileWriter("transactionlog.txt", true);
					writeUser = new BufferedWriter(makeUser);
					writeUser.write(format.format(timeNow) + ": " + value);
					writeUser.newLine();
					gui.print(format.format(timeNow) + ": " + value);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Unable to access files");
				} finally {
					try {
						writeUser.close();
						makeUser.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Unable to access files");
					}

				}
				
		} else {
			try {
				makeUser = new FileWriter("transactionlog.txt", false);
				writeUser = new BufferedWriter(makeUser);
				writeUser.write(format.format(timeNow) + ": " + value);
				writeUser.newLine();
				gui.print(format.format(timeNow) + ": " + value);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Unable to access files");
			} finally {
				try {
					writeUser.close();
					makeUser.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Unable to access files");
				}
			}
		}
   	}

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
            	readLog.close();
                loadLog.close();
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }

        }
    }
}





