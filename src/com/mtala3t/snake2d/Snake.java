/*
 * SnakeBody.java
 *
 * Created on 22 ãÇíæ, 2007, 10:54 Õ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mtala3t.snake2d;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
*
* @author Mohammed.Talaat (mtala3t)
* @version 1.0
*/
public class Snake {

	private static final int DEFAULT_SNAKE_LENGTH = 5;
	private static final int DEFAULT_SNAKE_DIRECTION = 3;

	private ArrayList<Ellipse2D.Double> snakeBody = new ArrayList<Ellipse2D.Double>();

	private int direction;

	public Snake() {

		direction = DEFAULT_SNAKE_DIRECTION;

		for (int i = 0; i < DEFAULT_SNAKE_LENGTH; i++) {
			snakeBody.add(new Ellipse2D.Double(355 - i * 16, 191, 16, 16));
		}
	}

	public void setDirection(int dir) {
		if (direction >= 3 && dir < 3) {
			this.direction = dir;
		} else if (direction <= 2 && dir > 2) {
			this.direction = dir;
		}
	}

	public void move() {
		for (int i = getLength() - 1; i > 0; i--) {
			snakeBody.set(i, (Ellipse2D.Double) snakeBody.get(i - 1));
		}

		if (direction == 1) {
			decreaseY();
		} else if (direction == 2) {
			increaseY();
		} else if (direction == 3) {
			increaseX();
		} else if (direction == 4) {
			decreaseX();
		}
	}

	public void eat() {

		snakeBody.add(snakeBody.get(getLength() - 1));
	}

	public void increaseY() {
		Ellipse2D.Double temp = (Ellipse2D.Double) snakeBody.get(0);
		Ellipse2D.Double elli = new Ellipse2D.Double(temp.x, temp.y + 16,
				temp.getWidth(), temp.getHeight());

		snakeBody.set(0, (Ellipse2D.Double) elli);

	}

	public void decreaseY() {
		Ellipse2D.Double temp = (Ellipse2D.Double) snakeBody.get(0);
		Ellipse2D.Double elli = new Ellipse2D.Double(temp.x, temp.y - 16,
				temp.getWidth(), temp.getHeight());

		snakeBody.set(0, (Ellipse2D.Double) elli);
	}

	public void increaseX() {
		Ellipse2D.Double temp = (Ellipse2D.Double) snakeBody.get(0);
		Ellipse2D.Double elli = new Ellipse2D.Double(temp.x + 16, temp.y,
				temp.getWidth(), temp.getHeight());

		snakeBody.set(0, (Ellipse2D.Double) elli);
	}

	public void decreaseX() {
		Ellipse2D.Double temp = (Ellipse2D.Double) snakeBody.get(0);
		Ellipse2D.Double elli = new Ellipse2D.Double(temp.x - 16, temp.y,
				temp.getWidth(), temp.getHeight());

		snakeBody.set(0, (Ellipse2D.Double) elli);
	}

	public ArrayList<Ellipse2D.Double> getSnakeBody() {
		return snakeBody;
	}

	public int getLength() {
		return snakeBody.size();
	}

	public int getDirection() {

		return direction;
	}

	public Ellipse2D.Double getHead() {

		return snakeBody.get(0);

	}

}
