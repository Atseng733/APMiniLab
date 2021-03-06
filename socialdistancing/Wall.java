package socialdistancing;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Wall extends Obstacle{
    protected Image image;
    protected boolean vertical;

    public Wall() {
    	x = (int)(Math.random()*Settings.sXExt);
    	y = (int)(Math.random()*Settings.sYExt);
    	visible = true;
    	vertical = true;
    	loadImage("SocialDistancingImages/wall2.png");
    	getImageDimensions();
    }
    
    public Wall(int x, int y, String imageS, boolean vertical) {

        this.x = x;
        this.y = y;
        visible = true;
        this.vertical = vertical;
        loadImage(imageS);
        getImageDimensions();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}