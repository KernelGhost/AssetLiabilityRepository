import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import CustomTypes.Entity;
import CustomTypes.Transaction;

import javax.swing.JSpinner;

public class frmNewEditTxn extends JFrame {
	private static final long serialVersionUID = 2192122483193247748L;
	
	private String TxnID;
	private String EntityID;
	
	private JPanel contentPane;
	private JTextField txtType;
	private JTextField txtHeld;
	private JTextField txtNumber;
	private DatePicker TxnDatePicker;
	private JComboBox<String> comboCatg;
	private JSpinner spinDebit;
	private JSpinner spinCredit;
	private JTextField txtDescription;
	
	private void GetEntityDetails() throws SQLException {
		Entity entity = Main.database_handler.GetEntityFromID(EntityID);
		txtType.setText(entity.GetEntityTypeName());
		txtHeld.setText(entity.GetInstitutionName());
		txtNumber.setText(entity.GetEntityNumber());
		
		String Categories[] = Main.database_handler.GetAllTransactionCategories();
		Arrays.sort(Categories);
		for (int intCtr = 0; intCtr < Categories.length; intCtr++) {
			comboCatg.addItem(Categories[intCtr]);
		}
	}
	
	private void Initialise() throws SQLException {
		if (TxnID != null) {
			Transaction transaction = Main.database_handler.GetTransaction(TxnID);
			
			// Set Transaction Date
			Calendar calendar = Calendar.getInstance();
			Date Date;
			int intDay;
			int intMonth;
			int intYear;
			
			Date = transaction.GetDate();
			calendar.setTime(Date);
			intDay = calendar.get(Calendar.DAY_OF_MONTH);
			intMonth = calendar.get(Calendar.MONTH);		// Caveat: January is '0' NOT '1'
			intYear = calendar.get(Calendar.YEAR);
			TxnDatePicker.setDate(LocalDate.of(intYear, intMonth + 1, intDay));
			
			// Set Category
			String Categories[] = Main.database_handler.GetAllTransactionCategories();
			Arrays.sort(Categories);
			
			for (int intctr = 0; intctr < Categories.length; intctr++) {
				if (Categories[intctr].equals(transaction.GetTransactionCategory())) {
					comboCatg.setSelectedIndex(intctr);
					break;
				}
		    }
			
			// Set Debits
			spinDebit.setValue(transaction.GetDebit());
			
			// Set Credits
			spinCredit.setValue(transaction.GetCredit());
			
			// Set Description
			txtDescription.setText(transaction.GetDescription());
		} else {
			TxnDatePicker.setDateToToday();
		}
	}
	
