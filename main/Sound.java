package main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * How we will create sound for the game.
 */
public class Sound {
    Clip clip;
    URL[] soundUrl;

    public Sound() {
        File[] files = new File("res/sound").listFiles();
        soundUrl = new URL[files.length];

        for (int i = 0; i < soundUrl.length; i++) {
            try {
                soundUrl[i] = files[i].toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setFile(String fileName) {
        int index = -1;
        for (int i = 0; i < soundUrl.length; i++) {
            if (soundUrl[i].getPath().contains(fileName)) {
                index = i;
            }
        }
        if (index > -1) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[index]);
                clip = AudioSystem.getClip();
                clip.open(ais);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("file not found for Sound.setFile");
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void setVolume(int i) {

    }
}
