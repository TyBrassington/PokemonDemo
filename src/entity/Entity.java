package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY, speed;
    public BufferedImage down0, down1, down2, up0, up1, up2, left0, left1, left2, right0, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle hitBoxArea, hitBoxArea1;
    public int hitBoxAreaDefaultX, hitBoxAreaDefaultY;
    public boolean collisionOn = false;
}
