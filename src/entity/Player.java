package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize);

        hitBoxArea = new Rectangle(3*gp.scale,8*gp.scale,11*gp.scale,16*gp.scale);
        hitBoxArea1 = new Rectangle(3*gp.scale,8*gp.scale,11*gp.scale,15*gp.scale);
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = ((gp.tileSize * 23));
        worldY = ((gp.tileSize * 24)+(gp.tileSize * 25))/2;
        speed = 1*gp.scale;
        direction = "down";
    }

    public void getPlayerImage() {

        try {
            down0 = ImageIO.read(getClass().getResourceAsStream("/player/lucasDown0.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/lucasDown1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/lucasDown2.png"));
            up0 = ImageIO.read(getClass().getResourceAsStream("/player/lucasUp0.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/lucasUp1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/lucasUp2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/player/lucasLeft0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/lucasLeft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/lucasLeft2.png"));
            right0 = ImageIO.read(getClass().getResourceAsStream("/player/lucasRight0.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/lucasRight1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/lucasRight2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Player successfully loaded.");
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }
            collisionOn = false;
            gp.cc.checkTile(this);

            gp.cc.checkObject(this, true);

            if(!collisionOn){
                switch (direction) {
                    case "up": worldY -= speed;
                        break;
                    case "down": worldY += speed;
                        break;
                    case "left": worldX -= speed;
                        break;
                    case "right": worldX += speed;
                        break;
                }
            }

            spriteCounter++;

            if (spriteCounter > 12) {
                spriteNum = (spriteNum + 1) % 3;
                spriteCounter = 0;
            }
        } else spriteNum = 0;
    }


    public void draw(Graphics2D g2d) {
        BufferedImage image = null;

            switch (direction) {
                case "up":
                    image = spriteNum == 0 ? up0 : spriteNum == 1 ? up1 : up2;
                    break;
                case "down":
                    image = spriteNum == 0 ? down0 : spriteNum == 1 ? down1 : down2;
                    break;
                case "left":
                    image = spriteNum == 0 ? left0 : spriteNum == 1 ? left1 : left2;
                    break;
                case "right":
                    image = spriteNum == 0 ? right0 : spriteNum == 1 ? right1 : right2;
                    break;
            }
        g2d.drawImage(image, screenX, screenY, 17 * gp.scale, 25 * gp.scale, null);

        //DRAW PLAYER HITBOX
      /* g2d.setColor(new Color(255, 0,0,120));
        int solidAreaX = screenX + hitBoxArea.x;
        int solidAreaY = screenY + hitBoxArea.y;
        int solidAreaWidth = hitBoxArea.width;
        int solidAreaHeight = hitBoxArea.height;
        g2d.fillRect(solidAreaX, solidAreaY, solidAreaWidth, solidAreaHeight);
        g2d.setColor(new Color(0, 21, 255,120));
        int solidAreaX1 = screenX + hitBoxArea1.x;
        int solidAreaY1 = screenY + hitBoxArea1.y;
        int solidAreaWidth1 = hitBoxArea1.width;
        int solidAreaHeight1 = hitBoxArea1.height;
        g2d.fillRect(solidAreaX1, solidAreaY1, solidAreaWidth1, solidAreaHeight1);
*/

    }
}
