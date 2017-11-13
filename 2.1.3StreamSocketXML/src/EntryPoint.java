public class EntryPoint {

    public EntryPoint(String[] args) {
        try {
            new ChatClient(args);
        }catch(Exception exc) {
            exc.printStackTrace();
            System.out.println(exc);
        }
    }

    public static void main(String[] args) {
        new EntryPoint(args);
    }
}
