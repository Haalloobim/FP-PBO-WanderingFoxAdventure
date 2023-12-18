package id.ac.its.fox.tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import id.ac.its.fox.entity.MapObject;

public class Prop extends MapObject {
    private BufferedImage br;
    private boolean onProp;

    public Prop(TileMap tm, String s) {
        super(tm);
        cheight = 18;
        cwidth = 20;
        try {
            br = ImageIO.read(getClass().getResourceAsStream(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        onProp = false;
    }
    
    public void draw(Graphics2D g) {
        setMapPosition();
        g.drawImage(br, (int) (x + xmap),
                (int) (y + ymap), null);
    }

    public void setOnProp(boolean onProp) {
        this.onProp = onProp;
    }

    public boolean isOnProp() {
        return onProp;
    }
}
