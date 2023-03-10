package object;

import environment.Lighting;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_House extends SuperObject {

    public OBJ_House(GamePanel gp) {

        name = "House";

        hitBoxArea = new Rectangle();
        hitBoxArea.x = 4 * gp.scale;
        hitBoxArea.y = 16 * gp.scale;
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = 27;
        hitBoxArea.height = 177;
        collision = true;

        hitBoxArea1 = new Rectangle();
        hitBoxArea1.x = 13 * gp.scale;
        hitBoxArea1.y = 16 * gp.scale;
        hitBoxAreaDefaultX1 = hitBoxArea1.x;
        hitBoxAreaDefaultY1 = hitBoxArea1.y;
        hitBoxArea1.width = 66;
        hitBoxArea1.height = 114;

        hitBoxArea2 = new Rectangle();
        hitBoxArea2.x = 35 * gp.scale;
        hitBoxArea2.y = 16 * gp.scale;
        hitBoxAreaDefaultX2 = hitBoxArea2.x;
        hitBoxAreaDefaultY2 = hitBoxArea2.y;
        hitBoxArea2.width = 75;
        hitBoxArea2.height = 177;

        Lighting lighting = new Lighting(gp);
        if (lighting.getFilterAlpha() < 0.2f) {
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/houseDL.png"));
                getScaledImage(image, gp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/houseDL.png"));
                getScaledImage(image, gp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void setHouseDayCycle(GamePanel gp) {
        Lighting lighting = new Lighting(gp);
        while (true) {
            if (lighting.getFilterAlpha() < 0.2f) {
                try {
                    image = ImageIO.read(getClass().getResourceAsStream("/objects/houseDL.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    image = ImageIO.read(getClass().getResourceAsStream("/objects/houseDL.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

