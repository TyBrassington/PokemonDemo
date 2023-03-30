package main;

import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SoundManager {

    private Clip clip;
    private long clipTime;
    public boolean playMusic = true;
    public float volume = -40.0f;

    public boolean isSoundEffect;
    public boolean isBumpSound;
    public List<Clip> musicClips = new ArrayList<>();

    public Clip setFile(int i) {
        Clip clip = null;
        try (InputStream is = getClass().getResourceAsStream("/AssetLoader/audioLoader.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            URL soundURL = br.lines()
                    .filter(line -> line.startsWith(i + " "))
                    .map(line -> {
                        String[] parts = line.split(" ");
                        boolean isSoundEffect = Integer.parseInt(parts[0]) != 0 && Integer.parseInt(parts[0]) != 5;
                        boolean isBumpSound = Integer.parseInt(parts[0]) == 4;
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
            if (!isSoundEffect) {
                musicClips.add(clip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }


    public void play(Clip clip) {
        clip.start();
        System.out.println(clip);
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

    public void loop(Clip clip) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        for (Clip clip : musicClips) {
            clip.loop(-1);
            clip.stop();
            clip.flush();
            clip.close();
        }
        musicClips.clear();
    }

    public void setSEVolume(float volume) {
        this.volume = !isBumpSound ? volume : volume - 10.0f;
    }

}
