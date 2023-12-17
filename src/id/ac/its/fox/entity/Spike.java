package id.ac.its.fox.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import id.ac.its.fox.tilemap.TileMap;

public class Spike extends Enemy {
    private int width;
    private int height;
    private BufferedImage spikebottom;
    private BufferedImage[] sprites;

    public Spike(TileMap tm) {
        super(tm);
        damage = 1;
        width = 16;
        height = 16;
        cwidth = 16;
        cheight = 10;

        try {
            spikebottom = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/spike.png"));
            sprites = new BufferedImage[1];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spikebottom.getSubimage(
                        i * width,
                        0,
                        width,
                        height);
            }
            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(300);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update() {
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
    }

    public void draw(java.awt.Graphics2D g) {
        setMapPosition();
        // super.draw(g);
        g.drawImage(
                spikebottom,
                (int) (x + xmap - width / 2),
                (int) (y + ymap - height / 2),
                null);
    }

    public void hit(int damage) {
        return;
    }

}
