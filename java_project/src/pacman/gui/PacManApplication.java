package pacman.gui;

import java.awt.EventQueue;
import java.util.Random;

import javax.swing.JFrame;

public class PacManApplication {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			MazeView mazeView = new MazeView();
			JFrame frame = new JFrame("Pac-Man");
			frame.getContentPane().add(mazeView);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
