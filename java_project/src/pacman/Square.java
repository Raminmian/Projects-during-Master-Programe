package pacman;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Each instance of this class represents a position in a maze, specified by a row index and a column index.
 * The top row and the leftmost column have index 0.
 */
public class Square {
///11323232321323
	private MazeMap mazeMap;
	private int rowIndex;
	private int columnIndex;

	public MazeMap getMazeMap() {return this.mazeMap; }

	public int getRowIndex() { return this.rowIndex; }

	public int getColumnIndex() { return this.columnIndex; }

	public boolean isPassable() { return getMazeMap().isPassable( rowIndex,columnIndex) ;}

	public static Square of(MazeMap mazeMap, int rowIndex, int columnIndex) {
		Square result=new Square();
		result.mazeMap=mazeMap;
		result.rowIndex=rowIndex;
		result.columnIndex=columnIndex;
		return result;
	}

	/**
	 * Returns this square's neighbor in the given direction.
	 * If this square has no neigbor in the given direction, return the square that is furthest away in the opposite direction.
	 */
	// No formal documentation required
	public Square getNeighbor(Direction direction) {
		// Implementation hint: use method java.lang.Math.floorMod.
		
		int width=getMazeMap().getWidth();
		int height=getMazeMap().getHeight();
		int newcol=columnIndex;
		int newrow=rowIndex;
		switch(direction) { 
	      case RIGHT:
	    	 newcol=getColumnIndex()+1;
	    	  break;
	      case DOWN:
	    	  newrow= getRowIndex()+1;
	    	  break;
	      case LEFT:
	    	 newcol=getColumnIndex()-1;
	    	  break;
	      case UP:
	    	 newrow=getRowIndex()-1;
	    	  break;
	    }
		
		if(newrow<0) {
			newrow=height-1;	
		}
		if(newrow==height) {
			newrow=0;
		}
		
		if(newcol<0) {
			newcol=width-1;
		}
		if(newcol==width) {
			newcol=0;
			
		}
		return Square.of(mazeMap, newrow, newcol);
		
	}

	/**
	 * Returns whether this square's neighbor in the given direction is passable.
	 */
	// No formal documentation required
	public boolean canMove(Direction direction) {
	return getNeighbor(direction).isPassable();
	}

	/**
	 * Returns the directions that are different from the given excluded direction and such that the neighbor in that direction is passable.
	 * The returned array shall have no null elements and shall have no duplicates.
	 */
	// No formal documentation required
	public Direction[] getPassableDirectionsExcept(Direction excludedDirection) {
	
		boolean[] pass=new boolean[4];
		Direction[]all=new Direction[] {Direction.UP,Direction.DOWN,Direction.LEFT,Direction.RIGHT};
		for(int i=0; i<4;i++) {
		pass[i]=canMove(all[i]);
		
		}
		
		Direction[] allnum=new 	Direction[4];
		int k=0;
		for(int i=0;i<4;i++) {
			if (pass[i]==true&&all[i]!=excludedDirection)
			allnum[k]=all[i];
			k=k+1;

			}
		Direction[] result=new Direction[k];
		System.arraycopy(allnum, 0, result, 0, k);
	
		return result;
	}

	/**
	 * Returns whether the given square refers to the same {@code MazeMap} object and has the same row and column index as this square.  
	 */
	public boolean equals(Square other) {
		boolean result=false;
		if (this.getColumnIndex()==other.getColumnIndex()&&this.getRowIndex()==other.getRowIndex()) {
			result=true;
		}
		return result;
	}

}
