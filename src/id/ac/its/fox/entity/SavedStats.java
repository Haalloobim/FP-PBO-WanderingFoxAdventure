package id.ac.its.fox.entity;

public class SavedStats {

    private static int lives = 3;
	private static int health = 5;

    public static void init() {
        lives = 3;
		health = 5;
    }

    public static int getHealth() {
        return health;
    }

    public static int getLives() {
        return lives;
    }   

    public static void setHealth(int health) {
        SavedStats.health = health;
    }   

    public static void setLives(int lives) {
        SavedStats.lives = lives;
    }
    
}
