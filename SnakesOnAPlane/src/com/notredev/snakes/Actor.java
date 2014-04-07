package com.notredev.snakes;

import java.util.Collection;

public abstract class Actor {
	public enum ActorType {SNAKE, OBSTACLE, BULLET, FOOD}
	ActorType type;
	PlayingSurface playingSurface = PlayingSurface.Instance();

	protected Actor(ActorType type)
	{
		this.type = type;
	}

	public ActorType getType()
	{
		return type;
	}
	
	public abstract Collection<Position> getPositions();
	
	public abstract void update();
	
}
