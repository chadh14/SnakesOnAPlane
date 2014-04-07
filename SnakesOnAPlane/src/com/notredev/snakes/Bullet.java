package com.notredev.snakes;

import java.util.Collection;
import java.util.HashSet;

public class Bullet extends Actor {
	
	private Position position;
	private Direction direction;
	
	public Bullet(Position position, Direction direction) {
		super(ActorType.BULLET);
		this.position = position;
		this.direction = direction;
	}

	@Override
	public Collection<Position> getPositions() {
		//TODO: Creating a new hash map each time isn't very efficient
		HashSet<Position> positions = new HashSet<Position>();
		positions.add(position);
		return positions;
	}
	
	@Override
	public void update() {
		try {
			position = playingSurface.getNextPosition(position, direction);
		}
		catch (PositionOutOfBoundsException e) {
			//TODO: Handle this
		}
	}
	
	
	
}
