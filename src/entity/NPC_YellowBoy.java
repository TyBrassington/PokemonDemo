package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_YellowBoy extends Entity {

    public NPC_YellowBoy(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1 * gp.scale;
        hitBoxArea = new Rectangle(0, 0, 17 * gp.scale, 22 * gp.scale);
        hitBoxArea1 = new Rectangle();

        getNPCImages();
    }

    private void getNPCImages() {
        down0 = setup("/npc/yellowBoyDown0");
        up0 = setup("/npc/yellowBoyUp0");
        left0 = setup("/npc/yellowBoyLeft0");
        right0 = setup("/npc/yellowBoyRight0");
        down1 = setup("/npc/yellowBoyDown1");
        up1 = setup("/npc/yellowBoyUp1");
        left1 = setup("/npc/yellowBoyLeft1");
        right1 = setup("/npc/yellowBoyRight1");
        down2 = setup("/npc/yellowBoyDown1");
        up2 = setup("/npc/yellowBoyUp1");
        left2 = setup("/npc/yellowBoyLeft1");
        right2 = setup("/npc/yellowBoyRight1");
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
        gp.cc.checkTile(this);
        gp.cc.checkObject(this, false);
        gp.cc.checkPlayer(this);
    }
}
