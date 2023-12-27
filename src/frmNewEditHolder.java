import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import CustomTypes.Holder;

public class frmNewEditHolder extends JFrame {
	private static final long serialVersionUID = -8719131650619047042L;
	// Holder Information
	private Holder holder_result;			// Stores the selected holder passed to the JFrame for editing (null when creating a new holder)
	
	// GUI Elements
	private JPanel contentPane;					// The JPanel for the JFrame
	private JTextField txtName;					// Displays the holder name
	private JTextField txtTFN;					// Displays the holder TFN
    private DatePicker DOBDatePicker;			// Displays the holder DOB
	
	// Called if the user elected to edit an existing Institution
	private void Initialise() {		
		txtName.setText(holder_result.GetName().trim());
		txtName.setCaretPosition(0);
		
		if (holder_result.GetTFN() != null) {
			txtTFN.setText(holder_result.GetTFN().trim());
			txtTFN.setCaretPosition(0);
		} else {
			txtTFN.setText("");
		}
		
		if (holder_result.GetDOB() != null) {
			// Try to set the datePicker date using the date string
			Date date = holder_result.GetDOB();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			
			int intDay = calendar.get(Calendar.DAY_OF_MONTH);
			int intMonth = calendar.get(Calendar.MONTH);		// Caveat: January is '0' NOT '1'
			int intYear = calendar.get(Calendar.YEAR);
			
			DOBDatePicker.setDate(LocalDate.of(intYear, intMonth + 1, intDay));
		} else {
			DOBDatePicker.clear();
		}
	}
	
	// Called when user requests to save changes to either a new or existing holder
	private void StoreHolder() {
		String strName;									// Stores the holder name
		String strTFN;									// Stores the holder TFN
		Date DOB;										// Stores the holder DOB as a string
		Boolean boolLength = true;						// Used to determine if fields exceed the maximum allowable string length for the database
		
		// Fields that are left blank are stored as null, otherwise they are trimmed and stored
		// Name
		if (!txtName.getText().trim().equals("")) {
			strName = txtName.getText().trim();
		} else {
			strName = null;
		}
		
		// TFN
		if (!txtTFN.getText().trim().equals("")) {
			strTFN = txtTFN.getText().trim();
		} else {
			strTFN = null;
		}
		
		// DOB
		if (DOBDatePicker.getDate() != null) {
			DOB = java.sql.Date.valueOf(DOBDatePicker.getDate());
		} else {
			DOB = null;
		}
		
		// Check if all mandatory fields (holder name) are provided
		if (strName != null) {
			// Check if fields exceed the maximum allowable VARCHAR length
			// Check Holder Name
			if (strName.length() > Main.VARCHAR_MAX_LENGTH) {
				boolLength = false;
			}
			
			// Check Holder TFN
			if (strTFN != null) {
				if (!strTFN.matches("[0-9]+") | (strTFN.length() < Main.HOLDERTFN_SIZE_RANGE[0]) | (strTFN.length() > Main.HOLDERTFN_SIZE_RANGE[1])) {
					boolLength = false;
				}
			}
			
			if (boolLength) {
				try {
					// Check if we are creating a new holder or editing an existing holder
					if (holder_result == null) {
						// A new holder is being created
						// Check if an existing holder by the same name does not exist
						if (!Main.database_handler.DoesHolderNameExist(strName)) {
							Holder new_holder = new Holder(strName, DOB, strTFN, null);
							try {
								Main.database_handler.NewHolder(new_holder);
								Close();
							} catch (SQLException e) {
								MessageBox.Error("There was a database related error adding the new holder to the database.", "New Holder");
							}
						} else {
							MessageBox.Information("A holder cannot have the same name as an existing holder.", "New Holder");
						}
					} else {
						// An existing holder is being edited
						// Check if an existing holder by the same name does not exist (excluding the holder being edited by the user)
						if (!Main.database_handler.DoesHolderNameExist(strName) | holder_result.GetName().toLowerCase().equals(strName.toLowerCase())) {
							Holder edited_holder = new Holder(strName, DOB, strTFN, holder_result.GetID());
							try {
								Main.database_handler.EditHolder(edited_holder);
								Close();
							} catch (SQLException e) {
								e.printStackTrace();
								MessageBox.Error("There was a database related error saving your edits to the existing holder", "Edit Holder");
							}
						} else {
							MessageBox.Information("A holder cannot have the same name as an existing holder.", "Edit Holder");
						}
					}
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error checking if an existing holder with the same name exists.", "New/Edit Holder");
				}
			} else {
				MessageBox.Information("Please ensure the holder name is no longer than "+ Integer.toString(Main.VARCHAR_MAX_LENGTH) + " characters."
							+ System.lineSeparator() + "Please ensure the TFN is either 8 or 9 digits long and contains no spaces, letters or special characters.", "New/Edit Holder");
			}
		} else {
			MessageBox.Information("Please ensure the holder has a name.", "New/Edit Holder");
		}
	}
	
