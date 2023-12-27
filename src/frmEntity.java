import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JSpinner;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swingViewer.*;
import org.graphstream.ui.view.*;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import CustomTypes.CreditCard;
import CustomTypes.Entity;
import CustomTypes.Holder;
import CustomTypes.Institution;
import CustomTypes.Insurance;
import CustomTypes.Loan;
import CustomTypes.Property;
import CustomTypes.Service;
import CustomTypes.Share;
import CustomTypes.ShareTransaction;
import CustomTypes.TermDeposit;
import CustomTypes.Transaction;

public class frmEntity extends JFrame {
	private static final long serialVersionUID = 4377093025213405434L;
	
	private frmNewEditService frmNewEditService;				// New/Edit services window
	private frmLinkedEntities frmLinkedEntities;				// Linked entities window
	private frmNewEditTxn frmNewEditTxn;						// New/Edit transaction window
	private frmNewEditShrTxn frmNewEditShrTxn;					// New/Edit share transaction window
	private boolean NewEditServiceOpen = false;					// Is the new/edit services window open?
	private boolean LinkedEntitiesOpen = false;					// Is the linked entities window open?
	private boolean NewEditTxnOpen = false;						// Is the new/edit transaction window open?
	private boolean NewEditShrTxnOpen = false;					// Is the new/edit share transaction window open?
	private String strEntity;									// Entity ID
	
	// Holders
	private DefaultListModel<String> HolderListModel;			// Entity holder list model
	private DefaultListModel<String> OtherHolderListModel;		// Holder bank list model
	private JList<String> HolderList;							// Entity holder list
	private JList<String> OtherHolderList;						// Holder bank list
	
	// Share Transactions
	private ArrayList<ShareTransaction> Buys = null;			// All share purchases
	private ArrayList<ShareTransaction> Sells = null;			// All share sells
	private ArrayList<ShareTransaction> Dividends = null;		// All dividends
	private ArrayList<ShareTransaction> DRPs = null;			// All DRPs
	
	// Generic Fields
	private JTextField txtNumber;								// Entity number
	private JComboBox<String> combInstitution;					// Entity institution
	private JComboBox<String> combAccStat;						// Entity status
	private DatePicker OpenDatePicker;							// Opening date
	private DatePicker CloseDatePicker;							// Closing date
	private JTextArea txtDesc;									// Entity description
	private JTextArea txtComm;									// Entity comments
	
	// Entity Specific Fields
	// Credit Card
	private JSpinner spinCreditCardLimit;						// Credit Card Limit
	
	// Insurance
	private JSpinner spinInsurancePremium;						// Insurance Premium Per Annum
	private JSpinner spinInsuredAmount;							// Insured Amount
	
	// Loan
	private JSpinner spinLoanAmount;							// Principal Loan Amount
	private JSpinner spinLoanCurrentValue;						// Amount Owing
	
	// Property
	private JTextField txtAddress;								// Property Address
	private JSpinner spinPropertyPurchaseAmount;				// Purchase Amount/Price
	private JSpinner spinPropertySoldAmount;					// Sale Amount/Price
	private JSpinner spinPropertyCurrentValue;					// Current Value
	private JSpinner spinPropertySolFeeBuy;						// Solicitor Fees (Buy)
	private JSpinner spinPropertySolFeeSell;					// Solicitor Fees (Sell)
	private JSpinner spinPropertyGovtChargesBuy;				// Government Charges (Buy)
	private JSpinner spinPropertyGovtChargesSell;				// Government Charges (Sell)
	private JSpinner spinPropertyAgtFeeBuy;						// Agent Fees (Buy)
	private JSpinner spinPropertyAgtFeeSell;					// Agent Fees (Sell)
	private JSpinner spinPropertyCapGainsTax;					// Capital Gains Tax
	
	// Shares / Managed Funds
	private JSpinner spinCurrentUnitShareValue;					// Current Share Price
	
	// Term Deposit
	private JSpinner spinTermDepositOpeningBalance;				// Opening Balance
	private JSpinner spinTermDepositInterestAmount;				// Interest Amount
	private JSpinner spinTermDepositOtherCharges;				// Government Charges
	private JSpinner spinTermDepositBankFees;					// Bank Fees
	private JSpinner spinTermDepositInterestRate;				// Interest Rate
	
	// Transactions
	private DefaultTableModel TxnTableModel;					// Transaction table model
	private JTable TxnTable;									// Transaction table
	private JLabel lblCurrentValue;								// Current value of entity
	private DefaultTableModel ShareBuyTableModel;				// Buy share table model
	private JTable ShareBuyTable;								// Buy share table
	private DefaultTableModel ShareSellTableModel;				// Sell share table model
	private JTable ShareSellTable;								// Sell share table
	private DefaultTableModel ShareDivTableModel;				// Dividend share table model
	private JTable ShareDivTable;								// Dividend share table
	private DefaultTableModel ShareDRPTableModel;				// DRP share table model
	private JTable ShareDRPTable;								// DRP share table
	private JLabel lblTotalSpent;								// Total spent on buying shares
	private JLabel lblTotalEarnt;								// Total earnt selling shares
	private JLabel lblTotalTaxableIncome;						// Taxable income from dividends
	private JLabel lblTotalReinvested;							// Reinvested amount via DRPs
	private JTextField txtTotalSharesHeld;						// Total shares held
	private JTextField txtTotalValue;							// Total value of held shares
	
	// Services
	private JLabel lblTotalServices;							// Total number of entity services
	private DefaultTableModel tblRelServModel;					// Services table model
	private JTable tblRelServ;									// Services table
	
	// Relationships
	private JPanel pnlRelTree;									// Relationship tree panel
	private Graph graph;										// Relationship tree graph
	private Viewer viewer;										// Relationship tree viewer
	private ViewPanel viewPanel;								// Relationship tree view panel
	
	private void CreateRelationshipTree() {
		// Find all entities linked to the currently opened entity
		String strEntities[] = FindAllEntities(this.strEntity);
		
		// Prepare graph
		graph = new SingleGraph("Relationship Tree");
		graph.addAttribute("ui.stylesheet", "graph { padding: 50px; fill-mode: none; } node { fill-color: rgb(127, 127, 127); size: 20px; text-color: white; text-size: 20px; text-alignment: under; } edge { fill-color: white; }");
		
		try {
			// Add nodes using linked entity results
			for (int intCtr = 0; intCtr < strEntities.length; intCtr++) {
				graph.addNode(strEntities[intCtr]);
				
				// Colour closed entities in red
				if(Main.database_handler.GetEntityFromID(strEntities[intCtr]).GetEntityStatusName().equalsIgnoreCase(Main.strClosed)) {
					graph.getNode(strEntities[intCtr]).setAttribute("ui.style", "fill-color: rgb(" + Integer.toString(Main.colDarkRed.getRed()) + ", " + Integer.toString(Main.colDarkRed.getGreen()) + ", " + Integer.toString(Main.colDarkRed.getBlue()) + ");");
				}
			}
			
			// Colour the selected entity red if it is closed
			String strColor = "";
			
			if (Main.database_handler.GetEntityFromID(strEntity).GetEntityStatusName().equalsIgnoreCase(Main.strClosed)) {
				strColor = "rgb(" + Integer.toString(Main.colDarkRed.getRed()) + ", " + Integer.toString(Main.colDarkRed.getGreen())+ ", " + Integer.toString(Main.colDarkRed.getBlue()) + ")";
			} else {
				strColor = "rgb(127, 127, 127)";
			}
			
			// Indicate the currently selected entity via a white outline
			graph.getNode(strEntity).setAttribute("ui.style",
					"stroke-mode: plain;"
					+ "stroke-width: 4px;"
					+ "stroke-color: rgb(255, 255, 255);"
					+ "fill-color: " + strColor + ";");
			
			try {
				// Create links between nodes
				for (int intCtr = 0; intCtr < strEntities.length; intCtr++) {
					String[] strParents = Main.database_handler.GetLinkedEntityIDFromEntityID(strEntities[intCtr]);
					if (strParents != null) {
						for (int intNCtr = 0; intNCtr < strParents.length; intNCtr++) {
							graph.addEdge(strParents[intNCtr] + " - " + strEntities[intCtr], strParents[intNCtr], strEntities[intCtr], true);
						}
					}
				}
				
				// Label nodes
				for (Node node : graph) {
					// TODO ADD HOLDER NAMES TO THIS AS REQUESTED BY RAVI
			        node.setAttribute("ui.label", node.getId() + " - " + Main.database_handler.GetEntityFromID(node.getId()).GetEntityNumber() + " [" + Main.database_handler.GetEntityFromID(node.getId()).GetInstitutionName() + "]");
			    }
			} catch (SQLException e) {
				MessageBox.Error("There was an error creating the relationship tree.", "Relationship Tree");
			}
		} catch (SQLException e) {
			MessageBox.Error("There was an error obtaining entity statuses.", "Relationship Tree");
		}
	}
	
	private String[] FindAllEntities(String strEntityID) {
		ArrayList<String> ArrListEntities = new ArrayList<String>();
		String strEntities[];
		
		try {
			int intLoopCtr = 0;
			ArrListEntities.add(strEntityID);
			
			// Search the entities for their parents and children
			while (intLoopCtr < ArrListEntities.size()) {
				// Add all parents
				String[] LinkedEntityIDs = Main.database_handler.GetLinkedEntityIDFromEntityID(ArrListEntities.get(intLoopCtr));
				if (LinkedEntityIDs != null) {
					List<String> ListLinkedEntityIDs = Arrays.asList(LinkedEntityIDs);					
					ArrListEntities.addAll(ListLinkedEntityIDs);
				}
				
				// Add all children
				String EntityIDs[] = Main.database_handler.GetEntityIDFromLinkedEntityID(ArrListEntities.get(intLoopCtr));
				if (EntityIDs != null) {
					List<String> ListEntityIDs = Arrays.asList(EntityIDs);
					ArrListEntities.addAll(ListEntityIDs);
				}
				
				// Remove duplicate entities
				ArrayList<String> tempArrListEntities = new ArrayList<String>();
				for (String strEntity : ArrListEntities) { 
					if (!tempArrListEntities.contains(strEntity)) { 
						tempArrListEntities.add(strEntity); 
					}
				}
				
				ArrListEntities = tempArrListEntities;
				intLoopCtr++;
			}
			
			strEntities = ArrListEntities.toArray(new String[0]);
			
		} catch (SQLException e) {
			MessageBox.Error("There was an error finding related entities.", "Relationship Tree");
			strEntities = null;
		}
		
		return strEntities;
	}
	
	private void RefreshHolders() throws SQLException {
		HolderListModel = new DefaultListModel<String>();
		OtherHolderListModel = new DefaultListModel<String>();
		
		// Get all Holders and Holders of the entity
		Holder[] AllHolders = Main.database_handler.GetAllHolders();
		String[] EntityHolderNames = Main.database_handler.GetEntityFromID(this.strEntity).GetHolderNames();
		
		// Place the Holder names in alphabetical order
		Arrays.sort(AllHolders);
		
		// Populate the Holder list models
		List<String> EntityHoldersList = Arrays.asList(EntityHolderNames);
		
		for (int intCtr = 0; intCtr < AllHolders.length; intCtr++) {
			if (EntityHoldersList.contains(AllHolders[intCtr].GetName())) {
				HolderListModel.addElement(AllHolders[intCtr].GetName());
			} else {
				OtherHolderListModel.addElement(AllHolders[intCtr].GetName());
			}
		}
	}
	
	private void AddHolder() {
		String SelectedHolder = "";
		
		if (OtherHolderList.getSelectedIndex() >= 0) {
			SelectedHolder = OtherHolderList.getSelectedValue();
			OtherHolderListModel.remove(OtherHolderList.getSelectedIndex());
			
			HolderListModel.addElement(SelectedHolder);
			OtherHolderList.clearSelection();
			HolderList.clearSelection();
		} else {
			MessageBox.Information("Please select a holder to add to the entity.", "Entity Holders");
		}
	}
	
	private void RemoveHolder() {
		String SelectedHolder = "";
		
		if (HolderList.getSelectedIndex() >= 0) {
			if (HolderListModel.getSize() - 1 != 0) {
				SelectedHolder = HolderList.getSelectedValue();
				HolderListModel.remove(HolderList.getSelectedIndex());
				
				OtherHolderListModel.addElement(SelectedHolder);
				HolderList.clearSelection();
				OtherHolderList.clearSelection();
			} else {
				MessageBox.Warning("The entity must have at least one holder.", "Entity Holders");
			}
		} else {
			MessageBox.Information("Please select a holder to remove from the entity.", "Entity Holders");
		}
	}
	
	private void GetEntityNumber() throws SQLException {
		txtNumber.setText(Main.database_handler.GetEntityFromID(strEntity).GetEntityNumber());
	}
	
	private void GetInstitutionName() throws SQLException {
		// POPULATE INSTITUTION COMBOBOX
		Institution[] AllInstitutions = Main.database_handler.GetAllInstitutions();
		Arrays.sort(AllInstitutions);
		for (int intCtr = 0; intCtr < AllInstitutions.length; intCtr++) {
			combInstitution.addItem(AllInstitutions[intCtr].GetName());
		}
		
		// SELECT THE CORRECT INSTITUTION
		String strInstitutionName = Main.database_handler.GetEntityFromID(strEntity).GetInstitutionName();
		for (int intctr = 0; intctr < AllInstitutions.length; intctr++) {
			if (AllInstitutions[intctr].GetName().equals(strInstitutionName)) {
				combInstitution.setSelectedIndex(intctr);
				break;
			}
	    }
	}
	
	private void GetEntityStatus() throws SQLException {
		// POPULATE STATUS COMBOBOX
		String Status = Main.database_handler.GetEntityFromID(strEntity).GetEntityStatusName();
		String Statuses[] = Main.database_handler.GetAllStatusNames();
		
		for (int intCtr = 0; intCtr < Statuses.length; intCtr++) {
			combAccStat.addItem(Statuses[intCtr]);
		}
		
		// SELECT THE CORRECT STATUS
		for (int intctr = 0; intctr < Statuses.length; intctr++) {
			if (Statuses[intctr].equals(Status)) {
				combAccStat.setSelectedIndex(intctr);
				break;
			}
	    }
	}
	
	private void GetDates() throws SQLException, ParseException {
		Entity entity_result = Main.database_handler.GetEntityFromID(strEntity);
		Calendar calendar = Calendar.getInstance();
		Date Date;
		int intDay;
		int intMonth;
		int intYear;
		
		// Set Opening Date
		Date = entity_result.GetOpenDate();
		calendar.setTime(Date);
		intDay = calendar.get(Calendar.DAY_OF_MONTH);
		intMonth = calendar.get(Calendar.MONTH);		// Caveat: January is '0' NOT '1'
		intYear = calendar.get(Calendar.YEAR);
		
		OpenDatePicker.setDate(LocalDate.of(intYear, intMonth + 1, intDay));
		
		// Set Closing Date (may be null since this field is non-mandatory)
		if (entity_result.GetCloseDate() != null) {
			Date = entity_result.GetCloseDate();
			calendar.setTime(Date);
			intDay = calendar.get(Calendar.DAY_OF_MONTH);
			intMonth = calendar.get(Calendar.MONTH);		// Caveat: January is '0' NOT '1'
			intYear = calendar.get(Calendar.YEAR);
			
			CloseDatePicker.setDate(LocalDate.of(intYear, intMonth + 1, intDay));
		} else {
			CloseDatePicker.clear();
		}
	}
	
