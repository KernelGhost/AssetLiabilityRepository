import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendLayout;
import org.knowm.xchart.style.Styler.LegendPosition;

import CustomTypes.DisplayEntity;
import CustomTypes.Entity;
import CustomTypes.Holder;
import CustomTypes.Institution;
import CustomTypes.ShareTransaction;

public class frmMain extends JFrame {
	private static final long serialVersionUID = 2293811548706316488L;
	
	// Keeps track of which entities are open
	public ArrayList<String> OpenedEntities = new ArrayList<String>();
	
	// JFrames, JPanels and other GUI objects
	private frmAbout frmAbout;				// The 'About' window
	private frmNewEntity frmNewEntity;		// The 'New Entity' window
	private frmInst frmInst;				// The 'Institutional Details' window
	private frmHolder frmHolder;			// The 'Holder Details' window
	private JPanel contentPane;				// JPanel for the main JFrame
	private JPanel panel_assport;			// JPanel for the asset portfolio graph
	private JPanel panel_liabport;			// JPanel for the liability portfolio graph
	private PieChart chartAssport;			// Asset portfolio pie chart
	private PieChart chartLiabport;			// Liability portfolio pie chart
	private DefaultTableModel tblModel;		// Table model for the search results table
	private JTable tblSearch;				// Search results table
	private JLabel lblNumEntities;			// JLabel displaying the number of search results
	private JLabel lblValue;				// JLabel displaying the overall current value of search results
	private JProgressBar progSearch;		// The progress bar used to indicate search progress
	
	// The comboboxes for the entity search (0-Entity Type, 1-Holder Name, 2-Institution Name, 3-Status Type)
	private ArrayList<JComboBox<String>> comboBoxes = new ArrayList<JComboBox<String>>();
	
	// Other global variables
	private boolean frmAboutOpen = false;	// Stores if the 'About' window is open or not
	ArrayList<DisplayEntity> SearchResults;	// Stores search results
	
	// Called by frmInst and frmHolder to make the main window visible when they are closed
	public void ShowWindow() {
		this.setVisible(true);
	}
	
	// Called to initialise/populate the comboboxes for the entity search
	private void RefreshComboboxes() {
		boolean OldOption;		// Stores if something was selected beforehand or not
		String strSelected;		// Stores the value of the selected option in the combobox
		int NewIndex;			// Stores the new selection index to be used
		
		try {
			for (int intctr = 0; intctr < comboBoxes.size(); intctr++) {
				OldOption = !(comboBoxes.get(intctr).getSelectedIndex() == -1);	// -1 if nothing selected
				NewIndex = -1;
				strSelected = "";
				
				// Refresh the list
				String strAllOptions[];
				switch(intctr) {
					case 0:
						strAllOptions = Main.database_handler.GetAllEntityTypes();
						break;
					case 1:
						Holder[] AllHolders = Main.database_handler.GetAllHolders();
						if (AllHolders != null) {
							Arrays.sort(AllHolders);
							strAllOptions = new String[AllHolders.length];
							for (int intctr2 = 0; intctr2 < AllHolders.length; intctr2++) {
								strAllOptions[intctr2] = AllHolders[intctr2].GetName();
							}
							break;
						} else {
							strAllOptions = new String[0];
							break;
						}
					case 2:
						Institution[] AllInstitutions = Main.database_handler.GetAllInstitutions();
						if (AllInstitutions != null) {
							Arrays.sort(AllInstitutions);
							strAllOptions = new String[AllInstitutions.length];
							for (int intctr2 = 0; intctr2 < AllInstitutions.length; intctr2++) {
								strAllOptions[intctr2] = AllInstitutions[intctr2].GetName();
							}
							break;
						} else {
							strAllOptions = new String[0];
							break;
						}
					case 3:
						strAllOptions = Main.database_handler.GetAllStatusNames();
						break;
					default:
						strAllOptions = null;
				}
				
				Arrays.sort(strAllOptions);
				
				// If the combobox is being refreshed, keep track of the previously selected option
				if (OldOption) {
					strSelected = comboBoxes.get(intctr).getSelectedItem().toString();
				}
				
				// Remove old items
				comboBoxes.get(intctr).removeAllItems();
				
				// Add new items
				comboBoxes.get(intctr).addItem("All");
				for (int intCtr = 0; intCtr < strAllOptions.length; intCtr++) {
					comboBoxes.get(intctr).addItem(strAllOptions[intCtr]);
				}
				
				// Select previously selected index if combobox is being refreshed
				if (OldOption) {
					// Search for the old option
					for (int intCtr = 0; intCtr < comboBoxes.get(intctr).getItemCount(); intCtr++) {
						if (strSelected.equals(comboBoxes.get(intctr).getItemAt(intCtr).toString())) {
							NewIndex = intCtr;
							break;
						}
					}
					
					if (NewIndex >= 0) {
						// If a matching result was found
						comboBoxes.get(intctr).setSelectedIndex(NewIndex);
					} else {
						// If a matching result was not found
						comboBoxes.get(intctr).setSelectedIndex(0);
						switch(intctr) {
						case 0:
							MessageBox.Warning("The previously selected entity type could not be found. Resetting to option 'All'.", "ALR Search");
							break;
						case 1:
							MessageBox.Warning("The previously selected holder could not be found. Resetting to option 'All'.", "ALR Search");
							break;
						case 2:
							MessageBox.Warning("The previously selected institution could not be found. Resetting to option 'All'.", "ALR Search");
							break;
						case 3:
							MessageBox.Warning("The previously selected entity status could not be found. Resetting to option 'All'.", "ALR Search");
							break;
						}
					}
				} else {
					comboBoxes.get(intctr).setSelectedIndex(0);	// Will always be 'All' since this was added first (see above code)
				}
			}
		} catch (SQLException e) {
			// Throw an error and close ALR if any database-related errors occurred
			MessageBox.Error("There was a database related error whilst populating the comboboxes. ALR will now exit.", "ALR");
			System.exit(1);
		}
	}
	
