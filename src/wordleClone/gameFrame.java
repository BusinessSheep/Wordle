package wordleClone;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;


public class gameFrame extends JFrame {
	private JFrame frame;
	
	public gameFrame(gamePanel panel) {
		frame = new JFrame();
		frame.setTitle("NOT WORDLE DON'T SUE ME :3");
		frame.add(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		pack();
		frame.setVisible(true);
		frame.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
}
