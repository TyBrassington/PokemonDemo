package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Pseudo extends Entity {

    public NPC_Pseudo(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;
        hitBoxArea = new Rectangle(0, 0, 17 * gp.scale, 22 * gp.scale);
        hitBoxArea1 = new Rectangle();

        getNPCImages("yellowBoy");
        setDialogue();
    }

    private static final String[] dirs = {"up", "down", "left", "right"};

    public void setBehaviour() {
        behavLockCount++;
        if (behavLockCount == 60) {
            if (new Random().nextInt(10) >= 3) {
                direction = dirs[new Random().nextInt(dirs.length)];
            }
            behavLockCount = 0;
        }
    }


    public void update() {
        setBehaviour();

        collisionOn = false;
        if (dialogues[dialogueIndex] == null) {
            hitBoxArea = new Rectangle();
            gp.initDialogueDone = true;
        }
    }



    public void setDialogue(){
        dialogues[0] = "That concludes our special report,\n\"Search for the Red GYARADOS!\"";
        dialogues[1] = "Brought to you by JubilifeTV on\nNationwide Net!";
        dialogues[2] = "See you next week, same time,\nsame channel!";

    }
}
