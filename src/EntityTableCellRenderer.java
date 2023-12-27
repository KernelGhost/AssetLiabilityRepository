import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/* This is the renderer for the entity search results table within the main window of the application.
 * This custom renderer was implemented since we desire all rows representing entities that are no
 * longer 'current' (i.e. closed) to be coloured a dark red instead of the usual white/grey
 * alternating colour scheme. */

public class EntityTableCellRenderer extends DefaultTableCellRenderer  {
	private static final long serialVersionUID = 7102195732476003815L;
	int recursiveColumn;	// Index of the 'Status' column within the table (needed to check status)
	
	public EntityTableCellRenderer(int intRecCol) {
		super();							// Invoke the parent class constructor
		this.recursiveColumn = intRecCol;	// Store the index of the status column within the table
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		String s = table.getModel().getValueAt(row, recursiveColumn).toString();	// Store status
		String p = table.getModel().getValueAt(row, column).toString();				// Store cell value/text
	    
	    this.setText(p);		// Populate the cell with the desired text
	    this.setOpaque(true);	// Set the cell background to opaque to allow for colouring
	    
	    if (s.toString().equalsIgnoreCase(Main.strClosed)) {
	    	// If the row represents a closed entity, colour it a dark red colour
	        this.setBackground(Main.colDarkRed);
	        this.setForeground(Color.WHITE);
	    }
	    else {
	    	// If the row represents a current entity, colour it using the default alternating white/grey scheme
	        this.setBackground(table.getBackground());
	        this.setForeground(Color.WHITE);
	    }
	    
	    // If a given row is selected by the user, colour it a dark blue colour
	    if (table.isRowSelected(row)) {
       		this.setBackground(Main.colDarkBlue);
       		this.setForeground(Color.WHITE);
   		}
	    
	    // Return the cell
	    return this;
   }
}