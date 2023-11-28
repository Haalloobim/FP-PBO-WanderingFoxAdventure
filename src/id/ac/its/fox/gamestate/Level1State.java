package id.ac.its.fox.gamestate;

import id.ac.its.fox.main.GamePanel;
import id.ac.its.fox.tilemap.TileMap;

import java.awt.Graphics2D;
import java.awt.*; 

public class Level1State extends GameState{

    private TileMap tilemap; 

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void init() {
        tilemap = new TileMap(30);
        tilemap.loadTiles("soon");
        tilemap.loadMap("soon");
        tilemap.setPosition(0, 0);

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        // clear screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        tilemap.draw(g);

    }

    @Override
    public void keyPressed(int k) {
    }

    @Override
    public void keyReleased(int k) {
    }
    
}
