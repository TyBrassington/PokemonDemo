package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_LargeHouse extends SuperObject{

    public OBJ_LargeHouse(GamePanel gp){

        name = "Large House";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/largeHouseS.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle();
        hitBoxArea.x = 7*gp.scale;
        hitBoxArea.y = 34*gp.scale;
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = 225;
        hitBoxArea.height = 195;
        collision = true;
    }
}
