package P2P3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private Connection conn;
    private ReizigerDAO rdao;


    public OVChipkaartDAOPsql(Connection connection, ReizigerDAO rdao){
        this.conn = connection;
        this.rdao = rdao;
    }

    public OVChipkaartDAOPsql(Connection connection) {
        this.conn = connection;
    }


    @Override
    public OVChipkaart findByKaartNummer(int kaart_nummer) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT* FROM ov_chipkaart WHERE kaart_nummer = ?");
        st.setInt(1, kaart_nummer);
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            int kaart_nummer2 = rs.getInt("kaart_nummer");
            Date geldig_tot = rs.getDate("geldig_tot");
            int klasse = rs.getInt("klasse");
            Double saldo = rs.getDouble("saldo");
            int reiziger_id = rs.getInt("reiziger_id");
            OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer2, geldig_tot, klasse, saldo, rdao.findById(reiziger_id));
            return ovChipkaart;
        }
        return null;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try{
            if(rdao.findById(ovChipkaart.getReiziger().getID())== null)
                return rdao.save(ovChipkaart.getReiziger());

            PreparedStatement st = conn.prepareStatement("INSERT INTO ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?,?,?,?,?)");

            st.setInt(1, ovChipkaart.getKaart_nummer());
            st.setDate(2, ovChipkaart.getGeldig_tot());
            st.setInt(3, ovChipkaart.getKlasse());
            st.setDouble(4, ovChipkaart.getSaldo());
            st.setInt(5, ovChipkaart.getReiziger().getID());

            st.executeUpdate();

        }catch (SQLException excp){
            System.out.println(excp.getMessage());
            excp.getSQLState();
        }
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {

        //sql statement om te updaten
        PreparedStatement st = conn.prepareStatement("UPDATE ov_chipkaart SET kaart_nummer=? ,geldig_tot=?, klasse=? , saldo= ?  WHERE kaart_nummer= ?");
        st.setInt(1, ovChipkaart.getKaart_nummer());
        st.setDate(2, ovChipkaart.getGeldig_tot());
        st.setInt(3, ovChipkaart.getKlasse());
        st.setDouble(4, ovChipkaart.getSaldo());
        st.setInt(5, ovChipkaart.getKaart_nummer());

        st.executeUpdate();

        return true;
    }

    @Override
    public void delete(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "DELETE FROM ov_chipkaart WHERE kaart_nummer= ?");
        statement.setInt(1, ovChipkaart.getKaart_nummer());
        statement.executeUpdate();


        statement.close();

    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id=? ");
            st.setInt(1, reiziger.getID());

            ResultSet rs = st.executeQuery();

            OVChipkaart ovChipkaart = null;
            List<OVChipkaart> ovChipkaarts = new ArrayList<>();

            while(rs.next()){

                int kaart_nummer;
                java.util.Date geldig_tot;
                int klasse;
                double saldo;

                kaart_nummer = rs.getInt("kaart_nummer");
                geldig_tot = rs.getDate("geldig_tot");
                klasse = rs.getInt("klasse");
                saldo = rs.getDouble("saldo");

                ovChipkaart  = new OVChipkaart(kaart_nummer, (Date) geldig_tot,klasse,
                        saldo, reiziger );
                ovChipkaarts.add(ovChipkaart);


            }
            rs.close();
            st.close();

            return ovChipkaarts;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Het zoeken is niet gelukt");
            throw new RuntimeException("Tijd voorbij");
        }

    }

    @Override
    public List<OVChipkaart> findAll() {
        try{

            List<OVChipkaart> zoekAlles = new ArrayList<>();

            Statement st = conn.createStatement();
            String query = "SELECT * FROM ov_chipkaart ";

            ResultSet rs = st.executeQuery(query);

            System.out.println("Er zijn de volgende reizigers gevonden: ");
            while(rs.next()){

                int kaart_nummer;
                java.util.Date geldig_tot;
                int klasse;
                double saldo;
                int reiziger_id;

                kaart_nummer = rs.getInt(1);
                geldig_tot = rs.getDate(2);
                klasse = rs.getInt(3);
                saldo = rs.getDouble(4);
                reiziger_id = rs.getInt(5);


                System.out.println("reiziger_id: " +reiziger_id + " kaart_nummer: " + kaart_nummer + " geldig_tot: "
                        + geldig_tot + " klasse: " + klasse+" saldo: "+ saldo);

                OVChipkaart ovChipkaart= new OVChipkaart(kaart_nummer, (Date) geldig_tot,
                        klasse, saldo, rdao.findById(reiziger_id) );
                zoekAlles.add(ovChipkaart);
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



    public void setRdao(ReizigerDAOPsql rdao) {
        this.rdao = rdao;
    }


}
