package main;

import entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;
    BufferedImage backgroundImage;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);


    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        /*try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/frame/playerHomeBkgTest.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        this.setOpaque(true);
        this.setBackground(Color.green);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long curTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            curTime = System.nanoTime();
            delta += (curTime - lastTime)/drawInterval;
            timer += (curTime - lastTime);
            lastTime = curTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                //Check FPS of game
                //System.out.println("FPS:"+ drawCount);
                drawCount  = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        player.update();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        player.draw(g2d);
        g2d.dispose();
    }
}
