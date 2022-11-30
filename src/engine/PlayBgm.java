package engine;

import screen.GameScreen;
import screen.Screen;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class PlayBgm {
    static Clip clip_hollow;

    static File bgm;
    static AudioInputStream stream;
    static AudioFormat format;
    static DataLine.Info info;
    static Clip clip;

    static File bgm2;
    static AudioInputStream stream2;
    static AudioFormat format2;
    static DataLine.Info info2;
    static Clip clip2;

    static File bgm3;
    static AudioInputStream stream3;
    static AudioFormat format3;
    static DataLine.Info info3;
    static Clip clip3;

    static File bgm_item;

    static Clip clip_item;

    public static void play() {
        bgm = new File("Bgm\\Dummy-data-bgm.wav");
        try {

            stream = AudioSystem.getAudioInputStream(bgm);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();

        } catch (Exception e) {
            System.out.println("err : " + e);
        }

    }

    public static void playSound(String s) {
        bgm_item = new File(s);
        try {
            AudioInputStream stream_itemEffect = AudioSystem.getAudioInputStream(bgm_item);
            clip_item = AudioSystem.getClip();
            clip_item.open(stream_itemEffect);
            clip_item.start();

        } catch (Exception e) {
            System.out.println("err : " + e);
        }
    }

    public static void BgmStop(Clip clip) {
        clip.stop();
    }

}
