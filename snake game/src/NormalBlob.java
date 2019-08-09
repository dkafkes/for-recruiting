import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NormalBlob extends Blob {
	
	public NormalBlob(String thisType, Color thisColor) {
		super();
		type = thisType;
		color = thisColor;
		typeNumber = 1;
	}
	
	@Override
	public void desiredSnakeMovement(Snake snake) {
		if (snake.isReversedAlreadyOrNot()) {
			snake.setNewOrder();
			
			snake.changeVelocityX(4);
			snake.changeVelocityY(4);
			
			snake.restoreTeleportState();
	}
		snake.changeVelocityX(4);
		snake.changeVelocityY(4);
		
		snake.restoreTeleportState();
}
	
}
