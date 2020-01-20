import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WeatherBuddyMainPage implements ActionListener {
	JFrame fa, f1;
	JLabel la1, la2, la3;
	JTextField ta1;
	JButton ba1, ba2, ba3;
	JCheckBox c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12;
	JPanel p;
	ImageIcon img;
	ArrayList<String> cityNames = new ArrayList<>();
	ArrayList<Integer> commandCodes = new ArrayList<>();

	public WeatherBuddyMainPage() {
		fa = new JFrame("Commands");
		fa.getContentPane().setLayout(null);
		fa.getContentPane().setBackground(Color.white);

		img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/flower.jpg");
		JLabel imgL = new JLabel(img);
		imgL.setBounds(0, 0, 1500, 1000);
		la1 = new JLabel("Enter city names, separated by spaces");
		la1.setBounds(50, 150, 1300, 30);
		la1.setFont(new Font("Arial", Font.BOLD, 24));
		la1.setForeground(Color.black);
		la2 = new JLabel("Cities with spaces in names should replace spaces with \"%20\"");
		la2.setBounds(50, 200, 1200, 30);
		la2.setFont(new Font("Arial", Font.BOLD, 24));
		la2.setForeground(Color.black);
		la3 = new JLabel("Make sure they are REAL and CORRECTLY SPELLED city names:");
		la3.setBounds(50, 250, 1200, 30);
		la3.setFont(new Font("Arial", Font.BOLD, 24));
		la3.setForeground(Color.black);

		ta1 = new JTextField(10);
		ta1.setForeground(Color.blue);
		ta1.addActionListener((ActionListener) this);
		ta1.setBounds(50, 300, 700, 30);

		c0 = new JCheckBox("longitude");
		c0.setForeground(Color.black);
		c1 = new JCheckBox("latitude");
		c1.setForeground(Color.black);
		c2 = new JCheckBox("overall weather conditions");
		c2.setForeground(Color.black);
		c3 = new JCheckBox("specific weather conditions");
		c3.setForeground(Color.black);
		c4 = new JCheckBox("current temperature in Celsius");
		c4.setForeground(Color.black);
		c5 = new JCheckBox("pressure in hectopascals");
		c5.setForeground(Color.black);
		c6 = new JCheckBox("percentage humidity");
		c6.setForeground(Color.black);
		c7 = new JCheckBox("minimum temperature");
		c7.setForeground(Color.black);
		c8 = new JCheckBox("maximum temperature");
		c8.setForeground(Color.black);
		c9 = new JCheckBox("wind speed in meters / seconds");
		c9.setForeground(Color.black);
		c10 = new JCheckBox("percent cloud cover");
		c10.setForeground(Color.black);
		c11 = new JCheckBox("sunrise time");
		c11.setForeground(Color.black);
		c12 = new JCheckBox("sunset time");
		c12.setForeground(Color.black);

		p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("Check all parameters you would like to query"));
		p.add(c0);
		p.add(c1);
		p.add(c2);
		p.add(c3);
		p.add(c4);
		p.add(c5);
		p.add(c6);
		p.add(c7);
		p.add(c8);
		p.add(c9);
		p.add(c10);
		p.add(c11);
		p.add(c12);
		p.setBounds(50, 350, 600, 150);
		p.setBackground(Color.WHITE);

		ba1 = new JButton("NONE");
		ba1.setBackground(Color.cyan);
		ba1.addActionListener(this);
		ba1.setBounds(350, 550, 100, 30);

		ba2 = new JButton("ALL");
		ba2.setBackground(Color.cyan);
		ba2.addActionListener(this);
		ba2.setBounds(50, 550, 100, 30);

		ba3 = new JButton("Submit");
		ba3.setBackground(Color.cyan);
		ba3.addActionListener(this);
		ba3.setBounds(200, 550, 100, 30);

		fa.getContentPane().add(la1);
		fa.getContentPane().add(la2);
		fa.getContentPane().add(la3);
		fa.getContentPane().add(ta1);
		fa.getContentPane().add(p);
		fa.getContentPane().add(ba1);
		fa.getContentPane().add(ba2);
		fa.getContentPane().add(ba3);
		fa.getContentPane().add(imgL);

		fa.setSize(1500, 1000);
		fa.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ba1) { // none is selected
			f1 = new JFrame("Exit");
			la1 = new JLabel("Thank you for using WeatherBuddy! See you again soon! uWu");
			la1.setBounds(300, 150, 1200, 50);
			la1.setFont(new Font("Arial", Font.BOLD, 30));
			la1.setForeground(Color.black);

			img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/exit.jpg");
			JLabel imgL = new JLabel(img);
			imgL.setBounds(0, 0, 1500, 1000);
			f1.getContentPane().add(la1);
			f1.getContentPane().add(imgL);
			f1.setSize(1500, 1000);
			f1.setVisible(true);
			fa.setVisible(false);
		} else if (e.getSource() == ba2) { // all is selected

			String[] cityNamesSplit = ta1.getText().split(" ");

			for (String cityName : cityNamesSplit) { // add city names to ArrayList
				cityNames.add(cityName);
			}
			// add command codes of all features to ArrayList
			for (int i = 0; i < 13; i++) {
				commandCodes.add(i);
			}
			if (ta1.getText().length() != 0) {
				WeatherBuddyCommands weatherBuddyCommands = new WeatherBuddyCommands();
				try {
					weatherBuddyCommands.implementMethods(cityNames, commandCodes);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			fa.setVisible(false);
		} else if (e.getSource() == ba3) {
			WeatherBuddyCommands weatherBuddyCommands = new WeatherBuddyCommands();
			String[] cityNamesSplit = ta1.getText().split(" ");

			for (String cityName : cityNamesSplit) { // add city names to ArrayList
				cityNames.add(cityName);
			}
			// add command codes of chosen features to ArrayList
			if (c0.isSelected()) {
				commandCodes.add(0);
			}
			if (c1.isSelected()) {
				commandCodes.add(1);
			}
			if (c2.isSelected()) {
				commandCodes.add(2);
			}
			if (c3.isSelected()) {
				commandCodes.add(3);
			}
			if (c4.isSelected()) {
				commandCodes.add(4);
			}
			if (c5.isSelected()) {
				commandCodes.add(5);
			}
			if (c6.isSelected()) {
				commandCodes.add(6);
			}
			if (c7.isSelected()) {
				commandCodes.add(7);
			}
			if (c8.isSelected()) {
				commandCodes.add(8);
			}
			if (c9.isSelected()) {
				commandCodes.add(9);
			}
			if (c10.isSelected()) {
				commandCodes.add(10);
			}
			if (c11.isSelected()) {
				commandCodes.add(11);
			}
			if (c12.isSelected()) {
				commandCodes.add(12);
			}
			try {
				weatherBuddyCommands.implementMethods(cityNames, commandCodes);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			fa.setVisible(false);
		}
	}
}
