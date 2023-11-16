import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.text.DecimalFormat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import CustomTypes.CreditCard;
import CustomTypes.Entity;
import CustomTypes.Holder;
import CustomTypes.Institution;
import CustomTypes.Insurance;
import CustomTypes.Loan;
import CustomTypes.Property;
import CustomTypes.Share;
import CustomTypes.TermDeposit;

public class frmNewEntity extends JFrame {
	private static final long serialVersionUID = -1439697375992556464L;
	private JPanel contentPane;
	private String strType;
	
	// Lists and List Models
	private DefaultListModel<String> EntityHolderListModel;
	private JList<String> EntityHolderList;
	private DefaultListModel<String> BankHolderListModel;
	private JList<String> BankHolderList;
	
	// GENERIC GUI ELEMENTS
	private JLabel lblGeneric;
	private JLabel lblNumber;
	private JLabel lblInstitution;
	private JLabel lblStatus;
	private JLabel lblDescription;
	private JLabel lblComments;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	private JLabel lblEntityHolders;
	private JLabel lblHolderBank;
	private JLabel lblSpecific;
	private JLabel lblNotif;
	private JTextField txtNumber;
	private JTextField txtDescription;
	private JTextField txtComments;
	private JComboBox<String> comboInst;
	private JComboBox<String> comboStatus;
	private DatePicker OpenDatePicker;
	private DatePicker CloseDatePicker;
	private JScrollPane EntityHolderListPanel;
	private JScrollPane BankHolderListPanel;
	private JSeparator separator1;
	private JSeparator separator2;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnSave;
	
	// CREDIT CARD
	private JLabel lblLimit;
	private JSpinner spinLimit;
	
	// INSURANCE
	private JLabel lblInsuredAmount;
	private JLabel lblInsurancePremium;
	private JSpinner spinInsuredAmount;
	private JSpinner spinInsurancePremium;
	
	// LOAN ACCOUNT
	private JLabel lblLoanAmount;
	private JLabel lblLoanCurrentValue;
	private JSpinner spinLoanAmount;
	private JSpinner spinLoanCurrentValue;
	
	// PROPERTY
	private JLabel lblAddress;
	private JLabel lblPropertyPurchaseAmount;
	private JLabel lblPropertySoldAmount;
	private JLabel lblPropertyCurrentValue;
	private JLabel lblPropertySolFeeBuy;
	private JLabel lblPropertySolFeeSell;
	private JLabel lblPropertyGovtChargesBuy;
	private JLabel lblPropertyGovtChargesSell;
	private JLabel lblPropertyAgtFeeBuy;
	private JLabel lblPropertyAgtFeeSell;
	private JLabel lblPropertyCapGainsTax;
	private JTextField txtAddress;
	private JSpinner spinPropertyPurchaseAmount;
	private JSpinner spinPropertySoldAmount;
	private JSpinner spinPropertyCurrentValue;
	private JSpinner spinPropertySolFeeBuy;
	private JSpinner spinPropertySolFeeSell;
	private JSpinner spinPropertyGovtChargesBuy;
	private JSpinner spinPropertyGovtChargesSell;
	private JSpinner spinPropertyAgtFeeBuy;
	private JSpinner spinPropertyAgtFeeSell;
	private JSpinner spinPropertyCapGainsTax;
	
	// SAVINGS ACCOUNT
	// Nothing Extra Needed
	
	// SHARES / MANAGED FUNDS
	private JLabel lblCurrentUnitShareValue;
	private JSpinner spinCurrentUnitShareValue;
	
	// TERM DEPOSIT
	private JLabel lblTermDepositOpeningBalance;
	private JLabel lblTermDepositInterestAmount;
	private JLabel lblTermDepositOtherCharges;
	private JLabel lblTermDepositBankFees;
	private JLabel lblTermDepositInterestRate;
	private JSpinner spinTermDepositOpeningBalance;
	private JSpinner spinTermDepositInterestAmount;
	private JSpinner spinTermDepositOtherCharges;
	private JSpinner spinTermDepositBankFees;
	private JSpinner spinTermDepositInterestRate;
	
	// SUPERANNUATION
	// Nothing Extra Needed
	
	private void GetInstitutions() throws SQLException {
		Institution[] AllInstitutions = Main.database_handler.GetAllInstitutions();
		
		if (AllInstitutions != null) {
			Arrays.sort(AllInstitutions);
			for (int intCtr = 0; intCtr < AllInstitutions.length; intCtr++) {
				comboInst.addItem(AllInstitutions[intCtr].GetName());
			}
		}
	}
	
	private void GetStatus() throws SQLException {
		String Statuses[] = Main.database_handler.GetAllStatusNames();
		Arrays.sort(Statuses, Collections.reverseOrder());
		for (int intCtr = 0; intCtr < Statuses.length; intCtr++) {
			comboStatus.addItem(Statuses[intCtr]);
		}
	}
	
	private void RefreshHolders() throws SQLException {
		EntityHolderListModel = new DefaultListModel<String>();
		BankHolderListModel = new DefaultListModel<String>();
		
		Holder[] AllHolders = Main.database_handler.GetAllHolders();
		
		if (AllHolders != null) {
			Arrays.sort(AllHolders);
			
			for (int intCtr = 0; intCtr < AllHolders.length; intCtr++) {
				BankHolderListModel.addElement(AllHolders[intCtr].GetName());
			}
		}
	}
	
	private void AddHolder() {
		String SelectedHolder = "";
		
		if (BankHolderList.getSelectedIndex() >= 0) {
			SelectedHolder = BankHolderList.getSelectedValue();
			BankHolderListModel.remove(BankHolderList.getSelectedIndex());
			
			EntityHolderListModel.addElement(SelectedHolder);
			BankHolderList.clearSelection();
			EntityHolderList.clearSelection();
		} else {
			MessageBox.Information("Please select a holder to add to the entity.", "Entity Holders");
		}
	}
	
	private void RemoveHolder() {
		String SelectedHolder = "";
		
		if (EntityHolderList.getSelectedIndex() >= 0) {
			SelectedHolder = EntityHolderList.getSelectedValue();
			EntityHolderListModel.remove(EntityHolderList.getSelectedIndex());
			
			BankHolderListModel.addElement(SelectedHolder);
			EntityHolderList.clearSelection();
			BankHolderList.clearSelection();
		} else {
			MessageBox.Information("Please select a holder to remove from the entity.", "Entity Holders");
		}
	}
	