	private void GetDescription() throws SQLException {
		String Description = Main.database_handler.GetEntityFromID(strEntity).GetDescription();
		txtDesc.setText(Description);
		txtDesc.setCaretPosition(0);
	}
	
	private void GetComment() throws SQLException {
		String Comments = Main.database_handler.GetEntityFromID(strEntity).GetComment();
		txtComm.setText(Comments);
		txtComm.setCaretPosition(0);
	}
	
	private void GetSpecific() throws SQLException {
		String EntityType = Main.database_handler.GetEntityFromID(strEntity).GetEntityTypeName();
		
		/* Note that we do not check for Savings Accounts and Superannuation Funds
		 * as these types of entities do not need/contain any additional information. */
		
		if (EntityType.equals(Main.ENTITY_TYPES[1])) {
			// CREDIT CARD
			CreditCard credit_card = Main.database_handler.GetCreditCardFromEntityID(strEntity);
			
			// Credit Card Limit
			spinCreditCardLimit.setValue(credit_card.GetCardLimit());
		} else if (EntityType.equals(Main.ENTITY_TYPES[2])) {
			// LOAN ACCOUNT
			Loan loan_account = Main.database_handler.GetLoanFromEntityID(strEntity);
			
			// Loan Principal
			spinLoanAmount.setValue(loan_account.GetLoanAmount());
			
			// Remaining Repayment Amount
			spinLoanCurrentValue.setValue(loan_account.GetCurrentValue());
		} else if (EntityType.equals(Main.ENTITY_TYPES[3])) {
			// TERM DEPOSIT
			TermDeposit term_deposit = Main.database_handler.GetTermDepositFromEntityID(strEntity);
			
			// Opening Balance
			spinTermDepositOpeningBalance.setValue(term_deposit.GetOpeningBalance());
			
			// Interest Amount
			if (term_deposit.GetInterestAmount() != null) {
				spinTermDepositInterestAmount.setValue(term_deposit.GetInterestAmount());
			} else {
				spinTermDepositInterestAmount.setValue(0);
			}
			
			// Government Charges
			if (term_deposit.GetOtherCharges() != null) {
				spinTermDepositOtherCharges.setValue(term_deposit.GetOtherCharges());
			} else {
				spinTermDepositOtherCharges.setValue(0);
			}
			
			// Bank Fees
			if (term_deposit.GetBankFees() != null) {
				spinTermDepositBankFees.setValue(term_deposit.GetBankFees());
			} else {
				spinTermDepositBankFees.setValue(0);
			}
			
			// Interest Rate
			if (term_deposit.GetInterestRate() != null) {
				spinTermDepositInterestRate.setValue(term_deposit.GetInterestRate());
			} else {
				spinTermDepositInterestRate.setValue(0);
			}
		} else if (EntityType.equals(Main.ENTITY_TYPES[5])) {
			// PROPERTY
			Property property = Main.database_handler.GetPropertyFromEntityID(strEntity);
			
			// Property Address
			txtAddress.setText(property.GetAddress());
			txtAddress.setCaretPosition(0);
			
			// Property Purchase Amount
			spinPropertyPurchaseAmount.setValue(property.GetPurchaseAmount());
			
			// Property Sold Amount
			if (property.GetSoldAmount() != null) {
				spinPropertySoldAmount.setValue(property.GetSoldAmount());
			} else {
				spinPropertySoldAmount.setValue(0);
			}
			
			// Property Current Value
			spinPropertyCurrentValue.setValue(property.GetCurrentValue());
			
			// Property Solicitor Fees (Buy)
			if (property.GetSolicitorFeesBuy() != null) {
				spinPropertySolFeeBuy.setValue(property.GetSolicitorFeesBuy());
			} else {
				spinPropertySolFeeBuy.setValue(0);
			}
			
			// Property Solicitor Fees (Sell)
			if (property.GetSolicitorFeesSell() != null) {
				spinPropertySolFeeSell.setValue(property.GetSolicitorFeesSell());
			} else {
				spinPropertySolFeeSell.setValue(0);
			}
			
			// Property Government Charges (Buy)
			if (property.GetGovernmentChargesBuy() != null) {
				spinPropertyGovtChargesBuy.setValue(property.GetGovernmentChargesBuy());
			} else {
				spinPropertyGovtChargesBuy.setValue(0);
			}
			
			// Property Government Charges (Sell)
			if (property.GetGovernmentChargesSell() != null) {
				spinPropertyGovtChargesSell.setValue(property.GetGovernmentChargesSell());
			} else {
				spinPropertyGovtChargesSell.setValue(0);
			}
			
			// Property Agent Fees (Buy)
			if (property.GetAgentFeesBuy() != null) {
				spinPropertyAgtFeeBuy.setValue(property.GetAgentFeesBuy());
			} else {
				spinPropertyAgtFeeBuy.setValue(0);
			}
			
			// Property Agent Fees (Sell)
			if (property.GetAgentFeesBuy() != null) {
				spinPropertyAgtFeeSell.setValue(property.GetAgentFeesBuy());
			} else {
				spinPropertyAgtFeeSell.setValue(0);
			}
			
			// Capital Gains Tax
			if (property.GetCapitalGainsTax() != null) {
				spinPropertyCapGainsTax.setValue(property.GetCapitalGainsTax());
			} else {
				spinPropertyCapGainsTax.setValue(0);
			}
		} else if (EntityType.equals(Main.ENTITY_TYPES[6])) {
			// SHARES / MANAGED FUNDS
			Share share = Main.database_handler.GetShareFromEntityID(strEntity);
			
			// Current Share Price
			spinCurrentUnitShareValue.setValue(share.GetCurrentUnitValue());
		} else if (EntityType.equals(Main.ENTITY_TYPES[7])) {
			// INSURANCE
			Insurance insurance_policy = Main.database_handler.GetInsuranceFromEntityID(strEntity);
			
			// Insured Amount
			spinInsuredAmount.setValue(insurance_policy.GetInsuredAmount());
			
			// Insurance Premium
			spinInsurancePremium.setValue(insurance_policy.GetPremium());
		}
	}
	
	private void RefreshTxnTable() {
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		DecimalFormat MoneyFormatter = new DecimalFormat("$#,##0.00;$-#,##0.00");
		
		try {
			// Get the selected row index
			int OldIndex = TxnTable.getSelectedRow();
			int NewIndex = -1;
			String strID = "";
			
			// Store the transaction ID corresponding to the currently selected row
			if (OldIndex >= 0) {
				strID = TxnTableModel.getValueAt(OldIndex, TxnTableModel.findColumn("ID")).toString();
			}
			
			// Clear the results table
			TxnTableModel.setRowCount(0);
			
			// Get new search results
			Transaction[] transactions = Main.database_handler.GetTransactions(strEntity);
			
			if (transactions != null) {
				Arrays.sort(transactions);
				
				for (int intCtr = 0; intCtr < transactions.length; intCtr++) {
					String TxnDate = dateFormat.format(transactions[intCtr].GetDate());
					TxnTableModel.addRow(new Object[]{
							transactions[intCtr].GetTransactionID(),
							TxnDate,
							transactions[intCtr].GetTransactionCategory(),
							MoneyFormatter.format(transactions[intCtr].GetDebit()),
							MoneyFormatter.format(transactions[intCtr].GetCredit()),
							transactions[intCtr].GetDescription(),
					});
				}
				
				TxnTableModel.fireTableDataChanged();
			}
				
			// If a row was previously selected
			if (OldIndex >= 0) {
				for (int intCtr = 0; intCtr < transactions.length; intCtr++) {
					if (strID.equals(transactions[intCtr].GetTransactionID())) {
						NewIndex = intCtr;
						break;
					}
				}
				
				if (NewIndex > 0) {
					// The entity was found
					TxnTable.getSelectionModel().addSelectionInterval(NewIndex, NewIndex);
				} else {
					// The entity was not found
					TxnTable.getSelectionModel().addSelectionInterval(0, 0);
				}
			}
		} catch (SQLException e) {
			MessageBox.Error("A database related error occurred trying to populate the transaction table.", "Transactions");
		}
	}
	
