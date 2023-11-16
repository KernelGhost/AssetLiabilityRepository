import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import CustomTypes.Institution;

import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class frmInst extends JFrame {
	private static final long serialVersionUID = -4463266297329736184L;
	private frmNewEditInst frame;					// Opened when an institution is created or edited
	private boolean NewEditInstOpen = false;		// Keeps track of the new/edit window
	private JPanel contentPane;						// frmInst JPanel
	private Institution[] Institutions;				// Array containing all institutions
	private DefaultTableModel tblModel;				// Table model driving tblInst
	private JTable tblInst;							// Table that lists all institutions
	private JLabel lblInst;							// Displays the number of institutions found
	
	// Called when JFrame opens (and on refresh events) to populate tblInst and lblInst
	private void Initialise() {
		try {
			// Get all the institutions
			Institutions = Main.database_handler.GetAllInstitutions();
			
			if (Institutions != null) {
				// Sort the institutions
				Arrays.sort(Institutions);
				
				// Add the institutions to the table
				for (int intCtr = 0; intCtr < Institutions.length; intCtr++) {
					tblModel.addRow(new Object[]{
							Institutions[intCtr].GetName(),
							Institutions[intCtr].GetID(),
							Institutions[intCtr].GetCode(),
							Institutions[intCtr].GetAddress(),
							Institutions[intCtr].GetComment()
					});
				}
				
				// Update the total counter
				lblInst.setText("Total Number of Institutions Found: " + Integer.toString(Institutions.length));
			} else {
				// Update the total counter
				lblInst.setText("Total Number of Institutions Found: 0");
			}
		} catch (SQLException e) {
			MessageBox.Error("There was a database related error populating the institutions table.", "Institutions");
		}
	}
	
	// Called when the user desires to create a new institution
	private void OpenNewInst() {
		if (!NewEditInstOpen) {
			// Passing null indicates we are creating a new institution
			frame = new frmNewEditInst(null);
			frame.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) {
			    	NewEditInstOpen = false;
			    	RefreshTable();
			    }
			});
			frame.setVisible(true);
			NewEditInstOpen = true;
		} else {
			MessageBox.Warning("Please finish modifying the institution that is currently open before attempting to create another institution.", "New Institution");
			frame.requestFocus();
		}
	}
	
	// Called when the user desires to delete an existing institution
	private void DeleteInst() {
		if (!NewEditInstOpen) {
			// Get the selected institution from the table
			int intRow = tblInst.getSelectedRow();
			if (intRow >= 0) {
				String strInstID = Institutions[intRow].GetID();
				int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected institution?",
						"Delete Institution?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.WARNING_MESSAGE);
				
				if (Response == 0) {
					try {
						// Check if entities exist under the selected institution
						if(!Main.database_handler.DoesEntityUnderInstitutionExist(strInstID)) {
							Main.database_handler.DeleteInstitution(strInstID);
							tblInst.getSelectionModel().clearSelection();
							RefreshTable();
						} else {
							// Do not delete the institution as linked entities exist
							MessageBox.Warning("The selected institution cannot be deleted as entities or services linked to it exist.", "Delete Institution");
						}
					} catch (SQLException e) {
						MessageBox.Error("A database related error occurred whilst trying to delete the selected institution.", "Delete Institution");
					}
				}
			} else {
				// No institution was selected from the table
				MessageBox.Information("Please select an institution from the table to delete.", "Delete Institution");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the institution that is currently open before attempting to delete an institution.", "Delete Institution");
			frame.requestFocus();
		}
	}
	
	// Called when the user desires to edit an existing institution
	private void EditInst() {
		if (!NewEditInstOpen) {
			// Get the selected institution from the table
			int intRow = tblInst.getSelectedRow();
			if (intRow >= 0) {
				String strInstID = Institutions[intRow].GetID();
				try {
					Institution instresult = Main.database_handler.GetInstitutionFromInstitutionID(strInstID);
					// Passing the selected institution indicates we are editing an existing institution
					frame = new frmNewEditInst(instresult);
					frame.addWindowListener(new WindowAdapter() { 
					    @Override public void windowClosed(WindowEvent e) { 
					    	NewEditInstOpen = false;
					    	RefreshTable();
					    }
					});
					frame.setVisible(true);
					NewEditInstOpen = true;
				} catch (SQLException e) {
					MessageBox.Error("There was an error opening the selected institution for editing.", "Edit Institution");
				}
			} else {
				// No institution was selected from the table
				MessageBox.Information("Please select an institution from the table to edit.", "Edit Institution");
			}
		} else {
			// The new/edit window is already open
			MessageBox.Warning("Please finish modifying the institution that is currently open before attempting to edit another institution.", "Edit Institution");
			frame.requestFocus();
		}
	}
	
	private void RefreshTable() {
		int OldIndex = tblInst.getSelectedRow();
		int NewIndex = -1;
		String strID = "";
		
		if (OldIndex >= 0) {
			// A row was previously selected by the user
			strID = tblInst.getModel().getValueAt(OldIndex, tblModel.findColumn("ID")).toString();
		}
		
		// Clear the table
		tblModel.setRowCount(0);
		
		// Repopulate the table
		Initialise();
		
		// Select the previously selected index
		if (OldIndex >= 0) {
			// If a row was previously selected
			if (OldIndex > tblInst.getRowCount() - 1) {
				// The old index is no longer valid - search for the row
				for (int intCtr = 0; intCtr < tblInst.getRowCount(); intCtr++) {
					if (strID.equals(tblInst.getValueAt(intCtr, tblModel.findColumn("ID")).toString())) {
						NewIndex = intCtr;
						break;
					}
				}
				
				if (NewIndex > 0) {
					// The old row was found
					tblInst.getSelectionModel().addSelectionInterval(NewIndex, NewIndex);
				} else {
					// The old row was not found
					tblInst.getSelectionModel().addSelectionInterval(0, 0);
				}
			} else {
				// The old index is still valid
				if (!strID.equals(tblInst.getValueAt(OldIndex, tblModel.findColumn("ID")).toString())) {
					// If the old index is no longer correct - search for the service
					for (int intCtr = 0; intCtr < tblInst.getRowCount(); intCtr++) {
						if (strID.equals(tblInst.getValueAt(intCtr, tblModel.findColumn("ID")).toString())) {
							NewIndex = intCtr;
							break;
						}
					}
					
					if (NewIndex > 0) {
						// The entity was found
						tblInst.getSelectionModel().addSelectionInterval(NewIndex, NewIndex);
					} else {
						// The entity was not found
						tblInst.getSelectionModel().addSelectionInterval(0, 0);
					}
				} else {
					// The old index is still the correct index
					tblInst.getSelectionModel().addSelectionInterval(OldIndex, OldIndex);
				}
			}
		}
	}
	
	// Called to close the JFrame
	private void Close() {
		this.dispose();
	}
	
	public frmInst() {
		setTitle("ALR - Institutions");
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmInst.class.getResource("/resources/graphics/Icon.png")));
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
		
		/*
		 * OLD CODE THAT WOULD REFRESH TABLE WHENEVER JFRAME GOT FOCUS
		 * 
		 * addWindowFocusListener(new WindowFocusListener() {
		 * 
		 * @Override public void windowGainedFocus(WindowEvent e) { RefreshTable(); }
		 * 
		 * @Override public void windowLostFocus(WindowEvent e) { } });
		 */
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setPreferredSize(new Dimension(650, 312));
		setContentPane(contentPane);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// TABLES
		// tblInst
		String[] columnNames = {"Name", "ID", "Code", "Address", "Comments"};
		tblModel = new DefaultTableModel(0, columnNames.length) ;
		tblModel.setColumnIdentifiers(columnNames);
		tblInst = new JTable(tblModel) {
			private static final long serialVersionUID = -487757318058734019L;

			// Ensure cell values cannot be modified by the user
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		JScrollPane panel_result = new JScrollPane(tblInst);
		panel_result.setBounds(6, 6, 638, 275);
		Border border_inst = BorderFactory.createTitledBorder("Institutions:");
		contentPane.setLayout(null);
		panel_result.setBorder(border_inst);
		tblInst.setFillsViewportHeight(true);
		panel_result.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_result.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tblInst.setBounds(0, 0, 390, 270);
		contentPane.add(panel_result);
		
		// LABELS
		// Institutions Label
		lblInst = new JLabel();
		lblInst.setBounds(6, 286, 400, 16);
		lblInst.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		contentPane.add(lblInst);
		
		// BUTTONS
		// Create
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenNewInst();
			}
		});
		btnCreate.setBounds(439, 279, 70, 29);
		contentPane.add(btnCreate);
		
		// Delete
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteInst();
			}
		});
		btnDelete.setBounds(573, 279, 70, 29);
		contentPane.add(btnDelete);
		
		// Edit
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditInst();
			}
		});
		btnEdit.setBounds(506, 279, 70, 29);
		contentPane.add(btnEdit);
		
		Initialise();
	}
}
