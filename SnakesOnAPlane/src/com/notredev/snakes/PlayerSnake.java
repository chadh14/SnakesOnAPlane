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
			direction = Direction.UP;
		}
		else if (state.Down()) {
			direction = Direction.DOWN;
		}
		else if (state.Left()) {
			direction = Direction.LEFT;
		}
		else if (state.Right()) {
			direction = Direction.RIGHT;
		}
		// If no buttons are pressed, coninue in the current direction
	}
	
}
