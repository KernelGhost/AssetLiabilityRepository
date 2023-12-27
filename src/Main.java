import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;

public class Main {
	// Database Details
	public static String database_domain = "localhost";			// Database domain (MariaDB database is intended to run on the local machine)
	public static Integer database_port = 3306;					// Port to connect to the MariaDB database (MariaDB database is intended to run on the local machine)
	public static String database_name = "ALR_DB";				// Name of the database within MariaDB
	
	// Database Constants
	public static final String ENTITY_TYPES[] = {"Savings Account",			// All entity types
			"Credit Card", "Loan Account", "Term Deposit",
			"Superannuation", "Property", "Shares/Managed Funds",
			"Insurance"};
	public static final String SHR_TXN_TYPES[] = {"Buy",					// All transaction types for shares/managed funds
			"Sell", "Dividend", "DRP"};
	public static final int VARCHAR_MAX_LENGTH = 255;						// Stores the maximum allowable string length that VARCHAR columns within the database can hold
	public static final int INSTCODE_MAX_LENGTH = 5;						// Stores the maximum allowable string length that the institution code columns within the database can hold
	public static final int[] HOLDERTFN_SIZE_RANGE = {8, 9};				// Stores the range of allowable string lengths for the holder TFN
	public static final String MariaDB_DATE_FORMAT = "yyyy-MM-dd";			// The format of dates returned from SQL queries to the database (default format for MariaDB)
	public static final String CE_DATE_FORMAT = "dd/MM/yyyy";				// The display format for dates in the common era
	public static final String BCE_DATE_FORMAT = "dd/MM/uuuu";				// The display format for dates after the common era
	public static final BigDecimal COMPANY_TAX_RATE = new BigDecimal(0.3);	// Company Tax Rate = 30% (in Australia)
	
	// GUI Constants
	public static String strClosed = "Closed";							// The string used to indicate a closed entity (required by EntityTableCellRenderer for main entity search result table)
	public static final Color colDarkRed = new Color(204, 0, 0);		// Custom dark red colour to be used in the entity search table and in the entity relationship tree
	public static final Color colDarkBlue = new Color(8, 84, 99);	// Custom dark blue colour to be used in the entity search table and in the entity relationship tree
	
	// Global Static Classes
	public static login_dialog dialog;					// Allows for users to login into the database
	public static frmMain frmMain;						// The main window of the application
	public static DatabaseHandler database_handler;		// The class that deals with the database
	
	public static void main(String[] args) {		
		// Set 'FlatLaf' look and feel.
		try {
		    UIManager.setLookAndFeel(new FlatCarbonIJTheme());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Could not start the application with the preferred look and feel.\nUsing system defaults.", "ALR", JOptionPane.ERROR_MESSAGE, null);
		}
		
		// Set Renderer for GraphStream (required for more advanced features such as node stroke)
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		// Show the login dialog
		try {
			dialog = new login_dialog();
			dialog.addWindowListener(new WindowAdapter() { 
			    @Override public void windowClosed(WindowEvent e) { 
			      System.exit(0);
			    }
			});
			dialog.setVisible(true);
		} catch (Exception e) {
			MessageBox.Error("There was an error displaying the login screen. ALR will now exit.", "ALR");
			System.exit(1);
		}
	}
	
	// Called by the login window once credentials are submitted by the user.
	public static void Launch(String database_url) {
		// Create an instance of the DatabaseHandler
		database_handler = new DatabaseHandler(database_url);
		
		try {
			// Connect to the database
			database_handler.Connect();
			
			// Show the main window
			Main.frmMain = new frmMain();
			Main.frmMain.setVisible(true);
			
			// Close the login dialog
			dialog.setVisible(false);
		} catch (SQLInvalidAuthorizationSpecException e) {
			MessageBox.Warning("Incorrect credentials provided. Please try again.", "ALR Login");
		} catch (SQLException e) {
			MessageBox.Warning("Unable to connect to the database." + System.lineSeparator() + "Is the database server running? Are the database name, domain and port correct?", "ALR Login");
		}
	}
}