	private void Search(String Entity, String Holder, String Institution, String Status) {
		String Holder_ID = "";			// Stores the holder ID of a given entity
		String Entity_Type_ID = "";		// Stores the entity type ID of a given entity
		String Institution_ID = "";		// Stores the institution ID of a given entity
		String Status_ID = "";			// Stores the status ID of a given entity
		String intArrResults[];			// Used to store the Entity IDs of all the search results
		
		// Set the progress bar in motion
		ProgBarrHandler(true);
		
		try {
			// Get Institution ID (if specified by the user)
			if (!Institution.equals("All")) {
				// Get the matching Institution ID for the specified Institution Name
				Institution_ID = Main.database_handler.GetInstitutionFromName(Institution).GetID();
				if (Institution_ID == null) {
					// No result was returned (error)
					MessageBox.Error("No matching 'Institution ID' could be found for the selected institution."
					+ System.lineSeparator() + "This is likely an issue with the 'Institution' table in the ALR database."
					+ System.lineSeparator() + "ALR will now exit.", "ALR Search");
					System.exit(1);
				}
			}
			
			// Get Holder ID (if specified by the user)
			if (!Holder.equals("All")) {
				// Get the matching Holder ID for the specified Holder Name
				Holder_ID = Main.database_handler.GetHolderFromName(Holder).GetID();
				if (Holder_ID == null) {
					// No result was returned (error)
					MessageBox.Error("No matching 'Holder ID' could be found for the selected holder."
					+ System.lineSeparator() + "This is likely an issue with the 'Holder' table in the ALR database."
					+ System.lineSeparator() + "ALR will now exit.", "ALR Search");
					System.exit(1);
				}
			}
			
			// Get Type ID (if specified by the user)
			if (!Entity.equals("All")) {
				// Get the matching Type ID for the specified Entity Type
				Entity_Type_ID = Main.database_handler.GetEntityTypeIDFromTypeName(Entity);
				if (Entity_Type_ID == null) {
					// No result was returned (error)
					MessageBox.Error("Entities of the selected type are not defined within the ALR database."
					+ System.lineSeparator() + "This is likely an issue with the 'Domn' table in the ALR database."
					+ System.lineSeparator() + "ALR will now exit.", "ALR Search");
					System.exit(1);
				}
			}
			
			// Get Status (if specified by the user)
			if (!Status.equals("All")) {
				// Get the matching Status ID for the specified Entity Status
				Status_ID = Main.database_handler.GetStatusIDFromStatusName(Status);
				if (Status_ID == null) {
					// No result was returned (error)
					MessageBox.Error("No entities with the selected status exist within the ALR database."
					+ System.lineSeparator() + "This is likely an issue with the 'Domn' table in the ALR database."
					+ System.lineSeparator() + "ALR will now exit.", "ALR Search");
					System.exit(1);
				}
			}
			
			// CONSTRUCT AGGREGATED QUERY
			intArrResults = Main.database_handler.Search(
					Holder,
					Status,
					Entity,
					Institution,
					Holder_ID,
					Status_ID,
					Entity_Type_ID,
					Institution_ID);
			
			if (intArrResults != null) {
				// Update Total Entity Count
				UpdateTotal(intArrResults.length);
				
				// Update Total Value + Create a more detailed array of search results
				BigDecimal[] intEntityAmounts = ProcessResults(intArrResults, Holder);
				BigDecimal TotalValue = BigDecimal.ZERO;
				
				for (int intctr = 0; intctr < intEntityAmounts.length; intctr++) {
					TotalValue = TotalValue.add(intEntityAmounts[intctr]);
				}
				
				UpdateTotalValue(TotalValue);
				
				// Update the pie chart
				if (intEntityAmounts != null) {
					UpdateGraph(intEntityAmounts);
				}
			} else {
				// Update Total Entity Count
				UpdateTotal(0);
				
				// Update Total Value
				UpdateTotalValue(BigDecimal.ZERO);
				
				// Clear the results table
				tblModel.setRowCount(0);
				
				// Blank out the graphs
				BigDecimal[] EmptyEntityAmounts = new BigDecimal[Main.ENTITY_TYPES.length];
				Arrays.fill(EmptyEntityAmounts, BigDecimal.ZERO);
				UpdateGraph(EmptyEntityAmounts);
				
				// Throw error
				MessageBox.Information("No entities with the selected characteristics were found.", "ALR Search");
			}
			
			// Stop the progress bar
			ProgBarrHandler(false);
			
		} catch (SQLException e) {
			// Display Error
			MessageBox.Error("A database related error occured whilst searching the database.", "ALR Search");
			
			// Stop the progress bar
			ProgBarrHandler(false);
		}
	}
	
