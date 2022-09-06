package pacman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Random;
import java.util.function.Supplier;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import pacman.Direction;
import pacman.Dot;
import pacman.Ghost;
import pacman.Maze;
import pacman.MazeDescriptions;
import pacman.MazeMap;

public class MazeView extends JPanel {
	
	private static int squareSize = 30;
	private static int lifeSize = squareSize;
	private static int lifeMargin = 1;
	private static int dotRadius = squareSize / 10;
	private static int ghostMoveDelayMillis = 1000;

	private static Image loadSquareImage(String filename) {
		URL url = MazeView.class.getResource(filename);
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		return image.getScaledInstance(squareSize, squareSize, Image.SCALE_SMOOTH);
	}
	private static Image pacManImage = loadSquareImage("PacMan.png");
	private static Image ghostImage = loadSquareImage("ghost.png");
	
	private Maze maze;
	private MazeMap map;
	private Timer ghostTimer;
	
	private void mazeChanged() {
		repaint();
		if (maze.getPacMan().getNbLives() == 0) {
			ghostTimer.stop();
			JOptionPane.showMessageDialog(this, "Game over :-(");
			initializeMaze();
		} else if (maze.isCompleted()) {
			ghostTimer.stop();
			JOptionPane.showMessageDialog(this, "Congratulations: you won!");
			initializeMaze();
		}
	}
	
	private void startMovingGhosts() {
		ghostTimer = new Timer(ghostMoveDelayMillis, actionEvent -> {
			maze.moveGhosts();
			mazeChanged();
		});
		ghostTimer.start();
	}
	
	private void initializeMaze() {
		maze = MazeDescriptions.createMazeFromDescription(new Random(), """
				#####################
				#.........#.........#
				#.###.###.#.###.###.#
				#.###.###.#.###.###.#
				#.###.###.#.###.###.#
				#...................#
				#.###.#.#####.#.###.#
				#.###.#.#####.#.###.#
				#.....#...#...#.....#
				#####.### # ###.#####
				    #.#   G   #.#    
				    #.# #   # #.#    
				#####.# #   # #.#####
				     .  #GGG#  .     
				#####.# ##### #.#####
				    #.#       #.#    
				    # # ##### #.#    
				#####.# ##### #.#####
				#.........#.........#
				#.###.###.#.###.###.#
				#...#.....P.....#...#
				###.#.#.#####.#.#.###
				###.#.#.#####.#.#.###
				#.....#...#...#.....#
				#.#######.#.#######.#
				#...................#
				#####################
				""");
		map = maze.getMap();
		repaint();
		startMovingGhosts();
	}
	
	private void movePacMan(Direction direction) {
		maze.movePacMan(direction);
		mazeChanged();
	}
	
	public MazeView() {
		initializeMaze();
		setBackground(Color.black);
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT -> movePacMan(Direction.RIGHT); 
				case KeyEvent.VK_DOWN -> movePacMan(Direction.DOWN);
				case KeyEvent.VK_LEFT -> movePacMan(Direction.LEFT);
				case KeyEvent.VK_UP -> movePacMan(Direction.UP);
				}
			}
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(squareSize * maze.getMap().getWidth(), squareSize * maze.getMap().getHeight() + lifeSize + 2 * lifeMargin);
	}
	
	@Override
	public boolean isFocusable() {
		return true;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Walls
		g.setColor(Color.blue);
		for (int row = 0; row < map.getHeight(); row++)
			for (int column = 0; column < map.getWidth(); column++)
				if (!map.isPassable(row, column))
					g.fillRect(squareSize * column, squareSize * row, squareSize, squareSize);
		
		// Dots
		g.setColor(Color.yellow);
		for (Dot dot : maze.getDots())
			g.fillOval(
					dot.getSquare().getColumnIndex() * squareSize + squareSize / 2 - dotRadius,
					dot.getSquare().getRowIndex() * squareSize + squareSize / 2 - dotRadius,
					2 * dotRadius,
					2 * dotRadius);
		
		// Ghosts
		for (Ghost ghost : maze.getGhosts())
			g.drawImage(
					ghostImage,
					ghost.getSquare().getColumnIndex() * squareSize,
					ghost.getSquare().getRowIndex() * squareSize,
					this);
		
		// PacMan
		g.drawImage(
				pacManImage,
				maze.getPacMan().getSquare().getColumnIndex() * squareSize,
				maze.getPacMan().getSquare().getRowIndex() * squareSize,
				this);
		
		g.setColor(Color.yellow);
		// Lives
		for (int i = 0; i < maze.getPacMan().getNbLives(); i++)
			g.fillOval(i * (lifeSize + 2 * lifeMargin) + lifeMargin, map.getHeight() * squareSize + lifeMargin, lifeSize, lifeSize);
	}

}
