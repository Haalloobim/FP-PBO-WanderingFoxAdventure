package id.ac.its.fox.tilemap;

import java.awt.image.*;

public class Tile {
    private BufferedImage image;
    private int type; 

    public static final int NORMAL = 0;
	public static final int BLOCKED = 1;

    public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}

    public BufferedImage getImage() { 
        return this.image; 
    }
    
    public void setImage(BufferedImage image) {
        this.image = image;
    }

	public int getType() { 
        return this.type; 
    }
    
    public void setType(int type) {
        this.type = type;
    }   
}
