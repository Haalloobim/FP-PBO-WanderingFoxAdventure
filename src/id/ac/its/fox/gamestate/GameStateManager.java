package id.ac.its.fox.gamestate;


public class GameStateManager {
    private GameState[] gameStates;
    private int currentState;

    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    public static final int ABOUTSTATE = 2;
    public static final int LEVEL1FINISHSTATE = 3;
    public static final int LEVEL2STATE = 4;

    public GameStateManager() {
        gameStates = new GameState[10];
        currentState = MENUSTATE;
        setState(MENUSTATE);
    }

    public void unloadState(int state) {
        gameStates[state] = null;
    }

    public void loadState(int state) {
        if (state == MENUSTATE)
            gameStates[state] = new MenuState(this);
        if (state == LEVEL1STATE)
            gameStates[state] = new Level1State(this);
        if (state == ABOUTSTATE)
            gameStates[state] = new AboutUsState(this);
        if (state == LEVEL1FINISHSTATE)
            gameStates[state] = new Level1FinishState(this);
        if (state == LEVEL2STATE)
            gameStates[state] = new Level2State(this);
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void update() {
        try {
            if (gameStates[currentState] != null) {
                gameStates[currentState].update();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(java.awt.Graphics2D g) {
        try {
            if (gameStates[currentState] != null) {
                gameStates[currentState].draw(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(int k) {
        gameStates[currentState].keyPressed(k);
    }

    public void keyReleased(int k) {
        gameStates[currentState].keyReleased(k);
    }
}
