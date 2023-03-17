package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_LargeHouse extends SuperObject {

    public OBJ_LargeHouse(GamePanel gp) {

        name = "Large House";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/largeHouseDL.png"));
            getScaledImage(image, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle(7 * gp.scale, 34 * gp.scale, 30, 195);
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;

        hitBoxArea1 = new Rectangle(17 * gp.scale, 34 * gp.scale, 66, 138);
        hitBoxAreaDefaultX1 = hitBoxArea1.x;
        hitBoxAreaDefaultY1 = hitBoxArea1.y;

        hitBoxArea2 = new Rectangle(39 * gp.scale, 34 * gp.scale, 129, 195);
        hitBoxAreaDefaultX2 = hitBoxArea2.x;
        hitBoxAreaDefaultY2 = hitBoxArea2.y;

        collision = true;
    }
}
