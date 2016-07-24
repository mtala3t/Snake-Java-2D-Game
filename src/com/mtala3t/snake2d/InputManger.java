/*
 * InputManger.java
 *
 * Created on 28 ãÇíæ, 2007, 01:32 ã
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mtala3t.snake2d;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
*
* @author Mohammed.Talaat (mtala3t)
* @version 1.0
*/
public class InputManger implements KeyListener {

	private GameBoardPanel gameBoard;

	public InputManger(GameBoardPanel gameBoard) {
		this.gameBoard = gameBoard;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP) {

			gameBoard.changeSnakeDirection(1);

		} else if (key == KeyEvent.VK_DOWN) {

			gameBoard.changeSnakeDirection(2);

		} else if (key == KeyEvent.VK_RIGHT) {

			gameBoard.changeSnakeDirection(3);

		} else if (key == KeyEvent.VK_LEFT) {

			gameBoard.changeSnakeDirection(4);

		} else if (key == KeyEvent.VK_SPACE) {

			if (gameBoard.isGameRunning()) {
				gameBoard.pauseGame();

			} else {
				gameBoard.startGame();

			}

		} else if (key == KeyEvent.VK_ESCAPE) {

			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

}
