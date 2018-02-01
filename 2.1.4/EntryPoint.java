public class EntryPoint {

    /**
     * entry point class, no responsibilities except creating the imageClient object. 
     * takes no parameters.
     */
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
