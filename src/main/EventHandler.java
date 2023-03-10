package main;

import object.SuperObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    BufferedImage fadeToFromBlack;
    int prevEventX, prevEventY;
    boolean canTouchEvent = true;

    private static final int noTrans = 0;
    private static final int startTrans = 1;
    private static final int midTrans = 2;
    private static final int endTrans = 3;
    public int transState = noTrans;

    int map, x, y;

    SuperObject myObject;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol * gp.tileSize][gp.maxWorldRow * gp.tileSize];
        map = 0;
        x = 0;
        y = 0;

        while (map < gp.maxMap && x < gp.maxWorldCol * gp.tileSize && y < gp.maxWorldRow * gp.tileSize) {
            eventRect[map][x][y] = new EventRect();
            eventRect[map][x][y].x = 0;
            eventRect[map][x][y].y = 0;
            eventRect[map][x][y].width = 66;
            eventRect[map][x][y].height = 57;
            eventRect[map][x][y].eventRectDefaultX = eventRect[map][x][y].x;
            eventRect[map][x][y].eventRectDefaultY = eventRect[map][x][y].y;

            x++;
            if (x == gp.maxWorldCol * gp.tileSize) {
                x = 0;
                y++;

                if (y == gp.maxWorldRow * gp.tileSize) {
                    y = 0;
                    map++;
                }
            }
        }
        setupTransition();
        update();
    }

    private void setupTransition() {
        fadeToFromBlack = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) fadeToFromBlack.getGraphics();
        g2d.setColor(new Color(0, 0, 0, 250));
        g2d.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2d.dispose();
    }

    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - prevEventX);
        int yDistance = Math.abs(gp.player.worldY - prevEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 460, 471, "up")) {

                transState = 1;
                gp.playSoundEffect(1);
                Timer transTimer = new Timer(600, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        teleport(1, 80, 116); //twinleafEXT to playerhouseDS
                    }
                });
                transTimer.setRepeats(false);
                transTimer.start();
                Timer timer = new Timer(600, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        gp.playSoundEffect(2);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else if (hit(1, 140, 80, "down")) {
                transState = 1;
                gp.playSoundEffect(3);
                Timer transTimer1 = new Timer(600, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        teleport(0, 460, 481); //twinleafEXT to playerhouseDS
                    }
                });
                transTimer1.setRepeats(false);
                transTimer1.start();

                Timer timer1 = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        gp.playSoundEffect(1);
                    }
                });
                Timer timer2 = new Timer(1600, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        gp.playSoundEffect(2);
                    }
                });

                timer1.setRepeats(false);
                timer1.start();
                timer2.setRepeats(false);
                timer2.start();
            }
        }
    }

    public boolean hit(int map, int eventX, int eventY, String reqDir) {
        boolean hit = false;

        if (map == gp.curMap) {
            gp.player.hitBoxArea.x = gp.player.worldX + gp.player.hitBoxArea.x;
            gp.player.hitBoxArea.y = gp.player.worldY + gp.player.hitBoxArea.y;

            eventRect[map][eventX][eventY].x = eventY * gp.scale + eventRect[map][eventX][eventY].x;
            eventRect[map][eventX][eventY].y = eventX * gp.scale + eventRect[map][eventX][eventY].y;

            if (gp.player.hitBoxArea.intersects(eventRect[map][eventX][eventY])) {
                if (gp.player.direction.contentEquals(reqDir) || reqDir.contentEquals("any")) {
                    hit = true;

                    prevEventX = gp.player.worldX;
                    prevEventY = gp.player.worldY;
                }
            }
            gp.player.hitBoxArea.x = gp.player.hitBoxAreaDefaultX;
            gp.player.hitBoxArea.y = gp.player.hitBoxAreaDefaultY;
            eventRect[map][eventX][eventY].x = eventRect[map][eventX][eventY].eventRectDefaultX;
            eventRect[map][eventX][eventY].y = eventRect[map][eventX][eventY].eventRectDefaultY;
        }

        return hit;

    }

    public void teleport(int map, int x, int y) {

        gp.curMap = map;
        gp.player.worldX = x * gp.scale;
        gp.player.worldY = y * gp.scale;
        prevEventX = gp.player.worldX;
        prevEventY = gp.player.worldY;
        canTouchEvent = false;
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2d.drawImage(fadeToFromBlack, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private int transCounter;
    private float filterAlpha;

    public void update() {
        switch (transState) {
            case noTrans -> {
                //Do nothing
            }
            case startTrans -> {
                gp.paused = true;
                filterAlpha = Math.min(filterAlpha + 0.05f, 1f);
                transState = (filterAlpha == 1f) ? midTrans : startTrans;
            }
            case midTrans -> {
                if (++transCounter > 60) {
                    transState = endTrans;
                    transCounter = 0;
                }
            }
            case endTrans -> {
                filterAlpha = Math.max(filterAlpha - 0.05f, 0f);
                transState = (filterAlpha == 0f) ? noTrans : endTrans;
                gp.paused = false;
            }

        }
    }


}
