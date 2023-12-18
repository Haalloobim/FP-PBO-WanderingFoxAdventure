package id.ac.its.fox.button;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import id.ac.its.fox.main.GamePanel;

public class VolumeButton extends Button {
    public static final int COL_NUM = 3;
    public static final int ROW_NUM = 2;
    private BufferedImage[][] button;
    private BufferedImage image;
    private boolean isMouseOver;
    private boolean isMousePressed;
    private boolean isMuted;
    private int row, col;

    public VolumeButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Button/VolumeButton.png"));
            button = new BufferedImage[ROW_NUM][COL_NUM];
            for (int i = 0; i < button.length; i++) {
                for (int j = 0; j < button[i].length; j++) {
                    button[i][j] = image.getSubimage(j * width, i * height, width, height);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isMousePressed = false;
        isMouseOver = false;
        isMuted = GamePanel.isMuted;
    }

    public void update() {
        if (isMuted) {
            row = 1;
        } else {
            row = 0;
        }
        col = 0;
        if (isMouseOver) {
            col = 1;
        }
        if (isMousePressed) {
            col = 2;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(button[row][col], this.getX(), this.getY(), null);
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
}
