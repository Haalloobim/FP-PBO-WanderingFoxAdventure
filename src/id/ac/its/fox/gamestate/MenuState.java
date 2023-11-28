package id.ac.its.fox.gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.tilemap.Background;

public class MenuState extends GameState {
    private Background bg;
	private BufferedImage gameTitle;
	private AudioPlayer bgMusic;
	/**
	 *
	 */
	private HashMap<String, AudioPlayer> sfx;
	
	private int currentChoice = 0;
	private String[] options = {
		"Play",
		"Option",
		"About",
		"Quit"
	};
	
	private Font font;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            gameTitle = ImageIO.read(
                getClass().getResourceAsStream("ISI TITLE NAK KENE JER")
            );

            font = new Font("Arial", Font.PLAIN, 12);

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void init() {
        bgMusic = new AudioPlayer("/Resource/Music/bgMenuHotel.mp3");
        bgMusic.bgplay();
        throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    public void update() {
        bg.update();
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void draw(Graphics2D g) {
        bg.draw(g);
        g.drawImage(gameTitle, 0, 30,gameTitle.getWidth()/4, gameTitle.getHeight()/4, null); 

        g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) 
			{
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.LIGHT_GRAY);
			}
			g.drawString(options[i], 145, 160 + i * 15);
		}
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    public void keyPressed(int k) {
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    public void keyReleased(int k) {
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    
}
