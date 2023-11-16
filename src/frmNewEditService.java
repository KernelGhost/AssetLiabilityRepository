import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import CustomTypes.Entity;
import CustomTypes.Service;

public class frmNewEditService extends JFrame {
	private static final long serialVersionUID = -8719131650619047042L;
	// Service & Entity Information
	private Service service_result;					// Stores the selected service passed to the JFrame for editing (null when creating a new service)
	private String parent_entity_id;				// Stores the Entity_ID of the parent entity of the service
	
	// GUI Elements
	private JPanel contentPane;						// The JPanel for the JFrame
	
	// Entity
	private JTextField txtType;						// Displays the entity type
	private JTextField txtNumber;					// Displays the entity number
	private JTextField txtHeld;						// Displays the entity's parent institution
	
	// Service
	private JComboBox<String> comboServiceType;		// Displays the service type
	private JTextField txtFrequency;				// Displays the service frequency
	private JTextField txtContact;					// Displays the service contact information
	private JTextField txtUserID;					// Displays the service user ID
	private JTextField txtPassword;					// Displays the service password
	private JTextField txtDescription;				// Displays the service description
	private DatePicker ExpiryDatePicker;			// Displays the service expiry date
	
	// Called to populate the GUI elements (JTextFields + JComboBoxes + DatePicker)
	private void Initialise() {
		try {
			// Populate Entity Information
			// This information is guaranteed to not be null since these are compulsory fields on creation of an entity
			Entity entity_result = Main.database_handler.GetEntityFromID(parent_entity_id);
			txtType.setText(entity_result.GetEntityTypeName());
			txtNumber.setText(entity_result.GetEntityNumber());
			txtHeld.setText(entity_result.GetInstitutionName());
			
			// Populate Service Information
			// Service Type
			String ServiceTypes[] = Main.database_handler.GetAllServiceTypes();
			Arrays.sort(ServiceTypes);
			for (int intCtr = 0; intCtr < ServiceTypes.length; intCtr++) {
				comboServiceType.addItem(ServiceTypes[intCtr]);
			}
			
			// If a service was passed to the JFrame, we populate the existing service information
			if (this.service_result != null) {
				comboServiceType.setSelectedItem(service_result.GetServiceType());	// Service Type
				
				// Frequency
				if (service_result.GetFrequency() != null) {
					txtFrequency.setText(service_result.GetFrequency().trim());
				} else {
					txtFrequency.setText("");
				}
				
				// User ID
				if (service_result.GetUserID() != null) {
					txtUserID.setText(service_result.GetUserID().trim());
				} else {
					txtUserID.setText("");
				}
				
				// Password
				if (service_result.GetPassword() != null) {
					txtPassword.setText(service_result.GetPassword().trim());
				} else {
					txtPassword.setText("");
				}
				
				// Contact
				if (service_result.GetContact() != null) {
					txtContact.setText(service_result.GetContact().trim());
				} else {
					txtContact.setText("");
				}
				
				// Description
				if (service_result.GetDescription() != null) {
					txtDescription.setText(service_result.GetDescription().trim());
				} else {
					txtDescription.setText("");
				}
				
				// Expiry Date
				if (service_result.GetExpiry() != null) {
					Date date = service_result.GetExpiry();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					
					int intDay = calendar.get(Calendar.DAY_OF_MONTH);
					int intMonth = calendar.get(Calendar.MONTH);		// Caveat: January is '0' NOT '1'
					int intYear = calendar.get(Calendar.YEAR);
					
					ExpiryDatePicker.setDate(LocalDate.of(intYear, intMonth + 1, intDay));
				} else {
					ExpiryDatePicker.clear();
				}
			}
		} catch (SQLException e) {
			MessageBox.Error("A database related error occurred whilst loading the service details.", "New/Edit Service");
		}
	}
	
