package id.ac.its.fox.entity;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

import javax.imageio.ImageIO;

import id.ac.its.fox.tilemap.TileMap;

public class Claw extends MapObject{

    private boolean hit;
    private boolean remove;
    private boolean right;
    private boolean left;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;

    public Claw(TileMap tm, Boolean right) {
        super(tm);
        moveSpeed = 3.8;
        if(right){
            dx = moveSpeed;
        }
        else{
            dx = -moveSpeed;
        }

        width = 30;
        height = 30;    
        cwidth = 14;
        cheight = 14;

        try {
            BufferedImage spritesheet = ImageIO.read(
                getClass().getResourceAsStream(
                    "/Sprites/Player/claw.gif"
                    )
                );
            sprites = new BufferedImage[3];
            for(int i = 0; i < sprites.length; i++){
                sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
            }
            hitSprites = new BufferedImage[3];
            for(int i = 0; i < hitSprites.length; i++){
                hitSprites[i] = spritesheet.getSubimage(i*width, height, width, height);
            }
            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);
        }
        catch (Exception e) {
            // TODO: handle exception
        }

    }

}
