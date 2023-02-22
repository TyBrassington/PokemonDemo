package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle hitBoxArea, hitBoxArea1, hitBoxArea2;
    public int hitBoxAreaDefaultX, hitBoxAreaDefaultX1, hitBoxAreaDefaultX2;
    public int hitBoxAreaDefaultY, hitBoxAreaDefaultY1, hitBoxAreaDefaultY2;

    public void draw(Graphics2D g2d, GamePanel gp){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        boolean isTileWithinXBounds = worldX + image.getWidth() *gp.scale+60> gp.player.worldX - gp.player.screenX && worldX - image.getWidth()*gp.scale-60< gp.player.worldX + gp.player.screenX;
        boolean isTileWithinYBounds = worldY + image.getHeight() *gp.scale+60> gp.player.worldY - gp.player.screenY && worldY - image.getHeight()*gp.scale-60 < gp.player.worldY + gp.player.screenY;
        if(isTileWithinXBounds && isTileWithinYBounds) {
            g2d.drawImage(image, screenX, screenY, image.getWidth() * gp.scale, image.getHeight() * gp.scale, null);
/*
            g2d.setColor(new Color(255, 0,0,120));
            g2d.fillRect(screenX + hitBoxArea.x, screenY + hitBoxArea.y, hitBoxArea.width, hitBoxArea.height);

            g2d.setColor(new Color(0, 4, 255,120));
            g2d.fillRect(screenX + hitBoxArea1.x, screenY + hitBoxArea1.y, hitBoxArea1.width, hitBoxArea1.height);

            g2d.setColor(new Color(8, 255, 0,120));
            g2d.fillRect(screenX + hitBoxArea2.x, screenY + hitBoxArea2.y, hitBoxArea2.width, hitBoxArea2.height);
*/



        }

    }
}
