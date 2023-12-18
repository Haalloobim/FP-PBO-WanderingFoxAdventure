package id.ac.its.fox.button;

import java.awt.Rectangle;

public class Button {
    private int x, y;
    private int width, height;
    private Rectangle bound;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bound = new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBound() {
        return bound;
    }

    public void setBound(Rectangle bound) {
        this.bound = bound;
    }

    public void setBoundX(int x)
    {
        bound.x = x - VolumeSlider.VOLUME_BUTTON_WIDTH / 2;
    }
}
