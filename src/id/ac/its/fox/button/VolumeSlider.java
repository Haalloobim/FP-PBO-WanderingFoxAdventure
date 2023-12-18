package id.ac.its.fox.button;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import id.ac.its.fox.main.GamePanel;

public class VolumeSlider extends Button {
    public static final int VOLUME_BUTTON_WIDTH = 24;
    public static final int COL_NUM = 3;
    private BufferedImage[] button;
    private BufferedImage image;
    private boolean isMouseOver;
    private boolean isMousePressed;
    private boolean isMuted;
    private int sliderX;
    private int xmin, xmax;
    private int col;
    private BufferedImage slider;

    public VolumeSlider(int x, int y, int width, int height) {
        super(x - VOLUME_BUTTON_WIDTH / 2, y, width, height);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Button/VolumeButton.png"));
            slider = ImageIO.read(getClass().getResourceAsStream("/Button/VolumeSlide.png"));
            button = new BufferedImage[COL_NUM];
            for (int i = 0; i < button.length; i++) {
                button[i] = image.getSubimage(i * width, 0, width, height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        xmin = 110 + VOLUME_BUTTON_WIDTH / 2;
        xmax = 200 + VOLUME_BUTTON_WIDTH / 2;
        sliderX = x;
        isMousePressed = false;
        isMouseOver = false;
        col = 0;
    }

    public void update() {
        col = 0;
        if (isMouseOver) {
            col = 1;
        }
        if (isMousePressed) {
            col = 2;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(slider, 112, 172, 108, 12,null);
        g.drawImage(button[col], sliderX - VOLUME_BUTTON_WIDTH / 2, this.getY(), null);
    }

    public void setMousePressed(boolean isMousePressed) {
        this.isMousePressed = isMousePressed;
    }

    public void setMouseOver(boolean isMouseOver) {
        this.isMouseOver = isMouseOver;
    }

    public void setMuted(boolean mute) {
        isMuted = mute;
    }

    public boolean isMousePressed() {
        return isMousePressed;
    }

    public boolean isMouseOver() {
        return isMouseOver;
    }

    public boolean isMuted() {
        GamePanel.isMuted = !isMuted;
        return isMuted;
    }

    public void reset() {
        isMousePressed = false;
        isMouseOver = false;
    }

    public void slideX(int x) {
        x /= GamePanel.SCALE;
        if (x < xmin) {
            x = xmin;
        }
        if (x > xmax) {
            x = xmax;
        }
        sliderX = x;
        setBoundX(sliderX);
    }
}