	// Called when user requests to save changes to either a new or existing service
	private void StoreService() {
		String ServiceType;																// Stores the service type
		String Frequency;																// Stores the service frequency
		String Contact;																	// Stores the service contact
		String UserID;																	// Stores the service User ID
		String Password;																// Stores the service password
		String Description;																// Stores the service description
		Date ExpiryDate;																// Stores the service expiry date as a string
		Boolean boolLength = true;														// Used to determine if fields exceed the maximum allowable string length for the database
		
		// Fields that are left blank are stored as null, otherwise they are trimmed and stored
		// Service Type
		if (comboServiceType.getSelectedIndex() >= 0) {
			ServiceType = comboServiceType.getSelectedItem().toString();
		} else {
			ServiceType = null;
		}
		
		// Frequency
		if (!txtFrequency.getText().trim().equals("")) {
			Frequency = txtFrequency.getText().trim();
		} else {
			Frequency = null;
		}
		
		// Contact
		if (!txtContact.getText().trim().equals("")) {
			Contact = txtContact.getText().trim();
		} else {
			Contact = null;
		}
		
		// User ID
		if (!txtUserID.getText().trim().equals("")) {
			UserID = txtUserID.getText().trim();
		} else {
			UserID = null;
		}
		
		// Password
		if (!txtPassword.getText().trim().equals("")) {
			Password = txtPassword.getText().trim();
		} else {
			Password = null;
		}
		
		// Description
		if (!txtDescription.getText().trim().equals("")) {
			Description = txtDescription.getText().trim();
		} else {
			Description = null;
		}
		
		// Expiry Date
		if (ExpiryDatePicker.getDate() != null) {
			ExpiryDate = java.sql.Date.valueOf(ExpiryDatePicker.getDate());
		} else {
			ExpiryDate = null;
		}
		
		// Check if all mandatory fields (service type and service description) are provided
		if ((ServiceType != null) & (Description != null)) {
			// Check if fields exceed the maximum allowable VARCHAR length
			// Check Frequency
			if (Frequency != null) {
				if (Frequency.length() > Main.VARCHAR_MAX_LENGTH) {
					boolLength = false;
				}
			}
			
			// Check Contact
			if (Contact != null) {
				if (Contact.length() > Main.VARCHAR_MAX_LENGTH) {
					boolLength = false;
				}
			}
			
			// Check User ID
			if (UserID != null) {
				if (UserID.length() > Main.VARCHAR_MAX_LENGTH) {
					boolLength = false;
				}
			}
			
			// Check Password
			if (Password != null) {
				if (Password.length() > Main.VARCHAR_MAX_LENGTH) {
					boolLength = false;
				}
			}
			
			// Check Description
			if (Description.length() > Main.VARCHAR_MAX_LENGTH) {
				boolLength = false;
			}
			
			if (boolLength) {
				// Check if we are creating a new service or editing an existing service
				if (this.service_result != null) {
					// Editing an existing service
					
					Service service = new Service(
							ServiceType,
							Description,
							parent_entity_id,
							Frequency,
							UserID,
							Password,
							Contact,
							ExpiryDate,
							service_result.GetServiceID()
							);
					
					try {
						Main.database_handler.EditService(service);
						Close();
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error editing the service.", "Edit Service");
					}
				} else {
					// Creating a new service
					
					Service service = new Service(
							ServiceType,
							Description,
							parent_entity_id,
							Frequency,
							UserID,
							Password,
							Contact,
							ExpiryDate,
							null
							);
					
					try {
						Main.database_handler.NewService(service);
						Close();
					} catch (SQLException e) {
						e.printStackTrace();
						MessageBox.Error("There was a database related error saving the new service.", "New Service");
					}
				}
			} else {
				MessageBox.Information("Please ensure that all supplied fields excluding the expiry date are no longer than "+ Integer.toString(Main.VARCHAR_MAX_LENGTH) +" characters in length.", "New/Edit Service");
			}
		} else {
			MessageBox.Information("Please ensure a service type, institution and service description are provided.", "New/Edit Service");
		}
	}
	
	// Called on close to dispose the JFrame
	private void Close() {
		this.dispose();
	}
	
