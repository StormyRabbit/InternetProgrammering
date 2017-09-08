


public class T1 extends Thread {
    private boolean alive = true;

    /**
     * Standard constructor, starts the thread
     * by calling the {@link Thread} superclass
     * {@link Thread#start()} method.
     */
    public T1() {
        this.start();
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
     * Overrides run of {@link Thread}
     * Outputs "Thread 2" every one (1) second while
     * {@link T1#alive} is true.
     * Uses {@link Thread#sleep(long)} for timer.
     */
    @Override
    public void run() {
      while(alive) {
          System.out.println("Thread 1");
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
