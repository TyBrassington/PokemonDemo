package main;

import entity.NPC_FatBoy;
import object.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetSetter {

    GamePanel gp;
    public int mapNum;
    public int currentMapNum;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjectFromFile() {
        loadObjects("objectLoaderMap0.txt", 0);
        loadObjects("objectLoaderMap3.txt", 3);
    }

    private void loadObjects(String filePath, int mapNum) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/AssetLoader/" + filePath)))) {
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.trim().startsWith("#")) { //Skip any line starting with #
                    String[] parts = line.split(" ");
                    String objName = parts[0];
                    int worldX = Integer.parseInt(parts[1]) * gp.scale;
                    int worldY = Integer.parseInt(parts[2]) * gp.scale;
                    setObject(i, objName, worldX, worldY, mapNum);
                    i++;
                }
            }
            System.out.println("Map "+ mapNum + " objects successfully loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    private void setObject(int index, String objName, int worldX, int worldY, int mapNum) {
        switch (objName) {
            case "House" -> gp.obj[mapNum][index] = new OBJ_House(gp);
            case "Sign" -> gp.obj[mapNum][index] = new OBJ_Sign(gp);
            case "LargeHouse" -> gp.obj[mapNum][index] = new OBJ_LargeHouse(gp);
            case "Tree" -> gp.obj[mapNum][index] = new OBJ_Tree(gp);
            case "RouteSign" -> gp.obj[mapNum][index] = new OBJ_RouteSign(gp);
            case "Mailbox" -> gp.obj[mapNum][index] = new OBJ_Mailbox(gp);
            case "DoorLH" -> gp.obj[mapNum][index] = new OBJ_DoorLargeHouse(gp);
            case "DoorH" -> gp.obj[mapNum][index] = new OBJ_DoorHouse(gp);
            case "FenceEnclosure" -> gp.obj[mapNum][index] = new OBJ_FenceEnclosure(gp);

        }
        //System.out.println("Setting object: " + objName + " at (" + worldX/gp.scale + ", " + worldY/gp.scale + ") on map " + mapNum);
        if (gp.obj[mapNum][index] != null) {
            gp.obj[mapNum][index].worldX = worldX;
            gp.obj[mapNum][index].worldY = worldY;
            gp.obj[mapNum][index].mapNum = mapNum;
        } else {
            //Handle the case where gp.obj[mapNum][index] is null
            System.out.println("Unknown object type: " + objName);
        }
    }

    public void setNPC(){

        int mapNum = 0;
        gp.npc[mapNum][0] =  new NPC_FatBoy(gp);
        gp.npc[mapNum][0].worldX = 21 * gp.tileSize;
        gp.npc[mapNum][0].worldY = 33 * gp.tileSize;
    }
}
