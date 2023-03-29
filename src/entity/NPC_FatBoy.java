package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_FatBoy extends Entity {

    public NPC_FatBoy(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1 * gp.scale;
        hitBoxArea = new Rectangle(0, 0, 21 * gp.scale, 20 * gp.scale);
        hitBoxArea1 = new Rectangle();

        getNPCImages("fatBoy");
        setDialogue();
    }

    public void getNPCImages(String fileName) {
        down0 = setup("/npc/" + fileName + "Down0");
        up0 = setup("/npc/" + fileName + "Up0");
        left0 = setup("/npc/" + fileName + "Left0");
        right0 = setup("/npc/" + fileName + "Right0");
    }

    public void setDialogue(){
        dialogues[0] = "Technology just blows me away !";
        dialogues[1] = "I mean, now you can play with people\naround the world... wirelessly !";
    }


    private static final String[] dirs = {"up", "down", "left", "right"};
    public void setBehaviour(){
        behavLockCount++;
        if (behavLockCount == 60) {
            if (new Random().nextInt(10) >= 3) {
                direction = dirs[new Random().nextInt(dirs.length)];
            }
            behavLockCount = 0;
        }
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
    public void update(){
        setBehaviour();

        collisionOn = false;
        gp.cc.checkTile(this);
        gp.cc.checkObject(this, false);
        gp.cc.checkPlayer(this);
    }
}
