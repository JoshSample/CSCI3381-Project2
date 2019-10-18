package project2;
/* Josh Sample
   Project 2
   10/21/2019 */

import java.awt.event.*;
import javax.swing.JFrame;


public class MainFrame {

	public static void main(String[] args) {
		// Sets frame
		JFrame frame = new JFrame("ACE Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainPanel panel = new MainPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		// Closes with writing to file
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				panel.doClose();
				System.exit(0);
			}
		});
	}

}