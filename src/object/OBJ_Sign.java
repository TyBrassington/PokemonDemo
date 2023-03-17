package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Sign extends SuperObject{

    public OBJ_Sign(GamePanel gp){

        name = "Sign";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/sign.png"));
            getScaledImage(image, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle(new Rectangle(6*gp.scale, 12*gp.scale, 22, 30));
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        collision = true;

        hitBoxArea1 = new Rectangle();
        hitBoxArea2 = new Rectangle();
    }
}
