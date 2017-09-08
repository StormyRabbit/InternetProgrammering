/**
 * Class that implements Runnable used
 * practicing different ways of implementing
 * threading.
 * @author Lasse Sjöblom
 */
public class T2 implements Runnable {
    private Thread thread;
    private volatile boolean alive = true;

    /**
     * Standard constructor, creates and starts
     * the inner {@link Thread} object.
     */
    public T2() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     *  Sets the internal alive boolean to false so
     *  that allows the thread to get collected by
     *  the garbage collector.
     */
    public void stopThread() {
        this.alive = false;
    }

    /**
     * Return the internal {@link Thread} objects
     * isAlive() response.
     * @return boolean {@link Thread} isAlive() response
     */
    public boolean isAlive() {
        return thread.isAlive();
    }

    /**
     * implementation of {@link Runnable}
     * Outputs "Thread 2" every one (1) second while
     * {@link T2#alive} is true.
     * Uses {@link Thread#sleep(long)} for timer.
     */
    @Override
    public void run() {
        while(alive) {
            System.out.println("Thread 2");
            sleepForOneSecond();
        }
    }

    private void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
        }catch(InterruptedException ie) {

        }
    }
}
