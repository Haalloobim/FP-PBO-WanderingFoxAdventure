package id.ac.its.fox.gamestate;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.main.GamePanel;
import id.ac.its.fox.tilemap.Background;
import id.ac.its.fox.tilemap.TileMap;
import id.ac.its.fox.entity.Player;

import java.awt.*;

public class Level1State extends GameState {
    private Player player;
    private AudioPlayer bgMusic;
    private Background bgLevel1;
    private TileMap tilemap;

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        bgMusic = new AudioPlayer("/Music/hurryBG.wav");
        bgMusic.bgplay();
        tilemap = new TileMap(16);
        tilemap.loadTiles("/Tileset/level1.png");
        tilemap.loadMap("/Maps/temp.map");
        tilemap.setPosition(0, 0);
        bgLevel1 = new Background("/Background/bg_level1.png", 3);
        bgLevel1.setVector(0, 0);
        player = new Player(tilemap);
        player.setPosition(80, 80);
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void draw(Graphics2D g) {
        // clear screen

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        bgLevel1.draw(g);
        tilemap.draw(g);
        player.draw(g);
    }

    @Override
    public void keyPressed(int k) {
    }

    @Override
    public void keyReleased(int k) {
    }

}
