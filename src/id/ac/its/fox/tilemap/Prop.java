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
        try {
            br = ImageIO.read(getClass().getResourceAsStream(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        height = br.getHeight();
        width = br.getWidth();
        cheight = br.getHeight() - 2;
        cwidth = br.getWidth() - 2;
        onProp = false;
    }

    public void update() {
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        g.drawImage(br, (int) (x + xmap - width / 2),
                (int) (y + ymap - height / 2) , null);
        // g.drawRect((int) (x + xmap - cwidth / 2), (int) (y + ymap - cheight / 2), cwidth, cheight);
    }

    public void setOnProp(boolean onProp) {
        this.onProp = onProp;
    }

    public boolean isOnProp() {
        return onProp;
    }
}
