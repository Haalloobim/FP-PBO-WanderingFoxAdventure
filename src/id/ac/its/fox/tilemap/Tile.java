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

    
}
