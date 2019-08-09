import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TransportBackFiveBlob extends Blob{
	
	public TransportBackFiveBlob(String thisType, Color thisColor) {
		super();
		type = thisType;
		color = thisColor;
		typeNumber = 6;
	}
	
	
	@Override		
		public void desiredSnakeMovement(Snake snake) {
			if (snake.isReversedAlreadyOrNot()) {
				snake.setNewOrder();
				snake.setSnakeHead(snake.getHasBeenHere().get(0));
				
				for (int i = 1; i < snake.getSnakeParts(snake).size(); i++) {
					snake.getSnakeParts(snake).get(i).setLocation((int)snake.getSnakeHead().getX()-25*i,
							(int) snake.getSnakeHead().getY());
				}

				int previousLength = snake.getWasOnceThisLength().get(0);
				int lengthNow = snake.getSnakeParts(snake).size();
				 
				if (previousLength-lengthNow > 0) {
					for (int i = 0; i < lengthNow-previousLength; i++) {
						snake.getSnakeParts(snake).remove(lengthNow-i);
					}

				}
				
				snake.changeVelocityX(4);
				snake.changeVelocityY(4);
				
				snake.restoreTeleportState();
			}
			
			snake.setSnakeHead(snake.getHasBeenHere().get(0));

			for (int i = 1; i < snake.getSnakeParts(snake).size(); i++) {
				snake.getSnakeParts(snake).get(i).setLocation((int)snake.getSnakeHead().getX()-25*i,
						(int) snake.getSnakeHead().getY());
			}
			
			int previousLength = snake.getWasOnceThisLength().get(0);
			int lengthNow = snake.getSnakeParts(snake).size();
			 
			if (previousLength-lengthNow > 0) {
				for (int i = 0; i < lengthNow-previousLength; i++) {
					snake.getSnakeParts(snake).remove(lengthNow-i);
				}
			}
			
			snake.changeVelocityX(4);
			snake.changeVelocityY(4);
			
			snake.restoreTeleportState();
		}

}