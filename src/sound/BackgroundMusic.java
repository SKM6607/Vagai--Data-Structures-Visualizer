package sound;
import javax.sound.sampled.*;
import java.io.File;

public class BackgroundMusic implements Runnable {
    private final String filePath;
    public BackgroundMusic(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public void run() {
        try {
            File file = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            while (clip.isActive()) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
