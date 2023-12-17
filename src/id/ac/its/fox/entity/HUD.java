package id.ac.its.fox.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {
    private Player player;
    private BufferedImage healthBar;
    private BufferedImage lifeTotal;
    private BufferedImage clawTotal;

    public HUD(Player p) {
        player = p;
        try {
            healthBar = ImageIO.read(getClass().getResourceAsStream("/HUD/HealthBar.png"));
            lifeTotal = ImageIO.read(getClass().getResourceAsStream("/HUD/playerLife.png"));
            clawTotal = ImageIO.read(getClass().getResourceAsStream("/Sprites/claw.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g) {
        g.drawImage(healthBar.getSubimage(0, (player.getMaxHealth() - player.getHealth()) * 20, 100, 20), 10, 5, null);

        g.drawImage(lifeTotal, 12, 30, null);
        g.setColor(Color.BLACK);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        g.drawString("x " + player.getLives(), 36, 43);

        g.drawImage(clawTotal.getSubimage(24, 0, 24, 24), 9, 48, null);
        g.drawString("x " + player.getClaw(), 36, 64);
    }
}
