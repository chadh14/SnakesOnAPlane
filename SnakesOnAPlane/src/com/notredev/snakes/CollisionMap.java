package com.notredev.snakes;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.notredev.snakes.Actor.ActorType;

public class CollisionMap {
	// Multi-map of all occupied positions
	HashMap<Position, HashMap<ActorType, HashSet<Actor>>> map = new HashMap<Position, HashMap<ActorType, HashSet<Actor>>>();
	
	public CollisionMap() {	
	}
	
	public void addActor(Actor actor) {
		for (Position position : actor.getPositions()) {
			if (!map.containsKey(position)) {
				map.put(position, new HashMap<ActorType, HashSet<Actor>>());
			}
			if (!map.get(position).containsKey(actor.getType())) {
				map.get(position).put(actor.getType(), new HashSet<Actor>());
			}
			map.get(position).get(actor.getType()).add(actor);
		}
	}
	
	/**
	 * Returns a collection of actors to delete
	 */
	private Collection<Actor> resolveCollision(Position position) {
		HashSet<Actor> actorsToRemove = new HashSet<Actor>();
		
		HashMap<ActorType, HashSet<Actor>> actorsByType = map.get(position);
		if (actorsByType.containsKey(ActorType.FOOD)) {
			if (actorsByType.containsKey(ActorType.BULLET)) {
				// The bullet destroys all food
				for (Actor foodActor : actorsByType.get(ActorType.FOOD)) {
					actorsToRemove.add(foodActor);
				}
			}
			
			if (actorsByType.containsKey(ActorType.SNAKE)) {
				for(Actor snakeActor : actorsByType.get(ActorType.SNAKE)) {
					// A snake head eats the food
					if (((Snake)snakeActor).getHeadPosition().equals(position)) {
						((Snake)snakeActor).setGrowOnNextMove(true);
					}
				}
				// All food items are destroyed
				for (Actor foodActor : actorsByType.get(ActorType.FOOD)) {
					actorsToRemove.add(foodActor);
				}
			}
		}
		
		// Check for multiple snake collisions
		
		// Check for bullet and snake collisions
		
		// Check for obstacle and snake collisions
		
		
		
		return actorsToRemove;
		
	}
	
	public Collection<Actor> resolveAllCollisions() {
		HashSet<Actor> actorsToRemove = new HashSet<Actor>();
		for (Position position : map.keySet()) {
			actorsToRemove.addAll(resolveCollision(position));
		}
		return actorsToRemove;
	}
	
}