	/* Called on creation of a new JFrame. Pass the parent entity ID as well as the service to be
	 * edited. Null is passed as the ServResult if a new service is being created. */
	public frmNewEditService(String strEntity, Service servresult) {
		this.parent_entity_id = strEntity;		// Store the entity ID
		this.service_result = servresult;		// Store the service to edit (or store null if creating a new service)
		
		// Set Title
		if (this.service_result == null) {
			setTitle("New Service");
		} else {
			setTitle("Edit Service");
		}
		
		// Set Window Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmNewEditService.class.getResource("/resources/graphics/Icon.png")));
		
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
		contentPane.setPreferredSize(new Dimension(480, 344));
		setContentPane(contentPane);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// ENTITY PANEL
		Border entity_panel_border = BorderFactory.createTitledBorder("Entity Details");
		contentPane.setLayout(null);
		JPanel pnlEntity = new JPanel();
		pnlEntity.setBounds(6, 6, 468, 119);
		pnlEntity.setBorder(entity_panel_border);
		contentPane.add(pnlEntity);
		pnlEntity.setLayout(null);
		
		// LABELS
		// Type
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(20, 26, 80, 16);
		pnlEntity.add(lblType);
		
		// Institution
		JLabel lblHeld = new JLabel("Held at:");
		lblHeld.setBounds(20, 86, 80, 16);
		pnlEntity.add(lblHeld);
		
		// Number
		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setBounds(20, 56, 80, 16);
		pnlEntity.add(lblNumber);
		
		// TEXTFIELDS
		// Type
		txtType = new JTextField();
		txtType.setEnabled(false);
		txtType.setEditable(false);
		txtType.setBounds(100, 22, 358, 24);
		txtType.setColumns(10);
		pnlEntity.add(txtType);
		
		// Institution
		txtHeld = new JTextField();
		txtHeld.setEnabled(false);
		txtHeld.setEditable(false);
		txtHeld.setBounds(100, 82, 358, 24);
		txtHeld.setColumns(10);
		pnlEntity.add(txtHeld);
		
		// Number
		txtNumber = new JTextField();
		txtNumber.setEnabled(false);
		txtNumber.setEditable(false);
		txtNumber.setBounds(100, 52, 358, 24);
		txtNumber.setColumns(10);
		pnlEntity.add(txtNumber);
		
		// SERVICE PANEL
		Border service_panel_border = BorderFactory.createTitledBorder("Service Details");
		JPanel pnlService = new JPanel();
		pnlService.setBorder(service_panel_border);
		pnlService.setBounds(6, 133, 468, 176);
		contentPane.add(pnlService);
		pnlService.setLayout(null);
		
		// LABELS
		// Service Type
		JLabel lblServiceType = new JLabel("*Service Type:");
		lblServiceType.setBounds(20, 26, 90, 16);
		pnlService.add(lblServiceType);
		
		// User ID
		JLabel lblUserId = new JLabel("User ID:");
		lblUserId.setBounds(20, 86, 90, 16);
		pnlService.add(lblUserId);
		
		// Password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(264, 86, 70, 16);
		pnlService.add(lblPassword);
		
		// Frequency
		JLabel lblFrequency = new JLabel("Frequency:");
		lblFrequency.setBounds(20, 56, 90, 16);
		pnlService.add(lblFrequency);
		
		// Contact
		JLabel lblContact = new JLabel("Contact:");
		lblContact.setBounds(264, 56, 70, 16);
		pnlService.add(lblContact);
		
		// Expiry Date
		JLabel lblExpiryDate = new JLabel("Expiry Date:");
		lblExpiryDate.setBounds(20, 116, 90, 16);
		pnlService.add(lblExpiryDate);
		
		// Description
		JLabel lblDescription = new JLabel("*Description:");
		lblDescription.setBounds(20, 146, 90, 16);
		pnlService.add(lblDescription);
		
		// COMBOBOXES
		// Service Type
		comboServiceType = new JComboBox<String>();
		comboServiceType.setBounds(112, 22, 346, 27);
		pnlService.add(comboServiceType);
		
		// TEXTFIELDS
		// Frequency
		txtFrequency = new JTextField();
		txtFrequency.setBounds(112, 51, 140, 26);
		txtFrequency.setColumns(10);
		pnlService.add(txtFrequency);
		
		// User ID
		txtUserID = new JTextField();
		txtUserID.setBounds(112, 81, 140, 26);
		txtUserID.setColumns(10);
		pnlService.add(txtUserID);
		
		// Password
		txtPassword = new JTextField();
		txtPassword.setBounds(332, 81, 126, 26);
		txtPassword.setColumns(10);
		pnlService.add(txtPassword);
		
		// Contact
		txtContact = new JTextField();
		txtContact.setBounds(332, 51, 126, 26);
		txtContact.setColumns(10);
		pnlService.add(txtContact);
		
		txtDescription = new JTextField();
		txtDescription.setBounds(112, 141, 346, 26);
		txtDescription.setColumns(10);
		pnlService.add(txtDescription);
		
		// CALENDAR
		// Expiry Date Picker
		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setFormatForDatesCommonEra(Main.CE_DATE_FORMAT);
		dateSettings.setFormatForDatesBeforeCommonEra(Main.BCE_DATE_FORMAT);
		ExpiryDatePicker = new DatePicker(dateSettings);
		ExpiryDatePicker.setBounds(112, 109, 346, 30);
		pnlService.add(ExpiryDatePicker);
		
		// BUTTONS
		// Ok Button
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StoreService();
			}
		});
		btnOk.setBounds(314, 310, 80, 29);
		contentPane.add(btnOk);
		
		// Cancel Button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Close();
			}
		});
		btnCancel.setBounds(394, 310, 80, 29);
		contentPane.add(btnCancel);
		
		// Populate fields
		Initialise();
	}
}