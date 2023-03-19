package environment;

import main.GamePanel;

import java.awt.*;
import java.util.Arrays;

public class EnvironmentManager {

    GamePanel gp;
    Lighting lighting;
    DebugUI debug;

    public EnvironmentManager(GamePanel gp){
        this.gp = gp;
    }

    public void setup(){
        lighting = new Lighting(gp);
        debug = new DebugUI(gp, lighting);
    }

    public void update(){
        lighting.update();
    }

    public void draw(Graphics2D g2d){
        // Draw the environment if outdoors
        if (Arrays.asList(0, 3).contains(gp.curMap)) { //maps 0 and 3 are both outdoors
            lighting.draw(g2d);
        }
        debug.draw(g2d);
    }
}
