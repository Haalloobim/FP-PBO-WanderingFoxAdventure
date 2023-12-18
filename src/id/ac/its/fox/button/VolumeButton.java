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
    private boolean isMouseClicked;
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
        isMouseClicked = false;
        isMouseOver = false;
        isMuted = GamePanel.isMuted;
    }

    public void update() {
        if (isMuted) {
            row = 1;
        } else {
            row = 0;
        }
        if (isMouseOver) {
            col = 1;
        } else if (isMouseClicked) {
            col = 2;
        } else {
            col = 0;
        }
    }

    public void draw(Graphics g)
    {
        g.drawImage(button[row][col], this.getX(), this.getY(),null);
    }

    public void setMouseClicked(boolean isMouseClicked) {
        this.isMouseClicked = isMouseClicked;
    }

    public void setMouseOver(boolean isMouseOver) {
        this.isMouseOver = isMouseOver;
    }

    public void setMuted(boolean mute) {
        isMuted = mute;
    }

    public boolean isMouseClicked() {
        return isMouseClicked;
    }

    public boolean isMouseOver() {
        return isMouseOver;
    }

    public boolean isMuted() {
        GamePanel.isMuted = !isMuted;
        return isMuted;
    }

    public void reset() {
        isMouseClicked = false;
        isMouseOver = false;
    }
}
