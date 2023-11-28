package id.ac.its.fox.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent keyEvent) {
		GamePanel.gsm.keyPressed(keyEvent.getKeyCode());
		int keyCode = keyEvent.getKeyCode();
		boolean pause = GamePanel.pause;
		if(keyCode == KeyEvent.VK_ESCAPE) {
			if(!pause)
				pause = true;
			else
				pause = false;
		}
		if(keyCode == KeyEvent.VK_ENTER && pause)
			pause = false;
	}

	public void keyReleased(KeyEvent keyEvent) {
		GamePanel.gsm.keyReleased(keyEvent.getKeyCode());
	}

}
