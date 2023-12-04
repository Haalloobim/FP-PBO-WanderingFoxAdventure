package id.ac.its.fox.entity.Enemies;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import id.ac.its.fox.entity.Animation;
import id.ac.its.fox.entity.Enemy;
import id.ac.its.fox.tilemap.TileMap;

public class Rat extends Enemy {

    private BufferedImage[] sprites;

    public Rat(TileMap tm) {
        super(tm);

        moveSpeed = 0.8;
        maxSpeed = 0.8;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 36; 
        height= 28; 
        cwidth = 32;
        cheight = 25;

        health = maxHealth = 2;
        damage = 1;

        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "SOON"
                            )
                    );
            sprites = new BufferedImage[6];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                        );
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(300);

            right = true;
            facingRight = true;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getNextPosition() {
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } 
        else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } 

        if (falling){
            dy += fallSpeed;
        }
    }

    public void update(){
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if(flinching) {
            long elapsed = 
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400) {
                flinching = false;
            }
        }

        // hit wall -> reverse it
        if(right && dx == 0) {
            right = false;
            left = true;
            facingRight = false;
        }
        else if(left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }

        // update animation
        animation.update();
    }

    public void draw(java.awt.Graphics2D g) {
        if(notOnScreen()) return;

        setMapPosition();

        super.draw(g);
    }
    
}
