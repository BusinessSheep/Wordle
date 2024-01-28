package wordleClone;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputs implements KeyListener{
	private gamePanel panel;
	public KeyInputs(gamePanel panel) {
		this.panel = panel;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(panel.getWordle().getCurrentWordLen() < 5) {
			if(((int) e.getKeyChar() >=65 && (int) e.getKeyChar() <= 90) || ((int) e.getKeyChar() >= 97 && (int) e.getKeyChar() <= 122)) {
				if((int) e.getKeyChar() >= 97 && (int) e.getKeyChar() <= 122) {
					panel.getWordle().addLetter((char)(e.getKeyChar()-32));
				} else {
					panel.getWordle().addLetter(e.getKeyChar());
				}
			}
		} else {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && panel.getWordle().getAttempt() <= 5 && panel.getWordle().validWord(0, panel.getWordle().validWords.length)) {
				panel.getWordle().checkWord();
			}
		}
		if(!panel.getWordle().hasWon()) {
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				panel.getWordle().deleteLetter();
			}
		}
		
		
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
