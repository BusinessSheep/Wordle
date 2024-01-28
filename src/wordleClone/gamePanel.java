package wordleClone;

import java.awt.Dimension;

import java.awt.Graphics;

import javax.swing.JPanel;


public class gamePanel extends JPanel{
	private Wordle wordle;
	
	public gamePanel(Wordle wordle) {
		this.wordle = wordle;
		setFocusable(true);
		setPreferredSize(new Dimension(500, 720));
		addKeyListener(new KeyInputs(this));
	}
	
	public Wordle getWordle() {
		return wordle;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		wordle.draw(g);
	}
}
