import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Snake {
	private int length;
	private boolean isAlive;
	private List<Point> hasBeenHere = new LinkedList<Point>();
	private List<Integer> wasOnceThisLength = new LinkedList<Integer>();
	private int velocityX;
	private int velocityY;
	private List<Point> snakeParts = new ArrayList<Point>();
	private boolean reverse;
	private boolean teleported;
	
	public Snake() {
		length = 3;
		isAlive = true;
		hasBeenHere.add(new Point(100,100));
		snakeParts.add(new Point(100,100));
		snakeParts.add(new Point(75,100));
		snakeParts.add(new Point(50,100));
		wasOnceThisLength.add(length);
		reverse = false;
		teleported = false;
		velocityX = 0;
		velocityY = 0;
	}
	
	public void changeLength(int howMuch) {
		length += howMuch;
	}
	
	public void changeVelocityX(int newSpeed) {
		velocityX = newSpeed;
	}
	
	public void changeVelocityY(int newSpeed) {
		velocityY = newSpeed;
	}
	
	public int getVelocityX() {
		return velocityX;
	}
	
	public int getVelocityY() {
		return velocityY;
	}
	
	public void growSnake() {
		changeLength(1);
		wasOnceThisLength.add(length);		
		Point newJoint = new Point();
		newJoint.setLocation((int) snakeParts.get(snakeParts.size()-1).getX(),
				(int) snakeParts.get(snakeParts.size()-1).getY());
		snakeParts.add(newJoint);
	}
	
	public void die() {
		isAlive = false;
	}
	
	
	public void addNewPosition(Point newPlace) {
		hasBeenHere.add(newPlace);
	}
	
	public void removePreviousPosition() {
		try {
		((LinkedList<Point>) hasBeenHere).removeFirst();
		}
		catch (Error e) {
			// do nothing
		}
	}
	
	public void addNewLength(int lengthNow) {
		wasOnceThisLength.add(lengthNow);
	}
	
	public List<Integer> getWasOnceThisLength() {
		return wasOnceThisLength;
	}
	
	public void removeFirstLength() {
		try {
		if (wasOnceThisLength != null) {
		wasOnceThisLength.remove(0);
		}
		}
		catch (Error e) {
			// do nothing
		}
	}
	
	public String justAteWhat(Blob blob) {
		return blob.getType();
	}
	
	public void draw(Graphics g) {
		if (getSnakeStatus()) {
		
		for (Point p: snakeParts) {
        	   g.setColor(Color.GREEN);
       		   g.fillOval((int) p.getX(), (int) p.getY(), 25, 25);
		}     
	}
	}
	
	public void movementAlgorithm(Point head, int velX, int velY) {
		List<Point> bodyOfSnake = new ArrayList<Point>();
		Point newHead = new Point(0,0);
		newHead.setLocation(head.getX()+velX*7, head.getY()+velY*7);
		
		bodyOfSnake.add(newHead);

		for (int i = 1; i< snakeParts.size(); i++) {
			bodyOfSnake.add(snakeParts.get(i-1));
		}
		
		snakeParts = bodyOfSnake;
	}
		
	
	public List<Point> getSnakeParts(Snake s) {
		return s.snakeParts;
	}
	
	public void addtoSnakeParts(Point p) {
		snakeParts.add(p);
	}
	
	public Point getSnakeHead() {
		return snakeParts.get(0);
	}

	public void setNewOrder() {
		if (!isReversedAlreadyOrNot()) {
		List<Point> switchUpOrder = new LinkedList<Point>();
		Object[] switched= snakeParts.toArray();
		
		for (int i = 0; i < switched.length; i++) {
			switchUpOrder.add((Point) switched[switched.length - (i+1)]);
		}
		
		snakeParts = switchUpOrder;
		reverse = !reverse;
	}
	}
	
	public List<Point> getHasBeenHere() {
		return hasBeenHere;
	}
	
	public boolean isReversedAlreadyOrNot() {
		return reverse;
	}
	
	public void setSnakeHead(Point newHead) {
		snakeParts.set(0, newHead);
	}
	
	public int getLength() {
		return length;
	}
	
	public void setSnakeLength(int newLength) {
		length = newLength;
	}
	
	public boolean getSnakeStatus() {
		return isAlive;
	}
	
	public void teleport() {
		teleported = true;
	}
	
	public void restoreTeleportState() {
		teleported = false;
	}
	
	public boolean hasBeenTeleportedOrNot() {
		return teleported;
	}
	
	public void setSnakeParts(Snake s, List<Point> newBody) {
		s.snakeParts = newBody;
	}
	
	public void resurrectSnake(Snake s) {
		s.isAlive = true;
	}

}
