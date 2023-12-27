import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.Font;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import CustomTypes.Entity;
import CustomTypes.ShareTransaction;

public class frmNewEditShrTxn extends JFrame {
	private static final long serialVersionUID = -2908981980869689311L;
	
	private String ShrTxnID = "";			// Stores the ID of the transaction being edited (or null if new transaction)
	private String EntityID = "";			// Stores the ID of the entity that the transaction belongs to
	private String TxnSubtype = "";			// Stores the type of transaction (e.g. Buy, Sell, Dividend or DRP)
	
	private JPanel contentPane;				// The main JPanel
	private JTextField txtType;				// Displays the type of entity
	private JTextField txtHeld;				// Displays the institution at which the entity is held
	private JTextField txtNumber;			// Displays the entity number
	private JTextField txtDescription;		// Displays the transaction description
	private DatePicker ShrTxnDatePicker;	// Displays the transaction date
	private JSpinner spinShareNumber;		// Displays the number of shares
	private JSpinner spinSharePrice;		// Displays the share price
	private JSpinner spinBrokerCharges;		// Displays the brokerage fees
	private JSpinner spinOtherCharges;		// Displays any other fees
	private JSpinner spinCGT;				// Displays the capital gains taxes
	private JSpinner spinDivPerShare;		// Displays the dividends paid per share
	private JSpinner spinFrankPercent;		// Displays the franking percentage
	
