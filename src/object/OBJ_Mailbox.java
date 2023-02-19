package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_Mailbox extends SuperObject{
    public OBJ_Mailbox(GamePanel gp){

        name = "Mailbox";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/mailbox.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle();
        hitBoxArea.x = 2*gp.scale;
        hitBoxArea.y = 10*gp.scale;
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = 36;
        hitBoxArea.height = 60;
        collision = true;
    }
}
