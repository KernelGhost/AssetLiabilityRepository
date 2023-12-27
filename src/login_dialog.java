import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class login_dialog extends JDialog {
	private static final long serialVersionUID = 4538831962479398438L;
	
	// Settings Window
	private ALRSettings ALRSettings;			// The 'ALR Settings' Window
	
	// GUI Elements
	private final JPanel contentPanel;			// The JPanel for the JFrame
	private JTextField txtUsername;				// Displays the database username
	private JPasswordField txtPassword;			// Displays the database password
	
	// Establishes a connection with the database using the provided username and password
	private void Connect(String database_username, String database_password) {
		Main.Launch("jdbc:mariadb://" + Main.database_domain + ":" + Main.database_port.toString() + "/" + Main.database_name + "?user=" + database_username + "&password=" + database_password);
	}
	
	public login_dialog() {
		// Set Title
		setTitle("ALR Login");
		
		// Set Window Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(login_dialog.class.getResource("/resources/graphics/appicon.png")));
		
		// Make Non-resizable
		setResizable(false);
		
		// Set JFrame to dispose
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		// Create Main JPanel
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setPreferredSize(new Dimension (280, 150));
		contentPanel.setLayout(null);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// Set up menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu File_menu = new JMenu("File");
		JMenuItem SettingsItem = new JMenuItem("Settings");
		JMenuItem ExitItem = new JMenuItem("Exit");
		
		menuBar.setPreferredSize(new Dimension(280,20));

		SettingsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ALRSettings = new ALRSettings();
				ALRSettings.addWindowListener(new WindowAdapter() { 
				    @Override public void windowClosed(WindowEvent e) { 
				    	Main.dialog.setVisible(true);
				    }
				});
				ALRSettings.setVisible(true);
				Main.dialog.setVisible(false);
			}
		});
		ExitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Close the whole application
				System.exit(0);
			}
		});
		
		menuBar.add(File_menu);
		File_menu.add(SettingsItem);
		File_menu.add(ExitItem);
		
		setJMenuBar(menuBar);
		
		// LABELS
		// Instructions
		JLabel txtInstructions = new JLabel("Please login with your credentials.");
		txtInstructions.setHorizontalAlignment(SwingConstants.CENTER);
		txtInstructions.setBounds(6, 6, 268, 16);
		contentPanel.add(txtInstructions);
		
		// Username
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setBounds(6, 34, 80, 16);
		contentPanel.add(lblUsername);
		
		// Password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(6, 63, 80, 16);
		contentPanel.add(lblPassword);
		
		// TEXTFIELDS
		// Username
		txtUsername = new JTextField();
		txtUsername.setBounds(98, 30, 169, 26);
		contentPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		// Password
		txtPassword = new JPasswordField();
		txtPassword.setBounds(98, 58, 169, 26);
		contentPanel.add(txtPassword);
		txtPassword.setColumns(10);
		
		// SEPARATORS
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 91, 268, 16);
		contentPanel.add(separator);
		
		// PANELS
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		// BUTTONS
		// Ok Button
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connect(txtUsername.getText(), String.valueOf(txtPassword.getPassword()));
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		// Cancel Button
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0); 
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
}