package id.ac.its.fox.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.imageio.ImageIO;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.main.GamePanel;

public class FinalStageFinishState extends GameState {
    private AudioPlayer bgMusic;
    private AudioPlayer mommy;
    private Image FinishScreen;
    private int currentChoice = 0;

    private String[] strList = {
            "Congratulations!!",
            "You Finish The Game!",
            "Once again, Thank you so much",
            "For Playing Our Game!",
    };

    private String[] optionsList = {
            "Back to Menu?",
            "Play Again",
    };

    private Color COLORRED = new Color(128, 0, 0);
    private Color COLORWHITE = new Color(255, 255, 255);

    private Font FONTBIG, FONTMED, FONTSMALL;

    private HashMap<String, AudioPlayer> sfx;

    public FinalStageFinishState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            FinishScreen = ImageIO.read(getClass().getResourceAsStream("/Background/BGBLACK.png"));
            FONTBIG = new Font("Arial", Font.PLAIN, 30);
            FONTMED = new Font("Arial", Font.PLAIN, 13);
            FONTSMALL = new Font("Arial", Font.PLAIN, 11);
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
        mommy = new AudioPlayer("/Music/Mommy.wav");
        bgMusic = new AudioPlayer("/Music/bgMenuHotel.wav");
        if (GamePanel.isMuted) {
            bgMusic.volumeMute();
        }
        mommy.update();
        mommy.volumeUp();
        mommy.volumeUp();
        mommy.volumeUp();
        mommy.volumeUp();
        mommy.bgplay();
        // bgMusic.update();
        // bgMusic.bgplay();
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(FinishScreen, 0, 0,
                FinishScreen.getWidth(null), FinishScreen.getHeight(null), null);
        g.setFont(FONTBIG);
        g.setColor(Color.WHITE);
        g.drawString(strList[0], 45, 75);
        for (int i = 1; i < strList.length; i++) {
            if (i == 1) {
                g.setFont(FONTMED);
                g.drawString(strList[i], 95, 105);
            } else if (i == 2) {
                g.setFont(FONTSMALL);
                g.drawString(strList[i], 80, 160);
            }
        }

        for (int i = 0; i < optionsList.length; i++) {
            if (i == currentChoice) {
                g.setColor(COLORRED);
            } else {
                g.setColor(COLORWHITE);
            }
            if (optionsList[i].equals("Back to Menu?")) {
                g.setFont(FONTSMALL);
                g.drawString(optionsList[i], 75, 190);
            } else {
                g.setFont(FONTSMALL);
                g.drawString(optionsList[i], 190, 190);
            }
        }
    }

    public void select() {
        if (currentChoice == 0) {
            bgMusic.close();
            mommy.close();
            gsm.setState(GameStateManager.MENUSTATE);
        }
        if (currentChoice == 1) {
            bgMusic.close();
            mommy.close();
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            sfx.get("select").clipPlay();
            select();
        }
        if (k == KeyEvent.VK_LEFT) {
            currentChoice--;
            sfx.get("choose").clipPlay();
            if (currentChoice == -1) {
                currentChoice = 0;
            }
        }
        if (k == KeyEvent.VK_RIGHT) {
            sfx.get("choose").clipPlay();
            currentChoice++;
            if (currentChoice == optionsList.length) {
                currentChoice = optionsList.length - 1;
            }
        }
    }

    @Override
    public void keyReleased(int k) {

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent k) {
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent k) {
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent k) {
        return;
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent k) {
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent k) {
    }
}
