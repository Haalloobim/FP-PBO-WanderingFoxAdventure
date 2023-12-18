package id.ac.its.fox.button;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class DownButton extends Button {
    private BufferedImage button;
    private BufferedImage image;
    private boolean isMousePressed;

    public DownButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Button/FinalChall.png"));
            button = image.getSubimage(7 * width, 1 * height, 16, 16);

        } catch (Exception e) {
            e.printStackTrace();
        }
        isMousePressed = false;
    }

    public void draw(Graphics g) {
        g.drawImage(button, this.getX(), this.getY(), null);
    }

    public void setMousePressed(boolean isMousePressed) {
        this.isMousePressed = isMousePressed;
    }

    public boolean isMousePressed() {
        return isMousePressed;
    }

    public void reset() {
        isMousePressed = false;
    }
}
