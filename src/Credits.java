package display;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static display.Window.window;

public class Credits {
	
	public JDialog jDialog;
	
	public JPanel jpanel;
	
	public JLabel title, newLine, author, info, codeName, newLine2, newLine3;
	
	public Font titleStyle;
	
	public Credits() {
		jDialog = new JDialog();
		jDialog.setTitle("About");
		jDialog.setModal(true);
		jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jDialog.setLocationRelativeTo(window.jframe);
		jDialog.setLayout(new FlowLayout(FlowLayout.LEFT));
		jDialog.add(jpanel = new JPanel());
		init();
		jDialog.setResizable(false);
		jDialog.pack();
		jDialog.setVisible(true);
	}
	
	public void init() {
		title = new JLabel("Credits");
		newLine = new JLabel("\n");
		author = new JLabel("@SemihDev");
		newLine2 = new JLabel("\n");
		codeName = new JLabel("Codename: RGB - Alpha");
		newLine3 = new JLabel("\n");
		info = new JLabel("Created with Eclipse IDE for Java Developers");
		titleStyle = new Font("SansSerif", Font.BOLD, 24);
		
		title.setFont(titleStyle);
		
		jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
		jpanel.add(title);
		jpanel.add(newLine);
		jpanel.add(author);
		jpanel.add(newLine2);
		jpanel.add(codeName);
		jpanel.add(newLine3);
		jpanel.add(info);
	}
}
