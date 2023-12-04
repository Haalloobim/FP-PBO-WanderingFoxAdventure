package id.ac.its.fox.entity;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {
    private Player player;
    private BufferedImage image;
    private Font font; 

    public HUD(Player p) {
        player = p;
        try {
            image = ImageIO.read(
                getClass().getResourceAsStream(
                    "/HUD/hud.gif"
                )
            );  
            font = new Font("Arial", Font.PLAIN, 14);
        } 
        catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void draw(java.awt.Graphics2D g) {
        g.drawImage(image, 0, 10, null);
        g.setFont(font);
        g.drawString(
            player.getHealth() + "/" + player.getMaxHealth(),
            30,
            25
        );
        g.drawString(
            player.getClaw() + "/" + player.getMaxClaw(),
            30,
            45
        );
    }   
}
