package engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.URL;

public class Sound {

    public void backroundmusic() {
        try {

            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(FileManager.class.getClassLoader().getResourceAsStream("background.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bulletsound() {
        try {
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(FileManager.class.getClassLoader().getResourceAsStream("ball.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void explosionsound() {
        try {
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(FileManager.class.getClassLoader().getResourceAsStream("bomb.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
