package environment;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.GamePanel;
import object.OBJ_House;

public class Lighting {

    private GamePanel gamePanel;
    private BufferedImage darknessFilter;
    private int dayCounter;
    private float filterAlpha;

    private static final int DAY = 0;
    private static final int DUSK = 1;
    private static final int NIGHT = 2;
    private static final int DAWN = 3;
    private int dayState = DAY;

    public Lighting(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setupLightSource();
        update();
    }

    private void setupLightSource() {
        darknessFilter = new BufferedImage(gamePanel.screenWidth, gamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = darknessFilter.createGraphics();
        graphics2D.setColor(new Color(0, 0, 51, 250));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/pokemon-dp-pro.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        graphics2D.dispose();
    }

    public void update() {
        switch (dayState) {
            case DAY -> {
                dayCounter++;
                if (dayCounter > 600) { //Keep at 600 frames (10 seconds) for now for the sake of testing
                    dayState = DUSK;
                    dayCounter = 0;
                }
            }
            case DUSK -> {
                filterAlpha += 0.001f;
                if (filterAlpha > 0.4f) {
                    filterAlpha = 0.4f;
                    dayState = NIGHT;
                }
            }
            case NIGHT -> {
                dayCounter++;
                if (dayCounter > 600) {
                    dayState = DAWN;
                    dayCounter = 0;
                }
            }
            case DAWN -> {
                filterAlpha -= 0.001f;
                if (filterAlpha < 0f) {
                    filterAlpha = 0f;
                    dayState = DAY;
                }
            }
        }
    }

    public float getFilterAlpha() {
        return filterAlpha;
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        graphics2D.drawImage(darknessFilter, 0, 0, null);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        String dayStateLabel = switch (dayState) {
            case DAY -> "Day";
            case DUSK -> "Dusk";
            case NIGHT -> "Night";
            case DAWN -> "Dawn";
            default -> "";
        };

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Pokémon DP Pro Regular", Font.PLAIN, 50));
        graphics2D.drawString(dayStateLabel, 20, 550);
    }
}