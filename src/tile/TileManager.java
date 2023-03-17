package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private final GamePanel gp;
    public final Tile[] tiles;
    public final int[][][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = getTileImage();
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        loadMap("/maps/twinleafTownEXT.txt", 0);
        loadMap("/maps/playerHouseDS.txt", 1);
        loadMap("/maps/playerHouseUS.txt", 2);
        loadMap("/maps/route_201.txt",3); //Placeholder for Route 201
    }

    public Tile[] getTileImage() {
        int numTiles = 0;
        try (InputStream tileListStream = getClass().getResourceAsStream("/AssetLoader/tileLoader.txt");
             BufferedReader tileListReader = new BufferedReader(new InputStreamReader(tileListStream))) {
            // Ignore first line
            tileListReader.readLine();
            while (true) {
                String line = tileListReader.readLine();
                if (line == null) {
                    break;
                }
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                numTiles++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tile[] tiles = new Tile[numTiles];
        try (InputStream tileListStream = getClass().getResourceAsStream("/AssetLoader/tileLoader.txt");
             BufferedReader tileListReader = new BufferedReader(new InputStreamReader(tileListStream))) {
            // Ignore first line
            tileListReader.readLine();
            int i = 0;
            while (true) {
                String line = tileListReader.readLine();
                if (line == null) {
                    break;
                }
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" ");
                int index = Integer.parseInt(parts[0]);
                String fileName = parts[1] + ".png";
                tiles[index] = new Tile();
                try (InputStream tileImageStream = getClass().getResourceAsStream("/tiles/" + fileName)) {
                    BufferedImage originalImage = ImageIO.read(tileImageStream);
                    int scaledWidth = gp.tileSize;
                    int scaledHeight = gp.tileSize;
                    Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_DEFAULT);
                    tiles[index].image = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = tiles[index].image.createGraphics();
                    g2d.drawImage(scaledImage, 0, 0, null);
                    g2d.dispose();

                    // If nothing is written in this column, assume its collision is false
                    if (parts.length > 2 && parts[2].equalsIgnoreCase("true")) {
                        tiles[index].collision = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Tiles successfully loaded.");
        return tiles;
    }



    public void loadMap(String filePath, int map) {
        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();
                if (line == null || line.trim().isEmpty()) {
                    // set num to 23 for all columns in empty row
                    for (int col = 0; col < gp.maxWorldCol; col++) {
                        mapTileNum[map][col][row] = 23;
                    }
                    continue;
                }
                String[] nums = line.split(" ");
                for (int col = 0; col < gp.maxWorldCol; col++) {
                    int num = col < nums.length ? Integer.parseInt(nums[col]) : 23;
                    mapTileNum[map][col][row] = num;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Map "+filePath+" successfully loaded.");
    }


    public void draw(Graphics2D g2d) {
        for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
            for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
                int tileNum = mapTileNum[gp.curMap][worldCol][worldRow];
                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                boolean isTileWithinXBounds = worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX;
                boolean isTileWithinYBounds = worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - 2 * gp.tileSize < gp.player.worldY + gp.player.screenY;

                if (isTileWithinXBounds && isTileWithinYBounds) {
                    g2d.drawImage(tiles[tileNum].image, screenX, screenY, null);

                }
            }
        }
    }
}
