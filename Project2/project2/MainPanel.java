package project2;
// Josh Sample

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import project1.*;



public class MainPanel extends JPanel {
	private AceDataManager myData;
	private String[] aceList;
	
	public MainPanel() {
		myData = new AceDataManager("./data.txt" , "./project1/data.txt");
		aceList = myData.getAceList();
		new JPanel();
		setPreferredSize(new Dimension(800, 500));
		setBackground(new Color(245, 245, 245));
		setLayout(null);
		
		// Image from https://socialworksynergy.org/2014/02/26/aces-adverse-childhood-experiences-basics/
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MainPanel.class.getResource("/project2/ace-pyramid-cdc.gif")));
		label.setBounds(425, 259, 375, 241);
		add(label);
		
		JLabel lblAceDataManager = new JLabel("ACE Data Manager");
		lblAceDataManager.setFont(new Font("Dialog", Font.BOLD, 30));
		lblAceDataManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblAceDataManager.setBounds(55, 13, 664, 85);
		add(lblAceDataManager);
		
		JLabel lblWhatWouldYou = new JLabel("What would you like to do?");
		lblWhatWouldYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWhatWouldYou.setFont(new Font("Dialog", Font.BOLD, 15));
		lblWhatWouldYou.setBounds(158, 121, 227, 36);
		add(lblWhatWouldYou);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Dialog", Font.PLAIN, 15));
		comboBox.setBounds(407, 121, 227, 36);
		comboBox.addItem("Add Patient");
		comboBox.addItem("Remove Patient");
		comboBox.addItem("View Patient Data");
		add(comboBox);
		
		JButton btnGo = new JButton("Go");
		btnGo.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnGo.setBounds(339, 170, 97, 25);
		add(btnGo);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// This Adds a Patient
				if (comboBox.getSelectedItem().equals("Add Patient")) {
					boolean idCheck = true;
					String id;
					String name;
					do {
						id = JOptionPane.showInputDialog(btnGo, "Add Patient ID", "Patient ID", JOptionPane.PLAIN_MESSAGE);
						if (myData.getPatient(id) != null)
							JOptionPane.showMessageDialog(btnGo, "ID Exists, choose different ID");
						else if (Integer.parseInt(id) == JOptionPane.CANCEL_OPTION);
						else
							idCheck = false;
					} while(idCheck);
					name = JOptionPane.showInputDialog(btnGo, "Add Patient Name", "Patient Name", JOptionPane.PLAIN_MESSAGE);
					if (Integer.parseInt(name) == JOptionPane.CANCEL_OPTION);
					else {
						Patient p = new Patient(id, name);
						int option;
						String ace;
						do {
							ace = (String) JOptionPane.showInputDialog(btnGo, "Add ACEs", "ACEs", JOptionPane.PLAIN_MESSAGE, null, aceList, aceList[0]);
							p.addACE(ace);
							option = JOptionPane.showConfirmDialog(btnGo, "Add Another?", "Input", JOptionPane.YES_NO_OPTION);
						} while(option != JOptionPane.NO_OPTION);
						myData.addPatient(p);
						doClose();
						JOptionPane.showMessageDialog(btnGo, "Added Patient.");
					}
				}
				// Remove Patient
				else if (comboBox.getSelectedItem().equals("Remove Patient")) {
					String id = JOptionPane.showInputDialog(btnGo, "Enter Patient ID", "Remove", JOptionPane.PLAIN_MESSAGE);
					if (myData.getPatient(id) == null)
						JOptionPane.showMessageDialog(btnGo, "Couldn't remove patient, returning home.");
					else if (Integer.parseInt(id) == JOptionPane.CANCEL_OPTION);
					else {
						myData.removePatient(id);
						JOptionPane.showMessageDialog(btnGo, "Removed Patient");
						doClose();
					}	
				}
				// Check patient data
				else {
					String id = JOptionPane.showInputDialog(btnGo, "Enter Patient ID", "Find", JOptionPane.PLAIN_MESSAGE);
					if (myData.getPatient(id) == null)
						JOptionPane.showMessageDialog(btnGo, "No patient has that ID, returning home.");
					else {
						Patient p = (Patient) myData.getPatient(id);
						JPanel panel = new JPanel();
						JRadioButton add = new JRadioButton("Add ACE");
						JRadioButton view = new JRadioButton("View Patient Data");
						JRadioButton risk = new JRadioButton("Get Risk Factors");
						ButtonGroup bGroup = new ButtonGroup();
						bGroup.add(add);
						bGroup.add(view);
						bGroup.add(risk);
						panel.add(add);
						panel.add(view);
						panel.add(risk);
						int n = JOptionPane.showOptionDialog(btnGo, panel,
						    "Selection", JOptionPane.OK_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE, null, null, null);
						// Performs add
						if (add.isSelected()) {
							if (n == JOptionPane.CANCEL_OPTION);
							else {
								String ace;
								int option;
								do {
									ace = (String) JOptionPane.showInputDialog(btnGo, "Add ACEs", "ACEs", JOptionPane.PLAIN_MESSAGE, null, aceList, aceList[0]);
									p.addACE(ace);
									option = JOptionPane.showConfirmDialog(btnGo, "Add Another?", "Input", JOptionPane.YES_NO_OPTION);
								} while(option != JOptionPane.NO_OPTION);
								myData.addPatient(p);
								doClose();
							}
						}
						// Performs view
						else if (view.isSelected()) {
							if (n == JOptionPane.CANCEL_OPTION);
							else
								JOptionPane.showMessageDialog(btnGo, p, "View Patient", JOptionPane.PLAIN_MESSAGE);
						}
						// Shows risk factors
						else if (risk.isSelected()){
							if (n == JOptionPane.CANCEL_OPTION);
							else {
								ArrayList<String> results = myData.getRiskFactors(p.getACEs());
								JOptionPane.showMessageDialog(btnGo, "Patient " + p.getId() + " has these risk factors:\n" 
										+ results, "Risk Factors", JOptionPane.PLAIN_MESSAGE);
							}
						}
					}
				}
			}
		});
	}
	
	public void doClose() {
		myData.writeToFile();
	}
	
	
}