	private void RefreshShrTxnTable() {
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		DecimalFormat MoneyFormatter = new DecimalFormat("$#,##0.00;$-#,##0.00");
		
		// Get transactions
		ShareTransaction[] sharetransactions = null;
		try {
			sharetransactions = Main.database_handler.GetShareTransactions(strEntity);
		} catch (SQLException e1) {
			MessageBox.Error("A database related error occurred trying to populate the share transaction tables.", "Transactions");
		}
		
		if (sharetransactions != null) {
			// Get the selected row index
			Integer[] OldIndex = {ShareBuyTable.getSelectedRow(), ShareSellTable.getSelectedRow(), ShareDivTable.getSelectedRow(), ShareDRPTable.getSelectedRow()};
			Integer[] NewIndex = {-1, -1, -1, -1};
			String[] strID = {"", "", "", ""};
			
			// Store the transaction ID corresponding to the currently selected row
			if (OldIndex[0] >= 0) {
				strID[0] = ShareBuyTableModel.getValueAt(OldIndex[0], ShareBuyTableModel.findColumn("ID")).toString();
			}
			
			if (OldIndex[1] >= 0) {
				strID[1] = ShareSellTableModel.getValueAt(OldIndex[1], ShareBuyTableModel.findColumn("ID")).toString();
			}
			
			if (OldIndex[2] >= 0) {
				strID[2] = ShareDivTableModel.getValueAt(OldIndex[2], ShareBuyTableModel.findColumn("ID")).toString();
			}
			
			if (OldIndex[3] >= 0) {
				strID[3] = ShareDRPTableModel.getValueAt(OldIndex[3], ShareBuyTableModel.findColumn("ID")).toString();
			}
			
			// Clear the tables
			ShareBuyTableModel.setRowCount(0);
			ShareSellTableModel.setRowCount(0);
			ShareDivTableModel.setRowCount(0);
			ShareDRPTableModel.setRowCount(0);
			
			// Initialise variables
			Buys = new ArrayList<ShareTransaction>();
			Sells = new ArrayList<ShareTransaction>();
			Dividends = new ArrayList<ShareTransaction>();
			DRPs = new ArrayList<ShareTransaction>();
			
			if (sharetransactions != null) {
				Arrays.sort(sharetransactions);
				
				for (int intCtr = 0; intCtr < sharetransactions.length; intCtr++) {
					String TxnDate = dateFormat.format(sharetransactions[intCtr].GetDate());
					
					if (sharetransactions[intCtr].GetTransactionSubtype().equals(Main.SHR_TXN_TYPES[0])) {
						// Buy
						Buys.add(sharetransactions[intCtr]);
						
						// Net Payment = (Share Number * Share Price) + Brokerage Charges + Government Charges
						BigDecimal NetPayment = BigDecimal.valueOf(sharetransactions[intCtr].GetNumberShares());
						NetPayment = NetPayment.multiply(sharetransactions[intCtr].GetSharePrice());
						NetPayment = NetPayment.add(sharetransactions[intCtr].GetBrokerageCharges());
						NetPayment = NetPayment.add(sharetransactions[intCtr].GetOtherCharges());
						
						ShareBuyTableModel.addRow(new Object[]{
								sharetransactions[intCtr].GetTransactionID(),
								TxnDate,
								MoneyFormatter.format(sharetransactions[intCtr].GetSharePrice()),
								sharetransactions[intCtr].GetNumberShares(),
								MoneyFormatter.format(sharetransactions[intCtr].GetBrokerageCharges()),
								MoneyFormatter.format(sharetransactions[intCtr].GetOtherCharges()),
								MoneyFormatter.format(NetPayment.setScale(2, RoundingMode.HALF_EVEN)),
								sharetransactions[intCtr].GetDescription()
						});	
					} else if (sharetransactions[intCtr].GetTransactionSubtype().equals(Main.SHR_TXN_TYPES[1])) {
						// Sell
						Sells.add(sharetransactions[intCtr]);
						
						// Net Income = (Share Number * Share Price) - Brokerage Charges - Government Charges - Capital Gains Tax
						BigDecimal NetIncome = BigDecimal.valueOf(sharetransactions[intCtr].GetNumberShares());
						NetIncome = NetIncome.multiply(sharetransactions[intCtr].GetSharePrice());
						NetIncome = NetIncome.subtract(sharetransactions[intCtr].GetBrokerageCharges());
						NetIncome = NetIncome.subtract(sharetransactions[intCtr].GetOtherCharges());
						NetIncome = NetIncome.subtract(sharetransactions[intCtr].GetCapitalGainsTax());
						
						ShareSellTableModel.addRow(new Object[]{
								sharetransactions[intCtr].GetTransactionID(),
								TxnDate,
								MoneyFormatter.format(sharetransactions[intCtr].GetSharePrice()),
								sharetransactions[intCtr].GetNumberShares(),
								MoneyFormatter.format(sharetransactions[intCtr].GetBrokerageCharges()),
								MoneyFormatter.format(sharetransactions[intCtr].GetOtherCharges()),
								MoneyFormatter.format(sharetransactions[intCtr].GetCapitalGainsTax()),
								MoneyFormatter.format(NetIncome.setScale(2, RoundingMode.HALF_EVEN)),
								sharetransactions[intCtr].GetDescription()
						});
					} else if (sharetransactions[intCtr].GetTransactionSubtype().equals(Main.SHR_TXN_TYPES[2])) {
						// Dividend
						Dividends.add(sharetransactions[intCtr]);
						
						// Franking Credit Per Share = Dividend Amount Per Share * Company Tax Rate * Franking Percentage / (1 - Company Tax Rate)
						BigDecimal FrankingCreditPerShare = sharetransactions[intCtr].GetDividendAmount();
						FrankingCreditPerShare = FrankingCreditPerShare.multiply(Main.COMPANY_TAX_RATE);
						FrankingCreditPerShare = FrankingCreditPerShare.multiply(sharetransactions[intCtr].GetFrankingPercentage());
						FrankingCreditPerShare = FrankingCreditPerShare.divide(new BigDecimal(100), 12, RoundingMode.HALF_EVEN);
						FrankingCreditPerShare = FrankingCreditPerShare.divide(BigDecimal.ONE.subtract(Main.COMPANY_TAX_RATE), 12, RoundingMode.HALF_EVEN);
						
						// Total Dividends = Number of Shares * Dividend Amount Per Share
						BigDecimal TotalDividends = BigDecimal.valueOf(sharetransactions[intCtr].GetNumberShares());
						TotalDividends = TotalDividends.multiply(sharetransactions[intCtr].GetDividendAmount());
						
						// Total Franking Credits = Number of Shares * Franking Credit Per Share
						BigDecimal TotalFrankingCredits = BigDecimal.valueOf(sharetransactions[intCtr].GetNumberShares());
						TotalFrankingCredits = TotalFrankingCredits.multiply(FrankingCreditPerShare);
						
						// Taxable Income = Total Dividends + Total Franking Credits
						BigDecimal TaxableIncome = TotalDividends.add(TotalFrankingCredits);
						
						ShareDivTableModel.addRow(new Object[]{
								sharetransactions[intCtr].GetTransactionID(),
								TxnDate,
								sharetransactions[intCtr].GetNumberShares(),
								MoneyFormatter.format(sharetransactions[intCtr].GetDividendAmount()),
								sharetransactions[intCtr].GetFrankingPercentage(),
								MoneyFormatter.format(FrankingCreditPerShare.setScale(2, RoundingMode.HALF_EVEN)),
								MoneyFormatter.format(TotalDividends.setScale(2, RoundingMode.HALF_EVEN)),
								MoneyFormatter.format(TotalFrankingCredits.setScale(2, RoundingMode.HALF_EVEN)),
								MoneyFormatter.format(TaxableIncome.setScale(2, RoundingMode.HALF_EVEN)),
								sharetransactions[intCtr].GetDescription()
						});
					} else {
						// DRP
						DRPs.add(sharetransactions[intCtr]);
						
						// Franking Credit Per Share = Dividend Amount Per Share * Company Tax Rate * Franking Percentage / (1 - Company Tax Rate)
						BigDecimal FrankingCreditPerShare = sharetransactions[intCtr].GetDividendAmount();
						FrankingCreditPerShare = FrankingCreditPerShare.multiply(Main.COMPANY_TAX_RATE);
						FrankingCreditPerShare = FrankingCreditPerShare.multiply(sharetransactions[intCtr].GetFrankingPercentage());
						FrankingCreditPerShare = FrankingCreditPerShare.divide(new BigDecimal(100), 12, RoundingMode.HALF_EVEN);
						FrankingCreditPerShare = FrankingCreditPerShare.divide(BigDecimal.ONE.subtract(Main.COMPANY_TAX_RATE), 12, RoundingMode.HALF_EVEN);
						
						// Total Dividends = Number of Shares * Dividend Amount Per Share
						BigDecimal TotalDividends = BigDecimal.valueOf(sharetransactions[intCtr].GetNumberShares());
						TotalDividends = TotalDividends.multiply(sharetransactions[intCtr].GetDividendAmount());
						
						// Total Franking Credits = Number of Shares * Franking Credit Per Share
						BigDecimal TotalFrankingCredits = BigDecimal.valueOf(sharetransactions[intCtr].GetNumberShares());
						TotalFrankingCredits = TotalFrankingCredits.multiply(FrankingCreditPerShare);
						
						// New Shares = (Total Dividends - Brokerage Charges - Other Charges) / Share Price
						BigDecimal NewShares = TotalDividends.subtract(sharetransactions[intCtr].GetBrokerageCharges());
						NewShares = NewShares.subtract(sharetransactions[intCtr].GetOtherCharges());
						NewShares = NewShares.divide(sharetransactions[intCtr].GetSharePrice(), 12, RoundingMode.HALF_EVEN);
						
						ShareDRPTableModel.addRow(new Object[]{
								sharetransactions[intCtr].GetTransactionID(),
								TxnDate,
								MoneyFormatter.format(sharetransactions[intCtr].GetSharePrice()), 
								NewShares.setScale(2, RoundingMode.HALF_EVEN),
								sharetransactions[intCtr].GetNumberShares(),
								MoneyFormatter.format(sharetransactions[intCtr].GetDividendAmount()),
								sharetransactions[intCtr].GetFrankingPercentage(),
								MoneyFormatter.format(FrankingCreditPerShare.setScale(2, RoundingMode.HALF_EVEN)),
								MoneyFormatter.format(TotalDividends.setScale(2, RoundingMode.HALF_EVEN)),
								MoneyFormatter.format(TotalFrankingCredits.setScale(2, RoundingMode.HALF_EVEN)),
								MoneyFormatter.format(sharetransactions[intCtr].GetBrokerageCharges()),
								MoneyFormatter.format(sharetransactions[intCtr].GetOtherCharges()),
								sharetransactions[intCtr].GetDescription()
						});
					}
				}
				
				ShareBuyTableModel.fireTableDataChanged();
				ShareSellTableModel.fireTableDataChanged();
				ShareDivTableModel.fireTableDataChanged();
				ShareDRPTableModel.fireTableDataChanged();
			}
			
			// Select previously selected row
			// Buys
			if (OldIndex[0] >= 0) {
				if (Buys != null) {
					for (int intCtr = 0; intCtr < Buys.size(); intCtr++) {
						if (strID[0].equals(Buys.get(intCtr).GetTransactionID())) {
							NewIndex[0] = intCtr;
							break;
						}
					}
					
					if (NewIndex[0] > 0) {
						// The entity was found
						ShareBuyTable.getSelectionModel().addSelectionInterval(NewIndex[0], NewIndex[0]);
					} else {
						// The entity was not found
						ShareBuyTable.getSelectionModel().addSelectionInterval(0, 0);
					}
				}
			}
			
			// Sells
			if (OldIndex[1] >= 0) {
				if (Sells != null) {
					for (int intCtr = 0; intCtr < Sells.size(); intCtr++) {
						if (strID[1].equals(Sells.get(intCtr).GetTransactionID())) {
							NewIndex[1] = intCtr;
							break;
						}
					}
					
					if (NewIndex[1] > 0) {
						// The entity was found
						ShareSellTable.getSelectionModel().addSelectionInterval(NewIndex[1], NewIndex[1]);
					} else {
						// The entity was not found
						ShareSellTable.getSelectionModel().addSelectionInterval(0, 0);
					}
				}
			}
			
			// Dividends
			if (OldIndex[2] >= 0) {
				if (Dividends != null) {
					for (int intCtr = 0; intCtr < Dividends.size(); intCtr++) {
						if (strID[2].equals(Dividends.get(intCtr).GetTransactionID())) {
							NewIndex[2] = intCtr;
							break;
						}
					}
					
					if (NewIndex[2] > 0) {
						// The entity was found
						ShareDivTable.getSelectionModel().addSelectionInterval(NewIndex[2], NewIndex[2]);
					} else {
						// The entity was not found
						ShareDivTable.getSelectionModel().addSelectionInterval(0, 0);
					}
				}
			}
			
			// DRPs
			if (OldIndex[3] >= 0) {
				if (DRPs != null) {
					for (int intCtr = 0; intCtr < DRPs.size(); intCtr++) {
						if (strID[3].equals(DRPs.get(intCtr).GetTransactionID())) {
							NewIndex[3] = intCtr;
							break;
						}
					}
					
					if (NewIndex[3] > 0) {
						// The entity was found
						ShareDRPTable.getSelectionModel().addSelectionInterval(NewIndex[3], NewIndex[3]);
					} else {
						// The entity was not found
						ShareDRPTable.getSelectionModel().addSelectionInterval(0, 0);
					}
				}
			}
		}
	}
	
