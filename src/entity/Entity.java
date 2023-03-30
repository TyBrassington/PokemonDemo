package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;

    public int worldX, worldY, speed;
    public BufferedImage down0, down1, down2, up0, up1, up2, left0, left1, left2, right0, right1, right2;
    public BufferedImage runDown0, runDown1, runDown2, runUp0, runUp1, runUp2, runLeft0, runLeft1, runLeft2, runRight0, runRight1, runRight2;
    public String direction;


    public int spriteCounter = 0;
    public int spriteNum = 0;
    public Rectangle hitBoxArea, hitBoxArea1;
    public int hitBoxAreaDefaultX, hitBoxAreaDefaultY;
    public boolean collisionOn = false;
    public int behavLockCount;
    public boolean isRunning;
    String dialogues[] = new String[20];
    int dialogueIndex;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public BufferedImage setup(String filePath){

        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(filePath + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void speak() {
        gp.subdialogueDone = false;
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
            gp.subdialogueDone = true;
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

    public void setBehaviour(){

    }
    public void update(){
        setBehaviour();

        collisionOn = false;
        gp.cc.checkTile(this);
        gp.cc.checkObject(this, false);
        gp.cc.checkPlayer(this);

        //Movement randomized and enabled
        if (!collisionOn) {
            switch (direction) {
                case "up"-> worldY -= speed;
                case "down"-> worldY += speed;
                case "left"-> worldX -= speed;
                case "right"-> worldX += speed;
            }
        }
        spriteCounter++;
        int maxSpriteCounter = isRunning ? 10 : 12;
        if (spriteCounter > maxSpriteCounter) {
            spriteNum = (spriteNum + 1) % 3;
            spriteCounter = 0;
        }
    }

    public void getNPCImages(String fileName) {
        down0 = setup("/npc/" + fileName + "Down0");
        down1 = setup("/npc/" + fileName + "Down1");
        down2 = setup("/npc/" + fileName + "Down2");
        up0 = setup("/npc/" + fileName + "Up0");
        up1 = setup("/npc/" + fileName + "Up1");
        up2 = setup("/npc/" + fileName + "Up2");
        left0 = setup("/npc/" + fileName + "Left0");
        left1 = setup("/npc/" + fileName + "Left1");
        left2 = setup("/npc/" + fileName + "Left2");
        right0 = setup("/npc/" + fileName + "Right0");
        right1 = setup("/npc/" + fileName + "Right1");
        right2 = setup("/npc/" + fileName + "Right2");
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        switch (direction) {
            case "up" -> image = spriteNum == 0 ? up0 : spriteNum == 1 ? up1 : up2;
            case "down" -> image = spriteNum == 0 ? down0 : spriteNum == 1 ? down1 : down2;
            case "left" -> image = spriteNum == 0 ? left0 : spriteNum == 1 ? left1 : left2;
            case "right" -> image = spriteNum == 0 ? right0 : spriteNum == 1 ? right1 : right2;
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        if (gp.curMap != 2) {
            g2d.drawImage(image, screenX, screenY, image.getWidth() * gp.scale, image.getHeight() * gp.scale, null);
        }
        }
}
