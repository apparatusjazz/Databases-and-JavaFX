package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsePrepared {

    public static void executeQuery(Connection con, String stmt) {
        try {

            PreparedStatement line = con.prepareStatement(stmt);

            line.executeQuery();
        }

        catch (Exception e){System.out.println(e);}
    }

    public static void executeUpdate(Connection con, String stmt, String [] values) {
        try {

            PreparedStatement line = con.prepareStatement(stmt);
            for(int i = 0; i < values.length; i++) {
                line.setString(i + 1, values[i]);
            }


            line.executeUpdate();
        }

        catch (Exception e){System.out.println(e);}
    }
}
