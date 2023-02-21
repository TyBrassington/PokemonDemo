package object;

import environment.Lighting;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_House extends SuperObject {

    public OBJ_House(GamePanel gp) {

        name = "House";

        hitBoxArea = new Rectangle();
        hitBoxArea.x = 4 * gp.scale;
        hitBoxArea.y = 16 * gp.scale;
        hitBoxAreaDefaultX = hitBoxArea.x;
        hitBoxAreaDefaultY = hitBoxArea.y;
        hitBoxArea.width = 169;
        hitBoxArea.height = 178;
        collision = true;
        Lighting lighting = new Lighting(gp);
        if (lighting.getFilterAlpha() < 0.2f) {
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/house.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                image = ImageIO.read(getClass().getResourceAsStream("/objects/house.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        }

        public void setHouseDayCycle(GamePanel gp) {
            Lighting lighting = new Lighting(gp);
            while (true) {
                if (lighting.getFilterAlpha() < 0.2f) {
                    try {
                        image = ImageIO.read(getClass().getResourceAsStream("/objects/house.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        image = ImageIO.read(getClass().getResourceAsStream("/objects/house.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

