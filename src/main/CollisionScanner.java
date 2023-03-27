
package main;

import entity.Entity;

import java.awt.*;

public class CollisionScanner {

    private final GamePanel gp;

    public CollisionScanner(GamePanel gp) {
        this.gp = gp;
    }

    boolean canCollide = true;
    int prevCollisionX, prevCollisionY;

    public void checkTile(Entity entity) {
        int entityLeftWorldx = entity.worldX + entity.hitBoxArea.x;
        int entityRightWorldx = entity.worldX + entity.hitBoxArea.x + entity.hitBoxArea.width;
        int entityTopWorldy = entity.worldY + entity.hitBoxArea.y;
        int entityBottomWorldy = entity.worldY + entity.hitBoxArea.y + entity.hitBoxArea.height;

        int entityLeftWorldx1 = entity.worldX + entity.hitBoxArea1.x;
        int entityRightWorldx1 = entity.worldX + entity.hitBoxArea1.x + entity.hitBoxArea1.width;
        int entityTopWorldy1 = entity.worldY + entity.hitBoxArea1.y;
        int entityBottomWorldy1 = entity.worldY + entity.hitBoxArea1.y + entity.hitBoxArea1.height;

        int entityLeftCol = entityLeftWorldx / gp.tileSize;
        int entityRightCol = entityRightWorldx / gp.tileSize;
        int entityTopRow = entityTopWorldy / gp.tileSize;
        int entityBottomRow = entityBottomWorldy / gp.tileSize;

        int entityLeftCol1 = entityLeftWorldx1 / gp.tileSize;
        int entityRightCol1 = entityRightWorldx1 / gp.tileSize;
        int entityTopRow1 = entityTopWorldy1 / gp.tileSize;
        int entityBottomRow1 = entityBottomWorldy1 / gp.tileSize;

        int[] cols = {entityLeftCol, entityRightCol, entityLeftCol1, entityRightCol1};
        int[] rows = {entityTopRow, entityBottomRow, entityTopRow1, entityBottomRow1};
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
                if (col < 0 || col >= gp.tileManager.mapTileNum[gp.curMap].length || row < 0 || row >= gp.tileManager.mapTileNum[gp.curMap][col].length ||
                        gp.tileManager.tiles[gp.tileManager.mapTileNum[gp.curMap][col][row]].collision) {
                    entity.collisionOn = true;
                    prevCollisionX = gp.player.worldX;
                    prevCollisionY = gp.player.worldY;
                    checkInCollision();
                    canCollide = false;
                    return;
                }
                int xDistance = Math.abs(gp.player.worldX - prevCollisionX);
                int yDistance = Math.abs(gp.player.worldY - prevCollisionY);
                int distance = Math.max(xDistance, yDistance);
                if (distance > gp.player.speed) {
                    canCollide = true;
                }
            }
        }
    }

    int bumpCounter;

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.curMap][i] != null) {
                entity.hitBoxArea.x = entity.worldX + entity.hitBoxArea.x;
                entity.hitBoxArea.y = entity.worldY + entity.hitBoxArea.y;

                gp.obj[gp.curMap][i].hitBoxArea.x = gp.obj[gp.curMap][i].worldX + gp.obj[gp.curMap][i].hitBoxArea.x;
                gp.obj[gp.curMap][i].hitBoxArea.y = gp.obj[gp.curMap][i].worldY + gp.obj[gp.curMap][i].hitBoxArea.y;

                gp.obj[gp.curMap][i].hitBoxArea1.x = gp.obj[gp.curMap][i].worldX + gp.obj[gp.curMap][i].hitBoxArea1.x;
                gp.obj[gp.curMap][i].hitBoxArea1.y = gp.obj[gp.curMap][i].worldY + gp.obj[gp.curMap][i].hitBoxArea1.y;

                gp.obj[gp.curMap][i].hitBoxArea2.x = gp.obj[gp.curMap][i].worldX + gp.obj[gp.curMap][i].hitBoxArea2.x;
                gp.obj[gp.curMap][i].hitBoxArea2.y = gp.obj[gp.curMap][i].worldY + gp.obj[gp.curMap][i].hitBoxArea2.y;

                switch (entity.direction) {
                    case "up":
                        entity.hitBoxArea.y -= entity.speed;
                        if (entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea) || entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea1) || entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea2)) {
                            if (gp.obj[gp.curMap][i].collision) {
                                entity.collisionOn = true;
                                prevCollisionX = gp.player.worldX;
                                prevCollisionY = gp.player.worldY;
                                checkInCollision();
                                canCollide = false;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.hitBoxArea.y += entity.speed;
                        if (entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea) || entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea1) || entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea2)) {
                            if (gp.obj[gp.curMap][i].collision) {
                                entity.collisionOn = true;
                                prevCollisionX = gp.player.worldX;
                                prevCollisionY = gp.player.worldY;
                                checkInCollision();
                                canCollide = false;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.hitBoxArea.x -= entity.speed;
                        if (entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea) || entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea1) || entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea2)) {
                            if (gp.obj[gp.curMap][i].collision) {
                                entity.collisionOn = true;
                                prevCollisionX = gp.player.worldX;
                                prevCollisionY = gp.player.worldY;
                                checkInCollision();
                                canCollide = false;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.hitBoxArea.x += entity.speed;
                        if (entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea) || entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea1) || entity.hitBoxArea.intersects(gp.obj[gp.curMap][i].hitBoxArea2)) {
                            if (gp.obj[gp.curMap][i].collision) {
                                entity.collisionOn = true;
                                prevCollisionX = gp.player.worldX;
                                prevCollisionY = gp.player.worldY;
                                checkInCollision();
                                canCollide = false;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }


                int xDistance = Math.abs(gp.player.worldX - prevCollisionX);
                int yDistance = Math.abs(gp.player.worldY - prevCollisionY);
                int distance = Math.max(xDistance, yDistance);
                if (distance > gp.player.speed) {
                    canCollide = true;
                }

                entity.hitBoxArea.x = entity.hitBoxAreaDefaultX;
                entity.hitBoxArea.y = entity.hitBoxAreaDefaultY;
                gp.obj[gp.curMap][i].hitBoxArea.x = gp.obj[gp.curMap][i].hitBoxAreaDefaultX;
                gp.obj[gp.curMap][i].hitBoxArea.y = gp.obj[gp.curMap][i].hitBoxAreaDefaultY;

                gp.obj[gp.curMap][i].hitBoxArea1.x = gp.obj[gp.curMap][i].hitBoxAreaDefaultX1;
                gp.obj[gp.curMap][i].hitBoxArea1.y = gp.obj[gp.curMap][i].hitBoxAreaDefaultY1;

                gp.obj[gp.curMap][i].hitBoxArea2.x = gp.obj[gp.curMap][i].hitBoxAreaDefaultX2;
                gp.obj[gp.curMap][i].hitBoxArea2.y = gp.obj[gp.curMap][i].hitBoxAreaDefaultY2;
            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;
        for (int i = 0; i < target[1].length; i++) {
            Entity targetEntity = target[gp.curMap][i];
            if (targetEntity == null) {
                continue;
            }
            Rectangle entityHitBox = new Rectangle(entity.worldX + entity.hitBoxArea.x, entity.worldY + entity.hitBoxArea.y, entity.hitBoxArea.width, entity.hitBoxArea.height);
            Rectangle targetHitBox1 = new Rectangle(targetEntity.worldX + targetEntity.hitBoxArea.x, targetEntity.worldY + targetEntity.hitBoxArea.y, targetEntity.hitBoxArea.width, targetEntity.hitBoxArea.height);
            Rectangle targetHitBox2 = new Rectangle(targetEntity.worldX + targetEntity.hitBoxArea1.x, targetEntity.worldY + targetEntity.hitBoxArea1.y, targetEntity.hitBoxArea1.width, targetEntity.hitBoxArea1.height);
            switch (entity.direction) {
                case "up":
                    entityHitBox.y -= entity.speed;
                    if (entityHitBox.intersects(targetHitBox1) || entityHitBox.intersects(targetHitBox2)) {
                        handleCollision(entity);
                        index = i;
                    }
                    break;
                case "down":
                    entityHitBox.y += entity.speed;
                    if (entityHitBox.intersects(targetHitBox1) || entityHitBox.intersects(targetHitBox2)) {
                        handleCollision(entity);
                        index = i;
                    }
                    break;
                case "left":
                    entityHitBox.x -= entity.speed;
                    if (entityHitBox.intersects(targetHitBox1) || entityHitBox.intersects(targetHitBox2)) {
                        handleCollision(entity);
                        index = i;
                    }
                    break;
                case "right":
                    entityHitBox.x += entity.speed;
                    if (entityHitBox.intersects(targetHitBox1) || entityHitBox.intersects(targetHitBox2)) {
                        handleCollision(entity);
                        index = i;
                    }
                    break;
            }
            resetHitBoxes(entity, targetEntity);
        }
        return index;
    }

    private void handleCollision(Entity entity) {
        entity.collisionOn = true;
        prevCollisionX = gp.player.worldX;
        prevCollisionY = gp.player.worldY;
        checkInCollision();
        canCollide = false;
        int xDistance = Math.abs(gp.player.worldX - prevCollisionX);
        int yDistance = Math.abs(gp.player.worldY - prevCollisionY);
        if (Math.max(xDistance, yDistance) >= gp.player.speed) {
            canCollide = true;
        }
    }

    private void resetHitBoxes(Entity entity, Entity targetEntity) {
        entity.hitBoxArea.x = entity.hitBoxAreaDefaultX;
        entity.hitBoxArea.y = entity.hitBoxAreaDefaultY;
        targetEntity.hitBoxArea.x = targetEntity.hitBoxAreaDefaultX;
        targetEntity.hitBoxArea.y = targetEntity.hitBoxAreaDefaultY;
    }


    public void checkPlayer(Entity entity){
        Entity targetEntity = gp.player;
        Rectangle entityHitBox = new Rectangle(entity.worldX + entity.hitBoxArea.x, entity.worldY + entity.hitBoxArea.y, entity.hitBoxArea.width, entity.hitBoxArea.height);
        Rectangle playerHitBox1 = new Rectangle(targetEntity.worldX + targetEntity.hitBoxArea.x, targetEntity.worldY + targetEntity.hitBoxArea.y, targetEntity.hitBoxArea.width, targetEntity.hitBoxArea.height);
        Rectangle playerHitBox2 = new Rectangle(targetEntity.worldX + targetEntity.hitBoxArea1.x, targetEntity.worldY + targetEntity.hitBoxArea1.y, targetEntity.hitBoxArea1.width, targetEntity.hitBoxArea1.height);
        switch (entity.direction) {
            case "up":
                entityHitBox.y -= entity.speed;
                if (entityHitBox.intersects(playerHitBox1) || entityHitBox.intersects(playerHitBox2)) {
                    handleCollision(entity);
                }
                break;
            case "down":
                entityHitBox.y += entity.speed;
                if (entityHitBox.intersects(playerHitBox1) || entityHitBox.intersects(playerHitBox2)) {
                    handleCollision(entity);
                }
                break;
            case "left":
                entityHitBox.x -= entity.speed;
                if (entityHitBox.intersects(playerHitBox1) || entityHitBox.intersects(playerHitBox2)) {
                    handleCollision(entity);
                }
                break;
            case "right":
                entityHitBox.x += entity.speed;
                if (entityHitBox.intersects(playerHitBox1) || entityHitBox.intersects(playerHitBox2)) {
                    handleCollision(entity);
                }
                break;
        }
        resetHitBoxes(entity, targetEntity);
    }

    public void checkInCollision() {
        if (canCollide) {
            gp.playSoundEffect(4);
        }
    }
}

