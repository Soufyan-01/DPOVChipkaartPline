package P2P3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;
    private OVChipkaartDAO odao;

    public void setOdao(OVChipkaartDAO odao){
        this.odao = odao;
    };

    public ProductDAOPsql(Connection connection) {
        this.conn = connection;
    }


    @Override
    public boolean save(Product product) {
        try{

            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO Product(product_nummer, naam, beschrijving, prijs) VALUES (?,?,?,?)");
            // sql statment waar je toevoegt
            //parameters inzetten met de gegevens
            st.setInt(1, product.getProduct_nummer());
            st.setString(2, product.getNaam());
            st.setString(3, product.getBeschrijving());
            st.setDouble(4, product.getPrijs());
            // laten runnen
            st.executeUpdate();

            for(OVChipkaart ovChipkaart : product.getOvChipkaarts()){
                PreparedStatement st2 = conn.prepareStatement("INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer) VALUES (?,?) ");
                st2.setInt(1, ovChipkaart.getKaart_nummer());
                st2.setInt(2, product.getProduct_nummer());
                st2.executeUpdate();
            }

            System.out.println(product.getProduct_nummer()+ product.getNaam()+ product.getBeschrijving()+product.getPrijs());
        }catch (SQLException excp){
            System.out.println(excp.getMessage());
            excp.getSQLState();
        }
        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException {

        PreparedStatement st = conn.prepareStatement(
                "UPDATE product SET naam=? , beschrijving= ?, prijs= ?  WHERE product_nummer= ?");
        st.setInt(4, product.getProduct_nummer());
        st.setString(1, product.getNaam());
        st.setString(2, product.getBeschrijving());
        st.setDouble(3, product.getPrijs());
        st.executeUpdate();

        PreparedStatement st2 = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
        st2.setInt(1, product.getProduct_nummer());
        st2.executeUpdate();

        for(OVChipkaart ovChipkaart : product.getOvChipkaarts()){
            PreparedStatement st3 = conn.prepareStatement("INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer) VALUES (?,?) ");
            st3.setInt(1, ovChipkaart.getKaart_nummer());
            st3.setInt(2, product.getProduct_nummer());
            st3.executeUpdate();
        }

        return false;
    }



    @Override
    public void delete(Product product) throws SQLException {
        PreparedStatement statement1 = conn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer=?");
        statement1.setInt(1, product.getProduct_nummer());
        statement1.executeUpdate();

        PreparedStatement statement2 = conn.prepareStatement("DELETE FROM product WHERE product_nummer= ?");
        statement2.setInt(1, product.getProduct_nummer());
        statement2.executeUpdate();

    }


    @Override
    public Product findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement st = conn.prepareStatement(
                    "SELECT p.naam, p.beschrijving, p.prijs, ov.kaart_nummer " +
                    "FROM product p " +
                    " JOIN ov_chipkaart_product ovp on p.product_nummer = ovp.product_nummer" +
                            " WHERE ovp.kaart_nummer = ?");
            //hier krijg je een loop waardoor andere ovs en producten gereturned worden
            // want als je een product returnt dan return je ook een ovchipkaart maar bij die ovchipkaart horen ook andere producten
            // die worden gertured/ weergegeven
            st.setInt(1, ovChipkaart.getKaart_nummer());

            ResultSet rs = st.executeQuery();

            while(rs.next()){

              String naam;
              String beschrijving;
              double prijs;
              int kaart_nummer;
              int product_nummer;

                naam = rs.getString("naam");
                beschrijving = rs.getString("beschrijving");
                prijs = rs.getDouble("prijs");
                product_nummer = rs.getInt("product_nummer");
                kaart_nummer = rs.getInt("kaart_nummer");

                //spaties
                System.out.println(naam +" "+beschrijving+" "+prijs+" "+product_nummer+" "+ kaart_nummer);


                Product product = new Product(product_nummer, naam, beschrijving, prijs);
                OVChipkaart ovChipkaart1 = odao.findByKaartNummer(kaart_nummer);
                product.addOVKaart(ovChipkaart1);
                return product;

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
    public List<Product> findAll() {
        try{

            List<Product> AlleProducten = new ArrayList<>();

            Statement st = conn.createStatement();
            String query =
                    " SELECT p.product_nummer, naam , beschrijving, prijs, ov.kaart_nummer " +
        "FROM product p" +
        " JOIN ov_chipkaart_product ovp ON p.product_nummer = ovp.product_nummer " +
        " JOIN ov_chipkaart ov ON ov.kaart_nummer = ovp.kaart_nummer " +
        " ORDER BY product_nummer;";


            ResultSet rs = st.executeQuery(query);

            System.out.println("Er zijn de volgende producten gevonden: ");
            while(rs.next()){

                int product_nummer;
                String naam;
                String beschrijving;
                double prijs;

                product_nummer = rs.getInt(1);
                naam = rs.getString(2);
                beschrijving = rs.getString(3);
                prijs = rs.getDouble(4);

                AlleProducten.add(new Product(product_nummer, naam, beschrijving, prijs));

            }

            rs.close();
            st.close();

            return AlleProducten;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Het zoeken is niet gelukt");
            throw new RuntimeException("Tijd voorbij");
        }
    }
    }

