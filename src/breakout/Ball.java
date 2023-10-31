package breakout;

import Graphics.CanvasWindow;
import Graphics.Ellipse;
import Graphics.GraphicsObject;
import Graphics.Point;
import Graphics.Rectangle;

import java.awt.Color;
import java.util.List;
import java.util.Set;

public class Ball {
    private Ellipse ball;
    private Color FILL_COLOUR = Color.WHITE;
    private Color STROKE_COLOUR = Color.BLACK;
    private static final int RADIUS = 5;
    private Double boardWidth;
    private double boardHeight;
    private Double leftGap;

    // Position and movement variables
    private double xPos;
    private double yPos;
    private double xVelocity = 25;
    private double yVelocity = 25;

    // Corners around ball
    private Point topLeft, topRight, bottomLeft, bottomRight;

    // X-bound
    private double maxX;

    // To change directions
    public final int DIRECTION_X = 0;
    public final int DIRECTION_Y = 1;
    
    /**
     * Creates a ball, moves it across the board and changes its direction
     */
    public Ball(double boardWidth, double boardHeight, double leftGap) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.leftGap = leftGap;
        maxX = boardWidth + leftGap - (RADIUS * 2);
        
        makeBall();
        shootNewBall();
    }

    private void makeBall() {
        ball = new Ellipse(0, 0, RADIUS * 2, RADIUS * 2);
        ball.setFillColor(FILL_COLOUR);
        ball.setStrokeColor(STROKE_COLOUR);
    }

    public Ellipse getGraphics() {
        return ball;
    }

    public void shootNewBall() {
        xPos = leftGap + boardWidth/2;
        yPos = leftGap + boardHeight/2;
        ball.setCenter(xPos, yPos);
        xVelocity = 25;
        yVelocity = 25;
    }

    private void changeDirection(int direction) {
        if (direction == DIRECTION_X) {
            xVelocity *= -1;
        } else {
            yVelocity *= -1;
        }
    }

    /**
     * Checks whether the ball intersects something and changes its direction if so
     */
    public boolean intersects(CanvasWindow canvas, Rectangle border, Rectangle border2) {
        // This is so that the ball doesn't interact with the background
        Set<Rectangle> borders = Set.of(border, border2); 
        
        // Getting objects at the four corners of the ball
        GraphicsObject topLeftObject = canvas.getElementAt(topLeft);
        topLeftObject = !borders.contains(topLeftObject) ? topLeftObject : null;
        GraphicsObject topRightObject = canvas.getElementAt(topRight);
        topRightObject = !borders.contains(topRightObject) ? topRightObject : null;
        GraphicsObject bottomLeftObject = canvas.getElementAt(bottomLeft);
        bottomLeftObject = !borders.contains(bottomLeftObject) ? bottomLeftObject : null;
        GraphicsObject bottomRightObject = canvas.getElementAt(bottomRight);
        bottomRightObject = !borders.contains(bottomRightObject) ? bottomRightObject : null;

        // Checking if there is an object above/below/beside the ball
        boolean topBottomIntersect = (topLeftObject!= null && topRightObject != null)
            || (bottomLeftObject != null && bottomRightObject != null);
        boolean leftRightIntersect = (topLeftObject!= null && bottomLeftObject !=null) 
            || (topRightObject != null && bottomRightObject != null);

        if (topBottomIntersect ) {
            changeDirection(DIRECTION_Y);
            return true;
        } else if (leftRightIntersect) {
            changeDirection(DIRECTION_X);
            return true;
        }
        return false;
    }
    
    /**
     * Moves ball across the board
     * @param dt time
     */
    public void move(Double dt) {
        xPos += (xVelocity * dt);
        yPos += (yVelocity * dt);
        ball.setPosition(xPos, yPos);
        if (xPos <= leftGap || xPos >= maxX) {
            changeDirection(DIRECTION_X);
        } else if (yPos <= leftGap) {
            changeDirection(DIRECTION_Y);
        }
        updateBallEdges();
    }

    private void updateBallEdges() {
        topLeft = new Point(xPos - RADIUS, yPos - RADIUS);
        topRight = new Point(xPos + RADIUS, yPos - RADIUS);
        bottomLeft = new Point(xPos - RADIUS, yPos + RADIUS);
        bottomRight = new Point(xPos + RADIUS, yPos + RADIUS);
    }
    
    public Point getPosition() {
        return ball.getPosition();
    }

    public List<Point> ballEdges() {
        return List.of(topLeft, topRight, bottomLeft, bottomRight);
    }

    public int getRadius() {
        return RADIUS;
    }

}
