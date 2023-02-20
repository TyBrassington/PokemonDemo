package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args){


        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        System.out.println("Pokemon by Ty Brassington, version 0.6.8\n");
        window.setTitle("Pokemon Demo Panel");
        Image icon = Toolkit.getDefaultToolkit().getImage("pokeball_icon.png");
        window.setIconImage(icon);
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        //Load objects before setting window to visible
        gamePanel.setupGame();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
