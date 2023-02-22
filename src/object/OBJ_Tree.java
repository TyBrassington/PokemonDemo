package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Tree extends SuperObject{
    public OBJ_Tree(GamePanel gp){

        name = "Tree";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/treeS.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle();

        hitBoxAreaDefaultX = hitBoxArea.x+18;
        hitBoxAreaDefaultY = hitBoxArea.y+64;
        hitBoxArea.width = (image.getWidth()*gp.scale)-36;
        hitBoxArea.height = image.getHeight()* gp.scale-73;
        collision = true;

        hitBoxArea1 = new Rectangle();
        hitBoxArea2 = new Rectangle();
    }
}
