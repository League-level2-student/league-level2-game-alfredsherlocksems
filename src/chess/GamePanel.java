package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, MouseListener {

	final static int SQUARESIZE = ChessRunner.WIDTH / 8;

	final static int WHITEROOK = 25;
	final static int WHITEKNIGHT = 23;
	final static int WHITEBISHOP = 24;
	final static int WHITEQUEEN = 29;
	final static int WHITEKING = 20;
	final static int WHITEPAWN = 21;

	final static int BLACKROOK = 15;
	final static int BLACKKNIGHT = 13;
	final static int BLACKBISHOP = 14;
	final static int BLACKQUEEN = 19;
	final static int BLACKKING = 10;
	final static int BLACKPAWN = 11;

	boolean pieceSelected = false;
	int intPieceSelected = 0;
	int rowSelected = 0;
	int columnSelected = 0;
	boolean blackToMove = false;
	boolean inCheck = false;
	boolean pieceBlocking = false;

	int[][] grid = { { WHITEROOK, WHITEKNIGHT, WHITEBISHOP, WHITEKING, WHITEQUEEN, WHITEBISHOP, WHITEKNIGHT, WHITEROOK },
			{ 0, WHITEPAWN, WHITEPAWN, 0, 0, WHITEPAWN, WHITEPAWN, WHITEPAWN },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN },
			{ BLACKROOK, BLACKKNIGHT, BLACKBISHOP, BLACKKING, BLACKQUEEN, BLACKBISHOP, BLACKKNIGHT, BLACKROOK } };
	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	Timer drawFrame;
	int currentState = GAME;
	int rowChange = 0;
	int colChange = 0;

	public GamePanel() {
		drawFrame = new Timer(1000 / 60, this);
		drawFrame.start();
	}

	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			DrawMenu(g);
		} else if (currentState == GAME) {
			DrawGame(g);
		} else {
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
		for (int rows = 0; rows < grid.length; rows++) {
			for (int col = 0; col < grid[rows].length; col++) {
				if ((rows + col) % 2 == 0) {
					g.setColor(Color.white);
				} else {
					g.setColor(Color.black);
				}
				g.fillRect(col * SQUARESIZE, rows * SQUARESIZE, SQUARESIZE, SQUARESIZE);
				// System.out.println("Columns = " + col + " and rows = " + rows + "." + " The
				// sum of the rows and columns is " + (col + rows) + ".");
			}
		}
		if (pieceSelected) {
			g.setColor(Color.red);
			g.fillRect(columnSelected * SQUARESIZE, rowSelected * SQUARESIZE, SQUARESIZE, SQUARESIZE);
		}
	}

	void DrawEnd(Graphics g) {

	}

	void Knight(int col, int row, int selectedSquare) {
		if (((rowSelected + 2) == row || (rowSelected - 2) == row) && ((columnSelected - 1) == col || (columnSelected + 1) == col)) {
			if (blackToMove && selectedSquare > 20
					|| !blackToMove && selectedSquare > 10 && selectedSquare < 20 || selectedSquare == 0) {
				grid[rowSelected][columnSelected] = 0;
				grid[row][col] = intPieceSelected;
				pieceSelected = false;
			}
		}
		else if (((rowSelected + 1) == row || (rowSelected - 1) == row) && ((columnSelected + 2) == col|| (columnSelected - 2) == col)) {
			if (blackToMove && selectedSquare > 20
					|| !blackToMove && selectedSquare > 10 && selectedSquare < 20 || selectedSquare == 0) {
				grid[rowSelected][columnSelected] = 0;
				grid[row][col] = intPieceSelected;
				pieceSelected = false;
			}
		}
	}
	
	void Rook(int col, int row, int selectedSquare) {

		if (rowSelected == row) {
			// need code to check if there's a piece blocking the column
			if (col > columnSelected) {
				for (int i = columnSelected + 1; i <= col - columnSelected; i++) {
					if (grid[row][i] > 0) {
						pieceBlocking = true;
					}
				}
			} else if (col < columnSelected) {
				for (int i = col; i < columnSelected - col; i++) {
					if (grid[row][i] > 0) {
						pieceBlocking = true;
					}
				}
			}
		}
		if (columnSelected == col) {
			// need code to check if there's a piece blocking the row
			if (row > rowSelected) {
				for (int i = rowSelected + 1; i < row; i++) {
					System.out.println(i);
					System.out.println(col);
					if (grid[i][col] > 0) {
						pieceBlocking = true;
					}
				}
			} 
			else if (row < rowSelected) {
				for (int i = row; i < rowSelected - row; i++) {
					System.out.println(i);
					System.out.println(col);
					if (grid[i][col] > 0) {
						pieceBlocking = true;
					}
				}
			}
		}
		if (!pieceBlocking) {
			if (blackToMove && selectedSquare > 20 || !blackToMove && selectedSquare > 10 && selectedSquare < 20
					|| selectedSquare == 0) {
				grid[rowSelected][columnSelected] = 0;
				grid[row][col] = intPieceSelected;
				pieceSelected = false;
			}
		}
	}
	
	void Bishop(int col, int row, int selectedSquare) {

		if (col > columnSelected) {
			colChange = 1;
		}
		else {
			colChange = -1;
		}
		if (row > rowSelected) {
			rowChange = 1;
		}
		else {
			rowChange = -1;
		}
		if (Math.abs(row - rowSelected) == Math.abs(col - columnSelected)) {
			for (int i = 1; i < Math.abs(row - rowSelected); i++) {
				int newRow = rowSelected + rowChange * i;
				int newCol = columnSelected + colChange * i;
				if (grid[newRow][newCol] == selectedSquare) {
					break;
				}
				if (grid[newRow][newCol] > 0) {
					pieceBlocking = true;
					break;
				}
			}
			if (!pieceBlocking) {
				if (blackToMove && selectedSquare > 20 || !blackToMove && selectedSquare > 10 && selectedSquare < 20
						|| selectedSquare == 0) {
					grid[rowSelected][columnSelected] = 0;
					grid[row][col] = intPieceSelected;
					pieceSelected = false;
				}
			}
		}
	}

	void Queen(int col, int row, int selectedSquare) {
		if ((rowSelected == row) || (columnSelected == col)) {
			Rook(col, row, selectedSquare);
		}
		else {
			Bishop(col, row, selectedSquare);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int col = arg0.getX() / SQUARESIZE;
		int row = arg0.getY() / SQUARESIZE;
		int selectedSquare = grid[row][col];
		System.out.println("piece = " + grid[row][col] + ".");
		System.out.println("The row is: " + row + ". the column is " + col + ".");
		// if a piece is already selected
		if (pieceSelected) {
			if (col == columnSelected && row == rowSelected) {
				pieceSelected = false;
			} else {
				// add an if statement to check if the selected square is the same color of the
				// piece your moving, and if it isn't, then move.
				if (intPieceSelected == WHITEROOK || intPieceSelected == BLACKROOK) {
					Rook(col, row, selectedSquare);
				}
				if (intPieceSelected == WHITEKNIGHT || intPieceSelected == BLACKKNIGHT) {
					Knight(col, row, selectedSquare);
				}
				if (intPieceSelected == WHITEBISHOP || intPieceSelected == BLACKBISHOP) {
					Bishop(col, row, selectedSquare);
				}
				if (intPieceSelected == WHITEQUEEN || intPieceSelected == BLACKQUEEN) {
					Queen(col, row, selectedSquare);
				}
				if (intPieceSelected == WHITEKING || intPieceSelected == BLACKKING) {

				}
				if (intPieceSelected == WHITEPAWN || intPieceSelected == BLACKPAWN) {

				}
				pieceBlocking = false;
			}
			// when you first select a piece.
		} else {
			if (blackToMove && selectedSquare < 20 && selectedSquare > 0) {
				pieceSelected = true;
				columnSelected = col;
				rowSelected = row;
				intPieceSelected = selectedSquare;
			} else if (!blackToMove && selectedSquare > 19) {
				pieceSelected = true;
				columnSelected = col;
				rowSelected = row;
				intPieceSelected = selectedSquare;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
