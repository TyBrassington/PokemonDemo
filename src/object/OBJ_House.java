package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_House extends SuperObject{

    public OBJ_House(GamePanel gp){

        name = "House";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/houseS.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle();
        hitBoxArea.x = 4*gp.scale;
        hitBoxArea.y = 16*gp.scale;
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = 169;
        hitBoxArea.height = 178;
        collision = true;
    }
}
