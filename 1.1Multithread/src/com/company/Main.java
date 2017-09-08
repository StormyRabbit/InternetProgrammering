package com.company;

public class Main {
    private T1 t1;
    private T2 t2;

    public Main() {
        createAndStartT1();
        fiveSecondSleep();
        createAndStartT2();
        fiveSecondSleep();
        killThreadOne();
        fiveSecondSleep();
        killThreadTwo();
    }

    private void killThreadOne() {
        printImportantMessage("Killing thread one");

        t1.stopThread();
    }

    private void killThreadTwo() {
        printImportantMessage("Killing thread two");
        t2.stopThread();
    }

    private void createAndStartT1() {
        printImportantMessage("Starting thread one");
        t1  = new T1();

    }

    private void printImportantMessage(String s) {
        System.out.println();
        System.out.println(s);
        System.out.println();
    }

    private void createAndStartT2() {
        printImportantMessage("Starting thread Two");
        t2 = new T2();
    }

    private void fiveSecondSleep() {
        try{
            Thread.sleep(5000);
        }catch(InterruptedException ie) {

        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
