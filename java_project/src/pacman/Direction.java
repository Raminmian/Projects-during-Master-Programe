package pacman;

public enum Direction {
	RIGHT, DOWN, LEFT, UP;
	//3333333
	public Direction getOpposite() {
		return switch (this) {
		case RIGHT -> LEFT;
		case LEFT -> RIGHT;
		case UP -> DOWN;
		case DOWN -> UP;
		};
	}
}
