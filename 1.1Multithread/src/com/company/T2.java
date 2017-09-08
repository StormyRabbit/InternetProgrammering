package com.company;


public class T2 implements Runnable {
    private Thread thread;
    private volatile boolean alive = true;

    public T2() {
        thread = new Thread(this);
        thread.start();
    }

    public void stopThread() {
        this.alive = false;
    }

    public boolean isAlive() {
        return thread.isAlive();
    }

    @Override
    public void run() {
        while(alive) {
            System.out.println("Thread 2");
            try {
                Thread.sleep(1000);
            }catch(InterruptedException ie) {
            }
        }
    }

}
