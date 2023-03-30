package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;


public class UI {

    GamePanel gp;
    Font pokemon_font;
    BufferedImage dialogueBox;
    public String curDialogue = "";
    public int curDialogueIndex;
    public int curCharIndex;
    public int rollingTextDelay = 5;
    Timer timer = new Timer(rollingTextDelay, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            curCharIndex++; // increment the current character index
            gp.repaint(); // repaint the game panel to update the text
        }
    });
    public UI(GamePanel gp){
        this.gp = gp;
        pokemon_font = new Font("Pokémon DP Pro Regular", Font.PLAIN, 47);
        try {
            dialogueBox = ImageIO.read(getClass().getResourceAsStream("/ui/dialogueBox.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
/*        setInitDialogues();
        switchInitDialogue();*/
    }

    public void draw (Graphics2D g2d){
        g2d.setFont(pokemon_font);

        if(gp.gameState == gp.playState){
        } else if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        } else if (gp.gameState == gp.dialogueState){
            drawDialogueScreen();
            g2d.drawImage(dialogueBox,9 ,gp.screenHeight - dialogueBox.getHeight()*gp.scale - 7, dialogueBox.getWidth() * gp.scale, dialogueBox.getHeight()* gp.scale, null);
            int y = 491;
            int yShadow = 494;
            if (curCharIndex > curDialogue.length()){
                curCharIndex = curDialogue.length();
                gp.subdialogueDone = true;
                timer.stop();
            }
            String displayText = curDialogue.substring(0, curCharIndex); // get the substring of the dialogue up to the current character index
            for (String line : displayText.split("\n")) {

                //Draw shadows
                g2d.setColor(new Color(165, 165,173));
                g2d.drawString(line, 48, yShadow);
                g2d.drawString(line, 45, yShadow);
                g2d.drawString(line, 48, y);

                //Draw text
                g2d.setColor(new Color(82, 82, 90));
                g2d.drawString(line, 45, y);

                y += 48;
                yShadow += 48;
            }
        }
    }

   /* public void switchInitDialogue(){
        if (dialogues[curDialogueIndex] == null) {
            System.out.println("REACHED");
            gp.initDialogueDone = true;

            if (gp.gameState != gp.playState) {
                gp.gameState = gp.playState;
            }
        } else {
            gp.subdialogueDone = false;
        }
        if(!gp.initDialogueDone){
            System.out.println(curDialogueIndex);

            curDialogue = dialogues[curDialogueIndex];
            curDialogueIndex++;
        }
        System.out.println(curDialogue);
    }
    String dialogues[] = new String[20];
    public void setInitDialogues(){
        dialogues[0] = "That concludes our special report,\n\"Search for the Red GYARADOS!\"";
        dialogues[1] = "Brought to you be JubilifeTV on\nNationwide Net!";
        dialogues[2] = "See you next week, same time,\nsame channel!";

    }*/
    public void drawPauseScreen(){
    }

    public void drawDialogueScreen(){// reset the current character index
        timer.start(); // start the timer
    }
}
