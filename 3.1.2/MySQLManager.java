import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLManager {

    private String url = "...";
    private String username = "...";
    private String password = "...";
    private Connection dbConnection;

    private String query = " insert into guestbook (name, email, homepage, message)"
            + " values (?, ?, ?, ?)";

    public MySQLManager() {
        /**
         * main constructor, loads db drivers
         */
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(ClassNotFoundException cnfe) {

        }catch(IllegalAccessException iae) {

        }catch(InstantiationException ie) {

        }
    }

    public List<GuestBookEntry> getAllEntries() {
        // gathers all stored entries in a arrayList and returns it.
        connectToDB();
        List retArr = new ArrayList();
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM guestbook");
            while (rs.next())
                retArr.add(convertResultToGBE(rs));

        }catch(SQLException se) {
            System.out.println(se);
        }
        closeDBConnection();
        return retArr;
    }

    private GuestBookEntry convertResultToGBE(ResultSet rs) {
        // takes a ResultSet and converts it to a GuestBookEntry object.
        GuestBookEntry gbe = new GuestBookEntry();
        try {
            gbe.setName(rs.getString("name"));
            gbe.setEmail(rs.getString("email"));
            gbe.setHomepage(rs.getString("homepage"));
            gbe.setMessage(rs.getString("message"));
        }catch(SQLException se) {
        }
        return gbe;
    }

    public void postNewEntry(GuestBookEntry gbe) {
        // Creates a preparedStatement object and binds the parameter values to it and then posts it to the DB
        connectToDB();
        try {
            PreparedStatement pstm = dbConnection.prepareStatement(query);
            pstm.setString(1, gbe.getName());
            pstm.setString(2, gbe.getEmail());
            pstm.setString(3, gbe.getHomepage());
            pstm.setString(4, gbe.getMessage());
            pstm.execute();
        }catch(SQLException se) {
            System.out.println(se);
        }
        closeDBConnection();
    }

    private void closeDBConnection() {
        // closes a db connection.
        try {
            dbConnection.close();
        }catch(SQLException se) {
            System.out.println(se);
        }
    }

    private void connectToDB() {
        // connects to the DB 
        try {
            dbConnection = DriverManager.getConnection(url, username, password);
        }catch(SQLException se) {
            System.out.println(se);
        }

    }
}
