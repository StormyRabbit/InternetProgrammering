import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLManager {

    private String url = "jdbc:mysql://mysql.dsv.su.se/lape5427";
    private String username = "lape5427";
    private String password = "wee0ohzooMeF";
    private Connection dbConnection;

    private String query = " insert into guestbook (name, email, homepage, message)"
            + " values (?, ?, ?, ?)";

    public MySQLManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(ClassNotFoundException cnfe) {

        }catch(IllegalAccessException iae) {

        }catch(InstantiationException ie) {

        }
    }

    public List<GuestBookEntry> getAllEntries() {
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
        try {
            dbConnection.close();
        }catch(SQLException se) {
            System.out.println(se);
        }
    }

    private void connectToDB() {
        try {
            dbConnection = DriverManager.getConnection(url, username, password);
        }catch(SQLException se) {
            System.out.println(se);
        }

    }
}
