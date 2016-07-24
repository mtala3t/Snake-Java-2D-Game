/*
 * GameLoader.java
 *
 * Created on 22 ãÇíæ, 2007, 10:54 Õ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.mtala3t.snake2d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
*
* @author Mohammed.Talaat (mtala3t)
* @version 1.0
*/
@SuppressWarnings("serial")
public class GameBoardWindow extends JFrame implements ActionListener {

	private JMenuBar menuBar = null;
	private JMenu fileMenu = null;

	private JMenuItem newGameMenuItem = null;
	private JMenuItem exitGameMenuItem = null;

	public GameBoardWindow(int level) {

		setTitle("Snake2D Game - mtala3t");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 5, 1100, 700);
		setResizable(false);

		getContentPane().add(new GameBoardPanel(level));

		menuBar = new JMenuBar();

		fileMenu = new JMenu("File");

		newGameMenuItem = new JMenuItem("New Game");
		exitGameMenuItem = new JMenuItem("Exit");

		fileMenu.add(newGameMenuItem);
		fileMenu.add(exitGameMenuItem);

		menuBar.add(fileMenu);

		setJMenuBar(menuBar);

		newGameMenuItem.addActionListener(this);
		exitGameMenuItem.addActionListener(this);

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		if (source == newGameMenuItem) {
			setVisible(false);
			dispose();

			new MainScreen();
		}

		if (source == exitGameMenuItem) {
			System.exit(0);
		}

	}
}
