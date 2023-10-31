package breakout;

import java.awt.Color;

import Graphics.GraphicsObject;
import Graphics.Rectangle;

public class Brick {
    public static final int WIDTH = 30; 
    public static final int HEIGHT = 20;
    private Rectangle brick;
    
    /**
     * Creates a single brick
     * @param color
     * @param x X-position of brick
     * @param y Y-position of brick
     */
    public Brick(Color color, double x, double y) {
        brick = new Rectangle(x, y, WIDTH, HEIGHT);
        brick.setFillColor(color);
        brick.setStrokeColor(color);
    }

    public GraphicsObject getGraphics() {
        return brick;
    }
}
