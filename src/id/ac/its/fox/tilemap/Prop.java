package id.ac.its.fox.tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Prop {
    private BufferedImage br;
    public double x, y;

    public Prop(String s, int x, int y) {
        this.x = x;
        this.y = y;
        try {
            br = ImageIO.read(getClass().getResourceAsStream(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g, int x , int y) {
        g.drawImage(br, (int)this.x + x, (int)this.y + y, null);
    }
}