	private void AddTxn() {
		if (!NewEditTxnOpen) {
			frmNewEditTxn = new frmNewEditTxn(this.strEntity, null);
			frmNewEditTxn.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			    	NewEditTxnOpen = false;
			    	RefreshTxnTable();
			    	try {
						UpdateCurrentValue();
					} catch (SQLException e1) {
						MessageBox.Error("A database related error occured when calculating the current value of the entity.", "Current Value");
					}
			    }
			});
			frmNewEditTxn.setVisible(true);
			NewEditTxnOpen = true;
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to create a new transaction.", "New Transaction");
			frmNewEditTxn.requestFocus();
		}
	}
	
	private void EditTxn() {
		if (!NewEditTxnOpen) {
			// Get the selected service from the table
			int intRow = TxnTable.getSelectedRow();
			if (intRow >= 0) {
				frmNewEditTxn = new frmNewEditTxn(this.strEntity, TxnTableModel.getValueAt(intRow, TxnTableModel.findColumn("ID")).toString());
				frmNewEditTxn.addWindowListener(new WindowAdapter() { 
				    @Override public void windowClosed(WindowEvent e) { 
				    	NewEditTxnOpen = false;
				    	RefreshTxnTable();
				    	try {
							UpdateCurrentValue();
						} catch (SQLException e1) {
							MessageBox.Error("A database related error occured when calculating the current value of the entity.", "Current Value");
						}
				    }
				});
				frmNewEditTxn.setVisible(true);
				NewEditTxnOpen = true;
			} else {
				// No transaction was selected from the table
				MessageBox.Information("Please select a transaction from the table to edit.", "Edit Transaction");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to edit another transaction.", "Edit Transaction");
			frmNewEditTxn.requestFocus();
		}
	}
	
	private void DeleteTxn() {
		int intRow = TxnTable.getSelectedRow();	// -1 if no row selected
		if (!NewEditTxnOpen) {
			if (intRow >= 0) {
				String TxnID = TxnTableModel.getValueAt(intRow, TxnTableModel.findColumn("ID")).toString();
				int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected transaction?",
						"Delete Transaction?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				
				if (Response == 0) {
					try {
						Main.database_handler.DeleteTransaction(TxnID);
						TxnTableModel.removeRow(intRow);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error deleting the selected transaction.", "Delete Transaction");
					}
				}
			} else {
				MessageBox.Information("Please select a transaction from the table to delete.", "Delete Transaction");
			}
		} else {
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to delete a transaction.", "Delete Transaction");
			frmNewEditTxn.requestFocus();
		}
	}
	
	private void NewBuyShare() {
		if (!NewEditShrTxnOpen) {
			frmNewEditShrTxn = new frmNewEditShrTxn(null, Main.SHR_TXN_TYPES[0], this.strEntity);
			frmNewEditShrTxn.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			    	NewEditShrTxnOpen = false;
			    	RefreshShrTxnTable();
			    	try {
						UpdateShrValuation();
					} catch (SQLException e2) {
						MessageBox.Error("A database related error occured when updating the valuation metrics of the entity.", "Entity Valuation");
					}
			    }
			});
			frmNewEditShrTxn.setVisible(true);
			NewEditShrTxnOpen = true;
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to edit another transaction.", "New Share Buy Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void EditBuyShare() {
		if (!NewEditShrTxnOpen) {
			// Get the selected service from the table
			int intRow = ShareBuyTable.getSelectedRow();
			if (intRow >= 0) {
				frmNewEditShrTxn = new frmNewEditShrTxn(ShareBuyTableModel.getValueAt(intRow, ShareBuyTableModel.findColumn("ID")).toString(), Main.SHR_TXN_TYPES[0], this.strEntity);
				frmNewEditShrTxn.addWindowListener(new WindowAdapter() { 
				    @Override public void windowClosed(WindowEvent e) { 
				    	NewEditShrTxnOpen = false;
				    	RefreshShrTxnTable();
				    	try {
							UpdateShrValuation();
						} catch (SQLException e2) {
							MessageBox.Error("A database related error occured when updating the valuation metrics of the entity.", "Entity Valuation");
						}
				    }
				});
				frmNewEditShrTxn.setVisible(true);
				NewEditShrTxnOpen = true;
			} else {
				// No transaction was selected from the table
				MessageBox.Information("Please select a transaction from the table to edit.", "Edit Share Buy Transaction");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to edit another transaction.", "Edit Share Buy Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void DeleteBuyShare() {
		int intRow = ShareBuyTable.getSelectedRow();	// -1 if no row selected
		if (!NewEditShrTxnOpen) {
			if (intRow >= 0) {
				String ShrTxnID = ShareBuyTableModel.getValueAt(intRow, ShareBuyTableModel.findColumn("ID")).toString();
				int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected transaction?",
						"Delete Share Buy Transaction?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				
				if (Response == 0) {
					try {
						Main.database_handler.DeleteShareTransaction(ShrTxnID);
						ShareBuyTableModel.removeRow(intRow);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error deleting the selected 'buy' transaction.", "Delete Share Buy Transaction");
					}
				}
			} else {
				MessageBox.Information("Please select a 'buy' transaction from the table to delete.", "Delete Share Buy Transaction");
			}
		} else {
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to delete a transaction.", "Delete Share Buy Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void NewSellShare() {
		if (!NewEditShrTxnOpen) {
			frmNewEditShrTxn = new frmNewEditShrTxn(null, Main.SHR_TXN_TYPES[1], this.strEntity);
			frmNewEditShrTxn.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			    	NewEditShrTxnOpen = false;
			    	RefreshShrTxnTable();
			    	try {
						UpdateShrValuation();
					} catch (SQLException e2) {
						MessageBox.Error("A database related error occured when updating the valuation metrics of the entity.", "Entity Valuation");
					}
			    }
			});
			frmNewEditShrTxn.setVisible(true);
			NewEditShrTxnOpen = true;
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to edit another transaction.", "New Share Sell Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void EditSellShare() {
		if (!NewEditShrTxnOpen) {
			// Get the selected service from the table
			int intRow = ShareSellTable.getSelectedRow();
			if (intRow >= 0) {
				frmNewEditShrTxn = new frmNewEditShrTxn(ShareSellTableModel.getValueAt(intRow, ShareSellTableModel.findColumn("ID")).toString(), Main.SHR_TXN_TYPES[1], this.strEntity);
				frmNewEditShrTxn.addWindowListener(new WindowAdapter() { 
				    @Override public void windowClosed(WindowEvent e) { 
				    	NewEditShrTxnOpen = false;
				    	RefreshShrTxnTable();
				    	try {
							UpdateShrValuation();
						} catch (SQLException e2) {
							MessageBox.Error("A database related error occured when updating the valuation metrics of the entity.", "Entity Valuation");
						}
				    }
				});
				frmNewEditShrTxn.setVisible(true);
				NewEditShrTxnOpen = true;
			} else {
				// No transaction was selected from the table
				MessageBox.Information("Please select a transaction from the table to edit.", "Edit Share Sell Transaction");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to edit another transaction.", "Edit Share Sell Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void DeleteSellShare() {
		int intRow = ShareSellTable.getSelectedRow();	// -1 if no row selected
		if (!NewEditShrTxnOpen) {
			if (intRow >= 0) {
				String ShrTxnID = ShareSellTableModel.getValueAt(intRow, ShareSellTableModel.findColumn("ID")).toString();
				int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected transaction?",
						"Delete Share Sell Transaction?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				
				if (Response == 0) {
					try {
						Main.database_handler.DeleteShareTransaction(ShrTxnID);
						ShareSellTableModel.removeRow(intRow);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error deleting the selected 'sell' transaction.", "Delete Share Sell Transaction");
					}
				}
			} else {
				MessageBox.Information("Please select a 'sell' transaction from the table to delete.", "Delete Share Sell Transaction");
			}
		} else {
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to delete a transaction.", "Delete Share Sell Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void NewDividendShare() {
		if (!NewEditShrTxnOpen) {
			frmNewEditShrTxn = new frmNewEditShrTxn(null, Main.SHR_TXN_TYPES[2], this.strEntity);
			frmNewEditShrTxn.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			    	NewEditShrTxnOpen = false;
			    	RefreshShrTxnTable();
			    	try {
						UpdateShrValuation();
					} catch (SQLException e2) {
						MessageBox.Error("A database related error occured when updating the valuation metrics of the entity.", "Entity Valuation");
					}
			    }
			});
			frmNewEditShrTxn.setVisible(true);
			NewEditShrTxnOpen = true;
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to edit another transaction.", "New Share Dividend Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void EditDividendShare() {
		if (!NewEditShrTxnOpen) {
			// Get the selected service from the table
			int intRow = ShareDivTable.getSelectedRow();
			if (intRow >= 0) {
				frmNewEditShrTxn = new frmNewEditShrTxn(ShareDivTableModel.getValueAt(intRow, ShareDivTableModel.findColumn("ID")).toString(), Main.SHR_TXN_TYPES[2], this.strEntity);
				frmNewEditShrTxn.addWindowListener(new WindowAdapter() { 
				    @Override public void windowClosed(WindowEvent e) { 
				    	NewEditShrTxnOpen = false;
				    	RefreshShrTxnTable();
				    	try {
							UpdateShrValuation();
						} catch (SQLException e2) {
							MessageBox.Error("A database related error occured when updating the valuation metrics of the entity.", "Entity Valuation");
						}
				    }
				});
				frmNewEditShrTxn.setVisible(true);
				NewEditShrTxnOpen = true;
			} else {
				// No transaction was selected from the table
				MessageBox.Information("Please select a transaction from the table to edit.", "Edit Share Dividend Transaction");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to edit another transaction.", "Edit Share Dividend Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void DeleteDividendShare() {
		int intRow = ShareDivTable.getSelectedRow();	// -1 if no row selected
		if (!NewEditShrTxnOpen) {
			if (intRow >= 0) {
				String ShrTxnID = ShareDivTableModel.getValueAt(intRow, ShareDivTableModel.findColumn("ID")).toString();
				int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected transaction?",
						"Delete Share Dividend Transaction?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				
				if (Response == 0) {
					try {
						Main.database_handler.DeleteShareTransaction(ShrTxnID);
						ShareDivTableModel.removeRow(intRow);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error deleting the selected 'dividend' transaction.", "Delete Share Dividend Transaction");
					}
				}
			} else {
				MessageBox.Information("Please select a 'dividend' transaction from the table to delete.", "Delete Share Dividend Transaction");
			}
		} else {
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to delete a transaction.", "Delete Share Dividend Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void NewDRPShare() {
		if (!NewEditShrTxnOpen) {
			frmNewEditShrTxn = new frmNewEditShrTxn(null, Main.SHR_TXN_TYPES[3], this.strEntity);
			frmNewEditShrTxn.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			    	NewEditShrTxnOpen = false;
			    	RefreshShrTxnTable();
			    	try {
						UpdateShrValuation();
					} catch (SQLException e2) {
						MessageBox.Error("A database related error occured when updating the valuation metrics of the entity.", "Entity Valuation");
					}
			    }
			});
			frmNewEditShrTxn.setVisible(true);
			NewEditShrTxnOpen = true;
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to edit another transaction.", "New Share DRP Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void EditDRPShare() {
		if (!NewEditShrTxnOpen) {
			// Get the selected service from the table
			int intRow = ShareDRPTable.getSelectedRow();
			if (intRow >= 0) {
				frmNewEditShrTxn = new frmNewEditShrTxn(ShareDRPTableModel.getValueAt(intRow, ShareDRPTableModel.findColumn("ID")).toString(), Main.SHR_TXN_TYPES[3], this.strEntity);
				frmNewEditShrTxn.addWindowListener(new WindowAdapter() { 
				    @Override public void windowClosed(WindowEvent e) { 
				    	NewEditShrTxnOpen = false;
				    	RefreshShrTxnTable();
				    	try {
							UpdateShrValuation();
						} catch (SQLException e2) {
							MessageBox.Error("A database related error occured when updating the valuation metrics of the entity.", "Entity Valuation");
						}
				    }
				});
				frmNewEditShrTxn.setVisible(true);
				NewEditShrTxnOpen = true;
			} else {
				// No transaction was selected from the table
				MessageBox.Information("Please select a transaction from the table to edit.", "Edit Share DRP Transaction");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to edit another transaction.", "Edit Share DRP Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void DeleteDRPShare() {
		int intRow = ShareDRPTable.getSelectedRow();	// -1 if no row selected
		if (!NewEditShrTxnOpen) {
			if (intRow >= 0) {
				String ShrTxnID = ShareDRPTableModel.getValueAt(intRow, ShareDRPTableModel.findColumn("ID")).toString();
				int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected transaction?",
						"Delete Share DRP Transaction?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				
				if (Response == 0) {
					try {
						Main.database_handler.DeleteShareTransaction(ShrTxnID);
						ShareDRPTableModel.removeRow(intRow);
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error deleting the selected 'DRP' transaction.", "Delete Share DRP Transaction");
					}
				}
			} else {
				MessageBox.Information("Please select a 'DRP' transaction from the table to delete.", "Delete Share DRP Transaction");
			}
		} else {
			MessageBox.Warning("Please finish modifying the transaction that is currently open before attempting to delete a transaction.", "Delete Share DRP Transaction");
			frmNewEditShrTxn.requestFocus();
		}
	}
	
	private void UpdateCurrentValue() throws SQLException {
		DecimalFormat MoneyFormatter = new DecimalFormat("$#,##0.00;$-#,##0.00");
		BigDecimal Debits = Main.database_handler.GetDebits(strEntity);
		BigDecimal Credits = Main.database_handler.GetCredits(strEntity);
		lblCurrentValue.setText("Current Value: " + MoneyFormatter.format(Debits.subtract(Credits)));
	}
	
	private void UpdateShrValuation() throws SQLException {
		DecimalFormat MoneyFormatter = new DecimalFormat("$#,##0.00;$-#,##0.00");
		BigDecimal Accumulator = BigDecimal.ZERO;
		float SharesHeld = (float) 0;
		
		if (Buys != null) {
			for (int intCtr = 0; intCtr < Buys.size(); intCtr++) {
				// Net Payment = (Share Number * Share Price) + Brokerage Charges + Government Charges
				BigDecimal NetPayment = BigDecimal.valueOf(Buys.get(intCtr).GetNumberShares());
				NetPayment = NetPayment.multiply(Buys.get(intCtr).GetSharePrice());
				NetPayment = NetPayment.add(Buys.get(intCtr).GetBrokerageCharges());
				NetPayment = NetPayment.add(Buys.get(intCtr).GetOtherCharges());
				NetPayment = NetPayment.setScale(2, RoundingMode.HALF_EVEN);
				Accumulator = Accumulator.add(NetPayment);
				
				// Update Shares Held
				SharesHeld = SharesHeld + Buys.get(intCtr).GetNumberShares();
			}
			
			if (Buys.size() > 0) {
				lblTotalSpent.setText("Σ Amount Spent ($): " + MoneyFormatter.format(Accumulator.setScale(2, RoundingMode.HALF_EVEN)));
			} else {
				lblTotalSpent.setText("Σ Amount Spent ($): $0.00");
			}
			
		} else {
			lblTotalSpent.setText("Σ Amount Spent ($): $0.00");
		}
		
		Accumulator = BigDecimal.ZERO;
		if (Sells != null) {
			for (int intCtr = 0; intCtr < Sells.size(); intCtr++) {
				// Net Income = (Share Number * Share Price) - Brokerage Charges - Government Charges - Capital Gains Tax
				BigDecimal NetIncome = BigDecimal.valueOf(Sells.get(intCtr).GetNumberShares());
				NetIncome = NetIncome.multiply(Sells.get(intCtr).GetSharePrice());
				NetIncome = NetIncome.subtract(Sells.get(intCtr).GetBrokerageCharges());
				NetIncome = NetIncome.subtract(Sells.get(intCtr).GetOtherCharges());
				NetIncome = NetIncome.subtract(Sells.get(intCtr).GetCapitalGainsTax());
				Accumulator = Accumulator.add(NetIncome);
				
				// Update Shares Held
				SharesHeld = SharesHeld - Sells.get(intCtr).GetNumberShares();
			}
			
			if (Buys.size() > 0) {
				lblTotalEarnt.setText("Σ Amount Earnt ($): " + MoneyFormatter.format(Accumulator.setScale(2, RoundingMode.HALF_EVEN)));
			} else {
				lblTotalEarnt.setText("Σ Amount Earnt ($): $0.00");
			}
			
		} else {
			lblTotalEarnt.setText("Σ Amount Earnt ($): $0.00");
		}
		
		// Calculate total taxable income earnt via dividends
		Accumulator = BigDecimal.ZERO;
		if (Dividends != null) {
			for (int intCtr = 0; intCtr < Dividends.size(); intCtr++) {
				// Franking Credit Per Share = Dividend Amount Per Share * Company Tax Rate * Franking Percentage / (1 - Company Tax Rate)
				BigDecimal FrankingCreditPerShare = Dividends.get(intCtr).GetDividendAmount();
				FrankingCreditPerShare = FrankingCreditPerShare.multiply(Main.COMPANY_TAX_RATE);
				FrankingCreditPerShare = FrankingCreditPerShare.multiply(Dividends.get(intCtr).GetFrankingPercentage());
				FrankingCreditPerShare = FrankingCreditPerShare.divide(new BigDecimal(100), 12, RoundingMode.HALF_EVEN);
				FrankingCreditPerShare = FrankingCreditPerShare.divide(BigDecimal.ONE.subtract(Main.COMPANY_TAX_RATE), 12, RoundingMode.HALF_EVEN);
				
				// Total Dividends = Number of Shares * Dividend Amount Per Share
				BigDecimal TotalDividends = BigDecimal.valueOf(Dividends.get(intCtr).GetNumberShares());
				TotalDividends = TotalDividends.multiply(Dividends.get(intCtr).GetDividendAmount());
				
				// Total Franking Credits = Number of Shares * Franking Credit Per Share
				BigDecimal TotalFrankingCredits = BigDecimal.valueOf(Dividends.get(intCtr).GetNumberShares());
				TotalFrankingCredits = TotalFrankingCredits.multiply(FrankingCreditPerShare);
				
				// Taxable Income = Total Dividends + Total Franking Credits
				BigDecimal TaxableIncome = TotalDividends.add(TotalFrankingCredits);
				
				Accumulator = Accumulator.add(TaxableIncome);
			}
			
			if (Buys.size() > 0) {
				lblTotalTaxableIncome.setText("Σ Taxable Income ($): " + MoneyFormatter.format(Accumulator.setScale(2, RoundingMode.HALF_EVEN)));
			} else {
				lblTotalTaxableIncome.setText("Σ Taxable Income ($): $0.00");
			}
			
		} else {
			lblTotalTaxableIncome.setText("Σ Taxable Income ($): $0.00");
		}
		
		Accumulator = BigDecimal.ZERO;
		if (DRPs != null) {
			for (int intCtr = 0; intCtr < DRPs.size(); intCtr++) {
				// Total Dividends = Number of Shares * Dividend Amount Per Share
				BigDecimal TotalDividends = BigDecimal.valueOf(DRPs.get(intCtr).GetNumberShares());
				TotalDividends = TotalDividends.multiply(DRPs.get(intCtr).GetDividendAmount());
				Accumulator = Accumulator.add(TotalDividends);
				
				// New Shares = (Total Dividends - Brokerage Charges - Other Charges)/Share Price
				BigDecimal NewShares = TotalDividends.subtract(DRPs.get(intCtr).GetBrokerageCharges());
				NewShares = NewShares.subtract(DRPs.get(intCtr).GetOtherCharges());
				NewShares = NewShares.divide(DRPs.get(intCtr).GetSharePrice(), 12, RoundingMode.HALF_EVEN);
				SharesHeld = SharesHeld + NewShares.floatValue();
			}
			
			if (Buys.size() > 0) {
				lblTotalReinvested.setText("Σ Money Reinvested ($): " + MoneyFormatter.format(Accumulator.setScale(2, RoundingMode.HALF_EVEN)));
			} else {
				lblTotalReinvested.setText("Σ Money Reinvested ($): $0.00");
			}
			
		} else {
			lblTotalReinvested.setText("Σ Money Reinvested ($): $0.00");
		}
		
		// Update Main Valuation Metrics
		BigDecimal SharePrice = Main.database_handler.GetShareFromEntityID(strEntity).GetCurrentUnitValue();
		BigDecimal TotalValue = SharePrice.multiply(new BigDecimal(SharesHeld)).setScale(2, RoundingMode.HALF_EVEN);
		txtTotalValue.setText(MoneyFormatter.format(TotalValue.setScale(2, RoundingMode.HALF_EVEN)));
		txtTotalValue.setCaretPosition(0);
		txtTotalSharesHeld.setText(String.format("%.02f", SharesHeld));
		txtTotalSharesHeld.setCaretPosition(0);
	}
	
	private void UpdateServicesTotal(int intTotal) {
		lblTotalServices.setText("Total Number of Services Found: " + Integer.toString(intTotal));
	}
	
	private void RefreshServicesTable() {
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		try {
			// Get the selected row index
			int OldIndex = tblRelServ.getSelectedRow();
			int NewIndex = -1;
			String strID = "";
			
			// Store the service ID corresponding to the currently selected row
			if (OldIndex >= 0) {
				strID = tblRelServModel.getValueAt(OldIndex, tblRelServModel.findColumn("ID")).toString();
			} 
			
			// Clear the results table
			tblRelServModel.setRowCount(0);
			
			// Get new search results
			Service[] Services = Main.database_handler.GetServicesFromEntityID(strEntity);
			
			if (Services != null) {
				// Sort the search results
				Arrays.sort(Services);
				
				// Populate the table
				for (int intCtr = 0; intCtr < Services.length; intCtr++) {
					String InstName = Main.database_handler.GetEntityFromID(Services[intCtr].GetEntityID()).GetInstitutionName();
					String strDate;
					if (Services[intCtr].GetExpiry() == null) {
						strDate = "N/A";
					} else {
						strDate = dateFormat.format(Services[intCtr].GetExpiry());
					}
					
					tblRelServModel.addRow(new Object[]{
							Services[intCtr].GetServiceType(),
							Services[intCtr].GetDescription(),
							InstName,
							Services[intCtr].GetFrequency(),
							Services[intCtr].GetUserID(),
							Services[intCtr].GetPassword(),
							Services[intCtr].GetContact(),
							strDate,
							Services[intCtr].GetServiceID()});
				}
				
				// Update table count
				UpdateServicesTotal(Services.length);
				
				// If a row was previously selected
				if (OldIndex >= 0) {
					for (int intCtr = 0; intCtr < Services.length; intCtr++) {
						if (strID.equals(Services[intCtr].GetServiceID())) {
							NewIndex = intCtr;
							break;
						}
					}
					
					if (NewIndex > 0) {
						// The entity was found
						tblRelServ.getSelectionModel().addSelectionInterval(NewIndex, NewIndex);
					} else {
						// The entity was not found
						tblRelServ.getSelectionModel().addSelectionInterval(0, 0);
					}
				}
			} else {
				UpdateServicesTotal(0);
			}
		} catch (SQLException e) {
			MessageBox.Error("An unknown error occurred trying to populate the related services table.", "Related Services");
		}
	}
	
	private void NewService() {
		if (!NewEditServiceOpen) {
			// Passing null indicates we are creating a new service
			frmNewEditService = new frmNewEditService(this.strEntity, null);
			frmNewEditService.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			    	NewEditServiceOpen = false;
			    	RefreshServicesTable();
			    }
			});
			frmNewEditService.setVisible(true);
			NewEditServiceOpen = true;
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the service that is currently open before attempting to create another service.", "New Service");
			frmNewEditService.requestFocus();
		}
	}
	
	private void EditService() {
		if (!NewEditServiceOpen) {
			// Get the selected service from the table
			int intRow = tblRelServ.getSelectedRow();
			if (intRow >= 0) {
				// Passing the selected service indicates we are editing an existing service
				try {
					Service service = Main.database_handler.GetService(tblRelServModel.getValueAt(intRow, tblRelServModel.findColumn("ID")).toString());
					frmNewEditService = new frmNewEditService(this.strEntity, service);
					frmNewEditService.addWindowListener(new WindowAdapter() { 
					    @Override public void windowClosed(WindowEvent e) { 
					    	NewEditServiceOpen = false;
					    	RefreshServicesTable();
					    }
					});
					frmNewEditService.setVisible(true);
					NewEditServiceOpen = true;
				} catch (SQLException e1) {
					MessageBox.Error("There was a database related error opening the service for editing.", "Edit Service");
					e1.printStackTrace();
				}
			} else {
				// No service was selected from the table
				MessageBox.Information("Please select a service from the table to edit.", "Edit Service");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the service that is currently open before attempting to edit another service.", "Edit Service");
			frmNewEditService.requestFocus();
		}
	}
	
	private void DeleteService() {
		if (!NewEditServiceOpen) {
			// Get the selected service from the table
			int intRow = tblRelServ.getSelectedRow();
			if (intRow >= 0) {
				int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected service?",
						"Delete Service?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				
				if (Response == 0) {
					try {
						Main.database_handler.DeleteService(tblRelServModel.getValueAt(intRow, tblRelServModel.findColumn("ID")).toString());
						tblRelServ.getSelectionModel().clearSelection();
						RefreshServicesTable();
					} catch (SQLException e) {
						e.printStackTrace();
						MessageBox.Error("There was an error deleting the selected service.", "Delete Service");
					}
				}
			} else {
				// No institution was selected from the table
				MessageBox.Information("Please select a service from the table to delete.", "Delete Service");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the service that is currently open before attempting to delete a service.", "Delete Service");
			frmNewEditService.requestFocus();
		}
	}
	
	private void OpenLinkedEntities() {
		if (!LinkedEntitiesOpen) {
			frmLinkedEntities = new frmLinkedEntities(this.strEntity);
			frmLinkedEntities.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			    	LinkedEntitiesOpen = false;
			    	
			    	// Update Relationship Tree Graph
			    	pnlRelTree.remove(viewPanel);
					CreateRelationshipTree();
					viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
					viewer.enableAutoLayout();
					viewPanel = viewer.addDefaultView(false);
					viewPanel.setSize(596, 357);
					pnlRelTree.add(viewPanel);
			    }
			});
			frmLinkedEntities.setVisible(true);
			LinkedEntitiesOpen = true;
		} else {
			// The window is already open
			frmLinkedEntities.requestFocus();
		}
	}
	
	private void Save() {
		Boolean boolSave = true;
		
		// Check required fields are completed
		if (txtNumber.getText().trim().equals("") || OpenDatePicker.getDate() == null) {
			MessageBox.Warning("Please ensure all fields prefixed with an asterisk (*) are completed.", "Save Entity");
			boolSave = false;
		}
		
		// Check length of entity number does not exceed VARCHAR_MAX_LENGTH
		if (txtNumber.getText().trim().length() > Main.VARCHAR_MAX_LENGTH) {
			MessageBox.Warning("Please ensure the entity number does not exceed " + Integer.toString(Main.VARCHAR_MAX_LENGTH) + " characters in length.", "Save Entity");
			boolSave = false;
		}
		
		// Check length of entity description does not exceed VARCHAR_MAX_LENGTH
		if (txtDesc.getText().trim().length() > Main.VARCHAR_MAX_LENGTH) {
			MessageBox.Warning("Please ensure the entity description does not exceed " + Integer.toString(Main.VARCHAR_MAX_LENGTH) + " characters in length.", "Save Entity");
			boolSave = false;
		}
		
		// Check length of entity comment does not exceed VARCHAR_MAX_LENGTH
		if (txtComm.getText().trim().length() > Main.VARCHAR_MAX_LENGTH) {
			MessageBox.Warning("Please ensure the entity comment does not exceed " + Integer.toString(Main.VARCHAR_MAX_LENGTH) + " characters in length.", "Save Entity");
			boolSave = false;
		}
		
		// Check closing date does not occur before opening date
		Date OpenDate = java.sql.Date.valueOf(OpenDatePicker.getDate());
		Date CloseDate;
		if (CloseDatePicker.getDate() != null) {
			CloseDate = java.sql.Date.valueOf(CloseDatePicker.getDate());
			if (CloseDate.before(OpenDate)) {
				MessageBox.Warning("Please ensure the closing date does not occur before the opening date.", "Save Entity");
				boolSave = false;
			}
		} else {
			CloseDate = null;
		}
		
		
		if (boolSave) {
			String EntityType;
			try {
				EntityType = Main.database_handler.GetEntityFromID(strEntity).GetEntityTypeName();
			} catch (SQLException e1) {
				EntityType = null;
			}
			
			if (EntityType != null) {
				// Holders
				String[] Holders = new String[HolderListModel.size()];
				for (int intCtr = 0; intCtr < HolderListModel.size(); intCtr++) {
					Holders[intCtr] = HolderListModel.get(intCtr);
				}
				
				// Entity Number
				String Number = txtNumber.getText().trim();
				txtNumber.setCaretPosition(0);
				
				// Institution
				String Institution = String.valueOf(combInstitution.getSelectedItem());
				
				// Status
				String Status = String.valueOf(combAccStat.getSelectedItem());
				
				// Description
				String Description;
				if (!txtDesc.getText().trim().equals("")) {
					Description = txtDesc.getText().trim();
				} else {
					Description = null;
				}
				
				// Comments
				String Comments;
				if (!txtComm.getText().trim().equals("")) {
					Comments = txtComm.getText().trim();
				} else {
					Comments = null;
				}
				
				Entity edited_entity = new Entity(
						Holders,
						strEntity,
						EntityType,
						Institution,
						Status,
						Number,
						OpenDate,
						CloseDate,
						Description,
						Comments);
				
				// Do not check Savings Accounts and Superannuation Funds as these do not contain additional fields
				if (EntityType.equals(Main.ENTITY_TYPES[1])) {	// Credit Card
					// Credit Card Limit
					BigDecimal CardLimit = new BigDecimal(spinCreditCardLimit.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					CreditCard edited_creditcard = new CreditCard(strEntity, CardLimit);
					try {
						Main.database_handler.EditCreditCard(edited_creditcard);
					} catch (SQLException e) {
						boolSave = false;
						MessageBox.Error("There was a database related error saving changes to the credit card specific fields.", "Save Entity");
					}
				} else if (EntityType.equals(Main.ENTITY_TYPES[2])) {	// Loan Account
					// Loan Amount
					BigDecimal LoanAmount = new BigDecimal(spinLoanAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					// Loan Amount
					BigDecimal CurrentValue = new BigDecimal(spinLoanCurrentValue.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					Loan edited_loan = new Loan(strEntity, LoanAmount, CurrentValue);
					try {
						Main.database_handler.EditLoan(edited_loan);
					} catch (SQLException e) {
						boolSave = false;
						MessageBox.Error("There was a database related error saving changes to the loan specific fields.", "Save Entity");
					}
				} else if (EntityType.equals(Main.ENTITY_TYPES[3])) {	// Term Deposit
					// Opening Balance
					BigDecimal OpeningBalance = new BigDecimal(spinTermDepositOpeningBalance.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					// Interest Amount
					BigDecimal InterestAmount = new BigDecimal(spinTermDepositInterestAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					// Government Charges
					BigDecimal GovtCharges = new BigDecimal(spinTermDepositOtherCharges.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					// Bank Fees
					BigDecimal BankFees = new BigDecimal(spinTermDepositBankFees.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					// Interest Rate
					BigDecimal InterestRate = new BigDecimal(spinTermDepositInterestRate.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					TermDeposit edited_termdeposit = new TermDeposit(
							strEntity,
							OpeningBalance,
							InterestAmount,
							GovtCharges,
							BankFees,
							InterestRate);
					
					try {
						Main.database_handler.EditTermDeposit(edited_termdeposit);
					} catch (SQLException e) {
						boolSave = false;
						MessageBox.Error("There was a database related error saving changes to the term deposit specific fields.", "Save Entity");
					}
				} else if (EntityType.equals(Main.ENTITY_TYPES[5])) {	// Property
					// Check an address is provided
					if (txtAddress.getText().trim().equals("")) {
						boolSave = false;
						MessageBox.Warning("An address for the property must be provided.", "Save Entity");
					}
					
					// Check the address length does not exceed VARCHAR_MAX_LENGTH
					if (txtAddress.getText().trim().length() > Main.VARCHAR_MAX_LENGTH) {
						boolSave = false;
						MessageBox.Warning("The property address must not exceed " + Integer.toString(Main.VARCHAR_MAX_LENGTH) + " characters in length.", "Save Entity");
					}
					
					if (boolSave) {
						// Property Address
						String Address = txtAddress.getText().trim();
						
						// Purchase Amount
						BigDecimal PurchaseAmount = new BigDecimal(spinPropertyPurchaseAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						// Sold Amount
						BigDecimal SoldAmount = new BigDecimal(spinPropertySoldAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						// Current Value
						BigDecimal CurrentValue = new BigDecimal(spinPropertyCurrentValue.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						// Solicitor Fees (Buy)
						BigDecimal SolicitorFeesBuy = new BigDecimal(spinPropertySolFeeBuy.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						// Solicitor Fees (Sell)
						BigDecimal SolicitorFeesSell = new BigDecimal(spinPropertySolFeeSell.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						// Government Charges (Buy)
						BigDecimal GovernmentChargesBuy = new BigDecimal(spinPropertyGovtChargesBuy.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						// Government Charges (Sell)
						BigDecimal GovernmentChargesSell = new BigDecimal(spinPropertyGovtChargesSell.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						// Agent Fees (Buy)
						BigDecimal AgentFeesBuy = new BigDecimal(spinPropertyAgtFeeBuy.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						// Agent Fees (Sell)
						BigDecimal AgentFeesSell = new BigDecimal(spinPropertyAgtFeeSell.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						// Capital Gains Tax
						BigDecimal CapGainsTax = new BigDecimal(spinPropertyCapGainsTax.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
						
						Property edited_property = new Property(
								strEntity,
								Address,
								PurchaseAmount,
								SolicitorFeesBuy,
								SolicitorFeesSell,
								GovernmentChargesBuy,
								GovernmentChargesSell,
								AgentFeesBuy,
								AgentFeesSell,
								SoldAmount,
								CapGainsTax,
								CurrentValue
								);
						
						try {
							Main.database_handler.EditProperty(edited_property);
						} catch (SQLException e) {
							boolSave = false;
							e.printStackTrace();
							MessageBox.Error("There was a database related error saving changes to the property specific fields.", "Save Entity");
						}
					}
				} else if (EntityType.equals(Main.ENTITY_TYPES[6])) {	// Shares/Managed Funds
					// Current Share Price
					BigDecimal CurrentUnitValue = new BigDecimal(spinCurrentUnitShareValue.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
					
					Share edited_share = new Share(strEntity, CurrentUnitValue);
					try {
						Main.database_handler.EditShare(edited_share);
					} catch (SQLException e) {
						boolSave = false;
						MessageBox.Error("There was a database related error saving changes to the share/managed fund specific fields.", "Save Entity");
					}
				} else if (EntityType.equals(Main.ENTITY_TYPES[7])) {	// Insurance
					// Insured Amount
					BigDecimal InsuredAmount = new BigDecimal(spinInsuredAmount.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
				
					// Insurance Premium Per Annum
					BigDecimal InsurancePremiumPA = new BigDecimal(spinInsurancePremium.getValue().toString()).setScale(2, RoundingMode.HALF_EVEN);
				
					Insurance edited_insurance = new Insurance(strEntity, InsuredAmount, InsurancePremiumPA);
					try {
						Main.database_handler.EditInsurance(edited_insurance);
					} catch (SQLException e) {
						boolSave = false;
						MessageBox.Error("There was a database related error saving changes to the insurance specific fields.", "Save Entity");
					}
				}
				
				if (boolSave) {
					try {
						Main.database_handler.EditEntity(edited_entity);
						Close();
					} catch (SQLException e) {
						MessageBox.Error("There was a database related error saving the changes to this entity.", "Save Entity");
					}
				}
			} else {
				MessageBox.Error("There was a database related error determing the type of the entity.", "Save Entity");
			}
		}
	}
	
	private void DeleteEntity() {
		int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this entity?" + System.lineSeparator() + "This will also delete all transaction history, relationships and related services!", "Delete Entity?",
			      JOptionPane.YES_NO_OPTION,
			      JOptionPane.WARNING_MESSAGE);
		
		if (Response == 0) {
			try {
				// Delete Services
				Service[] Services = Main.database_handler.GetServicesFromEntityID(strEntity);
				if (Services != null) {
					for (int intCtr = 0; intCtr < Services.length; intCtr++) {
						Main.database_handler.DeleteService(Services[intCtr].GetServiceID());
					}
				}
				
				// Delete Entity History
				Main.database_handler.DeleteEntityHistory(strEntity);
				
				// Delete Transactions
				Main.database_handler.DeleteAllTransactions(strEntity);
				
				// Delete Entity Holders
				Main.database_handler.DeleteEntityHolders(strEntity);
				
				// Delete Entity Type
				Main.database_handler.DeleteEntityType(strEntity);
				
				// Delete Entity
				Main.database_handler.DeleteEntity(strEntity);
				
			} catch (SQLException e) {
				MessageBox.Error("There was an error deleting this entity. The records for this entity may now be corrupt." + System.lineSeparator() + "Please manually inspect and fix the database.", "Delete Entity");
				e.printStackTrace();
			}
			
			Close();
		}
	}
	
	// Called to close the JFrame
	private void Close() {
		// Remove the entity from the list of opened entities
		Main.frmMain.OpenedEntities.remove(this.strEntity);
		
		// Dispose
		this.dispose();
	}
	
	public frmEntity(String strEntity) {
		// Save the entity ID
		this.strEntity = strEntity;
		
		try {
			// Grab the entity
			Entity entity = Main.database_handler.GetEntityFromID(strEntity);
			
			// Create the title
			setTitle(strEntity + " - " + entity.GetEntityTypeName() + " [" + entity.GetEntityNumber() + "]");
			
			// Set the icon
			setIconImage(Toolkit.getDefaultToolkit().getImage(frmEntity.class.getResource("/resources/graphics/appicon.png")));
			
			// Restrict resizing
			setResizable(false);
			
			// Dispose this when closed
			addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			    	Close();
			    }
			});
			
			// Create the main JPanel
			JPanel contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(null);
			contentPane.setPreferredSize(new Dimension(574, 432));
			setContentPane(contentPane);
			
			// Pack and center the JFrame
			pack();
			setLocationRelativeTo(null);
			
			// Create and place the tabbed pane
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(0, 0, 574, 432);
			contentPane.add(tabbedPane);
			
			// Details Panel
			JPanel pnlDetails = new JPanel();
			pnlDetails.setLayout(null);
			tabbedPane.addTab("Details", null, pnlDetails, "View entity details.");
			
			// Holders Panel
			Border holder_border = BorderFactory.createTitledBorder("Holders:");
			JPanel holder_panel = new JPanel();
			holder_panel.setBorder(holder_border);
			holder_panel.setBounds(6, 6, 563, 145);
			pnlDetails.add(holder_panel);
			holder_panel.setLayout(null);
			
			// Get Holders
			RefreshHolders();
			
			// Entity Holders
			HolderList = new JList<String>(HolderListModel);
			HolderList.setBackground(new Color(230, 255, 236));
			HolderList.setForeground(Color.BLACK);
			HolderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane HolderListPanel = new JScrollPane(HolderList);
			HolderListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			HolderListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			HolderListPanel.setBounds(16, 24, 200, 104);
			holder_panel.add(HolderListPanel);
			
			// Remaining/Other Holders
			OtherHolderList = new JList<String>(OtherHolderListModel);
			OtherHolderList.setBackground(new Color(255, 230, 231));
			OtherHolderList.setForeground(Color.BLACK);
			OtherHolderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane OtherHolderListPanel = new JScrollPane(OtherHolderList);
			OtherHolderListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			OtherHolderListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			OtherHolderListPanel.setBounds(347, 24, 200, 104);
			holder_panel.add(OtherHolderListPanel);
			
			// Add Holder Button
			JButton btnAdd = new JButton("<html> <center>" + "Add" + "<br>" + "&#x2190;" + "</center> </html>");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AddHolder();
				}
			});
			btnAdd.setBounds(224, 24, 115, 40);
			holder_panel.add(btnAdd);
			
			// Remove Holder Button
			JButton btnRemove = new JButton("<html> <center>" + "Remove" + "<br>" + "&#x2192;" + "</center> </html>");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RemoveHolder();
				}
			});
			btnRemove.setBounds(224, 88, 115, 40);
			holder_panel.add(btnRemove);
			
			// Properties Panel
			JPanel properties_panel = new JPanel();
			properties_panel.setLayout(null);
			
			// Size depends on entity type
			if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[0])) {
				// Savings Account
				properties_panel.setPreferredSize(new Dimension (563, 166));
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[1])) {
				// Credit Card
				properties_panel.setPreferredSize(new Dimension (563, 220));
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[2])) {
				// Loan Account
				properties_panel.setPreferredSize(new Dimension (563, 220));
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[3])) {
				// Term Deposit
				properties_panel.setPreferredSize(new Dimension (563, 290));
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[4])) {
				// Superannuation
				properties_panel.setPreferredSize(new Dimension (563, 166));
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[5])) {
				// Property
				properties_panel.setPreferredSize(new Dimension (563, 460));
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[6])) {
				// Shares/Managed Funds
				properties_panel.setPreferredSize(new Dimension (563, 220));
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[7])) {
				// Insurance
				properties_panel.setPreferredSize(new Dimension (563, 220));
			}
			
			// Assign the properties panel to a scrollbar
			Border properties_border = BorderFactory.createTitledBorder("Properties:");
			JScrollPane scroller = new JScrollPane(properties_panel);
			scroller.setBorder(properties_border);
			scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scroller.setBounds(6, 154, 563, 210);
			pnlDetails.add(scroller, BorderLayout.CENTER);
			
			// Entity Number Label
			JLabel lblNumber = new JLabel("*Number:");
			lblNumber.setBounds(0, 0, 61, 26);
			properties_panel.add(lblNumber);
			
			// Institution Label
			JLabel lblInstitution = new JLabel("*Institution:");
			lblInstitution.setBounds(0, 31, 130, 30);
			properties_panel.add(lblInstitution);
			
			// Entity Status Label
			JLabel lblAccStat = new JLabel("*Status:");
			lblAccStat.setBounds(0, 66, 130, 30);
			properties_panel.add(lblAccStat);
			
			// Entity Opening Date Label
			JLabel lblODate = new JLabel("*Opening Date:");
			lblODate.setBounds(0, 101, 130, 30);
			properties_panel.add(lblODate);
			
			// Entity Closing Date Label
			JLabel lblCDate = new JLabel("Closing Date:");
			lblCDate.setBounds(0, 136, 130, 26);
			properties_panel.add(lblCDate);
			
			// Entity Description Label
			JLabel lblDesc = new JLabel("Description:");
			lblDesc.setBounds(282, 31, 115, 26);
			properties_panel.add(lblDesc);
			
			// Entity Comments Label
			JLabel lblComm = new JLabel("Comments:");
			lblComm.setBounds(282, 101, 115, 26);
			properties_panel.add(lblComm);
			
			// Entity Number Textfield
			txtNumber = new JTextField();
			txtNumber.setBounds(61, 0, 450, 26);
			properties_panel.add(txtNumber);
			txtNumber.setColumns(10);
			
			// Institution Combobox
			combInstitution = new JComboBox<String>();
			combInstitution.setBounds(129, 31, 140, 30);
			properties_panel.add(combInstitution);
			
			// Entity Status Combobox
			combAccStat = new JComboBox<String>();
			combAccStat.setBounds(129, 66, 140, 30);
			properties_panel.add(combAccStat);
			
			// Open Date Picker
			DatePickerSettings OpenDateSettings = new DatePickerSettings();
			OpenDateSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
			OpenDateSettings.setFormatForDatesBeforeCommonEra("dd/MM/uuuu");
			OpenDatePicker = new DatePicker(OpenDateSettings);
			OpenDatePicker.getComponentToggleCalendarButton().setToolTipText("Change Date");
			OpenDatePicker.getComponentDateTextField().setHorizontalAlignment(SwingConstants.RIGHT);
			OpenDatePicker.setBounds(131, 101, 138, 30);
			properties_panel.add(OpenDatePicker);
			
			// Close Date Picker
			DatePickerSettings CloseDateSettings = new DatePickerSettings();
			CloseDateSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
			CloseDateSettings.setFormatForDatesBeforeCommonEra("dd/MM/uuuu");
			CloseDatePicker = new DatePicker(CloseDateSettings);
			CloseDatePicker.getComponentToggleCalendarButton().setToolTipText("Change Date");
			CloseDatePicker.getComponentDateTextField().setHorizontalAlignment(SwingConstants.RIGHT);
			CloseDatePicker.setBounds(131, 136, 138, 30);
			properties_panel.add(CloseDatePicker);
			
			// Entity Description Textarea
			txtDesc = new JTextArea();
			txtDesc.getDocument().putProperty("filterNewlines", Boolean.TRUE);
			txtDesc.setLineWrap(true);
			txtDesc.setWrapStyleWord(true);
			txtDesc.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);	// Disable inserting tabs
			txtDesc.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);	// Disable inserting tabs
			JScrollPane scrollDesc = new JScrollPane (txtDesc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollDesc.setBorder(new LineBorder(Color.BLACK, 1));
			scrollDesc.setBounds(282, 56, 225, 40);
			properties_panel.add(scrollDesc);
			
			// Entity Comments Textarea
			txtComm = new JTextArea();
			txtComm.getDocument().putProperty("filterNewlines", Boolean.TRUE);
			txtComm.setLineWrap(true);
			txtComm.setWrapStyleWord(true);
			txtComm.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);	// Disable inserting tabs
			txtComm.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);	// Disable inserting tabs
			JScrollPane scrollComm = new JScrollPane (txtComm, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollComm.setBorder(new LineBorder(Color.BLACK, 1));
			scrollComm.setBounds(282, 126, 225, 40);
			properties_panel.add(scrollComm);
			
			// Separator
			JSeparator separator = new JSeparator();
			
			// BUTTONS
			// Save
			JButton btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Save();
				}
			});
			btnSave.setBounds(366, 367, 100, 29);
			pnlDetails.add(btnSave);
			
			// Cancel
			JButton btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Close();
				}
			});
			btnCancel.setBounds(468, 367, 100, 29);
			pnlDetails.add(btnCancel);
			
			// Delete
			JButton btnDel = new JButton("Delete");
			btnDel.setBounds(6, 367, 100, 29);
			btnDel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DeleteEntity();
				}
			});
			pnlDetails.add(btnDel);
			
			// CREDIT CARD
			// Limit Label
			JLabel lblLimit = new JLabel("*Limit ($):");
					
			// Limit Spinner
			SpinnerNumberModel LimitSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
			spinCreditCardLimit = new JSpinner(LimitSpinModel);
			JSpinner.NumberEditor LimitSpinEditor = (JSpinner.NumberEditor)spinCreditCardLimit.getEditor();
			DecimalFormat LimitSpinFormat = LimitSpinEditor.getFormat();
			LimitSpinFormat.setMinimumFractionDigits(2);
			LimitSpinFormat.setMaximumFractionDigits(2);
			LimitSpinFormat.setGroupingSize(3);
			spinCreditCardLimit.setValue(0);
			
			// INSURANCE
			// Insured Amount Label
			JLabel lblInsuredAmount = new JLabel("*Insured Amount ($):");
			
			// Insured Amount Spinner
			SpinnerNumberModel InsuredAmountSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
			spinInsuredAmount = new JSpinner(InsuredAmountSpinModel);
			JSpinner.NumberEditor InsuredAmountSpinEditor = (JSpinner.NumberEditor)spinInsuredAmount.getEditor();
	        DecimalFormat InsuredAmountSpinFormat = InsuredAmountSpinEditor.getFormat();
	        InsuredAmountSpinFormat.setMinimumFractionDigits(2);
	        InsuredAmountSpinFormat.setMaximumFractionDigits(2);
	        InsuredAmountSpinFormat.setGroupingSize(3);
	        spinInsuredAmount.setValue(0);
	        
			// Insurance Premium Label
			JLabel lblInsurancePremium = new JLabel("*Premium p.a. ($):");
			
			// Insurance Premium Spinner
			SpinnerNumberModel PremiumSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
			spinInsurancePremium = new JSpinner(PremiumSpinModel);
			JSpinner.NumberEditor PremiumSpinEditor = (JSpinner.NumberEditor)spinInsurancePremium.getEditor();
	        DecimalFormat PremiumSpinFormat = PremiumSpinEditor.getFormat();
	        PremiumSpinFormat.setMinimumFractionDigits(2);
	        PremiumSpinFormat.setMaximumFractionDigits(2);
	        PremiumSpinFormat.setGroupingSize(3);
	        spinInsurancePremium.setValue(0);
	        
	        // LOAN ACCOUNT
	        // Loan Amount Label
	        JLabel lblLoanAmount = new JLabel("*Loan Amount ($):");
			
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
			JLabel lblLoanCurrentValue = new JLabel("*Current Value ($):");
			
			// Current Value Spinner
			SpinnerNumberModel LoanCurrentValueSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
			spinLoanCurrentValue = new JSpinner(LoanCurrentValueSpinModel);
			JSpinner.NumberEditor LoanCurrentValueSpinEditor = (JSpinner.NumberEditor)spinLoanCurrentValue.getEditor();
		    DecimalFormat LoanCurrentValueSpinFormat = LoanCurrentValueSpinEditor.getFormat();
		    LoanCurrentValueSpinFormat.setMinimumFractionDigits(2);
		    LoanCurrentValueSpinFormat.setMaximumFractionDigits(2);
		    LoanCurrentValueSpinFormat.setGroupingSize(3);
		    spinLoanCurrentValue.setValue(0);
		    
		    // PROPERTY
	        // Address Label
	        JLabel lblAddress = new JLabel("*Address:");
			
			// Address Textfield
			txtAddress = new JTextField();
			txtAddress.setColumns(10);
			
			// Property Purchase Amount Label
			JLabel lblPropertyPurchaseAmount = new JLabel("*Purchase Amount ($):");
			
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
			JLabel lblPropertySoldAmount = new JLabel("*Sold Amount ($):");
			
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
			JLabel lblPropertyCurrentValue = new JLabel("*Current Value ($):");
			
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
			JLabel lblPropertySolFeeBuy = new JLabel("*Solicitor Fees (Buy) ($):");
			
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
			JLabel lblPropertySolFeeSell = new JLabel("*Solicitor Fees (Sell) ($):");
			
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
			JLabel lblPropertyGovtChargesBuy = new JLabel("*Govt Charges (Buy) ($):");
			
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
			JLabel lblPropertyGovtChargesSell = new JLabel("*Govt Charges (Sell) ($):");
			
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
			JLabel lblPropertyAgtFeeBuy = new JLabel("*Agent Fees (Buy) ($):");
			
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
			JLabel lblPropertyAgtFeeSell = new JLabel("*Agent Fees (Sell) ($):");
			
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
			JLabel lblPropertyCapGainsTax = new JLabel("*Capital Gains Tax ($):");
			
			// Property Capital Gains Tax Spinner
			SpinnerNumberModel PropertyCapitalGainsTaxSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
			spinPropertyCapGainsTax = new JSpinner(PropertyCapitalGainsTaxSpinModel);
			JSpinner.NumberEditor PropertyCapitalGainsTaxSpinEditor = (JSpinner.NumberEditor)spinPropertyCapGainsTax.getEditor();
			DecimalFormat PropertyCapitalGainsTaxSpinFormat = PropertyCapitalGainsTaxSpinEditor.getFormat();
			PropertyCapitalGainsTaxSpinFormat.setMinimumFractionDigits(2);
			PropertyCapitalGainsTaxSpinFormat.setMaximumFractionDigits(2);
			PropertyCapitalGainsTaxSpinFormat.setGroupingSize(3);
			spinPropertyCapGainsTax.setValue(0);
			
			// SAVINGS ACCOUNT
			// Nothing Extra Needed
			
			// SHARES / MANAGED FUNDS
			// Share Price Label
			JLabel lblCurrentUnitShareValue = new JLabel("*Share Price ($):");
			
			// Share Price Spinner
			SpinnerNumberModel CurrentUnitShareValueSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
			spinCurrentUnitShareValue = new JSpinner(CurrentUnitShareValueSpinModel);
			JSpinner.NumberEditor CurrentUnitShareValueSpinEditor = (JSpinner.NumberEditor)spinCurrentUnitShareValue.getEditor();
			DecimalFormat CurrentUnitShareValueSpinFormat = CurrentUnitShareValueSpinEditor.getFormat();
			CurrentUnitShareValueSpinFormat.setMinimumFractionDigits(2);
			CurrentUnitShareValueSpinFormat.setMaximumFractionDigits(2);
			CurrentUnitShareValueSpinFormat.setGroupingSize(3);
			spinCurrentUnitShareValue.setValue(0);
			
			// TERM DEPOSIT
			// Term Deposit Opening Balance Label
			JLabel lblTermDepositOpeningBalance = new JLabel("*Opening Balance ($):");
			
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
			JLabel lblTermDepositInterestAmount = new JLabel("*Interest Amount ($):");
			
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
			JLabel lblTermDepositOtherCharges = new JLabel("*Other Charges ($):");
			
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
			JLabel lblTermDepositBankFees = new JLabel("*Bank Fees ($):");
			
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
			JLabel lblTermDepositInterestRate = new JLabel("*Interest Rate (%):");
			
			// Term Deposit Interest Rate Spinner
			SpinnerNumberModel TermDepositInterestRateSpinModel = new SpinnerNumberModel(0, 0, 1000000000, 0.01);
			spinTermDepositInterestRate = new JSpinner(TermDepositInterestRateSpinModel);
			JSpinner.NumberEditor TermDepositInterestRateSpinEditor = (JSpinner.NumberEditor)spinTermDepositInterestRate.getEditor();
			DecimalFormat TermDepositInterestRateSpinFormat = TermDepositInterestRateSpinEditor.getFormat();
			TermDepositInterestRateSpinFormat.setMinimumFractionDigits(2);
			TermDepositInterestRateSpinFormat.setMaximumFractionDigits(2);
			TermDepositInterestRateSpinFormat.setGroupingSize(3);
			spinTermDepositInterestRate.setValue(0);
			
			if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[0])) {
				// Savings Account
				// Nothing additional needed
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[1])) {
				// Credit Card
				// Position
				separator.setBounds(0, 175, 511, 12);
				lblLimit.setBounds(0, 185, 60, 30);
				spinCreditCardLimit.setBounds(60, 185, 451, 30);
				
				// Add
				properties_panel.add(separator);
				properties_panel.add(lblLimit);
				properties_panel.add(spinCreditCardLimit);
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[2])) {
				// Loan Account
				// Position
				separator.setBounds(0, 175, 511, 12);
				lblLoanAmount.setBounds(0, 185, 130, 30);
				spinLoanAmount.setBounds(129, 185, 140, 30);
				lblLoanCurrentValue.setBounds(282, 185, 130, 30);
				spinLoanCurrentValue.setBounds(394, 185, 116, 30);
				
				// Add
				properties_panel.add(separator);
				properties_panel.add(lblLoanAmount);
				properties_panel.add(spinLoanAmount);
				properties_panel.add(lblLoanCurrentValue);
				properties_panel.add(spinLoanCurrentValue);
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[3])) {
				// Term Deposit
				// Position
				separator.setBounds(0, 175, 511, 12);
				lblTermDepositOpeningBalance.setBounds(0, 185, 135, 30);
				spinTermDepositOpeningBalance.setBounds(135, 185, 376, 30);
				lblTermDepositInterestAmount.setBounds(0, 220, 135, 30);
				spinTermDepositInterestAmount.setBounds(135, 220, 115, 30);
				lblTermDepositOtherCharges.setBounds(258, 220, 130, 30);
				spinTermDepositOtherCharges.setBounds(396, 220, 115, 30);
				lblTermDepositBankFees.setBounds(0, 255, 135, 30);
				spinTermDepositBankFees.setBounds(135, 255, 115, 30);
				lblTermDepositInterestRate.setBounds(258, 255, 130, 30);
				spinTermDepositInterestRate.setBounds(396, 255, 115, 30);
				
				// Add
				properties_panel.add(separator);
				properties_panel.add(lblTermDepositOpeningBalance);
				properties_panel.add(spinTermDepositOpeningBalance);
				properties_panel.add(lblTermDepositInterestAmount);
				properties_panel.add(spinTermDepositInterestAmount);
				properties_panel.add(lblTermDepositOtherCharges);
				properties_panel.add(spinTermDepositOtherCharges);
				properties_panel.add(lblTermDepositBankFees);
				properties_panel.add(spinTermDepositBankFees);
				properties_panel.add(lblTermDepositInterestRate);
				properties_panel.add(spinTermDepositInterestRate);
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[4])) {
				// Superannuation
				// Nothing additional needed
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[5])) {
				// Property
				// Position
				separator.setBounds(0, 175, 511, 12);
				lblAddress.setBounds(0, 185, 70, 30);
				txtAddress.setBounds(70, 185, 441, 30);
				lblPropertyPurchaseAmount.setBounds(0, 220, 130, 30);
				spinPropertyPurchaseAmount.setBounds(145, 220, 366, 30);
				lblPropertySoldAmount.setBounds(0, 255, 130, 30);
				spinPropertySoldAmount.setBounds(145, 255, 366, 30);
				lblPropertyCurrentValue.setBounds(0, 290, 130, 30);
				spinPropertyCurrentValue.setBounds(145, 290, 366, 30);
				lblPropertySolFeeBuy.setBounds(0, 325, 140, 30);
				spinPropertySolFeeBuy.setBounds(145, 325, 105, 30);
				lblPropertySolFeeSell.setBounds(258, 325, 140, 30);
				spinPropertySolFeeSell.setBounds(406, 325, 105, 30);
				lblPropertyGovtChargesBuy.setBounds(0, 360, 140, 30);
				spinPropertyGovtChargesBuy.setBounds(145, 360, 105, 30);
				lblPropertyGovtChargesSell.setBounds(258, 360, 140, 30);
				spinPropertyGovtChargesSell.setBounds(406, 360, 105, 30);
				lblPropertyAgtFeeBuy.setBounds(0, 395, 140, 30);
				spinPropertyAgtFeeBuy.setBounds(145, 395, 105, 30);
				lblPropertyAgtFeeSell.setBounds(258, 395, 140, 30);
				spinPropertyAgtFeeSell.setBounds(406, 395, 105, 30);
				lblPropertyCapGainsTax.setBounds(0, 430, 140, 30);
				spinPropertyCapGainsTax.setBounds(145, 430, 105, 30);
				
				// Add
				properties_panel.add(separator);
				properties_panel.add(lblAddress);
				properties_panel.add(txtAddress);
				properties_panel.add(lblPropertyPurchaseAmount);
				properties_panel.add(spinPropertyPurchaseAmount);
				properties_panel.add(lblPropertySoldAmount);
				properties_panel.add(spinPropertySoldAmount);
				properties_panel.add(lblPropertyCurrentValue);
				properties_panel.add(spinPropertyCurrentValue);
				properties_panel.add(lblPropertySolFeeBuy);
				properties_panel.add(spinPropertySolFeeBuy);
				properties_panel.add(lblPropertySolFeeSell);
				properties_panel.add(spinPropertySolFeeSell);
				properties_panel.add(lblPropertyGovtChargesBuy);
				properties_panel.add(spinPropertyGovtChargesBuy);
				properties_panel.add(lblPropertyGovtChargesSell);
				properties_panel.add(spinPropertyGovtChargesSell);
				properties_panel.add(lblPropertyAgtFeeBuy);
				properties_panel.add(spinPropertyAgtFeeBuy);
				properties_panel.add(lblPropertyAgtFeeSell);
				properties_panel.add(spinPropertyAgtFeeSell);
				properties_panel.add(lblPropertyCapGainsTax);
				properties_panel.add(spinPropertyCapGainsTax);
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[6])) {
				// Shares/Managed Funds
				// Position
				separator.setBounds(0, 175, 511, 12);
				lblCurrentUnitShareValue.setBounds(0, 185, 130, 30);
				spinCurrentUnitShareValue.setBounds(100, 185, 411, 30);
				
				// Add
				properties_panel.add(separator);
				properties_panel.add(lblCurrentUnitShareValue);
				properties_panel.add(spinCurrentUnitShareValue);
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[7])) {
				// Insurance
				// Position
				separator.setBounds(0, 175, 511, 12);
				lblInsuredAmount.setBounds(0, 185, 130, 30);
				spinInsuredAmount.setBounds(129, 185, 140, 30);
				lblInsurancePremium.setBounds(282, 185, 130, 30);
				spinInsurancePremium.setBounds(390, 185, 120, 30);
				
				// Add
				properties_panel.add(separator);
				properties_panel.add(lblInsurancePremium);
				properties_panel.add(spinInsurancePremium);
				properties_panel.add(lblInsuredAmount);
				properties_panel.add(spinInsuredAmount);
			}
			
			// Transaction History Panel
			JPanel pnlTxn = new JPanel();
			pnlTxn.setLayout(null);
			
			// Only display the transactions panel for savings accounts, credit cards, superannuation and shares/managed funds
			if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[0]) ||
					entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[1]) ||
					entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[4])) {
				// Savings Accounts, Credit Cards or Superannuation Funds
				
				tabbedPane.addTab("Transaction History", null, pnlTxn, "View the transaction history of the entity.");
				
				// Current Value Label
				lblCurrentValue = new JLabel();
				lblCurrentValue.setBounds(7, 369, 325, 29);
				pnlTxn.add(lblCurrentValue);
				
				// Create Transaction Table Model and Table
				String[] TransactionTableColumnNames = {"ID", "Date", "Category", "Debits", "Credits", "Description"};
				TxnTableModel = new DefaultTableModel(0, TransactionTableColumnNames.length) ;
				TxnTableModel.setColumnIdentifiers(TransactionTableColumnNames);
				TxnTable = new JTable(TxnTableModel) {
					private static final long serialVersionUID = 3349872744977252542L;
					
					// Ensure cell values cannot be modified by the user
			        public boolean isCellEditable(int row, int column) {                
			                return false;
			        };
				};
				
				// Place table within a JScrollPane (allows for scrolling through results)
				JScrollPane panel_transactions = new JScrollPane(TxnTable);
				
				// Place JScrollPane within a JPanel
				TxnTable.setFillsViewportHeight(true);
				panel_transactions.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				panel_transactions.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel_transactions.setBounds(6, 6, 562, 358);
				pnlTxn.add(panel_transactions);
				
				// Add Transaction Button
				JButton btnAddTxn = new JButton("Add");
				btnAddTxn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AddTxn();
					}
				});
				btnAddTxn.setBounds(498, 368, 70, 29);
				pnlTxn.add(btnAddTxn);
				
				// Edit Transaction Button
				JButton btnEditTxn = new JButton("Edit");
				btnEditTxn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						EditTxn();
					}
				});
				btnEditTxn.setBounds(421, 368, 70, 29);
				pnlTxn.add(btnEditTxn);
				
				// Delete Transaction Button
				JButton btnDeleteTxn = new JButton("Delete");
				btnDeleteTxn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DeleteTxn();
					}
				});
				btnDeleteTxn.setBounds(344, 368, 70, 29);
				pnlTxn.add(btnDeleteTxn);
				
				// Initialise
				RefreshTxnTable();
				UpdateCurrentValue();
			} else if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[6])) {
				// Shares/Managed Funds
				// Assign the transaction panel a scrollbar
				pnlTxn.setPreferredSize(new Dimension (574, 1315));
				JScrollPane sharescroller = new JScrollPane(pnlTxn);
				sharescroller.setBorder(BorderFactory.createEmptyBorder());
				sharescroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				sharescroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				
				// Add the scrollbar to the tabbed pane
				tabbedPane.addTab("Transaction History", null, sharescroller, "View the transaction history of the entity.");
				
				// Valuation Panel
				Border sharestats_border = BorderFactory.createTitledBorder("Valuation:");
				JPanel pnlValuation = new JPanel();
				pnlValuation.setBorder(sharestats_border);
				pnlValuation.setBounds(6, 6, 548, 97);
				pnlTxn.add(pnlValuation);
				pnlValuation.setLayout(null);
				
				// Shares Held Label
				JLabel lblTotalSharesHeld = new JLabel("Σ Shares Held:");
				lblTotalSharesHeld.setBounds(12, 22, 140, 30);
				pnlValuation.add(lblTotalSharesHeld);
				
				// Shares Held Textfield
				txtTotalSharesHeld = new JTextField();
				txtTotalSharesHeld.setEditable(false);
				txtTotalSharesHeld.setBounds(154, 22, 384, 30);
				pnlValuation.add(txtTotalSharesHeld);
				txtTotalSharesHeld.setColumns(10);
				
				// Total Value Label
				JLabel lblTotalValue = new JLabel("Σ Holdings Value ($):");
				lblTotalValue.setBounds(12, 55, 140, 30);
				pnlValuation.add(lblTotalValue);
				
				// Total Value Textfield
				txtTotalValue = new JTextField();
				txtTotalValue.setEditable(false);
				txtTotalValue.setBounds(154, 55, 384, 30);
				pnlValuation.add(txtTotalValue);
				txtTotalValue.setColumns(10);
				
				// Buy Panel
				Border sharebuy_border = BorderFactory.createTitledBorder("Buy:");
				JPanel pnlShareBuy = new JPanel();
				pnlShareBuy.setBorder(sharebuy_border);
				pnlShareBuy.setBounds(6, 105, 548, 300);
				pnlTxn.add(pnlShareBuy);
				pnlShareBuy.setLayout(null);
				
				// New Share Buy Button
				JButton btnNewShrBuy = new JButton("New");
				btnNewShrBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						NewBuyShare();
					}
				});
				btnNewShrBuy.setBounds(456, 258, 80, 29);
				pnlShareBuy.add(btnNewShrBuy);
				
				// Edit Share Buy Button
				JButton btnEditShrBuy = new JButton("Edit");
				btnEditShrBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						EditBuyShare();
					}
				});
				btnEditShrBuy.setBounds(373, 258, 80, 29);
				pnlShareBuy.add(btnEditShrBuy);
				
				// Delete Share Buy Button
				JButton btnDeleteShrBuy = new JButton("Delete");
				btnDeleteShrBuy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DeleteBuyShare();
					}
				});
				btnDeleteShrBuy.setBounds(290, 258, 80, 29);
				pnlShareBuy.add(btnDeleteShrBuy);
				
				// Create Share Buy Table Model and Table
				String[] ShareBuyTableColumnNames = {"ID", "Date", "Share Price", "Number of Shares", "Brokerage Charges", "Other Charges", "Net Payment", "Description"};
				ShareBuyTableModel = new DefaultTableModel(0, ShareBuyTableColumnNames.length) ;
				ShareBuyTableModel.setColumnIdentifiers(ShareBuyTableColumnNames);
				ShareBuyTable = new JTable(ShareBuyTableModel) {
					private static final long serialVersionUID = 1L;

					// Ensure cell values cannot be modified by the user
			        public boolean isCellEditable(int row, int column) {                
			                return false;
			        };
				};
				
				// Place Share Buy Table within a JScrollPane (allows for scrolling through results)
				JScrollPane panel_sharebuy = new JScrollPane(ShareBuyTable);
				
				// Place JScrollPane within a JPanel
				ShareBuyTable.setFillsViewportHeight(true);
				ShareBuyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				ShareBuyTable.getColumnModel().getColumn(0).setPreferredWidth(40);
				ShareBuyTable.getColumnModel().getColumn(1).setPreferredWidth(90);
				ShareBuyTable.getColumnModel().getColumn(2).setPreferredWidth(80);
				ShareBuyTable.getColumnModel().getColumn(3).setPreferredWidth(70);
				ShareBuyTable.getColumnModel().getColumn(4).setPreferredWidth(100);
				ShareBuyTable.getColumnModel().getColumn(5).setPreferredWidth(100);
				ShareBuyTable.getColumnModel().getColumn(6).setPreferredWidth(90);
				ShareBuyTable.getColumnModel().getColumn(7).setPreferredWidth(300);
				panel_sharebuy.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				panel_sharebuy.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				panel_sharebuy.setBounds(12, 22, 524, 232);
				pnlShareBuy.add(panel_sharebuy);
				
				lblTotalSpent = new JLabel();
				lblTotalSpent.setBounds(12, 258, 250, 29);
				pnlShareBuy.add(lblTotalSpent);
				
				// Sell Panel
				Border sharesell_border = BorderFactory.createTitledBorder("Sell:");
				JPanel pnlShareSell = new JPanel();
				pnlShareSell.setBorder(sharesell_border);
				pnlShareSell.setBounds(6, 407, 548, 300);
				pnlTxn.add(pnlShareSell);
				pnlShareSell.setLayout(null);
				
				// New Share Sell Button
				JButton btnNewShrSell = new JButton("New");
				btnNewShrSell.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						NewSellShare();
					}
				});
				btnNewShrSell.setBounds(456, 258, 80, 29);
				pnlShareSell.add(btnNewShrSell);
				
				// Edit Share Sell Button
				JButton btnEditShrSell = new JButton("Edit");
				btnEditShrSell.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						EditSellShare();
					}
				});
				btnEditShrSell.setBounds(373, 258, 80, 29);
				pnlShareSell.add(btnEditShrSell);
				
				// Delete Share Sell Button
				JButton btnDeleteShrSell = new JButton("Delete");
				btnDeleteShrSell.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DeleteSellShare();
					}
				});
				btnDeleteShrSell.setBounds(290, 258, 80, 29);
				pnlShareSell.add(btnDeleteShrSell);			
				
				// Create Share Sell Table Model and Table
				String[] ShareSellTableColumnNames = {"ID", "Date", "Share Price", "Number of Shares", "Brokerage Charges", "Other Charges", "Capital Gains Tax", "Net Income", "Description"};
				ShareSellTableModel = new DefaultTableModel(0, ShareSellTableColumnNames.length) ;
				ShareSellTableModel.setColumnIdentifiers(ShareSellTableColumnNames);
				ShareSellTable = new JTable(ShareSellTableModel) {
					private static final long serialVersionUID = -5147644796527751975L;

					// Ensure cell values cannot be modified by the user
			        public boolean isCellEditable(int row, int column) {                
			                return false;
			        };
				};
							
				// Place Share Sell Table within a JScrollPane (allows for scrolling through results)
				JScrollPane panel_sharesell = new JScrollPane(ShareSellTable);
				
				// Place JScrollPane within a JPanel
				ShareSellTable.setFillsViewportHeight(true);
				ShareSellTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				ShareSellTable.getColumnModel().getColumn(0).setPreferredWidth(40);
				ShareSellTable.getColumnModel().getColumn(1).setPreferredWidth(90);
				ShareSellTable.getColumnModel().getColumn(2).setPreferredWidth(80);
				ShareSellTable.getColumnModel().getColumn(3).setPreferredWidth(70);
				ShareSellTable.getColumnModel().getColumn(4).setPreferredWidth(100);
				ShareSellTable.getColumnModel().getColumn(5).setPreferredWidth(100);
				ShareSellTable.getColumnModel().getColumn(6).setPreferredWidth(100);
				ShareSellTable.getColumnModel().getColumn(7).setPreferredWidth(90);
				ShareSellTable.getColumnModel().getColumn(8).setPreferredWidth(300);
				panel_sharesell.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				panel_sharesell.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				panel_sharesell.setBounds(12, 22, 524, 232);
				pnlShareSell.add(panel_sharesell);
				
				lblTotalEarnt = new JLabel();
				lblTotalEarnt.setBounds(12, 258, 250, 29);
				pnlShareSell.add(lblTotalEarnt);
				
				// Dividend Panel
				Border sharediv_border = BorderFactory.createTitledBorder("Dividends:");
				JPanel pnlShareDiv = new JPanel();
				pnlShareDiv.setBorder(sharediv_border);
				pnlShareDiv.setBounds(6, 709, 548, 300);
				pnlTxn.add(pnlShareDiv);
				pnlShareDiv.setLayout(null);
				
				// New Share Sell Button
				JButton btnNewShrDiv = new JButton("New");
				btnNewShrDiv.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						NewDividendShare();
					}
				});
				btnNewShrDiv.setBounds(456, 258, 80, 29);
				pnlShareDiv.add(btnNewShrDiv);
				
				// Edit Share Sell Button
				JButton btnEditShrDiv = new JButton("Edit");
				btnEditShrDiv.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						EditDividendShare();
					}
				});
				btnEditShrDiv.setBounds(373, 258, 80, 29);
				pnlShareDiv.add(btnEditShrDiv);
				
				// Delete Share Sell Button
				JButton btnDeleteShrDiv = new JButton("Delete");
				btnDeleteShrDiv.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DeleteDividendShare();
					}
				});
				btnDeleteShrDiv.setBounds(290, 258, 80, 29);
				pnlShareDiv.add(btnDeleteShrDiv);			
				
				// Create Share Dividend Table Model and Table
				String[] ShareDivTableColumnNames = {"ID", "Date", "Number of Shares", "Dividend Per Share", "Franking %", "Franking Credits Per Share", "Total Dividend", "Total Franking Credits", "Taxable Income", "Description"};
				ShareDivTableModel = new DefaultTableModel(0, ShareDivTableColumnNames.length) ;
				ShareDivTableModel.setColumnIdentifiers(ShareDivTableColumnNames);
				ShareDivTable = new JTable(ShareDivTableModel) {
					private static final long serialVersionUID = 2515747904272142317L;

					// Ensure cell values cannot be modified by the user
			        public boolean isCellEditable(int row, int column) {                
			                return false;
			        };
				};
							
				// Place Share Sell Table within a JScrollPane (allows for scrolling through results)
				JScrollPane panel_sharediv = new JScrollPane(ShareDivTable);
				
				// Place JScrollPane within a JPanel
				ShareDivTable.setFillsViewportHeight(true);
				ShareDivTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				ShareDivTable.getColumnModel().getColumn(0).setPreferredWidth(40);
				ShareDivTable.getColumnModel().getColumn(1).setPreferredWidth(90);
				ShareDivTable.getColumnModel().getColumn(2).setPreferredWidth(70);
				ShareDivTable.getColumnModel().getColumn(3).setPreferredWidth(80);
				ShareDivTable.getColumnModel().getColumn(4).setPreferredWidth(80);
				ShareDivTable.getColumnModel().getColumn(5).setPreferredWidth(100);
				ShareDivTable.getColumnModel().getColumn(6).setPreferredWidth(100);
				ShareDivTable.getColumnModel().getColumn(7).setPreferredWidth(90);
				ShareDivTable.getColumnModel().getColumn(8).setPreferredWidth(90);
				ShareDivTable.getColumnModel().getColumn(9).setPreferredWidth(300);
				panel_sharediv.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				panel_sharediv.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				panel_sharediv.setBounds(12, 22, 524, 232);
				pnlShareDiv.add(panel_sharediv);
				
				lblTotalTaxableIncome = new JLabel();
				lblTotalTaxableIncome.setBounds(12, 258, 250, 29);
				pnlShareDiv.add(lblTotalTaxableIncome);
				
				// Dividend Reinvestment Plan Panel
				Border sharedrp_border = BorderFactory.createTitledBorder("Dividend Reinvestment Plans:");
				JPanel pnlShareDRP = new JPanel();
				pnlShareDRP.setBorder(sharedrp_border);
				pnlShareDRP.setBounds(6, 1011, 548, 300);
				pnlTxn.add(pnlShareDRP);
				pnlShareDRP.setLayout(null);
				
				// New Share Sell Button
				JButton btnNewShrDRP = new JButton("New");
				btnNewShrDRP.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						NewDRPShare();
					}
				});
				btnNewShrDRP.setBounds(456, 258, 80, 29);
				pnlShareDRP.add(btnNewShrDRP);
				
				// Edit Share Sell Button
				JButton btnEditShrDRP = new JButton("Edit");
				btnEditShrDRP.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						EditDRPShare();
					}
				});
				btnEditShrDRP.setBounds(373, 258, 80, 29);
				pnlShareDRP.add(btnEditShrDRP);
				
				// Delete Share Sell Button
				JButton btnDeleteShrDRP = new JButton("Delete");
				btnDeleteShrDRP.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DeleteDRPShare();
					}
				});
				btnDeleteShrDRP.setBounds(290, 258, 80, 29);
				pnlShareDRP.add(btnDeleteShrDRP);			
				
				// Create Share DRP Table Model and Table
				String[] ShareDRPTableColumnNames = {
						"ID",
						"Date",
						"Share Price",
						"# New Shares",
						"# Existing Shares",
						"Dividend Per Share",
						"Franking %",
						"Franking Credits Per Share",
						"Total Dividend",
						"Total Franking Credits",
						"Brokerage Charges",
						"Other Charges",
						"Description"
						};
				ShareDRPTableModel = new DefaultTableModel(0, ShareDRPTableColumnNames.length) ;
				ShareDRPTableModel.setColumnIdentifiers(ShareDRPTableColumnNames);
				ShareDRPTable = new JTable(ShareDRPTableModel) {
					private static final long serialVersionUID = 1220296906281385328L;

					// Ensure cell values cannot be modified by the user
			        public boolean isCellEditable(int row, int column) {                
			                return false;
			        };
				};
				
				// Place Share Sell Table within a JScrollPane (allows for scrolling through results)
				JScrollPane panel_sharedrp = new JScrollPane(ShareDRPTable);
				
				// Place JScrollPane within a JPanel
				ShareDRPTable.setFillsViewportHeight(true);
				ShareDRPTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				ShareDRPTable.getColumnModel().getColumn(0).setPreferredWidth(40);
				ShareDRPTable.getColumnModel().getColumn(1).setPreferredWidth(90);
				ShareDRPTable.getColumnModel().getColumn(2).setPreferredWidth(70);
				ShareDRPTable.getColumnModel().getColumn(3).setPreferredWidth(80);
				ShareDRPTable.getColumnModel().getColumn(4).setPreferredWidth(80);
				ShareDRPTable.getColumnModel().getColumn(5).setPreferredWidth(100);
				ShareDRPTable.getColumnModel().getColumn(6).setPreferredWidth(100);
				ShareDRPTable.getColumnModel().getColumn(7).setPreferredWidth(100);
				ShareDRPTable.getColumnModel().getColumn(8).setPreferredWidth(100);
				ShareDRPTable.getColumnModel().getColumn(9).setPreferredWidth(100);
				ShareDRPTable.getColumnModel().getColumn(10).setPreferredWidth(80);
				ShareDRPTable.getColumnModel().getColumn(11).setPreferredWidth(80);
				ShareDRPTable.getColumnModel().getColumn(12).setPreferredWidth(300);
				panel_sharedrp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				panel_sharedrp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				panel_sharedrp.setBounds(12, 22, 524, 232);
				pnlShareDRP.add(panel_sharedrp);
				
				lblTotalReinvested = new JLabel();
				lblTotalReinvested.setBounds(12, 258, 250, 29);
				pnlShareDRP.add(lblTotalReinvested);
				
				// Initialise
				RefreshShrTxnTable();
				UpdateShrValuation();
			}
			
			// Related Services Panel
			JPanel pnlRelSer = new JPanel();
			tabbedPane.addTab("Related Services", null, pnlRelSer, "View services related to the entity.");
			pnlRelSer.setLayout(null);
			
			// Create services table model and header
			String[] ServicesTableColumnNames = {"Service Type", "Description", "Institution", "Frequency", "User ID", "Password/PIN", "Contact Details", "Expiry Date", "ID"};
			tblRelServModel = new DefaultTableModel(0, ServicesTableColumnNames.length) ;
			tblRelServModel.setColumnIdentifiers(ServicesTableColumnNames);
			
			// Create services table using table model
			tblRelServ = new JTable(tblRelServModel) {
				private static final long serialVersionUID = 6502932320740805856L;

				// Ensure cell values cannot be modified by the user
		        public boolean isCellEditable(int row, int column) {                
		                return false;               
		        };
			};
			
			// Place table within a JScrollPane (allows for scrolling through results)
			JScrollPane panel_result = new JScrollPane(tblRelServ);
			
			// Place JScrollPane within a JPanel
			tblRelServ.setFillsViewportHeight(true);
			panel_result.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			panel_result.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_result.setBounds(6, 6, 562, 337);
			tblRelServ.setBounds(0, 0, 390, 270);
			pnlRelSer.add(panel_result);
			
			// Total Services Label
			lblTotalServices = new JLabel("");
			lblTotalServices.setBounds(6, 348, 562, 16);
			pnlRelSer.add(lblTotalServices);
			
			// Update Services Table
			RefreshServicesTable();
			
			// New Service Button
			JButton btnNew = new JButton("New");
			btnNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NewService();
				}
			});
			btnNew.setBounds(304, 360, 80, 36);
			pnlRelSer.add(btnNew);
			
			// Edit Service Button
			JButton btnEdit = new JButton("Edit");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					EditService();
				}
			});
			btnEdit.setBounds(396, 360, 80, 36);
			pnlRelSer.add(btnEdit);
			
			// Delete Service Button
			JButton btnDelete = new JButton("Delete");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DeleteService();
				}
			});
			btnDelete.setBounds(488, 360, 80, 36);
			pnlRelSer.add(btnDelete);
			
			// Relationship Tree Panel
			pnlRelTree = new JPanel();
			tabbedPane.addTab("Relationship Tree", null, pnlRelTree, "Visualise relationships between entities.");
			pnlRelTree.setLayout(null);
			CreateRelationshipTree();
			viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
			viewer.enableAutoLayout();
			viewPanel = viewer.addDefaultView(false);
			viewPanel.setSize(574, 357);
			pnlRelTree.add(viewPanel);
			
			// Edit Relationships Button
			JButton btnNewButton = new JButton("Edit Relationships");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					OpenLinkedEntities();
				}
			});
			btnNewButton.setBounds(428, 365, 140, 30);
			pnlRelTree.add(btnNewButton);
			
			// Initialise Basic Entity Fields
			GetEntityNumber();
			GetInstitutionName();
			GetEntityStatus();
			GetDescription();
			GetComment();
			GetSpecific();
			try {
				GetDates();
			} catch (ParseException e1) {
				MessageBox.Error("There was an error parsing the dates associated with the selected entity.", "Date Parser");
				Close();
			}
			
			// Set the default focus to the entity number
			txtNumber.requestFocusInWindow();
		} catch (SQLException e4) {
			MessageBox.Error("A database related error was encountered whilst attempting to display the selected entity.", "Entity Viewer");
			Close();
		}
	}
}