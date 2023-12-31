package id.ac.its.fox.gamestate;
import java.awt.event.MouseEvent;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void mouseClicked(MouseEvent k);
	public abstract void mousePressed(MouseEvent k);
	public abstract void mouseReleased(MouseEvent k);
	public abstract void mouseMoved(MouseEvent k);
	public abstract void mouseDragged(MouseEvent k);
}

