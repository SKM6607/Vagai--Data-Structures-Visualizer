package sound;

import annotations.Music;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

@Music(
        name = "Granius",
        path = "src/resources/Granius.wav",
        isBackground = true
)
public final class BackgroundMusic implements Runnable {
    static private final String filePath;
    static private final Class<? extends BackgroundMusic> c = BackgroundMusic.class;
    static private final Music m = c.getAnnotation(Music.class);
    static private BackgroundMusic singleton = null;

    static {
        filePath = m.path();
    }

    private BackgroundMusic() {}

    public static @NotNull BackgroundMusic getInstance() {
        return (singleton == null) ? singleton = new BackgroundMusic() : singleton;
    }

    @Override
    public void run() {
        try {
            File file = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            if (m.isBackground()) clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            while (clip.isActive()) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
