package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_LargeHouse extends SuperObject{

    public OBJ_LargeHouse(GamePanel gp){

        name = "Large House";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/largeHouseDL.png"));
            getScaledImage(image, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle();
        hitBoxArea.x = 7*gp.scale;
        hitBoxArea.y = 34*gp.scale;
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = 30;
        hitBoxArea.height = 195;

        hitBoxArea1 = new Rectangle();
        hitBoxArea1.x = 17*gp.scale;
        hitBoxArea1.y = 34*gp.scale;
        hitBoxAreaDefaultX1 = hitBoxArea1.x;
        hitBoxAreaDefaultY1 = hitBoxArea1.y;
        hitBoxArea1.width = 66;
        hitBoxArea1.height = 138;

        hitBoxArea2 = new Rectangle();
        hitBoxArea2.x = 39*gp.scale;
        hitBoxArea2.y = 34*gp.scale;
        hitBoxAreaDefaultX2 = hitBoxArea2.x;
        hitBoxAreaDefaultY2 = hitBoxArea2.y;
        hitBoxArea2.width = 129;
        hitBoxArea2.height = 195;

        collision = true;
    }
}
