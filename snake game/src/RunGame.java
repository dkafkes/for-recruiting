import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class RunGame implements Runnable {
	int snakeHeadXCoord;
	int snakeHeadYCoord;
	int scoreInt;
	int length;
	int blobXCoord;
	int blobYCoord;
	int blobTypeNumber;
	ArrayList<Point> snakeBodyReload;
	
	public void run() {
		
	    File file = new File("previousCrazySnake.dat");
	    
	    if (file.exists()) {
	        try {
                InputStreamReader r = new InputStreamReader(new FileInputStream(file), "UTF8");

				LinkedList<Integer> maybeThisWorks = new LinkedList<Integer>();
				
				while (r.ready()) {
					maybeThisWorks.add(r.read());
				}	
				
				scoreInt = maybeThisWorks.get(0);
				length = maybeThisWorks.get(1);
				blobTypeNumber = maybeThisWorks.get(2);
				blobXCoord = maybeThisWorks.get(3);
				blobYCoord = maybeThisWorks.get(4);
				
				snakeBodyReload = new ArrayList<Point>();

				for (int i = 5; i < maybeThisWorks.size(); i=i+2) {
					Point newPartOfBody = new Point();
					int getX = maybeThisWorks.get(i);
					int getY = maybeThisWorks.get(i+1);
					newPartOfBody.setLocation(getX, getY);
					snakeBodyReload.add(newPartOfBody);
				}
				
				r.close();
		
			} catch (IOException e) {
				snakeBodyReload = new ArrayList<Point>();
				snakeBodyReload.add(new Point(100,100));
				snakeBodyReload.add(new Point(75,100));
				snakeBodyReload.add(new Point(50,100));
				scoreInt = 0;
				length = 3;
			}
	        
	    }
	    
	    else {
		snakeBodyReload = new ArrayList<Point>();
		snakeBodyReload.add(new Point(100,100));
		snakeBodyReload.add(new Point(75,100));
		snakeBodyReload.add(new Point(50,100));
		scoreInt = 0;
		length = 3;
	    }
		
		JOptionPane.showMessageDialog(null,
			    "Welcome to Crazy Snake! This is not a regular game of Snake." +'\n' +'\n'
			    + "Eating different blobs changes the way the Snake moves." +'\n'
			    + "Red Blobs are normal. Each new game will start with a Red Blob." +'\n'
			    + "Gray Blobs reverse the Snake's head and the way you control it on the keypad." +'\n'
			    + "White Blobs cause the Snake to smush and consequently slow down." +'\n'
			    + "Magenta Blobs cause the Snake to spread out." +'\n'
			    + "Blue Blobs teleport the Snake to a random location on the board." +'\n'
			    + "Yellow Blobs transport the Snake back to the location where it was five keystrokes ago" +'\n'
			    + "and revert the Snake back to its length five keystrokes ago." +'\n'+'\n'
			    + "Good luck!");
		
		final JFrame frame = new JFrame("Crazy Snake Game");
        frame.setFocusable(true);

        frame.setLocation(300, 100);
		frame.setSize(800, 800);

		final JPanel scorePanel = new JPanel();
		scorePanel.setBackground(Color.WHITE);
		frame.add(scorePanel, BorderLayout.NORTH);
		
		String updatedScorePotentially = Integer.toString(scoreInt);
		
		final JLabel score= new JLabel("Score: " + updatedScorePotentially);
		scorePanel.add(score);
		
		final JPanel fileIOPart = new JPanel();
		fileIOPart.setBackground(Color.WHITE);
		frame.add(fileIOPart, BorderLayout.SOUTH);

		 JButton saveGame = new JButton();
		 saveGame.setText("Save Game");
		 fileIOPart.add(saveGame);
		 
		 JButton newGame = new JButton();
		 newGame.setText("New Game");
		 fileIOPart.add(newGame);
		   		 
		final GameEngine panelBoard = new GameEngine(snakeBodyReload, scoreInt, score, length, blobXCoord, blobYCoord, blobTypeNumber);

		panelBoard.setBackground(Color.BLACK);
		frame.add(panelBoard, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.requestFocusInWindow();
		frame.setVisible(true);
		
		panelBoard.restart();
		
		 saveGame.addActionListener(new java.awt.event.ActionListener() {
		 @Override
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			 
			GameMethods game = panelBoard.getUnderlyingGameForIO(panelBoard); 
			
			LinkedList<Integer> maybeThisWorks2 = new LinkedList<Integer>();
			 
			int BlobXCoord = (int) game.blobPosition().getX();
			int BlobYCoord = (int) game.blobPosition().getY();
			int blobTypeNumber = game.getBlob().getTypeNumber();
			int currentScore = game.getScore();
			int currentLength = panelBoard.returnSnakeLength(panelBoard);
			
			java.util.List<Point> snakePartsCopy = game.getSnake().getSnakeParts(game.getSnake());
			
			maybeThisWorks2.add(currentScore);
			maybeThisWorks2.add(currentLength);
			maybeThisWorks2.add(blobTypeNumber);
			maybeThisWorks2.add(BlobXCoord);
			maybeThisWorks2.add(BlobYCoord);
			 
			for (int i = 0; i < snakePartsCopy.size(); i++) {
				maybeThisWorks2.add((int) snakePartsCopy.get(i).getX());
				maybeThisWorks2.add((int) snakePartsCopy.get(i).getY());
			}
			    
			 try {
			      OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream("previousCrazySnake.dat"), "UTF8");
			        
			      	for (int i = 0; i < maybeThisWorks2.size(); i++) {
			      		file.write(maybeThisWorks2.get(i));
			      	}
			      	file.close();
			      	
			    } catch (IOException e) {
			      System.out.println("Close File");
			    }
			 }
			 
		 }
		        );
		 
		 
		 newGame.addActionListener(new java.awt.event.ActionListener() {
			 @Override
			    public void actionPerformed(java.awt.event.ActionEvent evt) {
				 panelBoard.reset();
			 }
		 });
		 
	}

	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new RunGame());
	}
}