	// Takes the Entity IDs from the search function to create a more detailed array of search results
	private BigDecimal[] ProcessResults(String[] intArrResults, String Holder) {
		SearchResults = new ArrayList<DisplayEntity>();							// Stores the final search results
		BigDecimal[] EntityAmounts = new BigDecimal[Main.ENTITY_TYPES.length];	// Stores the summed current value of each entity type
		Arrays.fill(EntityAmounts, BigDecimal.ZERO);
		
		try {
			for (int intCtr = 0; intCtr < intArrResults.length; intCtr++) {
				Entity entity_result = Main.database_handler.GetEntityFromID(intArrResults[intCtr]);
				
				// Determine current value of entity
				String strCurVal = "";			// Stores the current value of the entity
				DecimalFormat MoneyFormatter = new DecimalFormat("$#,##0.00;$-#,##0.00");
				
				if (!entity_result.GetEntityStatusName().equals(Main.strClosed)) {
					int intCtr2 = 0;
					for (intCtr2 = 0; intCtr2 < Main.ENTITY_TYPES.length; intCtr2++) {
						if (entity_result.GetEntityTypeName().equals(Main.ENTITY_TYPES[intCtr2])) {
							break;
						}
					}
					
					BigDecimal Total = BigDecimal.ZERO;
					BigDecimal Debits = BigDecimal.ZERO;
					BigDecimal Credits = BigDecimal.ZERO;
					BigDecimal CurrentValue = BigDecimal.ZERO;
					BigDecimal OpeningBalance = BigDecimal.ZERO;
					BigDecimal InterestAmount = BigDecimal.ZERO;
					BigDecimal InsurancePremium = BigDecimal.ZERO;
					Float SharesHeld = (float) 0;
					
					switch(intCtr2) {
					 	case 0:	// Savings Account
					 		Debits = Main.database_handler.GetDebits(intArrResults[intCtr]);
							Credits = Main.database_handler.GetCredits(intArrResults[intCtr]);
							Total = Debits.subtract(Credits);
					 		break;
					 	case 1:	// Credit Card
					 		Debits = Main.database_handler.GetDebits(intArrResults[intCtr]);
							Credits = Main.database_handler.GetCredits(intArrResults[intCtr]);
							Total = Debits.subtract(Credits);
					 		break;
					 	case 2:	// Loan Account
					 		CurrentValue = Main.database_handler.GetLoanFromEntityID(intArrResults[intCtr]).GetCurrentValue();
							Total = CurrentValue.negate();
					 		break;
					 	case 3:	// Term Deposit
					 		OpeningBalance = Main.database_handler.GetTermDepositFromEntityID(intArrResults[intCtr]).GetOpeningBalance();
							InterestAmount = Main.database_handler.GetTermDepositFromEntityID(intArrResults[intCtr]).GetInterestAmount();
							Total = OpeningBalance.add(InterestAmount);
					 		break;
					 	case 4:	// Superannuation
					 		Debits = Main.database_handler.GetDebits(intArrResults[intCtr]);
							Credits = Main.database_handler.GetCredits(intArrResults[intCtr]);
							Total = Debits.subtract(Credits);
					 		break;
					 	case 5:	// Property
					 		CurrentValue = Main.database_handler.GetPropertyFromEntityID(intArrResults[intCtr]).GetCurrentValue();
							Total = CurrentValue;
					 		break;
					 	case 6:	// Shares/Managed Funds
					 		ShareTransaction[] AllShareTransactions = Main.database_handler.GetShareTransactions(intArrResults[intCtr]);
					 		if (AllShareTransactions != null) {
					 			for (int intCtr3 = 0; intCtr3 < AllShareTransactions.length; intCtr3++) {
						 			if (AllShareTransactions[intCtr3].GetTransactionSubtype().equals(Main.SHR_TXN_TYPES[0])) {
						 				// Buys
						 				SharesHeld = SharesHeld + AllShareTransactions[intCtr3].GetNumberShares();
						 			} else if (AllShareTransactions[intCtr3].GetTransactionSubtype().equals(Main.SHR_TXN_TYPES[1])) {
						 				// Sells
						 				SharesHeld = SharesHeld - AllShareTransactions[intCtr3].GetNumberShares();
						 			} else if (AllShareTransactions[intCtr3].GetTransactionSubtype().equals(Main.SHR_TXN_TYPES[3])) {
						 				// DRPs
						 				// New Shares = (Total Dividends - Brokerage Charges - Other Charges)/Share Price
						 				BigDecimal TotalDividends = BigDecimal.valueOf(AllShareTransactions[intCtr3].GetNumberShares());
										TotalDividends = TotalDividends.multiply(AllShareTransactions[intCtr3].GetDividendAmount());
										BigDecimal NewShares = TotalDividends.subtract(AllShareTransactions[intCtr3].GetBrokerageCharges());
										NewShares = NewShares.subtract(AllShareTransactions[intCtr3].GetOtherCharges());
										NewShares = NewShares.divide(AllShareTransactions[intCtr3].GetSharePrice(), 12, RoundingMode.HALF_EVEN);
										SharesHeld = SharesHeld + NewShares.floatValue();
						 			}
						 		}
					 			
					 			BigDecimal SharePrice = Main.database_handler.GetShareFromEntityID(intArrResults[intCtr]).GetCurrentUnitValue();
						 		Total = SharePrice.multiply(BigDecimal.valueOf(SharesHeld)).setScale(2, RoundingMode.HALF_EVEN);
					 		} else {
					 			Total = BigDecimal.ZERO;
					 		}
					 		break;
					 	case 7:	// Insurance
					 		InsurancePremium = Main.database_handler.GetInsuranceFromEntityID(intArrResults[intCtr]).GetPremium();
							Total = InsurancePremium.negate();
					 		break;
					}
										
					strCurVal = MoneyFormatter.format(Total);
					EntityAmounts[intCtr2] = EntityAmounts[intCtr2].add(Total);
				} else {
					// If the entity is closed, report the current value as $0
					strCurVal = MoneyFormatter.format(BigDecimal.ZERO);
				}
				
				// If there are multiple holders of a single entity, display duplicates of the entity for each holder
				String[] EntityHolders = Main.database_handler.GetEntityFromID(intArrResults[intCtr]).GetHolderNames();
				
				if (EntityHolders.length > 0) {
					for (int intCtr2 = 0; intCtr2 < EntityHolders.length; intCtr2++) {
						String strHolder = EntityHolders[intCtr2];
						if (Holder.equals("All")) {
							// Add result to search result list
							SearchResults.add(new DisplayEntity(
									strHolder,
									entity_result.GetEntityTypeName(),
									entity_result.GetInstitutionName(),
									entity_result.GetEntityStatusName(),
									entity_result.GetEntityNumber(),
									strCurVal,
									entity_result.GetOpenDate(),
									entity_result.GetCloseDate(),
									intArrResults[intCtr]));
						} else {
							if (strHolder.equals(Holder)) {
								SearchResults.add(new DisplayEntity(
										strHolder,
										entity_result.GetEntityTypeName(),
										entity_result.GetInstitutionName(),
										entity_result.GetEntityStatusName(),
										entity_result.GetEntityNumber(),
										strCurVal,
										entity_result.GetOpenDate(),
										entity_result.GetCloseDate(),
										intArrResults[intCtr]));
							}
						}
					}
				} else {
					MessageBox.Error("No holders could be found for entity " + intArrResults[intCtr] + "."
					+ System.lineSeparator() + "This entity will not be shown in the search results."
					+ System.lineSeparator() + "Please correct this issue within the database.", "ALR Search");
				}
			}
			
			// Display Search Results
			DisplayResults();
		} catch (SQLException e) {
			MessageBox.Error("A database related error occured whilst processing the search results."
			+ System.lineSeparator() + "ALR will now exit.", "ALR Search");
			EntityAmounts = null;
			System.exit(1);
		}
		return EntityAmounts;
	}
	
