package chess;

import javax.swing.JFrame;

public class ChessRunner {
	JFrame frame;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	GamePanel panel;
	
	public ChessRunner() {
		frame = new JFrame();
		panel = new GamePanel();
	}
	public static void main (String [] args) {
		ChessRunner chessRunner = new ChessRunner();
		chessRunner.setup();
	}
	void setup () {
		frame.add(panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.addMouseListener(panel);
	}
}
