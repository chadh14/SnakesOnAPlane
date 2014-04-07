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
	
	public List<Position> getAdjacentFreePositions(int numberOfPositions) {
		LinkedList<Position> positions;
		
		do {
			positions = new LinkedList<Position>();
			Position randomStartPosition = new Position(random.nextInt(rows), random.nextInt(columns));
	    	positions.add(randomStartPosition);
	    	while (positions.size() != numberOfPositions) {
	    		Direction randomDirection = Direction.values()[random.nextInt(4)];
	    		try {
	    			Position nextPosition = getNextPosition(positions.getLast(), randomDirection);
	    			while(positions.contains(nextPosition)) {
	    				nextPosition = getNextPosition(positions.getLast(), randomDirection);
	    			}
	    			positions.add(nextPosition);
	    		}
	    		catch (PositionOutOfBoundsException e) {
	    			continue;
	    		}
	    	}
		} while(!arePositionsFree(positions));
		return positions;  	
	}
	
	public boolean isPositionFree(Position position) {
		for (Actor actor : actors) {
			if (actor.getPositions().contains(position)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns true if all the positions are empty, false otherwise
	 * @param positions
	 * @return
	 */
	public boolean arePositionsFree(Collection<Position> positions) {
		for (Position position : positions) {
			if (!isPositionFree(position)) {
				return false;
			}
		}
		return true;
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