	private void DisplayResults () {
		// Sort the entities
		Collections.sort(SearchResults);
		
		// Clear the results table
		tblModel.setRowCount(0);
		
		// Display the entities within the table
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		for (int intCtr = 0; intCtr < SearchResults.size(); intCtr++) {
			// Avoid a null pointer exception by checking if the closing date is null
			String CloseDate;
			if (SearchResults.get(intCtr).GetCloseDate() == null) {
				CloseDate = "N/A";
			} else {
				CloseDate = dateFormat.format(SearchResults.get(intCtr).GetCloseDate());
			}
			
			// Add row to table
			tblModel.addRow(new Object[]{
					SearchResults.get(intCtr).GetHolder(),
					SearchResults.get(intCtr).GetEntityType(),
					SearchResults.get(intCtr).GetInstitutionName(),
					SearchResults.get(intCtr).GetEntityStatus(),
					SearchResults.get(intCtr).GetEntityNumber(),
					SearchResults.get(intCtr).GetCurrentValue(),
					dateFormat.format(SearchResults.get(intCtr).GetOpenDate()),
					CloseDate,
					SearchResults.get(intCtr).GetEntityID()});
		}
		
		// Notify table of changes to model
		tblModel.fireTableDataChanged();
		
		// Update Row Count
		UpdateTableCount(SearchResults.size());
	}
	
