package id.ac.its.fox.gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class MenuState extends GameState{
    // private Background bg;
	private BufferedImage gameTitle;
	// private AudioPlayer bgMusic;
	// private HashMap<String, AudioPlayer> sfx;
	
	private int currentChoice = 0;
	private String[] options = {
		"Play",
		"Option",
		"About",
		"Quit"
	};
	
	private Font font;
    public void init() {
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    public void update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void draw(Graphics2D g) {
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    public void keyPressed(int k) {
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    public void keyReleased(int k) {
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    
}
