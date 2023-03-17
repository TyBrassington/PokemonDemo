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

        hitBoxArea = new Rectangle(4 * gp.scale, 16 * gp.scale, 27, 177);
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        collision = true;

        hitBoxArea1 = new Rectangle(13 * gp.scale, 16 * gp.scale, 66, 114);
        hitBoxAreaDefaultX1 = hitBoxArea1.x;
        hitBoxAreaDefaultY1 = hitBoxArea1.y;

        hitBoxArea2 = new Rectangle(35 * gp.scale, 16 * gp.scale, 75, 177);
        hitBoxAreaDefaultX2 = hitBoxArea2.x;
        hitBoxAreaDefaultY2 = hitBoxArea2.y;

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
        System.out.println(lighting.getFilterAlpha());


    }

}

