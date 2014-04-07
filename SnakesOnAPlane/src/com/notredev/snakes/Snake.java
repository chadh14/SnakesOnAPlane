package com.notredev.snakes;

import java.util.Collection;
import java.util.LinkedList;

public abstract class Snake extends Actor {

	protected int playerNumber;
	LinkedList<Position> body = new LinkedList<Position>();
	Direction direction;
	protected boolean growOnNextMove = false; // Indicates if the snake should grow on his next move
	
	public Snake(Position startPosition, int playerNumber) {
		super(ActorType.SNAKE);
		this.playerNumber = playerNumber;
		body.add(startPosition);
		// TODO: Give snake a body
		this.direction = Direction.RIGHT; //TODO: Make this dynamic
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public Position getHeadPosition() {
		return body.getFirst();
	}
	
	public void setGrowOnNextMove(boolean growOnNextMove) {
		this.growOnNextMove = growOnNextMove;
	}
	
	protected void move() throws PositionOutOfBoundsException {
		Position nextPosition = playingSurface.getNextPosition(getHeadPosition(), direction);
				
		body.addFirst(nextPosition);

		if (growOnNextMove) {
			growOnNextMove = false;
		}
		else {
			body.removeLast();
		}
	}
	
	public Obstacle split(Position splitPosition) {
		Obstacle obstacle = new Obstacle();
		
		if (body.contains(splitPosition)) {
			for (int i = body.size() - 1; i >= 0; i--) { // Reverse loop
				if (body.get(i) == splitPosition) {
					// Remove the Position from the snake but don't add it to the obstacle
					body.removeLast(); 
					break;
				}
				else {
					// Remove the Position from the snake and add it to the obstacle
					obstacle.addPosition(body.removeLast()); 
				}
			}
		}
		
		playingSurface.addActor(obstacle);
		return obstacle;
	}
	
	@Override
	public Collection<Position> getPositions() {
		return body;
	}
	
}
