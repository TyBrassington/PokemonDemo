package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class TileManager {

    private final GamePanel gp;
    public final Tile[] tiles;
    public final int[][][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[200];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/twinleafTownEXT.txt", 0);
        loadMap("/maps/playerHouseDS.txt", 1);
        loadMap("/maps/route_201.txt",2); //Placeholder for Route 201
    }

    public void getTileImage() {
        Scanner scanner = new Scanner(getClass().getResourceAsStream("/AssetLoader/tileLoader.txt"));
        int i;
        String fileName;
        //Ignore first line
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(!line.trim().isEmpty()) {
                String[] parts = line.split(" ");
                i = Integer.parseInt(parts[0]);
                fileName = parts[1] + ".png";
                tiles[i] = new Tile();
                try {
                    BufferedImage originalImage = ImageIO.read(getClass().getResourceAsStream("/tiles/" + fileName));
                    int scaledWidth = gp.tileSize;
                    int scaledHeight = gp.tileSize;
                    Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_DEFAULT);
                    tiles[i].image = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = tiles[i].image.createGraphics();
                    g2d.drawImage(scaledImage, 0, 0, null);
                    g2d.dispose();

                    //If nothing is written in this column, assume it collision is false
                    if (parts.length > 2 && parts[2].equalsIgnoreCase("true")) {
                        tiles[i].collision = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        scanner.close();
        System.out.println("Tiles successfully loaded.");
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
