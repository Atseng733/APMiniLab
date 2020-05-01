package socialdistancing;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class Obstacle{

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    
    public Obstacle() {
    	x = (int)Math.random()*Settings.sXExt;
    	y = (int)Math.random()*Settings.sYExt;
    	visible = true;
    	loadImage("trashcan.jpg");
    	getImageDimensions();
    }

    public Obstacle(int x, int y, String imageS) {
        this.x = x;
        this.y = y;
        visible = true;
        loadImage(imageS);
        getImageDimensions();
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }
    
    protected void getImageDimensions() {

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }    

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}