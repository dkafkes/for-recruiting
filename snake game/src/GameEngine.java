import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameEngine extends JPanel {

    private Snake snake;
    private int len;
    private Blob currentBlob;
    private Blob previousBlob;
    private int score;
    public GameMethods game;
    public JLabel scoreText;
    boolean snakeAlive;
    boolean currentBlobStatus;

    public static final int COURT_WIDTH = 800;
    public static final int COURT_HEIGHT = 800;
    public static final int INTERVAL = 15;
    
        
    public GameEngine(ArrayList<Point> snakeBody, int newScore, JLabel scoreText, int currentLength, int blobXCoord, int blobYCoord, int blobType) {
    	game = new GameMethods();
    	
    	snake = game.getSnake();
    	snake.setSnakeParts(snake, snakeBody);
    	
    	snake.setSnakeLength(currentLength);
    	len = currentLength;
    	    	
    	Blob thisBlob = game.getBlobFromNumber(blobType, blobXCoord, blobYCoord);
    	game.setBlob(game, thisBlob);
    	currentBlob = game.getBlob();
    	
    	currentBlobStatus = currentBlob.getStatus();
    	previousBlob = new NormalBlob("Normal", Color.RED);
    	
    	score = game.getScore();
    	game.setScore(newScore);
    	this.scoreText = scoreText;
    	
    	snakeAlive = game.getGameStatus();
    	        
	   Timer timer = new Timer(INTERVAL, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            tick();
	        }
	    });
	   
	    timer.start();
	    
        setFocusable(true);
	    
		addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	                snake.changeVelocityY(0);
	                snake.movementAlgorithm(snake.getSnakeHead(), - snake.getVelocityX(), snake.getVelocityY());
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	                snake.changeVelocityY(0);
	                snake.movementAlgorithm(snake.getSnakeHead(), snake.getVelocityX(), snake.getVelocityY());
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	                snake.changeVelocityX(0);
	                snake.movementAlgorithm(snake.getSnakeHead(), snake.getVelocityX(), snake.getVelocityY());
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
	                snake.changeVelocityX(0);
	                snake.movementAlgorithm(snake.getSnakeHead(), snake.getVelocityX(), - snake.getVelocityY());

                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                	snake.changeVelocityX(0);
                	snake.changeVelocityY(0);
	                snake.movementAlgorithm(snake.getSnakeHead(), snake.getVelocityX(), snake.getVelocityY());
                }
            }
            
            public void keyReleased(KeyEvent e) {
                snake.changeVelocityX(0);
                snake.changeVelocityY(0);
                timer.restart();
            }
            
       });
		
    }
    
    public void restart() {
    	previousBlob = new NormalBlob("Normal", Color.RED);
		previousBlob.desiredSnakeMovement(snake);
        snakeAlive = true;
        requestFocusInWindow();
    }
    
    public void reset() {
    	game = new GameMethods();
    	currentBlob = game.getBlob();
		game.setSnake(game, new Snake());
		snake = game.getSnake();
		game.setScore(0);
		score = game.getScore();
		scoreText.setText("Score: "+game.getScoreforLabel());
    	previousBlob = new NormalBlob("Normal", Color.RED);
		previousBlob.desiredSnakeMovement(snake);
        snakeAlive = true;
        len = 3;
        requestFocusInWindow();
    }
  
    		    
	 void tick() {	
		 
		 game.checkStatusOfSnake();
		 snakeAlive = game.getGameStatus();
		 currentBlobStatus = currentBlob.getStatus();
		 
	    	if (snakeAlive) {
	    		
	    		game.setSnakeHeadPosition(snake.getSnakeHead());
	    		game.setBlobPosition(currentBlob.whereBlob());
	    		
	    		previousBlob.desiredSnakeMovement(snake);
    		
	    		if (snake.getHasBeenHere().size() > 5) {
	    		snake.removePreviousPosition();
	    		}
	    			    		
		    	if (((int) game.snakeHeadPosition().getX() <= (int) game.blobPosition().getX()+20) && ((int) game.snakeHeadPosition().getX() >= (int) game.blobPosition().getX()-20) 
						&& ((int) game.snakeHeadPosition().getY() <= (int) game.blobPosition().getY()+20) && ((int) game.snakeHeadPosition().getY() >= (int) game.blobPosition().getY()-20)) {
		    		
		    		Toolkit.getDefaultToolkit().beep();
		    		game.eatBlob(snake);
		    		len= len+1;
		    		previousBlob = currentBlob;
		    		
		    		Blob maybeBlob = game.generateRandomBlob();
		    		
		    		while (!game.isBlobOnTopOfSnake(snake, maybeBlob)) {
			    		maybeBlob = game.generateRandomBlob();
		    		}
		    			
		    		currentBlob = maybeBlob;
		    		currentBlob.desiredSnakeMovement(snake);

		        	scoreText.setText("Score: " + game.getScoreforLabel());		        	
	    		
		    		if (snake.getWasOnceThisLength().size() > 5) {
		    			snake.removeFirstLength();
		    		}

		    	}
	    	}
	    	
	    	
	    	if (!snakeAlive) {
	    		snake.die();
	    		
	    	}
	    	
	    	repaint();
	    }
	
	    @Override
	    public void paintComponent(Graphics g) {
	    	super.paintComponent(g);

	    	if (snake.getSnakeStatus()) { 
	        snake.draw(g);
	        currentBlob.draw(g);
	       }
	       
	       if (currentBlobStatus == true) {
	        	currentBlob.draw(g);
	       }
	       
	       if (!snake.getSnakeStatus()) {
	    	  	g.setColor(Color.WHITE);
	    	  	Font f = new Font("Dialog", Font.PLAIN, 100);
	    	  	g.setFont(f);
	        	g.drawString("GAME OVER", 90, 370);
	       }
	       
	    }
	    
	    public GameMethods getUnderlyingGameForIO(GameEngine gameEngine) {
	    	return gameEngine.game;
	    }
	    
	    public int returnSnakeLength(GameEngine gameEngine) {
	    	return gameEngine.len;
	    }
	    
	
}