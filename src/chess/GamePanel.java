package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
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
	boolean rookChecking = false;
	boolean bishopChecking = false;
	boolean knightChecking = false;
	boolean queenChecking = false;
	boolean pawnChecking = false;
	int lastCol = 0;
	int lastRow = 0;
	int colCheck = -1;
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	
	
	int[][] grid = { { WHITEROOK, WHITEKNIGHT, WHITEBISHOP, WHITEKING, WHITEQUEEN, WHITEBISHOP, WHITEKNIGHT, WHITEROOK },
			{ WHITEPAWN, WHITEPAWN, WHITEPAWN, WHITEPAWN, WHITEPAWN, WHITEPAWN, WHITEPAWN, WHITEPAWN },
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
		if (needImage) {
		    loadImage ("rocket.png");
		}
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
				switch (grid[rows][col]) {
				case 0: continue; 
				case 11: loadImage("BlackScuffPawn.png"); break;
				case 21: loadImage("WhiteScuffPawn.png"); break;
				case 10: loadImage("BlackScuffKing.png"); break;
				case 20: loadImage("WhiteScuffKing.png"); break;
				case 13: loadImage("BlackScuffKnight.png"); break;
				case 23: loadImage("WhiteScuffKnight.png"); break;
				case 14: loadImage("BlackScuffBishop.png"); break;
				case 24: loadImage("WhiteScuffBishop.png"); break;
				case 19: loadImage("BlackScuffQueen.png"); break;
				case 29: loadImage("WhiteScuffQueen.png"); break;
				case 15: loadImage("BlackScuffRook.png"); break;
				case 25: loadImage("WhiteScuffRook.png"); break;
				}
				g.drawImage(image, col * SQUARESIZE, rows * SQUARESIZE, SQUARESIZE, SQUARESIZE, null);
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
	
	void loadImage(String imageFile) {
	        try {
	            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
		    gotImage = true;
	        } catch (Exception e) {
	            
	        }
	}

	
	
	void Knight(int col, int row, int selectedSquare) {
		if (((rowSelected + 2) == row || (rowSelected - 2) == row) && ((columnSelected - 1) == col || (columnSelected + 1) == col)) {
			if (blackToMove && selectedSquare > 20
					|| !blackToMove && selectedSquare > 10 && selectedSquare < 20 || selectedSquare == 0) {
				grid[rowSelected][columnSelected] = 0;
				grid[row][col] = intPieceSelected;
				if (inCheck) {
					UltimateCheckingCode();
				}
				if (!inCheck){
					pieceSelected = false;
					blackToMove = !blackToMove;
					//"brilliant" plan -- check each possible square with if statements.
					knightChecking(col, row);
				}
				else {
					grid[rowSelected][columnSelected] = intPieceSelected;
					grid[row][col] = 0;
				}
			}
		}
		else if (((rowSelected + 1) == row || (rowSelected - 1) == row) && ((columnSelected + 2) == col|| (columnSelected - 2) == col)) {
			if (blackToMove && selectedSquare > 20
					|| !blackToMove && selectedSquare > 10 && selectedSquare < 20 || selectedSquare == 0) {
				grid[rowSelected][columnSelected] = 0;
				grid[row][col] = intPieceSelected;
				if (inCheck) {
					UltimateCheckingCode();
				}
				if (!inCheck){
					pieceSelected = false;
					blackToMove = !blackToMove;
					//"brilliant" plan -- check each possible square with if statements.
					knightChecking(col, row);
				}
				else {
					grid[rowSelected][columnSelected] = intPieceSelected;
					grid[row][col] = 0;
				}
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
				if (inCheck) {
					UltimateCheckingCode();
				}
				if (!inCheck){
					pieceSelected = false;
					blackToMove = !blackToMove;
					//"brilliant" plan -- check each possible square with if statements.
					rookChecking(col, row);
				}
				else {
					grid[rowSelected][columnSelected] = intPieceSelected;
					grid[row][col] = 0;
				}
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
					if (inCheck) {
						UltimateCheckingCode();
					}
					if (!inCheck){
						pieceSelected = false;
						blackToMove = !blackToMove;
						//"brilliant" plan -- check each possible square with if statements.
						bishopChecking(col, row);
					}
					else {
						grid[rowSelected][columnSelected] = intPieceSelected;
						grid[row][col] = 0;
					}
				}
			}
		}
	}

	void Queen(int col, int row, int selectedSquare) {
		if ((rowSelected == row) || (columnSelected == col)) {
			Rook(col, row, selectedSquare);
			if (rookChecking && inCheck) {
				rookChecking = false;
				queenChecking = true;
				lastCol=col;
				lastRow=row;
			}
		}
		else {
			Bishop(col, row, selectedSquare);
			if (bishopChecking && inCheck) {
				bishopChecking = false;
				queenChecking = true;
				lastCol = col;
				lastRow = row;
			}
		}
	}
	
	void Pawn(int col, int row, int selectedSquare) {
		
		if (selectedSquare == 0) {
			if (!blackToMove && (intPieceSelected == WHITEPAWN && row == rowSelected + 1 && col == columnSelected) || (rowSelected == 1 && row == 3 && col == columnSelected)) {
				grid[rowSelected][columnSelected] = 0;
				grid[row][col] = intPieceSelected;
				if (inCheck) {
					UltimateCheckingCode();
				}
				if (!inCheck){
					pieceSelected = false;
					blackToMove = !blackToMove;
					//"brilliant" plan -- check each possible square with if statements.
					pawnChecking(col, row);
				}
				else {
					grid[rowSelected][columnSelected] = intPieceSelected;
					grid[row][col] = 0;
				}
			}
			else if (blackToMove && (intPieceSelected == BLACKPAWN && row == rowSelected - 1 && col == columnSelected) || (rowSelected == 6 && row == 4 && col == columnSelected)) {
				grid[rowSelected][columnSelected] = 0;
				grid[row][col] = intPieceSelected;
				if (inCheck) {
					UltimateCheckingCode();
				}
				if (!inCheck){
					pieceSelected = false;
					blackToMove = !blackToMove;
					//"brilliant" plan -- check each possible square with if statements.
					pawnChecking(col, row);
				}
				else {
					grid[rowSelected][columnSelected] = intPieceSelected;
					grid[row][col] = 0;
				}
			}
		}
		else {
			if (intPieceSelected == WHITEPAWN) {
				if (row == rowSelected + 1 && col == columnSelected + 1 || col == columnSelected - 1) {
					if (blackToMove && selectedSquare > 20 || !blackToMove && selectedSquare > 10 && selectedSquare < 20) {
						grid[rowSelected][columnSelected] = 0;
						grid[row][col] = intPieceSelected;
						if (inCheck) {
							UltimateCheckingCode();
						}
						if (!inCheck){
							pieceSelected = false;
							blackToMove = !blackToMove;
							//"brilliant" plan -- check each possible square with if statements.
							pawnChecking(col, row);
						}
						else {
							grid[rowSelected][columnSelected] = intPieceSelected;
							grid[row][col] = 0;
						}
					}
				}
			}
			else {
				if (intPieceSelected == BLACKPAWN) {
					if (row == rowSelected - 1 && col == columnSelected + 1 || col == columnSelected - 1) {
						if (blackToMove && selectedSquare > 20 || !blackToMove && selectedSquare > 10 && selectedSquare < 20) {
							grid[rowSelected][columnSelected] = 0;
							grid[row][col] = intPieceSelected;
							if (inCheck) {
								UltimateCheckingCode();
							}
							if (!inCheck){
								pieceSelected = false;
								blackToMove = !blackToMove;
								//"brilliant" plan -- check each possible square with if statements.
								pawnChecking(col, row);
							}
							else {
								grid[rowSelected][columnSelected] = intPieceSelected;
								grid[row][col] = 0;
							}
						}
					}
				}
			}
		}
	}
	
	void King(int col, int row, int selectedSquare) {
		if ((row == rowSelected + 1 || row  == rowSelected - 1 || row == rowSelected) && (col == columnSelected + 1 || col == columnSelected - 1 || col == columnSelected)) {
			if (blackToMove && selectedSquare > 20 || !blackToMove && selectedSquare > 10 && selectedSquare < 20
					|| selectedSquare == 0) {
				inCheck = false;
				grid[rowSelected][columnSelected] = 0;
				grid[row][col] = intPieceSelected;
				if (rookChecking) {
					rookChecking(lastCol, lastRow);
				}
				else if (knightChecking) {
					knightChecking(lastCol, lastRow);
				}
				else if (bishopChecking) {
					bishopChecking(lastCol, lastRow);
				}
				else if (queenChecking) {
					Queen(lastCol, lastRow, selectedSquare);
				}
				else if (pawnChecking) {
					pawnChecking(lastCol, lastRow);
				}
				UltimateCheckingCode();
				if (!inCheck) {
					pieceSelected = false;
					blackToMove = !blackToMove;
				}
				else {
					grid[row][col] = 0;
					grid[rowSelected][columnSelected] = intPieceSelected;
				}
			}
		}
	}
	
	void rookChecking(int col, int row) {
		for (int i = 0; i < 8; i++) {
			if ((grid[i][col] == 10 && blackToMove) || (grid[i][col] == 20 && !blackToMove)) {
				rookChecking = true;
				inCheck = true;
				lastCol = col;
				lastRow = row;
			}
			else if ((grid[row][i] == 10 && blackToMove) || (grid[row][i] == 20 && !blackToMove)) {
				rookChecking = true;
				inCheck = true;
				lastCol = col;
				lastRow = row;
			}
			if (blackToMove && grid[i][col] != 20) {
				if (grid[i][col] > 0) {
				break;
				}
			}
			if (blackToMove && grid[row][i] != 20) {
				if (grid[row][i] > 0) {
					break;
				}
			}
			if (!blackToMove && grid[i][col] != 10) {
				if (grid[i][col] > 0) {
				break;
				}
			}
			if (!blackToMove && grid[row][i] != 10) {
				if (grid[row][i] > 0) {
					break;
				}
			}
		}
	}

	void knightChecking(int col, int row) {
		if (blackToMove) {
			if (row+2 < 8 && col+1< 8 && grid[row+2][col+1] == 10) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row+2 < 8 && col-1> -1 && grid[row+2][col-1] == 10) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row-2 > -1 && col+1 < 8 &&  grid[row-2][col+1] == 10) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row-2 > -1 && col-1> -1 &&  grid[row-2][col-1] == 10) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row+1 < 8 && col+2< 8 &&  grid[row+1][col+2] == 10) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row+1 < 8 && col-2> -1 &&  grid[row+1][col-2] == 10) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row-1 > -1 && col+2< 8 &&  grid[row-1][col+2] == 10) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row-1 > -1 && col-2> -1 &&  grid[row-1][col-2] == 10) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
		}
		else {
			if (row+2 < 8 && col+1< 8 && grid[row+2][col+1] == 20) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row+2 < 8 && col-1> -1 && grid[row+2][col-1] == 20) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row-2 > -1 && col+1 < 8 &&  grid[row-2][col+1] == 20) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row-2 > -1 && col-1> -1 &&  grid[row-2][col-1] == 20) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row+1 < 8 && col+2< 8 &&  grid[row+1][col+2] == 20) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row+1 < 8 && col-2> -1 &&  grid[row+1][col-2] == 20) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row-1 > -1 && col+2< 8 &&  grid[row-1][col+2] == 20) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
			else if (row-1 > -1 && col-2> -1 &&  grid[row-1][col-2] == 20) {
				inCheck=true; knightChecking = true; lastCol = col; lastRow = row;;
			}
		}	
	}
	
	
	void bishopChecking(int col, int row) {
		for (int i = 1; i < 7; i++) {
			int newRow = row + rowChange * i;
			int newCol = col + colChange * i;
			if (newCol == 8 || newRow == -1 || newCol == -1) {
				break;
			}
			else if ((blackToMove && grid[newRow][newCol] == 20) || !(blackToMove && grid[newRow][newCol] == 10)) {
				inCheck = true;
				bishopChecking = true; lastCol = col; lastRow = row;
				break;
			}
			else if (grid[newRow][newCol] > 0) {
				break;
			}
		}
		colChange = -1;
		rowChange = 1;
		if (!inCheck) {
			for (int i = 1; i < 7; i++) {
				int newRow = row + rowChange * i;
				int newCol = col + colChange * i;
				if (newCol == -1 || newRow == -1) {
					break;
				}
				else if ((blackToMove && grid[newRow][newCol] == 20) || (!blackToMove && grid[newRow][newCol] == 10)) {
					inCheck = true;
					bishopChecking = true; lastCol = col; lastRow = row;
					break;
				}
				else if (grid[newRow][newCol] > 0) {
					break;
				}
			}
		}
		colChange = 1;
		rowChange = -1;
		if (!inCheck) {
			for (int i = 1; i < 7; i++) {
				int newRow = row + rowChange * i;
				int newCol = col + colChange * i;
				if (newCol == 8 || newRow == 8 || newRow == -1 || newCol == -1) {
					break;
				}
				else if ((blackToMove && grid[newRow][newCol] == 20) || (!blackToMove && grid[newRow][newCol] == 10)) {
					inCheck = true;
					bishopChecking = true; lastCol = col; lastRow = row;
					break;
				}
				else if (grid[newRow][newCol] > 0) {
					break;
				}	
			}
		}
		colChange = -1;
		rowChange = -1;
		if (!inCheck ) {
			for (int i = 1; i < 7; i++) {
				int newRow = row + rowChange * i;
				int newCol = col + colChange * i;
				if (newCol == -1 || newRow == 8 || newRow == -1) {
					break;
				}
				else if ((blackToMove && grid[newRow][newCol] == 20) || (!blackToMove && grid[newRow][newCol] == 10)) {
					inCheck = true;
					bishopChecking = true; lastCol = col; lastRow = row;
					break;
				}
				else if (grid[newRow][newCol] > 0) {
					break;
				}
			}
		}
	}
	
	
	void pawnChecking(int col, int row) {
		if (row != 7 || row !=0) {
			if ((col != 7 && grid[row + 1][col + 1] == 10) || (col != 0 && grid[row + 1][col - 1] == 10)) {
				inCheck = true;
				pawnChecking = true;
				lastCol = col;
				lastRow = row;
			}
		}
	}
	
	void UltimateCheckingCode() {
		colCheck = 0;
		for (int i = 0; i < 64; i++) {
			if (blackToMove) {
				if (grid[i][colCheck] == WHITEPAWN) {
					pawnChecking(i, colCheck);
					if (inCheck) {
						break;
					}
				}
				if (grid[i][colCheck] == WHITEKNIGHT) {
					knightChecking(i, colCheck);
					if (inCheck) {
						break;
					}
				}
				if (grid[i][colCheck] == WHITEBISHOP) {
					bishopChecking(i, colCheck);
					if (inCheck) {
						break;
					}
				}
				if (grid[i][colCheck] == WHITEQUEEN) {
					bishopChecking(i, colCheck);
					if (inCheck) {
						break;
					}
					rookChecking(i, colCheck);
					if (inCheck) {
						break;
					}
				}
				if (grid[i][colCheck] == WHITEROOK) {
					rookChecking(i, colCheck);
					if (inCheck) {
						break;
					}
				}
			}
			else {
				if (grid[i][colCheck] == BLACKPAWN) {
					pawnChecking(i, colCheck); 
					if (inCheck) {
						break;
					}
				}
				if (grid[i][colCheck] == BLACKKNIGHT) {
					knightChecking(i, colCheck);
					if (inCheck) {
						break;
					}
				}
				if (grid[i][colCheck] == BLACKBISHOP) {
					bishopChecking(i, colCheck);
					if (inCheck) {
						break;
					}
				}
				if (grid[i][colCheck] == BLACKQUEEN) {
					bishopChecking(i, colCheck);
					if (inCheck) {
						break;
					}
					rookChecking(i, colCheck);
					if (inCheck) {
						break;
					}
				}
				if (grid[i][colCheck] == BLACKROOK) {
					rookChecking(i, colCheck);
					if (inCheck) {
						break;
					}
				}
			}
			if ((i + 1) % 8 == 0) {
			colCheck+=1;
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
		System.out.println("inCheck = " + inCheck + ".");
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
					King(col, row, selectedSquare);
				}
				if (intPieceSelected == WHITEPAWN || intPieceSelected == BLACKPAWN) {
					Pawn(col, row, selectedSquare);
				}
				pieceBlocking = false;
				if (inCheck) {
					
				}
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