	private void UpdateGraph(BigDecimal[] EntityAmounts) {
		DecimalFormat MoneyFormatter = new DecimalFormat("$#,##0.00;$-#,##0.00");
		BigDecimal TotalAsset = BigDecimal.ZERO;
		BigDecimal TotalLiab = BigDecimal.ZERO;
		
		Border border_assport = BorderFactory.createTitledBorder("Asset Portfolio");
		Border border_liabport = BorderFactory.createTitledBorder("Liability Portfolio");
		
		for (int intctr = 0; intctr < Main.ENTITY_TYPES.length; intctr++) {
			if (!(Main.ENTITY_TYPES[intctr].equals(Main.ENTITY_TYPES[1]) || Main.ENTITY_TYPES[intctr].equals(Main.ENTITY_TYPES[2]) || Main.ENTITY_TYPES[intctr].equals(Main.ENTITY_TYPES[7]))) {
				// If not credit card, loan or insurance
				TotalAsset = TotalAsset.add(EntityAmounts[intctr]);
			} else {
				// If credit card, loan or insurance
				TotalLiab = TotalLiab.add(EntityAmounts[intctr]);
			}
		}
		
		for (int intctr = 0; intctr < Main.ENTITY_TYPES.length; intctr++) {
			if (!(Main.ENTITY_TYPES[intctr].equals(Main.ENTITY_TYPES[1]) || Main.ENTITY_TYPES[intctr].equals(Main.ENTITY_TYPES[2]) || Main.ENTITY_TYPES[intctr].equals(Main.ENTITY_TYPES[7]))) {
				// If not credit card, loan or insurance
				chartAssport.removeSeries(Main.ENTITY_TYPES[intctr]);
				if (EntityAmounts[intctr].compareTo(BigDecimal.ZERO) != 0) {
					PieSeries Assets = chartAssport.addSeries(Main.ENTITY_TYPES[intctr], EntityAmounts[intctr]);
					Assets.setToolTip(Main.ENTITY_TYPES[intctr] + " - " +  MoneyFormatter.format(EntityAmounts[intctr]) + " (" + EntityAmounts[intctr].multiply(new BigDecimal(100)).divide(TotalAsset, 1, RoundingMode.HALF_UP) + "%)");
				}
			} else {
				// If credit card, loan or insurance
				chartLiabport.removeSeries(Main.ENTITY_TYPES[intctr]);
				if (EntityAmounts[intctr].compareTo(BigDecimal.ZERO) != 0) {
					PieSeries Liabilities = chartLiabport.addSeries(Main.ENTITY_TYPES[intctr], EntityAmounts[intctr].abs());
					Liabilities.setToolTip(Main.ENTITY_TYPES[intctr] + " - " +  MoneyFormatter.format(EntityAmounts[intctr].abs()) + " (" + EntityAmounts[intctr].abs().multiply(new BigDecimal(100)).divide(TotalLiab.abs(), 1, RoundingMode.HALF_UP) + "%)");
				}
			}
		}
		
		panel_assport = new XChartPanel<PieChart>(chartAssport);
		panel_assport.setBorder(border_assport);
		panel_assport.setBounds(757, 6, 375, 274);
		panel_liabport = new XChartPanel<PieChart>(chartLiabport);
		panel_liabport.setBorder(border_liabport);
		panel_liabport.setBounds(757, 285, 375, 279);
		
		contentPane.revalidate();
		contentPane.repaint();
	}
	
