/**
 * Main class for testing two different implementation of multithreading.
 * Coded for use in the course internet programming at DSV Stockholms University Autumn 2017.
 * @author Lasse
 */
public class Main {
    private T1 t1;
    private T2 t2;

    /**
     * Default constructor.
     * Starts the process of spawning and killing threads.
     */
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

    /**
     * EntryPoint method responsible for calling {@link Main#Main()}.
     * @param args not used.
     */
    public static void main(String[] args) {
        new Main();
    }
}
