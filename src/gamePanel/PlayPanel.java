package gamePanel;

import gameController.GameController;
import gameEngine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public final class PlayPanel extends JPanel {
    private static final int STICK_WIDTH = 3;
    private static final int RECT_HEIGHT = 440;
    private static final int RECT_START = 220;

    private final GameEngine engine;
    private final GameController controller;

    private int backgroundMoveValue = 0;

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
        backgroundMoveValue++;
        moveValue = 0;
        secondRectPos = 600;

        rotateDegree = 0;
        rotateSpeed = 1;

        RecWidth = Images.REC_WIDTH;

        monkeyX = RECT_START + RecWidth  - Images.MONKEY_WIDTH;
        monkeyY = RECT_HEIGHT + Images.MONKEY_HEIGHT;

        imageCycle = 0;
        cycleCnt = 0;
        dest = 0;

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        moveBackground(g2d);

        AffineTransform def = g2d.getTransform();

        calcRectMove();
        drawRects(g2d);

        //Draw Stick
        calcDegree();
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(rotateDegree), RECT_START + RecWidth + STICK_WIDTH,
                RECT_HEIGHT);
        g2d.fillRect(RECT_START + RecWidth , Images.BACKGROUND_HEIGHT - RECT_HEIGHT - engine.getStickLength(),
                STICK_WIDTH, engine.getStickLength());
        g2d.setColor(Color.yellow);
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
            controller.nextRect();
            init();
        }

        if (rotateDegree == 90 && monkeyX == dest && !engine.isGameOver()) {
            moveValue += 4;
        }
    }

    private void moveBackground(Graphics2D g2d) {
        if (rotateDegree == 90 && monkeyX == dest && backgroundMoveValue % 20 != 0 && !engine.isGameOver())
            backgroundMoveValue++;

        AffineTransform old = g2d.getTransform();
        g2d.translate(-backgroundMoveValue, 0);
        g2d.drawImage(Images.background, 0, 0, null);
        g2d.setTransform(old);
    }

    private void drawRects(Graphics2D g2d) {
        g2d.translate(-moveValue, 0);
        g2d.drawImage(Images.rectangle, RECT_START , RECT_HEIGHT, null);

        if (!engine.isMoving() && rotateDegree == 0 && secondRectPos != RECT_START + RecWidth + engine.getDistance() )
            secondRectPos -= 20;
        if (secondRectPos < RECT_START + RecWidth + engine.getDistance())
            secondRectPos = RECT_START + RecWidth + engine.getDistance();

        g2d.drawImage(Images.rectangle, secondRectPos, RECT_HEIGHT, null);
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
        if (monkeyX <= RECT_START + RecWidth)
            controller.upsideDown = false;
        AffineTransform old = g2d.getTransform();
        g2d.translate(monkeyX, monkeyY - Images.MONKEY_HEIGHT);
        if (engine.isMoving() && controller.isUpsideDown()) {
            g2d.scale(1, -1);
        }
        if (rotateDegree == 90 && monkeyX < dest) {
            switch (imageCycle) {
                case 0:
                    g2d.drawImage(Images.walk1, 0, -Images.MONKEY_HEIGHT, null);
                    break;
                case 1:
                    g2d.drawImage(Images.walk2, 0, -Images.MONKEY_HEIGHT, null);
                    break;
                case 2:
                    g2d.drawImage(Images.walk3, 0, -Images.MONKEY_HEIGHT, null);
                    break;
                case 3:
                    g2d.drawImage(Images.walk4, 0, -Images.MONKEY_HEIGHT, null);
                    break;
            }

            cycleCnt++;
            cycleCnt %= 8;
            if (cycleCnt % 8 == 0) {
                imageCycle++;
                imageCycle %= 4;
            }

        } else {
            g2d.drawImage(Images.monkey5, 0, -Images.MONKEY_HEIGHT, null);
        }

        g2d.setTransform(old);
    }


    private void calcDest() {
        if (engine.isGameOver() && controller.isUpsideDown())
            return;

        if (engine.isGameOver()) {
            dest = RECT_START + RecWidth - Images.MONKEY_WIDTH + engine.getStickLength();
        } else {
            dest = RECT_START + RecWidth + engine.getDistance() + RecWidth  - Images.MONKEY_WIDTH;
        }
    }


    private void moveMonkey() {
        if (rotateDegree == 90 && monkeyX < dest)
            monkeyX += 2;

        if (monkeyX > dest)
            monkeyX = dest;

        if (monkeyX == dest && engine.isGameOver())
            monkeyY += 20;

        if (monkeyY > Images.BACKGROUND_HEIGHT)
            controller.gameOver();
    }


    private void checkForGameOver() {
        if (controller.isUpsideDown() && monkeyX + Images.MONKEY_WIDTH >= RECT_START + RecWidth + engine.getDistance() ) {
            engine.setGameOver(true);
            dest = monkeyX;
        }
    }


    private void drawScore(Graphics2D g2d) {
        g2d.setFont(new Font("Gabriola", Font.PLAIN, 40));
        g2d.setColor(Color.blue);
        g2d.drawString("Score : " + engine.getScore(), 30, 70);
    }
}