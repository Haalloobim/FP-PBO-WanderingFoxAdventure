package id.ac.its.fox.entity;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Clock {
	private Timer clock;
	private BufferedImage image;
	private int second, minute;
	private Font font;
	private StringBuilder string;
	private boolean flinching = false;
	private long flinchTimer;
	
	public Clock() {
		clock = new Timer(1000, new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent e) {
					second--;
					if(second < 0) {
                        second = 59;
                        minute--;
                        if(minute < 0){
                            minute = second = 0;
                            clock.stop();
                        }
                    }
				}
			}
		);
		
		try {
			
			
			font = new Font("Arial", Font.PLAIN, 14);
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			flinch();
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		string = new StringBuilder();
		g.setFont(font);
		g.setColor(Color.WHITE);
		if(minute < 10)
			string.append("0" + minute);
		else
			string.append(minute);
		
		string.append(":");
		
		if(second < 10)
			string.append("0" + second);
		else
			string.append(second);
		
		g.drawString(string.toString(), 280, 25);
	}
	
	public void increaseTime(int time) {
		second += time;
		if(second > 60) {
			second = this.second % 60;
			minute++;
		}
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	private void flinch() {
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000) {
				flinching = false;
			}
		}
	}
	
	public void start() {
		clock.start();
	}
	
	public void stop() {
		clock.stop();
	}

    public int getSecond() {
        return second;
    }

    public int getMinute() {
        return minute;
    }
    public void setTimer(int minute, int second){
        this.minute = minute;
        this.second = second;   
    }
}

