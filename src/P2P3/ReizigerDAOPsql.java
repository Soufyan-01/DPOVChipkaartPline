package P2P3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReizigerDAOPsql implements ReizigerDAO  {

    private Connection conn;
    private AdresDao adao;
    private OVChipkaartDAO odao;

    public ReizigerDAOPsql(Connection connection) {
        this.conn = connection;
    }

    public void setAdao(AdresDao adao) {
        this.adao = adao;
    }

    public void setOdao(OVChipkaartDAO odao){this.odao = odao;}


    @Override
    public boolean save(Reiziger reiziger){
    try {
        PreparedStatement st = conn.prepareStatement("INSERT INTO reiziger(reiziger_id, voorletters,tussenvoegsel,achternaam,geboortedatum) VALUES (?,?,?,?,?)");

        st.setInt(1, reiziger.getID());
        st.setString(2, reiziger.getVoorletters());
        st.setString(3, reiziger.getTussenvoegsel());
        st.setString(4, reiziger.getAchternaam());
        st.setDate(5, reiziger.getGeboortedatum());

        st.executeUpdate();

    }catch (SQLException excp){
        System.out.println(excp.getMessage());
        excp.getSQLState();
    }
        return true;
    }

        @Override
    public boolean update(Reiziger reiziger) {
        try{
        PreparedStatement st = conn.prepareStatement("UPDATE reiziger SET reiziger_id=?, voorletters=? , tussenvoegsel= ?, achternaam= ?, geboortedatum=?  WHERE reiziger_id= ?");
        st.setInt(1, reiziger.getID());
        st.setString(2, reiziger.getVoorletters());
        st.setString(3, reiziger.getTussenvoegsel());
        st.setString(4, reiziger.getAchternaam());
        st.setDate(5, reiziger.getGeboortedatum());
        st.setInt(6, reiziger.getID());
        st.executeUpdate();

        }catch (SQLException excp){
            System.out.println(excp.getMessage());
            excp.getSQLState();
        }
        return true;
    }


    @Override
    public void delete(Reiziger reiziger) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id= ?");

        statement.setInt(1, reiziger.getID());

        statement.executeUpdate();

        statement.close();

        int rowsDeleted = statement.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Gebruiker is verwijderd");
        }
    }

    @Override
    public Reiziger findById(int id) {
        try{

            PreparedStatement st = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id=? ");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                int reiziger_id;
                String voorletters;
                String tussenvoegsel;
                String achternaam;
                Date geboortedatum;

                reiziger_id = rs.getInt("reiziger_id");
                voorletters = rs.getString("voorletters");
                tussenvoegsel = rs.getString("tussenvoegsel");
                achternaam = rs.getString("achternaam");
                geboortedatum = Date.valueOf(String.valueOf(rs.getDate("geboortedatum")));

                System.out.println(reiziger_id + " " +voorletters+". "+ tussenvoegsel+ " " + achternaam+" "+ geboortedatum);

                Reiziger reiziger = new Reiziger(reiziger_id, voorletters, tussenvoegsel,achternaam,geboortedatum);
                reiziger.setAdres(adao.findByReiziger(reiziger));
                return reiziger;
            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Het zoeken is niet gelukt");
            throw new RuntimeException("Tijd voorbij");
        }
        return null;
    }


    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try{
            // maken van een array waar alles ingestopt gaat worden
            List<Reiziger> bijGbDatum = new ArrayList<>();
            //sql query
            PreparedStatement st =  conn.prepareStatement("SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE geboortedatum=? ");
            st.setDate(1, Date.valueOf(datum));
            // uitvoeren van de sql query
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                int reiziger_id;
                String voorletters;
                String tussenvoegsel;
                String achternaam;
                Date geboortedatum;

                reiziger_id = rs.getInt(1);
                voorletters = rs.getString(2);
                tussenvoegsel = rs.getString(3);
                achternaam = rs.getString(4);
                geboortedatum = rs.getDate(5);

                System.out.println(reiziger_id + " " +voorletters+". "+ tussenvoegsel+ " " + achternaam+" "+ geboortedatum );

                Reiziger rezigerR= new Reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum);
                rezigerR.setAdres(adao.findByReiziger(rezigerR));
                rezigerR.setOvChipkaarts(odao.findByReiziger(rezigerR));
                bijGbDatum.add(rezigerR);

            }

            rs.close();
            st.close();

            return bijGbDatum;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Het zoeken is niet gelukt");
            throw new RuntimeException("Tijd voorbij");
        }
    }


    @Override
    public List<Reiziger> findAll() {
        try{
            List<Reiziger> zoekAlles = new ArrayList<>();
            Statement st = conn.createStatement();
            String query = "SELECT * FROM reiziger ";
            ResultSet rs = st.executeQuery(query);

            System.out.println("Er zijn de volgende reizigers gevonden: ");
            while(rs.next()){

                int reiziger_id;
                String voorletters;
                String tussenvoegsel;
                String achternaam;
                Date geboortedatum;

                reiziger_id = rs.getInt(1);
                voorletters = rs.getString(2);
                tussenvoegsel = rs.getString(3);
                achternaam = rs.getString(4);
                geboortedatum = rs.getDate(5);


                System.out.println(reiziger_id + " " + voorletters + ". " + tussenvoegsel + " "
                + achternaam + " " + geboortedatum);

                Reiziger reizigerR = new Reiziger(reiziger_id, voorletters,tussenvoegsel, achternaam, geboortedatum );
                reizigerR.setAdres(adao.findByReiziger(reizigerR));
                reizigerR.setOvChipkaarts(odao.findByReiziger(reizigerR));

                zoekAlles.add(reizigerR);
            }

            rs.close();
            st.close();

            return zoekAlles;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Het zoeken is niet gelukt");
            throw new RuntimeException("Tijd voorbij");
        }
    }
}
