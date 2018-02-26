import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Panel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

public class GUI {
	JFrame jFrame = new JFrame("Bookmark");
	
	public GUI() {
		jFrame.setSize(700, 500);
		
		JMenuBar menuBar = new JMenuBar();
		jFrame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("FIle");
		menuBar.add(mnFile);
		
		JMenuItem mntmImport = new JMenuItem("Import");
		mnFile.add(mntmImport);
		
		JMenuItem mntmExport = new JMenuItem("Export");
		mnFile.add(mntmExport);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnNewMenu = new JMenu("Edit");
		menuBar.add(mnNewMenu);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JToolBar toolBar = new JToolBar();
		jFrame.getContentPane().add(toolBar, BorderLayout.SOUTH);
		// jFrame.setLayout();
		
		
		
		jFrame.setVisible(true);
	}

}
