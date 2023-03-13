package main;

import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SoundManager {

    private Clip clip;
    private long clipTime;
    public boolean playMusic = true;
    public float volume = -40.0f;

    public boolean isSoundEffect;
    public boolean isBumpSound;

    public void setFile(int i) {
        try (InputStream is = getClass().getResourceAsStream("/AssetLoader/audioLoader.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            URL soundURL = br.lines()
                    .filter(line -> line.startsWith(i + " "))
                    .map(line -> {
                        String[] parts = line.split(" ");
                        isSoundEffect = Integer.parseInt(parts[0]) != 0;
                        isBumpSound = Integer.parseInt(parts[0]) == 4;
                        String fileName = parts[1] + ".wav";
                        return getClass().getResource("/audio/" + fileName);
                    })
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Sound file not found for index: " + i));
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void pause() {
        if (clip != null) {
            clipTime = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    public void resume() {
        if (clip != null) {
            clip.setMicrosecondPosition(clipTime);
            clip.start();
        }
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void setSEVolume(float seVolume) {
        if (isSoundEffect) {
            volume = seVolume;
            if (clip != null) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(isBumpSound ? seVolume - 10.0f : seVolume); //if the sound is the bump sound, set the volume 10.0 decibels lower than seVolume
            }
        }
    }

}
