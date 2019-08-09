import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SmushBlob extends Blob {

	public SmushBlob(String thisType, Color thisColor) {
		super();
		type = thisType;
		color = thisColor;
		typeNumber = 3;
	}
	
	@Override
	public void desiredSnakeMovement(Snake snake) {
		if (snake.isReversedAlreadyOrNot()) {
			snake.setNewOrder();

			snake.changeVelocityX(1);
			snake.changeVelocityY(1);
			
			snake.restoreTeleportState();

	}
		snake.changeVelocityX(1);
		snake.changeVelocityY(1);
		
		snake.restoreTeleportState();

}
}
	
