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

        getNPCImages();
    }

    private void getNPCImages() {
        down0 = setup("/npc/fatBoyDown0");
        up0 = setup("/npc/fatBoyUp0");
        left0 = setup("/npc/fatBoyLeft0");
        right0 = setup("/npc/fatBoyRight0");
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

    public void update(){
        setBehaviour();

        collisionOn = false;
        gp.cc.checkTile(this);
        gp.cc.checkObject(this, false);
        gp.cc.checkPlayer(this);
    }
}
