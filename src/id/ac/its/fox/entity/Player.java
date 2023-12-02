package id.ac.its.fox.entity;

import id.ac.its.fox.tilemap.TileMap;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject{
    public Player(TileMap tm) {
        super(tm);
    }
    private int health;
    private int maxHealth;
    private int claw;
    private int maxClaw;
    private boolean dead;
    private boolean flinching;
}
