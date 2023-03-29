package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed, spacePressed, keyPressed;
    boolean checkDrawTime;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        keyPressed = true;
        if (code == KeyEvent.VK_T && e.isShiftDown()) {
            checkDrawTime = !checkDrawTime;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = (gp.gameState + 1) % 3;
            System.out.println(gp.gameState);
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
            if (gp.subdialogueDone) {
                gp.ui.curCharIndex = 0;
                if (gp.gameState == gp.dialogueState) {
                    gp.gameState = gp.playState;
                }
            }
        }
        if (code == KeyEvent.VK_F1) {
            gp.toggleDebug = !gp.toggleDebug;
        }
        if (code == KeyEvent.VK_SHIFT && !shiftPressed) {
            gp.player.isRunning = !gp.player.isRunning;
            shiftPressed = true;
            if (gp.player.isRunning) {
                gp.player.speed = 2 * gp.scale;
            } else gp.player.speed = 1 * gp.scale;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        keyPressed = false;
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
