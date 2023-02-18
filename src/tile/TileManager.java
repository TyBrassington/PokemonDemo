package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TileManager {

    private final GamePanel gp;
    public final Tile[] tiles;
    public final int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap();
        boolean playMusic = false;
        if (playMusic) {
            play("/audio/TwinLeafDay_EXT.wav");
        }
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Grass.png"));
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathBotLeft.png"));
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathBotMid.png"));
            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathBotRight.png"));
            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathMidLeft.png"));
            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathMidMid.png"));
            tiles[6] = new Tile();
            tiles[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathMidRight.png"));
            tiles[7] = new Tile();
            tiles[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathTopLeft.png"));
            tiles[8] = new Tile();
            tiles[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathTopMid.png"));
            tiles[9] = new Tile();
            tiles[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathTopRight.png"));
            tiles[10] = new Tile();
            tiles[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathTLC.png"));
            tiles[11] = new Tile();
            tiles[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathTRC.png"));
            tiles[12] = new Tile();
            tiles[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathBLC.png"));
            tiles[13] = new Tile();
            tiles[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pathBRC.png"));

            tiles[14] = new Tile();
            tiles[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/WaterBotLeft.png"));
            tiles[15] = new Tile();
            tiles[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/WaterBotMid.png"));
            tiles[16] = new Tile();
            tiles[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/WaterBotRight.png"));
            tiles[17] = new Tile();
            tiles[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/WaterMidLeft.png"));
            tiles[18] = new Tile();
            tiles[18].image = ImageIO.read(getClass().getResourceAsStream("/tiles/WaterMidMid.png"));
            tiles[19] = new Tile();
            tiles[19].image = ImageIO.read(getClass().getResourceAsStream("/tiles/WaterMidRight.png"));
            tiles[20] = new Tile();
            tiles[20].image = ImageIO.read(getClass().getResourceAsStream("/tiles/WaterTopLeft.png"));
            tiles[21] = new Tile();
            tiles[22].image = ImageIO.read(getClass().getResourceAsStream("/tiles/WaterTopMid.png"));
            tiles[23] = new Tile();
            tiles[24].image = ImageIO.read(getClass().getResourceAsStream("/tiles/WaterTopRight.png"));


            tiles[25] = new Tile();
            tiles[25].image = ImageIO.read(getClass().getResourceAsStream("/tiles/treePlaceholder.png"));
            tiles[25].collision = true;
            tiles[26] = new Tile();
            tiles[26].image = ImageIO.read(getClass().getResourceAsStream("/tiles/placeholder.png"));
            tiles[26].collision = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadMap() {
        try (InputStream is = getClass().getResourceAsStream("/maps/map02.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();
                String[] nums = line.split(" ");
                for (int col = 0; col < gp.maxWorldRow; col++) {
                    int num = Integer.parseInt(nums[col]);
                    mapTileNum[col][row] = num;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
            for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {

                int tileNum = mapTileNum[worldCol][worldRow];
                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                boolean isTileWithinXBounds = worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX;
                boolean isTileWithinYBounds = worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - 2 * gp.tileSize < gp.player.worldY + gp.player.screenY;

                if (isTileWithinXBounds && isTileWithinYBounds) {
                        g2d.drawImage(tiles[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

                }
            }
        }
    }
    public void play(String audioFilePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(audioFilePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-40.0f);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
