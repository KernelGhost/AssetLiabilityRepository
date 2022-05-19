import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import CustomTypes.Holder;

public class frmHolder extends JFrame {
	private static final long serialVersionUID = -1871882865531909683L;
	private frmNewEditHolder frame;
	private boolean NewEditHolderOpen = false;
	private JPanel contentPane;
	private JTable tblHolder;
	private Holder[] Holders;
	private DefaultTableModel tblModel;
	private JLabel lblHolders;
	
	// Called when JFrame opens (and on refresh events) to populate tblHolder and lblHolders
	private void Initialise() {
		try {
			// Get all the holders
			Holders = Main.database_handler.GetAllHolders();
			
			if (Holders != null) {
				// Sort the holders
				Arrays.sort(Holders);
				
				// Add the holders to the table
				for (int intCtr = 0; intCtr < Holders.length; intCtr++) {
					String newDateString;
					// If a DOB exists for a holder, add it to the table in the 'dd/mm/yyyy' format
					if (!(Holders[intCtr].GetDOB() == null)) {
						Date date = Holders[intCtr].GetDOB();
						DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
						newDateString = dateFormat.format(date);
					} else {
						// No DOB exists, so set the string to be blank
						newDateString = "";
					}
						
					// Add Holder to Table
					tblModel.addRow(new Object[]{
							Holders[intCtr].GetName(),
							Holders[intCtr].GetID(),
							newDateString,
							Holders[intCtr].GetTFN()
					});
				}
				
				// Update the total counter
				lblHolders.setText("Total Number of Holders Found: " + Integer.toString(Holders.length));
			} else {
				// Update the total counter
				lblHolders.setText("Total Number of Holders Found: 0");
			}
		} catch (SQLException e) {
			MessageBox.Error("There was a database related error populating the holders table.", "Holders");
		}
	}
	
