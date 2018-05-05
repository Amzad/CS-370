import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.DropMode;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.ScrollPane;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class GUI {
	JFrame jFrame = new JFrame("Bookmark");
	JTextArea textAreaSystemLog;
	DefaultTableModel tModel;
	DefaultTableModel sModel;
	JTabbedPane tabbedPane;
	JLabel lblResults;
	JComboBox comboBox;
	JLabel lblTerm;
	private JTextField textFieldSearch;
	
	public GUI() {
		jFrame.setResizable(false);
		jFrame.setSize(900, 725);
		jFrame.setLocationRelativeTo(null);
		jFrame.getContentPane().setLayout(new FlowLayout());
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		// ALL THE MENU ITEMS
		JMenuBar menuBar = new JMenuBar();
		jFrame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmImport = new JMenuItem("Import");
		mntmImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bookmark.importFile();
			}
		});
		mnFile.add(mntmImport);

		JMenuItem mntmExport = new JMenuItem("Export");
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Bookmark.exportFile();
			}
		});
		mnFile.add(mntmExport);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem mntmGenerateReport = new JMenuItem("Generate Report");
		mnFile.add(mntmGenerateReport);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		mnFile.add(mntmExit);

		JMenu mnNewMenu = new JMenu("Edit");
		menuBar.add(mnNewMenu);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		// END MENU 
		
		
		// Search Bar Panel
	
		
		// TabbedPane START
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		// Home Tab
		JPanel homePanel = new JPanel();
		tabbedPane.add("My Library", homePanel);
		
		
		tModel = new DefaultTableModel();
		JTable bookTable = new JTable();
		bookTable.setPreferredScrollableViewportSize(new Dimension(750, 575));
		JScrollPane scrollPaneTable = new JScrollPane(bookTable);
		//homePanel.setSize(900, 500);
		
		
		bookTable.setModel(tModel);
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		bookTable.setFillsViewportHeight(true);
		
		
		// Add the columns
		tModel.addColumn("ISBN13");
		//tModel.addColumn("ISBN10");
		tModel.addColumn("Title");
		tModel.addColumn("Author");
		tModel.addColumn("Page");
		
		
		//tModel.addColumn("Year");
		//tModel.addColumn("Publisher");
		//tModel.addColumn("Link");
		//tModel.addColumn("Pages");
		
		// Create but hide the isbn13 for easy data processing
		TableColumnModel cModel = bookTable.getColumnModel();
		cModel.removeColumn(cModel.getColumn(0));
		
		
		homePanel.add(scrollPaneTable);

		JPanel panel_1 = new JPanel();
		
		JButton btnClearLogs = new JButton("Clear logs");
		panel_1.add(btnClearLogs);
		btnClearLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textAreaSystemLog.setText("");
			}
		});

		jFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		jFrame.setVisible(true);
		
		enableAdmin(true);
		searchTab();
		
	}
	
	public void enableAdmin(boolean value) {
		JPanel adminPanel = new JPanel();
		tabbedPane.add("Admin", adminPanel);
		adminPanel.setLayout(null);

		textAreaSystemLog = new JTextArea();
		textAreaSystemLog.setRows(10);
		textAreaSystemLog.setColumns(60);
		textAreaSystemLog.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textAreaSystemLog);
		scrollPane.setBounds(10, 41, 486, 186);
		adminPanel.add(scrollPane);
		
		JLabel lblTransactionalLog = new JLabel("Transaction Log");
		lblTransactionalLog.setBounds(10, 26, 96, 14);
		adminPanel.add(lblTransactionalLog);
		
	}
	
	public void print(String message) {
		textAreaSystemLog.append(message + "\n");
		
	}
	
	public void print(int message) {
		textAreaSystemLog.append(message + "\n");
		
	}
	
	public void searchTab() {
		JPanel sTab = new JPanel();
		sTab.setLayout(null);
		
		//Search Fields
		JPanel panel = new JPanel();
		sTab.add(panel);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(578, 7, 65, 23);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addSearchRow(Bookmark.urlP.findBook(textFieldSearch.getText()));

			}
		});
		sTab.add(btnSearch);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setColumns(50);
		textFieldSearch.setBounds(162, 8, 406, 20);
		sTab.add(textFieldSearch);
		
		JLabel label = new JLabel("Search ");
		label.setBounds(122, 11, 36, 14);
		sTab.add(label);

		JLabel lblNewLabel = new JLabel("Page");
		lblNewLabel.setBounds(340, 38, 24, 14);
		
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int page = comboBox.getSelectedIndex() + 1;
				System.out.println(page);
				String term = lblTerm.getText();
				clearTable();
				addSearchRow(Bookmark.urlP.changePage(page, term));
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
		//sModel.addColumn("     ");
		sModel.addColumn("ISBN13");
		sModel.addColumn("Title");
		sModel.addColumn("Author");
		sModel.addColumn("Year Published");
		sModel.addColumn("Type");
		
		//sModel.addColumn("Price");
		
		
		JTable table = new JTable();
		table.setModel(sModel);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 66, 742, 510);
		
		
		
		sTab.add(lblNewLabel);
		sTab.add(comboBox);
		sTab.add(scrollPane);
		tabbedPane.addTab("Search", sTab);
		
		JLabel lblSearchResultsFor = new JLabel("Search Results for:");
		lblSearchResultsFor.setBounds(10, 38, 92, 14);
		sTab.add(lblSearchResultsFor);
		
	
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex() +1 != 333) {
					comboBox.setSelectedIndex(comboBox.getSelectedIndex() + 1);
				}
			}
		});
		btnNext.setBounds(427, 34, 55, 23);
		sTab.add(btnNext);
		
		JButton btnPrev = new JButton("Prev.");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((comboBox.getSelectedIndex() - 1 != 333) && (comboBox.getSelectedIndex() != 0)) {
					comboBox.setSelectedIndex(comboBox.getSelectedIndex() - 1);
				}
			}
		});
		btnPrev.setBounds(265, 34, 65, 23);
		sTab.add(btnPrev);
		lblTerm = new JLabel("");
		lblTerm.setBounds(112, 38, 46, 14);
		sTab.add(lblTerm);
		
		lblResults = new JLabel("Results: ");
		lblResults.setBounds(642, 41, 110, 14);
		sTab.add(lblResults);
		
		
		
		
		
		
		sTab.add(label);
	}
	
	public void setTerm() {
		lblTerm.setText(textFieldSearch.getText());
	}
	
	public void setPageCount(int count) {
		System.out.println(count);
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
	
	public void addRow(String[] data) {
	    tModel.addRow(data);
	    
	}
	
	public void addSearchRow(ArrayList<Book> data) {
		
		clearTable();
		setTerm();
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
}
