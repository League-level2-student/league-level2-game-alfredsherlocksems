package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ChessRunner implements ActionListener {
	JFrame frame;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	GamePanel panel;
	JButton button = new JButton();
	
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
		panel.add(button);
		button.setLocation(400, 400);
		button.setSize(50, 50);
		button.setText("Surrender");
		button.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (panel.blackToMove) {
			JOptionPane.showMessageDialog(null, "White wins!");
		}
		else {
			JOptionPane.showMessageDialog(null, "Black wins!");
		}
		frame = new JFrame();
		panel = new GamePanel();
		setup();
	}
}
