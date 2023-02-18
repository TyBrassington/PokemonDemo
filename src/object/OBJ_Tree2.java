package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Tree2 extends SuperObject{
    public OBJ_Tree2(GamePanel gp){

        name = "Tree 2";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/tree2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle();
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = (image.getWidth()*gp.scale);
        hitBoxArea.height = image.getHeight()* gp.scale;
        collision = true;
        collision = true;
    }
}
