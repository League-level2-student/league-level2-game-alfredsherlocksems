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

	int[][] grid = { { WHITEROOK, WHITEKNIGHT, WHITEBISHOP, WHITEQUEEN, WHITEKING, WHITEBISHOP, WHITEKNIGHT, WHITEROOK },
			{ WHITEPAWN, 0, WHITEPAWN, WHITEPAWN, WHITEPAWN, WHITEPAWN, WHITEPAWN, WHITEPAWN },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN, BLACKPAWN },
			{ BLACKROOK, BLACKKNIGHT, BLACKBISHOP, BLACKQUEEN, BLACKKING, BLACKBISHOP, BLACKKNIGHT, BLACKROOK } };
	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	Timer drawFrame;
	int currentState = GAME;

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

	void Rook(int col, int row, int selectedSquare) {

		if (rowSelected == row) {
			// need code to check if there's a piece blocking the column
			if (col > columnSelected) {
				for (int i = columnSelected + 1; i < col - columnSelected; i++) {
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
				for (int i = rowSelected + 1; i < row - rowSelected; i++) {
					if (grid[i][col] > 0) {
						pieceBlocking = true;
					}
				}
			} 
			else if (row < rowSelected) {
				for (int i = row; i < rowSelected - row; i++) {
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
		if (row > rowSelected) {
			for (int i = rowSelected + 1; i < row - rowSelected; i++) {
				if (col > columnSelected) {
					for (int j = columnSelected; j < col - columnSelected; j++) {
						if (grid[i][j] > 0) {
							pieceBlocking = true;
						}
						i++;
					}
				}
				else {
					for (int j = columnSelected + 1; j > columnSelected - col; j--) {
						if (grid[i][j] > 0) {
							pieceBlocking = true;
						}
						i++;
					}
				}
			}
		}
		else {
			for (int i = rowSelected + 1; i > rowSelected - row; i--) {
				if (col > columnSelected) {
					for (int j = columnSelected + 1; j < col - columnSelected; j++) {
						if (grid[i][j] > 0) {
						pieceBlocking = true;
						}
						i--;
					}
				}
				else {
					for (int j = columnSelected; j > columnSelected - col; j--) {
						if (grid[i][j] > 0) {
						pieceBlocking = true;
						}
						i--;
					}
				}
			}
		}
		if (!pieceBlocking) {
			if (blackToMove && selectedSquare > 20
					|| !blackToMove && selectedSquare > 10 && selectedSquare < 20 || selectedSquare == 0) {
				grid[rowSelected][columnSelected] = 0;
				grid[row][col] = intPieceSelected;
				pieceSelected = false;
			}
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
					if (((rowSelected + 2) == row || (rowSelected - 2) == row && (columnSelected - 1) == col
							|| (columnSelected + 1) == col)
							|| ((rowSelected + 1) == row || (rowSelected - 1) == row && (columnSelected + 2) == col
									|| (columnSelected - 2) == col)) {
						if (blackToMove && selectedSquare > 20
								|| !blackToMove && selectedSquare > 10 && selectedSquare < 20 || selectedSquare == 0) {
							grid[rowSelected][columnSelected] = 0;
							grid[row][col] = intPieceSelected;
							pieceSelected = false;
						}
					}
				}
				if (intPieceSelected == WHITEBISHOP || intPieceSelected == BLACKBISHOP) {
					Bishop(col, row, selectedSquare);
				}
				if (intPieceSelected == WHITEQUEEN || intPieceSelected == BLACKQUEEN) {

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
