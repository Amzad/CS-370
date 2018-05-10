import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI {
	JFrame jFrame = new JFrame("Bookmark");
	JTextArea textAreaSystemLog;
	DefaultTableModel tModel;
	DefaultTableModel sModel;
	JTabbedPane tabbedPane;
	JTable table;
	JLabel lblResults;
	JComboBox comboBox;
	JLabel lblTerm;
	private JTextField textFieldSearch;
	JProgressBar progressBar;
	JButton btnNext;
	JButton btnPrev;
	JButton btnSearch;
	ArrayList<Book> data;
	ArrayList<Book> library = new ArrayList<Book>();
	Modify modify;
	int searchMode = 0;
	
	@SuppressWarnings("serial")
	public GUI(int value) {
		
		// Main frame <Open>
		jFrame.setResizable(false);
		jFrame.setSize(885, 686);
		jFrame.setLocationRelativeTo(null);
		jFrame.getContentPane().setLayout(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		// Menu Items <Open>
		JMenuBar menuBar = new JMenuBar();
		jFrame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmImport = new JMenuItem("Import"); // File Import Option
		mntmImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bookmark.importFile();
			}
		});
		mnFile.add(mntmImport);

		JMenuItem mntmExport = new JMenuItem("Export"); // File Export Option
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Bookmark.exportFile();
			}
		});
		mnFile.add(mntmExport);

		JMenuItem mntmExit = new JMenuItem("Exit"); // Exit Program
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem mntmGenerateReport = new JMenuItem("Generate Report");  // Generate Report
		mntmGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String email, subject, contents = "Hello, this report is generated list of items in the library. \n\n";
				email = JOptionPane.showInputDialog("Enter the recipient email");
				subject = "Your generated report from " + Bookmark.currentUser;
				for (int i = 0; i < library.size(); i++) {
					contents = contents + "Book: " + library.get(i).getTitle() + "\nAuthor: " + library.get(i).getAuthor() + "\nPublisher: " + library.get(i).getPublisher() 
							+ "\nYear Published: " + library.get(i).getYear() + "\nISBN13: " + library.get(i).getISBN13() + "\n\n";
				}
				System.out.println(contents);
				new Email(email, subject, contents);
			}
		});
		mnFile.add(mntmGenerateReport);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		mnFile.add(mntmExit);

		//JMenu mnNewMenu = new JMenu("Edit"); 
		//menuBar.add(mnNewMenu);

		JMenu mnAbout = new JMenu("About");
		mnAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "This program was created by \n Amzad Chowdhury \n for CS370 at Queens College.");
			}
		});
		menuBar.add(mnAbout);
		
		// Menu Items <Closed>

		
		// TabbedPane <Open>
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(10, 5, 859, 592);
		
		
		// Home Tab <Open>
		JPanel homePanel = new JPanel();
		//tabbedPane.add("My Library", homePanel);
		tModel = new DefaultTableModel() {
	    	@Override
	    	   public boolean isCellEditable(int row, int column) {       
	    	       return false; // or a condition at your choice with row and column
	    	   }
	    };
		JTable bookTable = new JTable();
		
		bookTable.setPreferredScrollableViewportSize(new Dimension(750, 575));
		bookTable.setModel(tModel);
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		bookTable.setFillsViewportHeight(true);
		
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2 ) {
					Book temp = null;
					if (bookTable.getSelectedRow() >= 0) {
						for (int i = 0; i < library.size(); i++) {
							Object number = bookTable.getModel().getValueAt(bookTable.getSelectedRow(), 0);
							if (number == library.get(i).getISBN13()) {
								temp = library.get(i);
								editBook(temp);
								break;
							}
						}
					}
				}
			}
		});
		
		JScrollPane scrollPaneTable = new JScrollPane(bookTable);
		
		
		// Add the columns
		tModel.addColumn("ISBN13");
		//tModel.addColumn("ISBN10");
		tModel.addColumn("Title");
		tModel.addColumn("Author");
		tModel.addColumn("Page");
		tModel.addColumn("Location");
		
		
		//tModel.addColumn("Year");
		//tModel.addColumn("Publisher");
		//tModel.addColumn("Link");
		//tModel.addColumn("Pages");
		
		// Create but hide the isbn13 for easy data processing
		TableColumnModel cModel = bookTable.getColumnModel();
		cModel.removeColumn(cModel.getColumn(0));
		homePanel.add(scrollPaneTable);
		
		if (value != 5) {
			tabbedPane.add("My Library", homePanel);
		}
		// Home Tab <Closed>

		JPanel panel_1 = new JPanel();
		JButton btnClearLogs = new JButton("Clear logs");
		panel_1.add(btnClearLogs);
		btnClearLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textAreaSystemLog.setText("");
			}
		});

		jFrame.getContentPane().add(tabbedPane);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 608, 226, 20);
		jFrame.getContentPane().add(progressBar);
		
		jFrame.setVisible(true);
		searchTab();
		enableAdmin(value);
		//tabbedPane.add("Admin", adminPanel);
	}
	
	public void enableAdmin(int value) {
		JPanel adminPanel = new JPanel();
		
		adminPanel.setLayout(null);

		textAreaSystemLog = new JTextArea();
		textAreaSystemLog.setRows(10);
		textAreaSystemLog.setColumns(80);
		textAreaSystemLog.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textAreaSystemLog);
		scrollPane.setBounds(10, 41, 704, 186);
		adminPanel.add(scrollPane);
		
		JLabel lblTransactionalLog = new JLabel("Transaction Log");
		lblTransactionalLog.setBounds(10, 26, 96, 14);
		adminPanel.add(lblTransactionalLog);
		
		//if (value == 1) {
			tabbedPane.add("Admin", adminPanel);
		//}
	
	}
	
	public void print(String message) {
		textAreaSystemLog.append(message + "\n");
		
	}
	
	public void print(int message) {
		textAreaSystemLog.append(message + "\n");
		
	}
	
	public void processAdvancedSearch(String term) {
		new Thread() {
        	public void run() {
        		addSearchRowAdvanced(Bookmark.urlP.findBookAdvanced(term), term);	
        	}
        }.start();
	}
	
	@SuppressWarnings("serial")
	public void searchTab() {
		JPanel sTab = new JPanel();
		sTab.setLayout(null);
		
		//Search Fields
		JPanel panel = new JPanel();
		sTab.add(panel);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(544, 7, 65, 23);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchMode = 0;
				new Thread() {
	            	public void run() {
	            		addSearchRow(Bookmark.urlP.findBook(textFieldSearch.getText()));	
	            	}
	            }.start();
				

			}
		});
		sTab.add(btnSearch);
		
		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		textFieldSearch.setColumns(50);
		textFieldSearch.setBounds(128, 8, 406, 20);
		sTab.add(textFieldSearch);
		
		JLabel label = new JLabel("Search ");
		label.setBounds(82, 11, 36, 14);
		sTab.add(label);

		JLabel lblNewLabel = new JLabel("Page");
		lblNewLabel.setBounds(340, 38, 24, 14);
		
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int page = comboBox.getSelectedIndex() + 1;
				String term = lblTerm.getText();
				clearTable();
				if (searchMode == 0) {
					addSearchRow(Bookmark.urlP.changePage(page, term));
				}
				else {
					addSearchRow(Bookmark.urlP.changePageAdvanced(page, term));
				}
				
			}
		});
		comboBox.setBounds(372, 35, 45, 20);
		comboBox.setPreferredSize(new Dimension(45, 20));
		

	    sModel = new DefaultTableModel() {
	    	@Override
	    	   public boolean isCellEditable(int row, int column) {       
	    	       return false; // or a condition at your choice with row and column
	    	   }
	    };
		sModel.addColumn("ISBN13");
		sModel.addColumn("Title");
		sModel.addColumn("Author");
		sModel.addColumn("Year Published");
		sModel.addColumn("Publisher");
		sModel.addColumn("Type");

		//sModel.addColumn("Price");
		
		
		table = new JTable();
		table.setModel(sModel);
		
		JScrollPane scrollPane = new JScrollPane(table);
	
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Book temp = null;
				if (arg0.getClickCount() == 2) {
					if (table.getSelectedRow() >= 0) {
						for (int i = 0; i < data.size(); i++) {
							if (table.getValueAt(table.getSelectedRow(), 0) == data.get(i).getISBN13()) {
								temp = data.get(i);
								viewBook(temp);
								break;
							}
						}
					}
					
				}
			}
		});
		scrollPane.setBounds(10, 66, 742, 510);
		
		
		
		sTab.add(lblNewLabel);
		sTab.add(comboBox);
		sTab.add(scrollPane);
		tabbedPane.addTab("Search", sTab);
		
		JLabel lblSearchResultsFor = new JLabel("Search Results for:");
		lblSearchResultsFor.setBounds(10, 38, 92, 14);
		sTab.add(lblSearchResultsFor);
		
	
		btnNext = new JButton("Next");
		btnNext.setEnabled(false);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (searchMode == 0) {
					if (comboBox.getSelectedIndex() +1 != 333 && (comboBox.getItemAt(comboBox.getSelectedIndex() +1) != null)) {
						new Thread() {
			            	public void run() {
			            		comboBox.setSelectedIndex(comboBox.getSelectedIndex() + 1);
			            	}
			            }.start();
						
		            }		
				}
				else {
					if (comboBox.getSelectedIndex() +1 != 333 && (comboBox.getItemAt(comboBox.getSelectedIndex() +1) != null)) {
						new Thread() {
			            	public void run() {
			            		comboBox.setSelectedIndex(comboBox.getSelectedIndex() + 1);
			            	}
			            }.start();
						
		            }		
				}		
			}
		});
		btnNext.setBounds(427, 34, 55, 23);
		sTab.add(btnNext);
		
		btnPrev = new JButton("Prev.");
		btnPrev.setEnabled(false);
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (searchMode == 0) {
					if ((comboBox.getSelectedIndex() - 1 != 333) && (comboBox.getSelectedIndex() != 0) && (comboBox.getItemAt(comboBox.getSelectedIndex() -1) != null)) {
						new Thread() {
			            	public void run() {
			            			comboBox.setSelectedIndex(comboBox.getSelectedIndex() - 1);	            		
			            	}
			            }.start();
	        		}	
				}
				else {
					if ((comboBox.getSelectedIndex() - 1 != 333) && (comboBox.getSelectedIndex() != 0) && (comboBox.getItemAt(comboBox.getSelectedIndex() -1) != null)) {
						new Thread() {
			            	public void run() {
			            			comboBox.setSelectedIndex(comboBox.getSelectedIndex() - 1);		            		
			            	}
			            }.start();
	        		}	

				}

			}
		});
		btnPrev.setBounds(265, 34, 65, 23);
		sTab.add(btnPrev);
		lblTerm = new JLabel("");
		lblTerm.setBounds(112, 38, 143, 14);
		sTab.add(lblTerm);
		
		lblResults = new JLabel("Results: ");
		lblResults.setBounds(642, 41, 110, 14);
		sTab.add(lblResults);
		
		sTab.add(label);
		
		JButton btnAdvancedSearch = new JButton("Advanced Search");
		btnAdvancedSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AdvancedSearch();
			}
		});
		btnAdvancedSearch.setBounds(619, 7, 116, 23);
		sTab.add(btnAdvancedSearch);
	}
	
	public void setTerm() {
		lblTerm.setText(textFieldSearch.getText());
	}
	
	public void setTermAdvanced(String term) {
		textFieldSearch.setText(term);
		lblTerm.setText(term);
	}
	
	@SuppressWarnings("rawtypes")
	public void setPageCount(int count) {
		int pageNum = (count/30);
		
		String[] pageCount = new String[pageNum];
		for (int i = 0; i < pageNum; i++) {
			pageCount[i] = Integer.toString(i+1);
		}
		comboBox.setModel(new DefaultComboBoxModel(pageCount));
		
	}
	
	public void setResultsCount (int count) {
		lblResults.setText("Results: " + Integer.toString(count));
	}
	
	public void setTotalCount (int count) {
		lblResults.setText("Results: " + Integer.toString(count));
	}

	public void addBookRow(Book book) {
		String[] data = {book.getISBN13(), book.getTitle(), book.getAuthor(), book.getPages(), book.getLocation()};
		library.add(book);
		tModel.addRow(data);
	    
	}
	
	public void addSearchRow(ArrayList<Book> data) {
		this.data = data;
		clearTable();
		setTerm();
		for(int i = 0; i < data.size(); i++) {
			Book temp = data.get(i);
			String[] tempS = {temp.getISBN13(), temp.getTitle(), temp.getAuthor(), temp.getYear(), temp.getPublisher(), temp.getType()};
			sModel.addRow(tempS);
			
		}
		
	}
	
	public void addSearchRowAdvanced(ArrayList<Book> data, String term) {
		this.data = data;
		clearTable();
		setTermAdvanced(term);
		for(int i = 0; i < data.size(); i++) {
			Book temp = data.get(i);
			String[] tempS = {temp.getISBN13(), temp.getTitle(), temp.getAuthor(), temp.getYear(), temp.getPublisher(), temp.getType()};
			sModel.addRow(tempS);
			
		}
		
	}
	
	public void clearTable() {
		while (sModel.getRowCount() != 0) {
			sModel.removeRow(0);
		}
	}
	
	public DefaultTableModel getModel() {
		return tModel;
	}
	
	public void setProgressBarValue(int min, int max) {
		progressBar.setMinimum(min);
		progressBar.setMaximum(max);
	}
	
	public void increaseProgressBar(int value) {
		progressBar.setValue(progressBar.getValue() + value);
		
	}
	
	public void resetProgressBar() {
		progressBar.setValue(0);
	}
	
	public void disableButtons() {
		comboBox.setEnabled(false);
		btnNext.setEnabled(false);
		btnPrev.setEnabled(false);
		btnSearch.setEnabled(false);
	}
	
	public void enableButtons() {
		comboBox.setEnabled(true);
		btnNext.setEnabled(true);
		btnPrev.setEnabled(true);
		btnSearch.setEnabled(true);
		
	}
	
	public void viewBook(Book book) {
			new Modify(book);

	}
	
	public void editBook(Book book) {
			new EditBook(book);
	}
	
	public void updateBook(Book book) {
		
	}
}
