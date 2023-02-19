package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle hitBoxArea;
    public int hitBoxAreaDefaultX;
    public int hitBoxAreaDefaultY;

    public void draw(Graphics2D g2d, GamePanel gp){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        boolean isTileWithinXBounds = worldX + image.getWidth() *gp.scale> gp.player.worldX - gp.player.screenX && worldX - image.getWidth()*gp.scale < gp.player.worldX + gp.player.screenX;
        boolean isTileWithinYBounds = worldY + image.getHeight() *gp.scale> gp.player.worldY - gp.player.screenY && worldY - image.getHeight()*gp.scale < gp.player.worldY + gp.player.screenY;

        //if(isTileWithinXBounds && isTileWithinYBounds) {
           g2d.drawImage(image, screenX, screenY, image.getWidth() * gp.scale, image.getHeight() * gp.scale, null);

           /* g2d.setColor(new Color(255, 0,0,120));
            int solidAreaX = screenX + hitBoxArea.x;
            int solidAreaY = screenY + hitBoxArea.y;
            int solidAreaWidth = hitBoxArea.width;
            int solidAreaHeight = hitBoxArea.height;
            g2d.fillRect(solidAreaX, solidAreaY, solidAreaWidth, solidAreaHeight);*/
        //}

    }
}
