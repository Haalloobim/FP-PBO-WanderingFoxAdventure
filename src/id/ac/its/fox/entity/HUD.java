package id.ac.its.fox.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {
    private Player player;
    private BufferedImage image1;
    private BufferedImage image2;

    public HUD(Player p) {
        player = p;
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthBar.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/HUD/playerLife.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g) {
        g.drawImage(image1.getSubimage(0, (player.getMaxHealth() - player.getHealth()) * 20, 100, 20), 10, 5, null);
        g.drawImage(image2.getSubimage(0, 0, 16, 16), 12, 30, null);
        g.setColor(Color.BLACK);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        g.drawString("x " + player.getLives(), 28, 43);
    }
}
