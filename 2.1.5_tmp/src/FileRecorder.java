import java.io.*;
import javax.sound.sampled.*;

public class FileRecorder {

    private AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F, false);
    private AudioFileFormat.Type targetType = AudioFileFormat.Type.AU;
    private TargetDataLine targetDataLine;
    private AudioInputStream audioInputStream;
    private File file;
    private boolean recording;

    public static void main(String[] args) {
        new FileRecorder(args[0]);
    }

    public FileRecorder(String fileName) {
        file = new File(fileName);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
        try {
            targetDataLine = (TargetDataLine)AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);
        } catch(LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        }
        audioInputStream = new AudioInputStream(targetDataLine);
        record();
    }

    public void record() {
        System.out.println("Press ENTER to start the recording!");
        try { System.in.read(); } catch(IOException e) {}
        targetDataLine.start();
        Recorder recorder = new Recorder();
        recorder.start();
        System.out.println("Recording ...");

        System.out.println("Press ENTER to stop the recording.");
        try { System.in.read(); System.in.read(); } catch(IOException e) {}
        targetDataLine.stop();
        targetDataLine.close();
        System.out.println("Recording stopped.");
        System.exit(0);
    }

    public class Recorder extends Thread {
        public void run() {
            try {
                AudioSystem.write(audioInputStream, targetType, file);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
