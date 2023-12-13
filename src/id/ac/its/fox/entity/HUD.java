package id.ac.its.fox.entity;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import id.ac.its.fox.tilemap.Tile;

public class HUD {
    private Player player;
    private BufferedImage image;

    public HUD(Player p) {
        player = p;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthBar.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g) {
        g.drawImage(image.getSubimage(0, (player.getMaxHealth() - player.getHealth()) * 20, 100, 20), 10, 5, null);
    }
}
