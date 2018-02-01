import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class ImageSender  {
    private Socket s;
    private Queue<Storage> fileQueue;

    public ImageSender(Socket s) {
        /**
         * main constructor takes a socket as parameter used for transmitting files.
         */
        fileQueue = new LinkedList<>();
        this.s = s;

    }

    public void addFileToQueue(String filePath) throws Exception {
        // used for adding files to the transmission queue.
        Storage prepedFile = prepareFileForTransmission(filePath);
        fileQueue.add(prepedFile);
    }

    private Storage prepareFileForTransmission(String filePath) throws Exception {
        // builds the Storage object used for transmission.
        System.out.printf("Preparing file %s for storage...\n", filePath);
        Path path = Paths.get(filePath);
        byte[] fileBytes = Files.readAllBytes(path);
        return new Storage(fileBytes, path.getFileName().toString());
    }

    public void manageOutQueue() {
        // mainloop thats used for sending queued files.
        while(!fileQueue.isEmpty()) {
            try {
                Storage outStorage = fileQueue.poll();
                System.out.printf("Sending file %s ...\n", outStorage.getId());
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(outStorage);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