	private void OpenRecord() {
		int intSelRow = tblSearch.getSelectedRow();
		if (intSelRow >= 0) {
			String strEntityID = SearchResults.get(intSelRow).GetEntityID();
			// Check if the entity is already open
			if (!OpenedEntities.contains(strEntityID)) {
				OpenedEntities.add(strEntityID);
				frmEntity frmEntity = new frmEntity(strEntityID);
				frmEntity.addWindowListener(new WindowAdapter() { 
				    @Override public void windowClosed(WindowEvent e) {				    	
				    	/* Refresh Search Results:
						 * Refreshing the search results ensures there are no exceptions resulting from
						 * the user attempting to interact with an entity listed within the results table
						 * that was deleted or modified. NOTE: Once a row is selected within the table,
						 * it can only be unselected by selecting another row (thus no need to check for
						 * row index < 0)*/
				    	
				    	int NewRowIndex = -1;
				    	
				    	// Store the entity ID corresponding to the currently selected row
				    	String strID = tblSearch.getModel().getValueAt(tblSearch.getSelectedRow(), tblModel.findColumn("ID")).toString();
				    	
				    	// Store the holder name corresponding to the currently selected row
				    	String strHolder = tblSearch.getModel().getValueAt(tblSearch.getSelectedRow(), tblModel.findColumn("Holder")).toString();
				    	
				    	// Refresh the search results
				    	Search(comboBoxes.get(0).getSelectedItem().toString(),
				    			comboBoxes.get(1).getSelectedItem().toString(),
				    			comboBoxes.get(2).getSelectedItem().toString(),
				    			comboBoxes.get(3).getSelectedItem().toString());

		    			// Check all the rows for a matching entity
		    			for (int intCtr = tblModel.getRowCount() - 1; intCtr >= 0; --intCtr) {
		    				/* We must check both the holder name and entity ID as duplicates of a
		    				 * single entity are listed under multiple holder names within the table
		    				 * if they contain multiple holders and 'All' holders are selected in the
		    				 * search options */
			    			if ((tblModel.getValueAt(intCtr, tblModel.findColumn("ID")).equals(strID)) && (tblModel.getValueAt(intCtr, tblModel.findColumn("Holder")).equals(strHolder))) {
			    				NewRowIndex = intCtr;
			    				break;
			    			}
			    		}
		    			
		    			if (NewRowIndex >= 0) {
		    				// If a matching row was found
		    				tblSearch.getSelectionModel().addSelectionInterval(NewRowIndex, NewRowIndex);
		    			} else {
		    				// If a matching row was not found
		    				tblSearch.getSelectionModel().addSelectionInterval(0, 0);
		    			}
				    }
				});
				frmEntity.setVisible(true);
			} else {
				MessageBox.Information("This entity is already open.", "Open Entity");
			}
		} else {
			MessageBox.Information("Please select an entity from the table to open.", "Open Entity");
		}
	}
	
	private void OpenNewEntityWindow(String strType) {
		frmNewEntity = new frmNewEntity(strType);
		frmNewEntity.addWindowListener(new WindowAdapter() { 
		    @Override public void windowClosed(WindowEvent e) { 
		    	Main.frmMain.setVisible(true);
		    }
		});
		frmNewEntity.setVisible(true);
		this.setVisible(false);
	}
	
	private void OpenInstWindow() {
		frmInst = new frmInst();
		frmInst.addWindowListener(new WindowAdapter() { 
		    @Override public void windowClosed(WindowEvent e) { 
		    	RefreshComboboxes();
		    	Main.frmMain.setVisible(true);
		    }
		});
		frmInst.setVisible(true);
		this.setVisible(false);
	}
	
	private void OpenHolderWindow() {
		frmHolder = new frmHolder();
		frmHolder.addWindowListener(new WindowAdapter() { 
		    @Override public void windowClosed(WindowEvent e) {
		    	RefreshComboboxes();
		    	Main.frmMain.setVisible(true);
		    }
		});
		frmHolder.setVisible(true);
		this.setVisible(false);
	}
	
