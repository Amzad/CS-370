import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class is the GUI for the modifyFrame gui which allows a user to view a searched Book object.
 * @author Amzad
 *
 */
@SuppressWarnings("serial")
public class Modify extends JDialog implements ActionListener {
	
	JTable modifyFrame;
	private JTextField textTitle;
	private JTextField textAuthor;
	private JTextField textPublisher;
	private JTextField textYear;
	private JTextField textType;
	private JTextField textPages;
	private JTextField textPrice;
	private JTextField textLink;
	
	/**
	 * Default constructor displays the details about a book.
	 * @param book
	 */
	public Modify(Book book) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				//setVisible(false);
			}
		});
		setSize(339,575);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Title");
		lblNewLabel.setBounds(28, 24, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Author");
		lblNewLabel_1.setBounds(28, 49, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblPublisher = new JLabel("Publisher");
		lblPublisher.setBounds(28, 74, 46, 14);
		getContentPane().add(lblPublisher);
		
		JLabel lblYearPublisher = new JLabel("Year Published");
		lblYearPublisher.setBounds(28, 99, 70, 14);
		getContentPane().add(lblYearPublisher);
		
		JLabel lblNewLabel_2 = new JLabel("Type");
		lblNewLabel_2.setBounds(28, 124, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblPages = new JLabel("Pages");
		lblPages.setBounds(28, 149, 46, 14);
		getContentPane().add(lblPages);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(28, 174, 46, 14);
		getContentPane().add(lblPrice);
		
		JLabel lblLink = new JLabel("Link");
		lblLink.setBounds(28, 199, 46, 14);
		getContentPane().add(lblLink);
		
		textTitle = new JTextField();
		textTitle.setBounds(108, 21, 171, 20);
		getContentPane().add(textTitle);
		textTitle.setColumns(10);
		textTitle.setEditable(false);
		
		textAuthor = new JTextField();
		textAuthor.setBounds(108, 46, 171, 20);
		getContentPane().add(textAuthor);
		textAuthor.setColumns(10);
		textAuthor.setEditable(false);
		
		textPublisher = new JTextField();
		textPublisher.setBounds(108, 71, 171, 20);
		getContentPane().add(textPublisher);
		textPublisher.setColumns(10);
		textPublisher.setEditable(false);
		
		textYear = new JTextField();
		textYear.setBounds(108, 96, 171, 20);
		getContentPane().add(textYear);
		textYear.setColumns(10);
		textYear.setEditable(false);
		
		textType = new JTextField();
		textType.setBounds(108, 121, 171, 20);
		getContentPane().add(textType);
		textType.setColumns(10);
		textType.setEditable(false);
		
		textPages = new JTextField();
		textPages.setBounds(108, 146, 171, 20);
		getContentPane().add(textPages);
		textPages.setColumns(10);
		textPages.setEditable(false);
		
		textPrice = new JTextField();
		textPrice.setBounds(108, 171, 171, 20);
		getContentPane().add(textPrice);
		textPrice.setColumns(10);
		textPrice.setEditable(false);
		
		textLink = new JTextField();
		textLink.setBounds(108, 196, 171, 20);
		getContentPane().add(textLink);
		textLink.setColumns(10);
		textLink.setEditable(false);
		
		JButton btnAddToLibrary = new JButton("Add to Library");
		btnAddToLibrary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Bookmark.bookExists(book)) {
					book.setPages("0");
					Bookmark.db.add(book);
					Bookmark.gui.addBookRow(book);
					Bookmark.db.saveImage(book.getImage());
					String value = "ADDED: " + book.getTitle() + ":" + book.getISBN13();
					Bookmark.println(value);
					JOptionPane.showMessageDialog(null, "Book added to your libary!");
					Bookmark.saveCurrentState();
					setVisible(false);		
				}
				else {
					JOptionPane.showMessageDialog(null, "Book already exists in the library.");
				}
				
			}
		});
		btnAddToLibrary.setBounds(178, 502, 101, 23);
		if (!Bookmark.currentUser.equalsIgnoreCase("guest")) {
			getContentPane().add(btnAddToLibrary);
		}
		//getContentPane().add(btnAddToLibrary);
		
		JButton btnViewLink = new JButton("View Link");
		btnViewLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URL(book.getLink()).toURI());
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
		});
		btnViewLink.setBounds(28, 502, 89, 23);
		getContentPane().add(btnViewLink);
		setLocationRelativeTo(null);
		setTitle(book.getTitle()); 
		
		textTitle.setText(book.getTitle());
		textAuthor.setText(book.getAuthor());
		textPublisher.setText(book.getPublisher());
		if (book.getYear() == null) {
			textYear.setText("Unavailable");
		} else {
			textYear.setText(book.getYear());
		}
		textType.setText(book.getType());
		textPages.setText(book.getPages());
		if (book.getPrice() == 0.0d) {
			textPrice.setText("Unavailable");
		} else {
			textPrice.setText(Double.toString(book.getPrice()));
		}
		
		textLink.setText(book.getLink());
		
		
		if (book.getImage().equals(null)) {
			JLabel lblImage = new JLabel();
			lblImage.setBounds(38, 232, 241, 259);
			getContentPane().add(lblImage);
			
		} else {
			try {
				BufferedImage img = ImageIO.read(new URL(book.getImage()));
				JLabel lblImage = new JLabel(new ImageIcon(img));
				lblImage.setBounds(38, 232, 241, 259);
				getContentPane().add(lblImage);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		
		setModal(true);
		setVisible(true);
	}
	



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
