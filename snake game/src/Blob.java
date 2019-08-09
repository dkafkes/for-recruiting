import java.awt.*;
import java.awt.event.KeyAdapter;

public abstract class Blob {
	String type;
	boolean eatenStatus;
	Color color;
	Point location;
	int velocity;
	int typeNumber;


	public Blob() {     
		int x = (int) Math.round(Math.random() * 600);
	    int y = (int) Math.round(Math.random() * 600); 
	    location = new Point(x,y);
	    eatenStatus = false;
	}
	
	public Point whereBlob() {
		return location;
	}
	
	public String getType() {
		return type;
	}
	
	public int getTypeNumber() {
		return typeNumber;
	}
	
	public boolean getStatus() {
		return eatenStatus;
	}
	
	public void changeStatus() {
		eatenStatus = !eatenStatus;
	}
	
	public void changeLocation(Blob b, Point p) {
		location.setLocation(p.getX(), p.getY());
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int) location.getX(),(int) location.getY(), 25, 25);
	}
	
	public Point getLocation(Blob b) {
		return b.location;
	}
	
	public abstract void desiredSnakeMovement(Snake snake);
	
}
