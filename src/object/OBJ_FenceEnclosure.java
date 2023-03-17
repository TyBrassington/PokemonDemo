package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_FenceEnclosure extends SuperObject {
    public OBJ_FenceEnclosure(GamePanel gp) {

        name = "FenceEnclosure";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/fenceEnclosure.png"));
            getScaledImage(image, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle();
        hitBoxArea.x = 0;
        hitBoxArea.y = 0;
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = image.getWidth() * gp.scale + 3;//66
        hitBoxArea.height = image.getHeight() * gp.scale - 3;//51

        hitBoxArea1 = new Rectangle();
        hitBoxArea2 = new Rectangle();
        collision = true;
    }
}
