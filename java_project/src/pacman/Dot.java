package pacman;

/**
 * Each instance of this class represents a dot (a piece of food for Pac-Man) in a Pac-Man maze.
 */
public class Dot {
	//222222
	private Square square;
	public Square getSquare() {return this.square; }
	
	public Dot(Square square) { this.square=square; }

}
