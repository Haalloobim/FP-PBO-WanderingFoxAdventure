package id.ac.its.fox.gamestate;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.button.Button;
import id.ac.its.fox.button.VolumeButton;
import id.ac.its.fox.button.VolumeSlider;
import id.ac.its.fox.main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class OptionState extends GameState {
    private AudioPlayer bgMusic;
    private BufferedImage image;
    private Image bg;
    private VolumeButton volumeButton;
    private VolumeSlider volumeSlider;

    public OptionState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Background/OptionsPanel.png"));
            bg = new ImageIcon(getClass().getResource("/Background/bg1.gif")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        volumeButton = new VolumeButton(190, 83, 24, 24);
        volumeSlider = new VolumeSlider(190, 166, 24, 24);
        bgMusic = new AudioPlayer("/Music/bgMenuHotel.wav");
        if (GamePanel.isMuted) {
            bgMusic.volumeMute();
        }
        bgMusic.bgplay();
    }

    @Override
    public void update() {
        volumeButton.update();
        volumeSlider.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(bg, null, null);
        g.drawImage(image, 0, 0, null);
        volumeButton.draw(g);
        volumeSlider.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent k) {
        if (inButton(k, volumeButton)) {
            volumeButton.setMouseClicked(true);
        }
        if (inButton(k, volumeSlider)) {
            volumeSlider.setMouseClicked(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent k) {
        if (inButton(k, volumeButton)) {
            if (volumeButton.isMousePressed()) {
                bgMusic.volumeMute();
                volumeButton.setMuted(!volumeButton.isMuted());
            }
        }
        volumeSlider.reset();
        volumeButton.reset();
    }

    @Override
    public void mouseMoved(MouseEvent k) {
        volumeButton.setMouseOver(false);
        volumeSlider.setMouseOver(false);
        if (inButton(k, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
        if (inButton(k, volumeSlider)) {
            volumeSlider.setMouseOver(true);
        }
    }

    @Override
    public void mouseDragged(MouseEvent k) {
        if (volumeSlider.isMousePressed()) {
            volumeSlider.slideX(k.getX());
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            bgMusic.clipStop();
            gsm.setState(GameStateManager.MENUSTATE);
        }
        return;
    }

    @Override
    public void keyReleased(int k) {
        return;
    }

    private boolean inButton(MouseEvent e, Button b) {
        return b.getBound().contains(e.getX() / GamePanel.SCALE, e.getY() / GamePanel.SCALE);
    }

    @Override
    public void mouseClicked(MouseEvent k) {
    }
}
