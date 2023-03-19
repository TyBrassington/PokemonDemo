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
        System.out.println("\nPokémon by Ty Brassington, version 0.9.14\n");
        window.setTitle("Pokémon Demo Panel");
        Image icon = Toolkit.getDefaultToolkit().getImage("pokeball_icon.png");
        window.setIconImage(icon);
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        //Load objects before setting window to visible
        gp.setupGame();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.startGameThread();
    }
}
