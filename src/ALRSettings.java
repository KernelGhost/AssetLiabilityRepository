import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSeparator;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.text.DecimalFormat;

public class ALRSettings extends JDialog {
	private static final long serialVersionUID = -521049519912823110L;
	private JPanel contentPane;
	private JTextField txtDName;	// Displays the database name
	private JTextField txtDomn;		// Displays the domain
	private JSpinner spinPort;		// Displays the port
	
	// Called on initialisation of the settings window
	private void Initialise() {
		txtDName.setText(Main.database_name);
		txtDName.setCaretPosition(0);
		
		txtDomn.setText(Main.database_domain);
		txtDomn.setCaretPosition(0);
		
		spinPort.setValue(Main.database_port);
	}
	
	// Called on saving modified ALR Settings
	private void Save() {
		Main.database_name = txtDName.getText().trim();
		Main.database_domain = txtDomn.getText().trim();
		Main.database_port = (Integer) spinPort.getValue();
		Close();
	}
	
	// Called on close to dispose the JFrame
	private void Close() {
		this.dispose();
	}
	
	public ALRSettings() {
		// Set Title
		setTitle("ALR Settings");
		
		// Set Window Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmNewEditHolder.class.getResource("/resources/graphics/Icon.png")));
		
		// Make Non-resizable
		setResizable(false);
		
		// Set JFrame to self-dispose on closing
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	Close();
		    }
		});
		
		// Create Main JPanel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setPreferredSize(new Dimension(280, 150));
		setContentPane(contentPane);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		// LABELS
		// Database Domain
		JLabel lblDomn = new JLabel("Domain:");
		lblDomn.setBounds(8, 34, 110, 26);
		contentPane.add(lblDomn);
		
		// Database Port
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(8, 62, 110, 26);
		contentPane.add(lblPort);
		
		// Database Name
		JLabel lblDName = new JLabel("Database Name:");
		lblDName.setBounds(8, 6, 110, 26);
		contentPane.add(lblDName);
		
		// TEXTFIELDS
		// Database Name
		txtDName = new JTextField();
		txtDName.setBounds(120, 6, 154, 26);
		contentPane.add(txtDName);
		txtDName.setColumns(10);
		
		// Database Domain
		txtDomn = new JTextField();
		txtDomn.setBounds(120, 34, 154, 26);
		contentPane.add(txtDomn);
		txtDomn.setColumns(10);
		
		// SPINNERS
		// Database Port
		SpinnerNumberModel spinPortmodel = new SpinnerNumberModel(0, 0, 65535, 1);
		spinPort = new JSpinner(spinPortmodel);
		JSpinner.NumberEditor spinPorteditor = (JSpinner.NumberEditor) spinPort.getEditor();
        DecimalFormat spinPortformat = spinPorteditor.getFormat();
        spinPortformat.setGroupingUsed(false);
        JComponent PortFieldEditor = spinPort.getEditor();
        JSpinner.DefaultEditor PortSpinnerEditor = (JSpinner.DefaultEditor) PortFieldEditor;
        PortSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		spinPort.setBounds(120, 62, 154, 26);
		contentPane.add(spinPort);
		
		// BUTTONS
		// Save
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save();
			}
		});
		btnSave.setBounds(174, 104, 100, 40);
		contentPane.add(btnSave);
		
		// Cancel
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Close();
			}
		});
		btnCancel.setBounds(6, 104, 100, 40);
		contentPane.add(btnCancel);
		
		// SEPARATORS
		JSeparator separator = new JSeparator();
		separator.setBounds(8, 96, 266, 12);
		contentPane.add(separator);
		
		// Initialise fields
		Initialise();
	}
}