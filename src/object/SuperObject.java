package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public BufferedImage scaledImage;

    public String name;
    public boolean collision = false;
    public int mapNum;
    public float alpha = 1f;
    public int worldX, worldY;
    public Rectangle hitBoxArea, hitBoxArea1, hitBoxArea2;
    public int hitBoxAreaDefaultX, hitBoxAreaDefaultX1, hitBoxAreaDefaultX2;
    public int hitBoxAreaDefaultY, hitBoxAreaDefaultY1, hitBoxAreaDefaultY2;


    public void draw(Graphics2D g2d, GamePanel gp) {
        mapNum = gp.curMap;
        if (gp.aSet.currentMapNum == gp.curMap && mapNum == gp.curMap) {
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            boolean isTileWithinXBounds = worldX + image.getWidth() * gp.scale + 60 > gp.player.worldX - gp.player.screenX && worldX - image.getWidth() * gp.scale - 60 < gp.player.worldX + gp.player.screenX;
            boolean isTileWithinYBounds = worldY + image.getHeight() * gp.scale + 60 > gp.player.worldY - gp.player.screenY && worldY - image.getHeight() * gp.scale - 60 < gp.player.worldY + gp.player.screenY;
            if (isTileWithinXBounds && isTileWithinYBounds) {
                AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
                g2d.setComposite(ac);
                g2d.drawImage(scaledImage, screenX, screenY, null);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
/*
            g2d.setColor(new Color(255, 0,0,120+));
            g2d.fillRect(screenX + hitBoxArea.x, screenY + hitBoxArea.y, hitBoxArea.width, hitBoxArea.height);

            g2d.setColor(new Color(0, 4, 255,120));
            g2d.fillRect(screenX + hitBoxArea1.x, screenY + hitBoxArea1.y, hitBoxArea1.width, hitBoxArea1.height);

            g2d.setColor(new Color(8, 255, 0,120));
            g2d.fillRect(screenX + hitBoxArea2.x, screenY + hitBoxArea2.y, hitBoxArea2.width, hitBoxArea2.height);
*/
            }
        }
    }

    public BufferedImage getScaledImage(BufferedImage image, GamePanel gp) {
        scaledImage = new BufferedImage(image.getWidth() * gp.scale, image.getHeight() * gp.scale, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, scaledImage.getWidth(), scaledImage.getHeight(), null);
        g2d.dispose();
        return scaledImage;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

}
