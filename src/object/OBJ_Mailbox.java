package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Mailbox extends SuperObject {
    public OBJ_Mailbox(GamePanel gp) {

        name = "Mailbox";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/mailbox.png"));
            getScaledImage(image, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle(2 * gp.scale, 10 * gp.scale, 36, 57);
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        collision = true;

        hitBoxArea1 = new Rectangle();
        hitBoxArea2 = new Rectangle();
    }
}
