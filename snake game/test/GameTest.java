import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GameTest {

	@Test
	public void testLengthAtGameStart() {
		Snake snake = new Snake();
		assertEquals(snake.getSnakeParts(snake).size(), snake.getLength());
	}
	
	@Test
	public void testSnakeMoves() {
		Snake snake = new Snake();
		snake.movementAlgorithm(snake.getSnakeHead(), 1, 0);
		assertEquals(snake.getSnakeParts(snake).size(), snake.getLength());
		assertEquals((int) snake.getSnakeParts(snake).get(0).getX(), (int) 107);
		assertEquals((int) snake.getSnakeParts(snake).get(0).getY(), (int) 100);
		assertEquals((int) snake.getSnakeParts(snake).get(1).getX(), (int) 100);
		assertEquals((int) snake.getSnakeParts(snake).get(1).getY(), (int) 100);
		assertEquals((int) snake.getSnakeParts(snake).get(2).getX(), (int) 75);
		assertEquals((int) snake.getSnakeParts(snake).get(2).getY(), (int) 100);
	}
	
	@Test
	public void testSnakeGrow() {
		Snake snake = new Snake();
		snake.growSnake();
		assertEquals(snake.getSnakeParts(snake).size(), snake.getLength());
	}
		
	@Test
	public void testCorrectBlobGeneratedFromNumber() {
		int BlobNumber = 4;
		int XPosition = 350;
		int YPosition = 400;
		
		GameMethods game = new GameMethods();
		Blob newBlob = game.getBlobFromNumber(BlobNumber, XPosition, YPosition);
		assertNotSame(game.getBlob(), newBlob);
		game.setBlob(game, newBlob);
		assertSame(game.getBlob(), newBlob);
	}
	
	@Test
	public void testBlobOnTopOfSnake() {
		GameMethods game = new GameMethods();
		Snake snake = new Snake();
		game.generateRandomBlob();
		Blob maybeBlob = game.generateRandomBlob();
		
		while (!game.isBlobOnTopOfSnake(snake, maybeBlob)) {
    		maybeBlob = game.generateRandomBlob();
		}
		
		for (int i = 0; i < snake.getSnakeParts(snake).size(); i++) {
		assertNotSame(game.blobPosition(), snake.getSnakeParts(snake).get(i));
	}
	}
	
	@Test
	public void testSnakeSetNewOrder() {
		Snake snake = new Snake();
		List<Point> snakePartsFirst = snake.getSnakeParts(snake);
		snake.setNewOrder();
		List<Point> snakePartsSecond = snake.getSnakeParts(snake);
		assertNotSame(snakePartsFirst, snakePartsSecond);
		assertEquals(snakePartsFirst.size(), snakePartsSecond.size());
		assertSame(snakePartsFirst.get(0), snakePartsSecond.get(2));
		assertSame(snakePartsFirst.get(1), snakePartsSecond.get(1));
		assertSame(snakePartsFirst.get(2), snakePartsSecond.get(0));
	}
	
	@Test
	public void testSnakeOutOfBounds() {
		GameMethods game = new GameMethods();
		Snake snake = game.getSnake();
		snake.movementAlgorithm(snake.getSnakeHead(), -400, -400);
		game.setSnakeHeadPosition(snake.getSnakeParts(snake).get(0));
		game.checkStatusOfSnake();
		assertFalse(game.getGameStatus());
		assertFalse(snake.getSnakeStatus());
	}
	
	@Test
	public void testTeleport() {
		GameMethods game = new GameMethods();
		Snake snake = game.getSnake();
		Point oldHead = snake.getSnakeHead();
		TeleportBlob previousBlob = new TeleportBlob("Teleport", Color.BLUE);
		previousBlob.desiredSnakeMovement(snake);
		game.setSnakeHeadPosition(snake.getSnakeParts(snake).get(0));
		assertNotSame(snake.getSnakeHead(), oldHead);
	}
	
	@Test
	public void testTransportBackFive() {
		GameMethods game = new GameMethods();
		Snake snake = game.getSnake();
		Point oldHead = snake.getSnakeHead();
		NormalBlob norm = new NormalBlob("Normal", Color.RED);
		norm.desiredSnakeMovement(snake);
		TransportBackFiveBlob previousBlob = new TransportBackFiveBlob("TransportBack", Color.YELLOW);
		snake.movementAlgorithm(snake.getSnakeHead(), 4, 0);
		snake.movementAlgorithm(snake.getSnakeHead(), 4, 0);
		snake.movementAlgorithm(snake.getSnakeHead(), 4, 0);
		snake.movementAlgorithm(snake.getSnakeHead(), 4, 0);
		snake.movementAlgorithm(snake.getSnakeHead(), 4, 0);
		previousBlob.desiredSnakeMovement(snake);
		game.setSnakeHeadPosition(snake.getSnakeParts(snake).get(0));
		Point newHead = snake.getSnakeHead();
		assertEquals((int) oldHead.getX(),(int) newHead.getX());
		assertEquals((int) oldHead.getY(),(int) newHead.getY());

	}
	
}

