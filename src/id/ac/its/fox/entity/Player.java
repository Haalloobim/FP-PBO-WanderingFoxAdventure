package id.ac.its.fox.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import id.ac.its.fox.tilemap.TileMap;

public class Player extends MapObject {

    private int health;
    private int maxHealth;
    private int claw;
    private int maxClaw;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;
    private int jumpCount;
    private boolean clawing;
    private int clawCost;
    private int clawDamage;
    private ArrayList<Claw> claws;
    private int jumpTemp;
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;
    private boolean gliding;
    private long lastJumpTime;

    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
            4, 6, 1, 1, 5, 4, 4
    };

    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 2;
    private static final int CLAWING = 5;
    private static final int SCRATCHING = 4;
    private static final int DOUBLE_JUMP = 6;

    public Player(TileMap tm) {
        super(tm);
        jumpTemp = 0;
        width = 24;
        height = 24;
        cwidth = 22;
        cheight = 22;
        jumpCount = 0;
        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.0;
        stopJumpSpeed = 0.3;
        isCollisionY = false;
        facingRight = true;
        health = maxHealth = 5;
        claw = maxClaw = 2500000;
        lastJumpTime = 0;

        clawCost = 200;
        clawDamage = 5;
        claws = new ArrayList<Claw>();

        scratchDamage = 8;
        scratchRange = 30;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/player.png"));
            sprites = new ArrayList<BufferedImage[]>();
            for (int i = 0; i < 7; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for (int j = 0; j < numFrames[i]; j++) {
                    if (i != 4) {
                        bi[j] = spritesheet.getSubimage(
                                j * width,
                                i * height,
                                width,
                                height);
                    } else {
                        bi[j] = spritesheet.getSubimage(
                                j * width * 2,
                                i * height,
                                width * 2,
                                height);
                    }

                }
                sprites.add(bi);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
    }

    public long getLastJumpTime() {
        return lastJumpTime;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getClaw() {
        return claw;
    }

    public int getMaxClaw() {
        return maxClaw;
    }

    public void setClawing() {
        clawing = true;
    }

    public void setScratching() {
        scratching = true;
    }

    public void setGliding(boolean b) {
        gliding = b;
    }

    public void checkAttack(ArrayList<Enemy> enemies) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (scratching) {
                if (facingRight) {
                    if (e.getX() > x &&
                        e.getX() < x + scratchRange && 
                        e.getY() > y - height / 2 && 
                        e.getY() < y + height / 2
                        ){
                        e.hit(scratchDamage);
                    }
                } 
                else {
                    if (e.getX() < x && 
                        e.getX() > x - scratchRange && 
                        e.getY() > y - height / 2 &&
                        e.getY() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                }
            }

            for (int j = 0; j < claws.size(); j++) {
                if (claws.get(j).intersects(e)) {
                    e.hit(clawDamage);
                    claws.get(j).setHit();
                    break;
                }
            }

            if (intersects(e)) {
                hit(e.getDamage());
            }
        }
    }

    public void hit(int damage) {
        if (flinching) {
            return;
        }
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            dead = true;
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    public void getNextPosition() {
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
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        if (currentAction == SCRATCHING || currentAction == CLAWING) {
            if (!(jumping || falling)) {
                dx = 0;
            }
        }

        if (isCollisionY) {
            jumpCount = 0;
        }

        if (jumping && !falling) {
            dy = jumpStart;
            isCollisionY = false;
            falling = true;
            jumpCount++;
        }

        if (falling) {
            if (dy > 0 && gliding) {
                dy += fallSpeed * 0.1;
            } else {
                dy += fallSpeed;
            }

            if (dy > 0 && jumpCount == 1 && jumping) {
                jumpCount++;
                dy = 0.8 * jumpStart;
            }

            if (dy < 0 && !jumping) {
                dy += stopJumpSpeed;
            }
            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }
        }
    }

    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if (currentAction == SCRATCHING) {
            if (animation.hasPlayedOnce()) {
                scratching = false;
            }
        }

        if (currentAction == CLAWING) {
            if (animation.hasPlayedOnce()) {
                clawing = false;
            }
        }

        claw += 1;
        if (claw > maxClaw) {
            claw = maxClaw;
        }

        if (clawing && currentAction != CLAWING) {
            if (claw > clawCost) {
                claw -= clawCost;
                Claw c = new Claw(tileMap, facingRight);
                c.setPosition(x, y);
                claws.add(c);
            }
        }

        for (int i = 0; i < claws.size(); i++) {
            claws.get(i).update();
            if (claws.get(i).shouldRemove()) {
                claws.remove(i);
                i--;
            }
        }

        if (scratching) {
            if (currentAction != SCRATCHING) {
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(100);
                width = 48;
            }
        } else if (clawing) {
            if (currentAction != CLAWING) {
                currentAction = CLAWING;
                animation.setFrames(sprites.get(CLAWING));
                animation.setDelay(100);
                width = 24;
            }
        } else if (dy > 0) {
            if (gliding) {
                if (currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 24;
                }
            } else if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 24;
            }
        } else if (dy < 0 && jumpCount == 1) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(40);
                width = 24;
            }
        } else if (dy < 0 && jumpCount == 2) {
            if (currentAction != DOUBLE_JUMP) {
                currentAction = DOUBLE_JUMP;
                animation.setFrames(sprites.get(DOUBLE_JUMP));
                animation.setDelay(100);
                width = 24;
            }
        } else if (left || right) {
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(100);
                width = 24;
            }
        } else {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 24;
            }
        }
        animation.update();

        if (currentAction != SCRATCHING && currentAction != CLAWING) {
            if (right)
                facingRight = true;
            if (left)
                facingRight = false;
        }
    }

    public void draw(Graphics2D g) {
        setMapPosition();

        for (int i = 0; i < claws.size(); i++) {
            claws.get(i).draw(g);
        }

        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }

        super.draw(g);

        // draw claws
        for (int i = 0; i < claws.size(); i++) {
            claws.get(i).draw(g);
        }
    }
}
