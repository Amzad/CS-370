import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditBook extends JDialog implements ActionListener {
	
	JTable modifyFrame;
	private JTextField textTitle;
	private JTextField textAuthor;
	private JTextField textPublisher;
	private JTextField textYear;
	private JTextField textType;
	private JTextField textPages;
	private JTextField textLocation;
	private JTextField textFieldISBN10;
	private JTextField textFieldISBN13;
	
	public EditBook(Book book) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				//setVisible(false);
			}
		});
		setSize(339,575);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Title");
		lblNewLabel.setBounds(28, 84, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Author");
		lblNewLabel_1.setBounds(28, 109, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblPublisher = new JLabel("Publisher");
		lblPublisher.setBounds(28, 134, 46, 14);
		getContentPane().add(lblPublisher);
		
		JLabel lblYearPublisher = new JLabel("Year Published");
		lblYearPublisher.setBounds(28, 159, 70, 14);
		getContentPane().add(lblYearPublisher);
		
		JLabel lblNewLabel_2 = new JLabel("Type");
		lblNewLabel_2.setBounds(28, 184, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblPages = new JLabel("Pages");
		lblPages.setBounds(28, 209, 46, 14);
		getContentPane().add(lblPages);
		
		textTitle = new JTextField();
		textTitle.setBounds(108, 81, 171, 20);
		getContentPane().add(textTitle);
		textTitle.setColumns(10);
		textTitle.setEditable(false);
		
		textAuthor = new JTextField();
		textAuthor.setBounds(108, 106, 171, 20);
		getContentPane().add(textAuthor);
		textAuthor.setColumns(10);
		textAuthor.setEditable(false);
		
		textPublisher = new JTextField();
		textPublisher.setBounds(108, 131, 171, 20);
		getContentPane().add(textPublisher);
		textPublisher.setColumns(10);
		textPublisher.setEditable(false);
		
		textYear = new JTextField();
		textYear.setBounds(108, 156, 171, 20);
		getContentPane().add(textYear);
		textYear.setColumns(10);
		textYear.setEditable(false);
		
		textType = new JTextField();
		textType.setBounds(108, 181, 171, 20);
		getContentPane().add(textType);
		textType.setColumns(10);
		textType.setEditable(false);
		
		textPages = new JTextField();
		textPages.setBounds(108, 206, 171, 20);
		getContentPane().add(textPages);
		textPages.setColumns(10);

		JButton btnViewLink = new JButton("View Link");
		btnViewLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URL(book.getLink()).toURI());
				}
				catch (Exception e) {
				}
			}
		});
		btnViewLink.setBounds(10, 502, 89, 23);
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

		JLabel lblLocation = new JLabel("Location");
		lblLocation.setBounds(28, 234, 46, 14);
		getContentPane().add(lblLocation);
		
		textLocation = new JTextField();
		textLocation.setBounds(108, 231, 171, 20);
		getContentPane().add(textLocation);
		textLocation.setColumns(10);
		textLocation.setText(book.getLocation());
		
		JLabel lblNewLabel_3 = new JLabel("ISBN10");
		lblNewLabel_3.setBounds(28, 59, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblIsbn = new JLabel("ISBN13");
		lblIsbn.setBounds(28, 34, 46, 14);
		getContentPane().add(lblIsbn);
		
		textFieldISBN10 = new JTextField();
		textFieldISBN10.setEnabled(false);
		textFieldISBN10.setBounds(108, 56, 171, 20);
		getContentPane().add(textFieldISBN10);
		textFieldISBN10.setColumns(10);
		textFieldISBN10.setText(book.getISBN10());
		
		textFieldISBN13 = new JTextField();
		textFieldISBN13.setEnabled(false);
		textFieldISBN13.setBounds(108, 31, 171, 20);
		getContentPane().add(textFieldISBN13);
		textFieldISBN13.setColumns(10);
		textFieldISBN13.setText(book.getISBN13());
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				book.setPages(textPages.getText());
				book.setLocation(textLocation.getText());
				Bookmark.updateBook(book);
				Bookmark.db.replaceBook(book);
				setVisible(false);
			}
		});
		btnSave.setBounds(224, 502, 89, 23);
		getContentPane().add(btnSave);
		
		JButton btnDeleteBook = new JButton("Delete Book");
		btnDeleteBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bookmark.db.remove(book.getISBN13());
				
			}
		});
		btnDeleteBook.setBounds(116, 502, 89, 23);
		getContentPane().add(btnDeleteBook);
		
		
		if (book.getImage().equals(null)) {
			JLabel lblImage = new JLabel();
			lblImage.setBounds(38, 232, 241, 259);
			getContentPane().add(lblImage);
			
		} else {
			try {
				
				String workingDirectory = System.getProperty("user.dir");
				String link = book.getImage();// Book Link
				link = link.substring(link.lastIndexOf("/")+1, link.length()); // Book filename
				String uri = workingDirectory + "\\Image\\" + link;
				BufferedImage img = ImageIO.read(new File(uri));

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
