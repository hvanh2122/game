package gamePanel;

import gameController.GameController;

import javax.swing.*;
import java.awt.*;


public class StartPanel extends JPanel {
    private GameController controller;
    JButton button;

    public StartPanel(GameController controller) {
        this.controller = controller;
        this.setLayout(null);

        button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.drawImage(Images.startButton, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };


        button.addActionListener(controller);
        button.setBounds(Images.BACKGROUND_WIDTH/2 - 75, Images.BACKGROUND_HEIGHT/2 + 25, 150, 150);
        add(button);


        setPreferredSize(new Dimension(Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(Images.background, 0, 0, null);
        g2d.drawImage(Images.name, 200, 100, null);
    }
}
