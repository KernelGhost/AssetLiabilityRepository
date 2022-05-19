import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MessageBox {
	public static void Error(String strMessage, String strTitle) {
		JOptionPane.showMessageDialog(new JFrame(), strMessage, strTitle, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void Warning(String strMessage, String strTitle) {
		JOptionPane.showMessageDialog(new JFrame(), strMessage, strTitle, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void Information(String strMessage, String strTitle) {
		JOptionPane.showMessageDialog(new JFrame(), strMessage, strTitle, JOptionPane.INFORMATION_MESSAGE);
	}
}