	private void Save() {
		Boolean boolSave = true;
		
		// Check Fields
		// Date
		if (ShrTxnDatePicker.getDate() == null) {
			boolSave = false;
			MessageBox.Warning("Please ensure a date is provided for the transaction.", "Save Transaction");
		}
		
		// Description
		if (txtDescription.getText().trim().length() > Main.VARCHAR_MAX_LENGTH) {
			boolSave = false;
			MessageBox.Warning("Please ensure the transaction description is no more than " + Integer.toString(Main.VARCHAR_MAX_LENGTH) + " characters in length.", "Save Transaction");
		}
		
		// Share Price
		if (TxnSubtype.equals(Main.SHR_TXN_TYPES[0]) || TxnSubtype.equals(Main.SHR_TXN_TYPES[1]) || TxnSubtype.equals(Main.SHR_TXN_TYPES[3])) {
			if (new BigDecimal(spinSharePrice.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN).equals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))) {
				boolSave = false;
				MessageBox.Warning("Please ensure the unit price is a non-zero amount.", "Save Transaction");
			}
		}
		
		// Number of Shares
		if (new BigDecimal(spinShareNumber.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN).equals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))) {
			boolSave = false;
			MessageBox.Warning("Please ensure the number of shares is a non-zero amount.", "Save Transaction");
		}
		
		// Dividends Per Share
		if (TxnSubtype.equals(Main.SHR_TXN_TYPES[2]) || TxnSubtype.equals(Main.SHR_TXN_TYPES[3])) {
			if (new BigDecimal(spinDivPerShare.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN).equals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN))) {
				boolSave = false;
				MessageBox.Warning("Please ensure the dividend per share is a non-zero amount.", "Save Transaction");
			}
		}
		
		if (boolSave) {
			ShareTransaction share_transaction = new ShareTransaction(
					java.sql.Date.valueOf(ShrTxnDatePicker.getDate()),
					ShrTxnID,
					EntityID,
					TxnSubtype,
					txtDescription.getText().trim(),
					new BigDecimal(spinSharePrice.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(spinBrokerCharges.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(spinOtherCharges.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(spinCGT.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(spinDivPerShare.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(spinFrankPercent.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN),
					Float.parseFloat(spinShareNumber.getValue().toString())
					);
		
			if (ShrTxnID == null) {
				// New Transaction
				try {
					Main.database_handler.NewShareTransaction(share_transaction);
					Close();
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error saving the new transaction.", "Save Transaction");
				}
			} else {
				// Edited Transaction
				try {
					Main.database_handler.EditShareTransaction(share_transaction);
					Close();
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error saving your edits to the transaction.", "Save Transaction");
				}
			}
		}
	}
	
	private void Initialise() throws SQLException {
		// Populate Entity Information
		Entity entity = Main.database_handler.GetEntityFromID(EntityID);
		txtType.setText(entity.GetEntityTypeName());
		txtNumber.setText(entity.GetEntityNumber());
		txtHeld.setText(entity.GetInstitutionName());
		
		// Populate Transaction Information
		if (ShrTxnID != null) {
			ShareTransaction share_transaction = Main.database_handler.GetShareTransaction(ShrTxnID);
			
			// Set Date
			Date date = share_transaction.GetDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int intDay = calendar.get(Calendar.DAY_OF_MONTH);
			int intMonth = calendar.get(Calendar.MONTH);		// Caveat: January is '0' NOT '1'
			int intYear = calendar.get(Calendar.YEAR);
			ShrTxnDatePicker.setDate(LocalDate.of(intYear, intMonth + 1, intDay));
			
			// Set Description
			txtDescription.setText(share_transaction.GetDescription());
			txtDescription.setCaretPosition(0);
			
			if (TxnSubtype.equals(Main.SHR_TXN_TYPES[0])) {	// Buy
				// Set Share Number
				spinShareNumber.setValue(share_transaction.GetNumberShares());
				
				// Set Share Price
				spinSharePrice.setValue(share_transaction.GetSharePrice());
				
				// Set Brokerage Charges
				spinBrokerCharges.setValue(share_transaction.GetBrokerageCharges());
				
				// Set Other Charges
				spinOtherCharges.setValue(share_transaction.GetOtherCharges());
				
			} else if (TxnSubtype.equals(Main.SHR_TXN_TYPES[1])) {	// Sell
				// Set Share Number
				spinShareNumber.setValue(share_transaction.GetNumberShares());
				
				// Set Share Price
				spinSharePrice.setValue(share_transaction.GetSharePrice());
				
				// Set Capital Gains Tax
				spinCGT.setValue(share_transaction.GetCapitalGainsTax());
				
				// Set Brokerage Charges
				spinBrokerCharges.setValue(share_transaction.GetBrokerageCharges());
				
				// Set Other Charges
				spinOtherCharges.setValue(share_transaction.GetOtherCharges());
			} else if (TxnSubtype.equals(Main.SHR_TXN_TYPES[2])) {	// Dividend
				// Set Share Number
				spinShareNumber.setValue(share_transaction.GetNumberShares());
				
				// Set Dividends Per Share
				spinDivPerShare.setValue(share_transaction.GetDividendAmount());
				
				// Set Franking Percentage
				spinFrankPercent.setValue(share_transaction.GetFrankingPercentage());
			} else {	// DRP
				// Set Share Number
				spinShareNumber.setValue(share_transaction.GetNumberShares());
				
				// Set Share Price
				spinSharePrice.setValue(share_transaction.GetSharePrice());
				
				// Set Dividends Per Share
				spinDivPerShare.setValue(share_transaction.GetDividendAmount());
				
				// Set Franking Percentage
				spinFrankPercent.setValue(share_transaction.GetFrankingPercentage());
				
				// Set Brokerage Charges
				spinBrokerCharges.setValue(share_transaction.GetBrokerageCharges());
				
				// Set Other Charges
				spinOtherCharges.setValue(share_transaction.GetOtherCharges());
			}
		} else {
			// This is a new transaction, so set the date to today
			ShrTxnDatePicker.setDateToToday();
		}
	}
	
	// Called on close to dispose the JFrame
	private void Close() {
		this.dispose();
	}

	public frmNewEditShrTxn(String ShrTxnID, String TxnSubtype, String EntityID) {
		this.ShrTxnID = ShrTxnID;
		this.TxnSubtype = TxnSubtype;
		this.EntityID = EntityID;
		
		// Set Title
		if (this.ShrTxnID == null) {
			if (TxnSubtype.equals(Main.SHR_TXN_TYPES[0])) {	// Buy
				setTitle("New Share " + Main.SHR_TXN_TYPES[0] + " Transaction [" + this.EntityID + "]");
			} else if (TxnSubtype.equals(Main.SHR_TXN_TYPES[1])) {	// Sell
				setTitle("New Share " + Main.SHR_TXN_TYPES[1] + " Transaction [" + this.EntityID + "]");
			} else if (TxnSubtype.equals(Main.SHR_TXN_TYPES[2])) {	// Dividend
				setTitle("New " + Main.SHR_TXN_TYPES[2] + " Transaction [" + this.EntityID + "]");
			} else {	// DRP
				setTitle("New " + Main.SHR_TXN_TYPES[3] + " Transaction [" + this.EntityID + "]");
			}
		} else {
			if (TxnSubtype.equals(Main.SHR_TXN_TYPES[0])) {	// Buy
				setTitle("Edit Share " + Main.SHR_TXN_TYPES[1] + " Transaction #" + this.ShrTxnID + " [" + this.EntityID + "]");
			} else if (TxnSubtype.equals(Main.SHR_TXN_TYPES[1])) {	// Sell
				setTitle("Edit Share " + Main.SHR_TXN_TYPES[1] + " Transaction #" + this.ShrTxnID + " [" + this.EntityID + "]");
			} else if (TxnSubtype.equals(Main.SHR_TXN_TYPES[2])) {	// Dividend
				setTitle("Edit " + Main.SHR_TXN_TYPES[2] + " Transaction #" + this.ShrTxnID + " [" + this.EntityID + "]");
			} else {	// DRP
				setTitle("Edit " + Main.SHR_TXN_TYPES[3] + " Transaction #" + this.ShrTxnID + " [" + this.EntityID + "]");
			}
		}
		
		// Set Window Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmNewEditService.class.getResource("/resources/graphics/appicon.png")));
		
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
		contentPane.setPreferredSize(new Dimension(480, 406));
		setContentPane(contentPane);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// ENTITY PANEL
		Border entity_panel_border = BorderFactory.createTitledBorder("Entity Details");
		contentPane.setLayout(null);
		JPanel pnlEntity = new JPanel();
		pnlEntity.setLayout(null);
		pnlEntity.setBorder(entity_panel_border);
		pnlEntity.setBounds(6, 6, 468, 119);
		contentPane.add(pnlEntity);
		
		// Type Label
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(20, 26, 80, 16);
		pnlEntity.add(lblType);
		
		// Type Textfield
		txtType = new JTextField();
		txtType.setEnabled(false);
		txtType.setEditable(false);
		txtType.setColumns(10);
		txtType.setBounds(100, 22, 358, 24);
		pnlEntity.add(txtType);
		
		// Institution Label
		JLabel lblHeld = new JLabel("Held at:");
		lblHeld.setBounds(20, 86, 80, 16);
		pnlEntity.add(lblHeld);
		
		// Institution Textfield
		txtHeld = new JTextField();
		txtHeld.setEnabled(false);
		txtHeld.setEditable(false);
		txtHeld.setColumns(10);
		txtHeld.setBounds(100, 82, 358, 24);
		pnlEntity.add(txtHeld);
		
		// Number Label
		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setBounds(20, 56, 80, 16);
		pnlEntity.add(lblNumber);
		
		// Number Textfield
		txtNumber = new JTextField();
		txtNumber.setEnabled(false);
		txtNumber.setEditable(false);
		txtNumber.setColumns(10);
		txtNumber.setBounds(100, 52, 358, 24);
		pnlEntity.add(txtNumber);
		
		// TRANSACTION PANEL
		Border service_panel_border = BorderFactory.createTitledBorder("Transaction Details");
		JPanel pnlService = new JPanel();
		pnlService.setLayout(null);
		pnlService.setBorder(service_panel_border);
		pnlService.setBounds(6, 133, 468, 239);
		contentPane.add(pnlService);
		
		// Transaction Date Label
		JLabel lblDate = new JLabel("*Date:");
		
		// Transaction Date Picker
		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setFormatForDatesCommonEra(Main.CE_DATE_FORMAT);
		dateSettings.setFormatForDatesBeforeCommonEra(Main.BCE_DATE_FORMAT);
		ShrTxnDatePicker = new DatePicker(dateSettings);
		
		// Share Number Label
		JLabel lblShareNumber = new JLabel("*# Shares:");
		
		// Share Number Spinner
		SpinnerNumberModel ShareNumberSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinShareNumber = new JSpinner(ShareNumberSpinModel);
		JSpinner.NumberEditor ShareNumberSpinEditor = (JSpinner.NumberEditor)spinShareNumber.getEditor();
		DecimalFormat ShareNumberSpinFormat = ShareNumberSpinEditor.getFormat();
		ShareNumberSpinFormat.setMinimumFractionDigits(2);
		ShareNumberSpinFormat.setMaximumFractionDigits(2);
		ShareNumberSpinFormat.setGroupingSize(3);
		spinShareNumber.setValue(0);
		JComponent spinShareNumberEditor = spinShareNumber.getEditor();
		JSpinner.DefaultEditor ShareNumberSpinnerEditor = (JSpinner.DefaultEditor) spinShareNumberEditor;
		ShareNumberSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		
		// Share Price Label
		JLabel lblSharePrice = new JLabel("*Unit Price ($):");
		
		// Share Price Spinner
		SpinnerNumberModel SharePriceSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinSharePrice = new JSpinner(SharePriceSpinModel);
		JSpinner.NumberEditor SharePriceSpinEditor = (JSpinner.NumberEditor)spinSharePrice.getEditor();
		DecimalFormat SharePriceSpinFormat = SharePriceSpinEditor.getFormat();
		SharePriceSpinFormat.setMinimumFractionDigits(2);
		SharePriceSpinFormat.setMaximumFractionDigits(2);
		SharePriceSpinFormat.setGroupingSize(3);
		spinSharePrice.setValue(0);
		JComponent spinSharePriceEditor = spinSharePrice.getEditor();
		JSpinner.DefaultEditor SharePriceSpinnerEditor = (JSpinner.DefaultEditor) spinSharePriceEditor;
		SharePriceSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		
		// Brokerage Charges Label
		JLabel lblBrokerCharges = new JLabel("*Broker Charges ($):");
		
		// Brokerage Charges Spinner
		SpinnerNumberModel BrokerChargesSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinBrokerCharges = new JSpinner(BrokerChargesSpinModel);
		JSpinner.NumberEditor BrokerChargesSpinEditor = (JSpinner.NumberEditor)spinBrokerCharges.getEditor();
		DecimalFormat BrokerChargesSpinFormat = BrokerChargesSpinEditor.getFormat();
		BrokerChargesSpinFormat.setMinimumFractionDigits(2);
		BrokerChargesSpinFormat.setMaximumFractionDigits(2);
		BrokerChargesSpinFormat.setGroupingSize(3);
		spinBrokerCharges.setValue(0);
		JComponent spinBrokerChargesEditor = spinBrokerCharges.getEditor();
		JSpinner.DefaultEditor BrokerChargesSpinnerEditor = (JSpinner.DefaultEditor) spinBrokerChargesEditor;
		BrokerChargesSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		
		// Other Charges Label
		JLabel lblOtherCharges = new JLabel("*Other Charges ($):");
		
		// Other Charges Spinner
		SpinnerNumberModel OtherChargesSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinOtherCharges = new JSpinner(OtherChargesSpinModel);
		JSpinner.NumberEditor OtherChargesSpinEditor = (JSpinner.NumberEditor)spinOtherCharges.getEditor();
		DecimalFormat OtherChargesSpinFormat = OtherChargesSpinEditor.getFormat();
		OtherChargesSpinFormat.setMinimumFractionDigits(2);
		OtherChargesSpinFormat.setMaximumFractionDigits(2);
		OtherChargesSpinFormat.setGroupingSize(3);
		spinOtherCharges.setValue(0);
		JComponent spinOtherChargesEditor = spinOtherCharges.getEditor();
		JSpinner.DefaultEditor OtherChargesSpinnerEditor = (JSpinner.DefaultEditor) spinOtherChargesEditor;
		OtherChargesSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		
		// Dividends Per Share Label
		JLabel lblDivPerShare = new JLabel("*Dividends (per share) ($):");
		
		// Dividends Per Share Spinner
		SpinnerNumberModel DivPerShareSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinDivPerShare = new JSpinner(DivPerShareSpinModel);
		JSpinner.NumberEditor DivPerShareSpinEditor = (JSpinner.NumberEditor)spinDivPerShare.getEditor();
		DecimalFormat DivPerShareSpinFormat = DivPerShareSpinEditor.getFormat();
		DivPerShareSpinFormat.setMinimumFractionDigits(2);
		DivPerShareSpinFormat.setMaximumFractionDigits(2);
		DivPerShareSpinFormat.setGroupingSize(3);
		spinDivPerShare.setValue(0);
		JComponent spinDivPerShareEditor = spinDivPerShare.getEditor();
		JSpinner.DefaultEditor DivPerShareSpinnerEditor = (JSpinner.DefaultEditor) spinDivPerShareEditor;
		DivPerShareSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		
		// Franking Percentage Label
		JLabel lblFrankPercent = new JLabel("*Franking Percentage (%):");
		
		// Franking Percentage Spinner
		SpinnerNumberModel FrankPercentSpinModel = new SpinnerNumberModel(100, 0, 100, 1);
		spinFrankPercent = new JSpinner(FrankPercentSpinModel);
		JSpinner.NumberEditor FrankPercentSpinEditor = (JSpinner.NumberEditor)spinFrankPercent.getEditor();
		DecimalFormat FrankPercentSpinFormat = FrankPercentSpinEditor.getFormat();
		FrankPercentSpinFormat.setMinimumFractionDigits(0);
		FrankPercentSpinFormat.setMaximumFractionDigits(0);
		spinFrankPercent.setValue(100);
		JComponent spinFrankPercentEditor = spinFrankPercent.getEditor();
		JSpinner.DefaultEditor FrankPercentSpinnerEditor = (JSpinner.DefaultEditor) spinFrankPercentEditor;
		FrankPercentSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		
		// Description Label
		JLabel lblDescription = new JLabel("Description:");
		
		// Description Textfield
		txtDescription = new JTextField();
		txtDescription.setColumns(10);
		
		// Capital Gains Tax Label
		JLabel lblCGT = new JLabel("*Capital Gains Tax ($):");
		
		// Capital Gains Tax Spinner
		SpinnerNumberModel CGTSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinCGT = new JSpinner(CGTSpinModel);
		JSpinner.NumberEditor CGTSpinEditor = (JSpinner.NumberEditor)spinCGT.getEditor();
		DecimalFormat CGTSpinFormat = CGTSpinEditor.getFormat();
		CGTSpinFormat.setMinimumFractionDigits(2);
		CGTSpinFormat.setMaximumFractionDigits(2);
		CGTSpinFormat.setGroupingSize(3);
		spinCGT.setValue(0);
		JComponent spinCGTEditor = spinCGT.getEditor();
		JSpinner.DefaultEditor CGTSpinnerEditor = (JSpinner.DefaultEditor) spinCGTEditor;
		CGTSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		
		// About DRP Label
		JLabel lblAboutDRP = new JLabel("<html> Note: Share # purchased via DRP is calculated as ((# Existing Shares * Dividends Per Share) - (Broker Charges + Other Charges))/Unit Price </html>");
		lblAboutDRP.setForeground(Main.colDarkRed);
		lblAboutDRP.setFont(new Font("Lucida Grande", Font.PLAIN, 8));
		
		// Ok Button
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save();
			}
		});
		btnOk.setBounds(314, 371, 80, 29);
		contentPane.add(btnOk);
		
		// Cancel Button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Close();
			}
		});
		btnCancel.setBounds(394, 371, 80, 29);
		contentPane.add(btnCancel);
		
		// Populate Window
		if (TxnSubtype.equals(Main.SHR_TXN_TYPES[0])) {	// Buy
			// Position
			lblDate.setBounds(20, 26, 90, 30);
			ShrTxnDatePicker.setBounds(158, 26, 300, 30);
			lblShareNumber.setBounds(20, 60, 90, 30);
			spinShareNumber.setBounds(157, 60, 301, 30);
			lblSharePrice.setBounds(20, 94, 94, 30);
			spinSharePrice.setBounds(157, 94, 301, 30);
			lblBrokerCharges.setBounds(20, 128, 125, 30);
			spinBrokerCharges.setBounds(157, 128, 301, 30);
			lblOtherCharges.setBounds(20, 162, 125, 30);
			spinOtherCharges.setBounds(157, 162, 301, 30);
			lblDescription.setBounds(20, 196, 90, 30);
			txtDescription.setBounds(157, 196, 301, 30);
			
			// Add
			pnlService.add(lblDate);
			pnlService.add(ShrTxnDatePicker);
			pnlService.add(lblShareNumber);
			pnlService.add(spinShareNumber);
			pnlService.add(lblSharePrice);
			pnlService.add(spinSharePrice);
			pnlService.add(lblBrokerCharges);
			pnlService.add(spinBrokerCharges);
			pnlService.add(lblOtherCharges);
			pnlService.add(spinOtherCharges);
			pnlService.add(lblDescription);
			pnlService.add(txtDescription);
		} else if (TxnSubtype.equals(Main.SHR_TXN_TYPES[1])) {	// Sell
			// Position
			lblDate.setBounds(20, 26, 90, 30);
			ShrTxnDatePicker.setBounds(158, 26, 300, 30);
			lblShareNumber.setBounds(20, 60, 90, 30);
			spinShareNumber.setBounds(157, 60, 100, 30);
			lblSharePrice.setBounds(269, 60, 94, 30);
			spinSharePrice.setBounds(358, 60, 100, 30);
			lblCGT.setBounds(20, 94, 140, 30);
			spinCGT.setBounds(157, 94, 301, 30);
			lblBrokerCharges.setBounds(20, 128, 125, 30);
			spinBrokerCharges.setBounds(157, 128, 301, 30);
			lblOtherCharges.setBounds(20, 162, 125, 30);
			spinOtherCharges.setBounds(157, 162, 301, 30);
			lblDescription.setBounds(20, 196, 90, 30);
			txtDescription.setBounds(157, 196, 301, 30);
			
			// Add
			pnlService.add(lblDate);
			pnlService.add(ShrTxnDatePicker);
			pnlService.add(lblShareNumber);
			pnlService.add(spinShareNumber);
			pnlService.add(lblSharePrice);
			pnlService.add(spinSharePrice);
			pnlService.add(lblCGT);
			pnlService.add(spinCGT);
			pnlService.add(lblBrokerCharges);
			pnlService.add(spinBrokerCharges);
			pnlService.add(lblOtherCharges);
			pnlService.add(spinOtherCharges);
			pnlService.add(lblDescription);
			pnlService.add(txtDescription);
		} else if (TxnSubtype.equals(Main.SHR_TXN_TYPES[2])) {	// Dividend
			// Position
			lblDate.setBounds(20, 26, 90, 30);
			ShrTxnDatePicker.setBounds(181, 26, 277, 30);
			lblShareNumber.setBounds(20, 60, 90, 30);
			spinShareNumber.setBounds(180, 60, 278, 30);
			lblDivPerShare.setBounds(20, 94, 140, 30);
			spinDivPerShare.setBounds(180, 94, 278, 30);
			lblFrankPercent.setBounds(20, 128, 160, 30);
			spinFrankPercent.setBounds(180, 128, 278, 30);
			lblDescription.setBounds(20, 162, 90, 30);
			txtDescription.setBounds(180, 162, 278, 30);
			
			// Add
			pnlService.add(lblDate);
			pnlService.add(ShrTxnDatePicker);
			pnlService.add(lblShareNumber);
			pnlService.add(spinShareNumber);
			pnlService.add(lblDivPerShare);
			pnlService.add(spinDivPerShare);
			pnlService.add(lblFrankPercent);
			pnlService.add(spinFrankPercent);
			pnlService.add(lblDescription);
			pnlService.add(txtDescription);
		} else {	// DRP
			// Modify
			lblShareNumber.setText("*# Existing Shares:");
			
			// Position
			lblDate.setBounds(20, 26, 90, 30);
			ShrTxnDatePicker.setBounds(158, 26, 300, 30);
			lblShareNumber.setBounds(20, 60, 110, 30);
			spinShareNumber.setBounds(157, 60, 100, 30);
			lblSharePrice.setBounds(269, 60, 94, 30);
			spinSharePrice.setBounds(358, 60, 100, 30);
			lblDivPerShare.setBounds(20, 94, 160, 30);
			spinDivPerShare.setBounds(180, 94, 278, 30);
			lblFrankPercent.setBounds(20, 128, 160, 30);
			spinFrankPercent.setBounds(180, 128, 278, 30);
			lblBrokerCharges.setBounds(20, 162, 125, 30);
			spinBrokerCharges.setBounds(158, 162, 87, 30);
			lblOtherCharges.setBounds(250, 162, 125, 30);
			spinOtherCharges.setBounds(371, 162, 87, 30);
			lblDescription.setBounds(20, 196, 90, 30);
			txtDescription.setBounds(157, 196, 301, 30);
			lblAboutDRP.setBounds(12, 371, 290, 29);
			
			// Add
			pnlService.add(lblDate);
			pnlService.add(ShrTxnDatePicker);
			pnlService.add(lblShareNumber);
			pnlService.add(spinShareNumber);
			pnlService.add(lblSharePrice);
			pnlService.add(spinSharePrice);
			pnlService.add(lblDivPerShare);
			pnlService.add(spinDivPerShare);
			pnlService.add(lblFrankPercent);
			pnlService.add(spinFrankPercent);
			pnlService.add(lblBrokerCharges);
			pnlService.add(spinBrokerCharges);
			pnlService.add(lblOtherCharges);
			pnlService.add(spinOtherCharges);
			pnlService.add(lblDescription);
			pnlService.add(txtDescription);
			contentPane.add(lblAboutDRP);
		}
		
		try {
			Initialise();
		} catch (SQLException e1) {
			MessageBox.Error("There was a database related error populating the new/edit transaction screen.", "Share Transaction");
			Close();
		}
	}

}
