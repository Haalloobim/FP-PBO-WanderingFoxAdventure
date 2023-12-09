package id.ac.its.fox.gamestate;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.main.GamePanel;
import id.ac.its.fox.tilemap.Background;
import id.ac.its.fox.tilemap.TileMap;
import id.ac.its.fox.entity.Enemy;
import id.ac.its.fox.entity.Explosion;
import id.ac.its.fox.entity.HUD;
import id.ac.its.fox.entity.Player;
import id.ac.its.fox.entity.Enemies.Rat;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Level1State extends GameState {
    private Player player;
    private AudioPlayer bgMusic;
    private Background bgLevel1;
    private TileMap tilemap;

    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;

    private HUD hud;

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }

    @Override
    public void init() {
        bgMusic = new AudioPlayer("/Music/hurryBG.wav");
        bgMusic.bgplay();
        bgMusic.volumeDown();
        tilemap = new TileMap(16);
        tilemap.loadTiles("/Tileset/level1.png");
        tilemap.loadMap("/Maps/temp.map");
        tilemap.setPosition(0, 0);
        tilemap.setTween(0.15);

        bgLevel1 = new Background("/Background/bg_level1.png", 3);
        bgLevel1.setVector(0, 0);

        player = new Player(tilemap);
        player.setPosition(48, 144);

        enemies = new ArrayList<Enemy>();

        Rat rat;
        rat = new Rat(tilemap);
        rat.setPosition(176, 100);
        
        // enemies.add(rat);

        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

    }

    @Override
    public void update() {
        player.update();
        tilemap.setPosition(
                GamePanel.WIDTH / 2 - player.getX(),
                GamePanel.HEIGHT / 2 - player.getY());

        player.checkAttack(enemies);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemies.remove(i);
                i--;
                explosions.add(
                    new Explosion(
                        e.getX(), e.getY()
                    )
                );
            }
        }

        for(int i = 0; i < explosions.size(); i++){
            Explosion exp = explosions.get(i);
            exp.update();
            if(exp.shouldRemove()){
                explosions.remove(i);
                i--;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        // clear screen

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        bgLevel1.draw(g);
        tilemap.draw(g);
        player.draw(g);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        for (int i = 0; i < explosions.size(); i++) {
            Explosion exp = explosions.get(i);
            exp.setMapPosition(
                    (int) tilemap.getx(),
                    (int) tilemap.gety()
            );
            exp.draw(g);
        }

        hud.draw(g);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_LEFT)
            player.setLeft(true);
        if (k == KeyEvent.VK_RIGHT)
            player.setRight(true);
        if (k == KeyEvent.VK_UP)
            player.setUp(true);
        if (k == KeyEvent.VK_DOWN)
            player.setDown(true);
        if (k == KeyEvent.VK_W)
            player.setJumping(true);
        if (k == KeyEvent.VK_E)
            player.setGliding(true);
        if (k == KeyEvent.VK_R)
            player.setScratching();
        if (k == KeyEvent.VK_F)
            player.setClawing();
    }

    @Override
    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT)
            player.setLeft(false);
        if (k == KeyEvent.VK_RIGHT)
            player.setRight(false);
        if (k == KeyEvent.VK_UP)
            player.setUp(false);
        if (k == KeyEvent.VK_DOWN)
            player.setDown(false);
        if (k == KeyEvent.VK_W)
            player.setJumping(false);
        if (k == KeyEvent.VK_E)
            player.setGliding(false);
    }
}
