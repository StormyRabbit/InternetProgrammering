import javax.sound.sampled.*;
import java.io.File;
import java.net.Socket;

public class AudioClient {
    private AudioFormat audioFormat =
            new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 8, 2, 4, 44100.0F, false);
    private TargetDataLine targetDataLine;
    private AudioInputStream audioInputStream;
    private Socket s;
    DataLine.Info info;


    public AudioClient() throws Exception {
        info = new DataLine.Info(TargetDataLine.class, audioFormat);
        try {
            targetDataLine = (TargetDataLine)AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            new Recorder((int)audioFormat.getSampleRate()
                    * audioFormat.getFrameSize(), targetDataLine);

        } catch(LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        }
        audioInputStream = new AudioInputStream(targetDataLine);

    }

    public void startAudio() {

    }

}