	private void OpenAboutWindow() {
		if (!frmAboutOpen) {
			frmAbout = new frmAbout();
			frmAbout.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			    	frmAboutOpen = false;
			    }
			});
			frmAbout.setVisible(true);
			frmAboutOpen = true;
		} else {
			frmAbout.requestFocus();
		}
	}
	
	// Terminate the connection with the database when the application is closed
	private void TermConnection() {
		try {
			Main.database_handler.Terminate();
		} catch (SQLException e) {
			MessageBox.Error("An error occurred whilst terminating the connection with the database. ALR will now exit.", "ALR");
			System.exit(1);
		}
	}
	
	// Turn the progress bar on and off
	private void ProgBarrHandler(Boolean boolOnOff) {
		EventQueue.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        	progSearch.setIndeterminate(boolOnOff);
	        }
	    });
	}
	
	// Update the label displaying the total number of results found
	private void UpdateTotal(int intResults) {
		lblNumEntities.setText("Total # of Entities Found: " + Integer.toString(intResults));
	}
	
	// Update the label displaying the total value of current entities within the search results table
	private void UpdateTotalValue(BigDecimal Value) {
		DecimalFormat MoneyFormatter = new DecimalFormat("$#,##0.00;$-#,##0.00");
		lblValue.setText("Total Value of Current Entities: " + MoneyFormatter.format(Value));
	}
	
	// Update the tooltip displaying the total number of rows within the search results table
	private void UpdateTableCount(int intCount) {
		tblSearch.setToolTipText(Integer.toString(intCount) + " Rows.");
	}
	
	public frmMain() {	
		// Prepare the JFrame
		setTitle("Asset Liability Repository");
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmMain.class.getResource("/resources/graphics/Icon.png")));
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	// Close the connection with the MariaDB Database
		    	TermConnection();
		    }
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setPreferredSize(new Dimension(1138, 588));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// MENU BAR
		JMenuBar menuBar;
		JMenu File_menu, View_menu, Help_menu, NewEntity;
		JMenuItem ExitItem, InstItem, HoldItem, AboutItem;
		JMenuItem[] NewTypes;
		
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(1400,20));
		
		File_menu = new JMenu("File");
		NewEntity = new JMenu("New Entity");
		ExitItem = new JMenuItem("Exit");
		ExitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TermConnection();
				System.exit(0); 
			}
		});
		
		View_menu = new JMenu("View");
		InstItem = new JMenuItem("Institutional Details");
		InstItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenInstWindow();
			}
		});
		HoldItem = new JMenuItem("Holder Details");
		HoldItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenHolderWindow();
			}
		});
		
		Help_menu = new JMenu("Help");
		AboutItem = new JMenuItem("About");
		AboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenAboutWindow();
			}
		});
		
		menuBar.add(File_menu);
		menuBar.add(View_menu);
		menuBar.add(Help_menu);
		File_menu.add(NewEntity);
		File_menu.add(ExitItem);
		View_menu.add(InstItem);
		View_menu.add(HoldItem);
		Help_menu.add(AboutItem);
		
		try {
			String AllEntityTypes[] = Main.database_handler.GetAllEntityTypes();
			Arrays.sort(AllEntityTypes);
			NewTypes = new JMenuItem[AllEntityTypes.length];
			for (int intctr = 0; intctr < AllEntityTypes.length; intctr++) {
				NewTypes[intctr] = new JMenuItem(AllEntityTypes[intctr]);
				NewTypes[intctr].putClientProperty("Entity Type", AllEntityTypes[intctr]);
				NewTypes[intctr].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						OpenNewEntityWindow(((JMenuItem)e.getSource()).getClientProperty("Entity Type").toString());
					}
				});
				NewEntity.add(NewTypes[intctr]);
			}
		} catch (SQLException e1) {
			MessageBox.Error("There was a database related error populating the 'New Entity' menu. ALR will now exit.", "ALR");
			System.exit(1);
		}
		
		setJMenuBar(menuBar);
		
		// GRAPHS
		// chartAssport
		chartAssport = new PieChartBuilder().width(200).height(400).title("").build();
		chartAssport.getStyler().setLegendPosition(LegendPosition.InsideS);
		chartAssport.getStyler().setLegendLayout(LegendLayout.Horizontal);
		chartAssport.getStyler().setLegendVisible(false);
		chartAssport.getStyler().setChartBackgroundColor(new Color(0f,0f,0f,0f));
		chartAssport.getStyler().setPlotBackgroundColor(new Color(0f,0f,0f,0f));
		chartAssport.getStyler().setPlotBorderVisible(false);
		chartAssport.getStyler().setToolTipsEnabled(true);
		
		// chartLiabport
		chartLiabport = new PieChartBuilder().width(200).height(400).title("").build();
		chartLiabport.getStyler().setLegendPosition(LegendPosition.InsideS);
		chartLiabport.getStyler().setLegendLayout(LegendLayout.Horizontal);
		chartLiabport.getStyler().setLegendVisible(false);
		chartLiabport.getStyler().setChartBackgroundColor(new Color(0f,0f,0f,0f));
		chartLiabport.getStyler().setPlotBackgroundColor(new Color(0f,0f,0f,0f));
		chartLiabport.getStyler().setPlotBorderVisible(false);
		chartLiabport.getStyler().setToolTipsEnabled(true);
		
		// TABLES
		// Create table model and header
		String[] columnNames = {"Holder", "Entity Type", "Institution", "Status", "Entity Number", "Current Value", "Open Date", "Close Date", "ID"};
		tblModel = new DefaultTableModel(0, columnNames.length) ;
		tblModel.setColumnIdentifiers(columnNames);
		
		// Create table using table model
		tblSearch = new JTable(tblModel) {
			private static final long serialVersionUID = 4907798436414022319L;

			// Ensure cell values cannot be modified by the user
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		
		// Set table to use custom table renderer
		tblSearch.setDefaultRenderer(Object.class, new EntityTableCellRenderer(tblModel.findColumn("Status")));
		
		// Place table within a JScrollPane (allows for scrolling through results)
		JScrollPane panel_result = new JScrollPane(tblSearch);
		
		// Place JScrollPane within a JPanel
		Border border_result = BorderFactory.createTitledBorder("Results:");
		panel_result.setBorder(border_result);
		tblSearch.setFillsViewportHeight(true);
		panel_result.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_result.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_result.setBounds(6, 242, 739, 275);
		tblSearch.setBounds(0, 0, 390, 270);
		contentPane.add(panel_result);
		
		// PANELS
		// Search Panel
		Border border_look = BorderFactory.createTitledBorder("Look in");
		JPanel panel_look = new JPanel();
		panel_look.setBounds(6, 6, 739, 200);
		panel_look.setBorder(border_look);
		contentPane.add(panel_look);
		panel_look.setLayout(null);
		
		// Asset Portfolio Panel
		Border border_assport = BorderFactory.createTitledBorder("Asset Portfolio");
		panel_assport = new XChartPanel<PieChart>(chartAssport);
		panel_assport.setBorder(border_assport);
		panel_assport.setBounds(757, 6, 375, 279);
		contentPane.add(panel_assport);
		
		// Liability Portfolio Panel
		Border border_liabport = BorderFactory.createTitledBorder("Liability Portfolio");
		panel_liabport = new XChartPanel<PieChart>(chartLiabport);
		panel_liabport.setBorder(border_liabport);
		panel_liabport.setBounds(757, 285, 375, 279);
		contentPane.add(panel_liabport);
		
		// LABELS
		// Entity Type
		JLabel lblEntity = new JLabel("Entity Type:");
		lblEntity.setBounds(20, 30, 123, 16);
		panel_look.add(lblEntity);
		
		// Holder Name
		JLabel lblHolder = new JLabel("Holder's Name:");
		lblHolder.setBounds(20, 58, 123, 16);
		panel_look.add(lblHolder);
		
		// Institution Name
		JLabel lblInst = new JLabel("Institution Name:");
		lblInst.setBounds(20, 86, 123, 16);
		panel_look.add(lblInst);
		
		// Entity Status
		JLabel lblStatus = new JLabel("Entity Status:");
		lblStatus.setBounds(20, 114, 123, 16);
		panel_look.add(lblStatus);
		
		// Number of Entities
		lblNumEntities = new JLabel();
		lblNumEntities.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNumEntities.setHorizontalAlignment(SwingConstants.LEFT);
		lblNumEntities.setBounds(12, 520, 400, 16);
		UpdateTotal(0);
		contentPane.add(lblNumEntities);
		
		// Total Value
		lblValue = new JLabel();
		lblValue.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblValue.setHorizontalAlignment(SwingConstants.LEFT);
		lblValue.setBounds(12, 538, 400, 16);
		UpdateTotalValue(BigDecimal.ZERO);
		contentPane.add(lblValue);
		
		
		// COMBO BOXES
		// Entity
		comboBoxes.add(new JComboBox<String>());
		comboBoxes.get(0).setBounds(143, 26, 585, 27);
		panel_look.add(comboBoxes.get(0));
		
		// Holder
		comboBoxes.add(new JComboBox<String>());
		comboBoxes.get(1).setBounds(143, 54, 585, 27);
		panel_look.add(comboBoxes.get(1));
		
		// Institution
		comboBoxes.add(new JComboBox<String>());
		comboBoxes.get(2).setBounds(143, 82, 585, 27);
		panel_look.add(comboBoxes.get(2));
		
		// Status
		comboBoxes.add(new JComboBox<String>());
		comboBoxes.get(3).setBounds(143, 110, 585, 27);
		panel_look.add(comboBoxes.get(3));
		
		
		// BUTTONS
		// Search
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search(comboBoxes.get(0).getSelectedItem().toString(),
						comboBoxes.get(1).getSelectedItem().toString(),
						comboBoxes.get(2).getSelectedItem().toString(),
						comboBoxes.get(3).getSelectedItem().toString());
			}
		});
		btnSearch.setBounds(10, 140, 719, 45);
		panel_look.add(btnSearch);
		
		// Open
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenRecord();
			}
		});
		btnOpen.setBounds(574, 520, 170, 40);
		contentPane.add(btnOpen);
		
		
		// PROGRESS BARS
		// Search Progress Bar
		progSearch = new JProgressBar();
		progSearch.setBounds(6, 208, 739, 30);
		contentPane.add(progSearch);
		
		RefreshComboboxes();
	}
}