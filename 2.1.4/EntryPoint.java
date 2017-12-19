public class EntryPoint {

    public EntryPoint() {
        try {
            new ImageClient();
        }catch(Exception e) {
            System.out.println(e);
        }

    }


    public static void main(String[] args) {
        new EntryPoint();
    }
}