	private void Save() {
		Boolean boolSave = true;
		String NewEntityID = null;
		
		// Check if an entity number, institution and opening date are provided
		if (txtNumber.getText().trim().equals("") || OpenDatePicker.getDate() == null || comboInst.getSelectedItem() == null) {
			boolSave = false;
			MessageBox.Information("Please ensure all fields prefixed with an asterisk (*) are completed.", "New Entity");
		}
		
		// Check if entity number is under or equal to VARCHAR_MAX_LENGTH characters in length
		if (txtNumber.getText().trim().length() > Main.VARCHAR_MAX_LENGTH) {
			boolSave = false;
			MessageBox.Information("Please ensure the entity number is no longer than " + Integer.toString(Main.VARCHAR_MAX_LENGTH) + " characters in length.", "New Entity");
		}
		
		// Check if at least one holder is specified
		if (EntityHolderListModel.getSize() == 0) {
			boolSave = false;
			MessageBox.Information("Please ensure at least one holder is allocated to the entity.", "New Entity");
		}
		
		// Check the closing date happens after the opening date
		Date OpenDate = java.sql.Date.valueOf(OpenDatePicker.getDate());
		Date CloseDate;
		if (CloseDatePicker.getDate() != null) {
			CloseDate = java.sql.Date.valueOf(CloseDatePicker.getDate());
			if (CloseDate.before(OpenDate)) {
				boolSave = false;
				MessageBox.Information("Please ensure the closing date does not occur before the opening date.", "New Entity");
			}
		} else {
			CloseDate = null;
		}
		
		// If the new entity is a property, ensure an address is entered
		if (strType.equals(Main.ENTITY_TYPES[5])) {
			if (txtAddress.getText().trim().equals("")) {
				boolSave = false;
				MessageBox.Information("Please enter an address for the new property.", "New Entity");
			}
		}
		
		// Save the new entity if no issues were detected
		if (boolSave) {
			// Prepare Holder Names
			String[] Holders = new String[EntityHolderListModel.size()];
			for (int intCtr = 0; intCtr < EntityHolderListModel.size(); intCtr++) {
				Holders[intCtr] = EntityHolderListModel.get(intCtr).toString();
			}
			
			// Prepare Description
			String Description;
			if (txtDescription.getText().trim().equals("")) {
				Description = null;
			} else {
				Description = txtDescription.getText().trim();
			}
			
			// Prepare Comment
			String Comment;
			if (txtComments.getText().trim().equals("")) {
				Comment = null;
			} else {
				Comment = txtComments.getText().trim();
			}
			
			Entity new_entity = new Entity(
					Holders,
					null, // We do not pass an entity ID as this is to be assigned by the database
					strType,
					comboInst.getSelectedItem().toString(),
					comboStatus.getSelectedItem().toString(),
					txtNumber.getText().trim(),
					OpenDate,
					CloseDate,
					Description,
					Comment
					);
			
			try {
				NewEntityID = Main.database_handler.NewEntity(new_entity);
			} catch (SQLException e) {
				MessageBox.Error("There was a database related error saving the new entity.", "New Entity");
			}
			
			// If saving the main entity was successful, save the 'entity specific' data
			if (NewEntityID != null) {
				if (strType.equals(Main.ENTITY_TYPES[1])) {
					// Credit Card
					BigDecimal CardLimit = new BigDecimal(spinLimit.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					CreditCard new_card = new CreditCard(NewEntityID, CardLimit);
					try {
						Main.database_handler.NewCreditCard(new_card);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error saving the new credit card." + System.lineSeparator() + "ALR will attempt to clean up any damage to the database.", "New Entity");
						try {
							Main.database_handler.DeleteEntity(NewEntityID);
							MessageBox.Information("All damage to the database was corrected.", "New Entity");
						} catch (SQLException e1) {
							MessageBox.Error("Damage to the database could not be corrected automatically." + System.lineSeparator() + "Please manually remove orphaned entity with ID " + NewEntityID + " from the database.", "New Entity");
						}
					}
				} else if (strType.equals(Main.ENTITY_TYPES[2])) {
					// Loan Account
					BigDecimal LoanAmount = new BigDecimal(spinLoanAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal CurrentValue = new BigDecimal(spinLoanCurrentValue.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					Loan new_loan = new Loan(NewEntityID, LoanAmount, CurrentValue);
					try {						
						Main.database_handler.NewLoanAccount(new_loan);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error saving the new loan account." + System.lineSeparator() + "ALR will attempt to clean up any damage to the database.", "New Entity");
						try {
							Main.database_handler.DeleteEntity(NewEntityID);
							MessageBox.Information("All damage to the database was corrected.", "New Entity");
						} catch (SQLException e1) {
							MessageBox.Error("Damage to the database could not be corrected automatically." + System.lineSeparator() + "Please manually remove orphaned entity with ID " + NewEntityID + " from the database.", "New Entity");
						}
					}
				} else if (strType.equals(Main.ENTITY_TYPES[3])) {
					// Term Deposit
					BigDecimal OpeningBalance = new BigDecimal(spinTermDepositOpeningBalance.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal InterestAmount = new BigDecimal(spinTermDepositInterestAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal GovtCharges = new BigDecimal(spinTermDepositOtherCharges.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal BankFees = new BigDecimal(spinTermDepositBankFees.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal InterestRate = new BigDecimal(spinTermDepositInterestRate.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);;
					
					TermDeposit new_termdeposit = new TermDeposit(
							NewEntityID,
							OpeningBalance,
							InterestAmount,
							GovtCharges,
							BankFees,
							InterestRate
							);
					
					try {						
						Main.database_handler.NewTermDeposit(new_termdeposit);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error saving the new term deposit." + System.lineSeparator() + "ALR will attempt to clean up any damage to the database.", "New Entity");
						try {
							Main.database_handler.DeleteEntity(NewEntityID);
							MessageBox.Information("All damage to the database was corrected.", "New Entity");
						} catch (SQLException e1) {
							MessageBox.Error("Damage to the database could not be corrected automatically." + System.lineSeparator() + "Please manually remove orphaned entity with ID " + NewEntityID + " from the database.", "New Entity");
						}
					}
				} else if (strType.equals(Main.ENTITY_TYPES[5])) {
					// Property
					BigDecimal SolicitorsFeesBuy = new BigDecimal(spinPropertySolFeeBuy.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);;
					BigDecimal SolicitorsFeesSell = new BigDecimal(spinPropertySolFeeSell.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal GovtChargesBuy = new BigDecimal(spinPropertyGovtChargesBuy.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal GovtChargesSell = new BigDecimal(spinPropertyGovtChargesSell.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);;
					BigDecimal AgentFeesBuy = new BigDecimal(spinPropertyAgtFeeBuy.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal AgentFeesSell = new BigDecimal(spinPropertyAgtFeeSell.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal SoldAmount = new BigDecimal(spinPropertySoldAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal CapGainsTax = new BigDecimal(spinPropertyCapGainsTax.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal PurchaseAmount = new BigDecimal(spinPropertyPurchaseAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal CurrentValue = new BigDecimal(spinPropertyCurrentValue.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					Property new_property = new Property(
							NewEntityID,
							txtAddress.getText(),
							PurchaseAmount,
							SolicitorsFeesBuy,
							SolicitorsFeesSell,
							GovtChargesBuy,
							GovtChargesSell,
							AgentFeesBuy,
							AgentFeesSell,
							SoldAmount,
							CapGainsTax,
							CurrentValue
							);
					
					try {
						Main.database_handler.NewProperty(new_property);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error saving the new property." + System.lineSeparator() + "ALR will attempt to clean up any damage to the database.", "New Entity");
						try {
							Main.database_handler.DeleteEntity(NewEntityID);
							MessageBox.Information("All damage to the database was corrected.", "New Entity");
						} catch (SQLException e1) {
							MessageBox.Error("Damage to the database could not be corrected automatically." + System.lineSeparator() + "Please manually remove orphaned entity with ID " + NewEntityID + " from the database.", "New Entity");
						}
					}
				} else if (strType.equals(Main.ENTITY_TYPES[6])) {
					// Shares/Managed Funds
					BigDecimal SharePrice = new BigDecimal(spinCurrentUnitShareValue.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					Share new_share = new Share(NewEntityID, SharePrice);
					try {
						Main.database_handler.NewSharesManagedFunds(new_share);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error saving the new shares / managed funds." + System.lineSeparator() + "ALR will attempt to clean up any damage to the database.", "New Entity");
						try {
							Main.database_handler.DeleteEntity(NewEntityID);
							MessageBox.Information("All damage to the database was corrected.", "New Entity");
						} catch (SQLException e1) {
							MessageBox.Error("Damage to the database could not be corrected automatically." + System.lineSeparator() + "Please manually remove orphaned entity with ID " + NewEntityID + " from the database.", "New Entity");
						}
					}
				} else if (strType.equals(Main.ENTITY_TYPES[7])) {
					// Insurance
					BigDecimal InsuredAmount = new BigDecimal(spinInsuredAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					BigDecimal InsurancePremium = new BigDecimal(spinInsurancePremium.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					Insurance new_policy = new Insurance(NewEntityID, InsuredAmount, InsurancePremium);
					try {
						Main.database_handler.NewInsurance(new_policy);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error saving the new insurance policy." + System.lineSeparator() + "ALR will attempt to clean up any damage to the database.", "New Entity");
						try {
							Main.database_handler.DeleteEntity(NewEntityID);
							MessageBox.Information("All damage to the database was corrected.", "New Entity");
						} catch (SQLException e1) {
							MessageBox.Error("Damage to the database could not be corrected automatically." + System.lineSeparator() + "Please manually remove orphaned entity with ID " + NewEntityID + " from the database.", "New Entity");
						}
					}
				}
			}
			
			Close();
		}
	}
	
	// Called on close to dispose the JFrame
	private void Close() {
		this.dispose();
	}
	
	public frmNewEntity(String EntityType) {	
		this.strType = EntityType;
		
		// Set Title
		setTitle("New Entity [" + strType + "]");
		
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
		
		if (strType.equals(Main.ENTITY_TYPES[0])) {
			// Savings Account
			contentPane.setPreferredSize(new Dimension(500, 394));
		} else if (strType.equals(Main.ENTITY_TYPES[1])) {
			// Credit Card
			contentPane.setPreferredSize(new Dimension(500, 459));
		} else if (strType.equals(Main.ENTITY_TYPES[2])) {
			// Loan Account
			contentPane.setPreferredSize(new Dimension(500, 459));
		} else if (strType.equals(Main.ENTITY_TYPES[3])) {
			// Term Deposit
			contentPane.setPreferredSize(new Dimension(500, 529));
		} else if (strType.equals(Main.ENTITY_TYPES[4])) {
			// Superannuation
			contentPane.setPreferredSize(new Dimension(500, 394));
		} else if (strType.equals(Main.ENTITY_TYPES[5])) {
			// Property
			contentPane.setPreferredSize(new Dimension(500, 704));
		} else if (strType.equals(Main.ENTITY_TYPES[6])) {
			// Shares/Managed Funds
			contentPane.setPreferredSize(new Dimension(500, 459));
		} else {
			// Insurance
			contentPane.setPreferredSize(new Dimension(500, 459));
		}
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// Prepare the combobox model with the list of holders
		// This needs to be done before GUI elements are created since the model is required to create the GUI elements
		try {
			RefreshHolders();
		} catch (SQLException e1) {
			MessageBox.Error("There was a database related error populating the list of holders.", "New Entity");
			Close();
		}

		// GENERIC GUI ELEMENTS
		// Generic Fields Label
		lblGeneric = new JLabel("<HTML><U>Generic Fields</U></HTML>");
		lblGeneric.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));

		// Entity Number Label
		lblNumber = new JLabel("*Number:");
		
		// Entity Number Textfield
		txtNumber = new JTextField();
		txtNumber.setColumns(10);
				
		// Institution Label
		lblInstitution = new JLabel("*Institution:");

		// Institution Combobox
		comboInst = new JComboBox<String>();
		
		// Status Label
		lblStatus = new JLabel("*Status:");
				
		// Status Combobox
		comboStatus = new JComboBox<String>();
				
		// Opening Date Label
		lblStartDate = new JLabel("*Opening Date:");
		
		// Opening Date Picker
		DatePickerSettings OpenDateSettings = new DatePickerSettings();
		OpenDateSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
		OpenDateSettings.setFormatForDatesBeforeCommonEra("dd/MM/uuuu");
		OpenDatePicker = new DatePicker(OpenDateSettings);
		OpenDatePicker.getComponentToggleCalendarButton().setToolTipText("Change Date");
		OpenDatePicker.getComponentDateTextField().setHorizontalAlignment(SwingConstants.RIGHT);
		OpenDatePicker.setDateToToday();
		
		// Closing Date Label
		lblEndDate = new JLabel("Closing Date:");
				
		// Closing Date Picker
		DatePickerSettings CloseDateSettings = new DatePickerSettings();
		CloseDateSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
		CloseDateSettings.setFormatForDatesBeforeCommonEra("dd/MM/uuuu");
		CloseDatePicker = new DatePicker(CloseDateSettings);
		CloseDatePicker.getComponentToggleCalendarButton().setToolTipText("Change Date");
		CloseDatePicker.getComponentDateTextField().setHorizontalAlignment(SwingConstants.RIGHT);
				
		// Description Label
		lblDescription = new JLabel("Description:");
		
		// Description Textfield
		txtDescription = new JTextField();
		txtDescription.setColumns(10);
				
		// Comments Label
		lblComments = new JLabel("Comments:");
		
		// Comments Textfield
		txtComments = new JTextField();
		txtComments.setColumns(10);
		
		// Entity Holders Label
		lblEntityHolders = new JLabel("Entity Holders");
		lblEntityHolders.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		lblEntityHolders.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Entity Holders Scroll Pane
		EntityHolderList = new JList<String>(EntityHolderListModel);
		EntityHolderList.setBackground(new Color(230, 255, 236));
		EntityHolderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		EntityHolderListPanel = new JScrollPane(EntityHolderList);
		EntityHolderListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		EntityHolderListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Available Holders Label
		lblHolderBank = new JLabel("Available Holders");
		lblHolderBank.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		lblHolderBank.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Holder Bank Scroll Pane
		BankHolderList = new JList<String>(BankHolderListModel);
		BankHolderList.setBackground(new Color(255, 230, 231));
		BankHolderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		BankHolderListPanel = new JScrollPane(BankHolderList);
		BankHolderListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		BankHolderListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Add Holder Button
		btnAdd = new JButton("<html> <center>" + "&#x2190;" + "</center> </html>");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddHolder();
			}
		});
		
		// Remove Holder Button
		btnRemove = new JButton("<html> <center>" + "&#x2192;" + "</center> </html>");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RemoveHolder();
			}
		});
		
		// First Separator
		separator1 = new JSeparator();
		
		// Entity Specific Fields Label
		lblSpecific = new JLabel("<HTML><U>Entity Specific Fields</U></HTML>");
		lblSpecific.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		
		// Second Separator
		separator2 = new JSeparator();
		
		// Save Button
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save();
			}
		});
		
		// Notification Label
		lblNotif = new JLabel("<HTML><I>Entity history and linked services may be added via the main search function once the entity is created here.</I></HTML>");
		lblNotif.setForeground(Main.colDarkRed);
		
		// ENTITY SPECIFIC GUI ELEMENTS
		// --> CREDIT CARD <--
		
		// Limit Label
		lblLimit = new JLabel("*Limit ($):");
		
		// Limit Spinner
		SpinnerNumberModel LimitSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinLimit = new JSpinner(LimitSpinModel);
		JSpinner.NumberEditor LimitSpinEditor = (JSpinner.NumberEditor)spinLimit.getEditor();
		DecimalFormat LimitSpinFormat = LimitSpinEditor.getFormat();
		LimitSpinFormat.setMinimumFractionDigits(2);
		LimitSpinFormat.setMaximumFractionDigits(2);
		LimitSpinFormat.setGroupingSize(3);
		spinLimit.setValue(0);
		
		// --> INSURANCE <--
		// Insurance Premium Label
		lblInsurancePremium = new JLabel("*Premium p.a. ($):");
		
		// Insurance Premium Spinner
		SpinnerNumberModel PremiumSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinInsurancePremium = new JSpinner(PremiumSpinModel);
		JSpinner.NumberEditor PremiumSpinEditor = (JSpinner.NumberEditor)spinInsurancePremium.getEditor();
        DecimalFormat PremiumSpinFormat = PremiumSpinEditor.getFormat();
        PremiumSpinFormat.setMinimumFractionDigits(2);
        PremiumSpinFormat.setMaximumFractionDigits(2);
        PremiumSpinFormat.setGroupingSize(3);
        spinInsurancePremium.setValue(0);
		
		// Insured Amount Label
		lblInsuredAmount = new JLabel("*Insured Amount ($):");
		
		// Insured Amount Spinner
		SpinnerNumberModel InsuredAmountSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinInsuredAmount = new JSpinner(InsuredAmountSpinModel);
		JSpinner.NumberEditor InsuredAmountSpinEditor = (JSpinner.NumberEditor)spinInsuredAmount.getEditor();
        DecimalFormat InsuredAmountSpinFormat = InsuredAmountSpinEditor.getFormat();
        InsuredAmountSpinFormat.setMinimumFractionDigits(2);
        InsuredAmountSpinFormat.setMaximumFractionDigits(2);
        InsuredAmountSpinFormat.setGroupingSize(3);
        spinInsuredAmount.setValue(0);
		
		// --> LOAN ACCOUNT <--
		// Loan Amount Label
		lblLoanAmount = new JLabel("*Loan Amount ($):");
		
		// Loan Amount Spinner
		SpinnerNumberModel LoanAmountSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinLoanAmount = new JSpinner(LoanAmountSpinModel);
		JSpinner.NumberEditor LoanAmountSpinEditor = (JSpinner.NumberEditor)spinLoanAmount.getEditor();
		DecimalFormat LoanAmountSpinFormat = LoanAmountSpinEditor.getFormat();
		LoanAmountSpinFormat.setMinimumFractionDigits(2);
		LoanAmountSpinFormat.setMaximumFractionDigits(2);
		LoanAmountSpinFormat.setGroupingSize(3);
		spinLoanAmount.setValue(0);
		
		// Current Value Label
		lblLoanCurrentValue = new JLabel("*Current Value ($):");
		
		// Current Value Spinner
		SpinnerNumberModel LoanCurrentValueSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinLoanCurrentValue = new JSpinner(LoanCurrentValueSpinModel);
		JSpinner.NumberEditor LoanCurrentValueSpinEditor = (JSpinner.NumberEditor)spinLoanCurrentValue.getEditor();
        DecimalFormat LoanCurrentValueSpinFormat = LoanCurrentValueSpinEditor.getFormat();
        LoanCurrentValueSpinFormat.setMinimumFractionDigits(2);
        LoanCurrentValueSpinFormat.setMaximumFractionDigits(2);
        LoanCurrentValueSpinFormat.setGroupingSize(3);
        spinLoanCurrentValue.setValue(0);
		
		// --> PROPERTY <--
        // Address Label
        lblAddress = new JLabel("*Address:");
		
		// Address Textfield
		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		
		// Property Purchase Amount Label
		lblPropertyPurchaseAmount = new JLabel("*Purchase Amount ($):");
		
		// Property Purchase Amount Spinner
		SpinnerNumberModel PropertyPurchaseAmountSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertyPurchaseAmount = new JSpinner(PropertyPurchaseAmountSpinModel);
		JSpinner.NumberEditor PropertyPurchaseAmountSpinEditor = (JSpinner.NumberEditor)spinPropertyPurchaseAmount.getEditor();
		DecimalFormat PropertyPurchaseAmountSpinFormat = PropertyPurchaseAmountSpinEditor.getFormat();
		PropertyPurchaseAmountSpinFormat.setMinimumFractionDigits(2);
		PropertyPurchaseAmountSpinFormat.setMaximumFractionDigits(2);
		PropertyPurchaseAmountSpinFormat.setGroupingSize(3);
		spinPropertyPurchaseAmount.setValue(0);
		
		// Property Sold Amount Label
		lblPropertySoldAmount = new JLabel("*Sold Amount ($):");
		
		// Property Sold Amount Spinner
		SpinnerNumberModel PropertySoldAmountSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertySoldAmount = new JSpinner(PropertySoldAmountSpinModel);
		JSpinner.NumberEditor PropertySoldAmountSpinEditor = (JSpinner.NumberEditor)spinPropertySoldAmount.getEditor();
		DecimalFormat PropertySoldAmountSpinFormat = PropertySoldAmountSpinEditor.getFormat();
		PropertySoldAmountSpinFormat.setMinimumFractionDigits(2);
		PropertySoldAmountSpinFormat.setMaximumFractionDigits(2);
		PropertySoldAmountSpinFormat.setGroupingSize(3);
		spinPropertySoldAmount.setValue(0);
		
		// Property Current Value Label
		lblPropertyCurrentValue = new JLabel("*Current Value ($):");
		
		// Property Current Value Spinner
		SpinnerNumberModel PropertyCurrentValueSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertyCurrentValue = new JSpinner(PropertyCurrentValueSpinModel);
		JSpinner.NumberEditor PropertyCurrentValueSpinEditor = (JSpinner.NumberEditor)spinPropertyCurrentValue.getEditor();
		DecimalFormat PropertyCurrentValueSpinFormat = PropertyCurrentValueSpinEditor.getFormat();
		PropertyCurrentValueSpinFormat.setMinimumFractionDigits(2);
		PropertyCurrentValueSpinFormat.setMaximumFractionDigits(2);
		PropertyCurrentValueSpinFormat.setGroupingSize(3);
		spinPropertyCurrentValue.setValue(0);
		
		// Property Solicitors Fees (Buy) Label
		lblPropertySolFeeBuy = new JLabel("*Solicitor Fees (Buy) ($):");
		
		// Property Solicitors Fees (Buy) Spinner
		SpinnerNumberModel PropertySolFeeBuySpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertySolFeeBuy = new JSpinner(PropertySolFeeBuySpinModel);
		JSpinner.NumberEditor PropertySolFeeBuySpinEditor = (JSpinner.NumberEditor)spinPropertySolFeeBuy.getEditor();
		DecimalFormat PropertySolFeeBuySpinFormat = PropertySolFeeBuySpinEditor.getFormat();
		PropertySolFeeBuySpinFormat.setMinimumFractionDigits(2);
		PropertySolFeeBuySpinFormat.setMaximumFractionDigits(2);
		PropertySolFeeBuySpinFormat.setGroupingSize(3);
		spinPropertySolFeeBuy.setValue(0);
		
		// Property Solicitors Fees (Sell) Label
		lblPropertySolFeeSell = new JLabel("*Solicitor Fees (Sell) ($):");
		
		// Property Solicitors Fees (Sell) Spinner
		SpinnerNumberModel PropertySolFeeSellSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertySolFeeSell = new JSpinner(PropertySolFeeSellSpinModel);
		JSpinner.NumberEditor PropertySolFeeSellSpinEditor = (JSpinner.NumberEditor)spinPropertySolFeeSell.getEditor();
		DecimalFormat PropertySolFeeSellSpinFormat = PropertySolFeeSellSpinEditor.getFormat();
		PropertySolFeeSellSpinFormat.setMinimumFractionDigits(2);
		PropertySolFeeSellSpinFormat.setMaximumFractionDigits(2);
		PropertySolFeeSellSpinFormat.setGroupingSize(3);
		spinPropertySolFeeSell.setValue(0);
		
		// Property Govt Charges (Buy) Label
		lblPropertyGovtChargesBuy = new JLabel("*Govt Charges (Buy) ($):");
		
		// Property Govt Charges (Buy) Spinner
		SpinnerNumberModel PropertyGovtChargesBuySpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertyGovtChargesBuy = new JSpinner(PropertyGovtChargesBuySpinModel);
		JSpinner.NumberEditor PropertyGovtChargesBuySpinEditor = (JSpinner.NumberEditor)spinPropertyGovtChargesBuy.getEditor();
		DecimalFormat PropertyGovtChargesBuySpinFormat = PropertyGovtChargesBuySpinEditor.getFormat();
		PropertyGovtChargesBuySpinFormat.setMinimumFractionDigits(2);
		PropertyGovtChargesBuySpinFormat.setMaximumFractionDigits(2);
		PropertyGovtChargesBuySpinFormat.setGroupingSize(3);
		spinPropertyGovtChargesBuy.setValue(0);
		
		// Property Govt Fees (Sell) Label
		lblPropertyGovtChargesSell = new JLabel("*Govt Charges (Sell) ($):");
		
		// Property Govt Charges (Sell) Spinner
		SpinnerNumberModel PropertyGovtChargesSellSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertyGovtChargesSell = new JSpinner(PropertyGovtChargesSellSpinModel);
		JSpinner.NumberEditor PropertyGovtChargesSellSpinEditor = (JSpinner.NumberEditor)spinPropertyGovtChargesSell.getEditor();
		DecimalFormat PropertyGovtChargesSellSpinFormat = PropertyGovtChargesSellSpinEditor.getFormat();
		PropertyGovtChargesSellSpinFormat.setMinimumFractionDigits(2);
		PropertyGovtChargesSellSpinFormat.setMaximumFractionDigits(2);
		PropertyGovtChargesSellSpinFormat.setGroupingSize(3);
		spinPropertyGovtChargesSell.setValue(0);
		
		// Property Agent Fees (Buy) Label
		lblPropertyAgtFeeBuy = new JLabel("*Agent Fees (Buy) ($):");
		
		// Property Agent Fees (Buy) Spinner
		SpinnerNumberModel PropertyAgtFeeBuySpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertyAgtFeeBuy = new JSpinner(PropertyAgtFeeBuySpinModel);
		JSpinner.NumberEditor PropertyAgtFeeBuySpinEditor = (JSpinner.NumberEditor)spinPropertyAgtFeeBuy.getEditor();
		DecimalFormat PropertyAgtFeeBuySpinFormat = PropertyAgtFeeBuySpinEditor.getFormat();
		PropertyAgtFeeBuySpinFormat.setMinimumFractionDigits(2);
		PropertyAgtFeeBuySpinFormat.setMaximumFractionDigits(2);
		PropertyAgtFeeBuySpinFormat.setGroupingSize(3);
		spinPropertyAgtFeeBuy.setValue(0);
		
		// Property Agent Fees (Sell) Label
		lblPropertyAgtFeeSell = new JLabel("*Agent Fees (Sell) ($):");
		
		// Property Agent Fees (Sell) Spinner
		SpinnerNumberModel PropertyAgtFeeSellSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertyAgtFeeSell = new JSpinner(PropertyAgtFeeSellSpinModel);
		JSpinner.NumberEditor PropertyAgtFeeSellSpinEditor = (JSpinner.NumberEditor)spinPropertyAgtFeeSell.getEditor();
		DecimalFormat PropertyAgtFeeSellSpinFormat = PropertyAgtFeeSellSpinEditor.getFormat();
		PropertyAgtFeeSellSpinFormat.setMinimumFractionDigits(2);
		PropertyAgtFeeSellSpinFormat.setMaximumFractionDigits(2);
		PropertyAgtFeeSellSpinFormat.setGroupingSize(3);
		spinPropertyAgtFeeSell.setValue(0);
		
		// Property Capital Gains Tax Label
		lblPropertyCapGainsTax = new JLabel("*Capital Gains Tax ($):");
		
		// Property Capital Gains Tax Spinner
		SpinnerNumberModel PropertyCapitalGainsTaxSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinPropertyCapGainsTax = new JSpinner(PropertyCapitalGainsTaxSpinModel);
		JSpinner.NumberEditor PropertyCapitalGainsTaxSpinEditor = (JSpinner.NumberEditor)spinPropertyCapGainsTax.getEditor();
		DecimalFormat PropertyCapitalGainsTaxSpinFormat = PropertyCapitalGainsTaxSpinEditor.getFormat();
		PropertyCapitalGainsTaxSpinFormat.setMinimumFractionDigits(2);
		PropertyCapitalGainsTaxSpinFormat.setMaximumFractionDigits(2);
		PropertyCapitalGainsTaxSpinFormat.setGroupingSize(3);
		spinPropertyCapGainsTax.setValue(0);
		
		// --> SAVINGS ACCOUNT <--
		// Nothing Extra Needed
		
		// --> SHARES / MANAGED FUNDS <--
		// Share Price Label
		lblCurrentUnitShareValue = new JLabel("*Share Price ($):");
		
		// Share Price Spinner
		SpinnerNumberModel CurrentUnitShareValueSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinCurrentUnitShareValue = new JSpinner(CurrentUnitShareValueSpinModel);
		JSpinner.NumberEditor CurrentUnitShareValueSpinEditor = (JSpinner.NumberEditor)spinCurrentUnitShareValue.getEditor();
		DecimalFormat CurrentUnitShareValueSpinFormat = CurrentUnitShareValueSpinEditor.getFormat();
		CurrentUnitShareValueSpinFormat.setMinimumFractionDigits(2);
		CurrentUnitShareValueSpinFormat.setMaximumFractionDigits(2);
		CurrentUnitShareValueSpinFormat.setGroupingSize(3);
		spinCurrentUnitShareValue.setValue(0);
		
		// --> SUPERANNUATION <--
		// Nothing Extra Needed
		
		// --> TERM DEPOSIT <--
		// Term Deposit Opening Balance Label
		lblTermDepositOpeningBalance = new JLabel("*Opening Balance ($):");
		
		// Term Deposit Opening Balance Spinner
		SpinnerNumberModel TermDepositOpeningBalanceSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinTermDepositOpeningBalance = new JSpinner(TermDepositOpeningBalanceSpinModel);
		JSpinner.NumberEditor TermDepositOpeningBalanceSpinEditor = (JSpinner.NumberEditor)spinTermDepositOpeningBalance.getEditor();
		DecimalFormat TermDepositOpeningBalanceSpinFormat = TermDepositOpeningBalanceSpinEditor.getFormat();
		TermDepositOpeningBalanceSpinFormat.setMinimumFractionDigits(2);
		TermDepositOpeningBalanceSpinFormat.setMaximumFractionDigits(2);
		TermDepositOpeningBalanceSpinFormat.setGroupingSize(3);
		spinTermDepositOpeningBalance.setValue(0);
		
		// Term Deposit Interest Amount Label
		lblTermDepositInterestAmount = new JLabel("*Interest Amount ($):");
		
		// Term Deposit Interest Amount Spinner
		SpinnerNumberModel TermDepositInterestAmountSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinTermDepositInterestAmount = new JSpinner(TermDepositInterestAmountSpinModel);
		JSpinner.NumberEditor TermDepositInterestAmountSpinEditor = (JSpinner.NumberEditor)spinTermDepositInterestAmount.getEditor();
		DecimalFormat TermDepositInterestAmountSpinFormat = TermDepositInterestAmountSpinEditor.getFormat();
		TermDepositInterestAmountSpinFormat.setMinimumFractionDigits(2);
		TermDepositInterestAmountSpinFormat.setMaximumFractionDigits(2);
		TermDepositInterestAmountSpinFormat.setGroupingSize(3);
		spinTermDepositInterestAmount.setValue(0);
		
		// Term Deposit Govt Charges Label
		lblTermDepositOtherCharges = new JLabel("*Other Charges ($):");
		
		// Term Deposit Govt Charges Spinner
		SpinnerNumberModel TermDepositGovtChargesSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinTermDepositOtherCharges = new JSpinner(TermDepositGovtChargesSpinModel);
		JSpinner.NumberEditor TermDepositGovtChargesSpinEditor = (JSpinner.NumberEditor)spinTermDepositOtherCharges.getEditor();
		DecimalFormat TermDepositGovtChargesSpinFormat = TermDepositGovtChargesSpinEditor.getFormat();
		TermDepositGovtChargesSpinFormat.setMinimumFractionDigits(2);
		TermDepositGovtChargesSpinFormat.setMaximumFractionDigits(2);
		TermDepositGovtChargesSpinFormat.setGroupingSize(3);
		spinTermDepositOtherCharges.setValue(0);
		
		// Term Deposit Bank Fees Label
		lblTermDepositBankFees = new JLabel("*Bank Fees ($):");
		
		// Term Deposit Bank Fees Spinner
		SpinnerNumberModel TermDepositBankFeesSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinTermDepositBankFees = new JSpinner(TermDepositBankFeesSpinModel);
		JSpinner.NumberEditor TermDepositBankFeesSpinEditor = (JSpinner.NumberEditor)spinTermDepositBankFees.getEditor();
		DecimalFormat TermDepositBankFeesSpinFormat = TermDepositBankFeesSpinEditor.getFormat();
		TermDepositBankFeesSpinFormat.setMinimumFractionDigits(2);
		TermDepositBankFeesSpinFormat.setMaximumFractionDigits(2);
		TermDepositBankFeesSpinFormat.setGroupingSize(3);
		spinTermDepositBankFees.setValue(0);
		
		// Term Deposit Interest Rate Label
		lblTermDepositInterestRate = new JLabel("*Interest Rate (%):");
		
		// Term Deposit Interest Rate Spinner
		SpinnerNumberModel TermDepositInterestRateSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
		spinTermDepositInterestRate = new JSpinner(TermDepositInterestRateSpinModel);
		JSpinner.NumberEditor TermDepositInterestRateSpinEditor = (JSpinner.NumberEditor)spinTermDepositInterestRate.getEditor();
		DecimalFormat TermDepositInterestRateSpinFormat = TermDepositInterestRateSpinEditor.getFormat();
		TermDepositInterestRateSpinFormat.setMinimumFractionDigits(2);
		TermDepositInterestRateSpinFormat.setMaximumFractionDigits(2);
		TermDepositInterestRateSpinFormat.setGroupingSize(3);
		spinTermDepositInterestRate.setValue(0);
		
		// Populate remaining GUI components
		try {
			LoadGUI();
			GetInstitutions();
			GetStatus();
		} catch (SQLException e) {
			MessageBox.Error("There was a database related error populating the institutions and possible entity statuses.", "New Entity");
			Close();
		}
	}
	
	private void LoadGUI() {
		// Position Generic GUI Elements
		lblGeneric.setBounds(6, 5, 100, 20);
		lblNumber.setBounds(6, 30, 65, 30);
		txtNumber.setBounds(80, 30, 414, 30);
		lblInstitution.setBounds(6, 65, 80, 30);
		comboInst.setBounds(80, 65, 150, 30);
		lblStatus.setBounds(6, 100, 70, 30);
		comboStatus.setBounds(80, 100, 150, 30);
		lblStartDate.setBounds(240, 65, 100, 30);
		OpenDatePicker.setBounds(357, 65, 137, 30);
		lblEndDate.setBounds(240, 100, 100, 30);
		CloseDatePicker.setBounds(357, 100, 137, 30);
		lblDescription.setBounds(6, 135, 80, 30);
		txtDescription.setBounds(80, 135, 414, 30);
		lblComments.setBounds(6, 170, 75, 30);
		txtComments.setBounds(80, 170, 414, 30);
		lblEntityHolders.setBounds(16, 206, 200, 30);
		lblHolderBank.setBounds(284, 206, 200, 30);
		EntityHolderListPanel.setBounds(16, 233, 200, 104);
		BankHolderListPanel.setBounds(284, 233, 200, 104);
		btnAdd.setBounds(216, 233, 68, 30);
		btnRemove.setBounds(216, 307, 68, 30);
		
		// Add Genetic GUI Elements
		contentPane.add(lblGeneric);
		contentPane.add(lblNumber);
		contentPane.add(txtNumber);
		contentPane.add(lblInstitution);
		contentPane.add(comboInst);
		contentPane.add(lblStatus);
		contentPane.add(comboStatus);
		contentPane.add(lblStartDate);
		contentPane.add(OpenDatePicker);
		contentPane.add(lblEndDate);
		contentPane.add(CloseDatePicker);
		contentPane.add(lblDescription);
		contentPane.add(txtDescription);
		contentPane.add(lblComments);
		contentPane.add(txtComments);
		contentPane.add(lblEntityHolders);
		contentPane.add(lblHolderBank);
		contentPane.add(EntityHolderListPanel);
		contentPane.add(BankHolderListPanel);
		contentPane.add(btnAdd);
		contentPane.add(btnRemove);
		
		// Specific GUI Elements
		if (strType.equals("Savings Account")) {
			// Position
			separator2.setBounds(6, 342, 488, 12);
			btnSave.setBounds(394, 348, 100, 40);
			lblNotif.setBounds(6, 348, 350, 40);
			
			// Add
			contentPane.add(separator2);
			contentPane.add(btnSave);
			contentPane.add(lblNotif);
		} else if (strType.equals("Credit Card")) {
			// Position
			lblLimit.setBounds(6, 372, 115, 30);
			spinLimit.setBounds(80, 372, 414, 30);
			separator1.setBounds(6, 342, 488, 12);
			lblSpecific.setBounds(6, 347, 140, 20);
			separator2.setBounds(6, 407, 488, 12);
			btnSave.setBounds(394, 413, 100, 40);
			lblNotif.setBounds(6, 413, 350, 40);
			
			// Add
			contentPane.add(lblLimit);
			contentPane.add(spinLimit);
			contentPane.add(separator1);
			contentPane.add(lblSpecific);
			contentPane.add(separator2);
			contentPane.add(btnSave);
			contentPane.add(lblNotif);
		} else if (strType.equals("Loan Account")) {
			// Position
			lblLoanAmount.setBounds(6, 372, 140, 30);
			spinLoanAmount.setBounds(120, 372, 124, 30);
			lblLoanCurrentValue.setBounds(254, 372, 115, 30);
			spinLoanCurrentValue.setBounds(370, 372, 124, 30);
			separator1.setBounds(6, 342, 488, 12);
			lblSpecific.setBounds(6, 347, 140, 20);
	        separator2.setBounds(6, 407, 488, 12);
			btnSave.setBounds(394, 413, 100, 40);
			lblNotif.setBounds(6, 413, 350, 40);
			
			// Add
			contentPane.add(lblLoanAmount);
			contentPane.add(spinLoanAmount);
			contentPane.add(lblLoanCurrentValue);
			contentPane.add(spinLoanCurrentValue);
			contentPane.add(separator1);
			contentPane.add(lblSpecific);
			contentPane.add(separator2);
			contentPane.add(btnSave);
			contentPane.add(lblNotif);
		} else if (strType.equals("Term Deposit")) {
			// Position
			lblTermDepositOpeningBalance.setBounds(6, 372, 150, 30);
			spinTermDepositOpeningBalance.setBounds(140, 372, 354, 30);
			lblTermDepositInterestAmount.setBounds(6, 407, 130, 30);
			spinTermDepositInterestAmount.setBounds(140, 407, 120, 30);
			lblTermDepositInterestRate.setBounds(270, 407, 115, 30);
			spinTermDepositInterestRate.setBounds(374, 407, 120, 30);
			lblTermDepositOtherCharges.setBounds(6, 442, 115, 30);
			spinTermDepositOtherCharges.setBounds(140, 442, 120, 30);
			lblTermDepositBankFees.setBounds(270, 442, 115, 30);
			spinTermDepositBankFees.setBounds(374, 442, 120, 30);
			separator1.setBounds(6, 342, 488, 12);
			lblSpecific.setBounds(6, 347, 140, 20);
			separator2.setBounds(6, 477, 488, 12);
			btnSave.setBounds(394, 483, 100, 40);
			lblNotif.setBounds(6, 483, 350, 40);
			
			// Add
			contentPane.add(lblTermDepositOpeningBalance);
			contentPane.add(spinTermDepositOpeningBalance);
			contentPane.add(lblTermDepositInterestAmount);
			contentPane.add(spinTermDepositInterestAmount);
			contentPane.add(lblTermDepositInterestRate);
			contentPane.add(spinTermDepositInterestRate);
			contentPane.add(lblTermDepositOtherCharges);
			contentPane.add(spinTermDepositOtherCharges);
			contentPane.add(lblTermDepositBankFees);
			contentPane.add(spinTermDepositBankFees);
			contentPane.add(separator1);
			contentPane.add(lblSpecific);
			contentPane.add(separator2);
			contentPane.add(btnSave);
			contentPane.add(lblNotif);
		} else if (strType.equals("Superannuation")) {
			// Position
			separator2.setBounds(6, 342, 488, 12);
			btnSave.setBounds(394, 348, 100, 40);
			lblNotif.setBounds(6, 348, 350, 40);
			
			// Add
			contentPane.add(separator2);
			contentPane.add(btnSave);
			contentPane.add(lblNotif);
		} else if (strType.equals("Property")) {
			// Position
			lblAddress.setBounds(6, 372, 140, 30);
			txtAddress.setBounds(80, 372, 414, 30);
			lblPropertyPurchaseAmount.setBounds(6, 407, 140, 30);
			spinPropertyPurchaseAmount.setBounds(140, 407, 354, 30);
			lblPropertySoldAmount.setBounds(6, 442, 140, 30);
			spinPropertySoldAmount.setBounds(140, 442, 354, 30);
			lblPropertyCurrentValue.setBounds(6, 477, 140, 30);
			spinPropertyCurrentValue.setBounds(140, 477, 354, 30);
			lblPropertySolFeeBuy.setBounds(6, 512, 140, 30);
			spinPropertySolFeeBuy.setBounds(145, 512, 105, 30);
			lblPropertySolFeeSell.setBounds(253, 512, 140, 30);
			spinPropertySolFeeSell.setBounds(389, 512, 105, 30);
			lblPropertyGovtChargesBuy.setBounds(6, 547, 140, 30);
			spinPropertyGovtChargesBuy.setBounds(145, 547, 105, 30);
			lblPropertyGovtChargesSell.setBounds(253, 547, 140, 30);
			spinPropertyGovtChargesSell.setBounds(389, 547, 105, 30);
			lblPropertyAgtFeeBuy.setBounds(6, 582, 140, 30);
			spinPropertyAgtFeeBuy.setBounds(145, 582, 105, 30);
			lblPropertyAgtFeeSell.setBounds(253, 582, 140, 30);
			spinPropertyAgtFeeSell.setBounds(389, 582, 105, 30);
			lblPropertyCapGainsTax.setBounds(6, 617, 140, 30);
			spinPropertyCapGainsTax.setBounds(145, 617, 105, 30);
			separator1.setBounds(6, 342, 488, 12);
			lblSpecific.setBounds(6, 347, 140, 20);
			separator2.setBounds(6, 652, 488, 12);
			btnSave.setBounds(394, 658, 100, 40);
			lblNotif.setBounds(6, 658, 350, 40);
			
			// Add
			contentPane.add(lblAddress);
			contentPane.add(txtAddress);
			contentPane.add(lblPropertyPurchaseAmount);
			contentPane.add(spinPropertyPurchaseAmount);
			contentPane.add(lblPropertySoldAmount);
			contentPane.add(spinPropertySoldAmount);
			contentPane.add(lblPropertyCurrentValue);
			contentPane.add(spinPropertyCurrentValue);
			contentPane.add(lblPropertySolFeeBuy);
			contentPane.add(spinPropertySolFeeBuy);
			contentPane.add(lblPropertySolFeeSell);
			contentPane.add(spinPropertySolFeeSell);
			contentPane.add(lblPropertyGovtChargesBuy);
			contentPane.add(spinPropertyGovtChargesBuy);
			contentPane.add(lblPropertyGovtChargesSell);
			contentPane.add(spinPropertyGovtChargesSell);
			contentPane.add(lblPropertyAgtFeeBuy);
			contentPane.add(spinPropertyAgtFeeBuy);
			contentPane.add(lblPropertyAgtFeeSell);
			contentPane.add(spinPropertyAgtFeeSell);
			contentPane.add(lblPropertyCapGainsTax);
			contentPane.add(spinPropertyCapGainsTax);
			contentPane.add(separator1);
			contentPane.add(lblSpecific);
			contentPane.add(separator2);
			contentPane.add(btnSave);
			contentPane.add(lblNotif);
		} else if (strType.equals("Shares/Managed Funds")) {
			// Position
			lblCurrentUnitShareValue.setBounds(6, 372, 140, 30);
			spinCurrentUnitShareValue.setBounds(104, 372, 390, 30);
			separator1.setBounds(6, 342, 488, 12);
			lblSpecific.setBounds(6, 347, 140, 20);
	        separator2.setBounds(6, 407, 488, 12);
			btnSave.setBounds(394, 413, 100, 40);
			lblNotif.setBounds(6, 413, 350, 40);
			
			// Add
			contentPane.add(lblCurrentUnitShareValue);
			contentPane.add(spinCurrentUnitShareValue);
			contentPane.add(separator1);
			contentPane.add(lblSpecific);
			contentPane.add(separator2);
			contentPane.add(btnSave);
			contentPane.add(lblNotif);
		} else if (strType.equals("Insurance")) {
			// Position
			lblInsuredAmount.setBounds(6, 372, 140, 30);
	        spinInsuredAmount.setBounds(130, 372, 120, 30);
	        lblInsurancePremium.setBounds(260, 372, 115, 30);
	        spinInsurancePremium.setBounds(374, 372, 120, 30);
	        separator1.setBounds(6, 342, 488, 12);
			lblSpecific.setBounds(6, 347, 140, 20);
	        separator2.setBounds(6, 407, 488, 12);
			btnSave.setBounds(394, 413, 100, 40);
			lblNotif.setBounds(6, 413, 350, 40);
			
			// Add
			contentPane.add(lblInsuredAmount);
			contentPane.add(spinInsuredAmount);
			contentPane.add(lblInsurancePremium);
			contentPane.add(spinInsurancePremium);
			contentPane.add(separator1);
			contentPane.add(lblSpecific);
			contentPane.add(separator2);
			contentPane.add(btnSave);
			contentPane.add(lblNotif);
		}
	}
}