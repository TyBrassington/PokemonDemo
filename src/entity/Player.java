package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){

        try{
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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
                y -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
                x -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                x += speed;
            }
            spriteCounter++;

            if (spriteCounter > 12) {
                if (spriteNum == 0) {
                    if (keyH.keyPressed) {
                        spriteNum = 1;
                    }
                } else if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 0;
                }
                spriteCounter = 0;
            }
        } else spriteNum = 0;
    }



    public void draw(Graphics2D g2d){
        BufferedImage image = null;

        switch(direction){
            case "up":
                if (spriteNum == 0) {
                    image = up0;
                }
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 0) {
                    image = down0;
                }
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 0) {
                    image = left0;
                }
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 0) {
                    image = right0;
                }
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2d.drawImage(image, x, y, 17*3, 25*3, null);
    }
}
