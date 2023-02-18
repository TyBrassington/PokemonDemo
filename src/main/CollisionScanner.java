
package main;

import entity.Entity;

public class CollisionScanner {

    private final GamePanel gp;

    public CollisionScanner(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldx = entity.worldX + entity.hitBoxArea.x;
        int entityRightWorldx = entity.worldX + entity.hitBoxArea.x + entity.hitBoxArea.width;
        int entityTopWorldy = entity.worldY + entity.hitBoxArea.y;
        int entityBottomWorldy = entity.worldY + entity.hitBoxArea.y + entity.hitBoxArea.height;

        int entityLeftWorldx1 = entity.worldX + entity.hitBoxArea1.x;
        int entityRightWorldx1 = entity.worldX + entity.hitBoxArea1.x + entity.hitBoxArea1.width;
        int entityTopWorldy1 = entity.worldY + entity.hitBoxArea1.y;
        int entityBottomWorldy1 = entity.worldY + entity.hitBoxArea1.y + entity.hitBoxArea1.height;

        int entityLeftCol = entityLeftWorldx/gp.tileSize;
        int entityRightCol = entityRightWorldx/gp.tileSize;
        int entityTopRow = entityTopWorldy/gp.tileSize;
        int entityBottomRow = entityBottomWorldy/gp.tileSize;

        int entityLeftCol1 = entityLeftWorldx1/gp.tileSize;
        int entityRightCol1 = entityRightWorldx1/gp.tileSize;
        int entityTopRow1 = entityTopWorldy1/gp.tileSize;
        int entityBottomRow1 = entityBottomWorldy1/gp.tileSize;

        int[] cols = { entityLeftCol, entityRightCol, entityLeftCol1, entityRightCol1 };
        int[] rows = { entityTopRow, entityBottomRow, entityTopRow1, entityBottomRow1 };
        switch (entity.direction) {
            case "up":
                rows[0] = (entityTopWorldy - entity.speed) / gp.tileSize;
                rows[2] = (entityTopWorldy1 - entity.speed) / gp.tileSize;
                break;
            case "down":
                rows[1] = (entityBottomWorldy + entity.speed) / gp.tileSize;
                rows[3] = (entityBottomWorldy1 + entity.speed) / gp.tileSize;
                break;
            case "left":
                cols[0] = (entityLeftWorldx - entity.speed) / gp.tileSize;
                cols[2] = (entityLeftWorldx1 - entity.speed) / gp.tileSize;
                break;
            case "right":
                cols[1] = (entityRightWorldx + entity.speed) / gp.tileSize;
                cols[3] = (entityRightWorldx1 + entity.speed) / gp.tileSize;
                break;
        }

        for (int row : rows) {
            for (int col : cols) {
                int tileNum = gp.tileManager.mapTileNum[col][row];
                if (gp.tileManager.tiles[tileNum].collision) {
                    entity.collisionOn = true;
                    return;
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player){

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] != null){
                entity.hitBoxArea.x = entity.worldX + entity.hitBoxArea.x;
                entity.hitBoxArea.y = entity.worldY + entity.hitBoxArea.y;

                gp.obj[i].hitBoxArea.x = gp.obj[i].worldX + gp.obj[i].hitBoxArea.x;
                gp.obj[i].hitBoxArea.y = gp.obj[i].worldY + gp.obj[i].hitBoxArea.y;

                switch (entity.direction){
                    case "up":
                        entity.hitBoxArea.y -= entity.speed;
                        if (entity.hitBoxArea.intersects(gp.obj[i].hitBoxArea)){
                            if(gp.obj[i].collision){
                                entity.collisionOn = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.hitBoxArea.y += entity.speed;
                        if (entity.hitBoxArea.intersects(gp.obj[i].hitBoxArea)){
                            if(gp.obj[i].collision){
                                entity.collisionOn = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.hitBoxArea.x -= entity.speed;
                        if (entity.hitBoxArea.intersects(gp.obj[i].hitBoxArea)){
                            if(gp.obj[i].collision){
                                entity.collisionOn = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.hitBoxArea.x += entity.speed;
                        if (entity.hitBoxArea.intersects(gp.obj[i].hitBoxArea)){
                            if(gp.obj[i].collision){
                                entity.collisionOn = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                }
                entity.hitBoxArea.x = entity.hitBoxAreaDefaultX;
                entity.hitBoxArea.y = entity.hitBoxAreaDefaultY;
                gp.obj[i].hitBoxArea.x = gp.obj[i].hitBoxAreaDefaultX;
                gp.obj[i].hitBoxArea.y = gp.obj[i].hitBoxAreaDefaultY;
            }
        }
        return index;
    }
}

