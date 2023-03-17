package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][], eventRect1[][][], eventRect2[][][];
    BufferedImage fadeToFromBlack;
    int prevEventX, prevEventY;
    boolean canTouchEvent = true;
    boolean oneTimeEventDone = false;
    private static final int noTrans = 0;
    private static final int startTrans = 1;
    private static final int midTrans = 2;
    private static final int endTrans = 3;
    public int transState = noTrans;

    int map, x, y;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol * gp.tileSize][gp.maxWorldRow * gp.tileSize];
        eventRect1 = new EventRect[gp.maxMap][gp.maxWorldCol * gp.tileSize][gp.maxWorldRow * gp.tileSize];
        map = 0;
        x = 0;
        y = 0;

        while (map < gp.maxMap && x < gp.maxWorldCol * gp.tileSize && y < gp.maxWorldRow * gp.tileSize) {
            createEventRects(map, x, y);

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

    public void createEventRects(int map, int x, int y) {
        eventRect[map][x][y] = new EventRect().x(0).y(0).width(66).height(57); //event rect for ext doors
        eventRect[map][x][y].eventRectDefaultX = eventRect[map][x][y].x;
        eventRect[map][x][y].eventRectDefaultY = eventRect[map][x][y].y;

        eventRect1[map][x][y] = new EventRect().x(0).y(0).width(300).height(10);  //eventRect for path from twinleaf to route 201
        eventRect1[map][x][y].eventRect1DefaultX = eventRect1[map][x][y].x;
        eventRect1[map][x][y].eventRect1DefaultY = eventRect1[map][x][y].y;

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
        if (distance > gp.tileSize / 2) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 460, 471, "up", 0)) {

                transState = 1;
                gp.stopMusic();
                gp.playSoundEffect(1);
                Timer transTimer = new Timer(600, evt -> {
                    teleport(1, 80, 116); //twinleafEXT to playerhouseDS
                });
                transTimer.setRepeats(false);
                transTimer.start();
                Timer timer = new Timer(600, evt -> gp.playSoundEffect(2));
                timer.setRepeats(false);
                timer.start();
            } else if (hit(1, 140, 80, "down", 1)) {
                transState = 1;
                gp.playSoundEffect(3);
                Timer transTimer = new Timer(600, evt -> {
                    teleport(0, 460, 481); //playerhouseDS to twinleafEXT
                });
                transTimer.setRepeats(false);
                transTimer.start();

                Timer timer = new Timer(1000, evt -> gp.playSoundEffect(1));
                Timer timer1 = new Timer(1600, evt -> gp.playSoundEffect(2));

                timer.setRepeats(false);
                timer.start();
                timer1.setRepeats(false);
                timer1.start();
            } else if (hit(0, 90, 350, "any", 1)) {
                transState = 1;
                gp.stopMusic();
                gp.playSoundEffect(3);
                Timer transTimer = new Timer(600, evt -> {
                    teleport(3, 30, 30); //twinleafEXT to Route 201 (placeholder currently)
                    gp.sm.setSEVolume(-40.0f);
                    gp.playMusic(5);
                });
                transTimer.setRepeats(false);
                transTimer.start();
            } else if (hit(3, 0, 0, "any", 1)) {
                transState = 1;
                gp.stopMusic();
                gp.playSoundEffect(3);
                Timer transTimer = new Timer(600, evt -> {
                    teleport(0, 390, 120); //Route 201 (placeholder currently) to twinleafEXT
                    gp.sm.setSEVolume(-40.0f);
                    gp.playMusic(0);
                });
                transTimer.setRepeats(false);
                transTimer.start();
            } else if (hit(1, 25, 120, "left", 0)) {
                transState = 1;
                gp.playSoundEffect(3);
                Timer transTimer = new Timer(600, evt -> {
                    teleport(2, 110, 42); //playerhouseDS to playerHouseUS
                });
                transTimer.setRepeats(false);
                transTimer.start();
            } else if (hit(2, 42, 128, "right", 0)) {
                transState = 1;
                gp.playSoundEffect(3);
                Timer transTimer = new Timer(600, evt -> {
                    teleport(1, 143, 31); //playerhouseUS to playerHouseDS
                    gp.sm.setSEVolume(-40.0f);
                });
                transTimer.setRepeats(false);
                transTimer.start();
            }
        }
    }

    public boolean hit(int map, int eventY, int eventX, String reqDir, int eventRectNum) {
        boolean hit = false;

        if (map == gp.curMap) {
            gp.player.hitBoxArea.x = gp.player.worldX + gp.player.hitBoxArea.x;
            gp.player.hitBoxArea.y = gp.player.worldY + gp.player.hitBoxArea.y;


            switch (eventRectNum) {
                case 0 -> {
                    eventRect[map][eventY][eventX].x += eventX * gp.scale;
                    eventRect[map][eventY][eventX].y += eventY * gp.scale;

                    if (gp.player.hitBoxArea.intersects(eventRect[map][eventY][eventX])) {
                        if (gp.player.direction.contentEquals(reqDir) || reqDir.contentEquals("any")) {
                            hit = true;

                            prevEventX = gp.player.worldX;
                            prevEventY = gp.player.worldY;
                        }
                    }
                }
                case 1 -> {
                    eventRect1[map][eventY][eventX].x += eventX * gp.scale;
                    eventRect1[map][eventY][eventX].y += eventY * gp.scale;

                    if (gp.player.hitBoxArea.intersects(eventRect1[map][eventY][eventX])) {
                        if (gp.player.direction.contentEquals(reqDir) || reqDir.contentEquals("any")) {
                            hit = true;

                            prevEventX = gp.player.worldX;
                            prevEventY = gp.player.worldY;
                        }
                    }
                }
            }

            gp.player.hitBoxArea.x = gp.player.hitBoxAreaDefaultX;
            gp.player.hitBoxArea.y = gp.player.hitBoxAreaDefaultY;

            eventRect[map][eventY][eventX].x = eventRect[map][eventY][eventX].eventRectDefaultX;
            eventRect[map][eventY][eventX].y = eventRect[map][eventY][eventX].eventRectDefaultY;
            eventRect1[map][eventY][eventX].x = eventRect1[map][eventY][eventX].eventRect1DefaultX;
            eventRect1[map][eventY][eventX].y = eventRect1[map][eventY][eventX].eventRect1DefaultY;
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
