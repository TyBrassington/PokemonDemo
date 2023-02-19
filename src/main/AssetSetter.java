package main;

import object.*;

import java.util.Scanner;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObjectFromFile() {

        Scanner scanner = new Scanner(getClass().getResourceAsStream("/AssetLoader/objectLoader.txt"));
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.trim().isEmpty()) {
                String[] parts = line.split(" ");
                String objName = parts[0];
                int worldX = Integer.parseInt(parts[1]) * gp.scale;
                int worldY = Integer.parseInt(parts[2]) * gp.scale;
                setObject(i, objName, worldX, worldY);
                i++;
            }
        }
        scanner.close();
        System.out.println("Objects successfully loaded.");
    }

    private void setObject(int index, String objName, int worldX, int worldY) {
        switch (objName) {
            case "House" -> gp.obj[index] = new OBJ_House(gp);
            case "Sign" -> gp.obj[index] = new OBJ_Sign(gp);
            case "LargeHouse" -> gp.obj[index] = new OBJ_LargeHouse(gp);
            case "Tree" -> gp.obj[index] = new OBJ_Tree(gp);
            case "Tree2" -> gp.obj[index] = new OBJ_Tree2(gp);
            case "Mailbox" -> gp.obj[index] = new OBJ_Mailbox(gp);
            default -> {
                System.out.println("Unknown object type: " + objName);
                gp.obj[index] = null;
            }
        }
        gp.obj[index].worldX = worldX;
        gp.obj[index].worldY = worldY;
    }
}
