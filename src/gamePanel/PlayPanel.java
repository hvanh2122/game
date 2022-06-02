package gamePanel;

import gameController.GameController;
import gameEngine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public final class PlayPanel extends JPanel {
    private static final int STICK_WIDTH = 5;
    private static final int RECT_HEIGHT = 500;
    private static final int RECT_START = 220;

    private final GameEngine engine;
    private final GameController controller;

    private int RecWidth;
    private int secondRectPos;
    private int moveValue;

    private int rotateDegree;
    private int rotateSpeed;

    private int dest;
    private int monkeyX;
    private int monkeyY;
    private int imageCycle;
    private int cycleCnt;


    public PlayPanel(GameEngine engine, GameController controller) {
        this.engine = engine;
        this.controller = controller;
        init();
    }

    public void init() {
        moveValue = 0;
        secondRectPos = 900;

        rotateDegree = 0;
        rotateSpeed = 1;

        RecWidth = Images.REC_WIDTH;

        monkeyX = RECT_START + RecWidth  - Images.MONKEY_WIDTH;
        monkeyY = Images.BACKGROUND_HEIGHT - RECT_HEIGHT- Images.MONKEY_HEIGHT;

        imageCycle = 0;
        cycleCnt = 0;
        dest = 0;

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(Images.background, 0, 0, null);

        AffineTransform def = g2d.getTransform();

        calcRectMove();
        drawRects(g2d);

        //Draw Stick
        calcDegree();
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(rotateDegree), RECT_START + RecWidth, Images.BACKGROUND_HEIGHT - RECT_HEIGHT);
        g2d.fillRect(RECT_START + RecWidth, Images.BACKGROUND_HEIGHT - RECT_HEIGHT - engine.getStickLength(), STICK_WIDTH, engine.getStickLength());
        g2d.setTransform(old);

        calcDest();
        moveMonkey();
        drawMonkey(g2d);

        g2d.setTransform(def);

        checkForGameOver();
        
        drawScore(g2d);
    }

    private void calcRectMove() {
        if (moveValue >= engine.getDistance() + RecWidth ) {
            engine.nextRectangle();
            init();
        }

        if (rotateDegree == 90 && monkeyX == dest && !engine.isGameOver()) {
            moveValue += 7;
        }
    }

    private void drawRects(Graphics2D g2d) {
        g2d.translate(-moveValue, 0);
        g2d.drawImage(Images.rectangle, RECT_START , Images.BACKGROUND_HEIGHT - RECT_HEIGHT, null);

        if (!engine.isMoving() && rotateDegree == 0 && secondRectPos != RECT_START + RecWidth + engine.getDistance() )
            secondRectPos -= 25;
        if (secondRectPos < RECT_START + RecWidth + engine.getDistance())
            secondRectPos = RECT_START + RecWidth + engine.getDistance();

        g2d.drawImage(Images.rectangle, secondRectPos, Images.BACKGROUND_HEIGHT - RECT_HEIGHT, null);
    }

    private void calcDegree() {
        if (!engine.isMoving())
            return;

        if (rotateDegree < 90) {
            rotateDegree += rotateSpeed / 5;
            rotateSpeed ++;
        } else {
            rotateDegree = 90;
        }
    }


    private void drawMonkey(Graphics2D g2d) {
        g2d.translate(monkeyX, monkeyY );
        if (rotateDegree == 90 && monkeyX < dest) {
            switch (imageCycle) {
                case 0:
                    g2d.drawImage(Images.walk1, 0, 0, null);
                    break;
                case 1:
                    g2d.drawImage(Images.walk2, 0, 0, null);
                    break;
                case 2:
                    g2d.drawImage(Images.walk3, 0, 0, null);
                    break;
                case 3:
                    g2d.drawImage(Images.walk4, 0, 0, null);
                    break;
            }
            cycleCnt++;
            cycleCnt %= 5;
            if (cycleCnt % 5 == 0) {
                imageCycle++;
                imageCycle %= 4;
            }
           
        } else {
            g2d.drawImage(Images.monkey5, 0, 0, null);
        }
    }

    private void calcDest() {
        dest = RECT_START + RecWidth + engine.getStickLength();
        if (dest > RECT_START + RecWidth + engine.getDistance()
                && dest <=  RECT_START + engine.getDistance() + 2 * RecWidth) {
            dest = RECT_START + RecWidth + engine.getDistance() + RecWidth  - Images.MONKEY_WIDTH;
        }
    }

    private void moveMonkey() {
        if (rotateDegree == 90 && monkeyX < dest)
            monkeyX += 5;

        if (monkeyX > dest)
            monkeyX = dest;

        if (monkeyX == dest && engine.isGameOver())
            monkeyY += 25;

        if (monkeyY > Images.BACKGROUND_HEIGHT)
            controller.gameOver();
    }


    private void checkForGameOver() {
        if (monkeyX > RECT_START + engine.getDistance() + 2* Images.REC_WIDTH ) {
            engine.setGameOver(true);
        }
    }


    private void drawScore(Graphics2D g2d) {
        g2d.setFont(new Font("Gabriola", Font.PLAIN, 40));
        g2d.setColor(Color.blue);
        g2d.drawString("Score : " + engine.getScore(), 30, 70);
    }
}