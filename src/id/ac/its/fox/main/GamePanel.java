package id.ac.its.fox.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import id.ac.its.fox.gamestate.*;

public class GamePanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	
	// dimension
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 3;
	
	// thread 
	private Thread thread;
	private boolean running;
	protected static boolean pause = false; // testing using static variable to modify it through other class
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	
	//keyHandler
	KeyHandler keyH = new KeyHandler(); 
	
	// image 
	private BufferedImage image; 
	private Graphics2D g; 

	//Game State Manager
	protected static GameStateManager gsm;
	
	//constructor
	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH); 
		this.setFocusable(true);
		this.requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); 
		g = (Graphics2D) g; 
		running = true; 
	}

	public void run() {
		init(); 
		
		long start; 
		long elapsed; 
		long wait; 
		
		while(running) {
			
			start = System.nanoTime(); 
			
			update(); 
			draw(); 
			drawToScreen(); 
			
			elapsed = System.nanoTime() -  start; 
			wait = targetTime - elapsed / 1000000; 
			
			try {
				Thread.sleep(wait); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void update() {
		
	}
	
	private void draw() {
		
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics(); 
		g2.drawImage(image, 0, 0, null); 
		g2.dispose();
	}
	
	
	
}