	private void Save() {
		Boolean boolSave = true;
		
		// Check Fields
		if (TxnDatePicker.getDate() == null) {
			boolSave = false;
			MessageBox.Warning("Please ensure a date is provided for the transaction." , "Save Transaction");
		}
		
		if (txtDescription.getText().trim().length() > Main.VARCHAR_MAX_LENGTH) {
			boolSave = false;
			MessageBox.Warning("Please ensure the transaction description does not exceed " + Integer.toString(Main.VARCHAR_MAX_LENGTH) + " characters in length." , "Save Transaction");
		}
		
		if (boolSave) {
			Date TxnDate = java.sql.Date.valueOf(TxnDatePicker.getDate());
			Transaction transaction = new Transaction(
					TxnID,
					EntityID,
					TxnDate,
					comboCatg.getSelectedItem().toString(),
					txtDescription.getText().trim(),
					new BigDecimal(spinDebit.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(spinCredit.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN));
			
			if (this.TxnID != null) {
				// Edited Transaction
				try {
					Main.database_handler.EditTransaction(transaction);
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error saving your edits to the transaction.", "Save Transaction");
					e.printStackTrace();
				}
				
				Close();
			} else {
				// New Transaction
				try {
					Main.database_handler.NewTransaction(transaction);
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error saving the new transaction.", "Save Transaction");
				}
				
				Close();
			}
		}
	}
	
	// Called on close to dispose the JFrame
	private void Close() {
		this.dispose();
	}

	public frmNewEditTxn(String EntityID, String TxnID) {
		this.EntityID = EntityID;	// Store the entity ID
		this.TxnID = TxnID;			// Store the transaction to edit (or store null if creating a new transaction)
		
		// Set Title
		if (this.TxnID == null) {
			setTitle("New Transaction [" + this.EntityID + "]");
		} else {
			setTitle("Edit Transaction #" + this.TxnID + " [" + this.EntityID + "]");
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
		contentPane.setPreferredSize(new Dimension(480, 339));
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
		
		// TRANSACTION PANEL
		Border service_panel_border = BorderFactory.createTitledBorder("Transaction Details");
		JPanel pnlService = new JPanel();
		pnlService.setBorder(service_panel_border);
		pnlService.setBounds(6, 133, 468, 172);
		contentPane.add(pnlService);
		pnlService.setLayout(null);
		
		// Transaction Date Label
		JLabel lblDate = new JLabel("*Date:");
		lblDate.setBounds(20, 26, 90, 30);
		pnlService.add(lblDate);
		
		// Transaction Date Picker
		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setFormatForDatesCommonEra(Main.CE_DATE_FORMAT);
		dateSettings.setFormatForDatesBeforeCommonEra(Main.BCE_DATE_FORMAT);
		TxnDatePicker = new DatePicker(dateSettings);
		TxnDatePicker.setBounds(113, 26, 345, 30);
		pnlService.add(TxnDatePicker);
		
		// Transaction Subtype Label
		JLabel lblCatg = new JLabel("*Category:");
		lblCatg.setBounds(20, 60, 90, 30);
		pnlService.add(lblCatg);
		
		// Transaction Subtype Combobox
		comboCatg = new JComboBox<String>();
		comboCatg.setBounds(112, 60, 346, 30);
		pnlService.add(comboCatg);
		
		// Debits Label
		JLabel lblDebit = new JLabel("Debits ($):");
		lblDebit.setBounds(20, 94, 90, 30);
		pnlService.add(lblDebit);
		
		// Debits Spinner
		SpinnerNumberModel DebitSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinDebit = new JSpinner(DebitSpinModel);
		JSpinner.NumberEditor DebitSpinEditor = (JSpinner.NumberEditor)spinDebit.getEditor();
		DecimalFormat DebitSpinFormat = DebitSpinEditor.getFormat();
		DebitSpinFormat.setMinimumFractionDigits(2);
		DebitSpinFormat.setMaximumFractionDigits(2);
		DebitSpinFormat.setGroupingSize(3);
		spinDebit.setValue(0);
		spinDebit.setBounds(112, 94, 120, 30);
		pnlService.add(spinDebit);
		
		// Credits Label
		JLabel lblCredit = new JLabel("Credits ($):");
		lblCredit.setBounds(260, 94, 80, 30);
		pnlService.add(lblCredit);
		
		// Credits Spinner
		SpinnerNumberModel CreditSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinCredit = new JSpinner(CreditSpinModel);
		JSpinner.NumberEditor CreditSpinEditor = (JSpinner.NumberEditor)spinCredit.getEditor();
		DecimalFormat CreditSpinFormat = CreditSpinEditor.getFormat();
		CreditSpinFormat.setMinimumFractionDigits(2);
		CreditSpinFormat.setMaximumFractionDigits(2);
		CreditSpinFormat.setGroupingSize(3);
		spinCredit.setValue(0);
		spinCredit.setBounds(338, 94, 120, 30);
		pnlService.add(spinCredit);
		
		// Description Label
		JLabel lblDescription = new JLabel("*Description:");
		lblDescription.setBounds(20, 128, 90, 30);
		pnlService.add(lblDescription);
		
		// Description Textfield
		txtDescription = new JTextField();
		txtDescription.setBounds(112, 128, 346, 30);
		txtDescription.setColumns(10);
		pnlService.add(txtDescription);
		
		// BUTTONS
		// Ok Button
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save();
			}
		});
		btnOk.setBounds(314, 304, 80, 29);
		contentPane.add(btnOk);
		
		// Cancel Button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Close();
			}
		});
		btnCancel.setBounds(394, 304, 80, 29);
		contentPane.add(btnCancel);
		
		try {
			GetEntityDetails();
			Initialise();
		} catch (SQLException e1) {
			MessageBox.Error("There was a database related error preparing the new/edit transaction screen.", "Entity Transactions");
			Close();
		}
	}
}
