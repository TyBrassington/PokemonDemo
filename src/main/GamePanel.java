package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
    public final int worldWidth = tileSize*maxScreenCol;
    public final int worldHeight = tileSize*maxScreenRow;
    int FPS = 60;
    BufferedImage backgroundImage;
    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionScanner cc = new CollisionScanner(this);
    public AssetSetter aSet = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[200];
    private final SoundManager soundManager = new SoundManager();


    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        /*try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/frame/playerHomeBkgTest.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        this.setOpaque(true);
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        boolean playMusic = true;
        if (playMusic) {
            soundManager.play("/audio/TwinLeafDay_EXT.wav");
            System.out.println("Game music successfully loaded.");
        }
    }

    public void setupGame(){
        aSet.setObjectFromFile();
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

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        tileManager.draw(g2d);


        player.draw(g2d);
        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2d, this);
            }
        }

        g2d.dispose();
    }

    public void stopMusic() {
        soundManager.stop();
    }
}
