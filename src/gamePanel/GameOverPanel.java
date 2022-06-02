package gamePanel;

import gameController.GameController;
import gameEngine.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public final class GameOverPanel extends JPanel {
    private GameEngine engine;
    JButton btreplay;
    JButton btquit;
    JButton X;
    JButton V;
    JTextField player;
    private static final Color color[]={Color.MAGENTA,Color.BLUE, Color.CYAN, Color.GRAY,Color.BLACK};
    
    private Connection con;
    private final String score[]= new String[5];
    private final String name[]= new String[5];

    public GameOverPanel(final GameEngine engine, final GameController controller) {
        this.setLayout(null);
        this.engine = engine;
        btreplay = new JButton() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(Images.replay, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        btreplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.replay();
            }
        });
        btreplay.setBounds(550, 540, 250, 100);
        add(btreplay);
                
        btquit = new JButton() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(Images.quit, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };

        btquit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.quit();
            }
        });
        btquit.setBounds(550, 700, 250, 100);
        add(btquit);
        
        database();
        inputName();
        setPreferredSize(new Dimension(Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT));
    }
    
    public void inputName(){
        player = new JTextField(36);
        player.setOpaque(false);
        player.setFont(new Font("Bahnschrift", Font.BOLD, 36));
        player.setForeground(Color.WHITE);
        player.setBounds(555, 350, 280, 54);
        add(player);
        
        X = new JButton() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(Images.X, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        X.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setText("");
            }
        });
        X.setBounds(650, 420, 50, 50);
        add(X);
        
        V = new JButton() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(Images.V, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        V.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inserttoDB();
                database();
                remove(V);
                remove(X);
            }
        });
        V.setBounds(750, 420, 50, 50);
        add(V);
        
    }
    
    public String checkScore(String s){
        if(s == null)
            return " ";
        return s;
    }

    public void database(){
        try {
            String dbURL = "jdbc:sqlserver://HOANGVANH_21226;databaseName=QLMB;user=sa;password=123";
            con = DriverManager.getConnection(dbURL);
            if (con != null) {
                int i=0;
                Statement start= con.createStatement();
                String sql="Select top 5* from test order by id desc";
                ResultSet rs= start.executeQuery(sql);
                while(rs.next()){
                    name[i]= rs.getString("name");
                    score[i]= checkScore(rs.getString("id"));
                    i++;
              }
            }
           } catch (Exception e) {
        }
    }
    
    public void inserttoDB(){
        try { 
            Statement st;
            st = con.createStatement();
            String sql = "insert into test values ("+engine.getScore()+",'"+player.getText()+"')";
            st.executeUpdate(sql);
        } catch (Exception e) {
        }
    }
    
    public boolean check5(){
        return engine.getScore() > Integer.parseInt(score[4]) && score[4] != null;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(Images.background, 0, 0, null);
        g2d.setFont(new Font("Snap ITC", Font.BOLD, 85));
        g2d.drawString("GAME OVER", 124, 120);
        
        g2d.setFont(new Font("Algerian", Font.BOLD, 60));
        g2d.drawString("HIGH SCORE", 150, 395);
        g2d.drawImage(Images.highscore, Images.BACKGROUND_WIDTH / 2 - 380,Images.BACKGROUND_HEIGHT / 2 - 80, null);

        g2d.setColor(Color.blue);
        g2d.setFont(new Font("Kristen ITC", Font.PLAIN, 60));
        g2d.drawString("YOUR SCORE : " + engine.getScore(), 200, 250);
        
        
        g2d.setFont(new Font("Kristen ITC", Font.PLAIN, 24));
        g2d.drawString("SCORE", 180, 470);
        g2d.drawString("NAME", 310, 470);
        
        
        g2d.setFont(new Font("Kristen ITC", Font.PLAIN, 36));
        for(int i=0; i<5; i++){
            g2d.setColor(color[i]);
            g2d.drawString(score[i], 230, 560+i*63);
            g2d.drawString(name[i], 310, 560+i*63);
        }
        
        g2d.drawImage(Images.yourname, 550, 300, 200, 40, null);
        
        g2d.drawImage(Images.player, 545, 342, 300, 70, this);
    }

}
