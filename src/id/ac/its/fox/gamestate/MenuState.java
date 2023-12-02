package id.ac.its.fox.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.tilemap.Background;

public class MenuState extends GameState{

    private AudioPlayer bgMusic;
    private Background bg;

    private int currentChoice = 0;
    private String[] options = {
        "Start",
        "Help",
        "Quit"
    };
    private Color titleColor;
    private Font titleFont;

    private Font font;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            bg = new Background("/Background/bg_menu.png", 1);
            bg.setVector(0, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font(
                "Century Gothic",
                Font.PLAIN,
                28);
            titleFont = new Font("Arial", Font.PLAIN, 12);

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

    @Override
    public void init() {
        bgMusic = new AudioPlayer("/Music/bgMenuHotel.wav");
        bgMusic.bgplay();
    }

    @Override
    public void update() {
        bg.update();
    }

    @Override
    public void draw(Graphics2D g) {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Wandering Fox Adventure", 80, 70);
        
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
            } 
            else {
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }

    }

    private void select(){
        if (currentChoice == 0) {
            bgMusic.close();
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        if (currentChoice == 1) {
            
        }
        if (currentChoice == 2) {
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if (currentChoice == options.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
    }
    
}
