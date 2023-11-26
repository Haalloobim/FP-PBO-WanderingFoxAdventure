package id.ac.its.fox.main;

import javax.swing.*;

public class Game {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Wandering Fox Adventures");
		window.pack();
		window.setVisible(true);
	}
}