	// Called on close to dispose the JFrame
	private void Close() {
		this.dispose();
	}
	
	/* Called on creation of a new JFrame. Pass the holder to be edited. Null is passed as the
	 * HolderResult if a new holder is being created. */
	public frmNewEditHolder(Holder holderresult) {
		this.holder_result = holderresult;			// Store the holder to edit (or store null if creating a new holder)
		
		// Set Title
		if (holder_result == null) {
			setTitle("New Holder");
		} else {
			setTitle("Edit Holder");
		}
		
		// Set Window Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmNewEditHolder.class.getResource("/resources/graphics/appicon.png")));
		
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
		contentPane.setPreferredSize(new Dimension(376, 160));
		setContentPane(contentPane);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// BORDER AND PANEL
		Border border_panel = BorderFactory.createTitledBorder("Holder Details");
		contentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 365, 119);
		panel.setBorder(border_panel);
		contentPane.add(panel);
		panel.setLayout(null);
		
		// LABELS
		// Name
		JLabel lblName = new JLabel("*Name:");
		lblName.setBounds(20, 26, 80, 16);
		panel.add(lblName);
		
		// Date of Birth
		JLabel lblDOB = new JLabel("DOB:");
		lblDOB.setBounds(20, 86, 80, 16);
		panel.add(lblDOB);
		
		// Tax File Number
		JLabel lblTFN = new JLabel("TFN:");
		lblTFN.setBounds(20, 56, 80, 16);
		panel.add(lblTFN);
		
		// TEXTFIELDS
		// Name
		txtName = new JTextField();
		txtName.setBounds(100, 22, 258, 24);
		panel.add(txtName);
		txtName.setColumns(10);
		
		// Tax File Number
		txtTFN = new JTextField();
		txtTFN.setBounds(100, 52, 258, 24);
		panel.add(txtTFN);
		txtTFN.setColumns(10);
		
		// CALENDAR
		// datePicker
		DatePickerSettings DOBDateSettings = new DatePickerSettings();
		DOBDateSettings.setFormatForDatesCommonEra(Main.CE_DATE_FORMAT);
		DOBDateSettings.setFormatForDatesBeforeCommonEra(Main.BCE_DATE_FORMAT);
		DOBDatePicker = new DatePicker(DOBDateSettings);
		DOBDatePicker.setBounds(100, 79, 258, 30);
		panel.add(DOBDatePicker);
		
		// BUTTONS
		// Ok Button
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StoreHolder();
			}
		});
		btnOk.setBounds(210, 124, 80, 29);
		contentPane.add(btnOk);
		
		// Cancel Button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Close();
			}
		});
		btnCancel.setBounds(290, 124, 80, 29);
		contentPane.add(btnCancel);
		
		// Populate fields if an existing holder is being edited
		if (this.holder_result != null) {
			Initialise();
		}
	}
}