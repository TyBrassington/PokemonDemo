package main;

import entity.Player;
import environment.EnvironmentManager;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16;
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionScanner cc = new CollisionScanner(this);
    public AssetSetter aSet = new AssetSetter(this);
    EnvironmentManager em = new EnvironmentManager(this);
    SoundManager sm = new SoundManager();

    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[200];


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setOpaque(true);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSet.setObjectFromFile();
        if (sm.playMusic) {
            playMusic(0);
            System.out.println("Game music successfully loaded.");
        }
        em.setup();
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
        int redrawCount = 0;

        while (gameThread != null) {
            curTime = System.nanoTime();
            delta += (curTime - lastTime)/drawInterval;
            timer += (curTime - lastTime);
            lastTime = curTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
                redrawCount++;
            }

            if(timer >= 1000000000){
                //Check FPS of game
                if (redrawCount<=59) {
                    //System.out.println("FPS has dropped");
                }
                //System.out.println("FPS:"+redrawCount);
                redrawCount  = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        player.update();
        em.update();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the game objects
        tileManager.draw(g2d);
        player.draw(g2d);
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2d, this);
            }
        }

        // Draw the environment
        em.draw(g2d);

        g2d.dispose();
    }

    public void playMusic(int i){
        sm.setFile(i);
        sm.play();
        sm.loop();
    }
    public void stopMusic(int i){
        sm.stop();
    }

    public void playSoundEffect(int i){
        sm.setFile(i);
        sm.play();
    }
}
