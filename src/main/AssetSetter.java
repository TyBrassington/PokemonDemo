package main;

import object.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObjectFromFile() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/AssetLoader/objectLoader.txt")))){
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.trim().startsWith("#")) {
                    String[] parts = line.split(" ");
                    String objName = parts[0];
                    int worldX = Integer.parseInt(parts[1]) * gp.scale;
                    int worldY = Integer.parseInt(parts[2]) * gp.scale;
                    setObject(i, objName, worldX, worldY);
                    i++;
                }
            }
        System.out.println("Objects successfully loaded.");
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void setObject(int index, String objName, int worldX, int worldY) {
        switch (objName) {
            case "House" -> gp.obj[0][index] = new OBJ_House(gp);
            case "Sign" -> gp.obj[0][index] = new OBJ_Sign(gp);
            case "LargeHouse" -> gp.obj[0][index] = new OBJ_LargeHouse(gp);
            case "Tree" -> gp.obj[0][index] = new OBJ_Tree(gp);
            case "Tree2" -> gp.obj[0][index] = new OBJ_Tree2(gp);
            case "Mailbox" -> gp.obj[0][index] = new OBJ_Mailbox(gp);
            case "DoorLH" -> gp.obj[0][index] = new OBJ_DoorLargeHouse(gp);
            case "DoorH" -> gp.obj[0][index] = new OBJ_DoorHouse(gp);

        }
        if (gp.obj[gp.curMap][index] != null) {
            gp.obj[gp.curMap][index].worldX = worldX;
            gp.obj[gp.curMap][index].worldY = worldY;
        } else{
            //Handle the case where gp.obj[gp.curMap][index] is null
            //System.out.println("Unknown object type: " + objName);
        }
    }
}
