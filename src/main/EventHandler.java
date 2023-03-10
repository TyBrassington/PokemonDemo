package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    int prevEventX, prevEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol* gp.tileSize][gp.maxWorldRow*gp.tileSize];
        int map = 0;
        int x = 0;
        int y = 0;
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
            if (hit(0, 0, 0, "any")){
                teleport(1,24,20);
            } else if (hit(1, 0, 0, "any")){
                teleport(0,24,20);
            }
        }
    }

    public boolean hit(int map, int eventX, int eventY, String reqDir){
        boolean hit = false;

        if (map ==  gp.curMap){
            gp.player.hitBoxArea.x = gp.player.worldX + gp.player.hitBoxArea.x;
            gp.player.hitBoxArea.y = gp.player.worldY + gp.player.hitBoxArea.y;

            eventRect[map][eventX][eventY].x = eventY + eventRect[map][eventX][eventY].x;
            eventRect[map][eventX][eventY].y = eventX + eventRect[map][eventX][eventY].y;

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

    public void teleport(int map, int col, int row){

        gp.curMap = map;
        gp.player.worldX = gp.tileSize*col;
        gp.player.worldY = gp.tileSize*row;
        prevEventX = gp.player.worldX;
        prevEventY = gp.player.worldY;
        canTouchEvent = false;
    }
}
