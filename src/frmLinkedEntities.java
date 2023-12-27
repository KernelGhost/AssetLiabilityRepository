import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Arrays;

import CustomTypes.Entity;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frmLinkedEntities extends JFrame {
	private static final long serialVersionUID = -4412249832700365455L;
	private String strEntityID;
	private JPanel contentPane;
	private DefaultTableModel tblModel;
	private JTable table;
	private DefaultListModel<String> ParentListModel;
	private JList<String> ParentList;
	private DefaultListModel<String> ChildListModel;
	private JList<String> ChildList;
	
	private void GetLinkedEntities() throws SQLException {
		ParentListModel = new DefaultListModel<String>();
		ChildListModel = new DefaultListModel<String>();
		
		String[] Parents = Main.database_handler.GetLinkedEntityIDFromEntityID(strEntityID);
		String[] Children = Main.database_handler.GetEntityIDFromLinkedEntityID(strEntityID);
		
		if (Parents != null) {
			Arrays.sort(Parents);
			
			for (int intCtr = 0; intCtr < Parents.length; intCtr++) {
				ParentListModel.addElement(Parents[intCtr]);
			}
		}
		
		if (Children != null) {
			Arrays.sort(Children);
			
			for (int intCtr = 0; intCtr < Children.length; intCtr++) {
				ChildListModel.addElement(Children[intCtr]);
			}
		}
	}
	
	private void GetAllEntities() throws SQLException {
		String[] Entities = Main.database_handler.Search("All", "All", "All", "All", "", "", "", "");
		
		for (int intCtr = 0; intCtr < Entities.length; intCtr++) {
			Entity new_entity = Main.database_handler.GetEntityFromID(Entities[intCtr]);
			tblModel.addRow(new Object[]{
				new_entity.GetEntityID(),
				new_entity.GetEntityTypeName(),
				new_entity.GetInstitutionName(),
				new_entity.GetEntityStatusName(),
				new_entity.GetEntityNumber()
			});
		}
		
		table.setToolTipText(Integer.toString(table.getRowCount()) + " Rows.");
		tblModel.fireTableDataChanged();
	}
	
	private void AddParent() {
		int row = table.getSelectedRow();	// -1 if nothing selected
		if (row >= 0) {
			String ParentID = table.getModel().getValueAt(row, tblModel.findColumn("ID")).toString();
			if (ParentListModel.contains(ParentID)) {
				MessageBox.Warning("The selected entity is already a parent.", "Add Parent");
			} else if (ChildListModel.contains(ParentID)) {
				MessageBox.Warning("The selected entity is already a child." + System.lineSeparator() + "Entities cannot simultaneously be both parents and children of an entity.", "Add Parent");
			} else if (ParentID.equals(strEntityID)) {
				MessageBox.Warning("You cannot add an entity as its own parent.", "Add Parent");
			} else {
				try {
					Main.database_handler.NewLinkedEntity(strEntityID, ParentID);
					ParentListModel.addElement(ParentID);
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error adding the selected entity as a parent.", "Add Parent");
				}
			}
		} else {
			MessageBox.Information("Please select an entity from the table to add as a parent.", "Add Parent");
		}
	}
	
	private void AddChild() {
		int row = table.getSelectedRow();	// -1 if nothing selected
		if (row >= 0) {
			String ChildID = table.getModel().getValueAt(row, tblModel.findColumn("ID")).toString();
			if (ChildListModel.contains(ChildID)) {
				MessageBox.Warning("The selected entity is already a child.", "Add Child");
			} else if (ParentListModel.contains(ChildID)) {
				MessageBox.Warning("The selected entity is already a parent." + System.lineSeparator() + "Entities cannot simultaneously be both parents and children of an entity.", "Add Child");
			} else if (ChildID.equals(strEntityID)) {
				MessageBox.Warning("You cannot add an entity as its own child.", "Add Child");
			} else {
				try {
					Main.database_handler.NewLinkedEntity(ChildID, strEntityID);
					ChildListModel.addElement(ChildID);
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error adding the selected entity as a child.", "Add Child");
				}
			}
		} else {
			MessageBox.Information("Please select an entity from the table to add as a child.", "Add Child");
		}
	}
	
	private void DeleteParent() {
		String selected = ParentList.getSelectedValue();
		if (selected != null) {
			int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this parent?",
					"Delete Parent?",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.WARNING_MESSAGE);
			
			if (Response == 0) {
				try {
					Main.database_handler.DeleteLinkedEntity(strEntityID, selected);
					ParentListModel.remove(ParentList.getSelectedIndex());
					ParentList.clearSelection();
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error deleting the selected parent.", "Delete Parent");
				}
			}
		} else {
			MessageBox.Information("Please select a parent entity to delete.", "Delete Parent");
		}
	}
	
	private void DeleteChild() {
		String selected = ChildList.getSelectedValue();
		if (selected != null) {
			int Response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this child?",
					"Delete Child?",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.WARNING_MESSAGE);
			
			if (Response == 0) {
				try {
					Main.database_handler.DeleteLinkedEntity(selected, strEntityID);
					ChildListModel.remove(ChildList.getSelectedIndex());
					ChildList.clearSelection();
				} catch (SQLException e) {
					MessageBox.Error("There was a database related error deleting the selected child.", "Delete Child");
					e.printStackTrace();
				}
			}
		} else {
			MessageBox.Information("Please select a child entity to delete.", "Delete Child");
		}
	}
	
	// Called on close to dispose the JFrame
	private void Close() {
		this.dispose();
	}
	
	public frmLinkedEntities(String EntityID) {
		this.strEntityID = EntityID;
		
		// Set Title
		setTitle("Linked Entities [" + EntityID + "]");
		
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
		contentPane.setPreferredSize(new Dimension(480, 374));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		try {
			GetLinkedEntities();
		} catch (SQLException e) {
			MessageBox.Error("There was a database related error finding the entities linked to the chosen entity.", "Linked Entities");
			Close();
		}
		
		JPanel ParentEntities = new JPanel();
		Border border_parententities = BorderFactory.createTitledBorder("Parent Entities:");
		ParentEntities.setBorder(border_parententities);
		ParentEntities.setBounds(6, 6, 228, 169);
		contentPane.add(ParentEntities);
		ParentEntities.setLayout(null);
		
		ParentList = new JList<String>(ParentListModel);
		ParentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane ParentListPanel = new JScrollPane(ParentList);
		ParentListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ParentListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ParentListPanel.setBounds(12, 22, 204, 100);
		ParentEntities.add(ParentListPanel);
		
		JButton btnAddParent = new JButton("Add");
		btnAddParent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddParent();
			}
		});
		btnAddParent.setBounds(12, 124, 94, 34);
		ParentEntities.add(btnAddParent);
		
		JButton btnDeleteParent = new JButton("Delete");
		btnDeleteParent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteParent();
			}
		});
		btnDeleteParent.setBounds(122, 124, 94, 34);
		ParentEntities.add(btnDeleteParent);
		
		JPanel ChildEntities = new JPanel();
		Border border_childentities = BorderFactory.createTitledBorder("Child Entities:");
		ChildEntities.setBorder(border_childentities);
		ChildEntities.setBounds(246, 6, 228, 169);
		contentPane.add(ChildEntities);
		ChildEntities.setLayout(null);
		
		ChildList = new JList<String>(ChildListModel);
		ChildList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane ChildListPanel = new JScrollPane(ChildList);
		ChildListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ChildListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ChildListPanel.setBounds(12, 22, 204, 100);
		ChildEntities.add(ChildListPanel);
		
		JButton btnAddChild = new JButton("Add");
		btnAddChild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddChild();
			}
		});
		btnAddChild.setBounds(12, 124, 94, 34);
		ChildEntities.add(btnAddChild);
		
		JButton btnDeleteChild = new JButton("Delete");
		btnDeleteChild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteChild();
			}
		});
		btnDeleteChild.setBounds(122, 124, 94, 34);
		ChildEntities.add(btnDeleteChild);
		
		String[] columnNames = {"ID", "Entity Type", "Institution", "Status", "Entity Number"};
		tblModel = new DefaultTableModel(0, columnNames.length) ;
		tblModel.setColumnIdentifiers(columnNames);
		table = new JTable(tblModel) {
			private static final long serialVersionUID = -841816206959587509L;
			
			// Ensure cell values cannot be modified by the user
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
		};
		
		// Set table to use custom table renderer
		table.setDefaultRenderer(Object.class, new EntityTableCellRenderer(tblModel.findColumn("Status")));
		
		// Place table within a JScrollPane (allows for scrolling through results)
		JScrollPane panel_entities = new JScrollPane(table);
		
		// Place JScrollPane within a JPanel
		Border border_entities = BorderFactory.createTitledBorder("All Entities:");
		panel_entities.setBorder(border_entities);
		table.setFillsViewportHeight(true);
		panel_entities.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_entities.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_entities.setBounds(6, 187, 468, 181);
		contentPane.add(panel_entities);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 178, 468, 12);
		contentPane.add(separator);
		
		try {
			GetAllEntities();
		} catch (SQLException e) {
			MessageBox.Error("There was a database related error populating the table containing all entities.", "Linked Entities");
			Close();
		}
	}
}