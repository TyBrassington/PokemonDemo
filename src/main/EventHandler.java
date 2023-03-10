package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    int prevEventX, prevEventY;
    boolean canTouchEvent = true;
    int map, x, y;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol* gp.tileSize][gp.maxWorldRow*gp.tileSize];

        map = 0;
        x = 0;
        y = 0;
        while (map < gp.maxMap && x < gp.maxWorldCol*gp.tileSize && y < gp.maxWorldRow*gp.tileSize){
            eventRect[map][x][y] = new EventRect();
            eventRect[map][x][y].x = 0;
            eventRect[map][x][y].y = 0;
            eventRect[map][x][y].width = 66;
            eventRect[map][x][y].height = 57;
            eventRect[map][x][y].eventRectDefaultX = eventRect[map][x][y].x;
            eventRect[map][x][y].eventRectDefaultY = eventRect[map][x][y].y;

            x++;
            if (x == gp.maxWorldCol* gp.tileSize){
                x=0;
                y++;

                if (y == gp.maxWorldRow* gp.tileSize){
                    y = 0;
                    map++;
                }
            }
        }
    }


    public void checkEvent(){
        int xDistance = Math.abs(gp.player.worldX - prevEventX);
        int yDistance = Math.abs(gp.player.worldY - prevEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize){
            canTouchEvent = true;
        }

        if (canTouchEvent){
            if (hit(0, 460, 471, "any")){
                teleport(1,80,116); //twinleafEXT to playerhouseDS
                gp.playSoundEffect(1);
                Timer timer = new Timer(400, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        gp.playSoundEffect(2);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else if (hit(1, 140, 80, "any")){
                teleport(0,460,481); //playhouseDS to twinleafEXT
                gp.playSoundEffect(3);
                Timer timer1 = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        gp.playSoundEffect(1);
                    }
                });
                Timer timer2 = new Timer(1400, new ActionListener() {
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

    public boolean hit(int map, int eventX, int eventY, String reqDir){
        boolean hit = false;

        if (map ==  gp.curMap){
            gp.player.hitBoxArea.x = gp.player.worldX + gp.player.hitBoxArea.x;
            gp.player.hitBoxArea.y = gp.player.worldY + gp.player.hitBoxArea.y;

            eventRect[map][eventX][eventY].x = eventY*gp.scale + eventRect[map][eventX][eventY].x;
            eventRect[map][eventX][eventY].y = eventX*gp.scale + eventRect[map][eventX][eventY].y;

            if (gp.player.hitBoxArea.intersects(eventRect[map][eventX][eventY])){
                if (gp.player.direction.contentEquals(reqDir) || reqDir.contentEquals("any")){
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

    public void teleport(int map, int x, int y){

        gp.curMap = map;
        gp.player.worldX = x*gp.scale;
        gp.player.worldY = y*gp.scale   ;
        prevEventX = gp.player.worldX;
        prevEventY = gp.player.worldY;
        canTouchEvent = false;
    }

    public EventRect[][][] getEventRect() {
        return eventRect;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(eventRect[map][x][y].x, eventRect[map][x][y].y, eventRect[map][x][y].width, eventRect[map][x][y].height);
    }


}
