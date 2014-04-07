package com.notredev.snakes;

public class PlayerSnake extends Snake {

	InputManager inputManager = InputManager.Instance();
	
	public PlayerSnake(Position startPosition, int playerNumber) {
		super(startPosition, playerNumber);
	}

	@Override
	public void update() {
		InputState state = inputManager.GetInputForController(playerNumber);
		try {
			setDirection(state);
			move();
		}
		catch (PositionOutOfBoundsException e) {
			//TODO: Handle this
		}
	}

	private void setDirection(InputState state) {
		if (!(state.Up() ^ state.Down() ^ state.Left() ^ state.Right())) {
			// If multiple directions are pressed, continue in the current direction
		}
		else if (state.Up()) {
			if (!direction.equals(Direction.DOWN)) {
				direction = Direction.UP;
			}
		}
		else if (state.Down()) {
			if (!direction.equals(Direction.UP)) {
				direction = Direction.DOWN;
			}
		}
		else if (state.Left()) {
			if (!direction.equals(Direction.RIGHT)) {
				direction = Direction.LEFT;
			}
		}
		else if (state.Right()) {
			if (!direction.equals(Direction.LEFT)) {
				direction = Direction.RIGHT;
			}
		}
		// If no buttons are pressed, coninue in the current direction
	}
	
}
