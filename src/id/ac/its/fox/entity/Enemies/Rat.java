package id.ac.its.fox.entity.Enemies;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import id.ac.its.fox.entity.Animation;
import id.ac.its.fox.entity.Enemy;
import id.ac.its.fox.tilemap.TileMap;

public class Rat extends Enemy {

    private double totalDistX;
    private BufferedImage[] sprites;

    public Rat(TileMap tm) {
        super(tm);
        isCollisionX = false;
        totalDistX = 0;
        moveSpeed = 0.8;
        maxSpeed = 0.8;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 36;
        height = 28;
        cwidth = 30;
        cheight = 20;

        health = maxHealth = 2;
        damage = 1;

        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/enemy1.png"));
            sprites = new BufferedImage[6];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height);
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(300);

            right = false;
            left = true;
            facingRight = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getNextPosition() {
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        if (falling) {
            dy += fallSpeed;
        }
    }

    public void update() {
        totalDistX += dx;
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }
        //add range 
        if (right && totalDistX > 150) {
            right = false;
            left = true;
            facingRight = false;
            totalDistX = 0;
        } else if (left && totalDistX < -150) {
            right = true;
            left = false;
            facingRight = true;
            totalDistX = 0;
        }
        // hit wall -> reverse it
        if (right && isCollisionX) {
            isCollisionX = false;
            right = false;
            left = true;
            facingRight = false;
            totalDistX = 0;
        } 
        else if (left && isCollisionX) {
            isCollisionX = false;
            right = true;
            left = false;
            facingRight = true;
            totalDistX = 0;
        }

        // update animation
        animation.update();
    }

    public void draw(java.awt.Graphics2D g) {
        // if (notOnScreen())
        //     return;

        setMapPosition();

        super.draw(g);
    }

}
