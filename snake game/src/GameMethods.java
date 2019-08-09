import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class GameMethods extends JComponent {
	final String[] blobOptions = {"Normal", "SwitchUp", "Smush", "Teleport", "TransportBack", "SpreadOut"};
	private int score;
    private Snake snake;
    private Blob newBlob;
    private Point blobLocation;
    private Point headLocation;
    private boolean keepRunning;
    
    public GameMethods() {
    	score = 0;
        snake = new Snake();
        newBlob = new NormalBlob("normal", Color.RED);
        blobLocation = newBlob.whereBlob();
        headLocation = snake.getSnakeHead();
        keepRunning = true;
    }
    
    public Blob generateRandomBlob() {
    	Random random = new Random();
		int result = random.nextInt(blobOptions.length);
		String createThis = blobOptions[result];
		
		if (createThis.equalsIgnoreCase("Normal")) {
			newBlob = new NormalBlob("Normal", Color.RED);
		}
		
		if (createThis.equalsIgnoreCase("SwitchUp")) {
			newBlob = new SwitchUpBlob("SwitchUp", Color.GRAY);
		}
		
		if (createThis.equalsIgnoreCase("Smush")) {
			newBlob = new SmushBlob("Smush", Color.WHITE);
		}
		
		if (createThis.equalsIgnoreCase("Teleport")) {
			newBlob = new TeleportBlob("Teleport", Color.BLUE);
		}
		
		if (createThis.equalsIgnoreCase("TransportBack")) {
			newBlob = new TransportBackFiveBlob("TransportBack", Color.YELLOW);
		}
		
		if (createThis.equalsIgnoreCase("SpreadOut")) {
			newBlob = new SpreadOutBlob("SpreadOut", Color.MAGENTA);
		}
		
		blobLocation = newBlob.whereBlob();
		
		return newBlob;
    }
	
	public void eatBlob(Snake s) {
			newBlob.changeStatus();
			setScore(score+=10);
			s.growSnake();
	}
	
	public Point snakeHeadPosition() {
		return headLocation;
	}
	
	public Point blobPosition() {
		return blobLocation;
	}
	
	public void checkStatusOfSnake() {
		if (headLocation.getX() >= 795 || headLocation.getY() >= 795 || headLocation.getX() <= 5 || headLocation.getY() <= 5) {
			snake.die();
			keepRunning = false;
		}
	}
	
	public Blob getBlob() {
		return newBlob;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getScoreforLabel() {
		return Integer.toString(score);
	}
	
	public void setScore(int oldScore) {
		score = oldScore;
	}
	
	public boolean getGameStatus() {
		return keepRunning;
	}
	
	public void resetGameStatus() {
		keepRunning = true;
	}
	
	public void setSnakeHeadPosition(Point p) {
		headLocation = p;
	}
	
	public void setBlobPosition(Point p) {
		blobLocation = p;
	}
	
	public void setSnake(GameMethods gMethods, Snake s) {
		gMethods.snake = s;
	}
	
	public Blob getBlobFromNumber(int typeNumber, int XCoord, int YCoord) {
		Blob potentialBlob = new NormalBlob("Normal", Color.RED);

		if (typeNumber == 1) {
			potentialBlob = new NormalBlob("Normal", Color.RED);
		}
		if (typeNumber == 2) {
			potentialBlob = new SwitchUpBlob("SwitchUp", Color.GRAY);
		}
		if (typeNumber == 3) {
			potentialBlob = new SmushBlob("Smush", Color.WHITE);
		}
		if (typeNumber == 4) {
			potentialBlob = new TeleportBlob("Teleport", Color.BLUE);
		}
		if (typeNumber == 5) {
			potentialBlob = new SpreadOutBlob("SpreadOut", Color.MAGENTA);
		}
		if (typeNumber == 6) {
			potentialBlob = new TransportBackFiveBlob("TransportBack", Color.YELLOW);
		}
		
		Point p = new Point();
		p.setLocation(XCoord, YCoord);		
		potentialBlob.changeLocation(potentialBlob, p);
		
		return potentialBlob;
	}
	
	public void setBlob(GameMethods g, Blob b) {
		g.newBlob = b;
	}
	
	public boolean isBlobOnTopOfSnake(Snake s, Blob b) {
		Point p = b.getLocation(b);
		boolean goodPlacement = true;
		
		for (int i = 0; i < s.getSnakeParts(s).size(); i++) {
			if (p.equals(s.getSnakeParts(s).get(i))) {
				goodPlacement = false;
			}
		}
		
		return goodPlacement;
		
	}
	

}
