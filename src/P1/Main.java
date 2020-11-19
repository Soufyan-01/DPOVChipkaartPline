package P1;

import java.sql.*;


public class Main {

    public static void main(String[] args) {

        try {
            String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Soufyan01";
            Connection conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();


            String query = "SELECT voorletters,tussenvoegsel, achternaam, geboortedatum FROM reiziger ";

            ResultSet rs = st.executeQuery(query);

            String voorletters ;
            String tussenvoegsel;
            String achternaam ;
            Date geboortedatum ;

            System.out.println("Alle Reizigers:");


            while (rs.next()) {
                voorletters = rs.getString("voorletters");
                tussenvoegsel = rs.getString("tussenvoegsel");
                achternaam = rs.getString("achternaam");
                geboortedatum = Date.valueOf(rs.getString("geboortedatum"));

                System.out.println( " " + voorletters +". " +tussenvoegsel+" "+ achternaam + " "+"(" + geboortedatum + ")");


            }
            rs.close();
            st.close();
            conn.close();

        } catch (Exception excp) {
            System.err.println("Er is wat misgegaan ");

        }




    }


}
