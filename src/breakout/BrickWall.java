package breakout;

import java.util.ArrayList;
import java.util.List;

import Graphics.CanvasWindow;
import Graphics.GraphicsGroup;
import Graphics.Point;

import java.awt.Color;

public class BrickWall {
    private CanvasWindow canvas;
    private final double CANVAS_WIDTH;
    private GraphicsGroup bricks;
    private List<Brick> brickList;
    private List<Color> colors = List.of(Color.blue, Color.GREEN, Color.orange);
    private double oddRowX;
    private double evenRowX;
    private double rowY;

    /**
     * Creates and manages all the bricks on the board
     * 
     */
    public BrickWall(CanvasWindow canvas, double leftGap, double canvasWidth, double canvasHeight) {
        this.canvas = canvas;
        CANVAS_WIDTH = canvasWidth;
        evenRowX = leftGap + 2; // +2 allows a small gap b/w wall and bricks
        oddRowX = evenRowX + Brick.WIDTH / 2; 
        rowY = canvasHeight * 0.1;
        brickList = new ArrayList<>();
        bricks = new GraphicsGroup();

        makeWall();
    }

    private void makeOneRow(Color color, double x, double y) {
        while (x < CANVAS_WIDTH - Brick.WIDTH * 1.5) {
            Brick brick = new Brick(color, x, y);
            bricks.add(brick.getGraphics());
            brickList.add(brick);
            x += Brick.WIDTH + 5;
        }
    }

    private void makeWall() {
        for (Color color : colors) { // Two rows of each colour in the list
            makeOneRow(color, evenRowX, rowY);
            makeOneRow(color, oddRowX, rowY + Brick.HEIGHT + 5);
            rowY += (Brick.HEIGHT + 5) * 2;
        }
        canvas.add(bricks);
    }

    /**
     * Checks whether any brick is hit by the ball
     * @param ball
     */
    public void testHit(Ball ball) {
        for (Brick b : brickList) {
            for (Point i : ball.ballEdges()) {
                if (canvas.getElementAt(i) == b.getGraphics()) {
                    hitBrick(b);
                    return;
                }
            }
        }
    }
    
    public boolean bricksStillExist() {
        if (!brickList.isEmpty()) {
            return true; 
        }
        return false;
    }

    private void hitBrick(Brick b) {
        bricks.remove(b.getGraphics());
        brickList.remove(b);
    }

}
