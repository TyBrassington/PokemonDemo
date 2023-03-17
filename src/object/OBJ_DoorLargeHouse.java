package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_DoorLargeHouse extends SuperObject {

    public OBJ_DoorLargeHouse(GamePanel gp) {

        name = "DoorLH";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/doorLH.png"));
            getScaledImage(image, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle(0, 0, 0, 0);
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = 0;//66
        hitBoxArea.height = 0;//51

        hitBoxArea1 = new Rectangle();
        hitBoxArea2 = new Rectangle();
        collision = true;
    }
}
