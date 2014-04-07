package com.notredev.snakes;

public class PlayingSurface {
	private int columns;
	private int rows;
	
	private static PlayingSurface playingSurface = new PlayingSurface();
	
	public static PlayingSurface Instance()
	{
		return playingSurface;
	}
	
	private PlayingSurface() {
	
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public int getRows() {
		return rows;
	}
	
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public Position getNextPosition(Position position, Direction direction) throws PositionOutOfBoundsException {
		int nextRow = position.getRow();
		int nextColumn = position.getColumn();

		switch (direction) {
	        case UP:
	        	nextRow--;
	        	break;
	        case DOWN:
	        	nextRow++;
	        	break;
	        case LEFT:
	        	nextColumn--;
	        	break;
	        case RIGHT:
	        	nextColumn++;
	        	break;
		}
		Position nextPosition = new Position(nextRow, nextColumn);
		
		if (!isPositionValid(nextPosition)) {
			throw new PositionOutOfBoundsException("Position " + position + " is not inside playing surface.");
		}
		return nextPosition;
	}
	
	/**
	 * Returns true if the position is on the playing surface (i.e. its coordinates are within bounds)
	 */
	public boolean isPositionValid(Position position) {
		if (position.getRow() < 0 || rows < position.getRow()) {
			return false;
		}
		if (position.getColumn() < 0 || columns < position.getColumn()) {
			return false;
		}
		return true;
	}
	
}
