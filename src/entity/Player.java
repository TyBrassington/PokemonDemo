package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    private HashMap<String, BufferedImage[]> images;
    boolean dirKeyPressed;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize);

        hitBoxArea = new Rectangle(3 * gp.scale, 0 * gp.scale, 11 * gp.scale, 24 * gp.scale); //(3,8,11,16)
        hitBoxArea1 = new Rectangle(3 * gp.scale, 8 * gp.scale, 11 * gp.scale, 15 * gp.scale);
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;

        setDefaultValues();
        images = new HashMap<>(); // add this
        loadPlayerImages(); // add this
    }

    public void setDefaultValues() {

        worldX = ((gp.tileSize * 23));
        worldY = ((gp.tileSize * 24) + (gp.tileSize * 25)) / 2;
        speed = 1 * gp.scale; //temp usually 1*gp.scale
        isRunning = false;
        direction = "down";
    }

    public void adjustPlayerHitboxes() {
        if (!isRunning || (isRunning && !keyH.leftPressed && !keyH.rightPressed)) {
            hitBoxArea = new Rectangle(3 * gp.scale, 0 * gp.scale, 11 * gp.scale, 24 * gp.scale); //(3,8,11,16)
            hitBoxArea1 = new Rectangle(3 * gp.scale, 8 * gp.scale, 11 * gp.scale, 15 * gp.scale);
        } else {
            hitBoxArea = new Rectangle(3 * gp.scale, 0 * gp.scale, 13 * gp.scale, 24 * gp.scale); //(3,8,11,16)
            hitBoxArea1 = new Rectangle(3 * gp.scale, 8 * gp.scale, 13 * gp.scale, 15 * gp.scale);
        }
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
    }


    private void loadPlayerImages() {
        String[] directions = {"up", "down", "left", "right"};
        String[] actions = {"", "Run"};
        int numSprites = 3;
        String[] extensions = {"0.png", "1.png", "2.png"};

        for (String dir : directions) {
            for (String action : actions) {
                BufferedImage[] spriteImages = new BufferedImage[numSprites];
                for (int i = 0; i < numSprites; i++) {
                    String path = "/player/lucas" + action + dir + extensions[i];
                    try {
                        spriteImages[i] = ImageIO.read(getClass().getResourceAsStream(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                images.put(action + dir, spriteImages);
            }
        }
        System.out.println("Player successfully loaded.");
    }
    private int npcIndex;
    public void update() {
        adjustPlayerHitboxes();
        dirKeyPressed = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;
        if (dirKeyPressed) {
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

            npcIndex = gp.cc.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            gp.eHandler.checkEvent();

            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            int maxSpriteCounter = isRunning ? 10 : 12;
            if (spriteCounter > maxSpriteCounter) {
                spriteNum = (spriteNum + 1) % 3;
                spriteCounter = 0;
            }
        } else {
            spriteNum = 0;
        }
    }

    public void interactNPC(int i){

        if (i != 999){
            if (gp.keyH.spacePressed) {
                isRunning = false;
                gp.player.speed = 1 * gp.scale;
                spriteNum = 0; //to avoid pause mid walk
                gp.gameState = gp.dialogueState;
                gp.npc[gp.curMap][i].speak();
            }
        }
        gp.keyH.spacePressed = false;
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image;
        String key = (isRunning && dirKeyPressed) ? "Run" + direction : direction;
        BufferedImage[] spriteImages = images.get(key);
        image = spriteImages[spriteNum];
        if (isRunning && dirKeyPressed) {
            g2d.drawImage(image, screenX - (1 * gp.scale), screenY, 21 * gp.scale, 26 * gp.scale, null);
        } else g2d.drawImage(image, screenX, screenY, 17 * gp.scale, 25 * gp.scale, null);


        //DRAW PLAYER HITBOX
/*        g2d.setColor(new Color(255, 0, 0, 120));
        g2d.fillRect(screenX + hitBoxArea.x, screenY + hitBoxArea.y, hitBoxArea.width, hitBoxArea.height);
        g2d.setColor(new Color(0, 21, 255, 120));
        g2d.fillRect(screenX + hitBoxArea1.x, screenY + hitBoxArea1.y, hitBoxArea1.width, hitBoxArea1.height);*/

    }
}

