package id.ac.its.fox.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.entity.Clock;
import id.ac.its.fox.entity.Enemy;
import id.ac.its.fox.entity.Explosion;
import id.ac.its.fox.entity.HUD;
import id.ac.its.fox.entity.Player;
import id.ac.its.fox.entity.SavedStats;
import id.ac.its.fox.entity.Spike;
import id.ac.its.fox.entity.Enemies.Rat;
import id.ac.its.fox.main.GamePanel;
import id.ac.its.fox.tilemap.Background;
import id.ac.its.fox.tilemap.Prop;
import id.ac.its.fox.tilemap.TileMap;

@SuppressWarnings("unused")
public class FinalStageState extends GameState {
    private Player player;
    private AudioPlayer bgMusic;
    private Background bgLevel1;
    private TileMap tilemap;
    private PauseState pauseState;

    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;

    private HUD hud;
    private Clock clock;
    private Prop cave;

    private boolean eventStart;
    private boolean eventDead;
    private boolean eventFinish = false;
    private boolean blockedInput = false;
    private boolean screenStop = false;
    private int eventCount = 0;
    public static final int COMEVENTBEGIN = 1;
    public static final int COMEVENTEND = 50;
    public static final int DEADEVENTBEGIN = 1;
    public static final int DEADEVENTMID = 60;
    public static final int DEADEVENTEND = 120;
    private ArrayList<Rectangle> RectScreens;

    private static boolean pause = false;
    private Font titleFont, BigtitleFont, MedtitleFont;
    private String[] Thx = {
            "Haha.. You think its final stage?",
            " ",
            "Its Over :\")",
            "",
            " Thank You so Much For Playing This Game"
    };

    public FinalStageState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            titleFont = new Font(
                    "Century Gothic",
                    Font.BOLD,
                    12);
            BigtitleFont = new Font(
                    "Century Gothic",
                    Font.BOLD,
                    16);
            MedtitleFont = new Font(
                    "Century Gothic",
                    Font.BOLD,
                    13);
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

    @Override
    public void init() {
        bgMusic = new AudioPlayer("/Music/quiet.wav");
        if (GamePanel.isMuted) {
            bgMusic.volumeMute();
        }
        bgMusic.bgplay();
        bgMusic.volumeDown();
        tilemap = new TileMap(16);
        tilemap.loadTiles("/Tileset/level1.png");
        tilemap.loadMap("/Maps/FinalStage.map");
        tilemap.setPosition(0, 0);
        tilemap.setTween(0.15);

        bgLevel1 = new Background("/Background/bglevel2.png", 3);
        bgLevel1.setVector(0, 0);

        pauseState = new PauseState();

        cave = new Prop(tilemap, "/Props/cave.png");
        cave.setPosition(1200, 162);

        player = new Player(tilemap);
        player.setPosition(48, 32);
        player.setHealth(SavedStats.getHealth());
        player.setLives(SavedStats.getLives());

        this.enemySpawned();

        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

        eventStart = true;
        eventDead = false;
        eventFinish = false;
        RectScreens = new ArrayList<Rectangle>();
        eventStart();
        clock = new Clock();
        clock.setTimer(1, 0);
        clock.start();
    }

    private void enemySpawned() {
        enemies = new ArrayList<Enemy>();
        Rat rat;
        createSpike(Spike.SPIKEBOTTOM, 232, 295, 51);
    }

    public void createSpike(int type, int x, int y, int total) {
        for (int i = 0; i < total; i++) {
            Spike spike;
            spike = new Spike(tilemap, type);
            spike.setPosition(x + i * 16, y);
            enemies.add(spike);
        }
    }

