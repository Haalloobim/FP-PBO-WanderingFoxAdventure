package id.ac.its.fox.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.tilemap.Background;

public class MenuState extends GameState {

    private AudioPlayer bgMusic;
    private Image bg;
    private Background title;

    private int currentChoice = 0;
    private String[] options = {
            "Start",
            "Options",
            "Quit"
    };
    private Color titleColor;
    private Font titleFont;

    private Font font;

    private HashMap<String, AudioPlayer> sfx;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            title = new Background("/Background/Title.gif", 1);
            bg = new ImageIcon(getClass().getResource("/Background/bg1.gif")).getImage();

            titleColor = new Color(128, 0, 0);
            titleFont = new Font(
                    "Century Gothic",
                    Font.BOLD,
                    12);

        } catch (Exception e) {
            e.printStackTrace();
        }
        sfx = new HashMap<String, AudioPlayer>();
        sfx.put("choose", new AudioPlayer("/SFX/chooseOpt.wav"));
        sfx.put("select", new AudioPlayer("/SFX/select.wav"));
        init();
    }

    @Override
    public void init() {
        bgMusic = new AudioPlayer("/Music/bgMenuHotel.wav");
        bgMusic.bgplay();
    }

    @Override
    public void update() {
        title.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(bg, null, null);
        title.draw(g);
        g.setColor(titleColor);
        g.setFont(titleFont);
        // g.drawString("Wandering Fox Adventure", 80, 100);

        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.WHITE);
            }
            if (options[i].equals("Options")) {
                g.drawString(options[i], 137, 170 + i * 15);
            } else {
                g.drawString(options[i], 145, 170 + i * 15);
            }
        }

    }

    private void select() {
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
            sfx.get("select").clipPlay();
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            sfx.get("choose").clipPlay();
            if (currentChoice == -1) {
                currentChoice = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            sfx.get("choose").clipPlay();
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
