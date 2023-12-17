package id.ac.its.fox.gamestate;

import id.ac.its.fox.audio.AudioPlayer;
import id.ac.its.fox.main.GamePanel;
import id.ac.its.fox.tilemap.Background;
import id.ac.its.fox.tilemap.Prop;
import id.ac.its.fox.tilemap.TileMap;
import id.ac.its.fox.entity.Clock;
import id.ac.its.fox.entity.Enemy;
import id.ac.its.fox.entity.Explosion;
import id.ac.its.fox.entity.HUD;
import id.ac.its.fox.entity.Player;
import id.ac.its.fox.entity.Spike;
import id.ac.its.fox.entity.Enemies.Rat;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level1State extends GameState {
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
        tilemap.loadMap("/Maps/Level_1.map");
        tilemap.setPosition(0, 0);
        tilemap.setTween(0.15);

        bgLevel1 = new Background("/Background/bg_level1.png", 3);
        bgLevel1.setVector(0, 0);

        pauseState = new PauseState();

        cave = new Prop(tilemap, "/Props/cave.png");
        cave.setPosition(1620, 117);
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
        rat.setPosition(1420, 50);
        enemies.add(rat);
        Spike spike;
        spike = new Spike(tilemap, Spike.SPIKEBOTTOM);
        spike.setPosition(360, 216);
        enemies.add(spike);
        spike = new Spike(tilemap, Spike.SPIKEBOTTOM);
        spike.setPosition(376, 216);
        enemies.add(spike);
        spike = new Spike(tilemap, Spike.SPIKEBOTTOM);
        spike.setPosition(1560, 153);
        enemies.add(spike);
        spike = new Spike(tilemap, Spike.SPIKEBOTTOM);
        spike.setPosition(1576, 153);
        enemies.add(spike);
        spike = new Spike(tilemap, Spike.SPIKEBOTTOM);
        spike.setPosition(503, 137);
        enemies.add(spike);
        spike = new Spike(tilemap, Spike.SPIKEBOTTOM);
        spike.setPosition(519, 137);
        enemies.add(spike);


        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

        eventStart = true;
        eventDead = false;
        eventFinish = false;
        RectScreens = new ArrayList<Rectangle>();
        eventStart();
        clock = new Clock();
        clock.setTimer(2, 0);
        clock.start();
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
                GamePanel.HEIGHT / 2 - player.getY());
        player.checkAttack(enemies);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                clock.increaseTime(5);
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
        
        if(player.getX() > 1644 && player.getY() > 149) {
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
        // clear screen

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        bgLevel1.draw(g);
        cave.draw(g);
        tilemap.draw(g);
        player.draw(g);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (e.getClass() == Spike.class){
                ((Spike)e).draw(g);
            }
            else if (e.getClass() == Rat.class){
                e.draw(g);
            }
                
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
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER && pause) {
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
        player.setPosition(32, 144);
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
            if(eventCount == 30) blockedInput = false;
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
            if (player.getLives() == 0) {
                gsm.setState(GameStateManager.MENUSTATE);
                bgMusic.close();
            } 
            else {
                eventDead = blockedInput = false;
                eventCount = 0;
                player.loseLife();
                reset();
            }
        }
    }

    private void eventFinish(){
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

		if(eventCount == 50) {
            player.setDead();
			player.gameStop();
			clock.stop();
			gsm.setState(GameStateManager.LEVEL1FINISHSTATE);
			bgMusic.close();
		}
    }
}
