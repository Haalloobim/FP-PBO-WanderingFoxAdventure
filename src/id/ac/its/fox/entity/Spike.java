package id.ac.its.fox.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import id.ac.its.fox.tilemap.TileMap;

public class Spike extends Enemy {
    private int width;
    private int height;
    private BufferedImage spikeBottom;
    private BufferedImage spikeTop;
    private int type; 
    public static final int SPIKEBOTTOM = 0;    
    public static final int SPIKETOP = 1;    
    
    public Spike(TileMap tm, int type) {
        super(tm);
        damage = 1;
        width = 16;
        height = 16;
        cwidth = 16;
        cheight = 8;
        this.type = type;

        try {
            spikeBottom = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/spike.png"));
            spikeTop = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/spikes-top.png"));
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
        if (this.type == SPIKEBOTTOM){
            g.drawImage(
                spikeBottom,
                (int) (x + xmap - width / 2),
                (int) (y + ymap - height / 2),
                null);
        }
        else if (this.type == SPIKETOP){
            g.drawImage(
                spikeTop,
                (int) (x + xmap - width / 2),
                (int) (y + ymap - height / 2),
                null);
        }
        
    }

    public void hit(int damage) {
        return;
    }

    public int getType() {
        return type;
    }
}
