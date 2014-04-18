package com.notredev.snakes;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PlayingSurface {
	private int columns;
	private int rows;
	
	private HashSet<Actor> actors = new HashSet<Actor>();
	
	private static PlayingSurface playingSurface = new PlayingSurface();
	
	private Random random = new Random();
	
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
	
	public void addActor(Actor actor) {
		actors.add(actor);
	}
	
	public boolean removeActor(Actor actor) {
		return actors.remove(actor);
	}
	
	public HashSet<Actor> getActors() {
		return actors;
	}
	
	public boolean removeActors(Collection<Actor> actors) {
		boolean wasModified = false;
		for (Actor actor : actors) {
			if (removeActor(actor)) {
				wasModified = true;
			}
		}
		return wasModified;
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
	
	private HashSet<Position> getFreePositions() {
		HashSet<Position> freePositions = new HashSet<Position>();
		// Add all rows and columns to the map
		for (int row=0; row < rows; row++) {
			for (int column=0; column < columns; column++) {
				freePositions.add(new Position(row, column));
			}
		}
		
		// Remove positions occupied by actors
		for (Actor actor : actors) {
			freePositions.removeAll(actor.getPositions());
		}
		return freePositions;
	}
	
	public HashSet<Position> getBorderPositions() {
		HashSet<Position> border = new HashSet<Position>();
		
		for (int column=0; column < columns; column++) {
			border.add(new Position(0, column)); // Top boarder
			border.add(new Position(rows, column)); // Bottom boarder
		}
		
		for (int row=0; row < rows; row++) {
			border.add(new Position(row, 0)); // Left boarder
			border.add(new Position(row, columns)); // Right boarder
		}
		
		return border;
	}
	
	public List<Position> getAdjacentFreePositions(int numberOfPositions) {
		HashSet<Position> freePositions = getFreePositions();
		LinkedList<Position> positions;
		
		do {
			positions = new LinkedList<Position>();
			Position randomStartPosition = new Position(random.nextInt(rows), random.nextInt(columns));
	    	positions.add(randomStartPosition);
	    	while (positions.size() != numberOfPositions) {
	    		Direction randomDirection = Direction.values()[random.nextInt(4)];
	    		try {
	    			Position nextPosition = getNextPosition(positions.getLast(), randomDirection);
	    			if (!positions.contains(nextPosition)) { // Don't duplicate positions already in the list
	    				positions.add(nextPosition);
	    			}
	    		}
	    		catch (PositionOutOfBoundsException e) {
	    			//TODO: It is possible that the only available positions are already in our list or are out of bounds
	    			// This will cause an infinite loop here
	    			continue; 
	    		}
	    	}
		} while(!freePositions.containsAll(positions));
		return positions;  	
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
