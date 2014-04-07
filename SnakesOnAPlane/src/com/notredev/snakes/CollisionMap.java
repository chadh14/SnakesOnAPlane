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
		//TODO: Check that the position is in the map
		HashMap<ActorType, HashSet<Actor>> actorsByType = map.get(position);
		
		if (actorsByType.containsKey(ActorType.SNAKE) && actorsByType.containsKey(ActorType.FOOD)) {	
			for(Actor actor : actorsByType.get(ActorType.SNAKE)) {
				// A snake head eats the food
				if (((Snake)actor).getHeadPosition().equals(position)) {
					((Snake)actor).setGrowOnNextMove(true);
				}
			}
			// All food items are destroyed
			for (Actor actor : actorsByType.get(ActorType.FOOD)) {
				actorsToRemove.add(actor);
			}
		}
		
		// Check if a snake collided with another snake
		if (actorsByType.containsKey(ActorType.SNAKE) && actorsByType.get(ActorType.SNAKE).size() > 1) {
			for (Actor actor : actorsByType.get(ActorType.SNAKE)) {
				// If the snake's head is in this position, it should be destroyed
				// Collisions of two snake bodies (non-heads) should never happen
				if( ((Snake)actor).getHeadPosition().equals(position)) {
					actorsToRemove.add(actor);
				}
			}
		}
		
		if (actorsByType.containsKey(ActorType.SNAKE) && actorsByType.containsKey(ActorType.BULLET)) {
			for (Actor actor : actorsByType.get(ActorType.SNAKE)) {
				// A bullet to the head is fatal
				if( ((Snake)actor).getHeadPosition().equals(position)) {
					actorsToRemove.add(actor);
				}
				// A bullet to the body, splits the snake and creates an obstacle
				else {
					((Snake)actor).split(position);
				}
			}
			// Bullets do not get destroyed when they hit snakes
		}
		
		if (actorsByType.containsKey(ActorType.BULLET) && actorsByType.containsKey(ActorType.FOOD)) {
			// The bullet destroys all food
			for (Actor foodActor : actorsByType.get(ActorType.FOOD)) {
				actorsToRemove.add(foodActor);
			}
		}
		
		if (actorsByType.containsKey(ActorType.OBSTACLE)) {
			// Anything colliding with an obstacle that is not an obstacle should be destroyed
			for (ActorType actorType : actorsByType.keySet()) {
				if (!actorType.equals(ActorType.OBSTACLE)) {
					for (Actor actor: actorsByType.get(actorType)) {
						actorsToRemove.add(actor);
					}
				}
			}
		}
		
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
