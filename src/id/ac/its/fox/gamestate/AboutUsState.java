package id.ac.its.fox.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.main.GamePanel;

public class AboutUsState extends GameState {

    private Image bg;
    private AudioPlayer bgMusic, sfx;
    private Color titleColor;
    private String title = "About Us";
    private Font titleFont, BigtitleFont;
    private String[] about = {
            " ",
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
            BigtitleFont = new Font(
                    "Century Gothic",
                    Font.BOLD,
                    24);
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
        if (GamePanel.isMuted) {
            bgMusic.volumeMute();
        }
        bgMusic.bgplay();
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(bg, null, null);
        for (int i = 0; i < about.length; i++) {
            if (i != about.length - 1) {
                g.setFont(titleFont);
                g.setColor(Color.WHITE);
                g.drawString(about[i], 15, 30 + i * 15);
            } else {
                g.setFont(titleFont);
                g.setColor(Color.RED);
                g.drawString(about[i], 145, 215);
            }
        }

        g.setFont(BigtitleFont);
        g.setColor(Color.black);
        g.drawString(title, 115, 30);
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

    @Override
    public void mousePressed(MouseEvent e) {
        return;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        return;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        return;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        return;
    }

    @Override
    public void mouseClicked(MouseEvent k) {
    }
}
