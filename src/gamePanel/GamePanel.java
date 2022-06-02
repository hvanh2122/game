package gamePanel;

import gameController.GameController;
import gameEngine.GameEngine;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{
    private PlayPanel playPanel;
    private StartPanel startPanel;
    private GameOverPanel gameOverPanel;

    private GameEngine engine;
    private GameController controller;

    public void init(GameEngine engine, GameController controller) {
        this.engine = engine;
        this.controller = controller;

        gameOverPanel = new GameOverPanel(engine, controller);
        startPanel = new StartPanel(controller);
        playPanel = new PlayPanel(engine, controller);
        playPanel.addMouseListener(controller);

        this.setLayout(new BorderLayout());

        this.add(startPanel, BorderLayout.CENTER);
    }

    public void goToGame() {
        this.remove(startPanel);
        this.add(playPanel, BorderLayout.CENTER);
        this.revalidate();
    }


    public void gameOver() {
        this.remove(playPanel);
        this.add(gameOverPanel, BorderLayout.CENTER);
        this.revalidate();
    }

    public void replay(GameEngine engine, GameController controller) {
        this.remove(gameOverPanel);
        revalidate();
        init(engine, controller);
    }

    public void quit() {
        System.exit(0);
    }

}
