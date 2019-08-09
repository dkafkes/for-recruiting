import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
	
public class SwitchUpBlob extends Blob{
	
	public SwitchUpBlob(String thisType, Color thisColor) {
		super();
		type = thisType;
		color = thisColor;
		typeNumber = 2;
	}
	
	@Override
	public void desiredSnakeMovement(Snake snake) {
		if (! snake.isReversedAlreadyOrNot()) {
			snake.setNewOrder();
			
			snake.changeVelocityX(-4);
			snake.changeVelocityY(-4);
			
			snake.restoreTeleportState();

		}
			snake.changeVelocityX(-4);
			snake.changeVelocityY(-4);
			
			snake.restoreTeleportState();
	}

}
