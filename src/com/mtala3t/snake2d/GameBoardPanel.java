package com.mtala3t.snake2d;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.mtala3t.snake2d.sound.SoundManger;

/**
 *
 * @author Mohammed.Talaat (mtala3t)
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel implements ActionListener {

	/** Creates a new instance of GameBoard */

	private Snake snake;
	private SnakeFood snakeFood;

	private InputManger inputManager;
	private SoundManger soundManger = null;

	private Timer gameThread;
	private Timer timerThread;

	private boolean isGameOver = false;

	private int timer = 0;
	private int playerScore = 0;

	private String soundFilePath = "start.wav";

	public GameBoardPanel(int level) {

		setBackground(Color.BLACK);
		setFocusable(true);

		snake = new Snake();
		snakeFood = new SnakeFood();

		inputManager = new InputManger(this);
		soundManger = new SoundManger(soundFilePath);

		gameThread = new Timer(getDelay(level), this);

		timerThread = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (isGameOver()) {
					stopGame();
				}

				timer++;
			}
		});

		// timerThread.setLogTimers(true);
		// gameThread.setLogTimers(true);

		addKeyListener(inputManager);

	}

	private int getDelay(int level) {

		int delay = 0;

		if (level == 1) {
			delay = 140;
		} else if (level == 2) {
			delay = 70;

		} else if (level == 3) {
			delay = 40;

		}
		return delay;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);
	}

	public void doDrawing(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		if (isGameRunning()) {

			snake.move();

			checkCollision();

			DrawSnakeFood(g2);

		}

		DrawStatusbar(g2);

		DrawBoundry(g2);

		DrawSnake(g2);

	}

	public void DrawBoundry(Graphics2D g2) {
		for (int i = 0; i < 17; i++) {
			Rectangle2D.Double rect = new Rectangle2D.Double(227.0 - i,
					127.0 - i, 624, 480);

			g2.setColor(Color.YELLOW);

			g2.draw(rect);

		}
	}

	public void DrawSnake(Graphics2D g2) {

		for (int i = 0; i < snake.getSnakeBody().size(); i++) {

			if (i == 0) {
				g2.setColor(Color.RED);
				g2.fill(snake.getSnakeBody().get(i));

			} else {
				g2.setColor(Color.ORANGE);
				g2.draw(snake.getSnakeBody().get(i));
			}

		}
	}

	public void DrawSnakeFood(Graphics2D g2) {
		g2.setColor(Color.GREEN);
		g2.fill(snakeFood.getFood());
	}

	public void DrawStatusbar(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		g2.drawString("Snake2D Game", 390, 50);
		g2.setColor(Color.ORANGE);
		g2.drawString("mtala3t", 450, 100);

		g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		g2.setColor(Color.WHITE);
		g2.drawString("Press Esc for exit!", 5, 20);
		g2.drawString("Press Spacebar for pause!", 5, 50);

		g2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		g2.drawString("Time: ", 210, 100);
		g2.drawString("Your Score: ", 680, 100);
		g2.setColor(Color.BLUE);
		g2.drawString("" + playerScore, 810, 100);
		g2.drawString("" + timer, 270, 100);

		if (isGameOver()) {
			g2.setColor(Color.WHITE);
			g2.drawString("Game Over!", 480, 350);

		} else if (!isGameRunning()) {
			g2.setColor(Color.WHITE);
			g2.drawString("Press SpaceBar to Start Game!", 400, 500);
		}

	}

	public void changeSnakeDirection(int direction) {
		this.snake.setDirection(direction);
	}

	public void checkCollision() {

		if (isSelfCollisioned() || isBoundryCollisioned()) {

			isGameOver = true;

			stopGame();

		}

		if (isFoodCollisioned()) {

			snake.eat();
			snakeFood = new SnakeFood();
			playerScore += 5;
		}
	}

	public boolean isBoundryCollisioned() {
		if (snake.getDirection() == 1) {
			double centerY = ((Ellipse2D.Double) snake.getSnakeBody().get(0))
					.getMinY();
			return centerY < 127;
		} else if (snake.getDirection() == 2) {
			double centerY = ((Ellipse2D.Double) snake.getSnakeBody().get(0))
					.getMaxY();
			return centerY > 591;
		} else if (snake.getDirection() == 3) {
			double centerX = ((Ellipse2D.Double) snake.getSnakeBody().get(0)).x;
			return centerX > 819;
		} else if (snake.getDirection() == 4) {
			double centerX = ((Ellipse2D.Double) snake.getSnakeBody().get(0))
					.getMinX();
			return centerX < 227.0;
		}
		return false;
	}

	public boolean isSelfCollisioned() {

		if (snake.getDirection() == 1) {
			for (int i = 1; i < snake.getSnakeBody().size(); i++) {
				if ((((Ellipse2D.Double) snake.getSnakeBody().get(0)).getMinY() == ((Ellipse2D.Double) snake
						.getSnakeBody().get(i)).getMaxY())
						&& (((Ellipse2D.Double) snake.getSnakeBody().get(0))
								.getCenterX() == ((Ellipse2D.Double) snake
								.getSnakeBody().get(i)).getCenterX())) {
					return true;
				}
			}

		} else if (snake.getDirection() == 2) {
			for (int i = 1; i < snake.getSnakeBody().size(); i++) {
				if ((((Ellipse2D.Double) snake.getSnakeBody().get(0)).getMaxY() == ((Ellipse2D.Double) snake
						.getSnakeBody().get(i)).getMinY())
						&& (((Ellipse2D.Double) snake.getSnakeBody().get(0))
								.getCenterX() == ((Ellipse2D.Double) snake
								.getSnakeBody().get(i)).getCenterX())) {
					return true;
				}
			}

		} else if (snake.getDirection() == 3) {
			for (int i = 1; i < snake.getSnakeBody().size(); i++) {
				if ((((Ellipse2D.Double) snake.getSnakeBody().get(0)).getMaxX() == ((Ellipse2D.Double) snake
						.getSnakeBody().get(i)).getMinX())
						&& (((Ellipse2D.Double) snake.getSnakeBody().get(0))
								.getCenterY() == ((Ellipse2D.Double) snake
								.getSnakeBody().get(i)).getCenterY())) {
					return true;
				}
			}

		} else if (snake.getDirection() == 4) {
			for (int i = 1; i < snake.getSnakeBody().size(); i++) {
				if ((((Ellipse2D.Double) snake.getSnakeBody().get(0)).getMinX() == ((Ellipse2D.Double) snake
						.getSnakeBody().get(i)).getMaxX())
						&& (((Ellipse2D.Double) snake.getSnakeBody().get(0))
								.getCenterY() == ((Ellipse2D.Double) snake
								.getSnakeBody().get(i)).getCenterY())) {
					return true;
				}
			}
		}

		return false;

	}

	public boolean isFoodCollisioned() {

		boolean collisionedWithFood = false;

		int direction = snake.getDirection();

		Ellipse2D.Double head = snake.getHead();

		if (direction == 1) {
			if ((head.getCenterY() == snakeFood.getFood().getCenterY())
					&& (head.getCenterX() == snakeFood.getFood().getCenterX())) {
				collisionedWithFood = true;
			} else
				collisionedWithFood = false;
		} else if (direction == 2) {

			if ((head.getCenterY() == snakeFood.getFood().getCenterY())
					&& (head.getCenterX() == snakeFood.getFood().getCenterX())) {
				collisionedWithFood = true;
			} else
				collisionedWithFood = false;

		} else if (direction == 3) {
			if ((head.getCenterX() == snakeFood.getFood().getCenterX())
					&& (head.getCenterY() == snakeFood.getFood().getCenterY())) {
				collisionedWithFood = true;
			} else
				collisionedWithFood = false;
		} else if (direction == 4) {
			if ((head.getCenterX() == snakeFood.getFood().getCenterX())
					&& (head.getCenterY() == snakeFood.getFood().getCenterY())) {
				collisionedWithFood = true;
			} else
				collisionedWithFood = false;
		}

		return collisionedWithFood;

	}

	public void startGame() {

		if (gameThread.isRunning()) {
			gameThread.restart();
			timerThread.restart();
			soundManger.startSound();

		} else {
			gameThread.start();
			timerThread.start();
			soundManger.startSound();
		}

	}

	public void pauseGame() {

		gameThread.stop();
		timerThread.stop();
		soundManger.pauseSound();
		repaint();

	}

	public void stopGame() {

		gameThread.stop();
		timerThread.stop();
		soundManger.stopSound();

	}

	public boolean isGameRunning() {
		return gameThread.isRunning() && !isGameOver();
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		repaint();

	}

}
