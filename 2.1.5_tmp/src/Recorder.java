import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Target;

class Recorder extends Thread {
    private int bufferSize;
    private byte[] buffer;
    private ByteArrayOutputStream out;
    private boolean running;
    private TargetDataLine lineIn;
    // (int)format.getSampleRate() * format.getFrameSize()

    public Recorder(int bufferSize, TargetDataLine lineIn) {
        this.bufferSize = bufferSize;
        buffer = new byte[bufferSize];
        this.lineIn = lineIn;
        start();
    }

    public void run() {
        out = new ByteArrayOutputStream();
        running = true;
        try {
            while (running) {
                int count = lineIn.read(buffer, 0, buffer.length);
                if (count > 0) {
                    out.write(buffer, 0, count);
                }
            }
            out.close();
        } catch (IOException e) {
            System.err.println("I/O problems: " + e);
            System.exit(-1);
        }
    }
}