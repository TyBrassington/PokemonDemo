package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_RouteSign extends SuperObject{
    public OBJ_RouteSign(GamePanel gp){

        name = "Route Sign";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/routeSign.png"));
            getScaledImage(image, gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hitBoxArea = new Rectangle(6*gp.scale, 12*gp.scale, 22, 30);
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        collision = true;

        hitBoxArea1 = new Rectangle();
        hitBoxArea2 = new Rectangle();
    }
}
