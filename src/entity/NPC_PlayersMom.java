package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_PlayersMom extends Entity {

    public NPC_PlayersMom(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1 * gp.scale;
        hitBoxArea = new Rectangle(3 * gp.scale, 2 * gp.scale, 13 * gp.scale, 18 * gp.scale);
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea1 = new Rectangle();

        getNPCImages("playersMom");
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0] = "Mom:  Lucas !";
        dialogues[1] = "Barry came calling for you\na little while ago.";
        dialogues[2] = "I don't know what it was about, but\nhe said it was an emergency.";

    }

    public void speak() {
        gp.subdialogueDone = false;
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
            gp.subdialogueDone = true;
            gp.eHandler.map1Event0Done = true;
            gp.gameState = gp.playState;
        }
        gp.ui.curDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    private static final String[] dirs = {"up", "down", "left", "right"};

    private static final Random random = new Random();

    public void setBehaviour() {
        behavLockCount++;
        if (behavLockCount == 60) {
            if (random.nextInt(10) >= 3) {
                direction = dirs[random.nextInt(dirs.length)];
            }
            behavLockCount = 0;
        }
    }


    public void update() {
        setBehaviour();
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;

        collisionOn = false;
        gp.cc.checkTile(this);
        gp.cc.checkObject(this, false);
        gp.cc.checkPlayer(this);

        if (!gp.eHandler.map1Event0DonePrelim) {
            if (worldX < 143 * gp.scale) {
                direction = "right";
                worldX += speed;
            } else if (worldY > 49 * gp.scale) {
                direction = "up";
                worldY -= speed;
            } else {
                gp.eHandler.map1Event0DonePrelim = true;
            }
            spriteCounter++;
            int maxSpriteCounter = 12;
            if (spriteCounter > maxSpriteCounter) {
                spriteNum = (spriteNum + 1) % 3;
                spriteCounter = 0;
            }
        } else if (!gp.eHandler.map1Event0Done && gp.eHandler.map1Event0DonePrelim) {
            gp.eHandler.map1Event0();
        }
        if (gp.eHandler.map1Event0Done && gp.eHandler.map1Event0DonePrelim) {
            if (worldY < 88 * gp.scale) {
                direction = "down";
                worldY += speed;
                spriteCounter++;
                int maxSpriteCounter = 10;
                if (spriteCounter > maxSpriteCounter) {
                    spriteNum = (spriteNum + 1) % 3;
                    spriteCounter = 0;
                }
            } else if (worldX > 95 * gp.scale) {
                direction = "left";
                worldX -= speed;
                spriteCounter++;
                int maxSpriteCounter = 12;
                if (spriteCounter > maxSpriteCounter) {
                    spriteNum = (spriteNum + 1) % 3;
                    spriteCounter = 0;
                }
            } else spriteNum = 0;
        }
    }
}
