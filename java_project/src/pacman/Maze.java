package pacman;

import java.util.Arrays;
import java.util.Random;

public class Maze {
	//wowowowoowowow
	
	private Random random;
	private MazeMap map;
	private PacMan pacMan;
	private Ghost[] ghosts;
	private Dot[] dots;
	
	public MazeMap getMap() { return map; }
	
	public PacMan getPacMan() { return pacMan; }
	
	public Ghost[] getGhosts() { return ghosts.clone(); }
	
	public Dot[] getDots() { return dots.clone(); }
	
	public Maze(Random random, MazeMap map, PacMan pacMan, Ghost[] ghosts, Dot[] dots) {
		this.random = random;
		this.map = map;
		this.pacMan = pacMan;
		this.ghosts = ghosts.clone();
		this.dots = dots.clone();
	}
	
	public boolean isCompleted() {
		return dots.length == 0;
	}
	
	private void checkPacManDamage() {
		for (Ghost ghost : ghosts)
			if (ghost.getSquare().equals(pacMan.getSquare()))
				pacMan.die();
	}
	
	public void moveGhosts() {
		for (Ghost ghost : ghosts)
			ghost.move(random);
		checkPacManDamage();
	}
	
	private void removeDotAtIndex(int index) {
		Dot[] newDots = new Dot[dots.length - 1];
		System.arraycopy(dots, 0, newDots, 0, index);
		System.arraycopy(dots, index + 1, newDots, index, newDots.length - index);
		dots = newDots;
	}
	
	private void removeDotAtSquare(Square square) {
		for (int i = 0; i < dots.length; i++) {
			if (dots[i].getSquare().equals(square)) {
				removeDotAtIndex(i);
				return;
			}
		}
	}
	
	public void movePacMan(Direction direction) {
		Square newSquare = pacMan.getSquare().getNeighbor(direction);
		if (newSquare.isPassable()) {
			pacMan.setSquare(newSquare);
			removeDotAtSquare(newSquare);
			checkPacManDamage();
		}
	}
	
}
