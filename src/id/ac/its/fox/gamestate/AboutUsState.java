package id.ac.its.fox.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import id.ac.its.fox.audio.AudioPlayer;

public class AboutUsState extends GameState {

    private Image bg;
    private AudioPlayer bgMusic, sfx;
    private Color titleColor;
    private Font titleFont;
    private String[] about = {
            "ABOUT US",
            " ",
            "Welcome to the enchanting world of",
            "Wandering Fox Adventure!",
            " ",
            "Wandering Fox Adventure is a Platformers game",
            "built for an OOP Final Project",
            " ",
            "by",
            "Jericho Nathanael Chrisnanta - 5025221001",
            "Muhammad Bimatara Indianto - 5025221260",
            "BACK"
    };

    public AboutUsState(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            bg = new ImageIcon(getClass().getResource("/Background/bg1.gif")).getImage();
            titleColor = new Color(128, 0, 0);
            titleFont = new Font(
                    "Century Gothic",
                    Font.BOLD,
                    12);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        sfx = new AudioPlayer("/SFX/select.wav");
        init();
    }

    @Override
    public void init() {
        bgMusic = new AudioPlayer("/Music/about.wav");
        bgMusic.bgplay();
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(bg, null, null);
        g.setColor(titleColor);
        g.setFont(titleFont);
        for (int i = 0; i < about.length; i++) {
            if (i != about.length - 1) {
                g.setColor(Color.WHITE);
                g.drawString(about[i], 15, 30 + i * 15);
            } else {
                g.setColor(Color.RED);
                g.drawString(about[i], 145, 215);
            }
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_ESCAPE) {
            sfx.clipPlay();
            gsm.setState(GameStateManager.MENUSTATE);
            bgMusic.close();
        }
    }

    @Override
    public void keyReleased(int k) {
    }

}
