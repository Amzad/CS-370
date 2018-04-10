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
	private JTextField textFieldSearch;
	JTextArea textAreaSystemLog;
	DefaultTableModel tModel;
	JTabbedPane tabbedPane;
	
	public GUI() {
		jFrame.setResizable(false);
		jFrame.setSize(900, 600);
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
		JPanel panel = new JPanel();
		jFrame.getContentPane().add(panel);
		JLabel lblSearch = new JLabel("Search ");
		panel.add(lblSearch);
		textFieldSearch = new JTextField();
		panel.add(textFieldSearch);
		textFieldSearch.setColumns(50);
		
		// TabbedPane START
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		// Home Tab
		JPanel homePanel = new JPanel();
		tabbedPane.add("My Library", homePanel);
		
		
		tModel = new DefaultTableModel();
		JTable bookTable = new JTable();
		bookTable.setPreferredScrollableViewportSize(new Dimension(750, 450));
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
		
		JLabel lblNewLabel = new JLabel("Page");
		lblNewLabel.setBounds(344, 8, 24, 14);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(373, 5, 45, 20);
		comboBox.setPreferredSize(new Dimension(45, 20));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6"}));
		

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("     ");
		model.addColumn("ISBN13");
		model.addColumn("Title");
		model.addColumn("Author");
		model.addColumn("Year Published");
		model.addColumn("Type");
		model.addColumn("Price");
		
		
		JTable table = new JTable();
		table.setModel(model);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 33, 742, 418);
		
		
		
		
		
		
		sTab.add(lblNewLabel);
		sTab.add(comboBox);
		sTab.add(scrollPane);
		tabbedPane.addTab("Search", sTab);
		
		JLabel lblSearchResultsFor = new JLabel("Search Results for:");
		lblSearchResultsFor.setBounds(10, 8, 92, 14);
		sTab.add(lblSearchResultsFor);
		
		JLabel lblTerm = new JLabel("term");
		lblTerm.setBounds(109, 8, 46, 14);
		sTab.add(lblTerm);
	}
	
	public void getBook() {
		
	}
	
	public void addRow(String[] data) {
	    tModel.addRow(data);
	    
	}
	
	public DefaultTableModel getModel() {
		return tModel;
	}
}
