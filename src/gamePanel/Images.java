package gamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Images {
    public static Image background;
    public static Image startButton;
    public static Image monkey5;
    public static Image walk1;
    public static Image walk2;
    public static Image walk3;
    public static Image walk4;
    public static Image gameover;
    public static Image replay;
    public static Image rectangle;
    public static Image name;
    public static Image highscore;
    public static Image quit;
    public static Image player;
    public static Image X;
    public static Image V;
    public static Image yourname;
    
    public static final int BACKGROUND_WIDTH = 900;
    public static final int BACKGROUND_HEIGHT = 900;
    public static final int MONKEY_WIDTH = 72;
    public static final int MONKEY_HEIGHT = 88;
    public static final int REC_WIDTH = 94;

    static {
        try {
            background = ImageIO.read(new File("images/back3.jpg"));
            startButton = ImageIO.read(new File("images/StartButton.png"));
            monkey5 = ImageIO.read(new File("images/monkey5.png"));
            walk1 = ImageIO.read(new File("images/khi22.png"));
            walk2 = ImageIO.read(new File("images/walk2.png"));
            walk3 = ImageIO.read(new File("images/walk4.png"));
            walk4 = ImageIO.read(new File("images/walk2.png"));
            gameover = ImageIO.read(new File("images/GameOver.png"));
            replay = ImageIO.read(new File("images/replay.png"));
            rectangle = ImageIO.read(new File("images/rectangle.png"));
            name = ImageIO.read(new File("images/name.png"));
            highscore =  ImageIO.read(new File("images/highscore.png"));
            quit =  ImageIO.read(new File("images/quit.png"));
            player =  ImageIO.read(new File("images/DIENTEN.png"));
            X =  ImageIO.read(new File("images/X.png"));
            V =  ImageIO.read(new File("images/V.png"));
            yourname =  ImageIO.read(new File("images/yourname.png"));
        } catch (IOException e) {

        }
    }

}
