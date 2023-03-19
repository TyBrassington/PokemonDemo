package environment;

import main.GamePanel;

import java.awt.*;
import java.util.Arrays;

public class DebugUI {
    GamePanel gp;
    Lighting lighting;

    public DebugUI(GamePanel gp, Lighting lighting) {
        this.gp = gp;
        this.lighting = lighting;
    }

    public void draw(Graphics2D g2d) {
        // Draw label if outdoors
        if (Arrays.asList(0, 3).contains(gp.curMap)) { // Maps 0 and 3 are both outdoors
            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("Pokémon DP Pro Regular", Font.PLAIN, 50));
            g2d.drawString(lighting.dayStateLabel, 23, 553);
            g2d.setColor(Color.WHITE);
            g2d.drawString(lighting.dayStateLabel, 20, 550);
        }

        // Debug player coords and map numb

        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("Pokémon DP Pro Regular", Font.PLAIN, 50));
        String coordinates = gp.player.worldX / 3 + " " + gp.player.worldY / 3;
        int coordinatesWidth = g2d.getFontMetrics().stringWidth(coordinates);
        g2d.drawString(coordinates, 740 - coordinatesWidth, 553);

        String mapName = "Map " + gp.curMap;
        int mapNameWidth = g2d.getFontMetrics().stringWidth(mapName);
        g2d.drawString(mapName, 740 - mapNameWidth, 503);

        g2d.setColor(Color.WHITE);
        g2d.drawString(coordinates, 737 - coordinatesWidth, 550);
        g2d.drawString(mapName, 737 - mapNameWidth, 500);


        // Debug map number
    }
}
