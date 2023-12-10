package id.ac.its.fox.gamestate;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.main.Game;
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

    private boolean eventStart;
    private boolean eventDead;
    private int eventCount = 0;
    public static final int STARTEVENTBEGIN = 1;
    public static final int STARTEVENTEND = 60;
    public static final int DEADEVENTBEGIN = 1;
    public static final int DEADEVENTMID = 60;
    public static final int DEADEVENTEND = 120;
    private ArrayList<Rectangle> RectScreens;

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
        rat.setPosition(1100, 50);
        enemies.add(rat);
        rat = new Rat(tilemap);
        rat.setPosition(1120, 50);
        enemies.add(rat);
        rat = new Rat(tilemap);
        rat.setPosition(1140, 50);
        enemies.add(rat);

        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

        eventStart = true;
        RectScreens = new ArrayList<Rectangle>();
        eventStart();

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
                                e.getX(), e.getY()));
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            Explosion exp = explosions.get(i);
            exp.update();
            if (exp.shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }

        if (player.getHealth() == 0) {
            eventDead = true;
        }

        if (eventStart) {
            eventStart();
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
                    (int) tilemap.gety());
            exp.draw(g);
        }

        hud.draw(g);

        g.setColor(java.awt.Color.BLACK);
        for (int i = 0; i < RectScreens.size(); i++) {
            g.fill(RectScreens.get(i));
        }
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

    private void reset() {
        player.PlayerReset();
        player.setPosition(48, 144);
        eventStart = true;
        eventStart();
    }

    private void eventStart() {
        eventCount++;
        if (eventCount == STARTEVENTBEGIN) {
            RectScreens.clear();
            RectScreens.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            RectScreens.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
            RectScreens.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            RectScreens.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
        }

        if (eventCount > STARTEVENTBEGIN && eventCount < STARTEVENTEND) {
            RectScreens.get(0).height -= 4; // mid to top
            RectScreens.get(1).width -= 6; // mid to left
            RectScreens.get(2).y += 4; // mid to bottom
            RectScreens.get(3).x += 6; // mid to right
        }

        if (eventCount == STARTEVENTEND) {
            eventStart = false;
            eventCount = 0;
            RectScreens.clear();
        }
    }
}