    @Override
    public void update() {
        if (eventFinish) {
            eventFinish();
        }

        if (pause || screenStop)
            return;
        try {
            player.update();
        } catch (Exception e) {
            eventDead = true;
        }

        tilemap.setPosition(
                GamePanel.WIDTH / 2 - player.getX(),
                3 * GamePanel.HEIGHT / 4 - player.getY());
        player.checkAttack(enemies);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                clock.increaseTime(1);
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
        
        if (player.getX() > 1865 && player.getX() < 1890 && player.getY() > 267 && player.getY() < 278) {
            blockedInput = true;
            screenStop = true;
            eventFinish = true;
        }

        if (player.getHealth() == 0 || (clock.getMinute() == 0 && clock.getSecond() == 0)) {
            clock.stop();
            eventDead = true;
        }

        if (eventStart) {
            eventStart();
        }

        if (eventDead) {
            eventDead();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        bgLevel1.draw(g);
        tilemap.draw(g);
        cave.draw(g);
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

        clock.draw(g);

        if (pause) {
            pauseState.drawPause(g);
        }

        g.setColor(java.awt.Color.BLACK);
        for (int i = 0; i < RectScreens.size(); i++) {
            g.fill(RectScreens.get(i));
        }
        if (player.getY() < 200) {
            for (int i = 0; i < Thx.length; i++) {
                if (i == 0) {
                    g.setFont(BigtitleFont);
                    g.setColor(Color.WHITE);
                    g.drawString(Thx[i], 60, 70 + i * 15);
                } else if (i == 2) {
                    g.setFont(titleFont);
                    g.setColor(Color.WHITE);
                    g.drawString(Thx[i], 135, 60 + i * 15);
                } else if (i == 4) {
                    g.setFont(MedtitleFont);
                    g.setColor(Color.RED);
                    g.drawString(Thx[i], 27, 70 + i * 15);
                } else {
                    g.setFont(titleFont);
                    g.setColor(Color.WHITE);
                    g.drawString(Thx[i], 15, 30 + i * 15);
                }
            }
        }

    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            gsm.setState(GameStateManager.MENUSTATE);
            bgMusic.close();
            pause = false;
        }
        if (k == KeyEvent.VK_ESCAPE) {
            if (!pause) {
                pause = true;
                clock.stop();
            } else {

                pause = false;
                clock.start();
            }
        }
        if ((blockedInput || player.getHealth() == 0) || pause)
            return;
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
        if (blockedInput || player.getHealth() == 0)
            return;
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
        player.setPosition(48, 32);
        clock.resetTimer();
        eventStart = true;
        eventStart();
    }

    private void eventStart() {
        eventCount++;
        if (eventCount == COMEVENTBEGIN) {
            blockedInput = true;
            RectScreens.clear();
            RectScreens.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            RectScreens.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
            RectScreens.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            RectScreens.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
        }

        if (eventCount > COMEVENTBEGIN && eventCount < COMEVENTEND) {
            if (eventCount == 30)
                blockedInput = false;
            RectScreens.get(0).height -= 4; // mid to top
            RectScreens.get(1).width -= 6; // mid to left
            RectScreens.get(2).y += 4; // mid to bottom
            RectScreens.get(3).x += 6; // mid to right
        }

        if (eventCount == COMEVENTEND) {
            eventStart = false;
            eventCount = 0;
            RectScreens.clear();
        }
    }

    private void eventDead() {
        eventCount++;
        if (eventCount == 1) {
            player.setDead();
            player.gameStop();
        }
        if (eventCount == 60) {
            RectScreens.clear();
            RectScreens.add(new Rectangle(
                    0, 0, GamePanel.WIDTH, 0));
        } else if (eventCount > 60 && eventCount < 90) {
            RectScreens.get(0).height += 16;
        }
        if (eventCount >= 120) {
            eventDead = blockedInput = false;
            eventCount = 0;
            player.loseLife();

            if (player.getLives() == 0) {
                gsm.setState(GameStateManager.MENUSTATE);
                bgMusic.close();
            }
            reset();
        }
    }

    private void eventFinish() {
        eventCount++;
        if (eventCount == COMEVENTBEGIN) {
            blockedInput = true;
            RectScreens.clear();
            RectScreens.add(new Rectangle(
                    0, 0, GamePanel.WIDTH, 0));
        }

        if (eventCount > COMEVENTBEGIN && eventCount < COMEVENTEND) {
            RectScreens.get(0).height += 8;
        }

        if (eventCount == 50) {
            player.setDead();
            player.gameStop();
            clock.stop();
            player.setHealth(SavedStats.getHealth());
            player.setLives(SavedStats.getLives());
            gsm.setState(GameStateManager.LEVEL2FINISHSTATE);
            bgMusic.close();
        }
    }

    @Override
    public void mouseClicked(MouseEvent k) {
        System.out.println(k.getX() / 3 + " " + k.getY() / 3);
    }

    @Override
    public void mousePressed(MouseEvent k) {

    }

    @Override
    public void mouseReleased(MouseEvent k) {

    }

    @Override
    public void mouseMoved(MouseEvent k) {

    }

    @Override
    public void mouseDragged(MouseEvent k) {

    }

}
