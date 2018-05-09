import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JFrame;
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
import java.io.IOException;

import javax.swing.JSplitPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.UIManager;

public class Login extends JDialog implements ActionListener {
	
	JFrame jFrame = new JFrame("Login");
	JTable modifyFrame;
	private JTextField textExistingUser;
	private JTextField textExistingPassword;
	private JTextField textNewPasswordConfirm;
	private JTextField textNewPassword;
	private JTextField textNewUser;

	public Login() {
		jFrame.setSize(290, 471);
		jFrame.getContentPane().setLayout(null);
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("Login");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 11, 255, 205);
		jFrame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lbl1 = new JLabel("Existing User?");
		lbl1.setBounds(10, 11, 67, 14);
		panel.add(lbl1);

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(10, 36, 51, 14);
		panel.add(lblNewLabel);

		textExistingUser = new JTextField();
		textExistingUser.setText("Amzad");
		textExistingUser.setBounds(71, 33, 171, 20);
		panel.add(textExistingUser);
		textExistingUser.setColumns(10);
		textExistingUser.setEditable(true);


		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(10, 64, 46, 14);
		panel.add(lblNewLabel_1);

		textExistingPassword = new JTextField();
		textExistingPassword.setText("Amzad");
		textExistingPassword.setBounds(71, 61, 171, 20);
		panel.add(textExistingPassword);
		textExistingPassword.setColumns(10);
		textExistingPassword.setEditable(true);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int value = Bookmark.tryLogin(textExistingUser.getText(), textExistingPassword.getText());
				if (value == 3) JOptionPane.showMessageDialog(null, "Invalid Password");
				if (value == 2) JOptionPane.showMessageDialog(null, "Invalid Username");
				if (value == 1) {
					Bookmark.currentUser = textExistingUser.getText();
					jFrame.setVisible(false);
					Bookmark.loadComponents(value);
				}
				if (value == 0) {
					Bookmark.currentUser = textExistingUser.getText();
					jFrame.setVisible(false);
					Bookmark.loadComponents(value);
				}
				if (value == 4) {
				}
				if (value == 5) {
					Bookmark.currentUser = textExistingUser.getText();
					jFrame.setVisible(false);
					Bookmark.loadComponents(value);
				}
			}
		});
		btnLogin.setBounds(71, 171, 89, 23);
		panel.add(btnLogin);
		
		JTextPane txtpnToOnlyView = new JTextPane();
		txtpnToOnlyView.setBackground(UIManager.getColor("Button.background"));
		txtpnToOnlyView.setText("To only view data, you can login as\r\nUsername: Guest\r\nPassword: Guest");
		txtpnToOnlyView.setBounds(10, 108, 162, 62);
		panel.add(txtpnToOnlyView);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBounds(10, 227, 255, 194);
		jFrame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewUser = new JLabel("New User? Register Now!");
		lblNewUser.setBounds(10, 11, 122, 14);
		panel_1.add(lblNewUser);

		JLabel lblNewusername = new JLabel("Username");
		lblNewusername.setBounds(10, 48, 49, 14);
		panel_1.add(lblNewusername);

		textNewUser = new JTextField();
		textNewUser.setBounds(69, 45, 171, 20);
		panel_1.add(textNewUser);
		textNewUser.setColumns(10);
		textNewUser.setEditable(true);

		JLabel lblNewPassword = new JLabel("Password");
		lblNewPassword.setBounds(10, 79, 46, 14);
		panel_1.add(lblNewPassword);

		textNewPassword = new JTextField();
		textNewPassword.setBounds(69, 76, 171, 20);
		panel_1.add(textNewPassword);
		textNewPassword.setColumns(10);
		textNewPassword.setEditable(true);

		textNewPasswordConfirm = new JTextField();
		textNewPasswordConfirm.setBounds(69, 104, 171, 20);
		panel_1.add(textNewPasswordConfirm);
		textNewPasswordConfirm.setColumns(10);
		textNewPasswordConfirm.setEditable(true);

		JLabel lblConfirmPassword = new JLabel("Confirm");
		lblConfirmPassword.setBounds(10, 107, 49, 14);
		panel_1.add(lblConfirmPassword);

		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((textNewPassword.getText().equals(textNewPasswordConfirm.getText()) && (textNewUser.getText().length() > 0)) && (textNewPassword.getText().length() > 0)) {
					if(Bookmark.newUser(textNewUser.getText(), textNewPassword.getText())) {					
						textExistingUser.setText(textNewUser.getText());
						textExistingPassword.setText(textNewPassword.getText());
						textNewPassword.setText("");
						textNewPasswordConfirm.setText("");
						textNewUser.setText("");
						JOptionPane.showMessageDialog(null, "New user account created");
					}
				}
				else {
					if ((textNewUser.getText().length() == 0) | (textNewPassword.getText().length() == 0)){
					JOptionPane.showMessageDialog(null, "No username or password entered.");
				} else {
					JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
				}	
			} 
			}
		});
		btnNewButton.setBounds(69, 160, 89, 23);
		panel_1.add(btnNewButton);

		setModal(true);
		jFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void readFile() {
		
	}
}
