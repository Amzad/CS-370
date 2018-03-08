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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.ScrollPane;
import javax.swing.JTable;

public class GUI {
	JFrame jFrame = new JFrame("Bookmark");
	private JTextField textField;
	JTextArea textAreaSystemLog;

	public GUI() {
		jFrame.setSize(700, 500);
		jFrame.setLocationRelativeTo(null);
		jFrame.getContentPane().setLayout(new FlowLayout());
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		JMenuBar menuBar = new JMenuBar();
		jFrame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("FIle");
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
		mnFile.add(mntmExit);

		JMenu mnNewMenu = new JMenu("Edit");
		menuBar.add(mnNewMenu);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		jFrame.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		jFrame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Search ");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(30);
		
		// Home Tab
		JPanel homePanel = new JPanel();
		JPanel adminPanel = new JPanel();
		adminPanel.setSize(new Dimension(0, 50));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("Home", homePanel);
		
		
		DefaultTableModel tModel = new DefaultTableModel();
		JTable bookTable = new JTable();
		bookTable.doLayout();
		bookTable.setFillsViewportHeight(true);
		String[] Column = {"Title", "Author"};
		bookTable.setModel(tModel);
		
		
		ScrollPane scrollPane_1 = new ScrollPane();
		scrollPane_1.add(bookTable);
		homePanel.add(scrollPane_1);
		
		tModel.addColumn("Title");
		tModel.addColumn("Author");
		tModel.addRow(Column);
		
		
		
		tabbedPane.add("Admin", adminPanel);
		
		JLabel lblS = new JLabel("System Log");
		lblS.setHorizontalAlignment(SwingConstants.CENTER);
		
		textAreaSystemLog = new JTextArea();
		textAreaSystemLog.setEditable(false);
		//textAreaSystemLog.setRows(10);
		//textAreaSystemLog.setColumns(83);
		JScrollPane scrollPane = new JScrollPane(textAreaSystemLog);
		
		adminPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		adminPanel.add(lblS);
		
		JButton btnClearLogs = new JButton("Clear logs");
		btnClearLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textAreaSystemLog.setText("");
			}
		});
		adminPanel.add(btnClearLogs);
		adminPanel.add(scrollPane);
		//tabbedPane.add(component)
		jFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		// jFrame.setLayout();

		jFrame.setVisible(true);
	}
	
	public void print(String message) {
		textAreaSystemLog.append(message + "\n");
		
	}
	
	public void searchTab(String term) {
		
	}

}
