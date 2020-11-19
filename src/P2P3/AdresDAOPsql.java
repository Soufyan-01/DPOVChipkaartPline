package P2P3;

import java.awt.color.ProfileDataException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDao{

    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection connection) {
        this.conn = connection;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }


    @Override
    public Adres findById(int ID) {
        try{
            PreparedStatement st = conn.prepareStatement("SELECT * FROM adres WHERE adres_id=? ");

            st.setInt(1, ID);
            ResultSet rs = st.executeQuery();

            if (rs.next()){
                int adres_id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reiziger_id = rs.getInt("reiziger_id");
                return new Adres(adres_id, postcode, huisnummer, straat, woonplaats, rdao.findById(reiziger_id));
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
    public boolean save(Adres adres) {
        try{
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO adres(adres_id, postcode,huisnummer,straat,woonplaats, reiziger_id) VALUES (?,?,?,?,?,?)");
            // sql statment waar je toevoegt
            //parameters inzetten met de gegevens
            st.setInt(1, adres.getID());
            st.setString(2, adres.getPostcode());
            st.setString(3, adres.getHuisnummer());
            st.setString(4, adres.getStraat());
            st.setString(5, adres.getWoonplaats());
            st.setInt(6, adres.getReiziger().getID());
            // laten runnen
            st.executeUpdate();

            System.out.println(adres.getPostcode()+ adres.getHuisnummer()+ adres.getStraat()+adres.getWoonplaats());
        }catch (SQLException excp){
            System.out.println(excp.getMessage());
            excp.getSQLState();
        }
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException{
        //sql statement om te updaten
        PreparedStatement st = conn.prepareStatement(
                "UPDATE adres SET adres_id=?, postcode=? , huisnummer= ?, straat= ?, woonplaats=?, reiziger_id=?  WHERE adres_id= ?");
        st.setInt(1, adres.getID());
        st.setString(2, adres.getPostcode());
        st.setString(3, adres.getHuisnummer());
        st.setString(4, adres.getStraat());
        st.setString(5, adres.getWoonplaats());
        st.setInt(6,adres.getReiziger().getID());
        st.setInt(7, adres.getID());
        st.executeUpdate();

        System.out.println("Gegevens zijn gewijzigd");
        return true;
    }

    @Override
    public void delete(Adres adres) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("DELETE FROM adres WHERE adres_id= ?");

        statement.setInt(1, adres.getID());
        statement.executeUpdate();

        System.out.println("De adres is verwijderd");

        // extra controle ter verwijdering
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Adres is verwijderd");
        }
    }


    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM adres WHERE reiziger_id=? ");
            st.setInt(1, reiziger.getID());

            ResultSet rs = st.executeQuery();

            while(rs.next()){

                int adres_id;
                int reiziger_id;
                String postcode;
                String huisnummer;
                String straat;
                String woonplaats;

                adres_id = rs.getInt("adres_id");
                postcode = rs.getString("postcode");
                huisnummer = rs.getString("huisnummer");
                straat = rs.getString("straat");
                woonplaats = rs.getString("woonplaats");
                reiziger_id = rs.getInt("reiziger_id");
                System.out.println(reiziger_id);
                //spaties
                System.out.println(adres_id+" "+postcode+" "+huisnummer+" "+straat+" "+woonplaats);


                Adres adres = new Adres(adres_id, postcode,huisnummer, straat, woonplaats, reiziger );
                return adres;

            }
            rs.close();
            st.close();




        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Het zoeken is niet gelukt");
            throw new RuntimeException("Tijd voorbij");
        }
        return null;

    }

    @Override
    public List<Adres> findAll() {
        try{

            List<Adres> AlleAdressen = new ArrayList<>();

            Statement st = conn.createStatement();
            String query = "SELECT adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id FROM adres ";

            ResultSet rs = st.executeQuery(query);

            System.out.println("Er zijn de volgende adressen gevonden: ");
            while(rs.next()){

                int adres_id;
                String postcode;
                String huisnummer;
                String straat;
                String woonplaats;

                adres_id = rs.getInt(1);
                postcode = rs.getString(2);
                huisnummer = rs.getString(3);
                straat = rs.getString(4);
                woonplaats = rs.getString(5);

                AlleAdressen.add(new Adres(adres_id, postcode,huisnummer, straat, woonplaats));
            }

            rs.close();
            st.close();

            return AlleAdressen;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Het zoeken is niet gelukt");
            throw new RuntimeException("Tijd voorbij");
        }
    }
}
