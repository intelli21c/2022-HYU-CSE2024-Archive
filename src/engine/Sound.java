package engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
                    .getAudioInputStream(FileManager.class.getClassLoader().getResourceAsStream("explosion.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void bombSound() {
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
    public void destorySound() {
        try {
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(FileManager.class.getClassLoader().getResourceAsStream("Destory.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
