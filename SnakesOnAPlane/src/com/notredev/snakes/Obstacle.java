package com.notredev.snakes;

import java.util.Collection;
import java.util.HashSet;

public class Obstacle extends Actor {
	
	HashSet<Position> positions = new HashSet<Position>();
	
	public Obstacle() {
		super(ActorType.OBSTACLE);
	}
	
	public Obstacle(Collection<Position> positions) {
		super(ActorType.OBSTACLE);
		for (Position position : positions) {
			this.positions.add(position);
		}
	}

	@Override
	public void update() {
		// Obstacles do not move or change on update
	}

	@Override
	public Collection<Position> getPositions() {
		return positions;
	}
	
	public void addPosition(Position position) {
		positions.add(position);
	}
	
	public boolean removePosition(Position position) {
		return positions.remove(position);
	}

}
