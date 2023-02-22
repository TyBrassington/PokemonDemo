package main;

import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class SoundManager {

    private Clip clip;
    URL soundURL[] = new URL[30];
    public boolean playMusic = false;

    public void setFile(int i) {
        try {
            InputStream is = getClass().getResourceAsStream("/AssetLoader/audioLoader.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int index = Integer.parseInt(parts[0]);
                if (index == i) {
                    String fileName = parts[1] + ".wav";
                    URL soundURL = getClass().getResource("/audio/" + fileName);
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
                    clip = AudioSystem.getClip();
                    clip.open(ais);
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-40.0f);
                    break;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        if (clip != null) {
            clip.stop();
        }
    }
}