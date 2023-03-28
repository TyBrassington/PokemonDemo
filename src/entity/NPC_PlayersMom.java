package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_PlayersMom extends Entity {

    public NPC_PlayersMom(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1 * gp.scale;
        hitBoxArea = new Rectangle(2 * gp.scale, 0, 15 * gp.scale, 20 * gp.scale);
        hitBoxArea1 = new Rectangle();

        getNPCImages("playersMom");
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
