package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Tree extends SuperObject{
    public OBJ_Tree(GamePanel gp){

        name = "Tree";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/tree.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle();
        hitBoxArea.x = 1*gp.scale;
        hitBoxArea.y = 10*gp.scale;
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = (image.getWidth()*gp.scale)-(2*gp.scale);
        hitBoxArea.height = image.getHeight()* gp.scale;
        collision = true;
    }
}
