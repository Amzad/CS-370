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
    static Database db;
    static FileReader loadFile;
    static BufferedReader readFile;
    static FileWriter makeFile;
    static BufferedWriter writeFile;

    public static void main(String[] args) {

        //args = new String[2]; // Fake cli args
        //args[0] = "-i";
        //args[1] = "input.txt";

        if (args.length > 0) { 
            new BookmarkCLI(args); // Start the CLI if arguments/flags are present.
        } else {
            setSystemLook();

            new Thread() {
                public void run() {
                    gui = new GUI();
                    loadCache();
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

            String isbn, name;
            loadFile = new FileReader(file); // Load file into the FileReader
            readFile = new BufferedReader(loadFile); // Read file into BufferedReader
            gui.print("Cache Found. Loading Cache");

            String inputLine; // The current line being read.
            String delimiter = "[|]"; // Book titles rarely have separators.

            // If the line isn't empty, process the data.
            while ((inputLine = readFile.readLine()) != null) {
                String[] info = inputLine.split(delimiter);
                isbn = info[0].trim(); // Remove starting and trailing white spaces.
                name = info[1].trim(); // Remove starting and trailing white spaces.

                Book tempBook = new Book(name, isbn); // Create a new Book object with the name and isbn10 number
                db.add(tempBook); // Add the new Book object to the database.
            }
            gui.print("Cache loaded");


        } catch (FileNotFoundException e) {
            gui.print("Database doesn't exist");
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
            makeFile = new FileWriter("database.txt", true);
            writeFile = new BufferedWriter(makeFile);

            HashMap<String, Book> hmap = db.getDatabase();

            Iterator<Entry<String, Book>> it = hmap.entrySet().iterator();
            while (it.hasNext()) {

                Map.Entry pair = (Map.Entry)it.next();
                Book temp = (Book) pair.getValue();

                writeFile.write(temp.getISBN10() + "|" + 
                        temp.getISBN13() + "|" +
                        temp.getbookName() + "|" +
                        temp.getYear() + "|" 
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
            }


        }
    }






    public static void setSystemLook() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void importFile() {

        JFileChooser fileChooser = new JFileChooser();
        int fileChosen = fileChooser.showOpenDialog(null);

        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            gui.print(selectedFile.getAbsolutePath() + " file picked.");
            try {

                String isbn, name;
                loadFile = new FileReader(selectedFile); // Load file into the FileReader
                readFile = new BufferedReader(loadFile); // Read file into BufferedReader


                String inputLine; // The current line being read.
                String delimiter = "[|]"; // Book titles rarely have separators.

                // If the line isn't empty, process the data.
                while ((inputLine = readFile.readLine()) != null) {
                    String[] info = inputLine.split(delimiter);
                    isbn = info[0].trim(); // Remove starting and trailing white spaces.
                    name = info[1].trim(); // Remove starting and trailing white spaces.

                    Book newBook = new Book(name, isbn); // Create a new Book object with the name and isbn10 number
                    db.add(newBook); // Add the new Book object to the database.
                    gui.print(newBook.getbookName() + " added to the database");
                }


            } catch (FileNotFoundException e) {
                gui.print("Database doesn't exist");
            } catch (IOException e) {
                gui.print("Unable to read or write to file.");
            }

            finally {
                try {
                    readFile.close();
                    loadFile.close();
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

                    writeFile.write(temp.getISBN10() + "|" + temp.getbookName());
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
