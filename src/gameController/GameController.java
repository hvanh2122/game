package gameController;

import gameEngine.GameEngine;
import gamePanel.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GameController implements MouseListener, ActionListener{
    private GameEngine engine;
    private GamePanel panel;
    private boolean isIncreasing;
    private boolean isRunning;

    public void init(GameEngine engine, GamePanel panel) {
        this.engine = engine;
        this.panel = panel;
    }

    public void start() {
        Thread gameLoop = new Thread(new Runnable() {
            @Override
            public void run() {
                isRunning = true;
                while (isRunning) {
                    panel.repaint();
                    try {
                        Thread.sleep(engine.getSpeed());
                    } catch (Exception e) {

                    }
                }
            }
        });

        gameLoop.start();
    }


    public void nextRect() {
        engine.nextRectangle();
    }

    public void gameOver() {
        panel.gameOver();
    }

    public void replay() {
        panel.replay(engine, this);
        engine.init();
    }
    
    public void quit(){
        panel.quit();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (engine.isMoving()) {
            return;
        }

        Thread increaseStick = new Thread(new Runnable() {
            @Override
            public void run() {
                isIncreasing = true;
                while (isIncreasing) {
                    engine.increaseStickLength();
                    try {
                        Thread.sleep(engine.getSpeed());
                    } catch (Exception e) {

                    }
                }
            }
        });

        increaseStick.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isIncreasing = false;
        engine.setMoving(true);
        engine.checkForGameOver();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.goToGame();
    }

}
