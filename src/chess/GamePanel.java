package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	int [][] grid = {
			{25, 23, 24, 29, 20, 24, 23, 25},
			{1, 1, 1, 1, 1, 1, 1, 1},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{11, 11, 11, 11, 11, 11, 11, 11},
			{15, 13, 14, 19, 10, 14, 13, 15}
	};
	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	Timer drawFrame;	
	int currentState = GAME;
	
	
	public GamePanel () {
		drawFrame = new Timer(1000/60, this);
		drawFrame.start();
	}
	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			DrawMenu(g);
		}
		else if(currentState == GAME) {
			DrawGame(g);
		}
		else {
			DrawEnd(g);
		}
	}
	
	
	void UpdateMenuState() {
		
	}
	
	void UpdateGameState() {
		
	}
	
	void UpdateEndState() {
		
	}
	
	
	void DrawMenu(Graphics g) {
		
	}
	void DrawGame(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, ChessRunner.WIDTH, ChessRunner.HEIGHT);
		for (int rows = 0; rows < grid.length; rows++)  {
			for (int col = 0; col < grid[rows].length; col++) {
				if (rows + col % 2 == 0) {
					g.setColor(Color.white);
				}
				else {
					g.setColor(Color.black);
				}
				g.fillRect(col * ChessRunner.WIDTH / 8, rows * ChessRunner.HEIGHT / 8, ChessRunner.WIDTH / 8, ChessRunner.HEIGHT / 8);
				System.out.println("Columns = " + col + " and rows = " + rows + "." + " The sum of the rows and columns is " + (col + rows) + ".");
			} 
		}
	}
	void DrawEnd(Graphics g) {	
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
}
