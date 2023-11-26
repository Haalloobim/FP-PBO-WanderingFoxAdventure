package id.ac.its.fox.gamestate;

import java.awt.Graphics2D;

public class GameStateManager {
    private GameState[] gameStates;

    private int currentState;
    public int currentLevel = 0;

    public static final int MENUSTATE = 1;
    public static final int NUMGAMESTATES = 12;

    public GameStateManager() {
        gameStates = new GameState[NUMGAMESTATES]; 

        currentState = MENUSTATE;

        loadState(currentState);
    }

    private void loadState(int state){
        if(state == MENUSTATE){
            gameStates[state] = new MenuState(this);    
        }
    }

    private void unloadState(int state){
        gameStates[state] = null;
    }

    public void setState(int state){
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void update(){
        try{
            gameStates[currentState].update();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g){
        try{
            gameStates[currentState].draw(g);
        }catch(Exception e){
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
