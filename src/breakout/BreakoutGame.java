package breakout;

import Graphics.CanvasWindow;
import Graphics.FontStyle;
import Graphics.GraphicsText;
import Graphics.Rectangle;

/**
 * The game of Breakout.
 */
public class BreakoutGame {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 500;

    private static final int LEFT_GAP = 20; //the distance from edge of canvas to the gameBoard
    private static final int BOARD_WIDTH = CANVAS_WIDTH - LEFT_GAP * 2;
    private static final int BOARD_HEIGHT = CANVAS_HEIGHT - LEFT_GAP * 2;

    private Paddle paddle;
    private Ball ball;
    private BrickWall brickWall;
    private Rectangle boardBorder;
    private Rectangle secondBorder;

    private CanvasWindow canvas;
    private int ballCount = 3;

    /**
     * Manages the entire game - creates the board, elements and handles win/lose logic
     */
    public BreakoutGame() {
        canvas = new CanvasWindow("Breakout!", CANVAS_WIDTH, CANVAS_HEIGHT);
        game();
    }

    private void game() {
        reset();
        canvas.onMouseMove(event -> paddle.movePaddle(event.getPosition().getX()));
        animation();
    }

    private void setUp() {
        //draws the edges of our game board - this defines the borders within which the ball and paddle can move
        boardBorder = new Rectangle(20, 20, BOARD_WIDTH, BOARD_HEIGHT);
        boardBorder.setStrokeWidth(2);
        
        // Adds a larger border around the board just for show
        secondBorder = new Rectangle(10, 10, BOARD_WIDTH + 20, BOARD_HEIGHT + 20);
        canvas.add(secondBorder);
        canvas.add(boardBorder);
        
        this.paddle = new Paddle(BOARD_WIDTH, BOARD_HEIGHT, LEFT_GAP);
        canvas.add(paddle.getGraphics());

        this.ball = new Ball(BOARD_WIDTH, BOARD_HEIGHT, LEFT_GAP);
        canvas.add(ball.getGraphics());

        this.brickWall = new BrickWall(canvas, LEFT_GAP, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    private void animation() {
        canvas.animate(() -> {
            if (ballCount > 0 && brickWall.bricksStillExist()) {
                ball.move(0.1);
                ball.intersects(canvas, boardBorder, secondBorder);
                brickWall.testHit(ball);
                loseBall();
            } else if (brickWall.bricksStillExist() == false) {
                winGame();
            } else {
                loseGame();
            }
        });
    }

    private void loseBall() {
        if (ball.getPosition().getY() > BOARD_HEIGHT + LEFT_GAP) {
            ball.shootNewBall();
            canvas.draw();
            canvas.pause(300);
            ballCount -=1;
        }
    }

    private void reset() {
        canvas.removeAll();
        ballCount = 3;
        setUp();
    }

    private void loseGame() {
        GraphicsText lostText = new GraphicsText("YOU LOST :)", CANVAS_WIDTH/4, CANVAS_HEIGHT/2);
        lostText.setFont("Comic Sans MS", FontStyle.BOLD, CANVAS_HEIGHT/10);
        canvas.add(lostText);
        canvas.draw();
        canvas.pause(1000);
        canvas.remove(lostText);
        reset();
    }

    private void winGame() {
        GraphicsText winText = new GraphicsText("YOU WON!", CANVAS_WIDTH/4, CANVAS_HEIGHT/2);
        winText.setFont("Comic Sans MS", FontStyle.BOLD, CANVAS_HEIGHT/10);
        canvas.add(winText);
    }

    public static void main(String[] args){
        new BreakoutGame();
    }
}
