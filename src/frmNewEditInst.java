import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import CustomTypes.Institution;

public class frmNewEditInst extends JFrame {
	private static final long serialVersionUID = -8719131650619047042L;
	// Institution Information
	private Institution inst_result;				// Stores the institution selected for editing (null when creating a new institution)
	
	// GUI Elements
	private JPanel contentPane;					// The JPanel for the JFrame
	private JTextField txtName;					// Displays the institution name
	private JTextField txtCode;					// Displays the institution name
	private JTextField txtAddress;				// Displays the institution address
	private JTextField txtComments;				// Displays institution comments
	
	// Called if the user elected to edit an existing Institution
	private void Initialise() {
		// Populate JTextFields
		txtName.setText(inst_result.GetName().trim());
		txtName.setCaretPosition(0);
		
		txtCode.setText(inst_result.GetCode().trim());
		txtCode.setCaretPosition(0);
		
		if (inst_result.GetAddress() != null) {
			txtAddress.setText(inst_result.GetAddress().trim());
			txtAddress.setCaretPosition(0);
		} else {
			txtAddress.setText("");
		}
		
		if (inst_result.GetComment() != null) {
			txtComments.setText(inst_result.GetComment().trim());
			txtComments.setCaretPosition(0);
		} else {
			txtComments.setText("");
		}
	}
	
	// Called when user requests to save changes to either a new or existing institution
	private void StoreInst() {
		String strName;					// Stores the institution name
		String strCode;					// Stores the institution code
		String strAddress;				// Stores the institution address
		String strComments;				// Stores institution comments
		Boolean boolAddrComm = true;	// Used to determine if fields exceed the maximum allowable string length for the database
		
		// Fields that are left blank are stored as null, otherwise they are trimmed and stored
		// Name
		if (!txtName.getText().trim().equals("")) {
			strName = txtName.getText().trim();
		} else {
			strName = null;
		}
		
		// Code
		if (!txtCode.getText().trim().equals("")) {
			strCode = txtCode.getText().trim();
		} else {
			strCode = null;
		}
		
		// Address
		if (!txtAddress.getText().trim().equals("")) {
			strAddress = txtAddress.getText().trim();
		} else {
			strAddress = null;
		}
		
		// Comments
		if (!txtComments.getText().trim().equals("")) {
			strComments = txtComments.getText().trim();
		} else {
			strComments = null;
		}
		
		// Check if all mandatory fields (institution name and code) are provided
		if (strName != null & strCode != null) {
			// Check if fields exceed the maximum allowable VARCHAR length
			// Check Name
			if (strName.length() > Main.VARCHAR_MAX_LENGTH) {
				boolAddrComm = false;
			}
			
			// Check Code
			if (strCode.length() > Main.INSTCODE_MAX_LENGTH) {
				boolAddrComm = false;
			}
			
			// Check Address
			if (strAddress != null) {
				if (strAddress.length() > Main.VARCHAR_MAX_LENGTH) {
					boolAddrComm = false;
				}
			}
			
			// Check Comments
			if (strComments != null) {
				if (strComments.length() > Main.VARCHAR_MAX_LENGTH) {
					boolAddrComm = false;
				}
			}
			
			if (boolAddrComm) {
				try {
					// Check if we are creating a new institution or editing an existing institution
					if (inst_result == null) {
						// A new institution is being created
						// Check if an existing institution by the same name or code does not exist
						if ((!Main.database_handler.DoesInstitutionNameExist(strName)) & (!Main.database_handler.DoesInstitutionCodeExist(strCode))) {
							Institution new_institution = new Institution(strName, null, strCode.toUpperCase(), strAddress, strComments);
							try {
								Main.database_handler.NewInstitution(new_institution);
								Close();
							} catch (SQLException e) {
								e.printStackTrace();
								MessageBox.Error("There was a database related error adding the new institution to the database.", "New Institution");
							}
						} else {
							MessageBox.Information("An institution cannot have the same name or code as an existing institution.", "New Institution");
						}
					} else {
						// An existing institution is being edited
						// Check if an existing institution by the same name or code does not exist (excluding the institution being edited by the user)
						if ((!Main.database_handler.DoesInstitutionNameExist(strName) | inst_result.GetName().toLowerCase().equals(strName.toLowerCase())) & 
								(!Main.database_handler.DoesInstitutionCodeExist(strCode) | inst_result.GetCode().toUpperCase().equals(strCode.toUpperCase()))) {
							Institution edited_institution = new Institution(strName, inst_result.GetID(), strCode.toUpperCase(), strAddress, strComments);
							try {
								Main.database_handler.EditInstitution(edited_institution);
								Close();
							} catch (SQLException e) {
								MessageBox.Error("There was a database related error saving your edits to the existing institution", "Edit Institution");
							}
						} else {
							MessageBox.Information("An institution cannot have the same name or code as an existing institution.", "Edit Institution");
						}
					}
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error checking if an existing institution with the same name or code exists.", "New/Edit Institution");
				}
			} else {
				MessageBox.Information("Please ensure the institution code is no longer than " + Integer.toString(Main.INSTCODE_MAX_LENGTH) + " characters and all other fields are no longer than "+ Integer.toString(Main.VARCHAR_MAX_LENGTH) + " characters.", "New/Edit Institution");
			}
		} else {
			MessageBox.Information("Please ensure the institution has a name and valid code.", "New/Edit Institution");
		}
	}
	
