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
    private static final int MAX_DAY_COUNTER = 600; //10 seconds for the sake of testing

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
                if (++dayCounter > MAX_DAY_COUNTER) {
                    dayState = DUSK;
                    dayCounter = 0;
                }
            }
            case DUSK -> {
                filterAlpha = Math.min(filterAlpha + 0.001f, 0.4f);
                dayState = (filterAlpha == 0.4f) ? NIGHT : DUSK;
            }
            case NIGHT -> {
                if (++dayCounter > MAX_DAY_COUNTER) {
                    dayState = DAWN;
                    dayCounter = 0;
                }
            }
            case DAWN -> {
                filterAlpha = Math.max(filterAlpha - 0.001f, 0f);
                dayState = (filterAlpha == 0f) ? DAY : DAWN;
            }
        }
    }

    public float getFilterAlpha() {
        return filterAlpha;
    }

    public void draw(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2d.drawImage(darknessFilter, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        String dayStateLabel = switch (dayState) {
            case DAY -> "Day";
            case DUSK -> "Dusk";
            case NIGHT -> "Night";
            case DAWN -> "Dawn";
            default -> "";
        };

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Pokémon DP Pro Regular", Font.PLAIN, 50));
        g2d.drawString(dayStateLabel, 20, 550);
    }
}