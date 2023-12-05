package id.ac.its.fox.entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Explosion {
    private double x;
    private double y;
    private int xmap;
    private int ymap;

    private int width;
    private int height;

    private Animation animation;
    private BufferedImage[] sprites;

    private boolean remove;

    public Explosion(double x, double y) {
        this.x = x;
        this.y = y;

        width = 30;
        height = 30;

        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Explosion/explosion.gif"
                            )
                    );  
            sprites = new BufferedImage[3];
            for(int i = 0; i < sprites.length; i++){
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(10);
    }

    public void update() {
        animation.update();
        if(animation.hasPlayedOnce()){
            remove = true;
        }
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void setMapPosition(int x, int y) {
        xmap = x;
        ymap = y;
    }

    public void draw(java.awt.Graphics2D g) {
        g.drawImage(
                animation.getImage(),
                (int)x + xmap - width / 2,
                (int)y + ymap - height / 2,
                null
        );
    }
}
