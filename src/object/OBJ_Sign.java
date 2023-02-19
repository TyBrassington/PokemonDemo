package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Sign extends SuperObject{

    public OBJ_Sign(GamePanel gp){

        name = "Sign";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/sign.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle();
        hitBoxArea.x =  6*gp.scale;
        hitBoxArea.y = 12*gp.scale;
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = 22;
        hitBoxArea.height = 36;
        collision = true;
    }
}
