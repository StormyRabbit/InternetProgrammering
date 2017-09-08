


public class T1 extends Thread {
    private boolean alive = true;

    public T1() {
        this.start();
    }

    public void stopThread() {
        this.alive = false;
    }

    @Override
    public void run() {
      while(alive) {
          System.out.println("Thread 1");
          try {
              Thread.sleep(1000);
          }catch(InterruptedException ie) {

          }

      }
    }
}
