package breakout;

import Graphics.Point;
import Graphics.Rectangle;

import java.awt.Color;

public class Paddle {
    private final Color FILL_COLOUR = Color.BLACK;
    private final Color STROKE_COLOUR = Color.BLACK;
    private Double boardWidth;
    private Double leftGap;
    private Rectangle paddle;
    private final Double yPosition;
    
    /**
     * Creates and moves the paddle
     */
    public Paddle(double boardWidth, double boardHeight, double leftGap) {
        this.boardWidth = boardWidth;
        this.yPosition = boardHeight * 0.95;
        this.leftGap = leftGap;

        paddle = new Rectangle(boardWidth/2, yPosition, 40, 5);
        paddle.setFillColor(FILL_COLOUR);
        paddle.setStrokeColor(STROKE_COLOUR);
    }

    public Rectangle getGraphics() {
        return paddle;
    }

    public Point getCenter() {
        return paddle.getCenter();
    }

    /**
     * Moves paddle to given X
     * 
     */
    public void movePaddle(double newX) {
        double leftBound = leftGap + paddle.getWidth()/2;
        double rightBound = boardWidth + leftGap- paddle.getWidth()/2;
        if (newX > leftBound && newX < rightBound) {
            paddle.setCenter(newX, yPosition);
        } else if (newX <= leftBound) {
            paddle.setX(leftGap);
        } else {
            paddle.setX(boardWidth + leftGap - paddle.getWidth());
        }
    }
}
