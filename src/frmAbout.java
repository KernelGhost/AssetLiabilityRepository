import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class frmAbout extends JFrame {
	private static final long serialVersionUID = 7816880762813642189L;
	
	// GUI Elements
	private JPanel contentPane;
	
	// Called on close to dispose the JFrame
	private void Close() {
		this.dispose();
	}
	
	public frmAbout() {
		// Set Title
		setTitle("About ALR");
		
		// Set Window Icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(frmAbout.class.getResource("/resources/graphics/appicon.png")));
		
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
		contentPane.setPreferredSize(new Dimension(844, 412));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Pack and center the JFrame
		pack();
		setLocationRelativeTo(null);
		
		// LABELS
		// lblBAssetLiabilityRepository
		JLabel lblBAssetLiabilityRepository = new JLabel("<html><body style=\"text-align: left;  text-justify: inter-word;\">Asset Liability Repository is a java-based port of Ravi Barar's original financial portfolio management software of the same name.<br><br>This program uses MariaDB, one of the most popular database servers in the world. You can learn more about it at https://mariadb.org/.<br><br>This program uses XChart, a light-weight and convenient library for plotting data. You can learn more about it at https://knowm.org/open-source/xchart/.<br><br> This program uses LGoodDatePicker, an easy to use and good looking Java Swing Date Picker. You can learn more about it at https://github.com/LGoodDatePicker/LGoodDatePicker.<br><br> This program uses GraphStream, a Java library for the modeling and analysis of dynamic graphs. You can learn more about it at https://graphstream-project.org/.<br><br>This program uses FlatLaf, a modern open-source cross-platform Look and Feel for Java Swing desktop applications. You can learn more about it at https://github.com/JFormDesigner/FlatLaf.</body></html></body></html>");
		lblBAssetLiabilityRepository.setHorizontalAlignment(SwingConstants.LEFT);
		lblBAssetLiabilityRepository.setVerticalAlignment(SwingConstants.TOP);
		lblBAssetLiabilityRepository.setBounds(276, 92, 558, 314);
		contentPane.add(lblBAssetLiabilityRepository);
		
		// lblHeading
		JLabel lblHeading = new JLabel("Asset Liability Repository");
		lblHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeading.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		lblHeading.setBounds(260, 6, 456, 48);
		contentPane.add(lblHeading);
		
		// lblVersion
		JLabel lblVersion = new JLabel("Version 2.0");
		lblVersion.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblVersion.setBounds(276, 46, 143, 25);
		contentPane.add(lblVersion);
		
		// lblAuthor
		JLabel lblAuthor = new JLabel("By Rohan Barar");
		lblAuthor.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblAuthor.setBounds(276, 66, 143, 25);
		contentPane.add(lblAuthor);
		
		// IMAGES
		// AppIcon
		JLabel lblIcon = new JLabel("");
		try {
			lblIcon.setBounds(10, 82, 256, 256);
			BufferedImage imgIcon;
			imgIcon = ImageIO.read(getClass().getResource("/resources/graphics/icon.bmp"));
			Image imgIconScaled = imgIcon.getScaledInstance(lblIcon.getWidth(), lblIcon.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon icoIcon = new ImageIcon(imgIconScaled);
			lblIcon.setIcon(icoIcon);
			contentPane.add(lblIcon);
		} catch (IOException | IllegalArgumentException e) {}
	}
}