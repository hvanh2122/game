package gameEngine;

import gamePanel.Images;
import java.util.Random;

public class GameEngine {
    public static final int MIN_DISTANCE = 100;
    public static final int MAX_DISTANCE = 300;
    private boolean gameOver;
    private boolean moving;
    private int distance;
    private int stickLength;
    private int score;


    public GameEngine() {
    }

    public void init() {

        assignDistance();
        stickLength = 0;
        score = 0;

        gameOver = false;
        moving = false;
    }

    private void assignDistance() {
        Random rand = new Random();

        distance = 0;
        while (distance < GameEngine.MIN_DISTANCE)
            distance = rand.nextInt(GameEngine.MAX_DISTANCE);

    }

    public void increaseStickLength() {
        stickLength+= 3;
    }

    public void checkForGameOver() {
        if (stickLength < distance  || distance + Images.REC_WIDTH < stickLength)
            gameOver = true;
    }

    public void nextRectangle() {
        assignDistance();

        moving = false;
        stickLength = 0;
        score++;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getDistance() {
        return distance;
    }

    public int getStickLength() {
        return stickLength;
    }

    public int getScore() {
        return score;
    }
}
