package com.notredev.snakes;

import java.util.Collection;
import java.util.HashSet;

public class Food extends Actor {
	private Position position;	
	
	public Food(Position position) {
		super(ActorType.FOOD);
		this.position = position;
	}

	@Override
	public void update() {
		// Do nothing, food doesn't move
	}

	@Override
	public Collection<Position> getPositions() {
		//TODO: Creating a new hash map each time isn't very efficient
		HashSet<Position> positions = new HashSet<Position>();
		positions.add(position);
		return positions;
	}
}
