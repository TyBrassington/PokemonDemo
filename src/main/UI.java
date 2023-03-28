package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {

    GamePanel gp;
    Font pokemon_font;
    BufferedImage image;
    public String curDialogue = "";

    public UI(GamePanel gp){
        this.gp = gp;
        pokemon_font = new Font("Pokémon DP Pro Regular", Font.PLAIN, 47);
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/ui/dialogueBox.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2d){
        g2d.setFont(pokemon_font);

        if(gp.gameState == gp.playState){

        } else if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        } else if (gp.gameState == gp.dialogueState){

            g2d.drawImage(image,9 ,gp.screenHeight - image.getHeight()*gp.scale - 7,image.getWidth() * gp.scale, image.getHeight()* gp.scale, null);
            int y = 491;
            int yShadow = 494;
            for (String line : curDialogue.split("\n")) {
                g2d.setColor(new Color(165, 165,173));
                g2d.drawString(line, 48, yShadow);
                g2d.drawString(line, 45, yShadow);
                g2d.drawString(line, 48, y);
                g2d.setColor(new Color(82, 82, 90));

                g2d.drawString(line, 45, y);
                y += 48;
                yShadow += 48;
            }
        }
    }

    public void drawPauseScreen(){
    }

    public void drawDialogueScreen(){

    }
}