	// Called when the user desires to create a new holder
	private void OpenNewHolder() {
		if (!NewEditHolderOpen) {
			// Passing null indicates we are creating a new holder
			frame = new frmNewEditHolder(null);
			frame.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			    	NewEditHolderOpen = false;
			    	RefreshTable();
			    }
			});
			frame.setVisible(true);
			NewEditHolderOpen = true;
		} else {
			MessageBox.Warning("Please finish modifying the holder that is currently open before attempting to create another holder.", "New Holder");
			frame.requestFocus();
		}
		
	}
	
	// Called when the user desires to delete an existing holder
	private void DeleteHolder() {
		if (!NewEditHolderOpen) {
			// Get the selected holder from the table
			int intRow = tblHolder.getSelectedRow();
			if (intRow >= 0) {
				String strHolderID = Holders[intRow].GetID();
				int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected holder?",
						"Delete Holder?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				if (Response == 0) {
					try {
						// Check if entities exist under the selected holder
						if(!Main.database_handler.DoesEntityUnderHolderExist(strHolderID)) {
							Main.database_handler.DeleteHolder(strHolderID);
							tblHolder.getSelectionModel().clearSelection();
							RefreshTable();
						} else {
							// Do not delete the holder as linked entities exist
							MessageBox.Warning("The selected holder cannot be deleted as entities linked to it exist.", "Delete Holder");
						}
					} catch (SQLException e) {
						MessageBox.Error("A database related error occurred whilst trying to delete the selected holder.", "Delete Holder");
					}
				}
			} else {
				// No holder was selected from the table
				MessageBox.Information("Please select a holder from the table to delete.", "Delete Holder");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the holder that is currently open before attempting to delete a holder.", "Delete Holder");
			frame.requestFocus();
		}
	}
	
	// Called when the user desires to edit an existing holder
	private void EditHolder() {
		if (!NewEditHolderOpen) {
			// Get the selected holder from the table
			int intRow = tblHolder.getSelectedRow();
			if (intRow >= 0) {
				String strHolderID = Holders[intRow].GetID();
				try {
					Holder holderresult = Main.database_handler.GetHolderFromHolderID(strHolderID);
					// Passing the selected holder indicates we are editing an existing holder
					frame = new frmNewEditHolder(holderresult);
					frame.addWindowListener(new WindowAdapter() { 
					    @Override public void windowClosed(WindowEvent e) { 
					    	NewEditHolderOpen = false;
					    	RefreshTable();
					    }
					});
					frame.setVisible(true);
					NewEditHolderOpen = true;
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error opening the selected holder for editing.", "Edit Holder");
				}
			} else {
				// No holder was selected from the table
				MessageBox.Information("Please select a holder from the table to edit.", "Edit Holder");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the holder that is currently open before attempting to edit another holder.", "Edit Holder");
			frame.requestFocus();
		}
	}
	
	private void RefreshTable() {
		int OldIndex = tblHolder.getSelectedRow();
		int NewIndex = -1;
		String strID = "";
		
		if (OldIndex >= 0) {
			// A row was previously selected by the user
			strID = tblHolder.getModel().getValueAt(OldIndex, tblModel.findColumn("ID")).toString();
		}
		
		// Clear the table
		tblModel.setRowCount(0);
		
		// Repopulate the table
		Initialise();
		
		// Select the previously selected index
		if (OldIndex >= 0) {
			// If a row was previously selected
			if (OldIndex > tblHolder.getRowCount() - 1) {
				// The old index is no longer valid - search for the row
				for (int intCtr = 0; intCtr < tblHolder.getRowCount(); intCtr++) {
					if (strID.equals(tblHolder.getValueAt(intCtr, tblModel.findColumn("ID")).toString())) {
						NewIndex = intCtr;
						break;
					}
				}
				
				if (NewIndex > 0) {
					// The old row was found
					tblHolder.getSelectionModel().addSelectionInterval(NewIndex, NewIndex);
				} else {
					// The old row was not found
					tblHolder.getSelectionModel().addSelectionInterval(0, 0);
				}
			} else {
				// The old index is still valid
				if (!strID.equals(tblHolder.getValueAt(OldIndex, tblModel.findColumn("ID")).toString())) {
					// If the old index is no longer correct - search for the service
					for (int intCtr = 0; intCtr < tblHolder.getRowCount(); intCtr++) {
						if (strID.equals(tblHolder.getValueAt(intCtr, tblModel.findColumn("ID")).toString())) {
							NewIndex = intCtr;
							break;
						}
					}
					
					if (NewIndex > 0) {
						// The entity was found
						tblHolder.getSelectionModel().addSelectionInterval(NewIndex, NewIndex);
					} else {
						// The entity was not found
						tblHolder.getSelectionModel().addSelectionInterval(0, 0);
					}
				} else {
					// The old index is still the correct index
					tblHolder.getSelectionModel().addSelectionInterval(OldIndex, OldIndex);
				}
			}
		}
	}
	
	// Called to close the JFrame
	private void Close() {
		this.dispose();
	}
	
	public frmHolder() {
		setTitle("ALR - Holders");
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmHolder.class.getResource("/resources/graphics/Icon.png")));
		setResizable(false);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if (frame != null) {
		    		frame.setVisible(false);
			    	frame.dispose();
		    	}
		    	Close();
		    }
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setPreferredSize(new Dimension(650, 312));
		setContentPane(contentPane);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// TABLES
		// tblHolder
		String[] columnNames = {"Name", "ID", "DOB", "TFN"};
		tblModel = new DefaultTableModel(0, columnNames.length) ;
		tblModel.setColumnIdentifiers(columnNames);
		tblHolder = new JTable(tblModel) {
			private static final long serialVersionUID = 6936768648021209361L;

			// Ensure cell values cannot be modified by the user
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		JScrollPane panel_result = new JScrollPane(tblHolder);
		panel_result.setBounds(6, 6, 638, 275);
		Border border_inst = BorderFactory.createTitledBorder("Holders:");
		contentPane.setLayout(null);
		panel_result.setBorder(border_inst);
		tblHolder.setFillsViewportHeight(true);
		panel_result.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_result.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tblHolder.setBounds(0, 0, 390, 270);
		contentPane.add(panel_result);
		
		// LABELS
		// Holders Label
		lblHolders = new JLabel();
		lblHolders.setBounds(6, 286, 400, 16);
		lblHolders.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		contentPane.add(lblHolders);
		
		// BUTTONS
		// Create
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenNewHolder();
			}
		});
		btnCreate.setBounds(439, 279, 70, 29);
		contentPane.add(btnCreate);
		
		// Delete
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteHolder();
			}
		});
		btnDelete.setBounds(573, 279, 70, 29);
		contentPane.add(btnDelete);
		
		// Edit
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditHolder();
			}
		});
		btnEdit.setBounds(506, 279, 70, 29);
		contentPane.add(btnEdit);
		
		Initialise();
	}
}