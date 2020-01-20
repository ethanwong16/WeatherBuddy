import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class WeatherBuddyClient implements ActionListener {
	JFrame f, f1, fa, fb, fc;
	JLabel l1, l2, l3, la1, lb1, la2, la3, lc1, lc2, lc3, lc4, lc5, imgL;
	JTextField t1, tc1, tc2;
	JButton b1, b2;
	ImageIcon img;
	CreateReadOnlyJTextField readOnly;
	WeatherBuddyMainPage mainPage;

	public WeatherBuddyClient() { // creates JFrame when class is constructed
		f = new JFrame("Welcome Page");
		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(Color.white);
		img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/world.jpg");
		imgL = new JLabel(img);
		imgL.setBounds(0, 0, 1500, 1000);
		l1 = new JLabel("\nWelcome to WeatherBuddy!\n");
		l1.setBounds(500, 200, 600, 75);
		l1.setFont(new Font("Arial", Font.BOLD, 40));
		l1.setForeground(Color.black);
		l2 = new JLabel("Would you like to run WeatherBuddy?");
		l2.setBounds(500, 300, 600, 50);
		l2.setFont(new Font("Arial", Font.BOLD, 24));
		l2.setForeground(Color.black);
		l3 = new JLabel("Enter YES or NO :");
		l3.setBounds(500, 350, 600, 50);
		l3.setFont(new Font("Arial", Font.BOLD, 24));
		l3.setForeground(Color.black);

		b1 = new JButton("YES");
		b1.setBackground(Color.cyan);
		b1.addActionListener(this);
		b1.setBounds(500, 400, 100, 30);

		b2 = new JButton("NO");
		b2.setBackground(Color.cyan);
		b2.addActionListener(this);
		b2.setBounds(750, 400, 100, 30);

		f.getContentPane().add(l1);
		f.getContentPane().add(l2);
		f.getContentPane().add(l3);
		f.getContentPane().add(b1);
		f.getContentPane().add(b2);
		f.getContentPane().add(imgL);
		f.setSize(1500, 1000);
		f.setVisible(true);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new WeatherBuddyClient();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) { // if yes is selected
			mainPage = new WeatherBuddyMainPage();
			f.setVisible(false);
		}
		if (e.getSource() == b2) { // if no is selected
			f1 = new JFrame("Exit");
			la1 = new JLabel("Thank you for using WeatherBuddy! See you again soon! uWu");
			la1.setBounds(300, 150, 1200, 50);
			la1.setFont(new Font("Arial", Font.BOLD, 30));
			la1.setForeground(Color.black);
			img = new ImageIcon("C:/Java/eclipse-workspace/APCS/src/exit.jpg");
			imgL = new JLabel(img);
			imgL.setBounds(0, 0, 1500, 1000);
			f1.getContentPane().add(la1);
			f1.getContentPane().add(imgL);
			f1.setSize(1500, 1000);
			f1.setVisible(true);
			f.setVisible(false);
		}
	}
}