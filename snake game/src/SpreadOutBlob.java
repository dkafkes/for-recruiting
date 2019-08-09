import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SpreadOutBlob extends Blob {

	public SpreadOutBlob(String thisType, Color thisColor) {
		super();
		type = thisType;
		color = thisColor;
		typeNumber = 5;
	}
	
	@Override
	public void desiredSnakeMovement(Snake snake) {
		if (snake.isReversedAlreadyOrNot()) {
			snake.setNewOrder();
			
			snake.changeVelocityX(8);
			snake.changeVelocityY(8);
			
			snake.restoreTeleportState();
	}
		
		snake.changeVelocityX(8);
		snake.changeVelocityY(8);
		
		snake.restoreTeleportState();
}
}