	// Called on close to dispose the JFrame
	private void Close() {
		this.dispose();
	}
	
	/* Called on creation of a new JFrame. Pass the institution to be edited. Null is passed as the
	 * InstResult if a new service is being created. */
	public frmNewEditInst(Institution instresult) {
		this.inst_result = instresult;		// Store the institution to edit (or store null if creating a new institution)
		
		// Set Title
		if (inst_result == null) {
			setTitle("New Institution");
		} else {
			setTitle("Edit Institution");
		}
		
		// Set Window Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmNewEditInst.class.getResource("/resources/graphics/Icon.png")));
		
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
		contentPane.setPreferredSize(new Dimension(376, 186));
		setContentPane(contentPane);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// BORDER AND PANEL
		Border border_panel = BorderFactory.createTitledBorder("Institution Details");
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 365, 146);
		panel.setBorder(border_panel);
		contentPane.add(panel);
		panel.setLayout(null);
		
		// LABELS
		// Name
		JLabel lblName = new JLabel("*Name:");
		lblName.setBounds(20, 26, 80, 16);
		panel.add(lblName);
		
		// Code
		JLabel lblCode = new JLabel("*Code:");
		lblCode.setBounds(20, 56, 80, 16);
		panel.add(lblCode);
		
		// Address
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(20, 86, 80, 16);
		panel.add(lblAddress);
		
		// Comments
		JLabel lblComments = new JLabel("Comments:");
		lblComments.setBounds(20, 116, 80, 16);
		panel.add(lblComments);
		
		// TEXTFIELDS
		// Name
		txtName = new JTextField();
		txtName.setBounds(100, 22, 258, 24);
		panel.add(txtName);
		txtName.setColumns(10);
		
		// Code
		txtCode = new JTextField();
		txtCode.setBounds(100, 52, 258, 24);
		panel.add(txtCode);
		txtCode.setColumns(10);
		
		// Address
		txtAddress = new JTextField();
		txtAddress.setBounds(100, 82, 258, 24);
		panel.add(txtAddress);
		txtAddress.setColumns(10);
		
		// Comments
		txtComments = new JTextField();
		txtComments.setBounds(100, 112, 258, 24);
		panel.add(txtComments);
		txtComments.setColumns(10);
		
		// BUTTONS
		// Ok Button
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StoreInst();
			}
		});
		btnOk.setBounds(210, 151, 80, 29);
		contentPane.add(btnOk);
		
		// Cancel Button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Close();
			}
		});
		btnCancel.setBounds(290, 151, 80, 29);
		contentPane.add(btnCancel);
		
		// Populate fields if an existing institution is being edited
		if (this.inst_result != null) {
			Initialise();
		}
	}
}