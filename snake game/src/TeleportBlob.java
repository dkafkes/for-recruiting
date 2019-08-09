import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TeleportBlob extends Blob{

	public TeleportBlob(String thisType, Color thisColor) {
		super();
		type = thisType;
		color = thisColor;
		typeNumber = 4;
	}
	
	@Override
	public void desiredSnakeMovement(Snake snake) {
		
		if (!snake.hasBeenTeleportedOrNot()) {
		
		Point newHere = new Point();
		newHere.setLocation(Math.random()*600, Math.random()*600);
				
		if (snake.isReversedAlreadyOrNot()) {
			snake.setNewOrder();
			
			snake.setSnakeHead(newHere);
			
			for (int i = 1; i < snake.getSnakeParts(snake).size(); i++) {
				snake.getSnakeParts(snake).get(i).setLocation((int)snake.getSnakeHead().getX()-25*i,
						(int) snake.getSnakeHead().getY());
			}
			
			snake.changeVelocityX(4);
			snake.changeVelocityY(4);
			snake.teleport();
		}
		
		snake.setSnakeHead(newHere);
		
		for (int i = 1; i < snake.getSnakeParts(snake).size(); i++) {
			snake.getSnakeParts(snake).get(i).setLocation((int)snake.getSnakeHead().getX()-25*i,
					(int) snake.getSnakeHead().getY());
		}
		
		snake.changeVelocityX(4);
		snake.changeVelocityY(4);	
		snake.teleport();
	}
		
		snake.changeVelocityX(4);
		snake.changeVelocityY(4);
	}

}
